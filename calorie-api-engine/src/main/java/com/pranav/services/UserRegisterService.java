package com.pranav.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pranav.api.ApiKey;
import com.pranav.dao.UserDAO;
import com.pranav.user.User;
import com.pranav.utils.Idutil;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;


@Slf4j
public class UserRegisterService {

    private final UserDAO userDao;

    @Inject
    public UserRegisterService(UserDAO userDao){
        this.userDao = userDao;
    }

    public String addUser(User user) {
        log.info("Request was recieved");
        String userId = Idutil.generateUserId();
        user.setUserId(userId);
        user.setApiKey(new ApiKey(userId, UUID.randomUUID().toString()));
        userDao.addUser(user);
        log.info("{}, User has been added", userId);
        return userId;
    }
}
