package game;

import java.util.Random;

public class PowerCard implements Card{
    private int cardID;
    private String cardUrl;
    private boolean plsDisposeMe = false;
    private PlayerController controller;
    private Player player;

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
        System.out.println("\nuse card:" + this.cardID);
//        switch แยก action ของแต่ละ ID
        plsDisposeMe = true;
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
