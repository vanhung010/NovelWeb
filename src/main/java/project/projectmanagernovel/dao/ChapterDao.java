package project.projectmanagernovel.dao;

import org.w3c.dom.stylesheets.LinkStyle;
import project.projectmanagernovel.entry.Chapter;
import project.projectmanagernovel.util.DBConnect;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChapterDao {
    //giao diện chi tiết truyện, lấy soos chap truyện dựa trên page
    public List<Chapter> getChapterByIdPage(int id_novel, int page){
        List<Chapter> result = new ArrayList<>();
        int limit = 12;
        int offset = (page-1) * limit;

        String query = "SELECT id_chapter, chapter_number, title " +
                "FROM chapter " +
                "WHERE id_novel = ? " +
                "ORDER BY chapter_number DESC " +
                "LIMIT ? OFFSET ?";

        // 2. SỬA LỖI LOGIC JDBC: Không gọi executeQuery ở đây
        try(Connection connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            // Gắn giá trị vào ?
            preparedStatement.setInt(1, id_novel);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);

            // 3. GỌI executeQuery SAU KHI ĐÃ GẮN GIÁ TRỊ
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()){
                    Chapter chapter = new Chapter();
                    chapter.setIdChapter(resultSet.getInt("id_chapter"));
                    chapter.setChapterNumber(resultSet.getInt("chapter_number"));
                    chapter.setTitle(resultSet.getString("title"));
                    result.add(chapter);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Lỗi database: "+ e.getMessage());
        }

        return result;
    }

    // Giao diện chi tiết truyện, lấy tổng chap truyện
    public int getTotalChapterOfNovel(int id_novel) {
        int result = 0;
        String query = "SELECT COUNT(id_chapter) AS total_chapter " +
                "FROM chapter " +
                "WHERE id_novel = ?";


        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {


            preparedStatement.setInt(1, id_novel);


            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    result = resultSet.getInt("total_chapter");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi database: " + e.getMessage());
        }

        return result;
    }

    //giao diện đọc, lấy chap truyện
    public Chapter getDetailChapter(int id_novel ,int id_chapter){
        Chapter chapter = null;
        String query = "SELECT * FROM chapter " +
                "WHERE id_chapter = ? AND id_novel = ?";
        try(Connection connection = DBConnect.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id_chapter);
            preparedStatement.setInt(2, id_novel);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
               if(resultSet.next()){
                   chapter = new Chapter();
                   NovelDao novelDao = new NovelDao();
                   chapter.setIdChapter(resultSet.getInt("id_chapter"));
                   chapter.setTitle(resultSet.getString("title"));
                   chapter.setContent(resultSet.getString("content"));
                   chapter.setChapterNumber(resultSet.getInt("chapter_number"));
                   chapter.setNovel(novelDao.getNameNovel(id_novel));
                   chapter.setWordCount(resultSet.getInt("word_count"));
               }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("lỗi database "+ e.getMessage());
        }
        return chapter;
    }

    //lấy id của chương chuyện tiếp theo
    public int nextChapterId(int id_novel, int current_chapter){
        int nextIdChapter = 0;
        String query = "SELECT id_chapter " +
                "FROM chapter " +
                "WHERE id_novel = ? AND chapter_number > ? " +
                        "ORDER BY chapter_number limit 1";
        try(Connection connection = DBConnect.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,id_novel);
            preparedStatement.setInt(2, current_chapter);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    nextIdChapter = resultSet.getInt("id_chapter");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("lỗi data base"+ e.getMessage());
        }
        return nextIdChapter;
    }

    //lấy id của chương truyện trước đó
    public int previousChapterId(int id_novel, int current_chapter){
        int nextIdChapter = 0;
        String query = "SELECT id_chapter " +
                "FROM chapter " +
                "WHERE id_novel = ? AND chapter_number < ? " +
                "ORDER BY chapter_number DESC limit 1";
        try(Connection connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,id_novel);
            preparedStatement.setInt(2, current_chapter);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    nextIdChapter = resultSet.getInt("id_chapter");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("lỗi data base"+ e.getMessage());
        }
        return nextIdChapter;
    }
}
