package game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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

    private Parent mapGenerator(int mapSelected){
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
        Image img = new Image(getClass().getResourceAsStream(String.format("/img/map/map%d.png", mapSelected)));
        ImageView bgImg = new ImageView(img);
//        bgImg.setImage(img);
        bgImg.setFitHeight(800);
        bgImg.setFitWidth(800);
        bgImg.setTranslateX(offSetX);
        bgImg.setTranslateY(offSetY);
        groupMap.getChildren().add(bgImg);

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

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });

        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                MapSelector selector = new MapSelector();
                selector.display();
                for(int i=0; i < selector.getMapNum(); i++){
                    selector.getBtn()[i].setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionEvent) {
                            //                GAME
                            playerController = new PlayerController(radius);
                            int mapSelected;
                            if (actionEvent.getSource().equals(selector.getBtn()[0])){
                                mapSelected = 1;
                                playerController.setLadder(selector.getMap1Ladder());
                            } else if (actionEvent.getSource().equals(selector.getBtn()[1])){
                                mapSelected = 2;
                                playerController.setLadder(selector.getMap2Ladder());
                            } else {
                                mapSelected = 3;
                                playerController.setLadder(selector.getMap3Ladder());
                            }
                            Scene sceneGame = new Scene(mapGenerator(mapSelected));
                            primaryStage.setScene(sceneGame);

                            Thread t = new Thread(playerController);
                            t.start();

                            groupMap.getChildren().addAll(playerController.getPlayers());
                        }
                    });
                }
            }
        });


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
