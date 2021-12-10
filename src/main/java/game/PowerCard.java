package game;

import javafx.scene.image.Image;

public class PowerCard implements Card{
    private int id;
    private String cardUrl;
    private Image cardImg;

    public PowerCard(){
    }

    public void action(){
//        switch แยก action ของแต่ละ ID
    };

    public String getCardUrl() {
        return cardUrl;
    }

    public void setCardUrl(String cardUrl) {
        this.cardUrl = cardUrl;
    }
}
