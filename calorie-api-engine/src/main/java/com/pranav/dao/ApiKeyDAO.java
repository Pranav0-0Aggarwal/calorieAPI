package com.pranav.dao;

import com.pranav.api.ApiKey;

import java.time.LocalDateTime;

public interface ApiKeyDAO {

    boolean isValidApiKey(String apiKey, LocalDateTime localTime);

    void addApiKey(ApiKey apiKey);

    void deleteApiKey(String apiKey);

    String getUserIdByApiKey(String apiKey);
}