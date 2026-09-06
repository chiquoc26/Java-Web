<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Danh Mục (JPA)</title>
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
        }
        .container {
            width: 100%;
            max-width: 900px;
            background-color: #ffffff;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05), 0 2px 4px -1px rgba(0, 0, 0, 0.03);
        }
        .header-section {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
            border-bottom: 1px solid #e2e8f0;
            padding-bottom: 1rem;
        }
        h1 {
            font-size: 1.75rem;
            font-weight: 700;
            color: #0f172a;
        }
        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 0.625rem 1.25rem;
            border-radius: 8px;
            font-size: 0.875rem;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.2s ease;
            cursor: pointer;
        }
        .btn-primary {
            background-color: #2563eb;
            color: #ffffff;
        }
        .btn-primary:hover {
            background-color: #1d4ed8;
        }
        .btn-edit {
            background-color: #f1f5f9;
            color: #0f172a;
            border: 1px solid #cbd5e1;
            padding: 0.375rem 0.75rem;
            font-size: 0.8rem;
            margin-right: 0.5rem;
        }
        .btn-edit:hover {
            background-color: #e2e8f0;
        }
        .btn-delete {
            background-color: #fee2e2;
            color: #b91c1c;
            border: 1px solid #fca5a5;
            padding: 0.375rem 0.75rem;
            font-size: 0.8rem;
        }
        .btn-delete:hover {
            background-color: #fca5a5;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            text-align: left;
            margin-top: 1rem;
        }
        th {
            background-color: #f8fafc;
            color: #64748b;
            font-weight: 600;
            font-size: 0.875rem;
            text-transform: uppercase;
            padding: 1rem;
            border-bottom: 2px solid #e2e8f0;
        }
        td {
            padding: 1rem;
            border-bottom: 1px solid #e2e8f0;
            color: #334155;
            font-size: 0.95rem;
            vertical-align: middle;
        }
        tr:hover td {
            background-color: #f8fafc;
        }
        .img-preview {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 8px;
            border: 1px solid #e2e8f0;
        }
        .no-img {
            color: #94a3b8;
            font-style: italic;
            font-size: 0.875rem;
        }
        .action-links {
            display: flex;
            align-items: center;
        }
    </style>
</head>

<body>
<div class="container">
    <div style="margin-bottom: 1.2rem;">
        <a href="${pageContext.request.contextPath}/admin/home" style="display: inline-block; padding: 0.45rem 1rem; border-radius: 8px; background: #e2e8f0; color: #334155; text-decoration: none; font-size: 0.88rem; font-weight: 600;">
            Quay lại trang chủ
        </a>
    </div>

    <div class="header-section">
        <h1>Danh Sách Danh Mục (JPA)</h1>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/jpa/category/add">
            Thêm danh mục
        </a>
    </div>

    <table>
        <thead>
        <tr>
            <th style="width: 10%;">ID</th>
            <th style="width: 45%;">Tên danh mục</th>
            <th style="width: 25%;">Hình ảnh</th>
            <th style="width: 20%;">Hành động</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td><strong>${category.cateId}</strong></td>
                <td>${category.cateName}</td>
                <td>
                    <c:if test="${not empty category.icons}">
                        <img
                            src="${pageContext.request.contextPath}/image?fname=${category.icons}"
                            class="img-preview"
                            alt="${category.cateName}"
                        >
                    </c:if>
                    <c:if test="${empty category.icons}">
                        <span class="no-img">Không có ảnh</span>
                    </c:if>
                </td>
                <td>
                    <div class="action-links">
                        <a class="btn btn-edit" href="${pageContext.request.contextPath}/admin/jpa/category/edit?id=${category.cateId}">
                            Sửa
                        </a>
                        <a class="btn btn-delete" href="${pageContext.request.contextPath}/admin/jpa/category/delete?id=${category.cateId}"
                           onclick="return confirm('Bạn có chắc muốn xóa Category này không?');">
                            Xóa
                        </a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
