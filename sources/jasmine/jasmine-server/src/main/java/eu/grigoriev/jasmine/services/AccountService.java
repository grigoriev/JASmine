package eu.grigoriev.jasmine.services;

import eu.grigoriev.jasmine.definitions.Role;
import eu.grigoriev.jasmine.exceptions.EntityAlreadyExistsException;
import eu.grigoriev.jasmine.exceptions.EntityNotFoundException;
import eu.grigoriev.jasmine.mappers.dto.RoleMapper;
import eu.grigoriev.jasmine.mappers.dto.UserMapper;
import eu.grigoriev.jasmine.model.User;
import eu.grigoriev.jasmine.persistence.ApplicationEntity;
import eu.grigoriev.jasmine.persistence.RoleEntity;
import eu.grigoriev.jasmine.persistence.UserEntity;
import eu.grigoriev.jasmine.repositories.ApplicationRepository;
import eu.grigoriev.jasmine.repositories.RoleRepository;
import eu.grigoriev.jasmine.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Path("/account")
public class AccountService {

    @EJB
    private ApplicationRepository applicationRepository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private RoleRepository roleRepository;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User register(
            @QueryParam("application") final String application,
            @QueryParam("username") final String username,
            @QueryParam("password") final String password
    ) throws EntityNotFoundException, EntityAlreadyExistsException {
        ApplicationEntity applicationEntity = applicationRepository.find(application);
        if (applicationEntity == null) {
            throw new EntityNotFoundException("application '" + application + "' not found");
        }

        UserEntity userEntity = userRepository.findByApplicationAndUsername(application, username);
        if (userEntity != null) {
            throw new EntityAlreadyExistsException("user '" + username + "' for application '" + application + "' already exists");
        }

        RoleEntity roleEntity = roleRepository.find(Role.USER);
        if (roleEntity == null) {
            throw new EntityNotFoundException("role '" + Role.USER + "' not found");
        }

        List<RoleEntity> roleEntities = Collections.singletonList(roleEntity);

        UserEntity createdUserEntity = userRepository.create(
                new UserEntity(
                        UUID.randomUUID().toString(),
                        username,
                        password,
                        false,
                        roleEntities,
                        null
                )
        );

        return UserMapper.MAPPER.toUser(createdUserEntity);
    }

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User info(
            @QueryParam("application") final String application,
            @QueryParam("username") final String username
    ) throws EntityNotFoundException {
        // get username from token and check permissions

        UserEntity userEntity = userRepository.findByApplicationAndUsername(application, username);
        if (userEntity == null) {
            throw new EntityNotFoundException("user '" + username + "' for application '" + application + "' not found");
        }

        return UserMapper.MAPPER.toUser(userEntity);
    }

    @PermitAll
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User update(final User user) {
        // get username from token and check permissions

        UserEntity userEntity = UserMapper.MAPPER.toUserEntity(user);

        UserEntity updatedUserEntity = userRepository.update(userEntity);

        return UserMapper.MAPPER.toUser(updatedUserEntity);
    }

    @RolesAllowed({Role.ADMINISTRATOR})
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(
            @QueryParam("application") final String application,
            @QueryParam("username") final String username
    ) throws EntityNotFoundException {
        // get username from token and check permissions

        UserEntity userEntity = userRepository.findByApplicationAndUsername(application, username);
        if (userEntity == null) {
            throw new EntityNotFoundException("user '" + username + "' for application '" + application + "' not found");
        }

        userRepository.delete(userEntity);
    }
}
