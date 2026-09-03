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

        String idString =
                request.getParameter("cateId");

        String cateName =
                request.getParameter("cateName");

        if (idString == null
                || cateName == null
                || cateName.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/category/list"
            );
            return;
        }

        try {

            int cateId =
                    Integer.parseInt(idString);

            Category oldCategory =
                    categoryService.findById(cateId);

            if (oldCategory == null) {
                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/category/list"
                );
                return;
            }

            String iconPath =
                    oldCategory.getIcons();

            Part iconPart =
                    request.getPart("icon");

            // Người dùng chọn ảnh mới
            if (iconPart != null
                    && iconPart.getSize() > 0) {

                String contentType =
                        iconPart.getContentType();

                if (!"image/jpeg".equalsIgnoreCase(
                        contentType)) {

                    request.setAttribute(
                            "category",
                            oldCategory
                    );

                    request.setAttribute(
                            "error",
                            "Chỉ được upload ảnh JPG hoặc JPEG"
                    );

                    request.getRequestDispatcher(
                            "/views/admin/category/edit.jsp"
                    ).forward(request, response);

                    return;
                }

                String originalFileName =
                        iconPart.getSubmittedFileName();

                String extension =
                        getExtension(originalFileName);

                if (!".jpg".equalsIgnoreCase(extension)
                        && !".jpeg".equalsIgnoreCase(extension)) {

                    request.setAttribute(
                            "category",
                            oldCategory
                    );

                    request.setAttribute(
                            "error",
                            "File phải có định dạng .jpg hoặc .jpeg"
                    );

                    request.getRequestDispatcher(
                            "/views/admin/category/edit.jsp"
                    ).forward(request, response);

                    return;
                }

                String newFileName =
                        UUID.randomUUID()
                                + extension.toLowerCase();

                Path categoryUploadDir =
                        Paths.get(
                                Constant.UPLOAD_DIR,
                                "category"
                        );

                Files.createDirectories(
                        categoryUploadDir
                );

                Path newFilePath =
                        categoryUploadDir.resolve(
                                newFileName
                        );

                iconPart.write(
                        newFilePath.toString()
                );

                iconPath =
                        "category/" + newFileName;

                // Xóa ảnh cũ
                deleteOldImage(
                        oldCategory.getIcons()
                );
            }

            Category updatedCategory =
                    new Category(
                            cateId,
                            cateName.trim(),
                            iconPath
                    );

            try {
                categoryService.update(
                        updatedCategory
                );
                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/category/list"
                );
            } catch (RuntimeException e) {
                request.setAttribute("category", oldCategory);
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher(
                        "/views/admin/category/edit.jsp"
                ).forward(request, response);
            }

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/category/list"
            );
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