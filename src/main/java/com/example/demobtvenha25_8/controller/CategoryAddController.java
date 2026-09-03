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

        String cateName = request.getParameter("cateName");

        if (cateName == null || cateName.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Tên Category không được để trống"
            );

            request.getRequestDispatcher(
                    "/views/admin/category/add.jsp"
            ).forward(request, response);

            return;
        }

        Part iconPart = request.getPart("icon");

        if (iconPart == null || iconPart.getSize() == 0) {

            request.setAttribute(
                    "error",
                    "Vui lòng chọn ảnh JPG hoặc JPEG"
            );

            request.getRequestDispatcher(
                    "/views/admin/category/add.jsp"
            ).forward(request, response);

            return;
        }

        String contentType = iconPart.getContentType();

        if (!"image/jpeg".equalsIgnoreCase(contentType)) {

            request.setAttribute(
                    "error",
                    "Chỉ được upload ảnh JPG hoặc JPEG"
            );

            request.getRequestDispatcher(
                    "/views/admin/category/add.jsp"
            ).forward(request, response);

            return;
        }

        String originalFileName =
                iconPart.getSubmittedFileName();

        String extension = getExtension(originalFileName);

        if (!".jpg".equalsIgnoreCase(extension)
                && !".jpeg".equalsIgnoreCase(extension)) {

            request.setAttribute(
                    "error",
                    "File phải có định dạng .jpg hoặc .jpeg"
            );

            request.getRequestDispatcher(
                    "/views/admin/category/add.jsp"
            ).forward(request, response);

            return;
        }

        String newFileName =
                UUID.randomUUID() + extension.toLowerCase();

        Path categoryUploadDir =
                Paths.get(Constant.UPLOAD_DIR, "category");

        Files.createDirectories(categoryUploadDir);

        Path filePath =
                categoryUploadDir.resolve(newFileName);

        iconPart.write(filePath.toString());

        String iconPath =
                "category/" + newFileName;

        Category category =
                new Category(
                        cateName.trim(),
                        iconPath
                );

        try {
            categoryService.insert(category);
            response.sendRedirect(
                    request.getContextPath() + "/admin/category/list"
            );
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(
                    "/views/admin/category/add.jsp"
            ).forward(request, response);
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