package com.example.snakedemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.Collections;

public class PlayerTable extends Application {
    private Scene scene;
    private StackPane layout;
    private Button dice_button;
    private Rectangle player_frame, dice_frame, card_frame, card_frame2, card_frame3;
    @Override
    public void start(Stage stage) throws Exception {
        layout = new StackPane();
        dice_button = new Button();
        player_frame = new Rectangle();
        dice_frame = new Rectangle();
        card_frame = new Rectangle();
        card_frame2 = new Rectangle();
        card_frame3 = new Rectangle();
        //player frame
        player_frame.setWidth(170);
        player_frame.setHeight(170);
        player_frame.setTranslateX(0);
        player_frame.setTranslateY(-100);
        ///dice
        dice_frame.setWidth(80);
        dice_frame.setHeight(80);
        dice_frame.setTranslateX(-120);
        dice_frame.setTranslateY(90);
        //dice button
        dice_button.setText("Roll");
        dice_button.setTranslateX(-120);
        dice_button.setTranslateY(150);
        //card frame
        card_frame.setWidth(60);
        card_frame.setHeight(72);
        card_frame.setTranslateX(-20);
        card_frame.setTranslateY(90);

        //card_frame.setFill(new ImagePattern(img));
        //card frame2
        card_frame2.setWidth(60);
        card_frame2.setHeight(72);
        card_frame2.setTranslateX(60);
        card_frame2.setTranslateY(90);
        //card frame3
        card_frame3.setWidth(60);
        card_frame3.setHeight(72);
        card_frame3.setTranslateX(140);
        card_frame3.setTranslateY(90);
        layout.getChildren().addAll(player_frame, dice_frame, dice_button, card_frame, card_frame2, card_frame3);
        scene = new Scene(layout, 400 ,400);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
