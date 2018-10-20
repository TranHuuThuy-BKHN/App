package Client1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import remotemessage.RemoteMessage;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Controller {
    @FXML
    private TextArea message;
    @FXML
    private TextArea mess;
    @FXML
    private Button send;
    @FXML
    private VBox vbox;
    @FXML
    private Label label;

    public Controller() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                    try {
                        Remote message = (RemoteMessage) Naming.lookup("Message");
                        String s = ((RemoteMessage) message).receive();
                        if (s.length() > this.message.getText().length()) {
                            this.message.setText(s);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Server is NOT running");
                        System.exit(0);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void sendMessage(ActionEvent event) throws RemoteException, MalformedURLException, NotBoundException {
        Remote message = null;
        message = (RemoteMessage) Naming.lookup("Message");
        ((RemoteMessage) message).send(vbox.getScene().getUserData() + " :" + mess.getText() + "\n");
        String s = ((RemoteMessage) message).receive();
        this.message.setText(s);
    }

    @FXML
    public void initialize() {
        message.setText("");
        mess.setText("");
    }
}
