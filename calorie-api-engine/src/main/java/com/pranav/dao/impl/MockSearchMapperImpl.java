package com.pranav.dao.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pranav.dao.SearchMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Singleton
public class MockSearchMapperImpl implements SearchMapper {
    private final Map<String, String> map;

    @Inject
    public MockSearchMapperImpl() {
        this.map = new HashMap<>();
    }

    @Override
    public boolean isPresent(String foodName) {
        boolean exists = map.containsKey(foodName);
        if (!exists) {
            log.warn("Food '{}' is not found in the search mapping.", foodName);
        }
        return exists;
    }

    @Override
    public void addMapping(String foodName, String foodId) {
        map.put(foodName, foodId);
        log.info("Added mapping: {} -> {}", foodName, foodId);
    }

    @Override
    public String getMapping(String foodName) {
        String mapping= map.getOrDefault(foodName, null);
        if(mapping == null) {
            log.warn("Food '{}' is not found in the search mapping.", foodName);
        }
        return mapping;
    }

    @Override
    public Map<String, String> getAllMappings(){
        return map;
    }
}
