package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.projectmanagernovel.dao.ChapterDao;
import project.projectmanagernovel.entry.Chapter;

import java.io.IOException;
@WebServlet( urlPatterns = {"/read"})
public class ReadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idChapterPara = req.getParameter("idchapter");
        String idNovelPara = req.getParameter("idnovel");

       try{ if(idNovelPara == null || idNovelPara.trim().isEmpty()){
            resp.sendRedirect("home");
            return;
        } }
       catch (NumberFormatException e) {
           resp.sendRedirect("home");
       }


       try {
           if (idChapterPara == null || idChapterPara.trim().isEmpty()) {
               resp.sendRedirect("home");
               return;
           }
       }
       catch (NumberFormatException e){
           resp.sendRedirect("home");
       }
        int idNovel = Integer.parseInt(idNovelPara);
        int idChapter = Integer.parseInt(idChapterPara);
        ChapterDao chapterDao = new ChapterDao();

        Chapter currentChapter = chapterDao.getDetailChapter(idNovel, idChapter);
        if(currentChapter == null){
            resp.sendRedirect("home");
            return;
        }

        int idChapterNext =  chapterDao.nextChapterId(idNovel, currentChapter.getChapterNumber());
        int idChapterPrevious = chapterDao.previousChapterId(idNovel, currentChapter.getChapterNumber());

        //gửi dữ liệu
        req.setAttribute("currentchapter", currentChapter);
        req.setAttribute("idchapternext", idChapterNext);
        req.setAttribute("idchapterprevious", idChapterPrevious);

        req.getRequestDispatcher("views/public/read.jsp").forward(req, resp);
    }
}
