package project.projectmanagernovel.dao;

import project.projectmanagernovel.entity.Category;
import project.projectmanagernovel.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    //thêm thể loại
    public void addCategoryDao(Category category){
        String query = "INSERT INTO category(name, description) "+
                "VALUES (?,?,?)";
        try(PreparedStatement preparedStatement = DBConnect.getConnection().prepareStatement(query)){

            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            int affectRow = preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Lỗi database "+ e.getMessage());
        }
    }
    //cập nhật thông tin thể loại
    public void updateCategoryDao(Category category){
        String query = "UPDATE category SET name = ?, description = ? " +
                "WHERE id_category = ?";
        try(Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, category.getIdCategory());
            int rowAffect = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("lỗi db "+  e.getMessage());
        }
    }

    //lấy danh sách thể loại của truyện
    public List<Category> getListCategoryByIdNovel(Integer idnovel){
        ArrayList<Category> result = new ArrayList<>();
        String query = "SELECT c.id_category, c.name, c.description " +
                "FROM category c " +
                "INNER JOIN novel_category nc ON c.id_category = nc.id_category " +
                "WHERE nc.id_category = ?";
        try(Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,idnovel);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result.add(new Category(resultSet.getInt("id_category"), resultSet.getString("name"), resultSet.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("lỗi database" + e.getMessage());
        }
        return  result;
    }

    // Lấy danh sách tất cả thể loại
    public List<Category> getAllCategories() { // Đổi tên hàm thành số nhiều

        List<Category> result = new ArrayList<>();
        String query = "SELECT id_category, name, description FROM category";


        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                result.add(new Category(
                        resultSet.getInt("id_category"),
                        resultSet.getString("name"),
                        resultSet.getString("description")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Lỗi tại getAllCategories: " + e.getMessage());
            e.printStackTrace();
        }


        return result;
    }
}
