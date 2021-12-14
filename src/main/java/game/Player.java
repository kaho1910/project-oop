package game;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

public class Player extends Rectangle {
    private final int ID;
    private int position = 1;
    private PowerCard[] cards = new PowerCard[3];
    private int numCardOnHand;
    private final int tileSize = Main.getTileSize();
    private final int width = Main.getWidth();
    private final int height = Main.getHeight();
    private final int offSetX = Main.getOffSetX();
    private final int offSetY = Main.getOffSetY();
    private Table table;
    private int[] pickCardHistory;
    private PlayerController controller;
    private int willSkip;
    private double runTimes;
    private int runPlus;

    private boolean threadRun;
    private TranslateTransition animate;

    private Image img;
    private ImagePattern imgPattern;

    private MediaPlayer mediaPlayer;
    private Media media;

    public Player(int id, PlayerController controller) {
        this.ID = id;
        this.controller = controller;
        this.numCardOnHand = 0;
        this.runTimes = 1;
        this.runPlus = 0;
        this.willSkip = 0;
        for (int i = 0; i < cards.length; i++) {
//            cards[i] = new PowerCard(i * 11);
            if (i < 2) {
                cards[i] = new PowerCard(controller, this, true, false);
            } else {
                cards[i] = new PowerCard(controller, this, true, true);
            }
        }
        table = new Table(id, this);
        setHeight(60);
        setWidth(45);
        setFill(Color.TRANSPARENT);
        img = new Image(getClass().getResourceAsStream(String.format("/img/sprite/%d_2.png", this.getID())));
        imgPattern = new ImagePattern(img);
        setFill(imgPattern);

        initPosition(position);
    }

    private int[] calculateXY() {
        int x = (this.position - 1) % width;
        int y = (this.position - 1) / height;
        if (y % 2 == 1) {
            x = width - x - 1;
        }
        x = offSetX + (tileSize * x);
        y = offSetY + (tileSize * (height - 1 - y));
        return new int[]{x + 18, y + 10};
    }

    public void initPosition(int pos) {
        int[] coordinate = calculateXY();
        setTranslateX(coordinate[0]);
        setTranslateY(coordinate[1]);
    }

    public void moveTo(int pos) {
        this.position = pos;
        int[] coordinate = calculateXY();
        animate = new TranslateTransition(Duration.millis(400), this);
        animate.setToX(coordinate[0]);
        animate.setToY(coordinate[1]);
        animate.setAutoReverse(false);
        animate.play();
        System.out.println("id: " + this.ID + " at [" + this.position + "]");
//        System.out.println("x : " + (this.position - 1) % width + ", " + coordinate[0]);
//        System.out.println("y : " + ((this.position - 1) / height) + ", " + coordinate[1]);
        try {
            TimeUnit.MILLISECONDS.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setPosition(int run) {
        boolean gHundred = false;
        int temp = 0;

        if (this.runTimes != 1 & this.runPlus != 0) {
            System.out.println("\nrun : (" + run + " * " + this.runTimes + ") + " + this.runPlus);
        } else if (this.runTimes != 1) {
            System.out.println("\nrun : " + run + " * " + this.runTimes);
        } else if (this.runPlus != 0) {
            System.out.println("\nrun : " + run + " + " + this.runPlus);
        } else {
            System.out.println("\nrun : " + run);
        }
        run = ((int) (run * this.runTimes)) + this.runPlus;
        this.runTimes = 1;
        this.runPlus = 0;

        threadRun = true;
        Thread thread = new Thread() {
            public void run() {
                int k = 0;
                try {
                    //System.out.println(this.getId());
                    media = new Media(getClass().getResource("/sound/Walk.mp3").toExternalForm());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                    mediaPlayer.setVolume(0.1);
                    mediaPlayer.play();
                    while (threadRun | (k % 3) != 2) {
                        img = new Image(getClass().getResourceAsStream("/img/sprite/" + ID + "_" + (k % 3 + 1) + ".png"));
                        imgPattern = new ImagePattern(img);
                        setFill(imgPattern);
                        Thread.sleep(250);
                        k++;
                    }
                    mediaPlayer.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

        if (run > 0) {
            if (this.position + run > 100) {
                gHundred = true;
                temp = -((this.position + run) - 100);
                run += temp;
                System.out.println("temp:" + temp + "\nnew :" + run);
            }
            for (int i = 0; i < run; i++) {
                moveTo(this.position + 1);
            }
            if (gHundred) {
                setPosition(temp);
                mediaPlayer.stop();
            }
        } else {
            for (int i = run; i < 0; i++) {
                if (this.position <= 1) {
                    break;
                }
                moveTo(this.position - 1);
            }
        }
        threadRun = false;
    }

    public int getPosition() {
        return position;
    }

    public int getNumCardOnHand() {
        return numCardOnHand;
    }

    public void addNumCardOnHand() {
        this.numCardOnHand++;
        System.out.println("id: " + ID + " numCardOnHand: " + numCardOnHand);
    }

    public void subtractNumCardOnHand() {
        this.numCardOnHand--;
        System.out.println("id: " + ID + " numCardOnHand: " + numCardOnHand);
    }

    public void setNumCardOnHand(int i){
        this.numCardOnHand = i;
    }

    public PowerCard[] getCards() {
        return cards;
    }

    public void setCards(PowerCard cards, int i) {
        this.cards[i] = cards;
        getPlayerTable().updateCard();
    }

    public Table getPlayerTable() {
        return table;
    }

    public int getID() {
        return ID;
    }

    public int[] getPickCardHistory() {
        return pickCardHistory;
    }

    public void setPickCardHistory(int[] pickCardHistory) {
        this.pickCardHistory = pickCardHistory.clone();
    }

    public int getWillSkip() {
        return willSkip;
    }

    public void setWillSkip(int willSkip) {
        this.willSkip = willSkip;
    }

    public void setRunTimes(double runTimes) {
        this.runTimes *= runTimes;
    }

    public void setRunPlus(int runPlus) {
        this.runPlus += runPlus;
    }

    public int cardCount() {
        int num = 0;
        for (int i = 0; i < 3; i++) {
            if (getCards()[i].getCardID() != 0) {
                num++;
            }
        }
        return num;
    }
}
