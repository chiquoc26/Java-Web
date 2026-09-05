<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt Lại Mật Khẩu</title>
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
            padding: 2.5rem 2rem; width: 100%; max-width: 460px;
            box-shadow: 0 25px 60px rgba(0,0,0,0.5);
        }
        .icon { font-size: 2.5rem; text-align: center; margin-bottom: 0.8rem; }
        h1 { font-size: 1.5rem; font-weight: 800; color: #f1f5f9; text-align: center; margin-bottom: 0.4rem; }
        .subtitle { color: #94a3b8; font-size: 0.85rem; text-align: center; margin-bottom: 1.5rem; }
        .email-hint { color: #a78bfa; font-weight: 600; }

        .otp-inputs { display: flex; gap: 8px; justify-content: center; margin-bottom: 1.5rem; }
        .otp-inputs input {
            width: 48px; height: 56px; text-align: center; font-size: 1.4rem; font-weight: 700;
            background: rgba(255,255,255,0.07); border: 2px solid rgba(255,255,255,0.15);
            border-radius: 10px; color: #f1f5f9; outline: none; transition: border-color 0.2s;
        }
        .otp-inputs input:focus { border-color: #7c3aed; box-shadow: 0 0 0 3px rgba(124,58,237,0.2); }

        .form-group { margin-bottom: 1rem; }
        label { display: block; font-size: 0.83rem; font-weight: 600; color: #cbd5e1; margin-bottom: 0.35rem; }
        input[type="password"] {
            width: 100%; padding: 0.75rem 1rem; border-radius: 10px;
            border: 1.5px solid rgba(255,255,255,0.15);
            background: rgba(255,255,255,0.07); color: #f1f5f9; font-size: 0.95rem; outline: none;
            transition: border-color 0.2s;
        }
        input[type="password"]:focus { border-color: #7c3aed; }
        .btn-submit {
            width: 100%; padding: 0.85rem; border: none; border-radius: 12px;
            cursor: pointer; font-size: 1rem; font-weight: 700; color: #fff;
            background: linear-gradient(135deg, #7c3aed, #2563eb);
            margin-top: 0.5rem; transition: opacity 0.2s, transform 0.15s;
        }
        .btn-submit:hover { opacity: 0.9; transform: translateY(-1px); }
        .alert-error {
            background: rgba(239,68,68,0.15); border: 1px solid rgba(239,68,68,0.4);
            color: #fca5a5; border-radius: 10px; padding: 0.75rem 1rem;
            font-size: 0.85rem; margin-bottom: 1rem;
        }
        .section-label {
            font-size: 0.8rem; font-weight: 700; color: #64748b; text-transform: uppercase;
            letter-spacing: 0.05em; margin-bottom: 0.6rem; margin-top: 0.3rem;
        }
    </style>
</head>
<body>
<div class="card">
    <div class="icon">🛡️</div>
    <h1>Đặt Lại Mật Khẩu</h1>
    <p class="subtitle">Nhập OTP đã gửi đến <span class="email-hint">${param.email != null ? param.email : requestScope.email}</span></p>

    <% if (request.getAttribute("alert") != null) { %>
    <div class="alert-error"><%= request.getAttribute("alert") %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/verify-reset-otp" method="post">
        <input type="hidden" name="email" value="${param.email != null ? param.email : requestScope.email}">

        <p class="section-label">Mã OTP (6 số)</p>
        <div class="otp-inputs">
            <input type="text" name="otp1" id="otp1" maxlength="1" autocomplete="off" required>
            <input type="text" name="otp2" id="otp2" maxlength="1" autocomplete="off" required>
            <input type="text" name="otp3" id="otp3" maxlength="1" autocomplete="off" required>
            <input type="text" name="otp4" id="otp4" maxlength="1" autocomplete="off" required>
            <input type="text" name="otp5" id="otp5" maxlength="1" autocomplete="off" required>
            <input type="text" name="otp6" id="otp6" maxlength="1" autocomplete="off" required>
        </div>

        <p class="section-label">Mật khẩu mới</p>
        <div class="form-group">
            <input type="password" name="newPassword" placeholder="Mật khẩu mới (ít nhất 6 ký tự)" minlength="6" required>
        </div>
        <div class="form-group">
            <input type="password" name="confirmPassword" placeholder="Nhập lại mật khẩu mới" minlength="6" required>
        </div>

        <button type="submit" class="btn-submit">Đặt Lại Mật Khẩu</button>
    </form>
</div>

<script>
    const inputs = document.querySelectorAll('.otp-inputs input');
    inputs.forEach((inp, i) => {
        inp.addEventListener('input', () => { if (inp.value.length === 1 && i < inputs.length - 1) inputs[i+1].focus(); });
        inp.addEventListener('keydown', e => { if (e.key === 'Backspace' && inp.value === '' && i > 0) inputs[i-1].focus(); });
        inp.addEventListener('keypress', e => { if (!/[0-9]/.test(e.key)) e.preventDefault(); });
    });
    inputs[0].focus();
</script>
</body>
</html>
