package eu.grigoriev.jasmine.repositories;


import eu.grigoriev.jasmine.persistence.TokenEntity;
import eu.grigoriev.jasmine.repositories.common.JPARepository;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class TokenRepository extends JPARepository<TokenEntity, String> {
}
