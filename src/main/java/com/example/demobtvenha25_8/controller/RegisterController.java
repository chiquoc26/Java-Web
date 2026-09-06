package com.example.demobtvenha25_8.controller;

import com.example.demobtvenha25_8.service.UserService;
import com.example.demobtvenha25_8.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username        = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("username"));
        String password        = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("password"));
        String confirmPassword = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("confirmPassword"));
        String email           = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("email"));
        String fullname        = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("fullname"));
        String phone           = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(req.getParameter("phone"));

        // Giữ lại dữ liệu đã nhập
        req.setAttribute("username", username);
        req.setAttribute("fullname", fullname);
        req.setAttribute("email", email);
        req.setAttribute("phone", phone);

        if (!com.example.demobtvenha25_8.util.ValidationUtil.isValidUsername(username)) {
            req.setAttribute("alert", "Ten dang nhap phai tu 4 den 30 ky tu, chi gom chu cai, so va dau gach duoi.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        if (userService.checkExistUsername(username)) {
            req.setAttribute("alert", "Ten dang nhap da ton tai, vui long chon ten khac!");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        if (!com.example.demobtvenha25_8.util.ValidationUtil.isValidPassword(password)) {
            req.setAttribute("alert", "Mat khau phai co it nhat 6 ky tu.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        if (!password.equals(confirmPassword)) {
            req.setAttribute("alert", "Mat khau xac nhan khong khop.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        if (fullname.isEmpty() || fullname.length() < 2) {
            req.setAttribute("alert", "Ho va ten phai co it nhat 2 ky tu.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        if (!com.example.demobtvenha25_8.util.ValidationUtil.isValidEmail(email)) {
            req.setAttribute("alert", "Dia chi email khong dung dinh dang.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        if (userService.checkExistEmail(email)) {
            req.setAttribute("alert", "Email nay da duoc su dung boi mot tai khoan khac.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        if (!phone.isEmpty() && !com.example.demobtvenha25_8.util.ValidationUtil.isValidPhone(phone)) {
            req.setAttribute("alert", "So dien thoai khong hop le (can 10 chu so hop le cua Viet Nam).");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        boolean ok = userService.register(username, password, email, fullname, phone);
        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/verify-otp?email="
                    + java.net.URLEncoder.encode(email, java.nio.charset.StandardCharsets.UTF_8));
        } else {
            req.setAttribute("alert", "Dang ky that bai hoac khong gui duoc ma OTP. Vui long thu lai!");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }
}
