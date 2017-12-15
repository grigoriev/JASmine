package eu.grigoriev.jasmine.mappers.dto;

import eu.grigoriev.jasmine.model.Role;
import eu.grigoriev.jasmine.persistence.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);

    Role toRole(RoleEntity roleEntity);

    RoleEntity toRoleEntity(Role role);
}
