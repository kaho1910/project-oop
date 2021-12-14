package game;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TargetPopup {
    private Stage popUpStage;
    private Button[] pbutton;
    private PlayerController controller;
    private Player fromPlayer;
    private Player[] players;
    private Image profile[], frame[];
    private Rectangle framebox[], innerframebox[];
    private int target, j;
    private boolean selected;
    private boolean cancelled;

    public TargetPopup(PlayerController controller) {
//        constructor += Player fromPlayer
        this.controller = controller;
        this.players = this.controller.getPlayers();
        this.fromPlayer = this.controller.getPlayers()[0];
    }
//    public TargetPopup(){
//        int[] cardPool = {11, 12, 13, 14, 21, 21, 22, 22, 23, 23, 24, 24, 31, 31, 31, 32, 32, 32, 33, 33, 33, 34, 34, 34, 41, 41, 41, 41, 42, 42, 42, 42, 43, 43, 43, 43, 44, 44, 44, 44};
//        this.controller = new PlayerController(cardPool);
//        this.players = this.controller.getPlayers();
//        this.fromPlayer = this.controller.getPlayers()[0];
//    }

    public void display() {
        this.selected = false;
        this.cancelled = false;
        popUpStage = new Stage();
        StackPane select_pane = new StackPane();
        profile = new Image[4];
        frame = new Image[4];
        framebox = new Rectangle[4];
        innerframebox = new Rectangle[4];
        pbutton = new Button[4];
        for (int i = 0; i <= 3; i++) {
//            MAKE THIS WORK!
//            player[i] = new Player(i + 1);
            pbutton[i] = new Button();
            profile[i] = controller.getPlayers()[i].getPlayerTable().getIm2();
            frame[i] = controller.getPlayers()[i].getPlayerTable().getIm1();
            framebox[i] = new Rectangle();
            innerframebox[i] = new Rectangle();
            pbutton[i].setText("select");
            pbutton[i].setDisable(true);
            framebox[i].setHeight(170);
            framebox[i].setWidth(170);
            framebox[i].setFill(new ImagePattern(frame[i]));
            innerframebox[i].setHeight(120);
            innerframebox[i].setWidth(120);
            innerframebox[i].setFill(new ImagePattern(profile[i]));

            if ((i + 1) % 2 == 0) {
                framebox[i].setTranslateX(200);
                innerframebox[i].setTranslateX(200);
                pbutton[i].setTranslateX(200);
                //framebox[i].setTranslateY();
            } else {
                framebox[i].setTranslateX(-200);
                innerframebox[i].setTranslateX(-200);
                pbutton[i].setTranslateX(-200);
            }
            if ((i + 1) / 3 < 1) {
                framebox[i].setTranslateY(-150);
                innerframebox[i].setTranslateY(-150);
                pbutton[i].setTranslateY(-30);
            } else {
                framebox[i].setTranslateY(100);
                innerframebox[i].setTranslateY(100);
                pbutton[i].setTranslateY(220);
            }
            select_pane.getChildren().addAll(framebox[i], innerframebox[i], pbutton[i]);
        }
        for (j = 0; j <= 3; j++) {
            pbutton[j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                String s = pbutton[j].toString();
                int num = j;

                public void handle(MouseEvent mouseEvent) {
//                    System.out.println(mouseEvent.getSource().toString().equals(s));
                    if (mouseEvent.getSource().toString().equals(s)) {
                        for (int k = 0; k <= 3; k++) {
                            pbutton[k].setDisable(true);
                        }
                        target = num;
                        selected = true;
//                        System.out.println("Target: " + target);
                        popUpStage.close();
                    }
                }
            });

        }
        Scene scene = new Scene(select_pane, 800, 600);

        popUpStage.setScene(scene);
        popUpStage.show();

        popUpStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                cancelled = true;
                popUpStage.close();
            }
        });
    }

    public int getTarget() {
        return this.target;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Stage getPopUpStage() {
        return popUpStage;
    }

    public Button[] getPbutton() {
        return pbutton;
    }
}
