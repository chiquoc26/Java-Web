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
        body { background: #0d0b1e; color: #e2e8f0; min-height: 100vh; display: flex; align-items: flex-start; justify-content: center; padding: 2rem 1.5rem; }
        .card {
            background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.1);
            border-radius: 20px; padding: 2rem; width: 100%; max-width: 620px;
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

        .form-actions { display: flex; gap: 0.8rem; margin-top: 1.5rem; }
        .btn-save {
            flex: 1; padding: 0.85rem; border: none; border-radius: 12px;
            cursor: pointer; font-size: 1rem; font-weight: 700; color: #fff;
            background: linear-gradient(135deg, #7c3aed, #2563eb); transition: opacity 0.2s;
        }
        .btn-save:hover { opacity: 0.88; }
        .btn-cancel {
            padding: 0.85rem 1.5rem; border-radius: 12px; text-decoration: none;
            font-size: 0.9rem; font-weight: 600; color: #94a3b8;
            background: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.1);
            transition: background 0.2s;
        }
        .btn-cancel:hover { background: rgba(255,255,255,0.09); color: #e2e8f0; }

        .alert-error { background: rgba(239,68,68,0.15); border: 1px solid rgba(239,68,68,0.4); color: #fca5a5; border-radius: 10px; padding: 0.75rem 1rem; font-size: 0.85rem; margin-bottom: 1.2rem; }
    </style>
</head>
<body>
<div class="card">
    <h1>${act == 'edit' ? '✏️ Sửa' : '➕ Thêm'} <span>Sản Phẩm</span></h1>

    <c:if test="${not empty error}">
        <div class="alert-error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/admin/products?act=${act}${act == 'edit' ? '&id='.concat(product.productId) : ''}"
          method="post" enctype="multipart/form-data">

        <div class="form-group">
            <label>Tên sản phẩm *</label>
            <input type="text" name="productName" placeholder="Nhập tên sản phẩm"
                   value="${product.productName}" required>
        </div>

        <div class="form-group">
            <label>Danh mục *</label>
            <select name="cateId" required>
                <option value="">-- Chọn danh mục --</option>
                <c:forEach var="c" items="${categories}">
                    <option value="${c.cateId}" ${c.cateId == product.cateId ? 'selected' : ''}>${c.cateName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="row-2">
            <div class="form-group">
                <label>Giá (₫) *</label>
                <input type="number" name="price" placeholder="0" min="0" step="1000"
                       value="${product.price}" required>
            </div>
            <div class="form-group">
                <label>Số lượng *</label>
                <input type="number" name="quantity" placeholder="0" min="0"
                       value="${product.quantity}" required>
            </div>
        </div>

        <div class="form-group">
            <label>Mô tả</label>
            <textarea name="description" placeholder="Mô tả sản phẩm...">${product.description}</textarea>
        </div>

        <div class="form-group">
            <label>Ảnh sản phẩm</label>
            <div class="upload-wrap">
                <label class="upload-label" for="imageFile">
                    <span class="icon">🖼️</span>
                    <div class="text">
                        <strong>Chọn ảnh</strong>
                        JPG, PNG, WEBP – Tối đa 5MB
                    </div>
                </label>
                <input type="file" id="imageFile" name="image" accept=".jpg,.jpeg,.png,.webp">
                <img id="previewImg" src="#" alt="Preview">
            </div>

            <c:if test="${act == 'edit' && not empty product.image}">
                <div class="existing-img">
                    <img src="${pageContext.request.contextPath}/image/product/${product.image}" alt="Ảnh hiện tại">
                    <p>Ảnh hiện tại. Upload ảnh mới để thay thế.</p>
                </div>
            </c:if>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-save">${act == 'edit' ? 'Cập Nhật' : 'Thêm Sản Phẩm'}</button>
            <a href="${pageContext.request.contextPath}/admin/products" class="btn-cancel">Hủy</a>
        </div>
    </form>
</div>

<script>
    document.getElementById('imageFile').addEventListener('change', function() {
        const preview = document.getElementById('previewImg');
        if (this.files && this.files[0]) {
            preview.src = URL.createObjectURL(this.files[0]);
            preview.style.display = 'block';
        }
    });
</script>
</body>
</html>
