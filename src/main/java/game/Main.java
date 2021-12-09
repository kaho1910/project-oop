package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class Main extends Application {
    private static final int playerNum = 4;
    private static final int TileSize = 80;
    private static final int Width = 10;
    private static final int Height = 10;
    private static final int offSetX = 400;
    private static final int offSetY = 80;
    private static final int radius = 40;
    private PlayerController playerController;
    private Player[] players;

    private Group tileGroup = new Group();

    private Parent createContent(){
        StackPane root = new StackPane();
        root.setPrefSize(Width * TileSize + offSetX * 2, Height * TileSize + offSetY * 2);
        root.getChildren().addAll(tileGroup);

        for(int i=0; i < Height; i++){
            for(int j=0; j < Width; j++){
                Tile tile = new Tile(TileSize);
                tile.setTranslateX(j * TileSize + offSetX);
                tile.setTranslateY(i * TileSize + offSetY);
                tileGroup.getChildren().add(tile);
            }
        }
//        Image img = new Image("map1.jpg");
        Image img = new Image(getClass().getResourceAsStream("/img/map1.jpg"));
        ImageView bgImg = new ImageView(img);
//        bgImg.setImage(img);
        bgImg.setFitHeight(800);
        bgImg.setFitWidth(800);
        bgImg.setTranslateX(offSetX);
        bgImg.setTranslateY(offSetY);
        tileGroup.getChildren().add(bgImg);

        playerController = new PlayerController(radius);
        players = playerController.getPlayers();
        PlayerTable playerTable;
        StackPane pane;
        for(int i=0; i < playerNum; i++){
            playerTable = players[i].getPlayerTable();
            pane = playerTable.getLayout();
            pane.setPrefSize(400, 400);
            pane.setTranslateX((i % 2) * 1200);
            pane.setTranslateY(i > 1 ? 480 : 80);
            tileGroup.getChildren().add(pane);
        }

        return root;
    }

    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Snake and Ladder");
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread t = new Thread(playerController);
        t.start();

        tileGroup.getChildren().addAll(playerController.getPlayers());
    }

    public static void main(String[] args){
        launch(args);
    }

    public static int getPlayerNum(){
        return playerNum;
    }

    public static int getTileSize(){
        return TileSize;
    }

    public static int getWidth(){
        return Width;
    }

    public static int getHeight(){
        return Height;
    }

    public static int getOffSetX() {
        return offSetX;
    }

    public static int getOffSetY() {
        return offSetY;
    }
}
