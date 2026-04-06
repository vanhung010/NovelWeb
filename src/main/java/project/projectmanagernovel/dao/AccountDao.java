package project.projectmanagernovel.dao;

import org.mindrot.jbcrypt.BCrypt;
import project.projectmanagernovel.entity.Account;
import project.projectmanagernovel.entity.Author;
import project.projectmanagernovel.entity.Role;
import project.projectmanagernovel.entity.RoleType;
import project.projectmanagernovel.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDao {

    public boolean addAccount(String userName, String plainPassword, String email, String roleType){
        boolean isSuccess = false;
        Connection conn = null;

        try{
            conn = DBConnect.getConnection();

            conn.setAutoCommit(false);    //không tự động commit

            String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));

            int idRole = roleType.equalsIgnoreCase("author") ? 2 : 3;

            String queryAccount = "INSERT INTO account (username, password, email, id_role) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatementAccount = conn.prepareStatement(queryAccount, PreparedStatement.RETURN_GENERATED_KEYS);
            //set value
            preparedStatementAccount.setString(1, email);
            preparedStatementAccount.setString(2, hashedPassword);
            preparedStatementAccount.setString(3, email);
            preparedStatementAccount.setInt(4, idRole);

            int arrowEffect = preparedStatementAccount.executeUpdate();//thực thi
            if(arrowEffect == 0) System.out.println("Tạo tài khoản thấy bại");
            //lấy id của Account vừa thêm
            ResultSet rsIdAcc = preparedStatementAccount.getGeneratedKeys();
            int idAccount = 0;
            if(rsIdAcc.next()){
                idAccount = rsIdAcc.getInt(1);
            }

            if(idRole == 2){
                String queryAuthor = "INSERT INTO author (id_account, pen_name) " +
                        "VALUES (?, ?)";
                PreparedStatement preparedStatementAuthor = conn.prepareStatement(queryAuthor);
                preparedStatementAuthor.setInt(1, idAccount);
                preparedStatementAuthor.setString(2, userName);
                preparedStatementAuthor.executeUpdate();
            }
            else if(idRole == 3) {
                String queryReader = "INSERT INTO reader (id_account, displayname) " +
                        "VALUES (?, ?)";
                PreparedStatement preparedStatementReader = conn.prepareStatement(queryReader);
                preparedStatementReader.setInt(1, idAccount);
                preparedStatementReader.setString(2, userName);
                preparedStatementReader.executeUpdate();
            }

            //chạy đến đây mà không bug thì commit
            conn.commit();
            isSuccess = true;

        }
        catch (Exception e){
            e.printStackTrace();

            if(conn != null){
                try {
                    conn.rollback();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        finally {
            if(conn != null){
                try{
                    conn.setAutoCommit(true);
                    conn.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    public Account checkAccount(String email, String password){
        Account account = null;

        String query = "SELECT * FROM account AS a " +
                "LEFT JOIN author AS at ON at.id_account = a.id_account " +
                "LEFT JOIN reader AS r ON r.id_account = a.id_account "+
                "WHERE a.email = ?";
        try(Connection connection = DBConnect.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String emailAccount = resultSet.getString("email");
                String hashPassword = resultSet.getString("password");
                String displayName = resultSet.getString("username");
                int idRole = resultSet.getInt("id_role");

                boolean check = (BCrypt.checkpw(password, hashPassword) && email.equalsIgnoreCase(emailAccount));
                //nếu đăng nhập đúng
                if(check){
                    account = new Account();
                   account.setEmail(resultSet.getString("email"));
                   account.setIdAccount(resultSet.getInt("id_account"));
                   if(idRole == 2){
                       account.setPofileName(resultSet.getString("pen_name"));
                       Role role = new Role();
                       role.setIdRole(2);
                       role.setRoleName(RoleType.AUTHOR);
                       account.setRole(role);
                   }
                   else if (idRole == 3 ){
                       account.setPofileName(resultSet.getString("displayname"));
                       Role role = new Role();
                       role.setIdRole(3);
                       role.setRoleName(RoleType.READER);
                       account.setRole(role);
                   }
                }


            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("lỗi data base" + e.getMessage());
        }
        return account;
    }
}
