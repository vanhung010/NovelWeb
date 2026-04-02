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
}
