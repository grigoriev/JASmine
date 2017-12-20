package eu.grigoriev.jasmine.exceptions.application;

import eu.grigoriev.jasmine.definitions.ErrorCode;

import javax.ws.rs.core.Response;

public class InvalidCredentialsException extends JasmineException {

    public InvalidCredentialsException(String message) {
        super(Response.Status.UNAUTHORIZED.getStatusCode(), ErrorCode.UNAUTHORIZED, message);
    }
}
