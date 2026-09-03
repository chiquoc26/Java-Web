package com.example.demobtvenha25_8.controller.jpa;

import com.example.demobtvenha25_8.model.Category;
import com.example.demobtvenha25_8.service.jpa.CategoryServiceImpl;
import com.example.demobtvenha25_8.service.jpa.ICategoryService;
import com.example.demobtvenha25_8.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = {
    "/admin/jpa/categories",
    "/admin/jpa/category/add",
    "/admin/jpa/category/edit",
    "/admin/jpa/category/delete"
})
@MultipartConfig(
    maxFileSize = 5 * 1024 * 1024,
    maxRequestSize = 6 * 1024 * 1024
)
public class CategoryJpaController extends HttpServlet {

    private final ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/jpa/categories".equals(path)) {
            listCategories(req, resp);
        } else if ("/admin/jpa/category/add".equals(path)) {
            showAddForm(req, resp);
        } else if ("/admin/jpa/category/edit".equals(path)) {
            showEditForm(req, resp);
        } else if ("/admin/jpa/category/delete".equals(path)) {
            deleteCategory(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/jpa/category/add".equals(path)) {
            addCategory(req, resp);
        } else if ("/admin/jpa/category/edit".equals(path)) {
            editCategory(req, resp);
        }
    }

    private void listCategories(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<Category> categories;
        if (keyword != null && !keyword.trim().isEmpty()) {
            categories = categoryService.searchByName(keyword.trim());
            req.setAttribute("keyword", keyword);
        } else {
            categories = categoryService.findAll();
        }
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("/views/admin/jpa/category/list.jsp").forward(req, resp);
    }

    private void showAddForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/admin/jpa/category/add.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        try {
            int id = Integer.parseInt(idString);
            Category category = categoryService.findById(id);
            if (category != null) {
                req.setAttribute("category", category);
                req.getRequestDispatcher("/views/admin/jpa/category/edit.jsp").forward(req, resp);
                return;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/admin/jpa/categories");
    }

    private void deleteCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        try {
            int id = Integer.parseInt(idString);
            Category category = categoryService.findById(id);
            if (category != null) {
                categoryService.delete(id);
                deleteOldImage(category.getIcons());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/admin/jpa/categories");
    }

    private void addCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String cateName = req.getParameter("cateName");

        if (cateName == null || cateName.trim().isEmpty()) {
            req.setAttribute("error", "Tên Category không được để trống");
            req.getRequestDispatcher("/views/admin/jpa/category/add.jsp").forward(req, resp);
            return;
        }

        Part iconPart = req.getPart("icon");
        if (iconPart == null || iconPart.getSize() == 0) {
            req.setAttribute("error", "Vui lòng chọn ảnh JPG hoặc JPEG");
            req.getRequestDispatcher("/views/admin/jpa/category/add.jsp").forward(req, resp);
            return;
        }

        String contentType = iconPart.getContentType();
        if (!"image/jpeg".equalsIgnoreCase(contentType)) {
            req.setAttribute("error", "Chỉ được upload ảnh JPG hoặc JPEG");
            req.getRequestDispatcher("/views/admin/jpa/category/add.jsp").forward(req, resp);
            return;
        }

        String originalFileName = iconPart.getSubmittedFileName();
        String extension = getExtension(originalFileName);
        if (!".jpg".equalsIgnoreCase(extension) && !".jpeg".equalsIgnoreCase(extension)) {
            req.setAttribute("error", "File phải có định dạng .jpg hoặc .jpeg");
            req.getRequestDispatcher("/views/admin/jpa/category/add.jsp").forward(req, resp);
            return;
        }

        try {
            String newFileName = UUID.randomUUID() + extension.toLowerCase();
            Path categoryUploadDir = Paths.get(Constant.UPLOAD_DIR, "category");
            Files.createDirectories(categoryUploadDir);
            Path filePath = categoryUploadDir.resolve(newFileName);
            iconPart.write(filePath.toString());

            String iconPath = "category/" + newFileName;
            Category category = new Category(cateName.trim(), iconPath);
            categoryService.insert(category);
            resp.sendRedirect(req.getContextPath() + "/admin/jpa/categories");
        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/views/admin/jpa/category/add.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi xảy ra trong quá trình lưu trữ");
            req.getRequestDispatcher("/views/admin/jpa/category/add.jsp").forward(req, resp);
        }
    }

    private void editCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String idString = req.getParameter("cateId");
        String cateName = req.getParameter("cateName");

        if (idString == null || cateName == null || cateName.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/jpa/categories");
            return;
        }

        try {
            int cateId = Integer.parseInt(idString);
            Category oldCategory = categoryService.findById(cateId);
            if (oldCategory == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/jpa/categories");
                return;
            }

            String iconPath = oldCategory.getIcons();
            Part iconPart = req.getPart("icon");

            if (iconPart != null && iconPart.getSize() > 0) {
                String contentType = iconPart.getContentType();
                if (!"image/jpeg".equalsIgnoreCase(contentType)) {
                    req.setAttribute("category", oldCategory);
                    req.setAttribute("error", "Chỉ được upload ảnh JPG hoặc JPEG");
                    req.getRequestDispatcher("/views/admin/jpa/category/edit.jsp").forward(req, resp);
                    return;
                }

                String originalFileName = iconPart.getSubmittedFileName();
                String extension = getExtension(originalFileName);
                if (!".jpg".equalsIgnoreCase(extension) && !".jpeg".equalsIgnoreCase(extension)) {
                    req.setAttribute("category", oldCategory);
                    req.setAttribute("error", "File phải có định dạng .jpg hoặc .jpeg");
                    req.getRequestDispatcher("/views/admin/jpa/category/edit.jsp").forward(req, resp);
                    return;
                }

                String newFileName = UUID.randomUUID() + extension.toLowerCase();
                Path categoryUploadDir = Paths.get(Constant.UPLOAD_DIR, "category");
                Files.createDirectories(categoryUploadDir);
                Path newFilePath = categoryUploadDir.resolve(newFileName);
                iconPart.write(newFilePath.toString());

                iconPath = "category/" + newFileName;
                deleteOldImage(oldCategory.getIcons());
            }

            Category updatedCategory = new Category(cateId, cateName.trim(), iconPath);
            categoryService.update(updatedCategory);
            resp.sendRedirect(req.getContextPath() + "/admin/jpa/categories");

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/jpa/categories");
        } catch (RuntimeException e) {
            int cateId = Integer.parseInt(idString);
            Category oldCategory = categoryService.findById(cateId);
            req.setAttribute("category", oldCategory);
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/views/admin/jpa/category/edit.jsp").forward(req, resp);
        }
    }

    private String getExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot == -1) {
            return "";
        }
        return fileName.substring(lastDot);
    }

    private void deleteOldImage(String iconPath) {
        if (iconPath == null || iconPath.isBlank()) {
            return;
        }
        try {
            Path uploadRoot = Paths.get(Constant.UPLOAD_DIR).toAbsolutePath().normalize();
            Path oldFile = uploadRoot.resolve(iconPath).normalize();
            if (oldFile.startsWith(uploadRoot) && Files.exists(oldFile)) {
                Files.delete(oldFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
