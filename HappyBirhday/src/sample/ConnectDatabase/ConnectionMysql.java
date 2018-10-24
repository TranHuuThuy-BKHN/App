package sample.ConnectDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMysql {
    public static Connection getConnection() {
        final String url = "jdbc:mysql://localhost:3306/quanlibanbe";
        final String user = "TranHuuThuy";
        final String pass = "tranhuuthuy";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                return DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
