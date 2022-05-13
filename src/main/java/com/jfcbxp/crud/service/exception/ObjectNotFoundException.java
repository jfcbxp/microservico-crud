package com.jfcbxp.crud.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5161209594785432441L;

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
