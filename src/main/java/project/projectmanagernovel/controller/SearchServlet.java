package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.projectmanagernovel.dao.NovelDao;
import project.projectmanagernovel.entity.Novel;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/seach"})
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = (String) req.getParameter("key");
        NovelDao novelDao = new NovelDao();

        List<Novel> novelList = novelDao.getNovelBySearch(key);

        //Trường hợp không nhập đá về trang chủ
        if(key.trim().isEmpty() || key == null){
            req.getRequestDispatcher("views/public/search.jsp");
            return;
        }

        //Trường hợp không có truyện phù hợp
        if(novelList.isEmpty()){
            req.setAttribute("error", "Không tìm thấy truyện");
           req.getRequestDispatcher("views/public/search.jsp").forward(req, resp);
           return;
        }

        req.setAttribute("listnovel", novelList);
        req.getRequestDispatcher("views/public/search.jsp");

    }
}
