package com.example.demobtvenha25_8.controller;

import com.example.demobtvenha25_8.service.UserService;
import com.example.demobtvenha25_8.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Xác thực OTP và đặt lại mật khẩu
 * URL: /verify-reset-otp?email=...
 */
@WebServlet("/verify-reset-otp")
public class VerifyResetOtpController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/verify-reset-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String email       = req.getParameter("email");
        String newPassword = req.getParameter("newPassword");
        String confirmPass = req.getParameter("confirmPassword");

        // Ghép OTP 6 ô
        String otp = req.getParameter("otp1") + req.getParameter("otp2") +
                     req.getParameter("otp3") + req.getParameter("otp4") +
                     req.getParameter("otp5") + req.getParameter("otp6");

        // Kiểm tra mật khẩu khớp
        if (newPassword == null || !newPassword.equals(confirmPass)) {
            req.setAttribute("alert", "Mật khẩu xác nhận không khớp.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/verify-reset-otp.jsp").forward(req, resp);
            return;
        }

        // Xác thực OTP
        boolean otpOk = userService.verifyForgotPasswordOtp(email, otp);
        if (!otpOk) {
            req.setAttribute("alert", "Mã OTP không đúng hoặc đã hết hạn.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/verify-reset-otp.jsp").forward(req, resp);
            return;
        }

        // Cập nhật mật khẩu mới
        userService.resetPassword(email, newPassword);
        resp.sendRedirect(req.getContextPath() + "/login?reset=success");
    }
}
