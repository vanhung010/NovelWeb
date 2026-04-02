package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.projectmanagernovel.dao.CategoryDao;
import project.projectmanagernovel.dao.ChapterDao;
import project.projectmanagernovel.dao.NovelDao;
import project.projectmanagernovel.entry.Category;
import project.projectmanagernovel.entry.Chapter;
import project.projectmanagernovel.entry.Novel;

import java.io.IOException;
import java.util.List;
@WebServlet(urlPatterns = {"/detail"})
public class DetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idPara = req.getParameter("id");
        String pagePara = req.getParameter("page");


        int idNovel=0;
        int page =1; //mặc định

        if(idPara == null || idPara.trim().isEmpty()){
            //không truyền dữ liệu
            resp.sendRedirect("home");
            return;
        }

        try{
            idNovel = Integer.parseInt(idPara);
            if(idNovel <= 0 ){    //nghịch chỉnh url id xuống số âm thì đưa về trang chủ
                resp.sendRedirect("home");
                return;
            }
        }
        catch (NumberFormatException e){
            //nhập sai định dạng
            resp.sendRedirect("home");
            return;
        }

        if(pagePara != null && !pagePara.trim().isEmpty()){
            try{
                page = Integer.parseInt(pagePara);
                if(page <1 ) page =1;
            }
            catch (NumberFormatException e){
                page =1;
            }
        }



        ChapterDao chapterDao = new ChapterDao();
        CategoryDao categoryDao = new CategoryDao();
        NovelDao novelDao = new NovelDao();

        novelDao.increseView(idNovel);

        Novel detailNovel = novelDao.getDetailNovel(idNovel, page);

        List<Category> listCategoryOfNovel  = categoryDao.getListCategoryByIdNovel(idNovel);


        //Không có truyện thì trả về trang váo lỗi
        if(detailNovel == null ){
            resp.sendRedirect("home");
            return;
        }

        List<Chapter> chapterOfNovelInPage = chapterDao.getChapterByIdPage(idNovel, page);

        int totalChapter = chapterDao.getTotalChapterOfNovel(idNovel);
        int totalPages = (int) Math.ceil((double) totalChapter /12);

        //đóng gói dữ liệu gửi đi JSP

        req.setAttribute("novel", detailNovel);
        req.setAttribute("currentpage",  page);
        req.setAttribute("totalpage", totalPages);
        req.setAttribute("listcategory", listCategoryOfNovel);
        req.setAttribute("listchapter", chapterOfNovelInPage);

        req.getRequestDispatcher("views/public/detail.jsp").forward(req, resp);
    }
}
