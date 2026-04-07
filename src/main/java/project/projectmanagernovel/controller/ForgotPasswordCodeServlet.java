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
        req.getRequestDispatcher("views/public/forgotPassWithCode.jsp").forward();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("name");
        HttpSession session = req.getSession();
        String codeSystem = (String) session.getAttribute("otpCode");
        String errorMSG = null;
        if(code == null || code.trim().isEmpty()){
            errorMSG = "Vui lòng nhập mã";
            req.setAttribute("errorMSG", errorMSG);
            req.getRequestDispatcher("forgotpasscode?msg=null").forward(req, resp);
            return;  //Nếu không nhập mã
        }
        //Nếu nhập mã không đúng
        if(!codeSystem.equals(code)){
            errorMSG = "Mã không chính xác, vui lòng thử lại";
            req.setAttribute("errorMSG", errorMSG);
            req.getRequestDispatcher("forgotpasscode?msg=codefail").forward(req, resp);
            return;
        }
        else{ //Nhập mật khẩu đúng
            req.getRequestDispatcher("forgotpass").forward(req, resp);
        }
    }
}
