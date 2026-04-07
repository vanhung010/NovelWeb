package project.projectmanagernovel.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = {"/forgotpasscode"})
public class ForgotPasswordCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = req.getParameter("msg");
        if ("null".equals(msg)) {
            req.setAttribute("errorMSG", "Vui lòng nhập mã xác nhận.");
        } else if ("codefail".equals(msg)) {
            req.setAttribute("errorMSG", "Mã không chính xác, vui lòng thử lại.");
        } else if ("expired".equals(msg)) {
            req.setAttribute("errorMSG", "Mã xác nhận đã hết hạn (quá 5 phút). Vui lòng nhận mã mới.");
        }
        else if ("resent".equals(msg)) {
            req.setAttribute("successMSG", "Mã mới đã được gửi! Vui lòng kiểm tra hộp thư.");
        } else if ("sendfail".equals(msg)) {
            req.setAttribute("errorMSG", "Hệ thống đang bận, không thể gửi lại mã lúc này.");
        }
        req.getRequestDispatcher("views/public/forgotPassWithCode.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        HttpSession session = req.getSession();
        String codeSystem = (String) session.getAttribute("otpCode");

        //trường hợp treo máy
        if(codeSystem == null) {
            resp.sendRedirect("forgotpasscode?msg=expired");
            return;
        }

        //Bắt lỗi không nhập
        if(code == null || code.trim().isEmpty()){
            resp.sendRedirect("forgotpasscode?msg=null");
            return;
        }


        if(!codeSystem.equals(code.trim())){
            resp.sendRedirect("forgotpasscode?msg=codefail");
            return;
        }

        // Thành công: Chuyển hướng sang giao diện đổi mật khẩu mới
        resp.sendRedirect("forgotpass");
    }
}
