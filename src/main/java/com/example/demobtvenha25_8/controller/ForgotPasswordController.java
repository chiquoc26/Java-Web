package com.example.demobtvenha25_8.controller;

import com.example.demobtvenha25_8.service.UserService;
import com.example.demobtvenha25_8.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Quên mật khẩu – nhập email để nhận OTP
 * URL: /forgot-password
 */
@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");

        if (email == null || email.isBlank()) {
            req.setAttribute("alert", "Vui lòng nhập địa chỉ email.");
            req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
            return;
        }

        boolean sent = userService.sendForgotPasswordOtp(email);

        if (sent) {
            resp.sendRedirect(req.getContextPath() + "/verify-reset-otp?email=" +
                    java.net.URLEncoder.encode(email, "UTF-8"));
        } else {
            req.setAttribute("alert", "Email không tồn tại trong hệ thống.");
            req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
        }
    }
}
