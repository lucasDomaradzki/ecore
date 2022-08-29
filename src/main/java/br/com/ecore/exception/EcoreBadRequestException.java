package br.com.ecore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EcoreBadRequestException extends EcoreException {

    private static final long serialVersionUID = 6847669829454599826L;

    public EcoreBadRequestException() {
        super();
    }

    public EcoreBadRequestException(final String message) {
        super(message);
    }

    public EcoreBadRequestException(final String message, final Throwable exception) {
        super(message, exception);
    }

    public EcoreBadRequestException(final Throwable exception) {
        super(exception);
    }
}
