package dkarlsso.portal.domain.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAuthorizedException  extends Exception{

    public NotAuthorizedException() {
        super();
    }

    public NotAuthorizedException(String message) {
        super(message);
    }
}
