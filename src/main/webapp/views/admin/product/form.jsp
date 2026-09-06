<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${act == 'edit' ? 'Sửa Sản Phẩm' : 'Thêm Sản Phẩm'} – Admin</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', sans-serif; }
        body { background: #0d0b1e; color: #e2e8f0; min-height: 100vh; display: flex; flex-direction: column; }

        .main-wrap {
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 2rem 1.5rem 3rem;
            width: 100%;
        }

        .nav-links-bar {
            width: 100%;
            max-width: 640px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 1.2rem;
            flex-wrap: wrap;
            gap: 0.8rem;
        }

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
        }
        .btn-back-link:hover {
            background: rgba(167, 139, 250, 0.2);
            color: #c4b5fd;
        }

        .breadcrumb-nav {
            font-size: 0.85rem;
            color: #64748b;
            display: flex;
            align-items: center;
            gap: 0.4rem;
        }
        .breadcrumb-nav a {
            color: #94a3b8;
            text-decoration: none;
            transition: color 0.2s;
        }
        .breadcrumb-nav a:hover {
            color: #cbd5e1;
        }
        .breadcrumb-nav span.current {
            color: #e2e8f0;
            font-weight: 600;
        }

        .card {
            background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.1);
            border-radius: 20px; padding: 2rem; width: 100%; max-width: 640px;
            box-shadow: 0 20px 50px rgba(0,0,0,0.4);
        }
        h1 { font-size: 1.5rem; font-weight: 800; color: #f1f5f9; margin-bottom: 1.5rem; }
        h1 span { background: linear-gradient(135deg, #a78bfa, #60a5fa); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text; }

        .form-group { margin-bottom: 1.1rem; }
        label { display: block; font-size: 0.83rem; font-weight: 600; color: #94a3b8; margin-bottom: 0.35rem; }
        input[type="text"], input[type="number"], textarea, select {
            width: 100%; padding: 0.75rem 1rem; border-radius: 10px;
            border: 1.5px solid rgba(255,255,255,0.12); background: rgba(255,255,255,0.06);
            color: #f1f5f9; font-size: 0.92rem; outline: none; transition: border-color 0.2s;
            resize: vertical;
        }
        input:focus, textarea:focus, select:focus { border-color: #7c3aed; }
        select option { background: #1e1b4b; }
        textarea { min-height: 100px; }

        /* Upload ảnh */
        .upload-wrap { position: relative; }
        .upload-label {
            display: flex; align-items: center; gap: 0.8rem;
            background: rgba(255,255,255,0.05); border: 2px dashed rgba(255,255,255,0.15);
            border-radius: 12px; padding: 1.2rem; cursor: pointer; transition: border-color 0.2s;
        }
        .upload-label:hover { border-color: #7c3aed; }
        .upload-label .icon { font-size: 2rem; }
        .upload-label .text { font-size: 0.85rem; color: #94a3b8; }
        .upload-label .text strong { display: block; color: #e2e8f0; margin-bottom: 0.1rem; }
        input[type="file"] { display: none; }

        #previewImg {
            margin-top: 0.8rem; width: 100%; max-height: 200px; object-fit: cover;
            border-radius: 10px; border: 1px solid rgba(255,255,255,0.1); display: none;
        }
        .existing-img { margin-top: 0.6rem; }
        .existing-img img { width: 100px; height: 100px; object-fit: cover; border-radius: 10px; }
        .existing-img p { font-size: 0.75rem; color: #64748b; margin-top: 0.3rem; }

        .row-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }

        .form-actions { display: flex; gap: 0.8rem; margin-top: 1.5rem; flex-wrap: wrap; }
        .btn-save {
            flex: 2; min-width: 140px; padding: 0.85rem; border: none; border-radius: 12px;
            cursor: pointer; font-size: 0.95rem; font-weight: 700; color: #fff;
            background: linear-gradient(135deg, #7c3aed, #2563eb); transition: opacity 0.2s;
        }
        .btn-save:hover { opacity: 0.88; }
        .btn-cancel {
            flex: 1; min-width: 110px; text-align: center;
            padding: 0.85rem 1rem; border-radius: 12px; text-decoration: none;
            font-size: 0.88rem; font-weight: 600; color: #94a3b8;
            background: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.1);
            transition: background 0.2s;
        }
        .btn-cancel:hover { background: rgba(255,255,255,0.09); color: #e2e8f0; }

        .btn-home {
            flex: 1; min-width: 110px; text-align: center;
            padding: 0.85rem 1rem; border-radius: 12px; text-decoration: none;
            font-size: 0.88rem; font-weight: 600; color: #a78bfa;
            background: rgba(124, 58, 237, 0.1); border: 1px solid rgba(124, 58, 237, 0.25);
            transition: background 0.2s;
        }
        .btn-home:hover { background: rgba(124, 58, 237, 0.2); color: #c4b5fd; }

        .alert-error { background: rgba(239,68,68,0.15); border: 1px solid rgba(239,68,68,0.4); color: #fca5a5; border-radius: 10px; padding: 0.75rem 1rem; font-size: 0.85rem; margin-bottom: 1.2rem; }
    </style>
</head>
<body>

<jsp:include page="/views/topbar.jsp"/>

<div class="main-wrap">
    <div class="nav-links-bar">
        <a href="${pageContext.request.contextPath}/admin/products" class="btn-back-link">
            Quay lại danh sách sản phẩm
        </a>
        <div class="breadcrumb-nav">
            <a href="${pageContext.request.contextPath}/admin/home">Admin Home</a>
            <span>/</span>
            <a href="${pageContext.request.contextPath}/admin/products">Sản phẩm</a>
            <span>/</span>
            <span class="current">${act == 'edit' ? 'Sửa' : 'Thêm'}</span>
        </div>
    </div>

    <div class="card">
        <h1>${act == 'edit' ? 'Sửa' : 'Thêm'} <span>Sản Phẩm</span></h1>

        <div id="clientError" class="alert-error" style="display: none;"></div>

        <c:if test="${not empty error}">
            <div class="alert-error">${error}</div>
        </c:if>

        <form id="productForm" action="${pageContext.request.contextPath}/admin/products?act=${act}${act == 'edit' ? '&id='.concat(product.productId) : ''}"
              method="post" enctype="multipart/form-data" onsubmit="return validateProductForm(event)">

            <div class="form-group">
                <label>Tên sản phẩm *</label>
                <input type="text" id="productName" name="productName" placeholder="Nhập tên sản phẩm"
                       value="${product.productName}" required>
            </div>

            <div class="form-group">
                <label>Danh mục *</label>
                <select id="cateId" name="cateId" required>
                    <option value="">-- Chọn danh mục --</option>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.cateId}" ${c.cateId == product.cateId ? 'selected' : ''}>${c.cateName}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="row-2">
                <div class="form-group">
                    <label>Giá (₫) *</label>
                    <input type="number" id="price" name="price" placeholder="0" min="0" step="any"
                           value="${product.price}" required>
                </div>
                <div class="form-group">
                    <label>Số lượng *</label>
                    <input type="number" id="quantity" name="quantity" placeholder="0" min="0"
                           value="${product.quantity}" required>
                </div>
            </div>

            <div class="form-group">
                <label>Mô tả</label>
                <textarea id="description" name="description" placeholder="Mô tả sản phẩm...">${product.description}</textarea>
            </div>

            <div class="form-group">
                <label>Ảnh sản phẩm</label>
                <div class="upload-wrap">
                    <label class="upload-label" for="imageFile">
                        <div class="text">
                            <strong>Chọn ảnh</strong>
                            JPG, PNG, WEBP – Tối đa 5MB
                        </div>
                    </label>
                    <input type="file" id="imageFile" name="image" accept=".jpg,.jpeg,.png,.webp">
                    <img id="previewImg" src="#" alt="Preview">
                </div>
                <c:if test="${not empty product.image}">
                    <div class="existing-img">
                        <img src="${pageContext.request.contextPath}/image/product/${product.image}" alt="Ảnh hiện tại">
                        <p>Ảnh hiện tại (để trống nếu không đổi)</p>
                    </div>
                </c:if>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-save">Lưu sản phẩm</button>
                <a href="${pageContext.request.contextPath}/admin/products" class="btn-cancel">Hủy</a>
                <a href="${pageContext.request.contextPath}/admin/home" class="btn-home">Về Admin Home</a>
            </div>
        </form>
    </div>
</div>

<script>
    const fileInput = document.getElementById('imageFile');
    const preview   = document.getElementById('previewImg');
    if (fileInput) {
        fileInput.addEventListener('change', function () {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = e => {
                    preview.src = e.target.result;
                    preview.style.display = 'block';
                };
                reader.readAsDataURL(file);
            }
        });
    }

    function validateProductForm(e) {
        const clientErr = document.getElementById('clientError');
        clientErr.style.display = 'none';

        const name = document.getElementById('productName').value.trim();
        const cateId = document.getElementById('cateId').value;
        const price = parseFloat(document.getElementById('price').value);
        const qty = parseInt(document.getElementById('quantity').value, 10);

        let err = '';
        if (name.length < 2) {
            err = 'Tên sản phẩm phải có ít nhất 2 ký tự.';
        } else if (!cateId) {
            err = 'Vui lòng chọn danh mục cho sản phẩm.';
        } else if (isNaN(price) || price <= 0) {
            err = 'Giá sản phẩm phải lớn hơn 0.';
        } else if (isNaN(qty) || qty < 0) {
            err = 'Số lượng sản phẩm phải lớn hơn hoặc bằng 0.';
        } else if (fileInput && fileInput.files && fileInput.files[0]) {
            const file = fileInput.files[0];
            const allowed = ['.jpg', '.jpeg', '.png', '.webp'];
            const fileName = file.name.toLowerCase();
            const isAllowed = allowed.some(ext => fileName.endsWith(ext));
            if (!isAllowed) {
                err = 'Chỉ chấp nhận các tệp ảnh định dạng .jpg, .jpeg, .png hoặc .webp.';
            } else if (file.size > 5 * 1024 * 1024) {
                err = 'Kích thước ảnh sản phẩm không được vượt quá 5MB.';
            }
        }

        if (err) {
            e.preventDefault();
            clientErr.textContent = err;
            clientErr.style.display = 'block';
            window.scrollTo({ top: 0, behavior: 'smooth' });
            return false;
        }
        return true;
    }
</script>
</body>
</html>
