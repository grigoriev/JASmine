package eu.grigoriev.jasmine.mappers.dto;

import eu.grigoriev.jasmine.model.Token;
import eu.grigoriev.jasmine.persistence.TokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TokenMapper {
    TokenMapper MAPPER = Mappers.getMapper(TokenMapper.class);

    Token toToken(TokenEntity tokenEntity);

    Token toTokenEntity(Token token);
}
