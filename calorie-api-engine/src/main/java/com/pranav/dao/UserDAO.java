package com.pranav.dao;


import com.pranav.user.User;

public interface UserDAO {
    boolean isValidUser(String userId);
    String getApiKeyByUserId(String userId);
    void addUser(User user);
}