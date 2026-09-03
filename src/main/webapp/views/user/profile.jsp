<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ Sơ Cá Nhân - Thông Tin Tài Khoản</title>
    <style>
        .profile-card {
            width: 100%;
            max-width: 680px;
            background: rgba(255, 255, 255, 0.04);
            backdrop-filter: blur(24px);
            -webkit-backdrop-filter: blur(24px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 24px;
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
            padding: 2.5rem;
            margin: auto;
        }

        .profile-header {
            text-align: center;
            margin-bottom: 2rem;
            position: relative;
        }

        .avatar-container {
            position: relative;
            width: 120px;
            height: 120px;
            margin: 0 auto 1.25rem;
        }

        .avatar-img {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #8b5cf6;
            box-shadow: 0 0 25px rgba(139, 92, 246, 0.45);
            background: #1e1b4b;
        }

        .avatar-placeholder {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            background: linear-gradient(135deg, #7c3aed, #2563eb);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 3rem;
            color: #ffffff;
            font-weight: 700;
            border: 3px solid #8b5cf6;
            box-shadow: 0 0 25px rgba(139, 92, 246, 0.45);
        }

        .avatar-upload-btn {
            display: inline-block;
            margin-top: 0.6rem;
            background: rgba(139, 92, 246, 0.2);
            color: #c4b5fd;
            border: 1px solid rgba(139, 92, 246, 0.4);
            padding: 0.35rem 0.85rem;
            border-radius: 6px;
            font-size: 0.8rem;
            cursor: pointer;
            transition: all 0.2s ease;
        }

        .avatar-upload-btn:hover {
            background: rgba(139, 92, 246, 0.35);
            color: #ffffff;
        }

        .file-input {
            display: none;
        }

        .profile-header h2 {
            font-size: 1.75rem;
            font-weight: 700;
            color: #f8fafc;
            margin-bottom: 0.35rem;
            letter-spacing: -0.02em;
        }

        .profile-header p {
            color: #94a3b8;
            font-size: 0.9rem;
        }

        .role-badge {
            display: inline-block;
            margin-top: 0.5rem;
            padding: 0.25rem 0.85rem;
            font-size: 0.78rem;
            font-weight: 600;
            border-radius: 9999px;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        .role-admin {
            background: rgba(239, 68, 68, 0.18);
            color: #fca5a5;
            border: 1px solid rgba(239, 68, 68, 0.35);
        }

        .role-manager {
            background: rgba(245, 158, 11, 0.18);
            color: #fcd34d;
            border: 1px solid rgba(245, 158, 11, 0.35);
        }

        .role-user {
            background: rgba(59, 130, 246, 0.18);
            color: #93c5fd;
            border: 1px solid rgba(59, 130, 246, 0.35);
        }

        /* alert */
        .alert {
            padding: 0.85rem 1.25rem;
            border-radius: 12px;
            margin-bottom: 1.5rem;
            font-size: 0.9rem;
        }

        .alert-success {
            background: rgba(34, 197, 94, 0.15);
            border: 1px solid rgba(34, 197, 94, 0.35);
            color: #86efac;
        }

        .alert-error {
            background: rgba(239, 68, 68, 0.15);
            border: 1px solid rgba(239, 68, 68, 0.35);
            color: #fca5a5;
        }

        /* form */
        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1.25rem;
            margin-bottom: 1.5rem;
        }

        .form-group-full {
            grid-column: span 2;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            gap: 0.45rem;
        }

        .form-group label {
            font-size: 0.85rem;
            font-weight: 600;
            color: #cbd5e1;
        }

        .form-control {
            background: rgba(255, 255, 255, 0.05);
            border: 1px solid rgba(255, 255, 255, 0.12);
            border-radius: 10px;
            padding: 0.75rem 1rem;
            color: #f1f5f9;
            font-size: 0.95rem;
            outline: none;
            transition: all 0.2s ease;
        }

        .form-control:focus {
            border-color: #8b5cf6;
            background: rgba(139, 92, 246, 0.08);
            box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.2);
        }

        .form-control[readonly] {
            background: rgba(255, 255, 255, 0.02);
            color: #64748b;
            cursor: not-allowed;
            border-color: rgba(255, 255, 255, 0.06);
        }

        .form-actions {
            display: flex;
            justify-content: flex-end;
            align-items: center;
            gap: 1rem;
            margin-top: 1.75rem;
            padding-top: 1.25rem;
            border-top: 1px solid rgba(255, 255, 255, 0.08);
        }

        .btn {
            padding: 0.75rem 1.6rem;
            border-radius: 10px;
            font-size: 0.9rem;
            font-weight: 600;
            text-decoration: none;
            cursor: pointer;
            transition: all 0.2s ease;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            border: none;
        }

        .btn-cancel {
            background: rgba(255, 255, 255, 0.06);
            color: #94a3b8;
            border: 1px solid rgba(255, 255, 255, 0.1);
        }

        .btn-cancel:hover {
            background: rgba(255, 255, 255, 0.1);
            color: #f1f5f9;
        }

        .btn-submit {
            background: linear-gradient(135deg, #7c3aed, #2563eb);
            color: #ffffff;
            box-shadow: 0 4px 15px rgba(124, 58, 237, 0.4);
        }

        .btn-submit:hover {
            box-shadow: 0 6px 22px rgba(124, 58, 237, 0.6);
            transform: translateY(-1px);
        }

        .upload-hint {
            font-size: 0.78rem;
            color: #64748b;
            text-align: center;
            margin-top: 0.4rem;
        }

        @media (max-width: 640px) {
            .form-grid {
                grid-template-columns: 1fr;
            }
            .form-group-full {
                grid-column: span 1;
            }
        }
    </style>
</head>
<body>

<div class="profile-card">

    <!-- Thong bao -->
    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <!-- Form profile -->
    <form action="${pageContext.request.contextPath}/user/profile" method="post" enctype="multipart/form-data">
        
        <div class="profile-header">
            <div class="avatar-container">
                <c:choose>
                    <c:when test="${not empty user.avatar}">
                        <img
                            id="avatarPreview"
                            src="${pageContext.request.contextPath}/image?fname=${user.avatar}"
                            class="avatar-img"
                            alt="${user.fullName}"
                        />
                    </c:when>
                    <c:otherwise>
                        <div id="avatarFallback" class="avatar-placeholder">
                            ${not empty user.fullName ? user.fullName.substring(0, 1).toUpperCase() : 'U'}
                        </div>
                        <img id="avatarPreview" class="avatar-img" style="display: none;" alt="Preview"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div>
                <label for="avatarInput" class="avatar-upload-btn">Đổi ảnh đại diện</label>
                <input
                    type="file"
                    id="avatarInput"
                    name="avatar"
                    class="file-input"
                    accept="image/png, image/jpeg, image/jpg"
                    onchange="previewImage(this)"
                />
            </div>

            <h2>${not empty user.fullName ? user.fullName : user.userName}</h2>
            <p>${user.email}</p>

            <c:choose>
                <c:when test="${user.roleid == 1}">
                    <span class="role-badge role-admin">Administrator</span>
                </c:when>
                <c:when test="${user.roleid == 2}">
                    <span class="role-badge role-manager">Manager</span>
                </c:when>
                <c:otherwise>
                    <span class="role-badge role-user">Member</span>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="form-grid">
            <div class="form-group">
                <label for="username">Tên đăng nhập</label>
                <input
                    type="text"
                    id="username"
                    class="form-control"
                    value="${user.userName}"
                    readonly
                />
            </div>

            <div class="form-group">
                <label for="email">Địa chỉ Email</label>
                <input
                    type="email"
                    id="email"
                    class="form-control"
                    value="${user.email}"
                    readonly
                />
            </div>

            <div class="form-group-full form-group">
                <label for="fullName">Họ và tên <span style="color: #ef4444;">*</span></label>
                <input
                    type="text"
                    id="fullName"
                    name="fullName"
                    class="form-control"
                    value="${user.fullName}"
                    placeholder="Nhập họ và tên đầy đủ"
                    required
                />
            </div>

            <div class="form-group-full form-group">
                <label for="phone">Số điện thoại</label>
                <input
                    type="tel"
                    id="phone"
                    name="phone"
                    class="form-control"
                    value="${user.phone}"
                    placeholder="Nhập số điện thoại (ví dụ: 0912345678)"
                />
            </div>
        </div>

        <div class="form-actions">
            <a href="${pageContext.request.contextPath}/waiting" class="btn btn-cancel">Quay lại</a>
            <button type="submit" class="btn btn-submit">Lưu thay đổi</button>
        </div>

    </form>
</div>

<script>
    function previewImage(input) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function(e) {
                const preview = document.getElementById('avatarPreview');
                const fallback = document.getElementById('avatarFallback');

                preview.src = e.target.result;
                preview.style.display = 'block';

                if (fallback) {
                    fallback.style.display = 'none';
                }
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>

</body>
</html>
