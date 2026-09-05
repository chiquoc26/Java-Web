package com.example.demobtvenha25_8.controller;

import com.example.demobtvenha25_8.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet({"/image", "/image/*"})
public class DownloadImageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lay ten file tu parameter ?fname=... hoac tu pathInfo /...
        String fileName = request.getParameter("fname");
        if (fileName == null || fileName.isBlank()) {
            String pathInfo = request.getPathInfo();
            if (pathInfo != null && !pathInfo.isBlank() && !pathInfo.equals("/")) {
                fileName = pathInfo.startsWith("/") ? pathInfo.substring(1) : pathInfo;
            }
        }

        if (fileName == null || fileName.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing image name");
            return;
        }

        // Chuan hoa duong dan de tranh directory traversal
        fileName = fileName.replace("\\", "/");
        if (fileName.contains("..")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Path filePath = resolveImagePath(fileName);

        if (filePath == null || !Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "image/jpeg";
        }

        response.setContentType(contentType);
        Files.copy(filePath, response.getOutputStream());
    }

    private Path resolveImagePath(String fileName) {
        // Neu fileName co tien to product/
        if (fileName.startsWith("product/")) {
            String name = fileName.substring("product/".length());
            Path p = Paths.get(Constant.PRODUCT_UPLOAD_DIR, name);
            if (Files.exists(p)) return p;
        }

        // Kiem tra trong PRODUCT_UPLOAD_DIR
        Path pProduct = Paths.get(Constant.PRODUCT_UPLOAD_DIR, fileName);
        if (Files.exists(pProduct)) return pProduct;

        // Kiem tra trong UPLOAD_DIR (bao gom ca thu muc user/ vi du: category_upload/user/...)
        Path pUpload = Paths.get(Constant.UPLOAD_DIR, fileName);
        if (Files.exists(pUpload)) return pUpload;

        // Kiem tra trong USER_UPLOAD_DIR
        String userFileOnly = fileName.startsWith("user/") ? fileName.substring("user/".length()) : fileName;
        Path pUser = Paths.get(Constant.USER_UPLOAD_DIR, userFileOnly);
        if (Files.exists(pUser)) return pUser;

        return null;
    }
}