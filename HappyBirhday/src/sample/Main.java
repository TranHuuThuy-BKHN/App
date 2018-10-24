package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.util.ArrayList;


public class Main extends Application{
    public static Stage primaryStage;
    public static Scene scene;
    public static ArrayList<Person> listPerson = new ArrayList<>();
    public static ArrayList<Person> listDOB = new ArrayList<>();
    public static ArrayList<Person> listDOBMess = new ArrayList<>();
    public static ListView list;
    public static Button ok_save;
    public static TextField addName;
    public static ChoiceBox addSex;
    public static DatePicker addDOB;
    public static TextField addAddress;
    public static TextField addEmail;
    public static TextField addPhone;
    public static StackPane image;
    public static StackPane avatar;
    public static Button bell;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        primaryStage.setTitle("Information @author Trần Hữu Thúy");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResource("Images/android.png").toExternalForm()));
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        Main.scene = new Scene(root);
        primaryStage.setScene(Main.scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
