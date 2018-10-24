package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import sample.ConnectDatabase.ReadDataStart;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {
    String path = "face2.png";
    @FXML
    private ChoiceBox addSex;
    @FXML
    private Button back;
    @FXML
    private Button ok;
    @FXML
    private Button file;
    @FXML
    private Button camera;
    @FXML
    private TextField addName;
    @FXML
    private TextField addPhone;
    @FXML
    private TextField addEmail;
    @FXML
    private TextField addAddress;
    @FXML
    private DatePicker addBirthday;
    @FXML
    private StackPane image;

    public Controller2() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Circle cir = new Circle(90);
        cir.setFill(new ImagePattern(new Image(getClass().getResource("Images/face2.png").toExternalForm())));
        cir.setStroke(Color.BLUE);
        image.getChildren().add(cir);
        Main.image = image;

        Main.addName = addName;
        addName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("") == false &&
                    (addEmail.getText().equals("") || addEmail.getText().matches(".+.@gmail.com")) &&
                    (addPhone.getText().equals("") || addPhone.getText().matches("\\d{10,11}")))
                ok.setDisable(false);
            else ok.setDisable(true);
        });

        ObservableList<String> sexs = FXCollections.observableArrayList("Nam", "Nữ");
        addSex.setValue(sexs.get(0));
        addSex.setItems(sexs);
        Main.addSex = addSex;

        addBirthday.setValue(LocalDate.of(1998, 1, 18));
        Main.addDOB = addBirthday;

        addEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if ((newValue.equals("") || newValue.matches(".+.@gmail.com")) && addName.getText().equals("") == false
                    && (addPhone.getText().equals("") || addPhone.getText().matches("\\d{10,11}"))) {
                ok.setDisable(false);
            } else ok.setDisable(true);
        });
        Main.addEmail = addEmail;

        addPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            if ((addPhone.getText().equals("") || addPhone.getText().matches("\\d{10,11}")) && addName.getText().equals("") == false
                    && (addEmail.getText().equals("") || addEmail.getText().matches(".+.@gmail.com"))) {
                ok.setDisable(false);
            } else ok.setDisable(true);
        });
        Main.addPhone = addPhone;

        Main.ok_save = ok;
        ok.setGraphic(new ImageView(new Image(getClass().getResource("Images/ok.png").toExternalForm(), 20, 20, true, true)));
        ok.setOnAction(event -> {
            if (ok.getText().equals("Ok")) {
                Person p = new Person();
                p.setName(addName.getText());
                p.setSex(addSex.getValue().toString());
                p.setDateOfBirth(addBirthday.getValue().getYear() + "/" + addBirthday.getValue().getMonthValue() + "/" + addBirthday.getValue().getDayOfMonth());
                p.setEmail(addEmail.getText());
                if (addEmail.getText().equals("")) p.setEmail("unknow");
                p.setPhone(addPhone.getText());
                if (addPhone.getText().equals("")) p.setPhone("unknow");
                p.setAddress(addAddress.getText());
                if (addAddress.getText().equals("")) p.setAddress("unknow");
                try {
                    ReadDataStart.addPerson(p, new FileInputStream(new File(path)));
                    //thêm vào bảng hiển thị
                    ReadDataStart.readStart(Main.listPerson);
                    ViewList v = new ViewList(Main.listPerson.get(Main.listPerson.size() - 1));
                    Main.list.getItems().add(v);
                    //thông báo hiển thị thành công
                    Alert sucess = new Alert(Alert.AlertType.INFORMATION);
                    sucess.setHeaderText(null);
                    sucess.setContentText("Thêm thành công !");
                    sucess.showAndWait();
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setHeaderText(null);
                    error.setContentText("Không thành công!");
                    error.showAndWait();
                }
            } else {

                Alert message = new Alert(Alert.AlertType.INFORMATION);
                message.setHeaderText(null);
                message.setContentText("Bạn muốn thay đổi thông tin ?");
                ButtonType ok = new ButtonType("Đồng ý");
                ButtonType cancle = new ButtonType("Hủy bỏ");
                message.getButtonTypes().clear();
                message.getButtonTypes().addAll(ok, cancle);
                Optional<ButtonType> selection = message.showAndWait();
                if (selection.get() == ok) {
                    try {
                        ViewList v = (ViewList)Main.list.getSelectionModel().getSelectedItem();
                        Person p = v.getPerson();
                        FileInputStream file;
                        if (path.equals("face2.png") == false) {
                            file = new FileInputStream(new File(path));
                            ReadDataStart.updatePerson(p.getID(), addName.getText(), addSex.getValue().toString(),
                                    addBirthday.getValue(), addAddress.getText(), addEmail.getText(), addPhone.getText(), file);

                        } else {
                            ReadDataStart.updatePerson(p.getID(), addName.getText(), addSex.getValue().toString(),
                                    addBirthday.getValue(), addAddress.getText(), addEmail.getText(), addPhone.getText());
                        }

                        new ReadDataStart().readPersonID(p);
                        Main.list.getItems().remove(Main.list.getSelectionModel().getSelectedIndex());
                        Main.list.getItems().add(new ViewList(p));
                        Main.list.getSelectionModel().select(0);

                        Alert dilog = new Alert(Alert.AlertType.INFORMATION);
                        dilog.setHeaderText(null);
                        dilog.setContentText("Cật nhật thông tin thành công !");
                        dilog.showAndWait();
                    } catch (SQLException | IOException e) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setHeaderText(null);
                        error.setContentText("Cật nhật KHÔNG thành công !");
                        error.showAndWait();
                        e.printStackTrace();
                    }
                }

            }
        });

        Main.addAddress = addAddress;

        back.setGraphic(new ImageView(new Image(getClass().getResource("Images/back.png").toExternalForm(), 20, 20, true, true)));
        back.setOnAction(event -> {
            Main.primaryStage.setScene(Main.scene);
            Main.primaryStage.setTitle("Information @author Trần Hữu Thúy");
        });

        file.setGraphic(new ImageView(new Image(getClass().getResource("Images/imageFile.png").toExternalForm(), 20, 20, true, true)));
        file.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn ảnh đại diện");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
            File file1 = fileChooser.showOpenDialog(Main.primaryStage);

            if (file1 != null) {
                path = file1.getAbsolutePath();
                try {
                    Circle c = new Circle(90);
                    c.setFill(new ImagePattern(new Image(new FileInputStream(new File(path)))));
                    c.setStroke(Color.BLUE);
                    image.getChildren().clear();
                    image.getChildren().add(c);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        camera.setGraphic(new ImageView(new Image(getClass().getResource("Images/camera.png").toExternalForm(), 20, 20, true, true)));
    }
}
