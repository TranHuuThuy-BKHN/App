package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;



public class ViewList extends HBox {
    private Person person;
    private  Circle c;

    public ViewList(Person p) throws FileNotFoundException {
        this.person = p;
        c = new Circle(15);
        c.setFill(new ImagePattern(p.getImage()));
        StackPane sp = new StackPane(c);
        this.getChildren().addAll(c, p);
        p.setPadding(new Insets(1, 1, 1, 40));
        p.setPrefHeight(30);
        p.setTextFill(Color.BLUE);
        p.setFont(Font.font(14));
        p.setAlignment(Pos.CENTER);
    }

    public Person getPerson() {
        return person;
    }

    public Circle getC() {
        return c;
    }
}
