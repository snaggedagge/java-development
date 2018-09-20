package dkarlsso.commons.container;

public class BasicContainer<P> {

    private P containedObject;

    public P get() {
        synchronized (this) {
            return containedObject;
        }
    }

    public void set(final P containedObject) {
        synchronized (this) {
            this.containedObject = containedObject;
        }
    }

}
