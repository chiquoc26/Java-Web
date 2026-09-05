package com.example.demobtvenha25_8.dao.impl;

import com.example.demobtvenha25_8.dao.DBcontext;
import com.example.demobtvenha25_8.dao.UserDAO;
import com.example.demobtvenha25_8.model.User;

import java.sql.*;

public class UserDaoImpl implements UserDAO {

    @Override
    public User get(String username) {
        String sql = "SELECT * FROM [User] WHERE username = ?";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        String sql = "SELECT * FROM [User] WHERE email = ?";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean checkExistUsername(String username) {
        String sql = "SELECT 1 FROM [User] WHERE username = ?";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO [User](email, username, fullname, password, avatar, "
                   + "roleid, phone, createddate, is_active) VALUES (?,?,?,?,?,?,?,?,0)";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getAvatar());
            ps.setInt(6, user.getRoleid());
            ps.setString(7, user.getPhone());
            ps.setDate(8, user.getCreatedDate());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void updateOtp(String email, String otp, Timestamp expired) {
        String sql = "UPDATE [User] SET otp = ?, otp_expired = ? WHERE email = ?";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, otp);
            ps.setTimestamp(2, expired);
            ps.setString(3, email);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        String sql = "SELECT 1 FROM [User] WHERE email = ? AND otp = ? AND otp_expired > GETDATE()";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, otp);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public void activateUser(String email) {
        String sql = "UPDATE [User] SET is_active = 1, otp = NULL, otp_expired = NULL WHERE email = ?";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        String sql = "UPDATE [User] SET password = ?, otp = NULL, otp_expired = NULL WHERE email = ?";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setUserName(rs.getString("username"));
        user.setFullName(rs.getString("fullname"));
        user.setPassword(rs.getString("password"));
        user.setAvatar(rs.getString("avatar"));
        user.setRoleid(rs.getInt("roleid"));
        user.setPhone(rs.getString("phone"));
        user.setCreatedDate(rs.getDate("createddate"));
        user.setActive(rs.getBoolean("is_active"));
        user.setOtp(rs.getString("otp"));
        user.setOtpExpired(rs.getTimestamp("otp_expired"));
        return user;
    }
}