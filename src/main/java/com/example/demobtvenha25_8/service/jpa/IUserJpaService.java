package com.example.demobtvenha25_8.service.jpa;

import com.example.demobtvenha25_8.model.User;

public interface IUserJpaService {

    User findById(int id);

    void update(User user);
}
