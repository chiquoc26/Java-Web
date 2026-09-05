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

        String email = req.getParameter("email");
        // Ghép 6 ô OTP lại thành 1 chuỗi
        String otp = req.getParameter("otp1") + req.getParameter("otp2") +
                     req.getParameter("otp3") + req.getParameter("otp4") +
                     req.getParameter("otp5") + req.getParameter("otp6");

        boolean ok = userService.verifyActivationOtp(email, otp);

        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/login?activated=true");
        } else {
            req.setAttribute("alert", "Mã OTP không đúng hoặc đã hết hạn. Vui lòng thử lại.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
        }
    }
}
