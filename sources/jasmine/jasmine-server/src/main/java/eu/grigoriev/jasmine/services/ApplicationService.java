package eu.grigoriev.jasmine.services;

import eu.grigoriev.jasmine.exceptions.application.EntityAlreadyExistsException;
import eu.grigoriev.jasmine.exceptions.application.EntityNotFoundException;
import eu.grigoriev.jasmine.mappers.dto.ServiceMapper;
import eu.grigoriev.jasmine.definitions.Role;
import eu.grigoriev.jasmine.model.Service;
import eu.grigoriev.jasmine.persistence.ServiceEntity;
import eu.grigoriev.jasmine.repositories.ServiceRepository;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Slf4j
@Path("/service")
public class ApplicationService {

    @EJB
    private ServiceRepository serviceRepository;

    @RolesAllowed({Role.ADMINISTRATOR})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Service addService(final Service service) throws EntityAlreadyExistsException {
        Optional<ServiceEntity> optionalApplicationEntity = serviceRepository.findUnique(service.getName());

        if (optionalApplicationEntity.isPresent()) {
            throw new EntityAlreadyExistsException("service '" + service.getName() + "' already exists");
        }

        ServiceEntity createdServiceEntity = serviceRepository.create(ServiceMapper.MAPPER.toServiceEntity(service));
        return ServiceMapper.MAPPER.toService(createdServiceEntity);
    }

    @RolesAllowed({Role.ADMINISTRATOR})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Service> allApplications() {
        List<ServiceEntity> serviceEntities = serviceRepository.getAll();

        return ServiceMapper.MAPPER.toServices(serviceEntities);
    }

    @PermitAll
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Service update(final Service service) throws EntityNotFoundException {
        ServiceEntity serviceEntity = serviceRepository.findUnique(service.getName())
                .orElseThrow(() -> new EntityNotFoundException("service '" + service.getName() + "' not found"));

        ServiceEntity updatedServiceEntity = serviceRepository.update(ServiceMapper.MAPPER.toServiceEntity(service));
        return ServiceMapper.MAPPER.toService(updatedServiceEntity);
    }

    @RolesAllowed({Role.ADMINISTRATOR})
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(final Service service) throws EntityNotFoundException {
        ServiceEntity serviceEntity = serviceRepository.findUnique(service.getName())
                .orElseThrow(() -> new EntityNotFoundException("service '" + service.getName() + "' not found"));

        serviceRepository.delete(serviceEntity);
    }
}
