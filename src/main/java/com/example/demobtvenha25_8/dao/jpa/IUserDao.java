package com.example.demobtvenha25_8.dao.jpa;

import com.example.demobtvenha25_8.model.User;

public interface IUserDao {
    User findById(int id);
    void update(User user);
}
