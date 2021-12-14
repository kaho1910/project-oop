package game;

import java.util.Random;

public class PowerCard implements Card {
    private int cardID;
    private String cardUrl;
    private boolean plsDisposeMe = false;
    private PlayerController controller;
    private Player player;
    private boolean powerCrisis;
    private PowerCard thisCard;
    private int needTarget;
    private int target;
    private TargetPopup targetPopup;

    public PowerCard(PlayerController controller, Player player, int cardID) {
        this.controller = controller;
        this.player = player;
        this.cardID = cardID;
//        if (cardID == 0){
//            this.cardUrl = "";
//        } else {
        this.cardUrl = String.format("/img/cards/%d.jpg", this.cardID);
//        }
        this.thisCard = this;
        this.needTarget = 0;
    }

    public PowerCard(PlayerController controller, Player player, boolean isInit, boolean isBlank) {
        this.controller = controller;
        this.player = player;
        this.cardID = isBlank ? 0 : pushCardPool(isInit);
        if (cardID == 0) {
            this.cardUrl = "";
        } else {
            this.cardUrl = String.format("/img/cards/%d.jpg", this.cardID);
        }
        this.thisCard = this;
        this.needTarget = 0;
    }

    public void action() {
        String emotion = "normal";
        powerCrisis = false;
//        switch แยก action ของแต่ละ ID
        switch (this.getCardID()) {
            case 11:
                System.out.println("\nswitch card id: 11");
                plsDisposeMe = true;
                emotion = "negative";
                break;
            case 12:
                System.out.println("\nswitch card id: 12");
                player.getPlayerTable().setIsBlackGlass("-g");
                break;
            case 13:
                System.out.println("\nswitch card id: 13");
                for (int i = 0; i < 3; i++) {
                    if (player.getCards()[i].getCardID() == 14) {
                        powerCrisis = true;
                        needTarget = 14;
                    }
                }
                break;
            case 14:
                System.out.println("\nswitch card id: 14");
                for (int i = 0; i < 3; i++) {
                    if (player.getCards()[i].getCardID() == 13) {
                        powerCrisis = true;
                        needTarget = 13;
                    }
                }
                break;
            case 21:
            case 22:
            case 23:
            case 24:
                System.out.println("\nswitch card id: " + cardID);
                controller.setTrapTiles(new TrapTile(player, controller, cardID));
                emotion = "positive";
                break;
            case 31:
                System.out.println("\nswitch card id: 31");
                needTarget = 31;
                emotion = "positive";
                break;
            case 32:
                System.out.println("\nswitch card id: 32");
                needTarget = 32;
                emotion = "positive";
                break;
            case 33:
                System.out.println("\nswitch card id: 33");
                needTarget = 33;
                emotion = "positive";
                break;
            case 34:
                System.out.println("\nswitch card id: 34");
                needTarget = 34;
                emotion = "positive";
                break;
            case 41:
                System.out.println("\nswitch card id: 41");
                player.setRunPlus(1);
                emotion = "positive";
                break;
            case 42:
                System.out.println("\nswitch card id: 42");
                player.setRunPlus(-1);
                emotion = "negative";
                break;
            case 43:
                System.out.println("\nswitch card id: 43");
                if (player.getCards()[0].getCardID() == 11) {
                    player.setCards(new PowerCard(controller, player, false, false), 1);
                    player.setNumCardOnHand(3);
                } else {
                    player.setCards(new PowerCard(controller, player, false, false), 0);
                    player.setCards(new PowerCard(controller, player, false, true), 1);
                    player.setNumCardOnHand(2);
                }
                player.setCards(new PowerCard(controller, player, false, true), 2);
                break;
            case 44:
                System.out.println("\nswitch card id: 44");
                plsDisposeMe = true;
                break;
            default:
                System.out.println("\nswitch default case");
                break;
        }

        player.getPlayerTable().updateEmotion(emotion);

        if (!powerCrisis & (cardID == 13 | cardID == 14)) {
            plsDisposeMe = true;
        }

        plsDisposeMe = !plsDisposeMe;

        targetPopup = controller.newTargetPopUp();

        if (needTarget != 0) {
            targetPopup.display();
        }

        Thread t = new Thread() {
            public void run() {
                if (needTarget != 0) {
                    while (!targetPopup.isCancelled()) {
                        System.out.print("");
                        if (targetPopup.isSelected()) {
                            System.out.println("selected");
                            target = targetPopup.getTarget();
                            break;
                        }
                        if (!player.getPlayerTable().isTurn()) {
//                            targetPopup.setCancelled(true);
                            for (int i = 0; i < targetPopup.getPbutton().length; i++) {
                                targetPopup.getPbutton()[i].setDisable(true);
                            }
                        }
                        if (player.getPlayerTable().isTurn()) {
//                            targetPopup.setCancelled(true);
                            for (int i = 0; i < targetPopup.getPbutton().length; i++) {
                                if (player.getID() - 1 != i & player.getPosition() != 100) {
                                    targetPopup.getPbutton()[i].setDisable(false);
                                }
                            }
                        }
                    }
                }

                if (needTarget != 0 & targetPopup.isCancelled()) {
                    System.out.println("cancel");
                } else if (plsDisposeMe) {
                    System.out.println("dispose card");
                    if (cardID == 13 | cardID == 14) {
                        System.out.println("use card: " + "13, 14");
                        for (int i = 0; i < 3; i++) {
                            if (player.getCards()[i].getCardID() == 13 | player.getCards()[i].getCardID() == 14) {
                                player.setCards(new PowerCard(controller, player, false, true), i);
                            }
                        }
                        player.getPlayerTable().updateEmotion("positive");
                        player.subtractNumCardOnHand();
                    } else {
                        System.out.println("use card: " + cardID);
                    }
                    for (int i = 0; i < 3; i++) {
                        if (player.getCards()[i].equals(thisCard)) {
                            player.setCards(new PowerCard(controller, player, false, true), i);
                        }
                    }
                    player.subtractNumCardOnHand();

                    boolean isAngeled = false;

                    if (needTarget != 0) {
                        System.out.println("Target: " + (target + 1));
                        Player targetPlayer = controller.getPlayers()[target];

                        for (int i = 0; i < 3; i++) {
                            if (targetPlayer.getCards()[i].getCardID() == 44) {
                                targetPlayer.getCards()[i].useAngelCard();
                                isAngeled = true;
                                break;
                            }
                        }

                        if (isAngeled) {
                            needTarget = 0;
                        }

                        String targetEmotion = "negative";
                        String playerEmotion = "positive";

                        switch (needTarget) {
                            case 13:
                            case 14:
                                targetPlayer.setWillSkip(-1);
                                new Notice("Snakes and Ladders", "Player: " + targetPlayer.getID() + " is removed from game");
                                break;
                            case 31:
                                targetPlayer.setPosition(2);
                                controller.endTurnChecker(targetPlayer);
                                targetEmotion = "positive";
                                playerEmotion = "negative";
                                new Notice("Snakes and Ladders", "Player: " + targetPlayer.getID() + " move forward 2 tiles");
                                break;
                            case 32:
                                targetPlayer.setPosition(-2);
                                controller.endTurnChecker(targetPlayer);
                                new Notice("Snakes and Ladders", "Player: " + targetPlayer.getID() + " move backward 2 tiles");
                                break;
                            case 33:
                                targetPlayer.setRunTimes(2);
                                targetEmotion = "positive";
                                playerEmotion = "negative";
                                new Notice("Snakes and Ladders", "Player: " + targetPlayer.getID() + " next turn move will be doubled");
                                break;
                            case 34:
                                targetPlayer.setRunTimes(0.5);
                                new Notice("Snakes and Ladders", "Player: " + targetPlayer.getID() + " next turn move will be half");
                                break;
                            default:
                                System.out.println("Player " + targetPlayer.getID() + " is protected by Ohm-angel Card");
                                new Notice("Snakes and Ladders", "Player: " + targetPlayer.getID() + " is protected by Ohm-angel Card");
                                targetEmotion = "positive";
                                playerEmotion = "negative";
                                break;
                        }
                        player.getPlayerTable().updateEmotion(playerEmotion);
                        targetPlayer.getPlayerTable().updateEmotion(targetEmotion);
                    }

                } else {
                    System.out.println("cannot use card: " + cardID);
                    new Notice("Snakes and Ladders", "cannot use card ID: " + cardID);
                }
            }
        };
        t.start();
    }

    public int pushCardPool(boolean isInit) {
        int num;
        int ans = 0;
        Random rand = new Random();
        while (true) {
            if (controller.getSumCardPool() == 0) {
//                alert card pool empty HERE
                System.out.println("card pool is empty");
                new Notice("Snakes and Ladders", "card pool is empty");
                break;
            }
            num = rand.nextInt(controller.getCardPool().length);
            if (controller.getCardPool()[num] != 0 & !(isInit & (controller.getCardPool()[num] / 10 == 1))) {
//                System.out.println(controller.getCardPool()[num]);
                ans += controller.getCardPool()[num];
                controller.cutSumCardPool(ans);
                controller.setCardPool(0, num);
//                System.out.println(playerID - 1);
                player.addNumCardOnHand();
                break;
            }
        }

//        PowerCard test return HERE

//        return rand.nextInt(2) + 41;


//        Production return HERE
        return ans;
    }

    public void useAngelCard() {
        if (this.cardID == 44) {
            System.out.println("dispose card");
            System.out.println("use card: 44");
            for (int i = 0; i < 3; i++) {
                if (player.getCards()[i].equals(thisCard)) {
                    player.setCards(new PowerCard(controller, player, false, true), i);
                }
            }
            player.subtractNumCardOnHand();
        }
    }


    public String getCardUrl() {
        return cardUrl;
    }

    public boolean isPlsDisposeMe() {
        return plsDisposeMe;
    }

    public int getCardID() {
        return cardID;
    }
}
