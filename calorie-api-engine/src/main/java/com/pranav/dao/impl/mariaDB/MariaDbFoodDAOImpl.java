package com.pranav.dao.impl.mariaDB;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.pranav.dao.FoodDAO;
import com.pranav.dao.SearchMapper;
import com.pranav.food.Food;
import com.pranav.mapper.FoodMapper;
import com.pranav.services.LlmService;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

@Slf4j
@Singleton
public class MariaDbFoodDAOImpl implements FoodDAO {

    private final Provider<Jdbi> jdbiProvider;
    private final SearchMapper searchMapper;
    private final LlmService llmService;

    @Inject
    public MariaDbFoodDAOImpl(Provider<Jdbi> jdbiProvider, SearchMapper searchMapper, LlmService llmService) {
        this.jdbiProvider = jdbiProvider;
        this.searchMapper = searchMapper;
        this.llmService = llmService;
    }

    @Override
    public void addFood(Food food) {
        jdbiProvider.get().useHandle(handle ->
                handle.createUpdate("INSERT INTO foods (food_id, name, calories, protein, carbs, fats) " +
                                "VALUES (:foodId, :name, :calories, :protein, :carbs, :fats)")
                        .bind("foodId", food.getFoodId())
                        .bind("name", food.getName())
                        .bind("calories", food.getCaloriesPer100g())
                        .bind("protein", food.getProteinPer100g())
                        .bind("carbs", food.getCarbsPer100g())
                        .bind("fats", food.getFatsPer100g())
                        .execute()
        );
        searchMapper.addMapping(food.getName(), food.getFoodId());
        log.info("Food '{}' added to MariaDB with ID {}", food.getName(), food.getFoodId());
    }

    @Override
    public boolean foodExists(String foodName) {
        boolean exists = searchMapper.isPresent(foodName);
        if (!exists) {
            log.warn("Food '{}' does not exist in the MariaDB database (according to searchMapper).", foodName);
        }
        return exists;
    }

    @Override
    public Food getFoodByName(String foodName) {
        if (searchMapper.isPresent(foodName)) {
            String foodId = searchMapper.getMapping(foodName);
            return getFoodById(foodId);
        }

        List<Food> allFoods = getAllFoods();
        Food closest = llmService.getClosestFood(foodName, allFoods);

        if (closest != null) {
            searchMapper.addMapping(foodName, closest.getFoodId());
            return closest;
        }

        log.warn("Could not find or infer food for '{}'", foodName);
        return null;
    }

    @Override
    public Food getFoodById(String foodId) {
        Optional<Food> result = jdbiProvider.get().withHandle(handle ->
                handle.createQuery("SELECT * FROM foods WHERE food_id = :foodId")
                        .bind("foodId", foodId)
                        .map(new FoodMapper())
                        .findOne()
        );

        if (result.isEmpty()) {
            log.warn("No food found in MariaDB with ID {}", foodId);
        }

        return result.orElse(null);
    }

    @Override
    public List<Food> getAllFoods() {
        return jdbiProvider.get().withHandle(handle ->
                handle.createQuery("SELECT * FROM foods")
                        .map(new FoodMapper())
                        .list()
        );
    }
}
