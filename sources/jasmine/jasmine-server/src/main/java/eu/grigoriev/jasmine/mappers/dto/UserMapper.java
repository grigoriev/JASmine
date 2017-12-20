package eu.grigoriev.jasmine.mappers.dto;

import eu.grigoriev.jasmine.model.User;
import eu.grigoriev.jasmine.persistence.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(AccountEntity userEntity);

    AccountEntity toUserEntity(User user);
}
