package eu.grigoriev.jasmine.services;

import eu.grigoriev.jasmine.model.status.State;
import eu.grigoriev.jasmine.model.status.Status;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Path("/session")
public class Session {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String create(
            @QueryParam("username") String username,
            @QueryParam("password") String encodedPassword

    ) {
        String password = new String(Base64.getDecoder().decode(encodedPassword), StandardCharsets.UTF_8);

        // find user in the database

        String id = UUID.randomUUID().toString();

        // save token ID to the db

        String token = "";

        return token;
    }

    @PermitAll
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public State close() {

        // delete token ID from the db

        return new State(Status.OK);
    }
}
