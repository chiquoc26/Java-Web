package com.example.demobtvenha25_8.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "[User]")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String userName;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "roleid")
    private int roleid;

    @Column(name = "phone")
    private String phone;

    @Column(name = "createddate")
    private Date createdDate;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_expired")
    private Timestamp otpExpired;

    public User() {}

    public User(int id, String email, String userName, String fullName, String password, String avatar, int roleid, String phone, Date createdDate) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.avatar = avatar;
        this.roleid = roleid;
        this.phone = phone;
        this.createdDate = createdDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public int getRoleid() { return roleid; }
    public void setRoleid(int roleid) { this.roleid = roleid; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public Timestamp getOtpExpired() { return otpExpired; }
    public void setOtpExpired(Timestamp otpExpired) { this.otpExpired = otpExpired; }
}