package eu.grigoriev.jasmine.repositories;

import org.apache.log4j.Logger;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public abstract class JPARepository<Entity, PrimaryKey extends Serializable> implements Repository<Entity, PrimaryKey> {
    protected final Logger logger = Logger.getLogger(this.getClass());

    @PersistenceContext(unitName = "JASminePersistenceUnit")
    protected EntityManager em;

    protected Class<Entity> entity;

    @SuppressWarnings("all")
    public JPARepository() {
        String abstractRepositorySimpleClassName = JPARepository.class.getSimpleName();
        Class<?> currentClass = getClass();
        while (!abstractRepositorySimpleClassName.equals(currentClass.getSuperclass().getSimpleName())) {
            currentClass = currentClass.getSuperclass();
        }
        Type type = currentClass.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            this.entity = (Class<Entity>) actualTypeArguments[0];
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Entity create(final Entity entity) {
        this.em.persist(entity);
        this.em.flush();
        return entity;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Entity find(final PrimaryKey id) {
        if (id == null) {
            return null;
        } else {
            return this.em.find(this.entity, id);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Entity> findAll() {
        return (List<Entity>) this.em.createQuery("select e from " + this.entity.getName() + " e").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Entity> findWithJpqlQuery(String jpqlQuery, Map<String, String> parameters) {
        Query query = em.createQuery(jpqlQuery);
        for (String key : parameters.keySet()) {
            String value = parameters.get(key);
            query = query.setParameter(key, value);
        }
        return query.getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Entity update(final Entity entity) {
        final Entity updatedEntity = this.em.merge(entity);
        this.em.persist(updatedEntity);
        this.em.flush();
        return updatedEntity;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(final Entity entity) {
        final Entity updatedEntity = this.em.merge(entity);
        this.em.remove(updatedEntity);
        this.em.flush();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteAll() {
        int deletedRows = this.em.createQuery("delete from " + this.entity.getName()).executeUpdate();
        logger.debug(deletedRows + " row(s) successfully deleted");
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public long count() {
        return (long) this.em.createQuery("select count(e) from " + this.entity.getName() + " e").getSingleResult();
    }
}
