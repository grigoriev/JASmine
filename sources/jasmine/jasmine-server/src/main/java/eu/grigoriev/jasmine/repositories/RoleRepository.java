package eu.grigoriev.jasmine.repositories;


import eu.grigoriev.jasmine.persistence.RoleEntity;
import eu.grigoriev.jasmine.persistence.UserEntity;
import eu.grigoriev.jasmine.repositories.common.JPARepository;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class RoleRepository extends JPARepository<RoleEntity, String> {
}
