package project.projectmanagernovel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static String url = "jdbc:postgresql://ep-fragrant-paper-amhhwia9-pooler.c-5.us-east-1.aws.neon.tech/neondb?user=neondb_owner&password=npg_seZvko9Ih4Ql&sslmode=require&channelBinding=require";


    private DBConnect() {
    }
    public static Connection getConnection()  {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
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
