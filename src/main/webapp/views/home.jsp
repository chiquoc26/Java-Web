<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Chủ User</title>
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
            color: #e2e8f0;
            min-height: 100vh;
            position: relative;
        }

        body::before {
            content: '';
            position: fixed;
            top: 10%;
            right: -5%;
            width: 500px;
            height: 500px;
            background: radial-gradient(circle, rgba(16,185,129,0.12) 0%, transparent 70%);
            pointer-events: none;
        }
        body::after {
            content: '';
            position: fixed;
            bottom: 5%;
            left: -5%;
            width: 400px;
            height: 400px;
            background: radial-gradient(circle, rgba(124,58,237,0.15) 0%, transparent 70%);
            pointer-events: none;
        }

        .main-container {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 3rem 1.5rem;
            min-height: calc(100vh - 70px);
            position: relative;
            z-index: 1;
        }

        .welcome-card {
            background: rgba(255, 255, 255, 0.04);
            backdrop-filter: blur(20px);
            -webkit-backdrop-filter: blur(20px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            padding: 3rem 2.5rem;
            border-radius: 24px;
            box-shadow: 0 25px 60px rgba(0, 0, 0, 0.4);
            text-align: center;
            max-width: 580px;
            width: 100%;
        }

        .status-badge {
            display: inline-flex;
            align-items: center;
            gap: 0.4rem;
            background: rgba(16, 185, 129, 0.15);
            color: #6ee7b7;
            font-weight: 600;
            padding: 0.4rem 1rem;
            border-radius: 9999px;
            font-size: 0.8rem;
            margin-bottom: 1.75rem;
            border: 1px solid rgba(16, 185, 129, 0.3);
            letter-spacing: 0.04em;
            text-transform: uppercase;
        }
        .status-badge::before {
            content: '●';
            font-size: 0.6rem;
            color: #34d399;
        }

        .welcome-card h1 {
            font-size: 2.25rem;
            font-weight: 800;
            color: #f1f5f9;
            margin-bottom: 1rem;
            line-height: 1.2;
            letter-spacing: -0.03em;
        }
        .welcome-card h1 span {
            background: linear-gradient(135deg, #a78bfa, #60a5fa);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .welcome-card p {
            font-size: 1rem;
            color: #64748b;
            line-height: 1.7;
        }
    </style>
</head>
<body>
    <jsp:include page="topbar.jsp"/>

    <div class="main-container">
        <div class="welcome-card">
            <span class="status-badge">Thành viên Thường</span>
            <h1>Chào mừng bạn đến với <span>hệ thống!</span></h1>
            <p>Trải nghiệm dịch vụ và các tiện ích dành riêng cho thành viên đã đăng ký tài khoản.</p>
        </div>
    </div>
</body>
</html>