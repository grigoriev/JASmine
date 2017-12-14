package eu.grigoriev.jasmine.repositories;


import eu.grigoriev.jasmine.persistence.UserEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class UserRepository extends JPARepository<UserEntity, String> {
}
