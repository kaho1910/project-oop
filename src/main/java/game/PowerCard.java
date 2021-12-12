package game;

import java.util.Random;

public class PowerCard implements Card{
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

    public PowerCard(PlayerController controller, Player player, boolean isInit, boolean isBlank){
        this.controller = controller;
        this.player = player;
        this.cardID = isBlank ? 0 : pushCardPool(isInit);
        if (cardID == 0){
            this.cardUrl = "";
        } else {
            this.cardUrl = String.format("/img/cards/%d.jpg", this.cardID);
        }
        this.thisCard = this;
        this.needTarget = 0;
    }

    public void action(){
        powerCrisis = false;
//        switch แยก action ของแต่ละ ID
        switch (this.getCardID()){
            case 11:
                System.out.println("\nswitch card id: 11");
                plsDisposeMe = true;
                break;
            case 12:
                System.out.println("\nswitch card id: 12");
                player.getPlayerTable().setIsBlackGlass("-g");
                break;
            case 13:
                System.out.println("\nswitch card id: 13");
                for (int i=0; i < 3; i++){
                    if (player.getCards()[i].getCardID() == 14){
                        powerCrisis = true;
                        needTarget = 14;
                    }
                }
                break;
            case 14:
                System.out.println("\nswitch card id: 14");
                for (int i=0; i < 3; i++){
                    if (player.getCards()[i].getCardID() == 13){
                        powerCrisis = true;
                        needTarget = 13;
                    }
                }
                break;
            default:
                System.out.println("\ndefault case");
                break;
        }

        if (!powerCrisis & (cardID == 13 | cardID == 14)){
            plsDisposeMe = true;
        }

        plsDisposeMe = !plsDisposeMe;

        targetPopup = new TargetPopup(controller);
        if (needTarget != 0) {
            targetPopup.display();
        }

        Thread t = new Thread(){
            public void run(){
                if (needTarget != 0) {
                    while (!targetPopup.isCancelled()) {
                        System.out.print("");
                        if (targetPopup.isSelected()){
                            System.out.println("selected");
                            target = targetPopup.getTarget();
                            break;
                        }
                    }
                }

                if (needTarget != 0 & targetPopup.isCancelled()){
                    System.out.println("cancel");
                } else if (plsDisposeMe){
                    System.out.println("dispose card");
                    if (cardID == 13 | cardID == 14){
                        System.out.println("use card: " + "13, 14");
                        for (int i=0; i < 3; i++){
                            if (player.getCards()[i].getCardID() ==  13 | player.getCards()[i].getCardID() == 14){
                                player.setCards(new PowerCard(controller, player, false, true), i);
                            }
                        }
                        player.subtractNumCardOnHand();
                    } else {
                        System.out.println("use card: " + cardID);
                    }
                    for (int i=0; i < 3; i++) {
                        if (player.getCards()[i].equals(thisCard)) {
                            player.setCards(new PowerCard(controller, player, false, true), i);
                        }
                    }
                    player.subtractNumCardOnHand();
                } else {
                    System.out.println("cannot use card: " + cardID);
                }
            }
        };
        t.start();
    }

    public int pushCardPool(boolean isInit){
        int num;
        int ans = 0;
        Random rand = new Random();
        while(true){
             if (controller.getSumCardPool() == 0){
//                alert card pool empty HERE
                System.out.println("card pool is empty");
                break;
            }
            num = rand.nextInt(controller.getCardPool().length);
            if (controller.getCardPool()[num] != 0 & !(isInit & (controller.getCardPool()[num] / 10 == 1))){
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
//        return rand.nextInt(2) + 13;

//        Production return HERE
        return ans;
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
