package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.stylesheets.LinkStyle;
import project.projectmanagernovel.dao.CategoryDao;
import project.projectmanagernovel.dao.NovelDao;
import project.projectmanagernovel.entry.Category;
import project.projectmanagernovel.entry.Novel;

import java.io.IOException;
import java.net.http.HttpClient;
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

        req.setAttribute("Category", listallCategory);
        req.setAttribute("featuredNovel", listNovelFeatured);
        req.setAttribute("novelRecentUpdate", listNovelRecentUpdate);
        req.setAttribute("commonNovels", listNovelCommon);

        req.getRequestDispatcher("views/public/index.jsp").forward(req, resp);

        System.out.println(listNovelFeatured.get(0).getCoverImage());
    }
}
