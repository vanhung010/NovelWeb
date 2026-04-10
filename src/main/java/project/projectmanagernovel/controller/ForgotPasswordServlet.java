package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import project.projectmanagernovel.dao.AccountDao;

import java.io.IOException;
@WebServlet(urlPatterns = {"/forgotpass"})
public class ForgotPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = req.getParameter("msg");
        if (msg != null) {

            if (msg.equals("notequal")) {
                req.setAttribute("errorMSG", "Mật khẩu xác nhận không khớp.");
            } else if (msg.equals("invalid")) {
                req.setAttribute("errorMSG", "Đã quá 5 phút, vui lòng thử lại.");
            }
            else if (msg.equals("fail")){
                req.setAttribute("errorMSG", "Cập nhật mật khẩu thất bại vui lòng thử lại.");
            }
        }

        req.getRequestDispatcher("views/public/forgotPass.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pass1 = req.getParameter("pass1");
        String pass2 = req.getParameter("pass2");
        //Mật khẩu xác nhập không khớp
        if (!pass1.trim().equals(pass2.trim())) {

            resp.sendRedirect("forgotpass?msg=notequal");
        } else {

            AccountDao accountDao = new AccountDao();
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("mail");
            String otpCode = (String) session.getAttribute("otpCode");
            //nếu quá 5 phút
            if (otpCode == null) {
                resp.sendRedirect("forgotpass?msg=invalid");
                return;
            }
            boolean check = accountDao.updatePassByEmail(email, pass1);
            //cập nhật pass thành coong
            if (check) {
                resp.sendRedirect("login?msg=finish");
                return;
            }
            else {
                resp.sendRedirect("forgotpass?msg=fail");
                return;
            }
        }
    }
}