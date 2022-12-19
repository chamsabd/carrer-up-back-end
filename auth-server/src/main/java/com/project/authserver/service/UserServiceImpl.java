package com.project.authserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.authserver.entities.User;
import com.project.authserver.repository.UserRepository;
import com.project.authserver.validator.UserValidator;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserValidator validator;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
		this.validator = new UserValidator();
        
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        repository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public String registerUser(User user) {
        validator.validateUser(user);
        return repository.save(user).getId();
    }

    @Override
    public boolean validateUserPassword(String username, String password) {
        return false;
    }

    @Override
    public boolean resetPassword() {
        return false;
    }

    @Override
    public void updateUser(User user) {
        repository.save(user);
    }

    @Override
    public Long getUsersCount() {
        return repository.count();
    }
}
