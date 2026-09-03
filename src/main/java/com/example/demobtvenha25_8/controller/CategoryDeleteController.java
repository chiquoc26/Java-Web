package com.example.demobtvenha25_8.controller;

import com.example.demobtvenha25_8.model.Category;
import com.example.demobtvenha25_8.service.jpa.CategoryServiceImpl;
import com.example.demobtvenha25_8.service.jpa.ICategoryService;
import com.example.demobtvenha25_8.util.Constant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/admin/category/delete")
public class CategoryDeleteController extends HttpServlet {

    private final ICategoryService categoryService =
            new CategoryServiceImpl();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String idString = request.getParameter("id");

        try {
            int cateId = Integer.parseInt(idString);

            Category category = categoryService.findById(cateId);
            if (category != null) {
                categoryService.delete(cateId);
                deleteOldImage(category.getIcons());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(
                request.getContextPath() + "/admin/category/list"
        );
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