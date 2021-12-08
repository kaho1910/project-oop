package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Player extends Circle implements Runnable {
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
        setPosition(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int pos) {
        this.position = pos;
        int x = (this.position - 1) % width;
        int y = (this.position - 1) / height;
        if (y % 2 == 1){
            x = width - x - 1;
        }
        x = offSetX + radius + (tileSize * x);
        y = offSetY + radius + (tileSize * (height - 1 - y));
        setTranslateX(x);
        setTranslateY(y);
        System.out.println("\nid: " + this.ID + " at " +  this.position);
        System.out.println("x : " + (this.position - 1) % width + ", " + x);
        System.out.println("y : " + ((int) ((this.position - 1) / height)) + ", " + y);
    }

    public void run() {
        while(alive) {
            try {
                Thread.sleep(rand.nextInt(500) + 200);
                if (this.position >= 99){
                    alive = false;
                }
                setPosition(this.position + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
