package eu.grigoriev.jasmine.exceptions.application;

import eu.grigoriev.jasmine.definitions.ErrorCode;

public class EntityNotFoundException extends JasmineException {

    public EntityNotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }
}
