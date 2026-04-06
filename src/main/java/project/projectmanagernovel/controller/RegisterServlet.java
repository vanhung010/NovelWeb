package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import project.projectmanagernovel.dao.AccountDao;

import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("views/public/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        //1 lấy dữ liệu
        String role = req.getParameter("userRole");
        String displayName = req.getParameter("displayName");
        String email = req.getParameter("email");
        String passwordExplain = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String agree = req.getParameter("agreeItem");
        //2 kiểm tra dữ liệu
        String errolMsg = null;
        if(role == null || role.trim().isEmpty() ||displayName == null || displayName.trim().isEmpty()|| email == null || email.trim().isEmpty() || passwordExplain == null || passwordExplain.trim().isEmpty()){
            errolMsg = "Vui lòng điền đầy đủ thông tin";
        }
        else if (agree == null) {
            errolMsg = "VUi lòng chon";
        }
        else if (!passwordExplain.equals(confirmPassword)){
            errolMsg = "Mật khẩu xác nhận khng khớp";
        }
        else if(passwordExplain.length() < 8) {
            errolMsg = "Maajt khẩu phải có ít nhất 8 kí tự";
        }
        //Neeus có lỗi
        if(errolMsg != null){
            req.setAttribute("errorMsg", errolMsg);

            req.setAttribute("oldDisplayName", displayName);
            req.setAttribute("oldEmail", email);
            req.setAttribute("oldRole", role);

            req.getRequestDispatcher("views/public/register.jsp").forward(req, resp);
            return;
        }

        //3 thực thi
        String password = BCrypt.hashpw(passwordExplain, BCrypt.gensalt(10));
        AccountDao accountDao = new AccountDao();
        boolean isSuccess =accountDao.addAccount(displayName, password, email, role);

        if(isSuccess){
            resp.sendRedirect("login?msg=register_success");
        }
        else {
            req.setAttribute("errorMsg", "Email này đã được sử dụng. Vui lòng chọn email khác!");
            req.setAttribute("oldDisplayName", displayName);
            req.setAttribute("oldEmail", email);
            req.getRequestDispatcher("views/public/register.jsp").forward(req, resp);
        }


    }
}
