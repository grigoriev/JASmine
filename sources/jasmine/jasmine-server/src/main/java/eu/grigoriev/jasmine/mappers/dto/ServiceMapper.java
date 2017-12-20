package eu.grigoriev.jasmine.mappers.dto;

import eu.grigoriev.jasmine.model.Service;
import eu.grigoriev.jasmine.persistence.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ServiceMapper {
    ServiceMapper MAPPER = Mappers.getMapper(ServiceMapper.class);

    Service toService(ServiceEntity serviceEntity);

    ServiceEntity toServiceEntity(Service app);

    List<Service> toServices(List<ServiceEntity> serviceEntities);

    List<ServiceEntity> toServiceEntities(List<Service> services);
}
