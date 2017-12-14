package eu.grigoriev.jasmine.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface Repository<Entity, PrimaryKey extends Serializable> {

    Entity create(Entity newInstance);

    Entity find(PrimaryKey id);

    List<Entity> findAll();

    Entity update(Entity transientObject);

    void delete(Entity persistentObject);

    void deleteAll();

    long count();

    List<Entity> findWithJpqlQuery(String jpqlQuery, Map<String, String> parameters);
}