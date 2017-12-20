package eu.grigoriev.jasmine.repositories;


import eu.grigoriev.jasmine.persistence.RoleEntity;
import eu.grigoriev.jasmine.persistence.RolePK;
import eu.grigoriev.jasmine.persistence.UserEntity;
import eu.grigoriev.jasmine.repositories.common.JPARepository;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Stateless
@Local
public class RoleRepository extends JPARepository<RoleEntity, RolePK> {
}
