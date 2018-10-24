package sample.AlarmClock;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sample.Main;
import sample.Person;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class AlarmClock {
    public static String path = new File("happybirthday.mp3").getAbsolutePath();
    public static Media m = new Media(new File(path).toURI().toString());
    public static MediaPlayer mediaPlayer = new MediaPlayer(m);

    public AlarmClock(ArrayList<Person> listDOB) {
        new Thread(() -> {
            while (true) {
                listDOB.clear();
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH), month = c.get(Calendar.MONTH) + 1;
                for (int i = 0; i < Main.listPerson.size(); i++) {
                    String s[] = Main.listPerson.get(i).getDateOfBirth().split("/");
                    if (s.length > 2 && day == Integer.parseInt(s[2]) &&
                            month == Integer.parseInt(s[1]) && Main.listPerson.get(i).isCheck() == false) {
                        Main.listPerson.get(i).setCheck(true);
                        listDOB.add(Main.listPerson.get(i));
                        if (Main.listDOBMess.contains(Main.listPerson.get(i)) == false)
                            Main.listDOBMess.add(Main.listPerson.get(i));

                    }
                }
                if (listDOB.size() > 0) {
                    new Thread(() -> {
                        mediaPlayer.play();
                    }).start();
                    JOptionPane.showMessageDialog(null, "Happy Birday !");
                    mediaPlayer.stop();
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}
