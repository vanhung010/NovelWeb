<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Xác Thực Mã Code - NovelWeb</title>
  <link rel="stylesheet" href="<c:url value='/assests/css/style.css'/>" />
  <link rel="stylesheet" href="<c:url value='/assests/css/forgotpass.css'/>" />
</head>
<body class="auth-page">
<div class="auth-container">
  <div class="auth-header">
    <a href="index.html" class="logo">Novel<span>Web</span></a>
    <h2>Xác Thực Mã Code</h2>
    <p>Nhập mã code 6 số được gửi đến email của bạn</p>
  </div>

  <div class="forgot-progress">
    <div class="progress-step completed">
      <div class="progress-number">✓</div>
      <span>Email</span>
    </div>
    <div class="progress-line"></div>
    <div class="progress-step active">
      <div class="progress-number">2</div>
      <span>Mã Code</span>
    </div>
    <div class="progress-line"></div>
    <div class="progress-step">
      <div class="progress-number">3</div>
      <span>Mật Khẩu Mới</span>
    </div>
  </div>
<c:if test="${not empty param.msg}">
<div style="background-color: #f8d7da; color: #721c24; padding: 12px 16px; margin-bottom: 20px; border-radius: 8px; border: 1px solid #f5c6cb; font-size: 14px; text-align: center;">
  ⚠️ ${errorMSG}
</div>   </c:if>
  <%-- Khối hiển thị Gửi lại mã thành công --%>
  <c:if test="${not empty successMSG}">
    <div style="background-color: #d4edda; color: #155724; padding: 12px 16px; margin-bottom: 20px; border-radius: 8px; border: 1px solid #c3e6cb; font-size: 14px; text-align: center;">
      ✅ ${successMSG}
    </div>
  </c:if>
  <form class="auth-form" id="codeForm" method="POST" action="forgotpasscode">
    <div class="form-group">
      <label for="code">Mã Xác Thực</label>
      <input
              type="text"
              id="code"
              class="code-input"
              placeholder="000000"
              maxlength="6"
              inputmode="numeric"
              name ="code"
              required
              pattern="[0-9]{6}"
      />
      <p class="form-hint">
        Hãy kiểm tra thư mục Spam nếu không thấy email
      </p>
    </div>

    <button type="submit" class="btn-primary btn-block">
      Xác Thực Mã
    </button>
  </form>

  <div class="form-resend">
    <p>Không nhận được mã?</p>
    <button type="button" class="btn-resend" id="resendBtn">
      Gửi lại mã
    </button>
  </div>

  <div class="auth-footer">
    <p>
      <a href="forgetPasswordwithEmail.html">← Quay lại</a>
    </p>
  </div>
</div>

<script>
  // Chỉ cho phép nhập số (Code cũ của bạn)
  document.getElementById("code").addEventListener("keydown", function (e) {
    if (!/[0-9]/.test(e.key) && !["Backspace", "Delete", "Tab", "ArrowLeft", "ArrowRight"].includes(e.key)) {
      e.preventDefault();
    }
  });

  // CODE MỚI: Bắt sự kiện click cho nút Gửi lại mã
  document.getElementById("resendBtn").addEventListener("click", function() {
    // Đổi chữ trên nút để người dùng biết hệ thống đang xử lý
    this.innerText = "Đang gửi...";
    this.disabled = true;

    // Chuyển hướng sang Servlet xử lý gửi lại mã
    window.location.href = "resend-code";
  });
</script>
</body>
</html>
