package dkarlsso.smartmirror.javafx.actions.annotation;


import dkarlsso.commons.speechrecognition.CommandEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Action {

    CommandEnum commandName();

}
