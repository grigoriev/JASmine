package eu.grigoriev.jasmine.exceptions;

import eu.grigoriev.jasmine.definitions.ErrorCode;

public class EntityAlreadyExistsException extends JasmineException {

    public EntityAlreadyExistsException(String message) {
        super(ErrorCode.ALREADY_EXISTS, message);
    }
}
