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
    private int[] cardPool;
    private int sumCardPool;
    private CardPopup popUp;
    private Image tempImg;
    private PlayerController controller;
    private TargetPopup targetPopup;

    private Button useCardBtn;

    public PlayerController(int[] cardPool){
        controller = this;
        this.cardPool = cardPool;
        for (int i:
             cardPool) {
            sumCardPool += i;
        }
        players = new Player[playerNum];
        for(int i=0; i < playerNum; i++){
            players[i] = new Player(i + 1, this);

            Rectangle[] cardFrame = players[i].getPlayerTable().getCardFrame();
            for(int j=0; j < players[i].getPlayerTable().getCardNumMax(); j++){
                cardFrame[j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    int pNum, cNum;
                    public void handle(MouseEvent mouseEvent) {
//                        System.out.printlnprintln(mouseEvent.getSource());
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
                            popUp.setFlag(true);
                            popUp.getPopUpStage().close();
                        }
                        if (!players[pNum].getCards()[cNum].getCardUrl().equals("")) {
                            popUp = new CardPopup();
                            popUp.display(tempImg);

                            useCardBtn = popUp.getUseCardBtn();
                            useCardBtn.setDisable(true);
                            useCardBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                public void handle(MouseEvent mouseEvent) {
                                    PowerCard card = players[pNum].getCards()[cNum];
                                    card.action();
//                                    if (card.isPlsDisposeMe()){
//                                        players[pNum].setCards(new PowerCard(controller, players[pNum], false, true), cNum);
//                                    }
                                    popUp.getPopUpStage().close();
                                    popUp.setFlag(true);
                                }
                            });
                            Thread newThread = new Thread(){
                                public void run() {
                                    while (!popUp.isFlag()){
                                        System.out.print(""); //ห้ามลบ
                                        if (players[pNum].getPlayerTable().isTurn()){
                                            useCardBtn.setDisable(false);
                                        } else {
                                            useCardBtn.setDisable(true);
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
        boolean lastTurnAlert = false;
        String playerAtGoal = new String();
        for (int i=0; i < playerNum; i++){
            players[i].setPickCardHistory(pickCard);
        }
        while (!isLastTurn()){
            for(int i=0; i < playerNum; i++){
                if (players[i].getWillSkip() != 0){
                    players[i].setWillSkip(players[i].getWillSkip() - 1);
                    continue;
                }
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
                onLadder(players[i]);
                onPickCard(players[i]);
                if (isLastTurn() & !lastTurnAlert){
                    lastTurnAlert = true;
                    System.out.println("\nPlayer " + players[i].getID() + " has TRIGGER Last Turn");
                }
            }
        }
        for(int i=0; i < players.length; i++){
            if (players[i].getPosition() == 100){
                playerAtGoal += "" + players[i].getID() + ", ";
            }
        }
        System.out.println("\nGame END");
        System.out.println("Player(s) ID: " + playerAtGoal.substring(0, playerAtGoal.length() - 2) + " win");
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
        for (int i = 0; i < pickCard.length; i++) {
            if (player.getPosition() == pickCard[i]) {
                if (player.getPickCardHistory()[i] == 0) {
                    System.out.println("id: " + player.getID() + " already been here");
                } else if (player.getNumCardOnHand() == 3) {
                    System.out.println("maximum card on hand");
                }  else {
                    for (int j=0; j < 3; j++){
                        if (player.getCards()[j].getCardID() == 0){
                            System.out.println("id: " + player.getID() + " Pick new card");
                            player.setCards(new PowerCard(this, player, false, false), j);
                            player.getPickCardHistory()[i] = 0;
                            break;
                        }
                    }
                }
                break;
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

    public int[] getCardPool() {
        return cardPool;
    }

    public int getSumCardPool() {
        return sumCardPool;
    }

    public void cutSumCardPool(int sumCardPool) {
        this.sumCardPool -= sumCardPool;
    }

    public void setCardPool(int cardPool, int i) {
        this.cardPool[i] = cardPool;
    }

    public TargetPopup newTargetPopUp(){
        if (targetPopup != null){
            System.out.println("targetPopUp");
            targetPopup.getPopUpStage().close();
        }
        targetPopup = new TargetPopup(this);
        return targetPopup;
    }
}
