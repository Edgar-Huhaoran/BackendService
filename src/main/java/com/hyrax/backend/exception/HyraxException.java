package com.hyrax.backend.exception;

import org.springframework.http.HttpStatus;

public class HyraxException extends RuntimeException {

    private final HttpStatus status;
    private final int errorCode;
    private final String clientMessage;

    public HyraxException(ErrorType errorType) {
        super(errorType.getDetailMessage());
        this.status = errorType.getStatus();
        this.errorCode = errorType.getErrorCode();
        this.clientMessage = errorType.getClinetMessage();
    }

    public HyraxException(ErrorType errorType, Throwable cause) {
        super(errorType.getDetailMessage(), cause);
        this.status = errorType.getStatus();
        this.errorCode = errorType.getErrorCode();
        this.clientMessage = errorType.getClinetMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getClientMessage() {
        return clientMessage;
    }
}
