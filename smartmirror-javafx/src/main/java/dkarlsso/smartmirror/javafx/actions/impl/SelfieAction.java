package dkarlsso.smartmirror.javafx.actions.impl;

import dkarlsso.commons.speechrecognition.CommandEnum;
import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.smartmirror.javafx.actions.annotation.Action;

@Action(commandName = CommandEnum.SELFIE)
public class SelfieAction implements CommandAction {
    @Override
    public void execute() {
/*                try {
            File picture = null;
            if(OSHelper.isRaspberryPi()) {
                //picture = camera.takePicture();
            }
            else {
                picture = new File(ApplicationUtils.getSubfolder("selfies"),"test.jpg");
            }

            Platform.runLater(() ->
            {
                final ImageView selfieImage = new ImageView(new Image(picture.toURI().toString()));
                viewInterface.displayView(selfieImage);
                PauseTransition pt = new PauseTransition();
                pt.setDuration(Duration.millis(3000));
                pt.setOnFinished(e ->
                        initAnimation());
                pt.play();
            });

        } catch (CommonsException e) {
            LOG.error(e.getMessage(), e);
        }*/
    }
}
