<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Quên Mật Khẩu - NovelWeb</title>
  <link rel="stylesheet" href="assests/css/forgotpass.css" />
</head>
<body class="auth-page">
<div class="auth-container">
  <div class="auth-header">
    <a href="index.html" class="logo">Novel<span>Web</span></a>
    <h2>Quên Mật Khẩu?</h2>
    <p>Nhập email để khôi phục tài khoản của bạn</p>
  </div>

  <div class="forgot-progress">
    <div class="progress-step active">
      <div class="progress-number">1</div>
      <span>Email</span>
    </div>
    <div class="progress-line"></div>
    <div class="progress-step">
      <div class="progress-number">2</div>
      <span>Mã Code</span>
    </div>
    <div class="progress-line"></div>
    <div class="progress-step">
      <div class="progress-number">3</div>
      <span>Mật Khẩu Mới</span>
    </div>
  </div>

  <form class="auth-form" id="emailForm" method="POST" action="forgotpassmail">
    <div class="form-group">
      <label for="email">Địa chỉ Email</label>
      <input
              type="email"
              id="email"
              placeholder="Nhập email liên kết với tài khoản"
              name = "email"
              required
      />
      <p class="form-hint">Chúng tôi sẽ gửi mã xác thực đến email này</p>
    </div>

    <button type="submit" class="btn-primary btn-block">
      Gửi Mã Xác Thực
    </button>
  </form>

  <div class="auth-footer">
    <p>
      Bạn đã nhớ mật khẩu?
      <a href="login.html">Đăng nhập ngay</a>
    </p>
    <p>
      Chưa có tài khoản?
      <a href="register.html">Đăng ký tại đây</a>
    </p>
  </div>
</div>

</body>
</html>