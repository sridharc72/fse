package com.pulsara.fse.exceptions;

import org.springframework.http.HttpStatus;

public class FSEServiceException extends RuntimeException {

    private HttpStatus status;

    public FSEServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
