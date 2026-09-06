<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.productName} – Chi Tiết Sản Phẩm</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', sans-serif; }
        body { background: #0d0b1e; color: #e2e8f0; min-height: 100vh; }
        body::before { content: ''; position: fixed; top: 10%; right: -5%; width: 500px; height: 500px; background: radial-gradient(circle, rgba(124,58,237,0.12) 0%, transparent 70%); pointer-events: none; }

        .container { max-width: 1100px; margin: 0 auto; padding: 2rem 1.5rem; position: relative; z-index: 1; }
        .breadcrumb { font-size: 0.82rem; color: #475569; margin-bottom: 1.5rem; }
        .breadcrumb a { color: #a78bfa; text-decoration: none; }
        .breadcrumb a:hover { text-decoration: underline; }

        .detail-layout { display: grid; grid-template-columns: 1fr 1fr; gap: 3rem; align-items: start; }
        @media (max-width: 700px) { .detail-layout { grid-template-columns: 1fr; } }

        /* Ảnh */
        .product-image-wrap {
            background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.08);
            border-radius: 20px; overflow: hidden; aspect-ratio: 1/1;
            display: flex; align-items: center; justify-content: center;
        }
        .product-image-wrap img { width: 100%; height: 100%; object-fit: cover; }
        .product-image-wrap .no-img { font-size: 6rem; }

        /* Info */
        .product-category-badge {
            display: inline-block; background: rgba(124,58,237,0.2); color: #a78bfa;
            font-size: 0.75rem; font-weight: 700; padding: 0.3rem 0.8rem;
            border-radius: 9999px; border: 1px solid rgba(124,58,237,0.3);
            text-transform: uppercase; letter-spacing: 0.05em; margin-bottom: 1rem;
        }
        .product-name { font-size: 2rem; font-weight: 800; color: #f1f5f9; line-height: 1.2; margin-bottom: 1rem; }
        .product-price { font-size: 1.8rem; font-weight: 800; color: #34d399; margin-bottom: 1.5rem; }

        .info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; margin-bottom: 1.5rem; }
        .info-item { background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.08); border-radius: 12px; padding: 0.8rem 1rem; }
        .info-item .label { font-size: 0.75rem; color: #64748b; font-weight: 600; text-transform: uppercase; letter-spacing: 0.04em; margin-bottom: 0.2rem; }
        .info-item .value { font-size: 1rem; font-weight: 700; color: #e2e8f0; }

        .description { background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.07); border-radius: 12px; padding: 1.2rem; margin-bottom: 1.5rem; }
        .description h3 { font-size: 0.85rem; color: #64748b; font-weight: 700; text-transform: uppercase; letter-spacing: 0.05em; margin-bottom: 0.6rem; }
        .description p { font-size: 0.95rem; color: #94a3b8; line-height: 1.7; }

        .btn-back {
            display: inline-flex; align-items: center; gap: 0.5rem;
            background: rgba(255,255,255,0.06); border: 1px solid rgba(255,255,255,0.12);
            color: #cbd5e1; padding: 0.7rem 1.2rem; border-radius: 10px;
            text-decoration: none; font-size: 0.9rem; font-weight: 600;
            transition: background 0.2s; margin-bottom: 2rem;
        }
        .btn-back:hover { background: rgba(124,58,237,0.2); color: #a78bfa; }
    </style>
</head>
<body>

<div class="container">
    <a href="${pageContext.request.contextPath}/product" class="btn-back">← Quay lại danh sách</a>

    <div class="breadcrumb">
        <a href="${pageContext.request.contextPath}/home">Trang chủ</a> /
        <a href="${pageContext.request.contextPath}/product">Sản phẩm</a> /
        ${product.productName}
    </div>

    <div class="detail-layout">
        <!-- Ảnh sản phẩm -->
        <div class="product-image-wrap">
            <c:choose>
                <c:when test="${not empty product.image}">
                    <img src="${pageContext.request.contextPath}/image/product/${product.image}"
                         alt="${product.productName}">
                </c:when>
                <c:otherwise>
                    <span class="no-img">📦</span>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Thông tin sản phẩm -->
        <div>
            <span class="product-category-badge">${product.cateName}</span>
            <h1 class="product-name">${product.productName}</h1>
            <div class="product-price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫" groupingUsed="true"/>
            </div>

            <div class="info-grid">
                <div class="info-item">
                    <div class="label">Số lượng còn</div>
                    <div class="value">${product.quantity} sp</div>
                </div>
                <div class="info-item">
                    <div class="label">Danh mục</div>
                    <div class="value">${product.cateName}</div>
                </div>
            </div>

            <c:if test="${not empty product.description}">
                <div class="description">
                    <h3>Mô tả sản phẩm</h3>
                    <p>${product.description}</p>
                </div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
