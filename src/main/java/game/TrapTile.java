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

    public TrapTile(Player player, PlayerController controller, int trapID) {
        this.fromPlayer = player;
        this.controller = controller;
        this.trapID = trapID % 10;
        this.tileNum = player.getPosition();
        this.used = false;
        this.random = new Random();
        System.out.println("\nTrap is set at " + tileNum + " trapID: " + this.trapID);
        new Notice("Snakes and Ladders", "Player " + fromPlayer.getID() + " set Trap at " + tileNum);
    }

    public void action(Player targetPlayer) {
        this.targetPlayer = targetPlayer;

        System.out.println("\nTrap triggered\nid: " + targetPlayer.getID() + " tileNum: " + tileNum + "\nfrom id: " + fromPlayer.getID());
        String txt = "";
        switch (trapID){
            case 1:
                txt = "Player " + targetPlayer.getID() + " swap card with Player: " + fromPlayer.getID();
                break;
            case 2:
                txt = "Player " + fromPlayer.getID() + " steal card from Player: " + targetPlayer.getID();
                break;
            case 3:
                txt = "Player " + targetPlayer.getID() + " next turn will be skipped";
                break;
            case 4:
                txt = "Player " + targetPlayer.getID() + " random jump -5 or 5 tiles";
                break;
        }
        new Notice("Trap Triggered at Player " + targetPlayer.getID(), txt);

        boolean isAngeled = false;
        for (int i = 0; i < 3; i++) {
            if (targetPlayer.getCards()[i].getCardID() == 44) {
                targetPlayer.getCards()[i].useAngelCard();
                isAngeled = true;
                break;
            }
        }

        String playerEmotion = "positive";
        String targetEmotion = "negative";

        if (isAngeled) {
            System.out.println("Player " + targetPlayer.getID() + " is protected by Ohm-angel Card");
            new Notice("Snakes and Ladders", "Player " + targetPlayer.getID() + " is protected by Ohm-angel Card");
            playerEmotion = "negative";
            targetEmotion = "positive";
        } else {
            switch (trapID) {
                case 1:
                    System.out.println("switch trap id: 1");
                    int cardIDFromPlayer = 0;
                    int fromPlayerIndex = 0;
                    int cardIDFromTarget = 0;
                    int targetPlayerIndex = 0;
                    if (fromPlayer.cardCount() != 0 & targetPlayer.cardCount() != 0) {
                        while (cardIDFromPlayer == 0) {
                            fromPlayerIndex = random.nextInt(3);
                            cardIDFromPlayer = fromPlayer.getCards()[fromPlayerIndex].getCardID();
                        }
                        while (cardIDFromTarget == 0) {
                            targetPlayerIndex = random.nextInt(3);
                            cardIDFromTarget = targetPlayer.getCards()[targetPlayerIndex].getCardID();
                        }
                        fromPlayer.setCards(new PowerCard(controller, fromPlayer, cardIDFromTarget), fromPlayerIndex);
                        targetPlayer.setCards(new PowerCard(controller, targetPlayer, cardIDFromPlayer), targetPlayerIndex);
                    } else {
                        playerEmotion = "negative";
                        targetEmotion = "negative";
                        int playerNoCard = fromPlayer.cardCount() == 0 ? fromPlayer.getID() : targetPlayer.getID();
                        System.out.println("id: " + playerNoCard + " has no card");
                        new Notice("Snakes and Ladders", "Player " + playerNoCard + " has no card");
                    }
                    break;
                case 2:
                    System.out.println("switch trap id: 2");
                    int cardID = 0;
                    int cardIndex = 0;
                    if (targetPlayer.cardCount() != 0) {
                        while (cardID == 0) {
                            cardIndex = random.nextInt(3);
                            cardID = targetPlayer.getCards()[cardIndex].getCardID();
                        }
                        targetPlayer.setCards(new PowerCard(controller, targetPlayer, false, true), cardIndex);
                        if (fromPlayer.cardCount() != 3) {
                            for (int i = 0; i < 3; i++) {
                                if (fromPlayer.getCards()[i].getCardID() == 0) {
                                    fromPlayer.setCards(new PowerCard(controller, fromPlayer, cardID), i);
                                    break;
                                }
                            }
                        } else {
                            System.out.println("id: " + fromPlayer.getID() + " has no card slot left");
                            new Notice("Snakes and Ladders", "Player " + fromPlayer.getID() + " has no card slot left");
                        }
                    } else {
                        playerEmotion = "negative";
                        targetEmotion = "positive";
                        System.out.println("id: " + targetPlayer.getID() + " has no card");
                        new Notice("Snakes and Ladders", "Player " + targetPlayer.getID() + " has no card");
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
        }
        fromPlayer.getPlayerTable().updateEmotion(playerEmotion);
        targetPlayer.getPlayerTable().updateEmotion(targetEmotion);
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
