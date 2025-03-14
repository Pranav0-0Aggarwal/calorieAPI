package com.pranav.dao;

public interface SearchMapper {
    boolean isPresent(String foodName);
    void addMapping(String foodName, String foodId);
    String getMapping(String foodName);
}
