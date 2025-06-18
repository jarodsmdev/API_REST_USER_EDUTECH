package com.briones.users.management.exception;

import lombok.Getter;

@Getter
public class DuplicateKeyException extends RuntimeException{
    private String field;
    private String value;

    public DuplicateKeyException(String message) {
        super(message);
    }

    public DuplicateKeyException(String message, String field, String value) {
        super(message);
        this.field = field;
        this.value = value;
    }
}
