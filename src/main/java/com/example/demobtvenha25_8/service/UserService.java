package com.example.demobtvenha25_8.service;

import com.example.demobtvenha25_8.model.User;

public interface UserService {
    User login(String username, String password);
    User get(String username);
    boolean register(String username, String password, String email, String fullname, String phone);
}