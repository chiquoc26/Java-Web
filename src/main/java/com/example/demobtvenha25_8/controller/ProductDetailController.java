package com.example.demobtvenha25_8.controller;

import com.example.demobtvenha25_8.model.Product;
import com.example.demobtvenha25_8.service.ProductService;
import com.example.demobtvenha25_8.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Chi tiết một sản phẩm
 * URL: /product-detail?id=...
 */
@WebServlet("/product-detail")
public class ProductDetailController extends HttpServlet {

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

        int id = 0;
        try { id = Integer.parseInt(req.getParameter("id")); }
        catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/product");
            return;
        }

        Product product = productService.getById(id);
        if (product == null) {
            resp.sendRedirect(req.getContextPath() + "/product");
            return;
        }

        req.setAttribute("product", product);
        req.getRequestDispatcher("/views/product-detail.jsp").forward(req, resp);
    }
}
