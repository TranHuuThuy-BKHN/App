package sample.ConnectDatabase;

import javafx.scene.image.Image;
import sample.Person;

import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReadDataStart {

    /**
     * Phương thức đọc cô sở dữ liệu ban đầu, ta chỉ đọc id, name, image
     *
     * @param listPerson
     * @throws SQLException
     * @throws IOException
     */
    public static void readStart(ArrayList<Person> listPerson) throws SQLException, IOException {
        Connection connection = ConnectionMysql.getConnection();
        final String sql = "select id, name, image, dob from person";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        listPerson.clear();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            Person p = new Person(id, name);
            p.setDateOfBirth(resultSet.getString(4));

            Blob image = resultSet.getBlob(3);
            byte[] b = image.getBytes(1, (int) (image.length()));
            FileOutputStream fileOutputStream = new FileOutputStream("thuy" + id + ".jpg");
            fileOutputStream.write(b);
            p.setImage(new Image(new FileInputStream("thuy" + p.getID() + ".jpg")));
            listPerson.add(p);
            fileOutputStream.close();
        }
        statement.close();
        connection.close();
    }

    /**
     * phương thức đọc tất cả thông tin cảu một ng khi biết id
     *
     * @param p
     * @throws SQLException
     * @throws IOException
     */
    public void readPersonID(Person p) throws SQLException, IOException {
        final String sql = "select * from person where id = ?";
        Connection connection = ConnectionMysql.getConnection();
        PreparedStatement pre = connection.prepareStatement(sql);
        pre.setInt(1, p.getID());
        ResultSet resultSet = pre.executeQuery();
        while (resultSet.next()) {
            p.setName(resultSet.getString(2));
            p.setSex(resultSet.getString(3));
            p.setDateOfBirth(resultSet.getString(4));
            p.setEmail(resultSet.getString(5));
            p.setPhone(resultSet.getString(6));
            Blob b = resultSet.getBlob(7);
            byte[] bytes = b.getBytes(1, (int) b.length());
            FileOutputStream file = new FileOutputStream(new File("thuy" + p.getID() + ".jpg"));
            file.write(bytes);
            p.setImage(new Image(new FileInputStream("thuy" + p.getID() + ".jpg")));
            p.setAddress(resultSet.getString(8));
        }
    }

    /**
     * phương thức thêm một person vào cơ sở dũ liệu
     *
     * @param p
     * @param file dữ liệu ảnh
     * @throws SQLException
     */
    public static void addPerson(Person p, FileInputStream file) throws SQLException {
        Connection connection = ConnectionMysql.getConnection();

        final String sql = "insert into person(name, sex, dob, email, phone, image, address) values(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prep = connection.prepareStatement(sql);

        connection.setAutoCommit(false);
        prep.setString(1, p.getName());
        prep.setString(2, p.getSex());
        prep.setString(3, p.getDateOfBirth());
        prep.setString(4, p.getEmail());
        prep.setString(5, p.getPhone());
        prep.setBinaryStream(6, file);
        prep.setString(7, p.getAddress());
        prep.executeUpdate();
        connection.commit();
        prep.close();
        connection.close();

    }

    /**
     * phương thức xóa một person khỏi có sở dữ liệu theo id
     *
     * @param id
     */
    public static void subPerson(int id) throws SQLException {
        Connection connection = ConnectionMysql.getConnection();
        final String sql = "delete from person where id = ?";
        PreparedStatement prep = connection.prepareStatement(sql);
        prep.setInt(1, id);
        prep.executeUpdate();
        prep.close();
        connection.close();
    }

    public static void updatePerson(int id, String name, String sex, LocalDate date, String address, String email, String phone, FileInputStream file) throws SQLException {
        Connection connection = ConnectionMysql.getConnection();
        final String sql = "update person set name = ?, sex = ?, dob = ?, address = ?, email = ?, phone = ?, image = ? where id = ?";
        PreparedStatement prep = connection.prepareStatement(sql);
        prep.setString(1, name);
        prep.setString(2, sex);
        String s = date.getYear() + "/" + date.getMonthValue() + "/" + date.getDayOfMonth();
        prep.setString(3, s);
        if (address.equals("")) address = "unknow";
        prep.setString(4, address);
        if (email.equals("")) email = "unknow";
        prep.setString(5, email);
        if (phone.equals("")) phone = "unknow";
        prep.setString(6, phone);
        prep.setBinaryStream(7, file);
        prep.setInt(8, id);
        prep.executeUpdate();
        prep.close();
        connection.close();
    }

    public static void updatePerson(int id, String name, String sex, LocalDate date, String address, String email, String phone) throws SQLException {
        Connection connection = ConnectionMysql.getConnection();
        final String sql = "update person set name = ?, sex = ?, dob = ?, address = ?, email = ?, phone = ? where id = ?";
        PreparedStatement prep = connection.prepareStatement(sql);
        prep.setString(1, name);
        prep.setString(2, sex);
        String s = date.getYear() + "/" + date.getMonthValue() + "/" + date.getDayOfMonth();
        prep.setString(3, s);
        if (address.equals("")) address = "unknow";
        prep.setString(4, address);
        if (email.equals("")) email = "unknow";
        prep.setString(5, email);
        if (phone.equals("")) phone = "unknow";
        prep.setString(6, phone);
        prep.setInt(7, id);
        prep.executeUpdate();
        prep.close();
        connection.close();
    }

    public static void resetData() throws SQLException {
        Connection connection = ConnectionMysql.getConnection();
        final String sql = "truncate table person";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
        connection.close();
    }
}
