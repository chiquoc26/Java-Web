package com.example.demobtvenha25_8.controller;

import com.example.demobtvenha25_8.model.User;
import com.example.demobtvenha25_8.service.UserService;
import com.example.demobtvenha25_8.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    User user = userService.get(cookie.getValue());
                    if (user != null && user.isActive()) {
                        session = req.getSession(true);
                        session.setAttribute("account", user);
                        resp.sendRedirect(req.getContextPath() + "/waiting");
                        return;
                    }
                }
            }
        }

        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username    = req.getParameter("username");
        String password    = req.getParameter("password");
        boolean rememberMe = "on".equals(req.getParameter("remember"));

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("alert", "Tai khoan hoac mat khau khong duoc de trong");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        User user = userService.login(username, password);

        if (user == null) {
            req.setAttribute("alert", "Tai khoan hoac mat khau khong dung");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        if (!user.isActive()) {
            req.setAttribute("alert", "Tai khoan chua duoc kich hoat. Vui long kiem tra email da dang ky de lay ma OTP.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("account", user);

        if (rememberMe) {
            Cookie cookie = new Cookie("username", username);
            cookie.setMaxAge(30 * 60);
            resp.addCookie(cookie);
        }

        resp.sendRedirect(req.getContextPath() + "/waiting");
    }
}