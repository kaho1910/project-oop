package game;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.image.*;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;


import java.util.Random;



public class PlayerTable {
    private int cardNumMax = 3;
    private Random random;
    private int k;
    private boolean turn;
    private int ID;
    private StackPane layout;
    private Button diceButton;
    private Rectangle player_frame, dice_frame, inner_frame;
    private Rectangle[] cardFrame;
    private String[] cardUrl;
    private Image[] cardImg;
//    private String cardUrl1, cardUrl2, cardUrl3;
    
    public PlayerTable(int id) {
        this.ID = id;
        layout = new StackPane();
        diceButton = new Button();
        player_frame = new Rectangle();
        inner_frame = new Rectangle();
        dice_frame = new Rectangle();

        cardFrame = new Rectangle[3];
        cardUrl = new String[cardNumMax];
        cardImg = new Image[cardNumMax];
//        cardUrl1 = new String();
//        cardUrl2 = new String();
//        cardUrl3 = new String();

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
        diceButton.setText("Roll");
        diceButton.setTranslateX(-120);
        diceButton.setTranslateY(150);
        //System.out.println("/img/dice/dice"+(random.nextInt(6)+1)+".png");
        diceButton.setDisable(true);
        diceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                diceButton.setDisable(true);
                Thread thread = new Thread(){
                    public void run(){
//                        System.out.println("Dice rolled");
                        try{
                            for (int i = 0;i < 15; i++){
                                k = random.nextInt(6)+1;
                                String url = "/img/dice/dice"+(k)+".png";
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
            }
        });
//        cardUrl1 = "/img/cards/giftofdev.jpg";
//        cardUrl2 = "/img/cards/giftofdev.jpg";
//        cardUrl3 = "/img/cards/giftofdev.jpg";
//        Image cardImg1 = new Image(getClass().getResourceAsStream(cardUrl1));
//        Image cardImg2 = new Image(getClass().getResourceAsStream(cardUrl2));
//        Image cardImg3 = new Image(getClass().getResourceAsStream(cardUrl3));
        for(int i=0; i < cardNumMax; i++){
            cardFrame[i] = new Rectangle();
            cardFrame[i].setWidth(60);
            cardFrame[i].setHeight(72);
            cardFrame[i].setTranslateX(-20 + 80 * i);
            cardFrame[i].setTranslateY(90);
            cardUrl[i] = "/img/cards/giftofdev.jpg";
        }
        updateCard();
//        cardFrame[0].setFill(new ImagePattern(cardImg1));
//        cardFrame[1].setFill(new ImagePattern(cardImg2));
//        cardFrame[2].setFill(new ImagePattern(cardImg3));

        //card frame
//        card_frame.setWidth(60);
//        card_frame.setHeight(72);
//        card_frame.setTranslateX(-20);
//        card_frame.setTranslateY(90);
//        this.setUrl1("/img/cards/giftofdev.jpg");
//        Image cm = new Image(getClass().getResourceAsStream(url1));
//        card_frame.setFill(new ImagePattern(cm));
//        card_frame.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
////                System.out.println(url1);
//                CardPopup.display(url1);
//            }
//        });
        //card frame2
//        card_frame2.setWidth(60);
//        card_frame2.setHeight(72);
//        card_frame2.setTranslateX(60);
//        card_frame2.setTranslateY(90);
//        this.setUrl2("/img/cards/giftofdev.jpg");
//        Image ck = new Image(getClass().getResourceAsStream(url2));
//        card_frame2.setFill(new ImagePattern(ck));
//        card_frame2.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
////                System.out.println(url);
//                CardPopup.display(url2);
//            }
//        });
        //card frame3
//        card_frame3.setWidth(60);
//        card_frame3.setHeight(72);
//        card_frame3.setTranslateX(140);
//        card_frame3.setTranslateY(90);
//        this.setUrl3("/img/cards/giftofdev.jpg");
//        Image cl = new Image(getClass().getResourceAsStream(url3));
//        card_frame3.setFill(new ImagePattern(cl));
//        card_frame3.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
////                System.out.println(url);
//                CardPopup.display(url3);
//            }
//        });
        layout.getChildren().addAll(player_frame,inner_frame, dice_frame, diceButton);
        for (Rectangle i:
             cardFrame) {
            layout.getChildren().add(i);
        }
//        scene = new Scene(layout, 400 ,400);
//        stage.setScene(scene);
//        stage.show();
//    }
//    public static void main(String[] args) {
//        launch();
    }

    public void updateCard(){
        for(int i=0; i < cardNumMax; i++) {
            if (cardUrl[i] == "") {
                cardFrame[i].setFill(Color.TRANSPARENT);
            } else {
                cardImg[i] = new Image(getClass().getResourceAsStream(cardUrl[i]));
                cardFrame[i].setFill(new ImagePattern(cardImg[i]));
            }
        }
    }

    public StackPane getLayout() {
        return layout;
    }

    public int getK() {
        return k;
    }

    public Button getDiceButton() {
        return diceButton;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public int getCardNumMax() {
        return cardNumMax;
    }

    public Rectangle[] getCardFrame() {
        return cardFrame;
    }

    public Image getCardImg(int i) {
        return cardImg[i];
    }

    public void setCardUrl(String cardUrl, int i) {
        this.cardUrl[i] = cardUrl;
        updateCard();
    }
}
