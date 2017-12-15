package eu.grigoriev.jasmine.services;

import eu.grigoriev.jasmine.exceptions.EntityNotFoundException;
import eu.grigoriev.jasmine.model.Token;
import eu.grigoriev.jasmine.persistence.UserEntity;
import eu.grigoriev.jasmine.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Path("/session")
public class SessionService {

    @EJB
    UserRepository userRepository;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Token create(
            @QueryParam("application") final String application,
            @QueryParam("username") final String username,
            @QueryParam("password") final String encodedPassword
    ) throws EntityNotFoundException {
        UserEntity userEntity = userRepository.findByApplicationAndUsername(application, username);

        if (userEntity == null) {
            throw new EntityNotFoundException("user '" + username + "' for application '" + application + "' not found");
        }

        String password = new String(Base64.getDecoder().decode(encodedPassword), StandardCharsets.UTF_8);

        if (!password.equals(userEntity.getPassword())) {
        }

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
