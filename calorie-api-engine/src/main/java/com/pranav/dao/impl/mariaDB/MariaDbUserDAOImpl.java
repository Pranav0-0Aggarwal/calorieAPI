package com.pranav.dao.impl.mariaDB;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.pranav.dao.UserDAO;
import com.pranav.user.User;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

@Slf4j
@Singleton
public class MariaDbUserDAOImpl implements UserDAO {

    private final Provider<Jdbi> jdbiProvider;

    @Inject
    public MariaDbUserDAOImpl(Provider<Jdbi> jdbiProvider) {
        this.jdbiProvider = jdbiProvider;
    }


    @Override
    public boolean isValidUser(String userId) {
        return jdbiProvider.get().withHandle(handle ->
                handle.createQuery("SELECT user_id FROM users WHERE user_id = :userId")
                        .bind("userId", userId)
                        .mapTo(String.class)
                        .findOne()
                        .isPresent()
        );
    }


    @Override
    public String getApiKeyByUserId(String userId) {
        return jdbiProvider.get().withHandle(handle ->
                handle.createQuery("SELECT api_key FROM users WHERE user_id = :userId")
                        .bind("userId", userId)
                        .mapTo(String.class)
                        .findOne()
                        .orElse(null)
        );
    }

    @Override
    public void addUser(User user) {
        jdbiProvider.get().useHandle(handle ->
                handle.createUpdate("INSERT INTO users (user_id, name, api_key) VALUES (:userId, :name, :apiKey)")
                        .bind("userId", user.getUserId())
                        .bind("name", user.getName())
                        .bind("apiKey", user.getApiKey().getKey())
                        .execute()
        );
        log.info("Added user with ID {}", user.getUserId());
    }
}
