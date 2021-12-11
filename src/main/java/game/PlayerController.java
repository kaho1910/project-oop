package game;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class PlayerController implements Runnable  {
    private int playerNum = Main.getPlayerNum();
    private Player[] players;
    private boolean lastTurn = false;
    private int[][] ladder;
    private int[] pickCard;
    private CardPopup popUp;
    private Image tempImg;

    private Button useCardBtn;

    public PlayerController(){
        players = new Player[playerNum];
        for(int i=0; i < playerNum; i++){
            players[i] = new Player(i + 1);

            Rectangle[] cardFrame = players[i].getPlayerTable().getCardFrame();
            for(int j=0; j < players[i].getPlayerTable().getCardNumMax(); j++){
                cardFrame[j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    int pNum, cNum;
                    public void handle(MouseEvent mouseEvent) {
//                        System.out.println(mouseEvent.getSource());
                        for(int m=0; m < players.length; m++){
                            for(int k=0; k < players[m].getPlayerTable().getCardNumMax(); k++){
                                if (mouseEvent.getSource().equals(players[m].getPlayerTable().getCardFrame()[k])){
                                    pNum = m;
                                    cNum = k;
                                }
                            }
                        }
//                        System.out.println(pNum + " " + cNum);
                        tempImg = players[pNum].getPlayerTable().getCardImg(cNum);
                        if (popUp != null){
                            popUp.getPopUpStage().close();
                        }
                        if (!players[pNum].getCards()[cNum].getCardUrl().equals("")) {
                            popUp = new CardPopup();
                            popUp.display(tempImg);

                            useCardBtn = popUp.getUseCardBtn();
                            useCardBtn.setDisable(true);
                            useCardBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                public void handle(MouseEvent mouseEvent) {
                                    PowerCard card = players[pNum].getCards()[0];
                                    card.action();
                                    if (card.isPlsDisposeMe()){
                                        System.out.println("dispose me");
                                    }
                                    popUp.getPopUpStage().close();
                                }
                            });
                            Thread newThread = new Thread(){
                                public void run() {
                                    while(true){
                                        System.out.println(""); //ห้ามลบ
                                        if (players[pNum].getPlayerTable().isTurn()){
                                            useCardBtn.setDisable(false);
                                            break;
                                        }
                                    }
                                }
                            };
                            newThread.start();
                        }
                    }
                });
            }
        }
    }

    public void run() {
        for (int i=0; i < playerNum; i++){
            players[i].setPickCardHistory(pickCard);
        }
        while (!isLastTurn()){
            for(int i=0; i < playerNum; i++){
                players[i].getPlayerTable().setTurn(true);
                players[i].getPlayerTable().getDiceButton().setDisable(false);
                players[i].getPlayerTable().setPressed(false);
                while (true) {
//                    if (players[i].getPlayerTable().getDice_button().isDisabled()) {
                    System.out.print(""); // ศักดิ์สิทธิ์ ห้ามลบ
                    if (players[i].getPlayerTable().isPressed()) {
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
                        players[i].getPlayerTable().setPressed(false);
                        break;
                    }
                }
                players[i].getPlayerTable().setTurn(false);
                onLadder(players[i]);
                onPickCard(players[i]);
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

    public void onPickCard(Player player){
        for(int i=0; i < pickCard.length; i++){
        if (player.getPosition() == pickCard[i] && player.getPickCardHistory()[i] != 0){
                System.out.println("id: " + player.getID() + " Pick card");
                player.getPickCardHistory()[i] = 0;
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

    public void setPickCard(int[] pickCard) {
        this.pickCard = pickCard;
    }
}
