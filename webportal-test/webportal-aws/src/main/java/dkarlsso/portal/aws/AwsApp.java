package dkarlsso.portal.aws;

import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.StackProps;

public final class AwsApp {


    public static void main(final String[] args) {
        final App app = new App();


        new WebportalStack(app, "Webportal-stack", StackProps.builder()
                .env(Environment.builder()
                        .account("145158422295")
                        .region("eu-west-1")
                        .build())
                .stackName("Webportal-stack")
                .build());


        new RDSStack(app, "Webportal-RDS-stack", StackProps.builder()
                .env(Environment.builder()
                        .account("145158422295")
                        .region("eu-west-1")
                        .build())
                .stackName("Webportal-RDS-stack")
                .build());
        app.synth();
    }
}
