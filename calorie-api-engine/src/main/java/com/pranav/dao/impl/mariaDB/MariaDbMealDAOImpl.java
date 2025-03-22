package com.pranav.dao.impl.mariaDB;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pranav.dao.MealDAO;
import com.pranav.macros.Macros;
import com.pranav.mapper.MealMapper;
import com.pranav.meal.MealResponse;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Singleton
public class MariaDbMealDAOImpl implements MealDAO {

    private final Jdbi jdbi;

    @Inject
    public MariaDbMealDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public void addMeal(MealResponse meal) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO meals (meal_id, user_id, meal_name, timestamp, calorie, protein, carbs, fats, sodium) " +
                                "VALUES (:mealId, :userId, :mealName, :timestamp, :calorie, :protein, :carbs, :fats, :sodium)")
                        .bind("mealId", meal.getMealId())
                        .bind("userId", meal.getUserId())
                        .bind("mealName", meal.getMealName())
                        .bind("timestamp", meal.getTimestamp())
                        .bind("calorie", meal.getMacros().getCalorie())
                        .bind("protein", meal.getMacros().getProtein())
                        .bind("carbs", meal.getMacros().getCarbs())
                        .bind("fats", meal.getMacros().getFats())
                        .bind("sodium", meal.getMacros().getSodium())
                        .execute()
        );
        log.info("Added meal: {}", meal.getMealId());
    }

    @Override
    public List<MealResponse> getMeals() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM meals")
                        .map(new MealMapper())
                        .list()
        );
    }

    @Override
    public List<MealResponse> getMealsBetween(LocalDateTime start, LocalDateTime end) {
        List<MealResponse> meals = getMeals();
        List<MealResponse> filteredMeals = new ArrayList<>();

        for (MealResponse meal : meals) {
            LocalDateTime mealDate = meal.getTimestamp();

            if ((mealDate.isAfter(start) || mealDate.isEqual(start)) &&
                    (mealDate.isBefore(end) || mealDate.isEqual(end))) {
                filteredMeals.add(meal);
            }
        }

        log.info("Retrieved {} meals between {} and {}", filteredMeals.size(), start, end);
        return filteredMeals;
    }

    @Override
    public Macros getMacrosBetween(LocalDateTime start, LocalDateTime end) {
        Macros totalMacros = new Macros(0, 0, 0, 0, 0);

        List<MealResponse> meals = getMealsBetween(start, end);

        for (MealResponse meal : meals) {
            Macros m = meal.getMacros();
            totalMacros.setCalorie(totalMacros.getCalorie() + m.getCalorie());
            totalMacros.setProtein(totalMacros.getProtein() + m.getProtein());
            totalMacros.setCarbs(totalMacros.getCarbs() + m.getCarbs());
            totalMacros.setFats(totalMacros.getFats() + m.getFats());
            totalMacros.setSodium(totalMacros.getSodium() + m.getSodium());
        }

        return totalMacros;
    }
}
