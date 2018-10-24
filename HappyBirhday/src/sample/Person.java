package sample;


import javafx.scene.control.Label;
import javafx.scene.image.Image;


public class Person extends Label implements Cloneable {
    private int id;
    private boolean check = false;
    private String name = "Trần Hữu Thúy";
    private String sex = "unknown";
    private String dateOfBirth = "1998/01/18";
    private String address = "unknown";
    private String email = "unknown";
    private String phone = "unknown";
    private Image image = new Image(getClass().getResource("Images/face.png").toExternalForm(), 30, 30, true, true);

    public Person() {

    }

    public Person(int id, String name) {
        this.name = name;
        this.id = id;
        this.setText(name);
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }
}
