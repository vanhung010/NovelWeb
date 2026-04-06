package project.projectmanagernovel.dao;

import org.mindrot.jbcrypt.BCrypt;
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

            String queryAccount = "UPDATE INTO account (username, password, email, id_role) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatementAccount = conn.prepareStatement(queryAccount, PreparedStatement.RETURN_GENERATED_KEYS);
            //set value
            preparedStatementAccount.setString(1, userName);
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
                String queryAuthor = "UPDATE INTO author (id_account, pen_name) " +
                        "VALUES (?, ?)";
                PreparedStatement preparedStatementAuthor = conn.prepareStatement(queryAuthor);
                preparedStatementAuthor.setInt(1, idAccount);
                preparedStatementAuthor.setString(2, userName);
                preparedStatementAuthor.executeUpdate();
            }
            else if(idRole == 3) {
                String queryReader = "UPDATE INTO reader (id_account, displayname) " +
                        "VALUES (?, ?)";
                PreparedStatement preparedStatementReader = conn.prepareStatement(queryReader);
                preparedStatementReader.setInt(1, idAccount);
                preparedStatementReader.setString(2, userName);
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
}
