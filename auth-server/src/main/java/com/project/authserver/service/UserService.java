package com.project.authserver.service;


import java.util.List;

import com.project.authserver.entities.User;

public interface UserService {
    List<User> getUsers();
    String registerUser(User user);
    boolean validateUserPassword(String username, String password);
    boolean resetPassword();
    void updateUser(User car);
    Long getUsersCount();
}