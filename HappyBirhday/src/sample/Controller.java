package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import sample.AlarmClock.AlarmClock;
import sample.ConnectDatabase.ReadDataStart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("happybirthday.mp3").toURI().toString()));

    @FXML
    private Button bell;
    @FXML
    private Button setting;
    @FXML
    private Button add;
    @FXML
    private Button sub;
    @FXML
    private Button update;
    @FXML
    private Button reset;
    @FXML
    private Label textName;
    @FXML
    private Label textSex;
    @FXML
    private Label textDOB;
    @FXML
    private Label textAddress;
    @FXML
    private Label textEmail;
    @FXML
    private Label textPhone;
    @FXML
    private StackPane avartar;

    @FXML
    private ListView listPerson;


    public Controller() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.bell = bell;
        bell.setGraphic(new ImageView(new Image(getClass().getResource("Images/bell.png").toExternalForm(), 20, 20, true, true)));
        bell.setTooltip(new Tooltip("Danh sách thông báo"));
        bell.setOnAction(event -> {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setHeaderText("Chúc mừng sinh nhật các bạn :");
            dialog.setGraphic(new ImageView(new Image(getClass().getResource("Images/birthday.png").toExternalForm(), 30, 30, true, true)));
            String s = "";
            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH), month = c.get(Calendar.MONTH) + 1;

            for (int j = 0; j < Main.list.getItems().size(); j++) {
                String str[] = ((ViewList) Main.list.getItems().get(j)).getPerson().getDateOfBirth().split("/");
                if (str.length > 2 && day == Integer.parseInt(str[2]) && Integer.parseInt(str[1]) == month) {
                    s += ((ViewList) Main.list.getItems().get(j)).getPerson().getName() + "\n";
                }
            }
            dialog.setContentText(s);
            dialog.showAndWait();
        });

        setting.setGraphic(new ImageView(new Image(getClass().getResource("Images/setting.png").toExternalForm(), 20, 20, true, true)));
        setting.setOnAction(event -> {
            Alert audioSetting = new Alert(Alert.AlertType.INFORMATION);
            audioSetting.setGraphic(new ImageView(new Image(getClass().getResource("Images/setting.png").toExternalForm(), 20, 20, true, true)));
            audioSetting.setTitle("Âm báo");
            audioSetting.setHeaderText("Chọn file báo thức");
            audioSetting.getDialogPane().setPrefWidth(300);
            audioSetting.getDialogPane().setMaxWidth(300);

            Audio a1 = new Audio(new File("baothuc.mp3"));
            Audio a2 = new Audio(new File("happybirthday.mp3"));

            ObservableList<Audio> list = FXCollections.observableArrayList(a2, a1);

            ChoiceBox files = new ChoiceBox(list);
            files.getSelectionModel().select(0);
            files.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.equals(oldValue) == false) {
                    mediaPlayer = new MediaPlayer(new Media(((Audio) newValue).getFile().toURI().toString()));
                }
            });

            Button br = new Button("file");
            br.setGraphic(new ImageView(new Image(getClass().getResource("Images/imageFile.png").toExternalForm(), 13, 13, true, true)));
            br.setPrefWidth(60);
            Button show = new Button("show");
            show.setGraphic(new ImageView(new Image(getClass().getResource("Images/notNhac.png").toExternalForm(), 13, 13, true, true)));
            show.setPrefWidth(65);

            br.setOnAction(event1 -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Chọn file .mp3");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3", "*mp3"));
                File file = fileChooser.showOpenDialog(Main.primaryStage);
                if (file != null) {
                    Audio audio = new Audio(file);
                    files.getItems().add(audio);
                }
            });

            show.setOnAction(event1 -> {
                if (show.getText().equals("show")) {
                    show.setText("pause");
                    mediaPlayer.play();
                } else {
                    show.setText("show");
                    mediaPlayer.stop();
                }
            });
            HBox hBox = new HBox(files, br, show);
            hBox.setSpacing(10);

            audioSetting.getDialogPane().setContent(hBox);

            ButtonType ok = new ButtonType("Đồng ý");
            ButtonType cancel = new ButtonType("Hủy bỏ");

            audioSetting.getButtonTypes().clear();
            audioSetting.getButtonTypes().addAll(ok, cancel);
            Optional<ButtonType> selection = audioSetting.showAndWait();
            if (selection.get() == ok) {
                AlarmClock.mediaPlayer = new MediaPlayer(new Media(((Audio) files.getValue()).getFile().toURI().toString()));
                mediaPlayer.stop();
            }
        });

        new AlarmClock(Main.listDOB);

        add.setGraphic(new ImageView(new Image(getClass().getResource("Images/add.png").toExternalForm(), 12, 12, true, true)));
        add.setTooltip(new Tooltip("Thêm thông tin bạn bè"));
        add.setShape(new Circle(0.7));
        add.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("add.fxml"));
                Scene scene = new Scene(root);
                Main.primaryStage.setScene(scene);
                Main.primaryStage.setTitle("Thêm thông tin bạn bè");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sub.setGraphic(new ImageView(new Image(getClass().getResource("Images/sub.png").toExternalForm(), 12, 12, true, true)));
        sub.setTooltip(new Tooltip("Xóa thông tin bạn bè"));
        sub.setShape(new Circle(0.7));
        sub.setOnAction(event -> {
            if (listPerson.getSelectionModel().getSelectedIndex() > -1) {
                try {
                    Person p = ((ViewList) listPerson.getSelectionModel().getSelectedItem()).getPerson();
                    Alert dilog = new Alert(Alert.AlertType.INFORMATION);
                    dilog.setHeaderText(null);
                    dilog.setContentText("Bạn muốn xóa " + p.getName() + " khỏi danh sách ?");

                    dilog.getButtonTypes().clear();
                    ButtonType ok = new ButtonType("Đồng ý");
                    ButtonType cancel = new ButtonType("Hủy bỏ");
                    dilog.getButtonTypes().addAll(ok, cancel);

                    Optional<ButtonType> res = dilog.showAndWait();
                    if (res.get() == ok) {
                        ReadDataStart.subPerson(p.getID());
                        listPerson.getItems().remove(listPerson.getSelectionModel().getSelectedIndex());
                        Main.listPerson.remove(p);
                        if (Main.listPerson.size() != 0) {
                            listPerson.getSelectionModel().select(0);
                        } else {
                            textName.setText("unknow");
                            textSex.setText("Unknow");
                            textDOB.setText("yyyy/MM/dd");
                            textPhone.setText("xxxxxxxxxx");
                            textEmail.setText("example@gmail.com");
                            textAddress.setText("unknow");
                            Circle c = new Circle(90);
                            c.setFill(new ImagePattern(new Image(getClass().getResource("Images/face2.png").toExternalForm())));
                            avartar.getChildren().clear();
                            avartar.getChildren().add(c);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        update.setGraphic(new ImageView(new Image(getClass().getResource("Images/update.png").toExternalForm(), 12, 12, true, true)));
        update.setTooltip(new Tooltip("Cập nhât thông tin bạn bè"));
        update.setShape(new Circle(0.7));
        update.setOnAction(event -> {
            if (listPerson.getSelectionModel().getSelectedIndex() > -1) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("add.fxml"));
                    Scene scene = new Scene(root);
                    Main.ok_save.setText("Save");
                    Main.primaryStage.setScene(scene);
                    Main.primaryStage.setTitle("Cật nhật thông tin");
                    Person p = ((ViewList) Main.list.getSelectionModel().getSelectedItem()).getPerson();
                    Main.addName.setText(p.getName());
                    Main.addSex.setValue(p.getSex());
                    Main.addAddress.setText(p.getAddress());
                    Main.addEmail.setText(p.getEmail());
                    Main.addPhone.setText(p.getPhone());
                    String s[] = p.getDateOfBirth().split("/");
                    Main.addDOB.setValue(LocalDate.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])));
                    Main.image.getChildren().clear();
                    Circle c = new Circle(90);
                    c.setFill(new ImagePattern(p.getImage()));
                    Main.image.getChildren().add(c);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        reset.setGraphic(new ImageView(new Image(getClass().getResource("Images/reset.png").toExternalForm(), 12, 12, true, true)));
        reset.setTooltip(new Tooltip("Reset toàn bộ dữ liệu"));
        reset.setShape(new Circle(0.7));
        reset.setOnAction(event -> {
            if (Main.listPerson.isEmpty() == false) {
                Alert dilog = new Alert(Alert.AlertType.INFORMATION);
                dilog.setHeaderText(null);
                dilog.setContentText("Bạn chắc chắn muốn xóa bỏ hoàn toàn dữ liệu ?");
                dilog.getButtonTypes().clear();
                ButtonType ok = new ButtonType("Đòng ý");
                ButtonType cancel = new ButtonType("Hủy bỏ");
                dilog.getButtonTypes().addAll(ok, cancel);
                Optional<ButtonType> selection = dilog.showAndWait();
                if (selection.get() == ok) {
                    try {
                        ReadDataStart.resetData();
                        listPerson.getItems().clear();
                        Main.listPerson.clear();
                        textName.setText("Trần Hữu Thúy");
                        textSex.setText("Nam");
                        textDOB.setText("1998/1/18");
                        textAddress.setText("Trung Nghĩa - TP Hưng Yên");
                        textEmail.setText("huuthuy2000@gmail.com");
                        textPhone.setText("01666524143");
                        Circle c = new Circle(90);
                        c.setFill(new ImagePattern(new Image(getClass().getResource("Images/face2.png").toExternalForm())));
                        avartar.getChildren().clear();
                        avartar.getChildren().add(c);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Circle c = new Circle(90);
        c.setStroke(Color.BLUE);
        c.setFill(new ImagePattern(new Image(getClass().getResource("Images/face2.png").toExternalForm())));
        avartar.getChildren().add(c);
        Main.avatar = avartar;

        listPerson.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listPerson.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != oldValue) {

                ViewList v = ((ViewList) listPerson.getSelectionModel().getSelectedItem());
                if (v != null) {
                    Person p = ((ViewList) listPerson.getSelectionModel().getSelectedItem()).getPerson();
                    try {
                        new ReadDataStart().readPersonID(p);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    textName.setText(p.getName());
                    textSex.setText(p.getSex());
                    textDOB.setText(p.getDateOfBirth());
                    textAddress.setText(p.getAddress());
                    textEmail.setText(p.getEmail());
                    textPhone.setText(p.getPhone());
                    Circle cir = new Circle(90);
                    cir.setStroke(Color.BLUE);
                    cir.setFill(new ImagePattern(p.getImage()));
                    avartar.getChildren().clear();
                    avartar.getChildren().add(cir);
                }
            }
        });

        Main.list = listPerson;
        //đọc dữ liệu ban đầu
        try {
            ReadDataStart.readStart(Main.listPerson);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void init() throws FileNotFoundException {
        for (int i = 0; i < Main.listPerson.size(); i++) {
            ViewList v = new ViewList(Main.listPerson.get(i));
            listPerson.getItems().add(v);
        }
    }

}
