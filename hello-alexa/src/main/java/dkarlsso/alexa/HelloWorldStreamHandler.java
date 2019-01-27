package dkarlsso.alexa;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.builder.CustomSkillBuilder;
import dkarlsso.alexa.intents.CancelandStopIntentHandler;
import dkarlsso.alexa.intents.HelloWorldIntentHandler;
import dkarlsso.alexa.intents.HelpIntentHandler;
import dkarlsso.alexa.intents.RadioIntentHandler;

//import com.amazon.ask.Skills;


public class HelloWorldStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {

        final CustomSkillBuilder skillBuilder = new CustomSkillBuilder();

        skillBuilder.addRequestHandlers(
                new CancelandStopIntentHandler(),
                new HelloWorldIntentHandler(),
                new HelpIntentHandler(),
                new LaunchRequestHandler(),
                new SessionEndedRequestHandler(),
                new RadioIntentHandler());

        return skillBuilder.build();

//        return Skills.standard()
//                .addRequestHandlers(
//                        new CancelandStopIntentHandler(),
//                        new HelloWorldIntentHandler(),
//                        new HelpIntentHandler(),
//                        new LaunchRequestHandler(),
//                        new SessionEndedRequestHandler())
//                .build();
    }

    public HelloWorldStreamHandler() {
        super(getSkill());
    }

}