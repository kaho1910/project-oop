package game;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class PlayerController implements Runnable  {
    private int playerNum = Main.getPlayerNum();
    private Player[] players;
    private boolean lastTurn = false;
    private int[][] ladder;
    private CardPopup popUp;
    private Image tempImg;

    public PlayerController(){
        players = new Player[playerNum];
        for(int i=0; i < playerNum; i++){
            players[i] = new Player(i + 1);
            Rectangle[] cardFrame = players[i].getPlayerTable().getCardFrame();
            for(int j=0; j < players[i].getPlayerTable().getCardNumMax(); j++){
                tempImg = players[i].getPlayerTable().getCardImg(j);
                cardFrame[j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        if (popUp != null){
                            popUp.getPopUpStage().close();
                        }
                        popUp = new CardPopup();
                        popUp.display(tempImg);
                    }
                });
            }
        }
    }

    public void run() {
        while (!isLastTurn()){
            for(int i=0; i < playerNum; i++){
                players[i].getPlayerTable().getDiceButton().setDisable(false);
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
                onLadder(players[i]);
            }
        }
    }

    public void onLadder(Player player){
        for(int j=0; j < ladder.length; j++){
            if (player.getPosition() == ladder[j][0]){
                player.moveTo(ladder[j][1]);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

    public void setLadder(int[][] ladder) {
        this.ladder = ladder;
    }
}
