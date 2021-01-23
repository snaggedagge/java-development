package dkarlsso.portal.aws;

import java.util.Arrays;
import java.util.Collections;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Fn;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.autoscaling.CfnAutoScalingGroup;
import software.amazon.awscdk.services.autoscaling.CfnLaunchConfiguration;
import software.amazon.awscdk.services.ec2.CfnSecurityGroupIngress;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ec2.VpcLookupOptions;
import software.amazon.awscdk.services.elasticbeanstalk.CfnApplication;
import software.amazon.awscdk.services.elasticbeanstalk.CfnApplicationVersion;
import software.amazon.awscdk.services.elasticbeanstalk.CfnConfigurationTemplate;
import software.amazon.awscdk.services.elasticbeanstalk.CfnEnvironment;
import software.amazon.awscdk.services.route53.CfnRecordSet;
import software.amazon.awssdk.services.elasticbeanstalk.ElasticBeanstalkClient;
import software.amazon.awssdk.services.elasticbeanstalk.model.ListAvailableSolutionStacksRequest;
import software.amazon.awssdk.services.elasticbeanstalk.model.ListPlatformVersionsRequest;
import software.amazon.awssdk.services.elasticbeanstalk.model.PlatformFilter;
import software.amazon.awssdk.services.elasticbeanstalk.model.PlatformSummary;


public class WebportalStack extends Stack {
    public WebportalStack(final Construct parent, final String id,  final RDSStack rdsStack) {
        this(parent, id, null, rdsStack);
    }

    public WebportalStack(final Construct parent, final String id, final StackProps props, final RDSStack rdsStack) {
        super(parent, id, props);


        final CfnApplication cfnApplication = CfnApplication.Builder.create(this, "WebportalApplication")
                .applicationName("WebportalApplication")
                .build();

        if (System.getenv("S3_KEY") == null) {
                // Fix logging... @Sl4j didnt work
                System.out.println("S3 key was not provided in environment, will use default");
        }

        final CfnApplicationVersion cfnApplicationVersion = CfnApplicationVersion.Builder.create(this, "WebportalApplicationVersion")
                .applicationName(Fn.ref("WebportalApplication"))
                .description("Application for webportal")
                .sourceBundle(new CfnApplicationVersion.SourceBundleProperty.Builder()
                        .s3Bucket("elasticbeanstalk-eu-west-1-145158422295")
                        .s3Key(System.getenv("S3_KEY") == null ? "uploads/webportal-1.0.jar" : System.getenv("S3_KEY"))
                        //.s3Key("uploads/webportal-1.0.jar")
                        .build())
                .build();


        CfnConfigurationTemplate.ConfigurationOptionSettingProperty.builder()
                .namespace("aws:elasticbeanstalk:environment")
                .optionName("EnvironmentType")
                .value("SingleInstance")
                .build();


        final PlatformSummary platformSummary = ElasticBeanstalkClient.create()
                .listPlatformVersions(ListPlatformVersionsRequest.builder()
                .filters(PlatformFilter.builder()
                        .type("PlatformBranchName")
                        .operator("=")
                        .values("Java 8 running on 64bit Amazon Linux")
                        .build())
                .build()).platformSummaryList().stream()
                .findFirst()
                .orElseThrow(IllegalStateException::new);


        // Cant use this, dont know what must be done.............
        final CfnEnvironment.OptionSettingProperty optionSettingProperty = CfnEnvironment.OptionSettingProperty.builder()
                .namespace("aws:elasticbeanstalk:environment")
                .optionName("EnvironmentType")
                .value("SingleInstance")
                .build();



/*
        final String solutionName = ElasticBeanstalkClient.create()
                .listAvailableSolutionStacks().solutionStacks().stream()
                .filter(stack -> stack.contains("64bit Amazon Linux") && stack.contains("Java 8"))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
 */
        System.out.println("Running solution " + platformSummary.platformArn());

        final CfnConfigurationTemplate cfnConfigurationTemplate = CfnConfigurationTemplate.Builder.create(this, "WebportalApplicationConfiguration")
                .applicationName(Fn.ref("WebportalApplication"))
                .optionSettings(Arrays.asList(
/* Not valid
                        CfnConfigurationTemplate.ConfigurationOptionSettingProperty.builder()
                                .Namespace("aws:autoscaling:launchconfiguration")
                                .OptionName("LaunchConfigurationName")
                                .Value(launchConfiguration.getLaunchConfigurationName())
                                .build(),
*/
                        CfnConfigurationTemplate.ConfigurationOptionSettingProperty.builder()
                                .namespace("aws:autoscaling:launchconfiguration")
                                .optionName("IamInstanceProfile")
                                .value("aws-elasticbeanstalk-ec2-role")
                                .build(),
                        CfnConfigurationTemplate.ConfigurationOptionSettingProperty.builder()
                                .namespace("aws:autoscaling:launchconfiguration")
                                .optionName("InstanceType")
                                .value("t3.micro")
                                .build(),
                        CfnConfigurationTemplate.ConfigurationOptionSettingProperty.builder()
                                .namespace("aws:elasticbeanstalk:environment")
                                .optionName("EnvironmentType")
                                .value("SingleInstance")
                                .build(),
                        CfnConfigurationTemplate.ConfigurationOptionSettingProperty.builder()
                                .namespace("aws:elasticbeanstalk:application:environment")
                                .optionName("WEBPORTAL_SQL_PASSWORD")
                                .value("lddo7wG8NBXTlx4dRYBa") //TODO: Move this out of here
                                .build(),
                        CfnConfigurationTemplate.ConfigurationOptionSettingProperty.builder()
                                .namespace("aws:elasticbeanstalk:application:environment")
                                .optionName("WEBPORTAL_SQL_USER")
                                .value("webportal")
                                .build(),
                        CfnConfigurationTemplate.ConfigurationOptionSettingProperty.builder()
                                .namespace("aws:elasticbeanstalk:application:environment")
                                .optionName("WEBPORTAL_SQL_URL")
                                .value(rdsStack.getRdsEndpoint())
                                .build()))
                // TODO: Fetch
                .solutionStackName(platformSummary.platformArn())
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
