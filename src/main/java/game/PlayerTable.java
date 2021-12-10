package game;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.image.*;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;


import java.util.Random;



public class PlayerTable {
    private Random random;
    private int k;
    private boolean turn;
    private int ID;
    private StackPane layout;
    private Button dice_button;
    private Rectangle player_frame, dice_frame, card_frame, card_frame2, card_frame3, inner_frame;
    private String url1, url2, url3;
    
    public PlayerTable(int id) {
        this.ID = id;
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

        Image im2 = new Image(getClass().getResourceAsStream(String.format("/img/characters/%d-normal.png", id)));

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
        dice_button.setDisable(true);
        dice_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dice_button.setDisable(true);
                Thread thread = new Thread(){
                    public void run(){
//                        System.out.println("Dice rolled");
                        try{
                            for (int i = 0;i < 15; i++){
                                String url = new String();
                                k = random.nextInt(6)+1;
                                url = "/img/dice/dice"+(k)+".png";
//                                System.out.println(url+"-alert");
                                Image dm = new Image(getClass().getResourceAsStream(url));
                                dice_frame.setFill(new ImagePattern(dm));
                                Thread.sleep(50);
                            }
                            Thread.sleep(500);
                            turn = true;
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
        this.setUrl1("/img/cards/giftofdev.jpg");
        Image cm = new Image(getClass().getResourceAsStream(url1));
        card_frame.setFill(new ImagePattern(cm));
        card_frame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                System.out.println(url1);
                CardPopup.display(url1);
            }
        });
        //card frame2
        card_frame2.setWidth(60);
        card_frame2.setHeight(72);
        card_frame2.setTranslateX(60);
        card_frame2.setTranslateY(90);
        this.setUrl2("/img/cards/giftofdev.jpg");
        Image ck = new Image(getClass().getResourceAsStream(url2));
        card_frame2.setFill(new ImagePattern(ck));
        card_frame2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                System.out.println(url);
                CardPopup.display(url2);
            }
        });
        //card frame3
        card_frame3.setWidth(60);
        card_frame3.setHeight(72);
        card_frame3.setTranslateX(140);
        card_frame3.setTranslateY(90);
        this.setUrl3("/img/cards/giftofdev.jpg");
        Image cl = new Image(getClass().getResourceAsStream(url3));
        card_frame3.setFill(new ImagePattern(cl));
        card_frame3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                System.out.println(url);
                CardPopup.display(url3);
            }
        });
        layout.getChildren().addAll(player_frame,inner_frame, dice_frame, dice_button, card_frame, card_frame2, card_frame3);
//        scene = new Scene(layout, 400 ,400);
//        stage.setScene(scene);
//        stage.show();
//    }
//    public static void main(String[] args) {
//        launch();
    }


    public StackPane getLayout() {
        return layout;
    }

    public int getK() {
        return k;
    }

    public Button getDice_button() {
        return dice_button;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }
}
