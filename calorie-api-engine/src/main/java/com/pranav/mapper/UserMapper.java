package com.pranav.mapper;

import com.pranav.api.ApiKey;
import com.pranav.user.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User map(ResultSet rs, StatementContext ctx) throws SQLException {
        return User.builder()
                .userId(rs.getString("user_id"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .apiKey(new ApiKey(rs.getString("api_key"),
                        rs.getString("user_id")))
                .build();
    }

}
