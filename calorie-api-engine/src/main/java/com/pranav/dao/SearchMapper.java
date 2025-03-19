package com.pranav.dao;

import java.util.Map;

public interface SearchMapper {
    boolean isPresent(String foodName);
    void addMapping(String foodName, String foodId);
    String getMapping(String foodName);
    Map<String, String> getAllMappings();
}
