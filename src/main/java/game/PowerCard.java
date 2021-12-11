package game;

public class PowerCard implements Card{
    private int cardID;
    private String cardUrl;

    public PowerCard(int cardID, String cardUrl){
        this.cardID = cardID;
        this.cardUrl = cardUrl;
    }

    public void action(){
//        switch แยก action ของแต่ละ ID
    };

    public String getCardUrl() {
        return cardUrl;
    }
}
