package jdbc.com.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class JdbcStatement {

    public static void addPerson(String name, String address, String phone) {
        Connection connection = JdbcConnection.getConnection();
        final String sql = "insert into person(Name, Address, Phone) values(?, ?, ?)";
        try {
            try (PreparedStatement prep = connection.prepareStatement(sql)) {
                prep.setString(1, name);
                prep.setString(2, address);
                prep.setString(3, phone);

                prep.executeUpdate();
                System.out.println("Add Sucessful...");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thể thực hiện câu lệnh SQL !", "Error", JOptionPane.ERROR_MESSAGE);
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đóng kết nối thất bại");
        }
    }

    public static void deletePeron(int[] ID) {
        Connection connection = JdbcConnection.getConnection();
        final String sql = "delete from databaseperson.person where ID = ?";

        try {
            try (PreparedStatement prep = connection.prepareStatement(sql)) {
                for (int i : ID) {
                    prep.setInt(1, i);
                    prep.executeUpdate();
                }
                System.out.println("Delete Sucessful...");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thể thực hiện câu lệnh SQL !", "Error", JOptionPane.ERROR_MESSAGE);
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đóng kết nối thất bại");
        }
    }

    public static void updatePerson(int ID, String name, String address, String phone) {
        Connection connection = JdbcConnection.getConnection();
        final String sql = "update databaseperson.person set Name = ?, Address = ?, Phone = ? where ID = ?";
        try {
            try (PreparedStatement prep = connection.prepareStatement(sql)) {
                prep.setString(1, name);
                prep.setString(2, address);
                prep.setString(3, phone);
                prep.setInt(4, ID);
                prep.executeUpdate();
                System.out.println("Update Sucessful...");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thể thực hiện câu lệnh SQL !", "Error", JOptionPane.ERROR_MESSAGE);
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đóng kết nối thất bại");
        }
    }

    public static void readPeople(ArrayList<Person> people) {
        Connection connection = JdbcConnection.getConnection();
        final String sql = "select * from databaseperson.person";
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(sql);

            people.clear();
            while (res.next()) {
                Person p = new Person(res.getInt(1), res.getString(2), res.getString(3), res.getString(4));
                people.add(p);
            }
            statement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đọc dữ liêu ban đầu không thành công !", "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đóng kết nối thất bại");
        }
    }

    public static void resetData() {
        Connection connection = JdbcConnection.getConnection();
        try {
            try (Statement statement = connection.createStatement()) {
                String sql = "truncate table person";
                statement.executeUpdate(sql);
                sql = "alter table person auto_increment = 1";
                statement.executeUpdate(sql);
                System.out.println("Truncate Sucessful...");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thể thực hiện câu lện SQL !", "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đóng kết nối thất bại", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
