package eu.grigoriev.jasmine.services;

import eu.grigoriev.jasmine.model.Token;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Path("/session")
public class SessionService {

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Token create(
            @QueryParam("username") final String username,
            @QueryParam("password") final String encodedPassword
    ) {
        String password = new String(Base64.getDecoder().decode(encodedPassword), StandardCharsets.UTF_8);

        // find user in the database

        String id = UUID.randomUUID().toString();

        // save token ID to the db

        return new Token();
    }

    @PermitAll
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void close(final Token token) {

    }
}
