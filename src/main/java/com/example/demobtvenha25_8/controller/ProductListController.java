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
 * Trang danh sách sản phẩm – phân trang 6 sản phẩm/trang
 * URL: /product?page=1
 */
@WebServlet("/product")
public class ProductListController extends HttpServlet {

    private final ProductService productService = new ProductServiceImpl();
    private static final int PAGE_SIZE = 6;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int page = 1;
        try {
            String pageParam = req.getParameter("page");
            if (pageParam != null) page = Integer.parseInt(pageParam);
        } catch (NumberFormatException ignored) {}

        int totalProducts = productService.countAll();
        int totalPages    = (int) Math.ceil((double) totalProducts / PAGE_SIZE);
        if (page < 1) page = 1;
        if (page > totalPages && totalPages > 0) page = totalPages;

        List<Product> products = productService.getPage(page, PAGE_SIZE);

        req.setAttribute("products",      products);
        req.setAttribute("currentPage",   page);
        req.setAttribute("totalPages",    totalPages);
        req.setAttribute("totalProducts", totalProducts);
        req.getRequestDispatcher("/views/product-list.jsp").forward(req, resp);
    }
}
