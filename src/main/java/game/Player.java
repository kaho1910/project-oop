package game;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class Player extends Circle {
    private final int ID;
    private int position = 1;
    private boolean turn = false;
    private PowerCard[] cards = new PowerCard[4];
    private int tileSize = Main.getTileSize();
    private int width = Main.getWidth();
    private int height = Main.getHeight();
    private int offSetX = Main.getOffSetX();
    private int offSetY = Main.getOffSetY();
    private int radius;
    boolean alive = true;
    Random rand = new Random();

    public Player(int id, int radius) {
        this.ID = id;
        for(int i=0; i < cards.length; i++){
            cards[i] = new PowerCard();
        }
        this.radius = radius;
        setRadius(radius);
        setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
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
        System.out.println("x : " + (this.position - 1) % width + ", " + coordinate[0]);
        System.out.println("y : " + ((int) ((this.position - 1) / height)) + ", " + coordinate[1]);
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setPosition(int run){
        System.out.println("\nrun : " + run);
        if (run > 0) {
            for(int i=0; i < run; i++) {
                moveTo(this.position + 1);
            }
        } else {
            for(int i=run; i < 0; i++) {
                moveTo(this.position - 1);
            }
        }
    }

    public int getPosition() {
        return position;
    }
}
