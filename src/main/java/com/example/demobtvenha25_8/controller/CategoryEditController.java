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

@WebServlet("/admin/category/edit")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 6 * 1024 * 1024
)
public class CategoryEditController extends HttpServlet {

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

            Category category =
                    categoryService.findById(cateId);

            if (category == null) {
                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/category/list"
                );
                return;
            }

            request.setAttribute(
                    "category",
                    category
            );

            request.getRequestDispatcher(
                    "/views/admin/category/edit.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/category/list"
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idString = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(request.getParameter("cateId"));
        String cateName = com.example.demobtvenha25_8.util.ValidationUtil.safeTrim(request.getParameter("cateName"));

        int cateId;
        try {
            cateId = Integer.parseInt(idString);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/category/list");
            return;
        }

        Category oldCategory = categoryService.findById(cateId);
        if (oldCategory == null) {
            response.sendRedirect(request.getContextPath() + "/admin/category/list");
            return;
        }

        Category draft = new Category(cateId, cateName, oldCategory.getIcons());

        if (cateName.length() < 2) {
            request.setAttribute("category", draft);
            request.setAttribute("error", "Tên danh mục phải có ít nhất 2 ký tự.");
            request.getRequestDispatcher("/views/admin/category/edit.jsp").forward(request, response);
            return;
        }

        Category duplicate = categoryService.findByCategoryName(cateName);
        if (duplicate != null && duplicate.getCateId() != cateId) {
            request.setAttribute("category", draft);
            request.setAttribute("error", "Tên danh mục đã tồn tại trong hệ thống.");
            request.getRequestDispatcher("/views/admin/category/edit.jsp").forward(request, response);
            return;
        }

        String iconPath = oldCategory.getIcons();
        try {
            Part iconPart = request.getPart("icon");
            if (iconPart != null && iconPart.getSize() > 0) {
                if (iconPart.getSize() > 5 * 1024 * 1024) {
                    request.setAttribute("category", draft);
                    request.setAttribute("error", "Dung lượng hình ảnh không được vượt quá 5MB.");
                    request.getRequestDispatcher("/views/admin/category/edit.jsp").forward(request, response);
                    return;
                }

                String originalFileName = iconPart.getSubmittedFileName();
                if (!com.example.demobtvenha25_8.util.ValidationUtil.isValidImageExtension(originalFileName)) {
                    request.setAttribute("category", draft);
                    request.setAttribute("error", "Chỉ chấp nhận các tệp ảnh định dạng .jpg, .jpeg, .png hoặc .webp.");
                    request.getRequestDispatcher("/views/admin/category/edit.jsp").forward(request, response);
                    return;
                }

                String extension = getExtension(originalFileName);
                String newFileName = UUID.randomUUID() + extension.toLowerCase();

                Path categoryUploadDir = Paths.get(Constant.UPLOAD_DIR, "category");
                Files.createDirectories(categoryUploadDir);

                Path newFilePath = categoryUploadDir.resolve(newFileName);
                iconPart.write(newFilePath.toString());

                iconPath = "category/" + newFileName;
                deleteOldImage(oldCategory.getIcons());
            }

            Category updatedCategory = new Category(cateId, cateName, iconPath);
            categoryService.update(updatedCategory);
            response.sendRedirect(request.getContextPath() + "/admin/category/list");

        } catch (Exception e) {
            request.setAttribute("category", draft);
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("/views/admin/category/edit.jsp").forward(request, response);
        }
    }

    private String getExtension(
            String fileName
    ) {

        if (fileName == null) {
            return "";
        }

        int lastDot =
                fileName.lastIndexOf('.');

        if (lastDot == -1) {
            return "";
        }

        return fileName.substring(lastDot);
    }

    private void deleteOldImage(
            String iconPath
    ) {

        if (iconPath == null
                || iconPath.isBlank()) {
            return;
        }

        try {

            Path uploadRoot =
                    Paths.get(
                                    Constant.UPLOAD_DIR
                            ).toAbsolutePath()
                            .normalize();

            Path oldFile =
                    uploadRoot.resolve(iconPath)
                            .normalize();

            if (oldFile.startsWith(uploadRoot)
                    && Files.exists(oldFile)) {

                Files.delete(oldFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}