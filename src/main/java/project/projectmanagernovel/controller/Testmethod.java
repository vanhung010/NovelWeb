package project.projectmanagernovel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.projectmanagernovel.dao.CategoryDao;
import project.projectmanagernovel.entry.Category;
import project.projectmanagernovel.util.DBConnect;

import java.io.IOException;

@WebServlet(urlPatterns = {"/test"})
public class Testmethod extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryDao categoryDao = new CategoryDao();
        Category category = new Category(1, "Tiên hiệp", "Tu tiên đánh nhau");
        categoryDao.updateCategoryDao(category);
    }
}
