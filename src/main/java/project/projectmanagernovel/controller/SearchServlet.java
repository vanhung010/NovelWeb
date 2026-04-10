package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.projectmanagernovel.dao.NovelDao;
import project.projectmanagernovel.entity.Novel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = req.getParameter("key");
        NovelDao novelDao = new NovelDao();

        int currentPage = 1;
        int pageSize = 12;
        int totalResults = 0;
        int totalPages = 0;
        List<Novel> novelList = new ArrayList<>();

        if (key != null) {
            key = key.trim();
        }

        if (key == null || key.isEmpty()) {
            req.setAttribute("listnovel", novelList);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("totalResults", totalResults);
            req.setAttribute("key", "");
            req.getRequestDispatcher("views/public/search.jsp").forward(req, resp);
            return;
        }

        String pageParam = req.getParameter("page");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException ignored) {
                currentPage = 1;
            }
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        totalResults = novelDao.countNovelBySearch(key);
        totalPages = (int) Math.ceil((double) totalResults / pageSize);

        if (totalPages > 0 && currentPage > totalPages) {
            currentPage = totalPages;
        }

        if (totalResults > 0) {
            novelList = novelDao.getNovelBySearchPaged(key, currentPage, pageSize);
        }

        if (totalResults == 0) {
            req.setAttribute("error", "Không tìm thấy truyện");
        }

        req.setAttribute("listnovel", novelList);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalResults", totalResults);
        req.setAttribute("key", key);
        req.getRequestDispatcher("views/public/search.jsp").forward(req, resp);

    }
}
