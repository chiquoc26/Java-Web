package com.example.demobtvenha25_8.controller.jpa;

import com.example.demobtvenha25_8.model.User;
import com.example.demobtvenha25_8.service.jpa.IUserJpaService;
import com.example.demobtvenha25_8.service.jpa.UserJpaServiceImpl;
import com.example.demobtvenha25_8.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/user/profile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1 MB
        maxFileSize = 5 * 1024 * 1024,         // 5 MB
        maxRequestSize = 10 * 1024 * 1024      // 10 MB
)
public class ProfileJpaController extends HttpServlet {

    private final IUserJpaService userJpaService = new UserJpaServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User sessionUser = (User) session.getAttribute("account");
        User currentUser = userJpaService.findById(sessionUser.getId());
        if (currentUser == null) {
            currentUser = sessionUser;
        }
        req.setAttribute("user", currentUser);
        req.getRequestDispatcher("/views/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User sessionUser = (User) session.getAttribute("account");
        User user = userJpaService.findById(sessionUser.getId());
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");

        if (fullName == null || fullName.trim().isEmpty()) {
            req.setAttribute("user", user);
            req.setAttribute("error", "Họ và tên không được để trống!");
            req.getRequestDispatcher("/views/user/profile.jsp").forward(req, resp);
            return;
        }

        user.setFullName(fullName.trim());
        user.setPhone(phone != null ? phone.trim() : "");

        // Upload avatar
        try {
            Part filePart = req.getPart("avatar");
            if (filePart != null && filePart.getSize() > 0) {
                String submittedFileName = filePart.getSubmittedFileName();
                String extension = getExtension(submittedFileName);

                if (!isAllowedImageExtension(extension)) {
                    req.setAttribute("user", user);
                    req.setAttribute("error", "Định dạng ảnh không hợp lệ! Vui lòng chọn .jpg, .jpeg, hoặc .png.");
                    req.getRequestDispatcher("/views/user/profile.jsp").forward(req, resp);
                    return;
                }

                String newFileName = UUID.randomUUID() + extension.toLowerCase();
                Path userUploadDir = Paths.get(Constant.UPLOAD_DIR, "user");
                Files.createDirectories(userUploadDir);
                Path filePath = userUploadDir.resolve(newFileName);
                filePart.write(filePath.toString());

                // Xoa anh cu
                deleteOldImage(user.getAvatar());

                user.setAvatar("user/" + newFileName);
            }

            // Cap nhat DB
            userJpaService.update(user);

            // Cap nhat session
            session.setAttribute("account", user);

            req.setAttribute("user", user);
            req.setAttribute("message", "Cập nhật thông tin cá nhân thành công!");
            req.getRequestDispatcher("/views/user/profile.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("user", user);
            req.setAttribute("error", "Có lỗi xảy ra khi cập nhật hồ sơ: " + e.getMessage());
            req.getRequestDispatcher("/views/user/profile.jsp").forward(req, resp);
        }
    }

    private String getExtension(String fileName) {
        if (fileName == null) return "";
        int lastDot = fileName.lastIndexOf('.');
        return (lastDot == -1) ? "" : fileName.substring(lastDot);
    }

    private boolean isAllowedImageExtension(String extension) {
        if (extension == null) return false;
        String ext = extension.toLowerCase();
        return ext.equals(".jpg") || ext.equals(".jpeg") || ext.equals(".png");
    }

    private void deleteOldImage(String avatarPath) {
        if (avatarPath == null || avatarPath.isBlank()) {
            return;
        }
        try {
            Path uploadRoot = Paths.get(Constant.UPLOAD_DIR).toAbsolutePath().normalize();
            Path oldFile = uploadRoot.resolve(avatarPath).normalize();
            if (oldFile.startsWith(uploadRoot) && Files.exists(oldFile)) {
                Files.delete(oldFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
