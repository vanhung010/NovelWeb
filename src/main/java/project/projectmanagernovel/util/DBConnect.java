package project.projectmanagernovel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static String url = "jdbc:postgresql://localhost:5432/ManagerStory";
    private static  String user = "postgres";
    private  static String password = "mw500wtl";

    private DBConnect() {
    }
    public static Connection getConnection()  {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("keets nối db thành công");
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối database" + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }


}
