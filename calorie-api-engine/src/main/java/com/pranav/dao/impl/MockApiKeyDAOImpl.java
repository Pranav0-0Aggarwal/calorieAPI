package com.pranav.dao.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pranav.api.ApiKey;
import com.pranav.dao.ApiKeyDAO;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Singleton
public class MockApiKeyDAOImpl implements ApiKeyDAO {
    private final Map<String, ApiKey> apiKeys;

    @Inject
    public MockApiKeyDAOImpl() {
        this.apiKeys = new HashMap<>();
    }

    @Override
    public boolean isValidApiKey(String apiKeyId, LocalDateTime localTime) {
        if (!apiKeys.containsKey(apiKeyId)) {
            return false;
        }
        ApiKey apiKey = apiKeys.get(apiKeyId);

        if (apiKey.getRequestCount() < 0) {
            return false;
        }

        return !apiKey.getExpiresAt().isBefore(localTime);
    }


    @Override
    public void addApiKey(ApiKey apiKey) {
        apiKeys.put(apiKey.getKey(), apiKey);
        log.info("ApiKey has been added");
    }

    @Override
    public void deleteApiKey(String apiKey) {
        apiKeys.remove(apiKey);
        log.info("ApiKey has been deleted");
    }

    @Override
    public String getUserIdByApiKey(String apiKeyId) {
        ApiKey apiKey = apiKeys.get(apiKeyId);
        return apiKey.getUserId();
    }
}
