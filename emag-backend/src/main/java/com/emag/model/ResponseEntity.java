package com.emag.model;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseEntity {
    private Object object;
    private HttpStatus status;

    public ResponseEntity(Object object, HttpStatus status) {
        this.object = object;
        this.status = status;
    }

    public ResponseEntity() {
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
