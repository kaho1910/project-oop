package game;

public class PowerCard implements Card{
    private int cardID;
    private String cardUrl;

    public PowerCard(int cardID){
        this.cardID = cardID;
        if (cardID % 10 == 0){
            this.cardUrl = "";
        } else {
            this.cardUrl = new String(String.format("/img/cards/%d.jpg", this.cardID));
        }
    }

    public void action(){
//        switch แยก action ของแต่ละ ID
    };

    public String getCardUrl() {
        return cardUrl;
    }
}
