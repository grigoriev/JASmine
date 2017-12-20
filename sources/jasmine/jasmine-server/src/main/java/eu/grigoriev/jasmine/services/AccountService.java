package eu.grigoriev.jasmine.services;

import eu.grigoriev.jasmine.definitions.Role;
import eu.grigoriev.jasmine.exceptions.application.EntityAlreadyExistsException;
import eu.grigoriev.jasmine.exceptions.application.EntityNotFoundException;
import eu.grigoriev.jasmine.mappers.dto.UserMapper;
import eu.grigoriev.jasmine.model.User;
import eu.grigoriev.jasmine.persistence.*;
import eu.grigoriev.jasmine.repositories.AccountRepository;
import eu.grigoriev.jasmine.repositories.RoleRepository;
import eu.grigoriev.jasmine.repositories.ServiceRepository;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Path("/account")
public class AccountService {

    @EJB
    private ServiceRepository serviceRepository;

    @EJB
    private AccountRepository accountRepository;

    @EJB
    private RoleRepository roleRepository;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User register(
            @QueryParam("service") final String service,
            @QueryParam("username") final String username,
            @QueryParam("password") final String password
    ) throws EntityNotFoundException, EntityAlreadyExistsException {
        ServiceEntity serviceEntity = serviceRepository.get(service)
                .orElseThrow(() -> new EntityNotFoundException("service '" + service + "' not found"));

        Optional<AccountEntity> userEntity = accountRepository.get(new AccountPK(serviceEntity, username));
        if (userEntity.isPresent()) {
            throw new EntityAlreadyExistsException("user '" + username + "' for service '" + service + "' already exists");
        }

        RoleEntity roleEntity = roleRepository.get(new RolePK(serviceEntity, Role.USER))
                .orElseThrow(() -> new EntityNotFoundException("role '" + Role.USER + "' not found"));

        List<RoleEntity> roleEntities = Collections.singletonList(roleEntity);

        AccountEntity createdUserEntity = accountRepository.create(
                new AccountEntity(
                        new AccountPK(serviceEntity, username),
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
            @QueryParam("service") final String service,
            @QueryParam("username") final String username
    ) throws EntityNotFoundException {
        // get username from token and check permissions
        ServiceEntity serviceEntity = serviceRepository.get(service)
                .orElseThrow(() -> new EntityNotFoundException("service '" + service + "' not found"));

        AccountEntity userEntity = accountRepository.get(new AccountPK(serviceEntity, username))
                .orElseThrow(() -> new EntityNotFoundException("user '" + username + "' for service '" + service + "' not found"));

        return UserMapper.MAPPER.toUser(userEntity);
    }

    @PermitAll
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User update(final User user) {
        // get username from token and check permissions

        AccountEntity userEntity = UserMapper.MAPPER.toUserEntity(user);

        AccountEntity updatedUserEntity = accountRepository.update(userEntity);

        return UserMapper.MAPPER.toUser(updatedUserEntity);
    }

    @RolesAllowed({Role.ADMINISTRATOR})
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(
            @QueryParam("service") final String service,
            @QueryParam("username") final String username
    ) throws EntityNotFoundException {
        ServiceEntity serviceEntity = serviceRepository.get(service)
                .orElseThrow(() -> new EntityNotFoundException("service '" + service + "' not found"));

        // get username from token and check permissions

        AccountEntity userEntity = accountRepository.get(new AccountPK(serviceEntity, username))
                .orElseThrow(() -> new EntityNotFoundException("user '" + username + "' for service '" + service + "' not found"));

        accountRepository.delete(userEntity);
    }
}
