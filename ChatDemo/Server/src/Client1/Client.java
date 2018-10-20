package Client1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Client extends Application {
    private String username = "Trần Hữu Thúy", password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Message");
        primaryStage.setResizable(false);
        Image image = new Image((new FileInputStream("C:\\Users\\DELL\\IdeaProjects\\Client\\Chat-icon.png")), 40, 40, true, true);
        primaryStage.getIcons().add(image);
        Scene scene = new Scene(root);
        scene.setUserData(username);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void run(String [] args){
        launch(args);
    }

}
