package com.example.demobtvenha25_8.service.impl;

import com.example.demobtvenha25_8.dao.UserDAO;
import com.example.demobtvenha25_8.dao.impl.UserDaoImpl;
import com.example.demobtvenha25_8.model.User;
import com.example.demobtvenha25_8.service.UserService;
import com.example.demobtvenha25_8.util.EmailUtil;
import com.example.demobtvenha25_8.util.OtpUtil;

import java.sql.Timestamp;

public class UserServiceImpl implements UserService {

    private final UserDAO userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User user = userDao.get(username);
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
    public boolean register(String username, String password, String email,
                            String fullname, String phone) {
        if (userDao.checkExistUsername(username)) return false;

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setFullName(fullname);
        newUser.setPhone(phone);
        newUser.setRoleid(3);
        newUser.setCreatedDate(date);
        userDao.insert(newUser);

        return sendActivationOtp(email);
    }

    @Override
    public boolean sendActivationOtp(String email) {
        return sendOtp(email, "Kich hoat tai khoan");
    }

    @Override
    public boolean verifyActivationOtp(String email, String otp) {
        if (userDao.verifyOtp(email, otp)) {
            userDao.activateUser(email);
            return true;
        }
        return false;
    }

    @Override
    public boolean sendForgotPasswordOtp(String email) {
        User user = userDao.getByEmail(email);
        if (user == null) return false;
        return sendOtp(email, "Dat lai mat khau");
    }

    @Override
    public boolean verifyForgotPasswordOtp(String email, String otp) {
        return userDao.verifyOtp(email, otp);
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        userDao.updatePassword(email, newPassword);
        return true;
    }

    private boolean sendOtp(String email, String subject) {
        String otp = OtpUtil.generate();
        Timestamp expired = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000L);
        userDao.updateOtp(email, otp, expired);
        try {
            EmailUtil.sendOtp(email, otp, subject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}