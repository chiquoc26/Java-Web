package com.example.demobtvenha25_8.dao;

import com.example.demobtvenha25_8.model.User;
import java.sql.Timestamp;

public interface UserDAO {
    User    get(String username);
    User    getByEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistEmail(String email);
    void    insert(User user);

    // OTP – kích hoạt tài khoản và quên mật khẩu
    void    updateOtp(String email, String otp, Timestamp expired);
    boolean verifyOtp(String email, String otp);
    void    activateUser(String email);
    void    updatePassword(String email, String newPassword);
}
