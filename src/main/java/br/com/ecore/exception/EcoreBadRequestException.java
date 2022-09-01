package br.com.ecore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.text.MessageFormat.format;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EcoreBadRequestException extends EcoreException {

    private static final long serialVersionUID = 6847669829454599826L;

    public EcoreBadRequestException() {
        super();
    }

    public EcoreBadRequestException(final String message, Object... args) {
        super(format(message, args));
    }

    public EcoreBadRequestException(final String message, final Throwable exception, Object... args) {
        super(format(message, args), exception);
    }

    public EcoreBadRequestException(final Throwable exception) {
        super(exception);
    }
}
