package com.example.demobtvenha25_8.controller;

import com.example.demobtvenha25_8.model.Product;
import com.example.demobtvenha25_8.service.ProductService;
import com.example.demobtvenha25_8.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Trang chủ – hiển thị 10 sản phẩm mới nhất
 * URL: /home
 */
@WebServlet("/home")
public class HomeController extends HttpServlet {

    private final ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Product> latestProducts = productService.getLatest10();
        req.setAttribute("latestProducts", latestProducts);
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}
