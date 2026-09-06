<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:if test="${latestProducts == null}">
    <c:redirect url="/home"/>
</c:if>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Chủ</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', sans-serif; }
        body { background: #0d0b1e; color: #e2e8f0; min-height: 100vh; position: relative; }
        body::before {
            content: ''; position: fixed; top: 5%; left: -5%; width: 500px; height: 500px;
            background: radial-gradient(circle, rgba(124,58,237,0.14) 0%, transparent 70%); pointer-events: none;
        }
        body::after {
            content: ''; position: fixed; bottom: 5%; right: -5%; width: 400px; height: 400px;
            background: radial-gradient(circle, rgba(37,99,235,0.12) 0%, transparent 70%); pointer-events: none;
        }

        .container { max-width: 960px; margin: 0 auto; padding: 1rem 1rem 2rem; position: relative; z-index: 1; }

        /* Hero */
        .hero { text-align: center; padding: 1.8rem 0 1.2rem; }
        .hero-badge {
            display: inline-flex; align-items: center; gap: 0.4rem;
            background: rgba(16,185,129,0.15); color: #6ee7b7; font-weight: 600;
            padding: 0.35rem 0.9rem; border-radius: 9999px; font-size: 0.75rem;
            margin-bottom: 0.8rem; border: 1px solid rgba(16,185,129,0.3);
            text-transform: uppercase; letter-spacing: 0.04em;
        }
        .hero-badge::before { content: '●'; font-size: 0.6rem; color: #34d399; }
        .hero h1 { font-size: 2rem; font-weight: 800; color: #f1f5f9; line-height: 1.25; margin-bottom: 0.5rem; }
        .hero h1 span { background: linear-gradient(135deg, #a78bfa, #60a5fa); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text; }
        .hero p { color: #64748b; font-size: 0.95rem; }

        /* Section title */
        .section-header { display: flex; align-items: center; justify-content: space-between; margin-bottom: 1.2rem; }
        .section-title { font-size: 1.2rem; font-weight: 700; color: #f1f5f9; }
        .section-title span { color: #a78bfa; }
        .view-all {
            color: #a78bfa; text-decoration: none; font-size: 0.85rem; font-weight: 600;
            border: 1px solid rgba(167,139,250,0.3); padding: 0.35rem 0.85rem; border-radius: 8px;
            transition: background 0.2s;
        }
        .view-all:hover { background: rgba(167,139,250,0.1); }

        /* Product grid */
        .product-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(180px, 210px)); gap: 1.2rem; justify-content: center; }
        .product-card {
            background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.08);
            border-radius: 14px; overflow: hidden; cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s, border-color 0.2s;
            text-decoration: none; color: inherit; display: block;
            width: 100%; max-width: 210px;
        }
        .product-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 12px 30px rgba(124,58,237,0.25);
            border-color: rgba(167,139,250,0.3);
        }
        .product-img {
            width: 100%; height: 180px; object-fit: cover;
            background: linear-gradient(135deg, #1e1b4b, #0f172a);
        }
        .product-img-placeholder {
            width: 100%; height: 180px;
            background: linear-gradient(135deg, rgba(124,58,237,0.2), rgba(37,99,235,0.2));
            display: flex; align-items: center; justify-content: center; font-size: 2.5rem;
        }
        .product-info { padding: 0.8rem 0.9rem; }
        .product-category {
            font-size: 0.7rem; font-weight: 600; color: #a78bfa; text-transform: uppercase;
            letter-spacing: 0.05em; margin-bottom: 0.25rem;
        }
        .product-name { font-size: 0.88rem; font-weight: 600; color: #e2e8f0; margin-bottom: 0.4rem; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
        .product-price { font-size: 0.95rem; font-weight: 700; color: #34d399; }

        /* Empty state */
        .empty-state { text-align: center; padding: 3.5rem 0; color: #475569; }
        .empty-state .emoji { font-size: 3.5rem; margin-bottom: 0.8rem; }
    </style>
</head>
<body>

<div class="container">
    <div class="hero">
        <span class="hero-badge">Chào mừng</span>
        <h1>Khám phá <span>Sản Phẩm</span> Mới Nhất</h1>
        <p>Xem ngay 10 sản phẩm mới nhất được cập nhật</p>
    </div>

    <div class="section-header">
        <div class="section-title">🔥 Mới Nhất <span>(10 sản phẩm)</span></div>
        <a href="${pageContext.request.contextPath}/product" class="view-all">Xem tất cả →</a>
    </div>

    <c:choose>
        <c:when test="${empty latestProducts}">
            <div class="empty-state">
                <div class="emoji">📦</div>
                <p>Chưa có sản phẩm nào. Hãy thêm sản phẩm từ trang quản trị!</p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="product-grid">
                <c:forEach var="p" items="${latestProducts}">
                    <a href="${pageContext.request.contextPath}/product-detail?id=${p.productId}" class="product-card">
                        <c:choose>
                            <c:when test="${not empty p.image}">
                                <img class="product-img"
                                     src="${pageContext.request.contextPath}/image/product/${p.image}"
                                     alt="${p.productName}">
                            </c:when>
                            <c:otherwise>
                                <div class="product-img-placeholder">📦</div>
                            </c:otherwise>
                        </c:choose>
                        <div class="product-info">
                            <div class="product-category">${p.cateName}</div>
                            <div class="product-name">${p.productName}</div>
                            <div class="product-price">
                                <fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>