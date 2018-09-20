package dkarlsso.smartmirror.javafx.actions;


import dkarlsso.smartmirror.javafx.model.CommandEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Action {
    CommandEnum commandName();
}
