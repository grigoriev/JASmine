package eu.grigoriev.jasmine.services;

import eu.grigoriev.jasmine.exceptions.application.EntityNotFoundException;
import eu.grigoriev.jasmine.exceptions.application.InvalidCredentialsException;
import eu.grigoriev.jasmine.mappers.dto.TokenMapper;
import eu.grigoriev.jasmine.model.Token;
import eu.grigoriev.jasmine.persistence.ServiceEntity;
import eu.grigoriev.jasmine.persistence.TokenEntity;
import eu.grigoriev.jasmine.persistence.UserEntity;
import eu.grigoriev.jasmine.persistence.UserPK;
import eu.grigoriev.jasmine.repositories.ServiceRepository;
import eu.grigoriev.jasmine.repositories.TokenRepository;
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
    private ServiceRepository serviceRepository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private TokenRepository tokenRepository;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Token create(
            @QueryParam("service") final String service,
            @QueryParam("username") final String username,
            @QueryParam("password") final String encodedPassword
    ) throws InvalidCredentialsException, EntityNotFoundException {
        ServiceEntity serviceEntity = serviceRepository.get(service)
                .orElseThrow(() -> new EntityNotFoundException("service '" + service + "' not found"));

        UserEntity userEntity = userRepository.get(new UserPK(serviceEntity, username))
                .orElseThrow(() -> new InvalidCredentialsException("invalid credentials provided"));

        String password = new String(Base64.getDecoder().decode(encodedPassword), StandardCharsets.UTF_8);

        if (!password.equals(userEntity.getPassword())) {
            throw new InvalidCredentialsException("invalid credentials provided");
        }

        String id = UUID.randomUUID().toString();

        String jwt = "jwt-token";

        TokenEntity tokenEntity = new TokenEntity(id, jwt, userEntity);

        TokenEntity createdTokenEntity = tokenRepository.create(tokenEntity);

        return TokenMapper.MAPPER.toToken(createdTokenEntity);
    }

    @PermitAll
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void close(final Token token) throws EntityNotFoundException {
        TokenEntity tokenEntity = tokenRepository.get(token.getId())
                .orElseThrow(() -> new EntityNotFoundException(""));

        tokenRepository.delete(tokenEntity);
    }
}
