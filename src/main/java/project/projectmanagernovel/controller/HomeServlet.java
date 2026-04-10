package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import project.projectmanagernovel.dao.AccountDao;
import project.projectmanagernovel.dao.CategoryDao;
import project.projectmanagernovel.dao.NovelDao;
import project.projectmanagernovel.entity.Account;
import project.projectmanagernovel.entity.Category;
import project.projectmanagernovel.entity.Novel;

import java.io.IOException;
import java.util.List;
@WebServlet(urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    CategoryDao categoryDao = new CategoryDao();
    NovelDao novelDao = new NovelDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> listallCategory = categoryDao.getAllCategories();
        List<Novel> listNovelFeatured = novelDao.getFeaturedNovels(); //lấy truyện đề cử
        List<Novel> listNovelRecentUpdate = novelDao.getRecentlyUpdatedNovels();
        List<Novel> listNovelCommon = novelDao.getCommonNovels();

        HttpSession sessionCate = req.getSession();
        sessionCate.setAttribute("Category", listallCategory);


        req.setAttribute("featuredNovel", listNovelFeatured);
        req.setAttribute("novelRecentUpdate", listNovelRecentUpdate);
        req.setAttribute("commonNovels", listNovelCommon);

        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("loggedUser");
        //chưa có session (chưa đăng nhập)
        if(account == null) {
            Cookie[] cookies = req.getCookies();

            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("cookieEmail")) {
                        String emailValueCookie = c.getValue();

                        AccountDao accountDao = new AccountDao();
                        Account account1 = accountDao.getAccountByEmail(emailValueCookie);

                        if (account1 != null) {
                            session.setAttribute("loggedUser", account1);
                            break;
                        }
                    }
                }
            }
        }

        req.getRequestDispatcher("views/public/index.jsp").forward(req, resp);



    }
}
