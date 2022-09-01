package br.com.ecore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.text.MessageFormat.format;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class EcoreInternalServerException extends EcoreException {

    private static final long serialVersionUID = 5221798354849644486L;

    public EcoreInternalServerException() {
        super();
    }

    public EcoreInternalServerException(final String message, Object... args) {
        super(format(message, args));
    }

    public EcoreInternalServerException(final String message, final Throwable exception, Object... args) {
        super(format(message, args), exception);
    }

    public EcoreInternalServerException(final Throwable exception) {
        super(exception);
    }

}
