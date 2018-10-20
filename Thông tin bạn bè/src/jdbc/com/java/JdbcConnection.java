package jdbc.com.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class JdbcConnection {

    public static Connection getConnection() {
        final String url = "jdbc:mysql://localhost:3306/databaseperson";
        final String user = "TranHuuThuy";
        final String password = "tranhuuthuy";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi kết nối", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đăng kí Driver", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
