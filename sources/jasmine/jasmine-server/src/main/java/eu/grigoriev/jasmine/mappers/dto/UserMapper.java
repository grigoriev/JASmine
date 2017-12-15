package eu.grigoriev.jasmine.mappers.dto;

import eu.grigoriev.jasmine.model.User;
import eu.grigoriev.jasmine.persistence.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserEntity userEntity);

    UserEntity toUserEntity(User user);
}
