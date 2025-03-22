package com.pranav.mapper;

import com.pranav.macros.Macros;
import com.pranav.meal.MealResponse;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MealMapper implements RowMapper<MealResponse> {
    @Override
    public MealResponse map(ResultSet rs, StatementContext ctx) throws SQLException {
        return MealResponse.builder()
                .mealId(rs.getString("meal_id"))
                .userId(rs.getString("user_id"))
                .mealName(rs.getString("meal_name"))
                .timestamp(rs.getTimestamp("timestamp").toLocalDateTime())
                .macros(new Macros(
                        rs.getDouble("calorie"),
                        rs.getDouble("protein"),
                        rs.getDouble("carbs"),
                        rs.getDouble("fats"),
                        rs.getDouble("sodium")
                ))
                .build();
    }
}
