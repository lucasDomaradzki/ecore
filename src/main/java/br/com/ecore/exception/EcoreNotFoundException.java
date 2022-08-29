package br.com.ecore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EcoreNotFoundException extends EcoreException {

    private static final long serialVersionUID = -8377701523242845916L;

    public EcoreNotFoundException() {
        super();
    }

    public EcoreNotFoundException(final String message) {
        super(message);
    }

    public EcoreNotFoundException(final String message, final Throwable exception) {
        super(message, exception);
    }

    public EcoreNotFoundException(final Throwable exception) {
        super(exception);
    }

}
