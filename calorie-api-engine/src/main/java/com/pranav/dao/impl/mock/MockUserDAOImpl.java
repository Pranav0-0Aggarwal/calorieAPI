package com.pranav.dao.impl.mock;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pranav.dao.UserDAO;
import com.pranav.user.User;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class MockUserDAOImpl implements UserDAO {
    Map<String, User> mockUsersStore;

    @Inject
    public MockUserDAOImpl() {
        mockUsersStore = new HashMap<>();
    }

    @Override
    public boolean isValidUser(String userId) {
        return mockUsersStore.containsKey(userId);
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
