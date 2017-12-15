package eu.grigoriev.jasmine.mappers.exceptions;

import eu.grigoriev.jasmine.exceptions.JasmineException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JasmineExceptionMapper implements ExceptionMapper<JasmineException> {

    @Override
    public Response toResponse(JasmineException e) {
        return Response
                .status(e.getStatus())
                .entity(e.getEntity())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}