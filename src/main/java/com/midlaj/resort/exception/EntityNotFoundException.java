package com.midlaj.resort.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    private String entityName;

    public EntityNotFoundException(String entityName) {
        super("Cannot Find " + entityName + ".");
        this.entityName = entityName;
    }

}