package eu.grigoriev.jasmine.exceptions.application;

import eu.grigoriev.jasmine.definitions.ErrorCode;

public class EntityAlreadyExistsException extends JasmineException {

    public EntityAlreadyExistsException(String message) {
        super(ErrorCode.ALREADY_EXISTS, message);
    }
}
