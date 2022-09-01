package br.com.ecore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.text.MessageFormat.format;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EcoreNotFoundException extends EcoreException {

    private static final long serialVersionUID = -8377701523242845916L;

    public EcoreNotFoundException() {
        super();
    }

    public EcoreNotFoundException(final String message, Object... args) {
        super(format(message, args));
    }

    public EcoreNotFoundException(final String message, final Throwable exception, Object... args) {
        super(format(message, args), exception);
    }

    public EcoreNotFoundException(final Throwable exception) {
        super(exception);
    }

}
