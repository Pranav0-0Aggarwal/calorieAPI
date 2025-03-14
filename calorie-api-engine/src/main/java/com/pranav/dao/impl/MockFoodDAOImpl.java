package com.pranav.dao.impl;

import com.pranav.dao.FoodDAO;
import com.pranav.dao.SearchMapper;
import com.pranav.food.Food;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MockFoodDAOImpl implements FoodDAO {

    //mapping of Id, with Food
    private final Map<String, Food> mockFoodStore;
    private final SearchMapper searchMapper;

    public MockFoodDAOImpl(SearchMapper searchMapper) {
        this.searchMapper = searchMapper;
        this.mockFoodStore = new HashMap<>();
    }

    @Override
    public Food getFoodByName(String foodName) {
        if (searchMapper.isPresent(foodName)) {
            return mockFoodStore.get(searchMapper.getMapping(foodName));
        }
        Food food = closestFood(foodName);
        searchMapper.addMapping(foodName, food.getFoodId());
        return food;
    }


    @Override
    public Food getFoodById(String foodId) {
        if (!mockFoodStore.containsKey(foodId)) {
            log.warn("There's no food with foodId-{}", foodId);
            return null;
        }
        return mockFoodStore.get(foodId);
    }

    @Override
    public void addFood(Food food) {
        mockFoodStore.put(food.getFoodId(), food);
        searchMapper.addMapping(food.getName(), food.getFoodId());
        log.info("Food with name '{}' added to the mock database.", food.getName());
    }

    @Override
    public boolean foodExists(String foodName) {
        boolean exists = searchMapper.isPresent(foodName);
        if (!exists) {
            log.warn("Food '{}' does not exist in the mock database.", foodName);
        }
        return exists;
    }

    @Override
    public List<Food> getAllFoods() {
        return new ArrayList<>(mockFoodStore.values());
    }

    private Food closestFood(String foodName) {
        return new Food();
    }

}
