package com.example.demobtvenha25_8.service;

import com.example.demobtvenha25_8.model.User;

public interface UserService {
    User    login(String username, String password);
    User    get(String username);
    boolean checkExistUsername(String username);
    boolean checkExistEmail(String email);
    boolean register(String username, String password, String email, String fullname, String phone);

    // OTP – kích hoạt tài khoản
    boolean sendActivationOtp(String email);
    boolean verifyActivationOtp(String email, String otp);

    // OTP – quên mật khẩu
    boolean sendForgotPasswordOtp(String email);
    boolean verifyForgotPasswordOtp(String email, String otp);
    boolean resetPassword(String email, String newPassword);
}