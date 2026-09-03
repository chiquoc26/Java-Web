package com.example.demobtvenha25_8.service.jpa;

import com.example.demobtvenha25_8.dao.jpa.IUserDao;
import com.example.demobtvenha25_8.dao.jpa.UserDao;
import com.example.demobtvenha25_8.model.User;

public class UserJpaServiceImpl implements IUserJpaService {

    private final IUserDao userDao = new UserDao();

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
}
