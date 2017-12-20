package eu.grigoriev.jasmine.repositories;


import eu.grigoriev.jasmine.persistence.UserEntity;
import eu.grigoriev.jasmine.persistence.UserPK;
import eu.grigoriev.jasmine.repositories.common.JPARepository;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Stateless
@Local
public class UserRepository extends JPARepository<UserEntity, UserPK> {
}
