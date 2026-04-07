package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import project.projectmanagernovel.dao.AccountDao;
import project.projectmanagernovel.util.EmailUtil;

import java.io.IOException;

@WebServlet(urlPatterns = {"/forgotpassmail"})
public class ForgotPasswordEmailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("views/public/forgotPassWithEmail.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String errorMSG = null;
        if(email == null || email.trim().isEmpty()){
            errorMSG = "Vui lòng điền email";
            resp.sendRedirect("forgotpassmail");
            return;
        }
        AccountDao accountDao = new AccountDao();
        boolean check = accountDao.checkAccountByEmail(email);
        //Nếu có tài khoản trong db
        if(check){
                String otpCode = EmailUtil.generateOTP();
                boolean isSendMail = EmailUtil.sendEmail(email, otpCode);
                //Nếu gửi thành công
                if(isSendMail){
                    HttpSession session = req.getSession();

                    session.setAttribute("mail", email);
                    session.setAttribute("otpCode", otpCode);

                    session.setMaxInactiveInterval(300);

                    resp.sendRedirect("forgotpasscode");
                }
                else {
                    // Xử lý nếu mạng lỗi không gửi được mail
                    errorMSG = "Hệ thống đang bận, không thể gửi email lúc này.";
                    req.setAttribute("errorMSG", errorMSG);
                    req.getRequestDispatcher("views/public/forgotPassWithEmail.jsp").forward(req, resp);
                }
        } else {
            // Nếu email không tồn tại trong DB
            errorMSG = "Email này chưa được đăng ký trong hệ thống.";
            req.setAttribute("errorMSG", errorMSG);
            req.getRequestDispatcher("views/public/forgotPassWithEmail.jsp").forward(req, resp);
        }

    }
}
