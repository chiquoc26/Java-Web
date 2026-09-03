<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Danh Mục (JPA)</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Inter', sans-serif;
        }
        body {
            background-color: #f8fafc;
            color: #1e293b;
            padding: 2.5rem;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            width: 100%;
            max-width: 500px;
            background-color: #ffffff;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05), 0 2px 4px -1px rgba(0, 0, 0, 0.03);
        }
        h1 {
            font-size: 1.5rem;
            font-weight: 700;
            color: #0f172a;
            margin-bottom: 1.5rem;
            border-bottom: 1px solid #e2e8f0;
            padding-bottom: 0.75rem;
        }
        .form-group {
            margin-bottom: 1.25rem;
        }
        label {
            display: block;
            font-size: 0.875rem;
            font-weight: 600;
            margin-bottom: 0.5rem;
            color: #475569;
        }
        input[type="text"],
        input[type="file"] {
            width: 100%;
            padding: 0.75rem 1rem;
            border: 1px solid #cbd5e1;
            border-radius: 8px;
            font-size: 0.95rem;
            outline: none;
            transition: all 0.2s ease;
        }
        input[type="file"] {
            padding: 0.5rem;
            background-color: #f8fafc;
        }
        input[type="text"]:focus {
            border-color: #2563eb;
            box-shadow: 0 0 0 4px rgba(37, 99, 235, 0.15);
        }
        .error {
            background-color: #fee2e2;
            color: #b91c1c;
            padding: 0.75rem 1rem;
            border-radius: 8px;
            margin-bottom: 1.25rem;
            font-size: 0.875rem;
            border: 1px solid #fca5a5;
        }
        .btn-group {
            display: flex;
            align-items: center;
            gap: 1rem;
            margin-top: 1.5rem;
        }
        button {
            padding: 0.75rem 1.5rem;
            background-color: #2563eb;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 0.95rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.2s ease;
        }
        button:hover {
            background-color: #1d4ed8;
        }
        button:active {
            transform: scale(0.98);
        }
        .btn-cancel {
            padding: 0.75rem 1.5rem;
            background-color: #f1f5f9;
            color: #475569;
            text-decoration: none;
            border-radius: 8px;
            font-size: 0.95rem;
            font-weight: 600;
            border: 1px solid #cbd5e1;
            text-align: center;
            transition: all 0.2s ease;
        }
        .btn-cancel:hover {
            background-color: #e2e8f0;
            color: #0f172a;
        }
    </style>
</head>

<body>

<div class="container">
    <h1>Thêm Danh Mục Mới (JPA)</h1>

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
          action="${pageContext.request.contextPath}/admin/jpa/category/add"
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
            <a class="btn-cancel" href="${pageContext.request.contextPath}/admin/jpa/categories">Hủy</a>
        </div>
    </form>
</div>

</body>
</html>
