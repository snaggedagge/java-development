package dkarlsso.portal.aws;

import software.amazon.awscdk.core.*;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.elasticbeanstalk.CfnApplication;
import software.amazon.awscdk.services.elasticbeanstalk.CfnApplicationVersion;
import software.amazon.awscdk.services.elasticbeanstalk.CfnConfigurationTemplate;
import software.amazon.awscdk.services.elasticbeanstalk.CfnEnvironment;
import software.amazon.awscdk.services.rds.*;
import software.amazon.awscdk.services.route53.CfnRecordSet;

import java.util.Arrays;
import java.util.Collections;

import lombok.Getter;

@Getter
public class RDSStack extends Stack {

private final String rdsEndpoint;


    public RDSStack(final Construct parent, final String id, final String snapshotVersion) {
        this(parent, id, null, snapshotVersion);
    }

    public RDSStack(final Construct parent, final String id, final StackProps props, final String snapshotVersion) {
        super(parent, id, props);

        IVpc vpc = Vpc.fromLookup(this, "DefaultVpc", VpcLookupOptions.builder()
                .isDefault(true).build());


        final SecurityGroup securityGroup = SecurityGroup.Builder.create(this, "WebportalRDSSecurityGroup")
                .securityGroupName("rds-webportal-securityGroup")
                .allowAllOutbound(true)
                .description("Security group for Webportal RDS database")
                .vpc(vpc)
                .build();

        CfnSecurityGroupIngress.Builder.create(this, "WebportalRDSSecurityGroupIngress")
                .ipProtocol("tcp")
                .cidrIp("0.0.0.0/0")
                .fromPort(3306)
                .toPort(3306)
                .groupId(securityGroup.getSecurityGroupId())
                .build();


/*
Subnet.Builder.create().
*/
        final DatabaseInstanceFromSnapshot database = DatabaseInstanceFromSnapshot.Builder.create(this, "WebportalRDSDatabase")
                .engine(DatabaseInstanceEngine.MYSQL)
                .instanceClass(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO))
                .vpcPlacement(SubnetSelection.builder()
                        .subnetType(SubnetType.PUBLIC)
                        //.subnets()
                        .build())
                .securityGroups(Arrays.asList(securityGroup))
                .vpc(vpc)
                .storageType(StorageType.GP2)
                .multiAz(false)
                .autoMinorVersionUpgrade(false)
                .allocatedStorage(20)
                .deletionProtection(false)
                //.databaseName("webportal2")
                //.masterUsername("dkarlsso")

                //.masterUserPassword(SecretValue.plainText("XJvXy0anjGFCLrrYqJwH"))
                .port(3306)
                .snapshotIdentifier(snapshotVersion)
                //.removalPolicy(RemovalPolicy.SNAPSHOT)
                .build();


        CfnRecordSet.Builder.create(this, "DatabaseRecordSet")
                .name("mysql.dkarlsso.com")
                .type("CNAME")
                .hostedZoneName("dkarlsso.com.")
                .resourceRecords(Collections.singletonList(database.getDbInstanceEndpointAddress()))
                .ttl("300")
                .build();
                this.rdsEndpoint = database.getDbInstanceEndpointAddress();
    }
}
