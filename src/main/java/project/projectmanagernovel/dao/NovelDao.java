package project.projectmanagernovel.dao;

import project.projectmanagernovel.entity.*;
import project.projectmanagernovel.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NovelDao {

    //update url hiình ảnh của truyện
    public void updateURLImage(int idnovel, String imageURL){
        String  query = "UPDATE novel " +
                "SET cover_image = ? " +
                "WHERE id_novel = ?";
        try(Connection connection = DBConnect.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, imageURL);
            preparedStatement.setInt(2, idnovel);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public boolean insertNovel(Novel novel, List<Integer> categoryIds) {
        String sqlInsertNovel = "INSERT INTO novel (title, id_author, description, cover_image) " +
                "VALUES (?, ?, ?, ?)";

        String sqlInsertNovelCategory = "INSERT INTO novel_category (id_novel, id_category) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement psNovel = null;
        PreparedStatement psCategory = null;
        ResultSet rs = null;
        boolean isSuccess = false;

        try {
            conn = DBConnect.getConnection();

            // TẮT AUTO-COMMIT: Bắt đầu một Transaction
            conn.setAutoCommit(false);

            // 1. Thực thi câu lệnh Insert Novel và yêu cầu trả về ID vừa tạo
            psNovel = conn.prepareStatement(sqlInsertNovel, PreparedStatement.RETURN_GENERATED_KEYS);
            psNovel.setString(1, novel.getTitle());
            psNovel.setInt(2, novel.getAuthor().getIdAuthor()); // Giả sử đã có object Author
            psNovel.setString(3, novel.getDescription());
            psNovel.setString(4, novel.getCoverImage());


            int affectedRows = psNovel.executeUpdate();

            if (affectedRows > 0) {
                // Lấy id_novel tự tăng vừa được tạo trong Database
                rs = psNovel.getGeneratedKeys();
                if (rs.next()) {
                    int newNovelId = rs.getInt(1);

                    // 2. Thêm vào bảng trung gian novel_category
                    if (categoryIds != null && !categoryIds.isEmpty()) {
                        psCategory = conn.prepareStatement(sqlInsertNovelCategory);

                        for (Integer categoryId : categoryIds) {
                            psCategory.setInt(1, newNovelId);
                            psCategory.setInt(2, categoryId);
                            psCategory.addBatch();
                        }


                        psCategory.executeBatch();
                    }


                    conn.commit();
                    isSuccess = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm truyện: " + e.getMessage());
            try {
                if (conn != null) {
                    // NẾU CÓ BẤT KỲ LỖI GÌ -> HỦY BỎ TẤT CẢ (ROLLBACK)
                    conn.rollback();
                    System.out.println("Đã Rollback dữ liệu an toàn!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Đóng các tài nguyên (Bắt buộc phải mở autoCommit lại như cũ)
            try {
                if (rs != null) {
                    rs.close();
                }
                if (psNovel != null) psNovel.close();
                if (psCategory != null) psCategory.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isSuccess;
    }

    public boolean deletedNovel(Integer idNovel){
        boolean isSucces = false;
        String query = "DELETE FROM novel WHERE id_novel = ?";
        try(Connection connection = DBConnect.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, idNovel);
            int arrowEffect = preparedStatement.executeUpdate();
            if(arrowEffect > 0) {
                isSucces = true;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa truyện: " + e.getMessage());
        }
        return isSucces;
    }

    //lấy 4 truyện đề cử
    public List<Novel> getFeaturedNovels(){
        List<Novel> result = new ArrayList<>();

        // 1. Sửa lại thứ tự SQL và bổ sung n.id_novel
        String query = "SELECT n.id_novel, n.cover_image, n.title, n.status, at.pen_name " +
                "FROM novel AS n " +
                "LEFT JOIN author AS at ON n.id_author = at.id_author " +
                "WHERE n.status = 'Completed' OR n.status= 'Ongoing'" +
                "ORDER BY RANDOM() LIMIT 4";

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Author author = new Author();
                author.setPername(resultSet.getString("pen_name"));

                Novel novel = new Novel();
                novel.setAuthor(author);



                novel.setIdNovel(resultSet.getInt("id_novel"));
                novel.setTitle(resultSet.getString("title"));
                novel.setCoverImage(resultSet.getString("cover_image"));
                novel.setStatus(NovelStatus.valueOf(resultSet.getString("status").toUpperCase()));

                result.add(novel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //lấy 3 mới cập nhật
    public List<Novel> getRecentlyUpdatedNovels(){
        List<Novel> result = new ArrayList<>();


//        String query = "SELECT n.id_novel, n.title AS novel_title, chap.title AS chapter_title, chap.create_at, chap.chapter_number, at.pen_name " +
//                "FROM chapter AS chap " +
//                "LEFT JOIN novel AS n ON chap.id_novel = n.id_novel " +
//                "LEFT JOIN author AS at ON n.id_author = at.id_author " +
//                "ORDER BY chap.create_at DESC " +
//                "LIMIT 3";
        String query = "WITH RankedChapters AS (\n" +
                "    SELECT \n" +
                "        n.id_novel, \n" +
                "        n.title AS novel_title, \n" +
                "        chap.title AS chapter_title, \n" +
                "        chap.create_at, \n" +
                "        chap.chapter_number, \n" +
                "        at.pen_name,\n" +
                "        -- Đánh số thứ tự (1, 2, 3...) cho các chương CỦA CÙNG 1 TRUYỆN\n" +
                "        ROW_NUMBER() OVER(\n" +
                "            PARTITION BY n.id_novel \n" +
                "            ORDER BY chap.create_at DESC, chap.chapter_number DESC\n" +
                "        ) as rn\n" +
                "    FROM novel AS n\n" +
                "    JOIN chapter AS chap ON chap.id_novel = n.id_novel\n" +
                "    JOIN author AS at ON n.id_author = at.id_author\n" +
                ")\n" +
                "SELECT \n" +
                "    id_novel, \n" +
                "    novel_title, \n" +
                "    chapter_title, \n" +
                "    create_at, \n" +
                "    chapter_number, \n" +
                "    pen_name\n" +
                "FROM RankedChapters\n" +
                "WHERE rn = 1 -- CHỈ lấy chương mới nhất (hạng 1) của mỗi truyện\n" +
                "ORDER BY create_at DESC\n" +
                "LIMIT 3;";

        try(Connection connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            CategoryDao categoryDao = new CategoryDao(); // Nên đưa ra ngoài vòng while để tối ưu hiệu suất

            while (resultSet.next()){
                Author author = new Author();
                Chapter chapter = new Chapter();
                Novel novel = new Novel();

                int idNovel = resultSet.getInt("id_novel");

                // SỬA LỖI 2: Dùng đúng bí danh để lấy Tên Chương
                chapter.setChapterNumber(resultSet.getInt("chapter_number"));
                chapter.setTitle(resultSet.getString("chapter_title"));

                author.setPername(resultSet.getString("pen_name"));


                novel.setIdNovel(idNovel);
                novel.setTitle(resultSet.getString("novel_title"));
                novel.setAuthor(author);

                // LƯU Ý: Class Novel BẮT BUỘC phải khai báo:
                // private List<Chapter> chapterList = new ArrayList<>();
                // Nếu không dòng dưới sẽ dính NullPointerException
                novel.getChapterList().add(chapter);
                novel.getCategoryList().addAll(categoryDao.getListCategoryByIdNovel(idNovel));

                result.add(novel);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    //laays 3 truyen hot nhat
    public List<Novel> getCommonNovels(){
        List<Novel> result = new ArrayList<>();

        String query = "SELECT id_novel, title, total_views " +
                "FROM novel " +
                "ORDER BY total_views DESC " +
                "LIMIT 3";
            try(Connection connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()) {
                    Novel novel = new Novel();
                    //set attribute
                    novel.setIdNovel(resultSet.getInt("id_novel"));
                    novel.setTitle(resultSet.getString("title"));
                    novel.setTotalViews(resultSet.getInt("total_views"));
                    result.add(novel);
                }
            }
            catch (SQLException e){
                e.printStackTrace();
                throw new RuntimeException("ERROR "+ e.getMessage());
            }
        return result;
    }


    //lấy chi tiết truyện
    public Novel getDetailNovel(int idNovels, int page) {
        Novel novel = null; // Nên để null ban đầu, nếu tìm thấy truyện mới khởi tạo

        // 1. Sửa LEFT JOIN, sửa lỗi chính tả description, và bỏ JOIN category đi cho đỡ trùng dòng
        String query = "SELECT n.title, n.cover_image, n.description, n.status, n.total_views, at.pen_name " +
                "FROM novel AS n " +
                "LEFT JOIN author AS at ON n.id_author = at.id_author " +
                "WHERE n.id_novel = ?";

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // 2. Phải truyền tham số (?) VÀO TRƯỚC khi gọi executeQuery()
            preparedStatement.setInt(1, idNovels);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // 3. Phải gọi next() để trỏ vào dòng dữ liệu đầu tiên
                if (resultSet.next()) {
                    novel = new Novel();
                    novel.setIdNovel(idNovels); // Nhớ set lại ID cho novel

                    // Gắn giá trị cơ bản của Novel
                    novel.setTitle(resultSet.getString("title"));
                    novel.setCoverImage(resultSet.getString("cover_image")); // Bạn bị thiếu dòng này
                    novel.setDescription(resultSet.getString("description"));
                    novel.setStatus(NovelStatus.valueOf(resultSet.getString("status").toUpperCase()));
                    novel.setTotalViews(resultSet.getInt("total_views"));

                    // Gắn Tác giả
                    Author author = new Author();
                    author.setPername(resultSet.getString("pen_name"));
                    novel.setAuthor(author); // Bạn bị thiếu dòng này để nối tác giả vào truyện

                    // Gọi DAO để lấy danh sách Chương (Trang 1)
                    ChapterDao chapterDao = new ChapterDao();
                    List<Chapter> listChapter = chapterDao.getChapterByIdPage(idNovels, page);
                    novel.getChapterList().addAll(listChapter);

                    // GỌI DAO ĐỂ LẤY DANH SÁCH THỂ LOẠI (Giống hệt cách lấy Chapter)
                    CategoryDao categoryDao = new CategoryDao();
                    novel.setCategoryList(categoryDao.getListCategoryByIdNovel(idNovels));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi Database: " + e.getMessage());
        }

        return novel;
    }

    //lấy tên truyenej
    public Novel getNameNovel(int idNovels) {
        Novel novel = null; // Nên để null ban đầu, nếu tìm thấy truyện mới khởi tạo

        // 1. Sửa LEFT JOIN, sửa lỗi chính tả description, và bỏ JOIN category đi cho đỡ trùng dòng
        String query = "SELECT title " +
                "FROM novel AS n " +
                "WHERE n.id_novel = ?";

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {


            preparedStatement.setInt(1, idNovels);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    novel = new Novel();
                    novel.setIdNovel(idNovels); // Nhớ set lại ID cho novel


                    novel.setTitle(resultSet.getString("title"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi Database: " + e.getMessage());
        }

        return novel;
    }
    //tăng lượt xem khi người dùng click vào truyện

    public void increseView(int idNovel){
        String query = "UPDATE novel " +
                "SET total_views = total_views + 1 " +
                "WHERE id_novel = ?";
        try(Connection connection = DBConnect.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, idNovel);
            preparedStatement.executeUpdate();
        }
            catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("lỗi database "+ e.getMessage());
            }
    }

    public List<Novel> getNovelBySearch(String key){
        List<Novel> result = new ArrayList<>();
        String query = "SELECT n.title, n.cover_image, n.description, n.status, a.pen_name, n.id_novel, a.id_author " +
                "FROM novel AS n " +
                "LEFT JOIN author AS a ON a.id_author = n.id_author " +
                "WHERE n.title ILIKE ?";
        try(Connection connection = DBConnect.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, "%"+key+"%");
            try(ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                Author author = new Author();
                Novel novel = new Novel();
                CategoryDao categoryDao = new CategoryDao();


                String titleNovel = resultSet.getString("title");
                String cover_image = resultSet.getString("cover_image");
                String description = resultSet.getString("description");
                String penName = resultSet.getString("pen_name");
                String status = resultSet.getString("status");
                int idNovel = resultSet.getInt("id_novel");
                int id_author = resultSet.getInt("id_author");

                List<Category> categoryList = categoryDao.getListCategoryByIdNovel(idNovel);

                author.setPername(penName);
                author.setIdAuthor(id_author);

                novel.setIdNovel(idNovel);
                novel.setTitle(titleNovel);
                novel.setCoverImage(cover_image);
                novel.setDescription(description);
                novel.setAuthor(author);
                novel.setStatus(NovelStatus.valueOf(status.toUpperCase()));
                novel.getCategoryList().addAll(categoryList);

                result.add(novel);

            }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

}
