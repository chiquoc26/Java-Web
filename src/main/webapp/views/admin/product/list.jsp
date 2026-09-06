<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Sản Phẩm – Admin</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', sans-serif; }
        body { background: #0d0b1e; color: #e2e8f0; min-height: 100vh; }
        .container { max-width: 1200px; margin: 0 auto; padding: 2rem 1.5rem; }

        .btn-back-link {
            display: inline-flex;
            align-items: center;
            gap: 0.4rem;
            color: #a78bfa;
            text-decoration: none;
            font-size: 0.88rem;
            font-weight: 600;
            padding: 0.4rem 0.8rem;
            border-radius: 8px;
            background: rgba(167, 139, 250, 0.1);
            border: 1px solid rgba(167, 139, 250, 0.25);
            transition: all 0.2s;
            margin-bottom: 1.2rem;
        }
        .btn-back-link:hover {
            background: rgba(167, 139, 250, 0.2);
            color: #c4b5fd;
        }

        .header-row { display: flex; align-items: center; justify-content: space-between; margin-bottom: 1.5rem; flex-wrap: wrap; gap: 1rem; }
        h1 { font-size: 1.6rem; font-weight: 800; color: #f1f5f9; }
        h1 span { background: linear-gradient(135deg, #a78bfa, #60a5fa); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text; }

        .btn-add {
            display: inline-flex; align-items: center; gap: 0.4rem;
            background: linear-gradient(135deg, #7c3aed, #2563eb); color: #fff;
            padding: 0.65rem 1.2rem; border-radius: 10px; text-decoration: none;
            font-size: 0.9rem; font-weight: 700; transition: opacity 0.2s;
        }
        .btn-add:hover { opacity: 0.88; }

        .table-wrap { background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.08); border-radius: 16px; overflow: hidden; }
        table { width: 100%; border-collapse: collapse; }
        thead { background: rgba(124,58,237,0.15); }
        thead th { padding: 0.9rem 1rem; text-align: left; font-size: 0.8rem; font-weight: 700; color: #a78bfa; text-transform: uppercase; letter-spacing: 0.05em; }
        tbody tr { border-top: 1px solid rgba(255,255,255,0.05); transition: background 0.15s; }
        tbody tr:hover { background: rgba(255,255,255,0.03); }
        td { padding: 0.85rem 1rem; font-size: 0.88rem; color: #cbd5e1; vertical-align: middle; }

        .img-thumb { width: 48px; height: 48px; object-fit: cover; border-radius: 8px; border: 1px solid rgba(255,255,255,0.1); }
        .no-img-thumb { width: 48px; height: 48px; background: rgba(124,58,237,0.2); border-radius: 8px; display: flex; align-items: center; justify-content: center; font-size: 1.3rem; }

        .badge { display: inline-block; padding: 0.25rem 0.6rem; border-radius: 9999px; font-size: 0.72rem; font-weight: 700; }
        .badge-purple { background: rgba(124,58,237,0.2); color: #a78bfa; }

        .btn-edit, .btn-delete {
            padding: 0.35rem 0.8rem; border-radius: 8px; text-decoration: none;
            font-size: 0.8rem; font-weight: 600; transition: opacity 0.2s;
        }
        .btn-edit { background: rgba(37,99,235,0.2); color: #60a5fa; border: 1px solid rgba(37,99,235,0.3); }
        .btn-delete { background: rgba(239,68,68,0.15); color: #f87171; border: 1px solid rgba(239,68,68,0.3); margin-left: 0.3rem; }
        .btn-edit:hover, .btn-delete:hover { opacity: 0.8; }

        .price-cell { color: #34d399; font-weight: 700; }
        .empty-row td { text-align: center; color: #475569; padding: 3rem; }
    </style>
</head>
<body>

<jsp:include page="/views/topbar.jsp"/>

<div class="container">
    <a href="${pageContext.request.contextPath}/admin/home" class="btn-back-link">
        Về trang chủ Admin
    </a>

    <div class="header-row">
        <h1>Quản Lý <span>Sản Phẩm</span></h1>
        <a href="${pageContext.request.contextPath}/admin/products?act=add" class="btn-add">Thêm sản phẩm</a>
    </div>

    <div class="table-wrap">
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Ảnh</th>
                    <th>Tên sản phẩm</th>
                    <th>Danh mục</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty products}">
                        <tr class="empty-row"><td colspan="7">Chưa có sản phẩm nào</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="p" items="${products}" varStatus="st">
                            <tr>
                                <td>${st.index + 1}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty p.image}">
                                            <img class="img-thumb"
                                                 src="${pageContext.request.contextPath}/image/product/${p.image}"
                                                 alt="${p.productName}">
                                        </c:when>
                                        <c:otherwise><div class="no-img-thumb">-</div></c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${p.productName}</td>
                                <td><span class="badge badge-purple">${p.cateName}</span></td>
                                <td class="price-cell">
                                    <fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                                </td>
                                <td>${p.quantity}</td>
                                <td>
                                    <a class="btn-edit" href="${pageContext.request.contextPath}/admin/products?act=edit&id=${p.productId}">Sửa</a>
                                    <a class="btn-delete" href="${pageContext.request.contextPath}/admin/products?act=delete&id=${p.productId}"
                                       onclick="return confirm('Xóa sản phẩm \'${p.productName}\'?')">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
