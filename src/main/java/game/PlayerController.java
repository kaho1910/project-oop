package game;

import java.util.Random;

public class PlayerController implements Runnable  {
    private int playerNum = Main.getPlayerNum();
    private Player[] players;
    private PlayerTable[] playerTable;
    private boolean lastTurn = false;
    Random rand = new Random();

    public PlayerController(int radius){
        players = new Player[playerNum];
        for(int i=0; i < playerNum; i++){
            players[i] = new Player(i + 1, radius);
        }
    }

    public void run() {
        while (!isLastTurn()){
            for(int i=0; i < playerNum; i++){
                players[i].getPlayerTable().getDice_button().setDisable(false);
                players[i].getPlayerTable().setTurn(false);
                while (true) {
//                    if (players[i].getPlayerTable().getDice_button().isDisabled()) {
                    System.out.print(""); // ศักดิ์สิทธิ์ ห้ามลบ
                    if (players[i].getPlayerTable().isTurn()) {
//                        System.out.println(players[i].getID() + "-check");
                        try {
                            if (players[i].getPosition() < 99 || players[i].getPosition() > 1) {
//                                System.out.println(players[i].getPlayerTable().getK());
//                                players[i].setPosition(rand.nextInt(6) + 1);
                                players[i].setPosition(players[i].getPlayerTable().getK());
                                if (players[i].getPosition() == 100) {
                                    lastTurn = true;
                                }
                                Thread.sleep(1000);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        players[i].getPlayerTable().setTurn(false);
                        break;
                    }
                }
            }
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public boolean isLastTurn() {
        return lastTurn;
    }
}
