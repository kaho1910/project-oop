package game;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class Player {
    private final int ID;
    private int position = 1;
    private PowerCard[] cards = new PowerCard[4];
    private final int tileSize = Main.getTileSize();
    private final int width = Main.getWidth();
    private final int height = Main.getHeight();
    private final int offSetX = Main.getOffSetX();
    private final int offSetY = Main.getOffSetY();
    private final int radius;
    private PlayerTable playerTable;
    private ImageView imageView;
    private Image img;
    Random rand = new Random();

    public Player(int id, int radius) {
        this.ID = id;
        playerTable = new PlayerTable(id);
        for(int i=0; i < cards.length; i++){
            cards[i] = new PowerCard();
        }
        this.radius = radius;
        initPosition(position);
    }

    private int[] calculateXY(){
        int x = (this.position - 1) % width;
        int y = (this.position - 1) / height;
        if (y % 2 == 1){
            x = width - x - 1;
        }
        x = offSetX + radius + (tileSize * x);
        y = offSetY + radius + (tileSize * (height - 1 - y));
        return new int[] {x, y};
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
        System.out.println("\nrun : " + run);
        Thread thread = new Thread(){
            public void run(){
//                        System.out.println("Dice rolled");
                try{
                    for (int j=0;j<=20;j++){
                        for (int k=1;k<=3;k++){
                            img = new Image(getClass().getResourceAsStream("/img/sprite/w3_"+j+".png"));
                            imageView = new ImageView(img);
                            Thread.sleep(50);
                        }

                    }
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                }}
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
    }

    public int getPosition() {
        return position;
    }

    public PlayerTable getPlayerTable() {
        return playerTable;
    }

    public int getID() {
        return ID;
    }
}
