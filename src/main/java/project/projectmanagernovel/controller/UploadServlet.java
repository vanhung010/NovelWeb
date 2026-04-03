package project.projectmanagernovel.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import project.projectmanagernovel.dao.NovelDao;
import project.projectmanagernovel.util.CloudinaryConfig;

import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/uploadImage")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idPara = request.getParameter("id");

        if(idPara == null || idPara.trim().isEmpty()){
            response.getWriter().println("<h3>Lỗi: Không tìm thấy ID truyện để cập nhật ảnh!</h3>");
            return;
        }

        int idnovel = Integer.parseInt(idPara);
        // 1. Lấy file từ form JSP (ví dụ input có name="imageFile")
        Part filePart = request.getPart("imageFile");

        // 2. Đọc file thành mảng byte để đẩy lên Cloudinary
        byte[] fileBytes = filePart.getInputStream().readAllBytes();

        // 3. Gọi Cloudinary để upload
        Cloudinary cloudinary = CloudinaryConfig.getCloudinary();

        try {
            // Upload và nhận kết quả trả về là một Map chứa các thông tin của ảnh
            Map uploadResult = cloudinary.uploader().upload(fileBytes, ObjectUtils.emptyMap());

            // 4. Lấy URL an toàn (https) của ảnh sau khi upload thành công
            String imageUrl = (String) uploadResult.get("secure_url");

            System.out.println("Đường dẫn ảnh trên Cloudinary: " + imageUrl);

            // ---- BƯỚC TIẾP THEO DÀNH CHO BẠN ----
            // Tại đây, bạn dùng JDBC để lưu chuỗi 'imageUrl' này vào Database (MySQL, SQL Server...)
            // Ví dụ: dao.updateCoverUrl(bookId, imageUrl);

            NovelDao novelDao = new NovelDao();
            novelDao.updateURLImage(idnovel, imageUrl);


            // Cuối cùng, redirect hoặc forward về trang JSP để hiển thị
            request.setAttribute("uploadedUrl", imageUrl);
            request.getRequestDispatcher("result.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi upload: " + e.getMessage());
        }
    }
}
