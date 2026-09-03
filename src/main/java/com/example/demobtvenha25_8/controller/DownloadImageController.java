package com.example.demobtvenha25_8.controller;

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

@WebServlet("/image")
public class DownloadImageController extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String fileName = request.getParameter("fname");

        if (fileName == null || fileName.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Missing image name"
            );
            return;
        }

        Path uploadRoot =
                Paths.get(Constant.UPLOAD_DIR)
                        .toAbsolutePath()
                        .normalize();

        Path filePath =
                uploadRoot.resolve(fileName)
                        .normalize();

        // Không cho phép truy cập ra ngoài thư mục upload
        if (!filePath.startsWith(uploadRoot)) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN
            );
            return;
        }

        if (!Files.exists(filePath)
                || !Files.isRegularFile(filePath)) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND
            );
            return;
        }

        String contentType =
                Files.probeContentType(filePath);

        if (contentType == null) {
            contentType = "image/jpeg";
        }

        response.setContentType(contentType);

        Files.copy(
                filePath,
                response.getOutputStream()
        );
    }
}