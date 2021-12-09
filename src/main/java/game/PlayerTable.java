package game;


import javafx.application.Application;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.image.*;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;


import java.util.Random;



public class PlayerTable {
    private Random random;
    private StackPane layout;
    private Button dice_button;
    private Rectangle player_frame, dice_frame, card_frame, card_frame2, card_frame3, inner_frame;
    public PlayerTable() {

        layout = new StackPane();
        dice_button = new Button();
        player_frame = new Rectangle();
        inner_frame = new Rectangle();
        dice_frame = new Rectangle();
        card_frame = new Rectangle();
        card_frame2 = new Rectangle();
        card_frame3 = new Rectangle();
        random = new Random();

        player_frame.setWidth(170);
        player_frame.setHeight(170);
        player_frame.setTranslateX(0);
        player_frame.setTranslateY(-100);
        Image im1 = new Image(getClass().getResourceAsStream("/img/blog.png"));
        player_frame.setFill(new ImagePattern(im1));
        //inner
        inner_frame.setWidth(120);
        inner_frame.setHeight(120);
        inner_frame.setTranslateX(0);
        inner_frame.setTranslateY(-99);
        Image im2 = new Image(getClass().getResourceAsStream("/img/map1.jpg"));
        inner_frame.setFill(new ImagePattern(im2));
        ///dice
        dice_frame.setWidth(80);
        dice_frame.setHeight(80);
        dice_frame.setTranslateX(-120);
        dice_frame.setTranslateY(90);
        Image im3 = new Image(getClass().getResourceAsStream("/img/dice/dice1.png"));
        dice_frame.setFill(new ImagePattern(im3));
        //dice button
        dice_button.setText("Roll");
        dice_button.setTranslateX(-120);
        dice_button.setTranslateY(150);
        //System.out.println("/img/dice/dice"+(random.nextInt(6)+1)+".png");

        dice_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dice_button.setDisable(true);
                Thread thread = new Thread(){
                    public void run(){
                System.out.println("Thread run");
                try{
                    for (int i = 0;i < 15; i++){
                        String url = new String();
                        int k = random.nextInt(6)+1;
                        url = "/img/dice/dice"+(k)+".png";
                        System.out.println(url+"-alert");
                        Image dm = new Image(getClass().getResourceAsStream(url));
                        dice_frame.setFill(new ImagePattern(dm));
                        Thread.sleep(50);
                    }
                    dice_button.setDisable(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}
            };
                thread.start();
        }});
        //card frame
        card_frame.setWidth(60);
        card_frame.setHeight(72);
        card_frame.setTranslateX(-20);
        card_frame.setTranslateY(90);
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
        layout.getChildren().addAll(player_frame,inner_frame, dice_frame, dice_button, card_frame, card_frame2, card_frame3);
//        scene = new Scene(layout, 400 ,400);
//        stage.setScene(scene);
//        stage.show();
//    }
//    public static void main(String[] args) {
//        launch();
    }


    public StackPane getLayout() {
        System.out.println(layout == null);
        return layout;

    }
}
