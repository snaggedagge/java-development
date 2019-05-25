package dkarlsso.smartmirror.alexa;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.builder.CustomSkillBuilder;
import dkarlsso.smartmirror.alexa.intents.action.LightsIntentHandler;
import dkarlsso.smartmirror.alexa.intents.action.RadioIntentHandler;
import dkarlsso.smartmirror.alexa.intents.action.TakeSelfieIntentHandler;
import dkarlsso.smartmirror.alexa.intents.action.WeatherIntentHandler;
import dkarlsso.smartmirror.alexa.intents.builtin.CancelandStopIntentHandler;
import dkarlsso.smartmirror.alexa.intents.builtin.HelpIntentHandler;

//import com.amazon.ask.Skills;


public class SmartMirrorStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {

        final CustomSkillBuilder skillBuilder = new CustomSkillBuilder();

        skillBuilder.addRequestHandlers(
                new CancelandStopIntentHandler(),
                new HelpIntentHandler(),
                new LaunchRequestHandler(),
                new SessionEndedRequestHandler(),
                new RadioIntentHandler(),
                new WeatherIntentHandler(),
                new TakeSelfieIntentHandler(),
                new LightsIntentHandler());

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

    public SmartMirrorStreamHandler() {
        super(getSkill());
    }

}