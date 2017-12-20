package eu.grigoriev.jasmine.repositories;


import eu.grigoriev.jasmine.persistence.ServiceEntity;
import eu.grigoriev.jasmine.repositories.common.JPARepository;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class ServiceRepository extends JPARepository<ServiceEntity, String> {
}
