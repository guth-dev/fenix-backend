package com.fenix.backend.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entity, Long id) {
        super(entity + " não encontrado(a) com id: " + id);
    }
}
