<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Đăng Nhập - NovelWeb</title>
  <link rel="stylesheet" href="assests/css/style.css" />
</head>
<body class="auth-page">
<div class="auth-container">
  <div class="auth-header">
    <a href="index.html" class="logo">Novel<span>Web</span></a>
    <h2>Chào mừng trở lại!</h2>
    <p>Đăng nhập để lưu lại tủ sách của bạn</p>
  </div>
  <c:if test="${not empty successMSG}">
    <div style="background-color: #d4edda; color: #155724; padding: 12px 16px; margin-bottom: 20px; border-radius: 8px; border: 1px solid #c3e6cb; font-size: 14px;">
      ✅ ${successMSG}
    </div>
  </c:if>

  <%-- Kiểm tra và hiển thị thông báo lỗi (vd: Sai email hoặc mật khẩu) --%>
  <c:if test="${not empty errorMSG}">
    <div style="background-color: #f8d7da; color: #721c24; padding: 12px 16px; margin-bottom: 20px; border-radius: 8px; border: 1px solid #f5c6cb; font-size: 14px;">
      ⚠️ ${errorMSG}
    </div>
  </c:if>
  <form class="auth-form" action="login" method="POST">
    <div class="form-group">
      <label for="email">Email</label>
      <input
              type="text"
              id="email"
              placeholder="Nhập email của bạn"
              name="email"
              value="${oldEmail}"
              required
      />
    </div>

    <div class="form-group">
      <label for="password">Mật khẩu</label>
      <input
              type="password"
              id="password"
              placeholder="Nhập mật khẩu"
              name="password"
              required
      />
    </div>

    <div class="form-actions">
      <label class="remember-me">
        <input type="checkbox" /> Ghi nhớ đăng nhập
      </label>
      <a href="#" class="forgot-password">Quên mật khẩu?</a>
    </div>

    <button type="submit" class="btn-primary btn-block">Đăng Nhập</button>
  </form>

  <div class="auth-divider">
    <span>Hoặc đăng nhập bằng</span>
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
    <p>Chưa có tài khoản? <a href="register.html">Đăng ký ngay</a></p>
    <p class="auth-footer-copyright">
      &copy; 2024 NovelWeb. Bảo lưu mọi quyền.
    </p>
  </div>
</div>

</body>
</html>
