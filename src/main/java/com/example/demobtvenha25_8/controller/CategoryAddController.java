package com.example.demobtvenha25_8.controller;

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
import java.util.UUID;

@WebServlet("/admin/category/add")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 6 * 1024 * 1024
)
public class CategoryAddController extends HttpServlet {

    private final ICategoryService categoryService =
            new CategoryServiceImpl();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.getRequestDispatcher(
                "/views/admin/category/add.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String cateName = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(request.getParameter("cateName"));
        request.setAttribute("cateName", cateName);

        if (cateName.length() < 2) {
            request.setAttribute("error", "Tên danh mục phải có ít nhất 2 ký tự.");
            request.getRequestDispatcher("/views/admin/category/add.jsp").forward(request, response);
            return;
        }

        Category existingCate = categoryService.findByCategoryName(cateName);
        if (existingCate != null) {
            request.setAttribute("error", "Tên danh mục đã tồn tại trong hệ thống.");
            request.getRequestDispatcher("/views/admin/category/add.jsp").forward(request, response);
            return;
        }

        Part iconPart = request.getPart("icon");
        if (iconPart == null || iconPart.getSize() == 0) {
            request.setAttribute("error", "Vui lòng chọn hình ảnh đại diện cho danh mục.");
            request.getRequestDispatcher("/views/admin/category/add.jsp").forward(request, response);
            return;
        }

        if (iconPart.getSize() > 5 * 1024 * 1024) {
            request.setAttribute("error", "Dung lượng hình ảnh không được vượt quá 5MB.");
            request.getRequestDispatcher("/views/admin/category/add.jsp").forward(request, response);
            return;
        }

        String originalFileName = iconPart.getSubmittedFileName();
        if (!com.example.demobtvenha25_8.util.ValidationUtil.isValidImageExtension(originalFileName)) {
            request.setAttribute("error", "Chỉ chấp nhận các tệp ảnh định dạng .jpg, .jpeg, .png hoặc .webp.");
            request.getRequestDispatcher("/views/admin/category/add.jsp").forward(request, response);
            return;
        }

        String extension = getExtension(originalFileName);
        String newFileName = UUID.randomUUID() + extension.toLowerCase();

        Path categoryUploadDir = Paths.get(Constant.UPLOAD_DIR, "category");
        Files.createDirectories(categoryUploadDir);

        Path filePath = categoryUploadDir.resolve(newFileName);
        iconPart.write(filePath.toString());

        String iconPath = "category/" + newFileName;
        Category category = new Category(cateName, iconPath);

        try {
            categoryService.insert(category);
            response.sendRedirect(request.getContextPath() + "/admin/category/list");
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/admin/category/add.jsp").forward(request, response);
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
}