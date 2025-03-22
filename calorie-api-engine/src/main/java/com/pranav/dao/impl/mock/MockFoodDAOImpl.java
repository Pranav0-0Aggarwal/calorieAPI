package com.pranav.dao.impl.mock;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pranav.dao.FoodDAO;
import com.pranav.dao.SearchMapper;
import com.pranav.food.Food;
import com.pranav.services.LlmService;
import com.pranav.utils.ClosestFinder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Singleton
public class MockFoodDAOImpl implements FoodDAO {

    private final Map<String, Food> mockFoodStore;
    private final SearchMapper searchMapper;
    private final ClosestFinder closestFinder;
    @Inject
    public MockFoodDAOImpl(SearchMapper searchMapper,ClosestFinder closestFinder) {
        this.searchMapper = searchMapper;
        this.closestFinder = closestFinder;
        this.mockFoodStore = new LinkedHashMap<>();}

    @Override
    public Food getFoodByName(String foodName) {
        if (searchMapper.isPresent(foodName)) {
            return mockFoodStore.get(searchMapper.getMapping(foodName));
        }
        Food food = closestFinder.getClosestFood(foodName, mockFoodStore.values().stream().toList());
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

}
