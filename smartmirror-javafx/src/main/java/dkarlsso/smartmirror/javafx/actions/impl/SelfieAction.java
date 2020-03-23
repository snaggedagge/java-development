package dkarlsso.smartmirror.javafx.actions.impl;

import dkarlsso.smartmirror.javafx.model.CommandEnum;
import dkarlsso.commons.commandaction.CommandAction;



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

        } catch (CommonsRaspberryException e) {
            LOG.error(e.getMessage(), e);
        }*/
    }
}
