<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký tài khoản</title>
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
            padding: 2rem 1rem;
            position: relative;
            overflow-x: hidden;
        }

        body::before {
            content: '';
            position: fixed;
            top: -15%;
            right: -10%;
            width: 550px;
            height: 550px;
            background: radial-gradient(circle, rgba(124,58,237,0.22) 0%, transparent 70%);
            animation: blobMove 9s ease-in-out infinite alternate;
            pointer-events: none;
        }
        body::after {
            content: '';
            position: fixed;
            bottom: -15%;
            left: -10%;
            width: 450px;
            height: 450px;
            background: radial-gradient(circle, rgba(37,99,235,0.18) 0%, transparent 70%);
            animation: blobMove 11s ease-in-out infinite alternate-reverse;
            pointer-events: none;
        }
        @keyframes blobMove {
            from { transform: translate(0, 0) scale(1); }
            to   { transform: translate(35px, 25px) scale(1.07); }
        }

        .register-wrapper {
            position: relative;
            z-index: 1;
            width: 100%;
            max-width: 460px;
        }

        .brand-header {
            text-align: center;
            margin-bottom: 1.75rem;
        }
        .brand-header .logo-icon {
            font-size: 2.2rem;
            margin-bottom: 0.4rem;
            display: block;
        }
        .brand-header h1 {
            font-size: 1.6rem;
            font-weight: 800;
            background: linear-gradient(135deg, #a78bfa, #60a5fa);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            letter-spacing: -0.04em;
        }
        .brand-header p {
            font-size: 0.82rem;
            color: #64748b;
            margin-top: 0.2rem;
        }

        .register-container {
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

        .form-group {
            margin-bottom: 1.1rem;
        }

        label {
            display: block;
            font-size: 0.78rem;
            font-weight: 600;
            margin-bottom: 0.4rem;
            color: #94a3b8;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        input {
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
        input::placeholder {
            color: #475569;
        }
        input:focus {
            border-color: rgba(167, 139, 250, 0.6);
            background: rgba(255, 255, 255, 0.09);
            box-shadow: 0 0 0 3px rgba(124, 58, 237, 0.2);
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
            margin-top: 0.5rem;
            margin-bottom: 1rem;
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
            margin: 0.5rem 0 1rem;
        }
    </style>
</head>
<body>
    <div class="register-wrapper">
        <div class="brand-header">
            <span class="logo-icon">⬡</span>
            <h1>AppDemo</h1>
            <p>Tạo tài khoản mới</p>
        </div>

        <div class="register-container">
            <h2>Đăng Ký Tài Khoản</h2>

            <div id="clientError" class="alert-error" style="display: none;"></div>

            <c:if test="${not empty alert}">
                <div class="alert-error">
                    ${alert}
                </div>
            </c:if>

            <form id="registerForm" action="${pageContext.request.contextPath}/register" method="post" onsubmit="return validateRegisterForm(event)">
                <div class="form-group">
                    <label for="username">Tài khoản (*)</label>
                    <input type="text" id="username" name="username" value="${username}" placeholder="4-30 ký tự, chữ, số hoặc gạch dưới..." required />
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu (*)</label>
                    <input type="password" id="password" name="password" placeholder="Tối thiểu 6 ký tự..." required />
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Xác nhận mật khẩu (*)</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu..." required />
                </div>
                <div class="form-group">
                    <label for="fullname">Họ và tên (*)</label>
                    <input type="text" id="fullname" name="fullname" value="${fullname}" placeholder="Nhập họ và tên..." required />
                </div>
                <div class="form-group">
                    <label for="email">Email (*)</label>
                    <input type="email" id="email" name="email" value="${email}" placeholder="example@domain.com..." required />
                </div>
                <div class="form-group">
                    <label for="phone">Số điện thoại</label>
                    <input type="text" id="phone" name="phone" value="${phone}" placeholder="10 chữ số (VD: 0912345678)..." />
                </div>
                <button type="submit">Đăng ký</button>
            </form>

            <hr class="divider">

            <div class="footer-links">
                <a href="${pageContext.request.contextPath}/login">Đã có tài khoản? Đăng nhập</a>
            </div>
        </div>
    </div>

    <script>
        function validateRegisterForm(e) {
            const clientErr = document.getElementById('clientError');
            clientErr.style.display = 'none';
            clientErr.textContent = '';

            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const fullname = document.getElementById('fullname').value.trim();
            const email = document.getElementById('email').value.trim();
            const phone = document.getElementById('phone').value.trim();

            const usernameRegex = /^[a-zA-Z0-9_]{4,30}$/;
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            const phoneRegex = /^(03|05|07|08|09)\d{8}$/;

            let error = '';

            if (!usernameRegex.test(username)) {
                error = 'Tên tài khoản phải từ 4-30 ký tự, chỉ gồm chữ cái, số và dấu gạch dưới.';
            } else if (password.length < 6) {
                error = 'Mật khẩu phải có ít nhất 6 ký tự.';
            } else if (password !== confirmPassword) {
                error = 'Mật khẩu xác nhận không khớp.';
            } else if (fullname.length < 2) {
                error = 'Họ và tên phải có ít nhất 2 ký tự.';
            } else if (!emailRegex.test(email)) {
                error = 'Địa chỉ email không đúng định dạng.';
            } else if (phone.length > 0 && !phoneRegex.test(phone)) {
                error = 'Số điện thoại không hợp lệ (cần đúng 10 số, đầu số VN: 03, 05, 07, 08, 09).';
            }

            if (error) {
                e.preventDefault();
                clientErr.textContent = error;
                clientErr.style.display = 'block';
                return false;
            }
            return true;
        }
    </script>
</body>
</html>
