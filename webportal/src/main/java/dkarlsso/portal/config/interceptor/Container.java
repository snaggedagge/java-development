package dkarlsso.portal.config.interceptor;

public class Container <T>{

    private T value;


    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
