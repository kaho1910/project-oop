package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.image.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Random;

public class Table {
    private int cardNumMax = 3;
    private Random random;
    private int k;
    private boolean pressed;
    private boolean turn;
    private int ID;
    private StackPane layout;
    private Button diceButton;
    private Rectangle player_frame, dice_frame, inner_frame;
    private Rectangle[] cardFrame, innercardFrame;
    private Image[] cardImg;
    private Image im1, im2;
    private Player player;
    private String isBlackGlass;

    private MediaPlayer mediaPlayer;
    private Media media;

    public Table(int id, Player player) {
        this.ID = id;
        this.player = player;
        isBlackGlass = "";
        layout = new StackPane();
        layout.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), CornerRadii.EMPTY, Insets.EMPTY)));
        Image tableBg = new Image(getClass().getResourceAsStream("/img/123.png"));
        if (id % 2 == 1) {
            tableBg = new Image(getClass().getResourceAsStream("/img/321.png"));
        }
        layout.setBackground(new Background(new BackgroundImage(tableBg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        diceButton = new Button();
        player_frame = new Rectangle();
        inner_frame = new Rectangle();
        dice_frame = new Rectangle();
        innercardFrame = new Rectangle[3];
        cardFrame = new Rectangle[3];
        cardImg = new Image[cardNumMax];
        random = new Random();

        player_frame.setWidth(170);
        player_frame.setHeight(170);
        player_frame.setTranslateX(0);
        player_frame.setTranslateY(-80);
        im1 = new Image(getClass().getResourceAsStream("/img/blog.png"));
        player_frame.setFill(new ImagePattern(im1));
        //inner
        inner_frame.setWidth(147);
        inner_frame.setHeight(147);
        inner_frame.setTranslateX(-0);
        inner_frame.setTranslateY(-80);

        im2 = new Image(getClass().getResourceAsStream(String.format("/img/characters/%d-%s%s.png", id, "normal", isBlackGlass)));

        inner_frame.setFill(new ImagePattern(im2));
        ///dice
        dice_frame.setWidth(80);
        dice_frame.setHeight(80);
        dice_frame.setTranslateX(-120);
        dice_frame.setTranslateY(80);
        Image im3 = new Image(getClass().getResourceAsStream("/img/dice/dice1.png"));
        dice_frame.setFill(new ImagePattern(im3));
        //dice button
        diceButton.setText("Roll");
        diceButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        diceButton.setTranslateX(-120);
        diceButton.setTranslateY(145);
        //System.out.println("/img/dice/dice"+(random.nextInt(6)+1)+".png");
        diceButton.setDisable(true);
        diceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                diceButton.setDisable(true);
                media = new Media(getClass().getResource("/sound/Dice.mp3").toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setVolume(0.2);
                Thread thread = new Thread() {
                    public void run() {
//                        System.out.println("Dice rolled");
                        mediaPlayer.play();
                        setTurn(false);
                        try {
                            for (int i = 0; i < 15; i++) {
                                k = random.nextInt(6) + 1;
                                String url = "/img/dice/dice" + (k) + ".png";
//                                System.out.println(url+"-alert");
                                Image dm = new Image(getClass().getResourceAsStream(url));
                                dice_frame.setFill(new ImagePattern(dm));
                                Thread.sleep(50);
                            }
                            Thread.sleep(500);
                            pressed = true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });
        for (int i = 0; i < cardNumMax; i++) {
            cardFrame[i] = new Rectangle();
            cardFrame[i].setWidth(60);
            cardFrame[i].setHeight(72);
            cardFrame[i].setTranslateX(-20 + 70 * i);
            cardFrame[i].setTranslateY(90);
            innercardFrame[i] = new Rectangle();
            innercardFrame[i].setWidth(60);
            innercardFrame[i].setHeight(72);
            innercardFrame[i].setTranslateX(-20 + 70 * i);
            innercardFrame[i].setTranslateY(90);
            innercardFrame[i].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/img/cards/frameCard.jpg"))));
        }
        updateCard();
        layout.getChildren().addAll(player_frame, inner_frame, dice_frame, diceButton);
        for (Rectangle k :
                innercardFrame) {
            layout.getChildren().add(k);
        }
        for (Rectangle i :
                cardFrame) {
            layout.getChildren().add(i);
        }

    }

    public void updateCard() {
        for (int i = 0; i < cardNumMax; i++) {
            String cardUrl = player.getCards()[i].getCardUrl();
            if (cardUrl.equals("")) {
                cardFrame[i].setFill(Color.TRANSPARENT);
            } else {
                cardImg[i] = new Image(getClass().getResourceAsStream(cardUrl));
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

    public Image getIm1() {
        return im1;
    }

    public Image getIm2() {
        return im2;
    }

    public Button getDiceButton() {
        return diceButton;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
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

    public void setIsBlackGlass(String isBlackGlass) {
        this.isBlackGlass = isBlackGlass;
        updateEmotion("normal");
    }

    public void updateEmotion(String feedback) {
        if (feedback.equals("normal")) {
            im2 = new Image(getClass().getResourceAsStream(String.format("/img/characters/%d-%s%s.png", ID, "normal", isBlackGlass)));
            inner_frame.setFill(new ImagePattern(im2));
            return;
        }
        Thread t = new Thread() {
            public void run() {
                try {
                    im2 = new Image(getClass().getResourceAsStream(String.format("/img/characters/%d-%s%s.png", ID, feedback, isBlackGlass)));
                    inner_frame.setFill(new ImagePattern(im2));
                    Thread.sleep(4500);
                    im2 = new Image(getClass().getResourceAsStream(String.format("/img/characters/%d-%s%s.png", ID, "normal", isBlackGlass)));
                    inner_frame.setFill(new ImagePattern(im2));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}
