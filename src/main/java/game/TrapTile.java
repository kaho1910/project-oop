package game;

import java.util.Random;

public class TrapTile {
    private final Player fromPlayer;
    private final PlayerController controller;
    private final int trapID;
    private final int tileNum;
    private boolean used;
    private Player targetPlayer;
    private Random random;

    public TrapTile(Player player, PlayerController controller, int trapID){
        this.fromPlayer = player;
        this.controller = controller;
        this.trapID = trapID % 10;
        this.tileNum = player.getPosition();
        this.used = false;
        this.random = new Random();
        System.out.println("\nTrap is set at " + tileNum + " trapID: " + this.trapID);
    }

    public void action(Player targetPlayer){
        this.targetPlayer = targetPlayer;

        System.out.println("\nTrap triggered\nid: " + targetPlayer.getID() + " tileNum: " + tileNum + "\nfrom id: " + fromPlayer.getID());
//        switch HERE
        switch (trapID){
            case 1:
                System.out.println("switch trap id: 1");
                int cardIDFromPlayer = -1;
                int fromPlayerIndex = 0;
                int cardIDFromTarget = -1;
                int targetPlayerIndex = 0;
                if (fromPlayer.cardCount() != 0 & targetPlayer.cardCount() != 0){
                    while (cardIDFromPlayer != 0){
                        fromPlayerIndex = random.nextInt(3);
                        cardIDFromPlayer = fromPlayer.getCards()[fromPlayerIndex].getCardID();
                    }
                    while (cardIDFromTarget != 0){
                        targetPlayerIndex = random.nextInt(3);
                        cardIDFromTarget = targetPlayer.getCards()[targetPlayerIndex].getCardID();
                    }
                    fromPlayer.setCards(new PowerCard(controller, fromPlayer, cardIDFromTarget), fromPlayerIndex);
                    targetPlayer.setCards(new PowerCard(controller, targetPlayer, cardIDFromPlayer), targetPlayerIndex);
                } else {
                    int playerNoCard = fromPlayer.cardCount() == 0 ? fromPlayer.getID() : targetPlayer.getID();
                    System.out.println("id: " + playerNoCard + " has no card");
                }
                break;
            case 2:
                System.out.println("switch trap id: 2");
                int cardID = -1;
                int cardIndex = 0;
                if (targetPlayer.cardCount() != 0){
                    while (cardID != 0){
                        cardIndex = random.nextInt(3);
                        cardID = targetPlayer.getCards()[cardIndex].getCardID();
                    }
                    targetPlayer.setCards(new PowerCard(controller, targetPlayer, false, true), cardIndex);
                    if (fromPlayer.cardCount() != 3){
                        for (int i=0; i < 3; i++){
                            if (fromPlayer.getCards()[i].getCardID() == 0){
                                fromPlayer.setCards(new PowerCard(controller, fromPlayer, cardID), i);
                                break;
                            }
                        }
                    } else {
                        System.out.println("id: " + fromPlayer.getID() + " has no card slot left");
                    }
                } else {
                    System.out.println("id: " + targetPlayer.getID() + " has no card");
                }
                break;
            case 3:
                System.out.println("switch trap id: 3");
                targetPlayer.setWillSkip(1);
                break;
            case 4:
                System.out.println("switch trap id: 4");
                targetPlayer.setPosition(random.nextInt(2) == 0 ? 5 : -5);
                break;
        }
        this.used = true;
    }

    public int getTileNum() {
        return tileNum;
    }

    public boolean isUsed() {
        return used;
    }

    public Player getFromPlayer() {
        return fromPlayer;
    }
}
