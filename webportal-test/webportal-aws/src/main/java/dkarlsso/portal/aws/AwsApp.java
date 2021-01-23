package dkarlsso.portal.aws;

import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.StackProps;

public final class AwsApp {


    public static void main(final String[] args) {
        final App app = new App();

        final RDSStack rdsStack = new RDSStack(app, "Webportal-RDS-stack", StackProps.builder()
                .env(Environment.builder()
                        .account("145158422295")
                        .region("eu-west-1")
                        .build())
                .stackName("Webportal-RDS-stack")
                .build());
// "arn:aws:rds:eu-west-1:145158422295:snapshot:webportal-rds-stack-snapshot-webportalrdsdatabase0b756546-1hw0c7jzj98rh"
                /*
                final AutoScalingStack autoScalingStack =  new AutoScalingStack(app, "Autoscaling-stack", StackProps.builder()
                .env(Environment.builder()
                        .account("145158422295")
                        .region("eu-west-1")
                        .build())
                .stackName("Autoscaling-stack")
                .build());

                */

        final WebportalStack webportalStack = new WebportalStack(app, "Webportal-stack", StackProps.builder()
                .env(Environment.builder()
                        .account("145158422295")
                        .region("eu-west-1")
                        .build())
                .stackName("Webportal-stack")
                .build(), rdsStack);


                webportalStack.addDependency(rdsStack);
                //webportalStack.addDependency(autoScalingStack);

        app.synth();
    }
}
