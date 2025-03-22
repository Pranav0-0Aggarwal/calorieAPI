package com.pranav.dao.impl.mariaDB;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pranav.dao.SearchMapper;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Singleton
public class MariaDbSearchMapperImpl implements SearchMapper {

    private final Jdbi jdbi;

    @Inject
    public MariaDbSearchMapperImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public boolean isPresent(String foodName) {
        Boolean exists = jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM search_mappings WHERE LOWER(food_name) = LOWER(:foodName)")
                        .bind("foodName", foodName)
                        .mapTo(int.class)
                        .one() > 0
        );

        if (!exists) {
            log.warn("Food '{}' is not found in the search mapping.", foodName);
        }

        return exists;
    }

    @Override
    public void addMapping(String foodName, String foodId) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO search_mappings (food_name, food_id) VALUES (:foodName, :foodId) " +
                                "ON DUPLICATE KEY UPDATE food_id = :foodId")
                        .bind("foodName", foodName)
                        .bind("foodId", foodId)
                        .execute()
        );
        log.info("Added/Updated mapping: {} -> {}", foodName, foodId);
    }

    @Override
    public String getMapping(String foodName) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT food_id FROM search_mappings WHERE LOWER(food_name) = LOWER(:foodName)")
                        .bind("foodName", foodName)
                        .mapTo(String.class)
                        .findOne()
                        .orElseGet(() -> {
                            log.warn("Food '{}' is not found in the search mapping.", foodName);
                            return null;
                        })
        );
    }

    @Override
    public Map<String, String> getAllMappings() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT food_name, food_id FROM search_mappings")
                        .reduceResultSet(new HashMap<>(), (map, rs, ctx) -> {
                            map.put(rs.getString("food_name"), rs.getString("food_id"));
                            return map;
                        })
        );
    }
}
