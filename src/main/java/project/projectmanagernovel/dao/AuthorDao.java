package project.projectmanagernovel.dao;

import project.projectmanagernovel.entity.Author;
import project.projectmanagernovel.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDao {


    public int getTotalViewAuthorById(int id){
        int total =0;
        String query = "SELECT COALESCE(SUM(total_views), 0) AS total " +
                "FROM novel " +
                "WHERE id_author = ?";

        try(Connection connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    total = resultSet.getInt("total");
                }
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return total;

    }

    public int getTotalNovelAuthorById(int id){
        int total =0;
        String query = "SELECT COUNT(*) AS total " +
                "FROM novel " +
                "WHERE id_author = ?";

        try(Connection connection = DBConnect.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt( 1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    total = resultSet.getInt("total");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return total;
    }

    public Author getAuthorById(int id){
        Author author = new Author();
        String query = "SELECT pen_name, balance, id_author " +
                "FROM author " +
                "WHERE id_author = ?";
        try(Connection connection = DBConnect.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                author.setTotalNovel(getTotalNovelAuthorById(id));
                author.setTotalView(getTotalViewAuthorById(id));
                author.setPername(resultSet.getString("pen_name"));
                author.setBalance(resultSet.getDouble("balance"));
                author.setIdAuthor(resultSet.getInt("id_author"));

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return author;
    }
}
