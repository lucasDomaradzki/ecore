package br.com.ecore.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EcoreResponse implements Serializable {

    private static final long serialVersionUID = -6317845388595399229L;

    private int httpStatus;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String createdId;

    public EcoreResponse(int httpStatus, String createdId, String message) {
        this.httpStatus = httpStatus;
        this.createdId = createdId;
        this.message = message;
    }

    public EcoreResponse(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreatedId() {
        return createdId;
    }

    public void setCreatedId(String createdId) {
        this.createdId = createdId;
    }

}
