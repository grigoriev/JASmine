package eu.grigoriev.jasmine.repositories.common;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class JPARepository<Entity, PrimaryKey extends Serializable> implements Repository<Entity, PrimaryKey> {

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
    public Entity create(@NotNull final Entity entity) {
        this.em.persist(entity);
        this.em.flush();

        return entity;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Optional<Entity> get(@NotNull final PrimaryKey primaryKey) {
        Entity entity = this.em.find(this.entity, primaryKey);

        return Optional.ofNullable(entity);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Entity> getAll() {
        return find("SELECT e FROM " + this.entity.getName() + " e");
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Entity> find(@NotNull final String jpqlQuery) {
        return find(jpqlQuery, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Entity> find(@NotNull final String jpqlQuery, @Nullable final Map<String, String> jpqlQueryParameters) {
        Query query = createQueryWithParams(jpqlQuery, jpqlQueryParameters);

        return (List<Entity>) query.getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Optional<Entity> findUnique(@NotNull final String jpqlQuery) {
        return findUnique(jpqlQuery, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Optional<Entity> findUnique(@NotNull final String jpqlQuery, @Nullable final Map<String, String> jpqlQueryParameters) {
        Query query = createQueryWithParams(jpqlQuery, jpqlQueryParameters);

        try {
            return Optional.of((Entity) query.getSingleResult());
        } catch (NoResultException | NonUniqueResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Entity update(@NotNull final Entity entity) {
        final Entity updatedEntity = this.em.merge(entity);
        this.em.persist(updatedEntity);
        this.em.flush();

        return updatedEntity;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(@NotNull final Entity entity) {
        final Entity updatedEntity = this.em.merge(entity);
        this.em.remove(updatedEntity);
        this.em.flush();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteAll() {
        this.em.createQuery("DELETE FROM " + this.entity.getName()).executeUpdate();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public long count() {
        return (long) this.em.createQuery("SELECT count(e) FROM " + this.entity.getName() + " e").getSingleResult();
    }

    private Query createQueryWithParams(@NotNull String jpqlQuery, @Nullable Map<String, String> jpqlQueryParameters) {
        Query query = this.em.createQuery(jpqlQuery);

        if (jpqlQueryParameters != null) {
            for (Map.Entry<String, String> entry : jpqlQueryParameters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                query.setParameter(key, value);
            }
        }

        return query;
    }
}
