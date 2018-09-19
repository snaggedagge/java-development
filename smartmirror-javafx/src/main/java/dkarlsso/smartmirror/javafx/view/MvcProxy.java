package dkarlsso.smartmirror.javafx.view;

import java.util.function.Function;

public interface MvcProxy {

    void executeFunction(Class classToExecute, Function<?,Void> functionToExecute );

    <T> T getClass(Class<T> implementationToGet);


}
