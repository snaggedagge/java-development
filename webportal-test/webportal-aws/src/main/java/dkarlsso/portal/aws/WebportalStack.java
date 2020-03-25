package dkarlsso.portal.aws;

import software.amazon.awscdk.core.*;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.elasticbeanstalk.CfnApplication;
import software.amazon.awscdk.services.elasticbeanstalk.CfnApplicationVersion;
import software.amazon.awscdk.services.elasticbeanstalk.CfnConfigurationTemplate;
import software.amazon.awscdk.services.elasticbeanstalk.CfnEnvironment;
import software.amazon.awscdk.services.rds.DatabaseInstanceEngine;
import software.amazon.awscdk.services.rds.DatabaseInstanceFromSnapshot;
import software.amazon.awscdk.services.rds.StorageType;
import software.amazon.awscdk.services.route53.CfnRecordSet;

import java.util.Arrays;
import java.util.Collections;

public class WebportalStack extends Stack {
    public WebportalStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public WebportalStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        final CfnApplication cfnApplication = CfnApplication.Builder.create(this, "WebportalApplication")
                .applicationName("WebportalApplication")
                .build();

        final CfnApplicationVersion cfnApplicationVersion = CfnApplicationVersion.Builder.create(this, "WebportalApplicationVersion")
                .applicationName(Fn.ref("WebportalApplication"))
                .description("Application for webportal")
                .sourceBundle(new CfnApplicationVersion.SourceBundleProperty.Builder()
                        .s3Bucket("elasticbeanstalk-eu-west-1-145158422295")
                        .s3Key(System.getenv("S3_KEY") == null ? "S3_KEY_NOT_DEFINED" : System.getenv("S3_KEY"))
                        //.s3Key("uploads/webportal-1.0.jar")
                        .build())
                .build();


        PropertyTest.builder()
                .Namespace("aws:elasticbeanstalk:environment")
                .OptionName("EnvironmentType")
                .Value("SingleInstance")
                .build();

        // Cant use this, dont know what must be done.............
        final CfnEnvironment.OptionSettingProperty optionSettingProperty = CfnEnvironment.OptionSettingProperty.builder()
                .namespace("aws:elasticbeanstalk:environment")
                .optionName("EnvironmentType")
                .value("SingleInstance")
                .build();



        final CfnConfigurationTemplate cfnConfigurationTemplate = CfnConfigurationTemplate.Builder.create(this, "WebportalApplicationConfiguration")
                .applicationName(Fn.ref("WebportalApplication"))
                .optionSettings(Arrays.asList(
                        PropertyTest.builder()
                                .Namespace("aws:autoscaling:launchconfiguration")
                                .OptionName("InstanceType")
                                .Value("t3.micro")
                                .build(),
                        PropertyTest.builder()
                                .Namespace("aws:elasticbeanstalk:environment")
                                .OptionName("EnvironmentType")
                                .Value("SingleInstance")
                                .build(),
                        PropertyTest.builder()
                                .Namespace("aws:elasticbeanstalk:application:environment")
                                .OptionName("WEBPORTAL_SQL_PASSWORD")
                                .Value("lddo7wG8NBXTlx4dRYBa") //TODO: Move this out of here
                                .build(),
                        PropertyTest.builder()
                                .Namespace("aws:elasticbeanstalk:application:environment")
                                .OptionName("WEBPORTAL_SQL_USER")
                                .Value("webportal")
                                .build(),
                        PropertyTest.builder()
                                .Namespace("aws:elasticbeanstalk:application:environment")
                                .OptionName("WEBPORTAL_SQL_URL")
                                .Value("jdbc:mysql://mysql.dkarlsso.com:3306/webportal")
                                .build()))
                .solutionStackName("64bit Amazon Linux 2018.03 v2.10.2 running Java 8")
                .build();


        final CfnEnvironment cfnEnvironment = CfnEnvironment.Builder.create(this, "WebportalEnvironment")
                .applicationName(Fn.ref("WebportalApplication"))
                .templateName(Fn.ref("WebportalApplicationConfiguration"))
                .versionLabel(Fn.ref("WebportalApplicationVersion"))
                .build();


/*
        // Has been created manually.
        CfnHostedZone.Builder.create(this, "DkarlssoZone")
                .name("dkarlsso.com.")
                .build();
 */

        CfnRecordSet.Builder.create(this, "WebportalRecordSet")
                .name("dkarlsso.com")
                .type("A")
                .hostedZoneName("dkarlsso.com.")
                .resourceRecords(Collections.singletonList(
                        Fn.getAtt("WebportalEnvironment", "EndpointURL").toString()))
                .ttl("300")
                .build();

        CfnRecordSet.Builder.create(this, "WebportalSpecificRecordSet")
                .name("webportal.dkarlsso.com")
                .type("A")
                .hostedZoneName("dkarlsso.com.")
                .resourceRecords(Collections.singletonList(
                        Fn.getAtt("WebportalEnvironment", "EndpointURL").toString()))
                .ttl("300")
                .build();
    }

}
