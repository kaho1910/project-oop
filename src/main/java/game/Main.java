package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int playerNum = 4;
    private static final int TileSize = 80;
    private static final int Width = 10;
    private static final int Height = 10;
    private static final int offSetX = 480;
    private static final int offSetY = 80;
    private static final int radius = 40;

    private Player[] players = new Player[playerNum];

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

        return root;
    }

    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Snake and Ladder");
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread[] thread = new Thread[4];
        for(int i=0; i < playerNum; i++){
            players[i] = new Player(i + 1, radius);
            thread[i] = new Thread(players[i]);
            thread[i].start();
            tileGroup.getChildren().addAll(players[i]);
        }
    }

    public static void main(String[] args){
        launch(args);
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
