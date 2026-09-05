package com.example.demobtvenha25_8.controller;

import com.example.demobtvenha25_8.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/waiting")
public class WaitingController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            User u = (User) session.getAttribute("account");
            req.setAttribute("username", u.getUserName());

            if (u.getRoleid() == 1) {
                resp.sendRedirect(req.getContextPath() + "/admin/home");
            } else if (u.getRoleid() == 2) {
                resp.sendRedirect(req.getContextPath() + "/views/manager/home.jsp");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}