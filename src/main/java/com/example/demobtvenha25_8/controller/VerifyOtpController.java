package com.example.demobtvenha25_8.controller;

import com.example.demobtvenha25_8.service.UserService;
import com.example.demobtvenha25_8.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Xác thực OTP kích hoạt tài khoản sau đăng ký
 * URL: /verify-otp?email=...&type=activation
 */
@WebServlet("/verify-otp")
public class VerifyOtpController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Hiển thị trang nhập OTP, truyền email xuống view
        req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String email = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("email"));
        String otp = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("otp1")) +
                     com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("otp2")) +
                     com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("otp3")) +
                     com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("otp4")) +
                     com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("otp5")) +
                     com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("otp6"));

        req.setAttribute("email", email);

        if (!com.example.demobtvenha25_8.util.ValidationUtil.isValidEmail(email)) {
            req.setAttribute("alert", "Email không hợp lệ.");
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        if (otp.length() != 6 || !otp.matches("\\d{6}")) {
            req.setAttribute("alert", "Mã OTP phải gồm đủ 6 chữ số.");
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        boolean ok = userService.verifyActivationOtp(email, otp);

        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/login?activated=true");
        } else {
            req.setAttribute("alert", "Mã OTP không đúng hoặc đã hết hạn. Vui lòng thử lại.");
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
        }
    }
}
