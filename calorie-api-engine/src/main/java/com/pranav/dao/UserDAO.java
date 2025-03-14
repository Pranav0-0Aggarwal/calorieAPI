package com.pranav.dao;


import com.pranav.user.User;

public interface UserDAO {
    String getApiKeyByUserId(String userId);
    void addUser(User user);
}