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

        String email       = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("email"));
        String newPassword = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("newPassword"));
        String confirmPass = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("confirmPassword"));

        // Ghép OTP 6 ô
        String otp = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("otp1")) +
                     com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("otp2")) +
                     com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("otp3")) +
                     com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("otp4")) +
                     com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("otp5")) +
                     com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("otp6"));

        req.setAttribute("email", email);

        if (!com.example.demobtvenha25_8.util.ValidationUtil.isValidEmail(email)) {
            req.setAttribute("alert", "Email không hợp lệ.");
            req.getRequestDispatcher("/views/verify-reset-otp.jsp").forward(req, resp);
            return;
        }

        if (otp.length() != 6 || !otp.matches("\\d{6}")) {
            req.setAttribute("alert", "Mã OTP phải gồm đủ 6 chữ số.");
            req.getRequestDispatcher("/views/verify-reset-otp.jsp").forward(req, resp);
            return;
        }

        if (!com.example.demobtvenha25_8.util.ValidationUtil.isValidPassword(newPassword)) {
            req.setAttribute("alert", "Mật khẩu mới phải có ít nhất 6 ký tự.");
            req.getRequestDispatcher("/views/verify-reset-otp.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra mật khẩu khớp
        if (!newPassword.equals(confirmPass)) {
            req.setAttribute("alert", "Mật khẩu xác nhận không khớp.");
            req.getRequestDispatcher("/views/verify-reset-otp.jsp").forward(req, resp);
            return;
        }

        // Xác thực OTP
        boolean otpOk = userService.verifyForgotPasswordOtp(email, otp);
        if (!otpOk) {
            req.setAttribute("alert", "Mã OTP không đúng hoặc đã hết hạn.");
            req.getRequestDispatcher("/views/verify-reset-otp.jsp").forward(req, resp);
            return;
        }

        // Cập nhật mật khẩu mới
        userService.resetPassword(email, newPassword);
        resp.sendRedirect(req.getContextPath() + "/login?reset=success");
    }
}
