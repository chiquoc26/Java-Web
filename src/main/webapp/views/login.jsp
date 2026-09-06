<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Inter', sans-serif;
        }

        body {
            background: #0d0b1e;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            color: #e2e8f0;
            position: relative;
            overflow: hidden;
        }

        /* Animated background blobs */
        body::before {
            content: '';
            position: fixed;
            top: -20%;
            left: -10%;
            width: 600px;
            height: 600px;
            background: radial-gradient(circle, rgba(124,58,237,0.25) 0%, transparent 70%);
            animation: blobMove 8s ease-in-out infinite alternate;
            pointer-events: none;
        }
        body::after {
            content: '';
            position: fixed;
            bottom: -20%;
            right: -10%;
            width: 500px;
            height: 500px;
            background: radial-gradient(circle, rgba(37,99,235,0.2) 0%, transparent 70%);
            animation: blobMove 10s ease-in-out infinite alternate-reverse;
            pointer-events: none;
        }
        @keyframes blobMove {
            from { transform: translate(0, 0) scale(1); }
            to   { transform: translate(40px, 30px) scale(1.08); }
        }

        .login-wrapper {
            position: relative;
            z-index: 1;
            width: 100%;
            max-width: 420px;
            padding: 1rem;
        }

        .brand-header {
            text-align: center;
            margin-bottom: 1.75rem;
        }
        .brand-header .logo-icon {
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
            display: block;
        }
        .brand-header h1 {
            font-size: 1.75rem;
            font-weight: 800;
            background: linear-gradient(135deg, #a78bfa, #60a5fa);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            letter-spacing: -0.04em;
        }
        .brand-header p {
            font-size: 0.85rem;
            color: #64748b;
            margin-top: 0.25rem;
        }

        .login-container {
            background: rgba(255, 255, 255, 0.04);
            backdrop-filter: blur(20px);
            -webkit-backdrop-filter: blur(20px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            padding: 2.25rem 2rem;
            border-radius: 20px;
            box-shadow: 0 25px 60px rgba(0, 0, 0, 0.5);
        }

        h2 {
            font-weight: 700;
            font-size: 1.4rem;
            margin-bottom: 1.5rem;
            color: #f1f5f9;
            text-align: center;
        }

        .alert-error {
            background-color: rgba(239, 68, 68, 0.15);
            color: #fca5a5;
            padding: 0.75rem 1rem;
            border-radius: 10px;
            margin-bottom: 1.25rem;
            font-size: 0.875rem;
            border: 1px solid rgba(239, 68, 68, 0.3);
            text-align: center;
        }
        .alert-success {
            background-color: rgba(16, 185, 129, 0.15);
            color: #6ee7b7;
            padding: 0.75rem 1rem;
            border-radius: 10px;
            margin-bottom: 1.25rem;
            font-size: 0.875rem;
            border: 1px solid rgba(16, 185, 129, 0.3);
            text-align: center;
        }

        .form-group {
            margin-bottom: 1.1rem;
        }

        label {
            display: block;
            font-size: 0.8rem;
            font-weight: 600;
            margin-bottom: 0.4rem;
            color: #94a3b8;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 0.75rem 1rem;
            background: rgba(255, 255, 255, 0.06);
            border: 1px solid rgba(255, 255, 255, 0.12);
            border-radius: 10px;
            font-size: 0.95rem;
            outline: none;
            color: #f1f5f9;
            transition: all 0.2s ease;
        }
        input[type="text"]::placeholder,
        input[type="password"]::placeholder {
            color: #475569;
        }
        input[type="text"]:focus,
        input[type="password"]:focus {
            border-color: rgba(167, 139, 250, 0.6);
            background: rgba(255, 255, 255, 0.09);
            box-shadow: 0 0 0 3px rgba(124, 58, 237, 0.2);
        }

        .checkbox-group {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            margin-bottom: 1.5rem;
        }
        .checkbox-group input[type="checkbox"] {
            width: 16px;
            height: 16px;
            cursor: pointer;
            accent-color: #7c3aed;
        }
        .checkbox-group label {
            margin-bottom: 0;
            font-weight: 500;
            cursor: pointer;
            text-transform: none;
            letter-spacing: normal;
            font-size: 0.875rem;
            color: #64748b;
        }

        button[type="submit"] {
            width: 100%;
            padding: 0.8rem;
            background: linear-gradient(135deg, #7c3aed, #2563eb);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 1rem;
            font-weight: 700;
            cursor: pointer;
            transition: all 0.2s ease;
            box-shadow: 0 4px 20px rgba(124, 58, 237, 0.4);
            letter-spacing: 0.02em;
        }
        button[type="submit"]:hover {
            box-shadow: 0 6px 28px rgba(124, 58, 237, 0.6);
            transform: translateY(-1px);
        }
        button[type="submit"]:active {
            transform: scale(0.98);
        }

        .footer-links {
            margin-top: 1.5rem;
            text-align: center;
        }
        .footer-links a {
            color: #818cf8;
            text-decoration: none;
            font-size: 0.875rem;
            font-weight: 500;
            transition: color 0.2s ease;
        }
        .footer-links a:hover {
            color: #a78bfa;
            text-decoration: underline;
        }

        .divider {
            border: none;
            border-top: 1px solid rgba(255,255,255,0.07);
            margin: 1.25rem 0;
        }
    </style>
</head>
<body>
    <div class="login-wrapper">
        <div class="brand-header">
            <span class="logo-icon">⬡</span>
            <h1>AppDemo</h1>
            <p>Hệ thống quản lý thông minh</p>
        </div>

        <div class="login-container">
            <h2>Đăng Nhập</h2>

            <div id="clientError" class="alert-error" style="display: none;"></div>

            <c:if test="${not empty alert}">
                <div class="${alert.contains('thành công') ? 'alert-success' : 'alert-error'}">
                    ${alert}
                </div>
            </c:if>
            <%-- Thong bao dat lai mat khau thanh cong --%>
            <% if ("success".equals(request.getParameter("reset"))) { %>
            <div class="alert-success">Mat khau da duoc dat lai. Vui long dang nhap!</div>
            <% } %>
            <% if ("true".equals(request.getParameter("activated"))) { %>
            <div class="alert-success">Kich hoat tai khoan thanh cong! Vui long dang nhap.</div>
            <% } %>

            <form id="loginForm" action="${pageContext.request.contextPath}/login" method="post" onsubmit="return validateLoginForm(event)">
                <div class="form-group">
                    <label for="username">Tài khoản</label>
                    <input type="text" id="username" name="username" value="${username}" placeholder="Nhập tài khoản..." required autocomplete="username" />
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu</label>
                    <input type="password" id="password" name="password" placeholder="Nhập mật khẩu..." required autocomplete="current-password" />
                </div>
                <div class="checkbox-group">
                    <input type="checkbox" name="remember" id="remember" />
                    <label for="remember">Nhớ tài khoản</label>
                </div>
                <button type="submit">Đăng nhập</button>
            </form>

            <hr class="divider">

            <div class="footer-links">
                <a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a>
                &nbsp;·&nbsp;
                <a href="${pageContext.request.contextPath}/register">Chưa có tài khoản? Đăng ký ngay</a>
            </div>
        </div>
    </div>

    <script>
        function validateLoginForm(e) {
            const clientErr = document.getElementById('clientError');
            clientErr.style.display = 'none';
            clientErr.textContent = '';

            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value.trim();

            if (!username || !password) {
                e.preventDefault();
                clientErr.textContent = 'Vui lòng nhập đầy đủ tài khoản và mật khẩu.';
                clientErr.style.display = 'block';
                return false;
            }
            return true;
        }
    </script>
</body>
</html>