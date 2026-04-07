package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import project.projectmanagernovel.dao.AccountDao;
import project.projectmanagernovel.entity.Account;

import java.io.IOException;
@WebServlet(urlPatterns = {"/logout"})
public class LogOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //Xóa cookie
        Cookie[] cookies = req.getCookies();
        for(Cookie c : cookies){
            if(c != null) {
                if (c.getName().equalsIgnoreCase("cookieEmail")) {
                    c.setMaxAge(0); //xóa cookie
                    resp.addCookie(c);
                    break;
                }
            }
        }

        session.invalidate();
        resp.sendRedirect("home");
    }


}
