<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên Mật Khẩu</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', sans-serif; }
        body {
            min-height: 100vh; display: flex; align-items: center; justify-content: center;
            background: #0d0b1e;
            background-image: radial-gradient(circle at 20% 30%, rgba(124,58,237,0.18) 0%, transparent 50%),
                              radial-gradient(circle at 80% 70%, rgba(16,185,129,0.12) 0%, transparent 50%);
        }
        .card {
            background: rgba(255,255,255,0.05); backdrop-filter: blur(20px);
            border: 1px solid rgba(255,255,255,0.1); border-radius: 24px;
            padding: 2.5rem 2rem; width: 100%; max-width: 420px;
            box-shadow: 0 25px 60px rgba(0,0,0,0.5); text-align: center;
        }
        .icon { font-size: 3rem; margin-bottom: 1rem; }
        h1 { font-size: 1.6rem; font-weight: 800; color: #f1f5f9; margin-bottom: 0.5rem; }
        .subtitle { color: #94a3b8; font-size: 0.88rem; margin-bottom: 1.8rem; line-height: 1.6; }
        .form-group { text-align: left; margin-bottom: 1.2rem; }
        label { display: block; font-size: 0.85rem; font-weight: 600; color: #cbd5e1; margin-bottom: 0.4rem; }
        input[type="email"] {
            width: 100%; padding: 0.8rem 1rem; border-radius: 10px;
            border: 1.5px solid rgba(255,255,255,0.15);
            background: rgba(255,255,255,0.07); color: #f1f5f9; font-size: 0.95rem; outline: none;
            transition: border-color 0.2s;
        }
        input[type="email"]:focus { border-color: #7c3aed; }
        .btn-submit {
            width: 100%; padding: 0.85rem; border: none; border-radius: 12px;
            cursor: pointer; font-size: 1rem; font-weight: 700; color: #fff;
            background: linear-gradient(135deg, #7c3aed, #2563eb);
            transition: opacity 0.2s, transform 0.15s;
        }
        .btn-submit:hover { opacity: 0.9; transform: translateY(-1px); }
        .alert-error {
            background: rgba(239,68,68,0.15); border: 1px solid rgba(239,68,68,0.4);
            color: #fca5a5; border-radius: 10px; padding: 0.75rem 1rem;
            font-size: 0.85rem; margin-bottom: 1.2rem;
        }
        .back-link { display: block; margin-top: 1.2rem; color: #64748b; font-size: 0.82rem; text-decoration: none; }
        .back-link:hover { color: #a78bfa; }
    </style>
</head>
<body>
<div class="card">
    <div class="icon">🔑</div>
    <h1>Quên Mật Khẩu</h1>
    <p class="subtitle">Nhập email đăng ký của bạn.<br>Chúng tôi sẽ gửi mã OTP để đặt lại mật khẩu.</p>

    <% if (request.getAttribute("alert") != null) { %>
    <div class="alert-error"><%= request.getAttribute("alert") %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/forgot-password" method="post">
        <div class="form-group">
            <label for="email">Địa chỉ Email</label>
            <input type="email" id="email" name="email" placeholder="example@gmail.com" required autofocus>
        </div>
        <button type="submit" class="btn-submit">Gửi Mã OTP</button>
    </form>
    <a class="back-link" href="${pageContext.request.contextPath}/login">← Quay lại đăng nhập</a>
</div>
</body>
</html>
