package eu.grigoriev.jasmine.mappers.dto;

import eu.grigoriev.jasmine.model.Application;
import eu.grigoriev.jasmine.persistence.ApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ApplicationMapper {
    ApplicationMapper MAPPER = Mappers.getMapper(ApplicationMapper.class);

    Application toApp(ApplicationEntity applicationEntity);

    ApplicationEntity toApplicationEntity(Application app);

    List<Application> toApps(List<ApplicationEntity> applicationEntities);

    List<ApplicationEntity> toApplicationEntities(List<Application> apps);
}
