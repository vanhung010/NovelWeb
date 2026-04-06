<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Đăng Ký Tài Khoản - NovelWeb</title>
    <link rel="stylesheet" href="assests/css/style.css" />
</head>
<body class="auth-page">
<div class="auth-container">
    <div class="auth-header">
        <a href="index.html" class="logo">Novel<span>Web</span></a>
        <h2>Tạo tài khoản mới</h2>
        <p>Tham gia cộng đồng đọc truyện lớn nhất</p>
    </div>

    <form class="auth-form" action="register" method="POST">
        <div class="form-group">
            <label>Bạn là ai?</label>
            <div class="role-selection">
                <label class="role-option">
                    <input type="radio" name="userRole" value="reader" checked />
                    <span class="role-label">
                <span class="role-icon">📖</span>
                <span class="role-text">
                  <span class="role-title">Đọc Giả</span>
                  <span class="role-desc">Đọc truyện và bình luận</span>
                </span>
              </span>
                </label>
                <label class="role-option">
                    <input type="radio" name="userRole" value="author" />
                    <span class="role-label">
                <span class="role-icon">✍️</span>
                <span class="role-text">
                  <span class="role-title">Tác Giả</span>
                  <span class="role-desc">Viết và chia sẻ truyện của bạn</span>
                </span>
              </span>
                </label>
            </div>
        </div>

        <div class="form-group">
            <label for="username">Tên hiển thị</label>
            <input
                    type="text"
                    id="username"
                    placeholder="Ví dụ: Phong Tôn Giả"
                    name="displayName"
                    required
            />
        </div>

        <div class="form-group">
            <label for="email">Địa chỉ Email</label>
            <input
                    type="email"
                    id="email"
                    placeholder="Nhập email của bạn"
                    name="email"
                    required
            />
        </div>

        <div class="form-group">
            <label for="password">Mật khẩu</label>
            <input
                    type="password"
                    id="password"
                    placeholder="Tạo mật khẩu (ít nhất 6 ký tự)"
                    name="password"
                    required
            />
        </div>

        <div class="form-group">
            <label for="confirm-password">Xác nhận mật khẩu</label>
            <input
                    type="password"
                    id="confirm-password"
                    placeholder="Nhập lại mật khẩu"
                    name="confirmPassword"
                    required
            />
        </div>

        <label class="terms-text">
            <input type="checkbox" name="agreeItem" value="yes" required />
            <span
            >Tôi đồng ý với các <a href="#">Điều khoản dịch vụ</a> và
            <a href="#">Chính sách bảo mật</a> của NovelWeb.</span
            >
        </label>

        <button type="submit" class="btn-primary btn-block">Đăng Ký</button>
    </form>

    <div class="auth-divider">
        <span>Hoặc đăng ký nhanh bằng</span>
    </div>

    <div class="social-login">
        <button type="button" class="btn-social btn-google">Google</button>
        <button type="button" class="btn-social btn-facebook">Facebook</button>
    </div>

    <div class="auth-footer">
        <div class="auth-footer-links">
            <a href="#">Điều Khoản Dịch Vụ</a>
            <span>•</span>
            <a href="#">Chính Sách Riêng Tư</a>
            <span>•</span>
            <a href="#">Liên Hệ Hỗ Trợ</a>
        </div>
        <p>Đã có tài khoản? <a href="login.html">Đăng nhập ngay</a></p>
        <p class="auth-footer-copyright">
            &copy; 2024 NovelWeb. Bảo lưu mọi quyền.
        </p>
    </div>
</div>
</body>
</html>
