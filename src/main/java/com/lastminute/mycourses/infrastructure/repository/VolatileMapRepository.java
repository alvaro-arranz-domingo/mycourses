package com.lastminute.mycourses.infrastructure.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by administrator on 7/12/15.
 */
public class VolatileMapRepository<T> {

    protected Map<Long, T> map = new HashMap<>();

    public Optional<T> findById(Long id) {
        T entity = map.get(id);

        if (entity != null) {
            return Optional.of(entity);
        }

        return Optional.empty();
    }

    public Collection<T> findAll() {
        return map.values();
    }

    public void save(Long id, T entity) {
        map.put(id, entity);
    }

    public void remove(Long id) {
        map.remove(id);
    }

    public void clear() {
        map.clear();
    }
}
