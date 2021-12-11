package game;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Player extends Rectangle {
    private final int ID;
    private int position = 1;
    private PowerCard[] cards = new PowerCard[3];
    private final int tileSize = Main.getTileSize();
    private final int width = Main.getWidth();
    private final int height = Main.getHeight();
    private final int offSetX = Main.getOffSetX();
    private final int offSetY = Main.getOffSetY();
    private PlayerTable playerTable;
    private int[] pickCardHistory;

    private boolean threadRun;

    private Image img;
    private ImagePattern imgPattern;

    public Player(int id) {
        this.ID = id;
        for(int i=0; i < cards.length; i++){
//            cards[i] = new PowerCard((i + 1) * 11);
            cards[i] = new PowerCard((i) * 11);
        }
        playerTable = new PlayerTable(id, this);
        setHeight(60);
        setWidth(30);
        setFill(Color.TRANSPARENT);
        img = new Image(getClass().getResourceAsStream("/img/sprite/w" + "3" +"_2.png"));
        imgPattern = new ImagePattern(img);
        setFill(imgPattern);

        initPosition(position);
    }

    private int[] calculateXY(){
        int x = (this.position - 1) % width;
        int y = (this.position - 1) / height;
        if (y % 2 == 1){
            x = width - x - 1;
        }
        x = offSetX + (tileSize * x);
        y = offSetY + (tileSize * (height - 1 - y));
        return new int[] {x + 25, y + 10};
    }

    public void initPosition(int pos){
        int[] coordinate = calculateXY();
        setTranslateX(coordinate[0]);
        setTranslateY(coordinate[1]);
    }

    public void moveTo(int pos) {
        this.position = pos;
        int[] coordinate = calculateXY();
        TranslateTransition animate = new TranslateTransition(Duration.millis(400), this);
        animate.setToX(coordinate[0]);
        animate.setToY(coordinate[1]);
        animate.setAutoReverse(false);
        animate.play();
        System.out.println("id: " + this.ID + " at [" +  this.position + "]");
//        System.out.println("x : " + (this.position - 1) % width + ", " + coordinate[0]);
//        System.out.println("y : " + ((this.position - 1) / height) + ", " + coordinate[1]);
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setPosition(int run){
        boolean gHundred = false;
        int temp = 0;
        threadRun = true;
        System.out.println("\nrun : " + run);
        Thread thread = new Thread(){
            public void run(){
                int k = 0;
                try{
                    while (threadRun | (k % 3) != 2) {
                        img = new Image(getClass().getResourceAsStream("/img/sprite/w3_" + (k % 3 + 1) + ".png"));
                        imgPattern = new ImagePattern(img);
                        setFill(imgPattern);
                        Thread.sleep(250);
                        k++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        if (run > 0) {
            if (this.position + run > 100){
                gHundred = true;
                temp = -((this.position + run) - 100);
                run += temp;
                System.out.println("temp:" + temp + "\nnew :" + run);
            }
            for (int i = 0; i < run; i++) {
                moveTo(this.position + 1);
            }
            if (gHundred){
                setPosition(temp);
            }
        } else {
            for (int i = run; i < 0; i++) {
                if (this.position <= 0){
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

    public PowerCard[] getCards() {
        return cards;
    }

    public void setCards(PowerCard cards, int i) {
        this.cards[i] = cards;
        getPlayerTable().updateCard();
    }

    public PlayerTable getPlayerTable() {
        return playerTable;
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
}
