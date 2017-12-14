package eu.grigoriev.jasmine.services;

import eu.grigoriev.jasmine.model.Roles;
import eu.grigoriev.jasmine.model.account.User;
import eu.grigoriev.jasmine.model.status.State;
import eu.grigoriev.jasmine.model.status.Status;
import eu.grigoriev.jasmine.persistence.UserEntity;
import eu.grigoriev.jasmine.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Slf4j
@Path("/account")
public class Account {

    @EJB
    private UserRepository userRepository;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public State register(
            @QueryParam("") String email, String password) {
        log.info("register");
        return new State(Status.OK);
    }

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User info() {
        List<UserEntity> allUserEntities = userRepository.findAll();

        return allUserEntities.get(0).toUser();
    }

    @PermitAll
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String update() {
        log.info("update");
        return "";
    }

    @RolesAllowed({Roles.ADMINISTRATOR})
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String delete() {
        log.info("delete");
        return "";
    }
}
