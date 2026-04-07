package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import project.projectmanagernovel.util.EmailUtil;

import java.io.IOException;
@WebServlet(urlPatterns = {"/resend-code"})
public class ResendCodeRegister extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // 1. Mở Session lấy Email đang cần reset
        String email = (String) session.getAttribute("resetEmail");

        // BẮT LỖI: Lỡ người dùng để treo máy quá lâu làm Session bay màu mất cả Email
        if (email == null) {
            resp.sendRedirect("forgotpassmail"); // Đuổi về bước 1 bắt nhập lại email
            return;
        }

        // 2. Sinh mã OTP MỚI
        String newOTP = EmailUtil.generateOTP();

        // 3. Bắn email
        boolean isSent = EmailUtil.sendEmail(email, newOTP);

        if (isSent) {
            // 4. Ghi đè mã OTP mới vào Session và thiết lập lại thời gian sống 5 phút (300 giây)
            session.setAttribute("otpCode", newOTP);
            session.setMaxInactiveInterval(300);

            // 5. Đá về trang nhập mã kèm "ám hiệu" thành công
            resp.sendRedirect("forgotpasscode?msg=resent");
        } else {
            // Lỗi mạng không gửi được
            resp.sendRedirect("forgotpasscode?msg=sendfail");
        }
    }

    }

