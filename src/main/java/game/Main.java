package game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    private StackPane root = new StackPane();

    private static final int playerNum = 4;
    private static final int TileSize = 80;
    private static final int Width = 10;
    private static final int Height = 10;
    private static final int offSetX = 400;
    private static final int offSetY = 80;
    private static final int radius = 40;
    private PlayerController playerController;
    private Player[] players;

    private Group groupMap = new Group();
    private Group groupMainMenu = new Group();

    private Button startBtn;

    private Parent mainMenu(){
        StackPane root = new StackPane();
        root.getChildren().addAll(groupMainMenu);
        root.setPrefSize(Width * TileSize + offSetX * 2, Height * TileSize + offSetY * 2);

//        Text gameTitle = new Text();
//        gameTitle.setText("Snake And Ladder");
//        gameTitle.setFont(Font.font(60));

        startBtn = new Button("Start");
//        Button devIntro = new Button("developer");

        groupMainMenu.getChildren().addAll(startBtn);

        return root;
    }

    private Parent mapGenerator(){
        StackPane root = new StackPane();
        root.getChildren().addAll(groupMap);
        root.setPrefSize(Width * TileSize + offSetX * 2, Height * TileSize + offSetY * 2);

//        for(int i=0; i < Height; i++){
//            for(int j=0; j < Width; j++){
//                Tile tile = new Tile(TileSize);
//                tile.setTranslateX(j * TileSize + offSetX);
//                tile.setTranslateY(i * TileSize + offSetY);
//                tileGroupMap.getChildren().add(tile);
//            }
//        }

        Image img = new Image(getClass().getResourceAsStream("/img/map1.jpg"));
        ImageView bgImg = new ImageView(img);
//        bgImg.setImage(img);
        bgImg.setFitHeight(800);
        bgImg.setFitWidth(800);
        bgImg.setTranslateX(offSetX);
        bgImg.setTranslateY(offSetY);
        groupMap.getChildren().add(bgImg);

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
            groupMap.getChildren().add(pane);
        }

        return root;
    }

    public void start(Stage primaryStage){
        Scene sceneMainMenu = new Scene(mainMenu());
        primaryStage.setTitle("Snake and Ladder");
        primaryStage.setScene(sceneMainMenu);
        primaryStage.show();

        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
//                GAME
                Scene sceneGame = new Scene(mapGenerator());
                primaryStage.setScene(sceneGame);

                Thread t = new Thread(playerController);
                t.start();

                groupMap.getChildren().addAll(playerController.getPlayers());
            }
        });
//
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
