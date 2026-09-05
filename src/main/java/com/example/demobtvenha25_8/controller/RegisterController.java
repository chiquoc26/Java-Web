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

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email    = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String phone    = req.getParameter("phone");

        boolean ok = userService.register(username, password, email, fullname, phone);

        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/verify-otp?email="
                    + java.net.URLEncoder.encode(email, "UTF-8"));
        } else {
            req.setAttribute("alert", "Ten dang nhap da ton tai, vui long chon ten khac!");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }
}
