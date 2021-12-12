package game;

import java.util.Random;

public class PowerCard implements Card{
    private int cardID;
    private String cardUrl;
    private boolean plsDisposeMe = false;
    private PlayerController controller;
    private Player player;
    private boolean powerCrisis;

    public PowerCard(PlayerController controller, Player player, boolean isInit, boolean isBlank){
        this.controller = controller;
        this.player = player;
        this.cardID = isBlank ? 0 : pushCardPool(isInit);
        if (cardID == 0){
            this.cardUrl = "";
        } else {
            this.cardUrl = String.format("/img/cards/%d.jpg", this.cardID);
        }
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
                        player.setCards(new PowerCard(controller, player, false, true), i);
                    }
                }
                break;
            case 14:
                System.out.println("\nswitch card id: 14");
                for (int i=0; i < 3; i++){
                    if (player.getCards()[i].getCardID() == 13){
                        powerCrisis = true;
                        player.setCards(new PowerCard(controller, player, false, true), i);
                    }
                }
                break;
        }
        if (!powerCrisis & (cardID == 13 | cardID == 14)){
            plsDisposeMe = true;
        }
        plsDisposeMe = !plsDisposeMe;
        if (plsDisposeMe){
            System.out.println("dispose card");
            if (this.cardID == 13 | this.cardID == 14){
                System.out.println("use card: " + "13, 14");
                player.subtractNumCardOnHand();
            } else {
                System.out.println("use card: " + this.cardID);
            }
            player.subtractNumCardOnHand();
        } else {
            System.out.println("cannot use card: " + this.cardID);
        }
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
        return rand.nextInt(2) + 13;

//        Production return HERE
//        return ans;
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
