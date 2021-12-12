package game;

public class TrapTile {
    private final Player fromPlayer;
    private final int trapID;
    private final int tileNum;
    private boolean used;
    private Player targetPlayer;

    public TrapTile(Player player, int trapID){
        this.fromPlayer = player;
        this.trapID = trapID % 10;
        this.tileNum = player.getPosition();
        this.used = false;
    }

    public void action(Player targetPlayer){
        this.targetPlayer = targetPlayer;
        System.out.println("\nTrap triggered\n trapID: " + trapID + " tileNum: " + tileNum);
        this.used = true;
    }

    public int getTileNum() {
        return tileNum;
    }

    public boolean isUsed() {
        return used;
    }
}
