package game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    private static final int playerNum = 4;
    private static final int TileSize = 80;
    private static final int Width = 10;
    private static final int Height = 10;
    private static final int offSetX = TileSize * Width / 2;
    private static final int offSetY = 80;
    private PlayerController playerController;
    private Player[] players;
    private int[] cardPool = {11, 12, 13, 14, 21, 21, 22, 22, 23, 23, 24, 24, 31, 31, 31, 32, 32, 32, 33, 33, 33, 34, 34, 34, 41, 41, 41, 41, 42, 42, 42, 42, 43, 43, 43, 43, 44, 44, 44, 44};

    private Group groupMap = new Group();
    private Group groupMainMenu = new Group();

    private Scene sceneMainMenu;
    private MapSelector selector;
    private Text startBtn;

    private Parent mainMenu(){
        StackPane root = new StackPane();
        root.getChildren().addAll(groupMainMenu);
        root.setPrefSize(Width * TileSize + offSetX * 2, Height * TileSize + offSetY * 2);

        Image bg = new Image(getClass().getResourceAsStream("/img/menu.png"));
        ImagePattern bgPattern = new ImagePattern(bg);
        Rectangle rect = new Rectangle();
        rect.setFill(bgPattern);
        rect.setWidth(Width * TileSize + offSetX * 2);
        rect.setHeight(Height * TileSize + offSetY * 2);

//        Text gameTitle = new Text();
//        gameTitle.setText("Snake And Ladder");
//        gameTitle.setFont(Font.font(60));

//        startBtn = new Button("Start");
//        startBtn = new Button("Start");
        startBtn = new Text("Start");
        startBtn.setFont(Font.font(null, FontWeight.BOLD, 72));
        startBtn.setFill(Color.WHITE);
        startBtn.setTranslateX(300);
        startBtn.setTranslateY(500);

        Button devIntro = new Button("developer");
//
        startBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                sceneMainMenu.setCursor(Cursor.HAND);
            }
        });

        startBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                sceneMainMenu.setCursor(Cursor.DEFAULT);
            }
        });

        groupMainMenu.getChildren().addAll(rect, startBtn);

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
        bgImg.setFitHeight(Height * TileSize);
        bgImg.setFitWidth(Width * TileSize);
        bgImg.setTranslateX(offSetX);
        bgImg.setTranslateY(offSetY);
        groupMap.getChildren().add(bgImg);

        players = playerController.getPlayers();
        PlayerTable playerTable;
        StackPane pane;
        for(int i=0; i < playerNum; i++){
            playerTable = players[i].getPlayerTable();
            pane = playerTable.getLayout();
            pane.setPrefSize(Width * TileSize / 2, Height * TileSize / 2);
            pane.setTranslateX((i % 2) * Width * TileSize / 2 * 3);
            pane.setTranslateY(i > 1 ?  Width * TileSize / 2 + 80 : 80);
            groupMap.getChildren().add(pane);
        }

        return root;
    }

    public void start(Stage primaryStage){
        sceneMainMenu = new Scene(mainMenu());
        primaryStage.setTitle("Snakes and Ladders");
        primaryStage.setScene(sceneMainMenu);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });

        startBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (selector != null){
                    selector.getPopUpStage().close();
                }
                selector = new MapSelector();
                selector.display();
                for(int i=0; i < selector.getMapNum(); i++){
                    selector.getBtn()[i].setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionEvent) {
                            //                GAME
                            playerController = new PlayerController(cardPool);
                            int mapSelected;
                            if (actionEvent.getSource().equals(selector.getBtn()[0])){
                                mapSelected = 1;
                                playerController.setLadder(selector.getMap1Ladder());
                                playerController.setPickCard(selector.getMap1PickCard());
                            } else if (actionEvent.getSource().equals(selector.getBtn()[1])){
                                mapSelected = 2;
                                playerController.setLadder(selector.getMap2Ladder());
                                playerController.setPickCard(selector.getMap2PickCard());
                            } else {
                                mapSelected = 3;
                                playerController.setLadder(selector.getMap3Ladder());
                                playerController.setPickCard(selector.getMap3PickCard());
                            }

                            selector.getPopUpStage().close();

                            Scene sceneGame = new Scene(mapGenerator(mapSelected));
                            primaryStage.setScene(sceneGame);

                            Player[] players = playerController.getPlayers();
                            for (Player p:
                                    players) {
                                Rectangle[] cardFrames = p.getPlayerTable().getCardFrame();
                                for (Rectangle cardFrame:
                                     cardFrames) {
                                    cardFrame.setOnMouseEntered(new EventHandler<MouseEvent>() {
                                        public void handle(MouseEvent mouseEvent) {
                                            sceneGame.setCursor(Cursor.HAND);
                                        }
                                    });
                                    cardFrame.setOnMouseExited(new EventHandler<MouseEvent>() {
                                        public void handle(MouseEvent mouseEvent) {
                                            sceneGame.setCursor(Cursor.DEFAULT);
                                        }
                                    });
                                }
                                p.getPlayerTable().getDiceButton().setOnMouseEntered(new EventHandler<MouseEvent>() {
                                    public void handle(MouseEvent mouseEvent) {
                                        if (!p.getPlayerTable().getDiceButton().isDisabled()) {
                                            sceneGame.setCursor(Cursor.HAND);
                                        }
                                    }
                                });
                                p.getPlayerTable().getDiceButton().setOnMouseExited(new EventHandler<MouseEvent>() {
                                    public void handle(MouseEvent mouseEvent) {
                                        sceneGame.setCursor(Cursor.DEFAULT);
                                    }
                                });
                            }

                            Thread t = new Thread(playerController);
                            t.start();

                            groupMap.getChildren().addAll(playerController.getPlayers()[3], playerController.getPlayers()[2], playerController.getPlayers()[1], playerController.getPlayers()[0]);
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
