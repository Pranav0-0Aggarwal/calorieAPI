package com.pranav.dao.impl;

import com.pranav.dao.UserDAO;
import com.pranav.user.User;

import java.util.HashMap;
import java.util.Map;

public class MockUserDAOImpl implements UserDAO {
    Map<String, User> mockUsersStore;

    public MockUserDAOImpl() {
        mockUsersStore = new HashMap<>();
    }

    @Override
    public String getApiKeyByUserId(String userId) {
        return mockUsersStore.get(userId).getApiKey().getKey();
    }

    @Override
    public void addUser(User user) {
        mockUsersStore.put(user.getUserId(), user);
    }
}
