package br.com.ecore.config.handler;

import br.com.ecore.exception.EcoreAlreadyReportedException;
import br.com.ecore.exception.EcoreBadRequestException;
import br.com.ecore.exception.EcoreInternalServerException;
import br.com.ecore.exception.EcoreNotFoundException;
import br.com.ecore.common.EcoreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ErrorExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EcoreInternalServerException.class})
    protected ResponseEntity<EcoreResponse> handleInternalServerException(EcoreInternalServerException exception, WebRequest request) {
        return ResponseEntity.internalServerError().body(new EcoreResponse(INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {EcoreAlreadyReportedException.class})
    protected ResponseEntity<EcoreResponse> handleAlreadyReportedException(EcoreAlreadyReportedException exception, WebRequest request) {
        return ResponseEntity.status(ALREADY_REPORTED).body(new EcoreResponse(ALREADY_REPORTED.value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {EcoreNotFoundException.class})
    protected ResponseEntity<EcoreResponse> handleNotFoundException(EcoreNotFoundException exception, WebRequest request) {
        return ResponseEntity.status(NOT_FOUND).body(new EcoreResponse(NOT_FOUND.value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {EcoreBadRequestException.class})
    protected ResponseEntity<EcoreResponse> handleBadRequestException(EcoreBadRequestException exception, WebRequest request) {
        return ResponseEntity.status(BAD_REQUEST).body(new EcoreResponse(BAD_REQUEST.value(), exception.getMessage()));
    }
}
