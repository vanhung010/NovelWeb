package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import project.projectmanagernovel.dao.AccountDao;
import project.projectmanagernovel.entity.Account;

import java.io.IOException;
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = req.getParameter("msg");
        if("register_success".equals(msg)){
            req.setAttribute("successMSG", "Đăng kí thành công");
        }
        req.getRequestDispatcher("views/public/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
            String errorMSG = null;
        if(email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()){
            errorMSG = "Vui lòng điền đầy đủ thông tin";
            req.setAttribute("errorMSG", errorMSG);
            req.setAttribute("oldEmail", email);
            req.getRequestDispatcher("views/public/login.jsp").forward(req, resp);
            return;
        }
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.checkAccount(email, password);
        System.out.println("Tài khoản tìm được: " + account);

        if(account == null){
            errorMSG = "Sai email hoặc mật khẩu";
            req.setAttribute("errorMSG", errorMSG);
            req.setAttribute("oldEmail", email);
            req.getRequestDispatcher("views/public/login.jsp").forward(req, resp);
        }
        else {
            HttpSession session = req.getSession();
            session.setAttribute("currentUser", account);

            // Tùy chọn nâng cao: Phân luồng theo Role (Admin đi đường riêng, Reader đi đường riêng)
            resp.sendRedirect("home");
        }

    }
}
