<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Danh Mục</title>
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
            padding: 2.5rem 1rem;
            display: flex;
            justify-content: center;
            align-items: center;
            position: relative;
            overflow: hidden;
        }

        body::before {
            content: '';
            position: fixed;
            top: -15%;
            left: -10%;
            width: 550px;
            height: 550px;
            background: radial-gradient(circle, rgba(124,58,237,0.2) 0%, transparent 70%);
            pointer-events: none;
        }
        body::after {
            content: '';
            position: fixed;
            bottom: -15%;
            right: -10%;
            width: 450px;
            height: 450px;
            background: radial-gradient(circle, rgba(37,99,235,0.16) 0%, transparent 70%);
            pointer-events: none;
        }

        .container {
            position: relative;
            z-index: 1;
            width: 100%;
            max-width: 500px;
            background: rgba(255, 255, 255, 0.04);
            backdrop-filter: blur(20px);
            -webkit-backdrop-filter: blur(20px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            padding: 2.25rem 2rem;
            border-radius: 20px;
            box-shadow: 0 25px 60px rgba(0, 0, 0, 0.5);
        }

        h1 {
            font-size: 1.4rem;
            font-weight: 800;
            color: #f1f5f9;
            margin-bottom: 1.5rem;
            border-bottom: 1px solid rgba(255, 255, 255, 0.08);
            padding-bottom: 0.875rem;
            letter-spacing: -0.02em;
        }
        h1 span {
            background: linear-gradient(135deg, #a78bfa, #60a5fa);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
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

        input[type="text"],
        input[type="file"] {
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
        input[type="text"]::placeholder {
            color: #475569;
        }
        input[type="text"]:focus {
            border-color: rgba(167, 139, 250, 0.6);
            background: rgba(255, 255, 255, 0.09);
            box-shadow: 0 0 0 3px rgba(124, 58, 237, 0.2);
        }
        input[type="file"] {
            padding: 0.5rem 0.75rem;
            color: #94a3b8;
            cursor: pointer;
        }
        input[type="file"]::-webkit-file-upload-button {
            background: rgba(124, 58, 237, 0.2);
            border: 1px solid rgba(124, 58, 237, 0.4);
            color: #c4b5fd;
            border-radius: 6px;
            padding: 0.3rem 0.75rem;
            cursor: pointer;
            font-size: 0.82rem;
            font-weight: 600;
            margin-right: 0.75rem;
            transition: all 0.2s ease;
        }
        input[type="file"]::-webkit-file-upload-button:hover {
            background: rgba(124, 58, 237, 0.35);
        }

        .error {
            background-color: rgba(239, 68, 68, 0.15);
            color: #fca5a5;
            padding: 0.75rem 1rem;
            border-radius: 10px;
            margin-bottom: 1.25rem;
            font-size: 0.875rem;
            border: 1px solid rgba(239, 68, 68, 0.3);
        }

        .btn-group {
            display: flex;
            align-items: center;
            gap: 0.875rem;
            margin-top: 1.75rem;
        }

        button[type="submit"] {
            padding: 0.75rem 1.5rem;
            background: linear-gradient(135deg, #7c3aed, #2563eb);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 0.95rem;
            font-weight: 700;
            cursor: pointer;
            transition: all 0.2s ease;
            box-shadow: 0 3px 15px rgba(124, 58, 237, 0.4);
        }
        button[type="submit"]:hover {
            box-shadow: 0 5px 22px rgba(124, 58, 237, 0.6);
            transform: translateY(-1px);
        }
        button[type="submit"]:active {
            transform: scale(0.98);
        }

        .btn-cancel {
            padding: 0.75rem 1.5rem;
            background: rgba(255, 255, 255, 0.06);
            color: #94a3b8;
            text-decoration: none;
            border-radius: 10px;
            font-size: 0.95rem;
            font-weight: 600;
            border: 1px solid rgba(255, 255, 255, 0.1);
            text-align: center;
            transition: all 0.2s ease;
        }
        .btn-cancel:hover {
            background: rgba(255, 255, 255, 0.1);
            color: #e2e8f0;
        }
    </style>
</head>

<body>

<div class="container">
    <h1>Thêm Danh Mục <span>Mới</span></h1>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="error">
        <%= error %>
    </div>
    <%
        }
    %>

    <form method="post"
          action="${pageContext.request.contextPath}/admin/category/add"
          enctype="multipart/form-data">

        <div class="form-group">
            <label for="cateName">Tên danh mục</label>
            <input type="text" id="cateName" name="cateName" value="${cateName}" placeholder="Nhập tên danh mục..." required>
        </div>

        <div class="form-group">
            <label for="icon">Hình ảnh danh mục</label>
            <input type="file" id="icon" name="icon" accept=".jpg,.jpeg,image/jpeg" required>
        </div>

        <div class="btn-group">
            <button type="submit">Thêm danh mục</button>
            <a class="btn-cancel" href="${pageContext.request.contextPath}/admin/category/list">Hủy</a>
        </div>
    </form>
</div>

</body>
</html>