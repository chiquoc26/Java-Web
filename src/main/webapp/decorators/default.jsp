<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property="title">Hệ Thống</sitemesh:write></title>
    
    <!-- Google Fonts -->
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
            display: flex;
            flex-direction: column;
        }

        /* Gradient background blobs */
        body::before {
            content: '';
            position: fixed;
            top: 5%;
            left: -8%;
            width: 550px;
            height: 550px;
            background: radial-gradient(circle, rgba(124, 58, 237, 0.16) 0%, transparent 70%);
            pointer-events: none;
            z-index: 0;
        }

        body::after {
            content: '';
            position: fixed;
            bottom: 5%;
            right: -8%;
            width: 500px;
            height: 500px;
            background: radial-gradient(circle, rgba(37, 99, 235, 0.14) 0%, transparent 70%);
            pointer-events: none;
            z-index: 0;
        }

        .layout-main-content {
            flex: 1;
            position: relative;
            z-index: 1;
            padding: 2.5rem 1.5rem;
            display: flex;
            justify-content: center;
            align-items: flex-start;
        }

        .footer {
            position: relative;
            z-index: 1;
            text-align: center;
            padding: 1.5rem;
            color: #64748b;
            font-size: 0.85rem;
            border-top: 1px solid rgba(139, 92, 246, 0.15);
            background: rgba(15, 10, 30, 0.6);
        }
    </style>
    
    <sitemesh:write property="head"/>
</head>
<body>

    <!-- topbar -->
    <jsp:include page="/views/topbar.jsp"/>

    <!-- content -->
    <main class="layout-main-content">
        <sitemesh:write property="body"/>
    </main>

    <!-- footer -->
    <footer class="footer">
        <p>&copy; 2026 Web Application</p>
    </footer>

</body>
</html>
