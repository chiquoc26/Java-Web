package com.example.demobtvenha25_8.service.impl;

import com.example.demobtvenha25_8.dao.UserDAO;
import com.example.demobtvenha25_8.dao.impl.UserDaoImpl;
import com.example.demobtvenha25_8.model.User;
import com.example.demobtvenha25_8.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDAO userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User user = this.get(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User get(String username) {
        return userDao.get(username);
    }

    @Override
    public boolean register(String username, String password, String email, String fullname, String phone) {
        // 1. Kiểm tra tài khoản đã tồn tại chưa
        if (userDao.checkExistUsername(username)) {
            return false;
        }

        // 2. Tạo đối tượng User mới (mặc định roleid = 3 cho người dùng thường)
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setFullName(fullname);
        newUser.setPhone(phone);
        newUser.setRoleid(3); // Mặc định là User
        newUser.setCreatedDate(date);

        userDao.insert(newUser);
        return true;
    }
}