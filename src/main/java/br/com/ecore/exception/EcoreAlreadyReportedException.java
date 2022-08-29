package br.com.ecore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED)
public class EcoreAlreadyReportedException extends EcoreException {

    private static final long serialVersionUID = -2726708667081038925L;

    public EcoreAlreadyReportedException() {
        super();
    }

    public EcoreAlreadyReportedException(final String message) {
        super(message);
    }

    public EcoreAlreadyReportedException(final String message, final Throwable exception) {
        super(message, exception);
    }

    public EcoreAlreadyReportedException(final Throwable exception) {
        super(exception);
    }

}
