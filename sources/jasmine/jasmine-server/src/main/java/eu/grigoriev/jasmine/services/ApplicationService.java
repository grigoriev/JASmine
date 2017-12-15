package eu.grigoriev.jasmine.services;

import eu.grigoriev.jasmine.exceptions.EntityAlreadyExistsException;
import eu.grigoriev.jasmine.exceptions.EntityNotFoundException;
import eu.grigoriev.jasmine.mappers.dto.ApplicationMapper;
import eu.grigoriev.jasmine.definitions.Role;
import eu.grigoriev.jasmine.model.Application;
import eu.grigoriev.jasmine.persistence.ApplicationEntity;
import eu.grigoriev.jasmine.repositories.ApplicationRepository;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Slf4j
@Path("/application")
public class ApplicationService {

    @EJB
    private ApplicationRepository applicationRepository;

    @RolesAllowed({Role.ADMINISTRATOR})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Application addApplication(final Application application) throws EntityAlreadyExistsException {
        ApplicationEntity applicationEntity = applicationRepository.find(application.getName());
        if (applicationEntity == null) {
            ApplicationEntity createdApplicationEntity = applicationRepository.create(ApplicationMapper.MAPPER.toApplicationEntity(application));
            return ApplicationMapper.MAPPER.toApp(createdApplicationEntity);
        } else {
            throw new EntityAlreadyExistsException("application '" + application.getName() + "' already exists");
        }
    }

    @RolesAllowed({Role.ADMINISTRATOR})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Application> allApplications() {
        List<ApplicationEntity> applicationEntities = applicationRepository.findAll();

        return ApplicationMapper.MAPPER.toApps(applicationEntities);
    }

    @PermitAll
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Application update(final Application application) throws EntityNotFoundException {
        ApplicationEntity applicationEntity = applicationRepository.find(application.getName());
        if (applicationEntity != null) {
            ApplicationEntity updatedApplicationEntity = applicationRepository.update(ApplicationMapper.MAPPER.toApplicationEntity(application));
            return ApplicationMapper.MAPPER.toApp(updatedApplicationEntity);
        } else {
            throw new EntityNotFoundException("application '" + application.getName() + "' not found");
        }
    }

    @RolesAllowed({Role.ADMINISTRATOR})
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(final Application application) throws EntityNotFoundException {
        ApplicationEntity applicationEntity = applicationRepository.find(application.getName());
        if (applicationEntity != null) {
            applicationRepository.delete(applicationEntity);
        } else {
            throw new EntityNotFoundException("application '" + application.getName() + "' not found");
        }
    }
}
