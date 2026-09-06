<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap');

    .navbar {
        display: flex !important;
        justify-content: space-between !important;
        align-items: center !important;
        background: rgba(15, 10, 30, 0.85) !important;
        backdrop-filter: blur(16px);
        -webkit-backdrop-filter: blur(16px);
        padding: 0.85rem 2.5rem !important;
        border-bottom: 1px solid rgba(139, 92, 246, 0.25) !important;
        margin-bottom: 0 !important;
        position: sticky !important;
        top: 0 !important;
        z-index: 100 !important;
        box-shadow: 0 4px 30px rgba(0,0,0,0.4) !important;
        flex-wrap: nowrap !important;
        width: 100% !important;
        box-sizing: border-box !important;
        min-height: 66px !important;
    }
    .nav-brand a {
        font-size: 1.3rem;
        font-weight: 800;
        background: linear-gradient(135deg, #a78bfa, #60a5fa);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
        text-decoration: none;
        letter-spacing: -0.03em;
        font-family: 'Inter', sans-serif;
    }
    .nav-brand a::before {
        content: '⬡ ';
        -webkit-text-fill-color: #a78bfa;
    }
    .nav-links {
        display: flex;
        align-items: center;
        gap: 1.25rem;
    }
    .user-greeting {
        font-size: 0.875rem;
        color: #94a3b8;
        font-family: 'Inter', sans-serif;
    }
    .user-greeting b {
        color: #c4b5fd;
        font-weight: 600;
    }
    .nav-btn {
        padding: 0.45rem 1.1rem;
        border-radius: 8px;
        font-size: 0.85rem;
        font-weight: 600;
        text-decoration: none;
        transition: all 0.2s ease;
        font-family: 'Inter', sans-serif;
        cursor: pointer;
    }
    .btn-login {
        background: linear-gradient(135deg, #7c3aed, #2563eb);
        color: #ffffff;
        border: none;
        box-shadow: 0 2px 12px rgba(124, 58, 237, 0.4);
    }
    .btn-login:hover {
        box-shadow: 0 4px 20px rgba(124, 58, 237, 0.6);
        transform: translateY(-1px);
    }
    .btn-profile {
        background: rgba(139, 92, 246, 0.15);
        color: #c4b5fd;
        border: 1px solid rgba(139, 92, 246, 0.35);
    }
    .btn-profile:hover {
        background: rgba(139, 92, 246, 0.25);
        color: #ffffff;
        border-color: rgba(139, 92, 246, 0.6);
    }
    .btn-logout {
        background: rgba(255,255,255,0.06);
        color: #94a3b8;
        border: 1px solid rgba(255,255,255,0.1);
    }
    .btn-logout:hover {
        background: rgba(255,255,255,0.12);
        color: #e2e8f0;
        border-color: rgba(255,255,255,0.2);
    }
    .topbar-avatar {
        width: 32px;
        height: 32px;
        border-radius: 50%;
        object-fit: cover;
        border: 2px solid #8b5cf6;
        vertical-align: middle;
        margin-right: 0.4rem;
    }
    .topbar-avatar-placeholder {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        width: 32px;
        height: 32px;
        border-radius: 50%;
        background: linear-gradient(135deg, #7c3aed, #2563eb);
        color: #ffffff;
        font-size: 0.8rem;
        font-weight: 700;
        border: 1px solid rgba(255,255,255,0.2);
        vertical-align: middle;
        margin-right: 0.4rem;
    }
    .user-info-box {
        display: inline-flex;
        align-items: center;
        text-decoration: none;
    }
</style>

<div class="navbar">
    <div class="nav-brand">
        <a href="${pageContext.request.contextPath}/waiting">AppDemo</a>
    </div>
    <div class="nav-links">
        <c:choose>
            <c:when test="${sessionScope.account == null}">
                <a class="nav-btn btn-login" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/user/profile" class="user-info-box" title="Xem thông tin cá nhân">
                    <c:choose>
                        <c:when test="${not empty sessionScope.account.avatar}">
                            <img src="${pageContext.request.contextPath}/image?fname=${sessionScope.account.avatar}" class="topbar-avatar" alt="Avatar"/>
                        </c:when>
                        <c:otherwise>
                            <span class="topbar-avatar-placeholder">
                                ${not empty sessionScope.account.fullName ? sessionScope.account.fullName.substring(0,1).toUpperCase() : 'U'}
                            </span>
                        </c:otherwise>
                    </c:choose>
                    <span class="user-greeting">Xin chào, <b>${sessionScope.account.fullName}</b></span>
                </a>
                <a class="nav-btn btn-profile" href="${pageContext.request.contextPath}/user/profile">Hồ sơ</a>
                <a class="nav-btn btn-logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>
