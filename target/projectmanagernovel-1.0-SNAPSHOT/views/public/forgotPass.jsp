<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Đổi Mật Khẩu - NovelWeb</title>
    <link rel="stylesheet" href="<c:url value='/assests/css/style.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assests/css/forgotpass.css'/>" />
</head>
<body class="auth-page">
<div class="auth-container">
    <div class="auth-header">
        <a href="index.html" class="logo">Novel<span>Web</span></a>
        <h2>Đặt Mật Khẩu Mới</h2>
        <p>Tạo mát khẩu mạnh để bảo vệ tài khoản của bạn</p>
    </div>

    <div class="forgot-progress">
        <div class="progress-step completed">
            <div class="progress-number">✓</div>
            <span>Email</span>
        </div>
        <div class="progress-line"></div>
        <div class="progress-step completed">
            <div class="progress-number">✓</div>
            <span>Mã Code</span>
        </div>
        <div class="progress-line"></div>
        <div class="progress-step active">
            <div class="progress-number">3</div>
            <span>Mật Khẩu Mới</span>
        </div>
    </div>
<c:if test="${not empty param.msg}">
    <div style="background-color: #f8d7da; color: #721c24; padding: 12px 16px; margin-bottom: 20px; border-radius: 8px; border: 1px solid #f5c6cb; font-size: 14px; text-align: center;">
    ⚠️ ${errorMSG}
    </div>
</c:if>
    <form class="auth-form" id="passwordForm" method="POST" action="forgotpass">
        <div class="form-group">
            <label for="newPassword">Mật Khẩu Mới</label>
            <div class="password-input-wrapper">
                <input
                        type="password"
                        id="newPassword"
                        placeholder="Nhập mật khẩu mới (ít nhất 8 ký tự)"
                        minlength="8"
                        required
                        name="pass1"
                />
                <button type="button" class="toggle-password" toggles="newPassword">

                </button>
            </div>
            <div class="password-strength">
                <div class="strength-bar" id="strengthBar"></div>
                <p id="strengthText"></p>
            </div>
        </div>

        <div class="form-group">
            <label for="confirmPassword">Xác Nhận Mật Khẩu</label>
            <div class="password-input-wrapper">
                <input
                        type="password"
                        id="confirmPassword"
                        placeholder="Nhập lại mật khẩu"
                        name="pass2"
                        required
                />
                <button
                        type="button"
                        class="toggle-password"
                        toggles="confirmPassword"
                >

                </button>
            </div>
        </div>

        <div id="passwordError" class="password-error"></div>



        <button type="submit" class="btn-primary btn-block">
            Cập Nhật Mật Khẩu
        </button>
    </form>

    <div class="auth-footer">
        <p>
            <a href="forgetPasswordwithEmail.html">← Bắt đầu lại</a>
        </p>
    </div>
</div>

<script>
    const newPasswordInput = document.getElementById("newPassword");
    const confirmPasswordInput = document.getElementById("confirmPassword");
    const passwordForm = document.getElementById("passwordForm");
    const passwordError = document.getElementById("passwordError");
    const strengthBar = document.getElementById("strengthBar");
    const strengthText = document.getElementById("strengthText");
    // Cập nhật UI các yêu cầu
    document
        .getElementById("req-length")
        .classList.toggle("met", requirements.length);
    document
        .getElementById("req-uppercase")
        .classList.toggle("met", requirements.uppercase);
    document
        .getElementById("req-lowercase")
        .classList.toggle("met", requirements.lowercase);
    document
        .getElementById("req-number")
        .classList.toggle("met", requirements.number);

    // Toggle hiển thị mật khẩu
    document.querySelectorAll(".toggle-password").forEach((btn) => {
        btn.addEventListener("click", function (e) {
            e.preventDefault();
            const inputId = this.getAttribute("toggles");
            const input = document.getElementById(inputId);
            input.type = input.type === "password" ? "text" : "password";
            this.textContent = input.type === "password" ? "👁️" : "👁️‍🗨️";
        });
    });
</script>
</body>
</html>
