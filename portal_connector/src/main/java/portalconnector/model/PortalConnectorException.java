package portalconnector.model;

public class PortalConnectorException extends Exception{

    public PortalConnectorException(){
        super();
    }

    public PortalConnectorException(final String message){
        super(message);
    }

    public PortalConnectorException(final String message, final Exception e){
        super(message, e);
    }
}
