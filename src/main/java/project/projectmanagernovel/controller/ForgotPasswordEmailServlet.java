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
       String msg = req.getParameter("msg");
       if(msg != null){
           if(msg.equals("c")){
               req.setAttribute("errorMSG", "Vui lòng điền email");
           }
           else if(msg.equals("busy")){
               req.setAttribute("errorMSG", "Hệ thống đang bận, không thể gửi email lúc này.");
           }
           else if(msg.equals("notacc")){
               req.setAttribute("errorMSG", "Email này chưa được đăng ký trong hệ thống.");
           }
       }

        req.getRequestDispatcher("views/public/forgotPassWithEmail.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        //trường hợp chưa nhập mail
        if(email == null || email.trim().isEmpty()){
            resp.sendRedirect("forgotpassmail?msg=c");
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
                    resp.sendRedirect("forgotmail?msg=busy");
                }
        } else {
            // Nếu email không tồn tại trong DB

            resp.sendRedirect("forgotpassmail?msg=notacc");
        }

    }
}
