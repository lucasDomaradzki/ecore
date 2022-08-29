package br.com.ecore.exception;

public class EcoreException extends Exception {

    private static final long serialVersionUID = 7181717435864914675L;

    public EcoreException() {
        super();
    }

    public EcoreException(final String message) {
        super(message);
    }

    public EcoreException(final String message, final Throwable exception) {
        super(message, exception);
    }

    public EcoreException(final Throwable exception) {
        super(exception);
    }

}
