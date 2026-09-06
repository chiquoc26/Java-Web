<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Danh Mục</title>
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
            padding: 0 0 3rem;
            display: flex;
            flex-direction: column;
            align-items: center;
            position: relative;
        }

        body::before {
            content: '';
            position: fixed;
            top: -10%;
            right: -5%;
            width: 500px;
            height: 500px;
            background: radial-gradient(circle, rgba(124,58,237,0.14) 0%, transparent 70%);
            pointer-events: none;
        }

        .container {
            width: 100%;
            max-width: 960px;
            background: rgba(255, 255, 255, 0.04);
            backdrop-filter: blur(20px);
            -webkit-backdrop-filter: blur(20px);
            border: 1px solid rgba(255, 255, 255, 0.09);
            padding: 2rem 2rem 2.5rem;
            border-radius: 20px;
            box-shadow: 0 20px 50px rgba(0, 0, 0, 0.4);
            position: relative;
            z-index: 1;
        }

        .header-section {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.75rem;
            padding-bottom: 1.25rem;
            border-bottom: 1px solid rgba(255, 255, 255, 0.08);
        }

        h1 {
            font-size: 1.6rem;
            font-weight: 800;
            color: #f1f5f9;
            letter-spacing: -0.03em;
        }
        h1 span {
            background: linear-gradient(135deg, #a78bfa, #60a5fa);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 0.6rem 1.25rem;
            border-radius: 10px;
            font-size: 0.875rem;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.2s ease;
            cursor: pointer;
            border: none;
        }
        .btn-primary {
            background: linear-gradient(135deg, #7c3aed, #2563eb);
            color: #ffffff;
            box-shadow: 0 3px 15px rgba(124, 58, 237, 0.4);
        }
        .btn-primary:hover {
            box-shadow: 0 5px 22px rgba(124, 58, 237, 0.6);
            transform: translateY(-1px);
        }
        .btn-edit {
            background: rgba(255, 255, 255, 0.07);
            color: #cbd5e1;
            border: 1px solid rgba(255, 255, 255, 0.12);
            padding: 0.35rem 0.8rem;
            font-size: 0.8rem;
            margin-right: 0.4rem;
        }
        .btn-edit:hover {
            background: rgba(255, 255, 255, 0.13);
            color: #f1f5f9;
        }
        .btn-delete {
            background: rgba(239, 68, 68, 0.15);
            color: #fca5a5;
            border: 1px solid rgba(239, 68, 68, 0.3);
            padding: 0.35rem 0.8rem;
            font-size: 0.8rem;
        }
        .btn-delete:hover {
            background: rgba(239, 68, 68, 0.28);
            color: #fecaca;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            text-align: left;
        }

        th {
            background: rgba(255, 255, 255, 0.04);
            color: #64748b;
            font-weight: 600;
            font-size: 0.75rem;
            text-transform: uppercase;
            letter-spacing: 0.06em;
            padding: 0.875rem 1rem;
            border-bottom: 1px solid rgba(255, 255, 255, 0.08);
        }

        td {
            padding: 0.875rem 1rem;
            border-bottom: 1px solid rgba(255, 255, 255, 0.05);
            color: #cbd5e1;
            font-size: 0.95rem;
            vertical-align: middle;
        }

        tr:last-child td {
            border-bottom: none;
        }

        tr:hover td {
            background: rgba(255, 255, 255, 0.03);
        }

        .id-badge {
            display: inline-block;
            background: rgba(124, 58, 237, 0.2);
            color: #c4b5fd;
            padding: 0.2rem 0.55rem;
            border-radius: 6px;
            font-weight: 700;
            font-size: 0.85rem;
            border: 1px solid rgba(124, 58, 237, 0.3);
        }

        .img-preview {
            width: 72px;
            height: 72px;
            object-fit: cover;
            border-radius: 10px;
            border: 1px solid rgba(255, 255, 255, 0.1);
        }

        .no-img {
            color: #475569;
            font-style: italic;
            font-size: 0.85rem;
        }

        .action-links {
            display: flex;
            align-items: center;
        }

        .empty-state {
            text-align: center;
            padding: 3rem 1rem;
            color: #475569;
        }
        .empty-state .icon {
            font-size: 3rem;
            margin-bottom: 1rem;
            display: block;
        }
    </style>
</head>

<body>
<jsp:include page="/views/topbar.jsp"/>

<div class="container" style="margin-top: 2rem;">
    <div style="margin-bottom: 1.2rem;">
        <a href="${pageContext.request.contextPath}/admin/home" style="display: inline-block; padding: 0.45rem 1rem; border-radius: 8px; background: rgba(255,255,255,0.06); border: 1px solid rgba(255,255,255,0.12); color: #cbd5e1; text-decoration: none; font-size: 0.88rem; font-weight: 600;">
            Quay lại trang chủ
        </a>
    </div>

    <div class="header-section">
        <h1>Danh Sách <span>Danh Mục</span></h1>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/category/add">
            Thêm danh mục
        </a>
    </div>

    <table>
        <thead>
        <tr>
            <th style="width: 10%;">ID</th>
            <th style="width: 43%;">Tên danh mục</th>
            <th style="width: 25%;">Hình ảnh</th>
            <th style="width: 22%;">Hành động</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td><span class="id-badge">${category.cateId}</span></td>
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
                        <a class="btn btn-edit" href="${pageContext.request.contextPath}/admin/category/edit?id=${category.cateId}">
                            Sửa
                        </a>
                        <a class="btn btn-delete" href="${pageContext.request.contextPath}/admin/category/delete?id=${category.cateId}"
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