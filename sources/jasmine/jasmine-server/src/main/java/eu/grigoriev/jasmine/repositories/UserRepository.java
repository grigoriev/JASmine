package eu.grigoriev.jasmine.repositories;


import eu.grigoriev.jasmine.persistence.UserEntity;
import eu.grigoriev.jasmine.repositories.common.JPARepository;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class UserRepository extends JPARepository<UserEntity, String> {

    public UserEntity findByApplicationAndUsername(String application, String username) {

        return
                null;
    }
}