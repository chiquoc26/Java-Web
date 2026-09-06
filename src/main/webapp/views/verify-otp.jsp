<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác Thực OTP</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', sans-serif; }
        body {
            min-height: 100vh; display: flex; align-items: center; justify-content: center;
            background: #0d0b1e;
            background-image: radial-gradient(circle at 15% 25%, rgba(124,58,237,0.18) 0%, transparent 50%),
                              radial-gradient(circle at 85% 75%, rgba(37,99,235,0.15) 0%, transparent 50%);
        }
        .card {
            background: rgba(255,255,255,0.05); backdrop-filter: blur(20px);
            border: 1px solid rgba(255,255,255,0.1); border-radius: 24px;
            padding: 2.5rem 2rem; width: 100%; max-width: 440px;
            box-shadow: 0 25px 60px rgba(0,0,0,0.5); text-align: center;
        }
        .icon { font-size: 3rem; margin-bottom: 1rem; }
        h1 { font-size: 1.6rem; font-weight: 800; color: #f1f5f9; margin-bottom: 0.5rem; }
        .subtitle { color: #94a3b8; font-size: 0.9rem; margin-bottom: 0.3rem; }
        .email-hint { color: #a78bfa; font-size: 0.85rem; font-weight: 600; margin-bottom: 1.8rem; }

        .otp-inputs { display: flex; gap: 10px; justify-content: center; margin-bottom: 1.5rem; }
        .otp-inputs input {
            width: 52px; height: 60px; text-align: center; font-size: 1.5rem; font-weight: 700;
            background: rgba(255,255,255,0.07); border: 2px solid rgba(255,255,255,0.15);
            border-radius: 12px; color: #f1f5f9; outline: none;
            transition: border-color 0.2s, box-shadow 0.2s;
        }
        .otp-inputs input:focus {
            border-color: #7c3aed;
            box-shadow: 0 0 0 3px rgba(124,58,237,0.25);
        }
        .btn-submit {
            width: 100%; padding: 0.85rem; border: none; border-radius: 12px; cursor: pointer;
            font-size: 1rem; font-weight: 700; color: #fff;
            background: linear-gradient(135deg, #7c3aed, #2563eb);
            transition: opacity 0.2s, transform 0.15s;
        }
        .btn-submit:hover { opacity: 0.9; transform: translateY(-1px); }

        .resend-row { margin-top: 1.2rem; font-size: 0.85rem; color: #64748b; }
        .resend-row a { color: #a78bfa; text-decoration: none; font-weight: 600; }
        .resend-row a:hover { text-decoration: underline; }

        .alert-error {
            background: rgba(239,68,68,0.15); border: 1px solid rgba(239,68,68,0.4);
            color: #fca5a5; border-radius: 10px; padding: 0.75rem 1rem;
            font-size: 0.85rem; margin-bottom: 1.2rem;
        }
        .back-link { display: block; margin-top: 1rem; color: #64748b; font-size: 0.82rem; text-decoration: none; }
        .back-link:hover { color: #a78bfa; }
    </style>
</head>
<body>
<div class="card">
    <h1>Nhập Mã OTP</h1>
    <p class="subtitle">Mã xác thực đã được gửi đến</p>
    <p class="email-hint">${param.email != null ? param.email : requestScope.email}</p>

    <div id="clientError" class="alert-error" style="display: none;"></div>

    <% if (request.getAttribute("alert") != null) { %>
    <div class="alert-error"><%= request.getAttribute("alert") %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/verify-otp" method="post" id="otpForm" onsubmit="return validateOtpForm(event)">
        <input type="hidden" name="email" value="${param.email != null ? param.email : requestScope.email}">
        <input type="hidden" name="type"  value="${param.type}">

        <div class="otp-inputs">
            <input type="text" name="otp1" id="otp1" maxlength="1" autocomplete="off" required>
            <input type="text" name="otp2" id="otp2" maxlength="1" autocomplete="off" required>
            <input type="text" name="otp3" id="otp3" maxlength="1" autocomplete="off" required>
            <input type="text" name="otp4" id="otp4" maxlength="1" autocomplete="off" required>
            <input type="text" name="otp5" id="otp5" maxlength="1" autocomplete="off" required>
            <input type="text" name="otp6" id="otp6" maxlength="1" autocomplete="off" required>
        </div>

        <button type="submit" class="btn-submit">Xác Nhận</button>
    </form>

    <div class="resend-row">
        Không nhận được mã?
        <a href="${pageContext.request.contextPath}/verify-otp?email=${param.email != null ? param.email : requestScope.email}&resend=true">Gửi lại OTP</a>
    </div>
    <a class="back-link" href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
</div>

<script>
    // Tu dong chuyen o khi nhap
    const inputs = document.querySelectorAll('.otp-inputs input');
    inputs.forEach((inp, i) => {
        inp.addEventListener('input', () => {
            if (inp.value.length === 1 && i < inputs.length - 1) inputs[i + 1].focus();
        });
        inp.addEventListener('keydown', e => {
            if (e.key === 'Backspace' && inp.value === '' && i > 0) inputs[i - 1].focus();
        });
        // Chi cho nhap so
        inp.addEventListener('keypress', e => { if (!/[0-9]/.test(e.key)) e.preventDefault(); });
    });
    inputs[0].focus();

    function validateOtpForm(e) {
        const clientErr = document.getElementById('clientError');
        clientErr.style.display = 'none';
        let code = '';
        inputs.forEach(i => code += i.value.trim());
        if (code.length < 6 || !/^\d{6}$/.test(code)) {
            e.preventDefault();
            clientErr.textContent = 'Vui lòng nhập đủ 6 chữ số mã OTP.';
            clientErr.style.display = 'block';
            return false;
        }
        return true;
    }
</script>
</body>
</html>
