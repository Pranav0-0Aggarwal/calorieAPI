package com.pranav.mapper;

import com.pranav.food.Food;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodMapper implements RowMapper<Food> {
    @Override
    public Food map(ResultSet rs, StatementContext ctx) throws SQLException {
        return Food.builder()
                .foodId(rs.getString("food_id"))
                .name(rs.getString("name"))
                .caloriesPer100g(rs.getDouble("calories_per100g"))
                .carbsPer100g(rs.getDouble("carbohydrates_per100g"))
                .fatsPer100g(rs.getDouble("fats_per100g"))
                .proteinPer100g(rs.getDouble("protein_per100g"))
                .sodiumPer100g(rs.getDouble("sodium_per100g"))
                .build();
    }
}
