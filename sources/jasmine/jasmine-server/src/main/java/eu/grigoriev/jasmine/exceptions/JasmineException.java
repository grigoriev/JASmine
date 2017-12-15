package eu.grigoriev.jasmine.exceptions;

import eu.grigoriev.jasmine.definitions.ErrorCode;
import lombok.Data;
import lombok.Getter;

import javax.ws.rs.core.Response;

public class JasmineException extends Exception {
    @Getter
    int status = Response.Status.BAD_REQUEST.getStatusCode();
    @Getter
    int errorCode;

    public JasmineException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode.getValue();
    }

    public JasmineException(int status, ErrorCode errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode.getValue();
    }

    public ErrorMessage getEntity() {
        return new ErrorMessage(this);
    }

    @Data
    public class ErrorMessage {
        private int status;
        private int code;
        private String message;

        ErrorMessage(JasmineException e) {
            status = e.getStatus();
            code = e.getErrorCode();
            message = e.getMessage();
        }
    }
}
