package eu.grigoriev.jasmine.repositories;


import eu.grigoriev.jasmine.persistence.AccountEntity;
import eu.grigoriev.jasmine.persistence.AccountPK;
import eu.grigoriev.jasmine.repositories.common.JPARepository;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class AccountRepository extends JPARepository<AccountEntity, AccountPK> {
}
