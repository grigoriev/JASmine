package eu.grigoriev.jasmine.repositories.common;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Repository<Entity, PrimaryKey extends Serializable> {

    Entity create(@NotNull Entity newInstance);

    Optional<Entity> get(@NotNull PrimaryKey id);

    List<Entity> getAll();

    List<Entity> find(@NotNull String jpqlQuery);

    List<Entity> find(@NotNull String jpqlQuery, @Nullable Map<String, String> jpqlQueryParameters);

    Optional<Entity> findUnique(@NotNull String jpqlQuery);

    Optional<Entity> findUnique(@NotNull String jpqlQuery, @Nullable Map<String, String> jpqlQueryParameters);

    Entity update(@NotNull Entity transientObject);

    void delete(@NotNull Entity persistentObject);

    void deleteAll();

    long count();
}