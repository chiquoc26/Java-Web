package com.example.demobtvenha25_8.dao;

import com.example.demobtvenha25_8.model.User;

public interface UserDAO {
    User get(String username);
    boolean checkExistUsername(String username);
    void insert(User user);
}
