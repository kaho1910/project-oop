package game;

public class PowerCard implements Card{
    private int cardID;
    private String cardUrl;
    private boolean plsDisposeMe = false;

    public PowerCard(int cardID){
        this.cardID = cardID;
        if (cardID % 10 == 0){
            this.cardUrl = "";
        } else {
            this.cardUrl = new String(String.format("/img/cards/%d.jpg", this.cardID));
        }
    }

    public void action(){
        System.out.println("use card:" + this.cardID);
//        switch แยก action ของแต่ละ ID
        plsDisposeMe = true;
    };

    public String getCardUrl() {
        return cardUrl;
    }

    public boolean isPlsDisposeMe() {
        return plsDisposeMe;
    }
}
