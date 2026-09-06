<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi" data-bs-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property="title">Hệ Thống</sitemesh:write></title>

    <!-- Bootstrap 5.3 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

    <!-- Google Fonts Inter -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">

    <style>
        *, *::before, *::after {
            font-family: 'Inter', sans-serif;
        }

        body {
            background-color: #0d0b1e;
            color: #e2e8f0;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .navbar-custom {
            background: rgba(15, 10, 30, 0.9);
            backdrop-filter: blur(16px);
            -webkit-backdrop-filter: blur(16px);
            border-bottom: 1px solid rgba(139, 92, 246, 0.25);
            box-shadow: 0 4px 30px rgba(0, 0, 0, 0.4);
        }

        .navbar-brand {
            font-weight: 800;
            background: linear-gradient(135deg, #a78bfa, #60a5fa);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            letter-spacing: -0.02em;
        }

        .topbar-avatar {
            width: 34px;
            height: 34px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid #7c3aed;
        }

        .topbar-avatar-placeholder {
            width: 34px;
            height: 34px;
            border-radius: 50%;
            background: linear-gradient(135deg, #7c3aed, #2563eb);
            color: #ffffff;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            font-size: 0.85rem;
        }

        .user-greeting {
            font-size: 0.875rem;
            color: #94a3b8;
        }

        .user-greeting b {
            color: #c4b5fd;
        }

        .footer {
            background: rgba(15, 10, 30, 0.8);
            border-top: 1px solid rgba(139, 92, 246, 0.15);
            color: #64748b;
            font-size: 0.85rem;
        }
    </style>

    <sitemesh:write property="head"/>
</head>
<body>

    <!-- Bootstrap Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark navbar-custom sticky-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/waiting">AppDemo</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/home">Trang chủ</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/product">Sản phẩm</a>
                    </li>
                    <c:if test="${sessionScope.account != null && sessionScope.account.roleid == 1}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle text-warning" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Quản trị
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/home">Trang chủ Admin</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/products">Quản lý Sản phẩm</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/category/list">Quản lý Danh mục</a></li>
                            </ul>
                        </li>
                    </c:if>
                </ul>

                <div class="d-flex align-items-center gap-3">
                    <c:choose>
                        <c:when test="${sessionScope.account == null}">
                            <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                            <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/register">Đăng ký</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/user/profile" class="d-flex align-items-center gap-2 text-decoration-none" title="Hồ sơ cá nhân">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.account.avatar}">
                                        <img src="${pageContext.request.contextPath}/image?fname=${sessionScope.account.avatar}"
                                             class="topbar-avatar" alt="Avatar"/>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="topbar-avatar-placeholder">
                                            ${not empty sessionScope.account.fullName ? sessionScope.account.fullName.substring(0,1).toUpperCase() : 'U'}
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                                <span class="user-greeting d-none d-md-inline">Xin chào, <b>${sessionScope.account.fullName}</b></span>
                            </a>
                            <a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/user/profile">Hồ sơ</a>
                            <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <main class="flex-grow-1 w-100">
        <sitemesh:write property="body"/>
    </main>

    <!-- Footer -->
    <footer class="footer py-3 text-center">
        <div class="container">
            <p class="mb-0">&copy; 2026 AppDemo - Java Web Application</p>
        </div>
    </footer>

    <!-- Bootstrap 5.3 JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
