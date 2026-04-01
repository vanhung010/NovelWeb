package project.projectmanagernovel.dao;

import project.projectmanagernovel.entry.Author;
import project.projectmanagernovel.entry.Category;
import project.projectmanagernovel.entry.Chapter;
import project.projectmanagernovel.entry.Novel;
import project.projectmanagernovel.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NovelDao {

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

    public boolean deletedNonel(Integer idNovel){
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
                "WHERE n.status = 'Completed' " +
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
                novel.setStatus(resultSet.getString("status"));

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

        // SỬA LỖI 1: Thêm bí danh (AS novel_title, AS chapter_title) để phân biệt 2 cột
        String query = "SELECT n.id_novel, n.title AS novel_title, chap.title AS chapter_title, chap.created_at, chap.chapter_number, at.pen_name " +
                "FROM chapter AS chap " +
                "LEFT JOIN novel AS n ON chap.id_novel = n.id_novel " +
                "LEFT JOIN author AS at ON n.id_author = at.id_author " +
                "ORDER BY chap.created_at DESC " +
                "LIMIT 3";

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

                // SỬA LỖI 3: Bổ sung set Tên Truyện (novel_title)
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
    public Novel getDetailNovel(int idNovels){
        Novel novel = new Novel();
        String query = "SELECT n.title, n.cover_image,n.discription, n.status, at.pen_name, ct.name, c.Max(chapter_number) AS max_chapter, n.total_views " +
                "FROM novel AS n " +
                "JOIN LEFT author AS at ON n.id_author = at.id_author " +
                "JOIN LEFT novel_category AS ct ON ct.id_novel = n.id_novel " +
                "JOIN LEFT chapter AS c ON c.id_novel = n.id_novel " +
                "WHERE n.id_novel = ?";
        try(Connection connection = DBConnect.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery()){
            preparedStatement.setInt(idNovels);
            Chapter chapter = new Chapter();
            Author author = new Author();

            //gắn giá trị
            //gắn giá trị novel
            novel.setTitle(resultSet.getString("title"));
            novel.setDescription(resultSet.getString("discription"));
            novel.setStatus(resultSet.getString("status"));
            novel.setTotalViews(resultSet.getInt("total_views"));
            novel.getChapterList().add(chapter)
            //gắn giá trị chapter
            chapter.setChapterNumber(resultSet.getInt("max_chapter"));
            //gắn giá trị author
            author.setPername(resultSet.getString("pen_name"));

        }
    }
}
