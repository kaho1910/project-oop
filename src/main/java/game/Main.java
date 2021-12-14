package game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    private Text[] mainMenuBtn;
    private String[] txtBtn = {"Start", "How to play", "Cards", "Developers"};
    private Info info;
    private Text teams;

    private MediaPlayer mediaPlayer;
    private Media media;

    private Main thisMain = this;
    private Stage primaryStage;

    public Parent mainMenu() {
        StackPane root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(Color.web("#d7d7d7"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().addAll(groupMainMenu);
        root.setPrefSize(Width * TileSize + offSetX * 2, Height * TileSize + offSetY * 2);

        Image bg = new Image(getClass().getResourceAsStream("/img/menu.gif"));
        ImagePattern bgPattern = new ImagePattern(bg);
        Rectangle rect = new Rectangle();
        rect.setFill(bgPattern);
        rect.setWidth(Width * TileSize + offSetX * 2);
        rect.setHeight(Height * TileSize + offSetY * 2);

        mainMenuBtn = new Text[4];
        for (int i = 0; i < mainMenuBtn.length; i++) {
            mainMenuBtn[i] = new Text(txtBtn[i]);
            mainMenuBtn[i].setFont(Font.font(null, FontWeight.BOLD, 72));
            mainMenuBtn[i].setFill(Color.WHITE);
            mainMenuBtn[i].setTranslateX(200);
            mainMenuBtn[i].setTranslateY(470 + i * 110);

            mainMenuBtn[i].setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    sceneMainMenu.setCursor(Cursor.HAND);
                }
            });

            mainMenuBtn[i].setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    sceneMainMenu.setCursor(Cursor.DEFAULT);
                }
            });
        }

        Image title = new Image(getClass().getResourceAsStream("/img/title.png"));
        ImagePattern titleBox = new ImagePattern(title);
        Rectangle titleRect = new Rectangle();
        titleRect.setFill(titleBox);
        titleRect.setWidth(1105);
        titleRect.setHeight(123.5);
        titleRect.setTranslateX(100);
        titleRect.setTranslateY(130);

        Image subTitle = new Image(getClass().getResourceAsStream("/img/subtitle.png"));
        ImagePattern subTitleBox = new ImagePattern(subTitle);
        Rectangle subTitleRect = new Rectangle();
        subTitleRect.setFill(subTitleBox);
        subTitleRect.setWidth(433);
        subTitleRect.setHeight(61);
        subTitleRect.setTranslateX(110);
        subTitleRect.setTranslateY(290);

        teams = new Text("© TEAM OHM 2021");
        teams.setFont(Font.font(null, FontWeight.BOLD, 26));
        teams.setFill(Color.WHITE);
        teams.setTranslateX(200);
        teams.setTranslateY(890);

        groupMainMenu.getChildren().addAll(rect, titleRect, subTitleRect, teams);
        groupMainMenu.getChildren().addAll(mainMenuBtn);

        return root;
    }

    private Parent mapGenerator(int mapSelected) {
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
        Table ptb;
        StackPane pane;
        for (int i = 0; i < playerNum; i++) {
            ptb = players[i].getPlayerTable();
            pane = ptb.getLayout();
            pane.setPrefSize(Width * TileSize / 2, Height * TileSize / 2);
            pane.setTranslateX((i % 2) * Width * TileSize / 2 * 3);
            pane.setTranslateY(i > 1 ? Width * TileSize / 2 + 80 : 80);
            groupMap.getChildren().add(pane);
        }

        return root;
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        sceneMainMenu = new Scene(mainMenu());
        this.primaryStage.setTitle("Snakes and Ladders");
        this.primaryStage.show();

        startGame();
    }

    public void startGame(){
        this.primaryStage.setScene(sceneMainMenu);
        this.primaryStage.centerOnScreen();

        media = new Media(getClass().getResource("/sound/BGM-LOCO.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        info = new Info("How to play");
        info.getStage().close();

        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });

        mainMenuBtn[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (selector != null) {
                    selector.getPopUpStage().close();
                }
                selector = new MapSelector();
                selector.display();

                if (!info.equals(null)) {
                    info.getStage().close();
                }

                for (int i = 0; i < selector.getMapNum(); i++) {
                    selector.getBtn()[i].setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionEvent) {
                            //                GAME
                            playerController = new PlayerController(cardPool, thisMain);
                            int mapSelected;
                            if (actionEvent.getSource().equals(selector.getBtn()[0])) {
                                mapSelected = 1;
                                playerController.setLadder(selector.getMap1Ladder());
                                playerController.setPickCard(selector.getMap1PickCard());
                            } else if (actionEvent.getSource().equals(selector.getBtn()[1])) {
                                mapSelected = 2;
                                playerController.setLadder(selector.getMap2Ladder());
                                playerController.setPickCard(selector.getMap2PickCard());
                            } else {
                                mapSelected = 3;
                                playerController.setLadder(selector.getMap3Ladder());
                                playerController.setPickCard(selector.getMap3PickCard());
                            }

                            selector.getPopUpStage().close();

                            mediaPlayer.stop();

                            media = new Media(getClass().getResource(String.format("/sound/map%d.mp3", mapSelected)).toExternalForm());
                            mediaPlayer = new MediaPlayer(media);
                            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                            mediaPlayer.setVolume(0.65);
                            mediaPlayer.play();

                            Scene sceneGame = new Scene(mapGenerator(mapSelected));
                            primaryStage.setScene(sceneGame);

                            Player[] players = playerController.getPlayers();
                            for (Player p :
                                    players) {
                                Rectangle[] cardFrames = p.getPlayerTable().getCardFrame();
                                for (Rectangle cardFrame :
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

        mainMenuBtn[1].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (info != null) {
                    info.getStage().close();
                }
                info = new Info(txtBtn[1]);
            }
        });

        mainMenuBtn[2].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (info != null) {
                    info.getStage().close();
                }
                info = new Info(txtBtn[2]);
            }
        });

        mainMenuBtn[3].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (info != null) {
                    info.getStage().close();
                }
                info = new Info(txtBtn[3]);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scene getSceneMainMenu() {
        return sceneMainMenu;
    }

    public void setSceneMainMenu(Scene sceneMainMenu) {
        this.sceneMainMenu = sceneMainMenu;
    }

    public static int getPlayerNum() {
        return playerNum;
    }

    public static int getTileSize() {
        return TileSize;
    }

    public static int getWidth() {
        return Width;
    }

    public static int getHeight() {
        return Height;
    }

    public static int getOffSetX() {
        return offSetX;
    }

    public static int getOffSetY() {
        return offSetY;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
