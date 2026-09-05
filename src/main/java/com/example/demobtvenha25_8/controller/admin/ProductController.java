package com.example.demobtvenha25_8.controller.admin;

import com.example.demobtvenha25_8.model.Category;
import com.example.demobtvenha25_8.model.Product;
import com.example.demobtvenha25_8.model.User;
import com.example.demobtvenha25_8.service.ProductService;
import com.example.demobtvenha25_8.service.impl.ProductServiceImpl;
import com.example.demobtvenha25_8.service.jpa.CategoryServiceImpl;
import com.example.demobtvenha25_8.service.jpa.ICategoryService;
import com.example.demobtvenha25_8.util.Constant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/*
 * CRUD san pham cho admin
 * GET  /admin/products                 - danh sach
 * GET  /admin/products?act=add         - form them moi
 * POST /admin/products?act=add         - xu ly them moi
 * GET  /admin/products?act=edit&id=x   - form sua
 * POST /admin/products?act=edit        - xu ly sua
 * GET  /admin/products?act=delete&id=x - xoa
 */
@WebServlet("/admin/products")
@MultipartConfig(
        maxFileSize    = 5 * 1024 * 1024,
        maxRequestSize = 6 * 1024 * 1024
)
public class ProductController extends HttpServlet {

    private final ProductService   productService  = new ProductServiceImpl();
    private final ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath() + "/login"); return; }

        String act = req.getParameter("act");
        if (act == null) act = "";

        switch (act) {
            case "add":    showAddForm(req, resp);  break;
            case "edit":   showEditForm(req, resp); break;
            case "delete": handleDelete(req, resp); break;
            default:       showList(req, resp);     break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        req.setCharacterEncoding("UTF-8");

        String act = req.getParameter("act");
        if ("edit".equals(act)) handleEdit(req, resp);
        else                    handleAdd(req, resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> products = productService.getAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/views/admin/product/list.jsp").forward(req, resp);
    }

    private void showAddForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("categories", categoryService.findAll());
        req.setAttribute("act", "add");
        req.getRequestDispatcher("/views/admin/product/form.jsp").forward(req, resp);
    }

    private void handleAdd(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Product p = buildProduct(req, resp);
        if (p == null) return;
        productService.insert(p);
        resp.sendRedirect(req.getContextPath() + "/admin/products");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = parseId(req);
        Product product = productService.getById(id);
        if (product == null) { resp.sendRedirect(req.getContextPath() + "/admin/products"); return; }

        req.setAttribute("product", product);
        req.setAttribute("categories", categoryService.findAll());
        req.setAttribute("act", "edit");
        req.getRequestDispatcher("/views/admin/product/form.jsp").forward(req, resp);
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = parseId(req);
        Product existing = productService.getById(id);
        if (existing == null) { resp.sendRedirect(req.getContextPath() + "/admin/products"); return; }

        Product updated = buildProduct(req, resp);
        if (updated == null) return;

        if (updated.getImage() == null || updated.getImage().isEmpty()) {
            updated.setImage(existing.getImage());
        }
        updated.setProductId(id);
        productService.update(updated);
        resp.sendRedirect(req.getContextPath() + "/admin/products");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        productService.delete(parseId(req));
        resp.sendRedirect(req.getContextPath() + "/admin/products");
    }

    private Product buildProduct(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name        = req.getParameter("productName");
        String description = req.getParameter("description");
        String priceStr    = req.getParameter("price");
        String qtyStr      = req.getParameter("quantity");
        String cateIdStr   = req.getParameter("cateId");

        if (name == null || name.isBlank()) {
            forwardWithError(req, resp, "Ten san pham khong duoc de trong.");
            return null;
        }

        BigDecimal price;
        int quantity, cateId;
        try {
            price    = new BigDecimal(priceStr);
            quantity = Integer.parseInt(qtyStr);
            cateId   = Integer.parseInt(cateIdStr);
        } catch (Exception e) {
            forwardWithError(req, resp, "Du lieu khong hop le.");
            return null;
        }

        String imagePath = null;
        Part imagePart = req.getPart("image");
        if (imagePart != null && imagePart.getSize() > 0) {
            String originalName = imagePart.getSubmittedFileName();
            String ext = getExtension(originalName).toLowerCase();
            if (!ext.equals(".jpg") && !ext.equals(".jpeg")
                    && !ext.equals(".png") && !ext.equals(".webp")) {
                forwardWithError(req, resp, "Chi chap nhan anh JPG, PNG, WEBP.");
                return null;
            }
            String fileName = UUID.randomUUID() + ext;
            Path uploadDir  = Paths.get(Constant.PRODUCT_UPLOAD_DIR);
            Files.createDirectories(uploadDir);
            imagePart.write(uploadDir.resolve(fileName).toString());
            imagePath = fileName;
        }

        Product p = new Product();
        p.setProductName(name.trim());
        p.setDescription(description);
        p.setPrice(price);
        p.setQuantity(quantity);
        p.setCateId(cateId);
        p.setImage(imagePath);
        return p;
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String msg)
            throws ServletException, IOException {
        req.setAttribute("error", msg);
        req.setAttribute("act", "add");
        req.setAttribute("categories", categoryService.findAll());
        req.getRequestDispatcher("/views/admin/product/form.jsp").forward(req, resp);
    }

    private boolean isAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) return false;
        User user = (User) session.getAttribute("account");
        return user != null && user.getRoleid() == 1;
    }

    private int parseId(HttpServletRequest req) {
        try { return Integer.parseInt(req.getParameter("id")); }
        catch (Exception e) { return 0; }
    }

    private String getExtension(String fileName) {
        if (fileName == null) return "";
        int dot = fileName.lastIndexOf('.');
        return dot == -1 ? "" : fileName.substring(dot);
    }
}
