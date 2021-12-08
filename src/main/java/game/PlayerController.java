package game;

import java.util.Random;

public class PlayerController implements Runnable  {
    private int playerNum = Main.getPlayerNum();
    private Player[] players;
    Random rand = new Random();

    public PlayerController(int radius){
        players = new Player[playerNum];
        for(int i=0; i < playerNum; i++){
            players[i] = new Player(i + 1, radius);
        }
    }

    public void run() {
        while (true){
            for(int i=0; i < playerNum; i++){
                try {
                    if (players[i].getPosition() < 99 || players[i].getPosition() > 1) {
                        players[i].setPosition(rand.nextInt(6) + 1);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Player[] getPlayers() {
        return players;
    }
}
