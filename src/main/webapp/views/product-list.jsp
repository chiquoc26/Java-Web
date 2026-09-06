<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tất Cả Sản Phẩm</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', sans-serif; }
        body { background: #0d0b1e; color: #e2e8f0; min-height: 100vh; }
        body::before { content: ''; position: fixed; top: 0; left: -10%; width: 500px; height: 500px; background: radial-gradient(circle, rgba(124,58,237,0.12) 0%, transparent 70%); pointer-events: none; }

        .container { max-width: 960px; margin: 0 auto; padding: 1.5rem 1rem 2.5rem; position: relative; z-index: 1; }
        .page-header { margin-bottom: 1.5rem; }
        .page-header h1 { font-size: 1.8rem; font-weight: 800; color: #f1f5f9; }
        .page-header h1 span { background: linear-gradient(135deg, #a78bfa, #60a5fa); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text; }
        .page-header p { color: #64748b; margin-top: 0.3rem; font-size: 0.9rem; }

        .product-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(180px, 210px)); gap: 1.2rem; justify-content: center; margin-bottom: 2.5rem; }
        .product-card {
            background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.08);
            border-radius: 14px; overflow: hidden; cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s, border-color 0.2s;
            text-decoration: none; color: inherit; display: block;
            width: 100%; max-width: 210px;
        }
        .product-card:hover { transform: translateY(-4px); box-shadow: 0 12px 30px rgba(124,58,237,0.25); border-color: rgba(167,139,250,0.3); }
        .product-img { width: 100%; height: 180px; object-fit: cover; }
        .product-img-placeholder { width: 100%; height: 180px; background: linear-gradient(135deg, rgba(124,58,237,0.2), rgba(37,99,235,0.2)); display: flex; align-items: center; justify-content: center; font-size: 2.5rem; }
        .product-info { padding: 0.8rem 0.9rem; }
        .product-category { font-size: 0.7rem; font-weight: 600; color: #a78bfa; text-transform: uppercase; letter-spacing: 0.05em; margin-bottom: 0.25rem; }
        .product-name { font-size: 0.88rem; font-weight: 600; color: #e2e8f0; margin-bottom: 0.4rem; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
        .product-price { font-size: 0.95rem; font-weight: 700; color: #34d399; }

        /* Pagination */
        .pagination { display: flex; align-items: center; justify-content: center; gap: 0.5rem; flex-wrap: wrap; }
        .page-btn {
            min-width: 40px; height: 40px; display: flex; align-items: center; justify-content: center;
            border-radius: 10px; text-decoration: none; font-size: 0.9rem; font-weight: 600;
            background: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.1);
            color: #cbd5e1; transition: background 0.2s, border-color 0.2s;
        }
        .page-btn:hover { background: rgba(124,58,237,0.2); border-color: rgba(124,58,237,0.4); color: #a78bfa; }
        .page-btn.active { background: linear-gradient(135deg, #7c3aed, #2563eb); border-color: transparent; color: #fff; }
        .page-btn.disabled { opacity: 0.3; pointer-events: none; }
        .page-info { color: #475569; font-size: 0.85rem; margin-bottom: 1rem; }

        .empty-state { text-align: center; padding: 4rem 0; color: #475569; }
        .empty-state .emoji { font-size: 3.5rem; margin-bottom: 0.8rem; }
    </style>
</head>
<body>

<div class="container">
    <div class="page-header">
        <h1>Tất Cả <span>Sản Phẩm</span></h1>
        <p>Tổng cộng ${totalProducts} sản phẩm – Trang ${currentPage} / ${totalPages}</p>
    </div>

    <c:choose>
        <c:when test="${empty products}">
            <div class="empty-state">
                <div class="emoji">📦</div>
                <p>Chưa có sản phẩm nào.</p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="product-grid">
                <c:forEach var="p" items="${products}">
                    <a href="${pageContext.request.contextPath}/product-detail?id=${p.productId}" class="product-card">
                        <c:choose>
                            <c:when test="${not empty p.image}">
                                <img class="product-img" src="${pageContext.request.contextPath}/image/product/${p.image}" alt="${p.productName}">
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

            <!-- Phân trang -->
            <c:if test="${totalPages > 1}">
                <div class="pagination">
                    <a class="page-btn ${currentPage <= 1 ? 'disabled' : ''}"
                       href="${pageContext.request.contextPath}/product?page=${currentPage - 1}">←</a>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <a class="page-btn ${i == currentPage ? 'active' : ''}"
                           href="${pageContext.request.contextPath}/product?page=${i}">${i}</a>
                    </c:forEach>

                    <a class="page-btn ${currentPage >= totalPages ? 'disabled' : ''}"
                       href="${pageContext.request.contextPath}/product?page=${currentPage + 1}">→</a>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
