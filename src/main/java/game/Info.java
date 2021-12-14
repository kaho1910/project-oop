package game;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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

public class Info {
    private Stage mainStage;

    public Info(String checker) {
        if (checker.equals("How to play")) {
            checker = "htp";
        } else if (checker.equals("Cards")) {
            checker = "cardinfo";
        } else if (checker.equals("Developers")) {
            checker = "dev";
        }
        mainStage = new Stage();
        Rectangle out = new Rectangle();
        Rectangle in = new Rectangle();
        out.setWidth(1080);
        out.setHeight(810);
        in.setWidth(1080);
        in.setHeight(810);
        StackPane panel = new StackPane();
        Image out_img = new Image(getClass().getResourceAsStream("/img/" + checker + ".png"));
        Image in_img = new Image(getClass().getResourceAsStream("/img/4_3.gif"));
        in.setFill(new ImagePattern(in_img));
        out.setFill(new ImagePattern(out_img));
        panel.getChildren().addAll(in, out);
        Scene scene = new Scene(panel, 1080, 810);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public Info(int[] winnerList, Main main){
        int numWinner = winnerList.length;

        mainStage = new Stage();
        StackPane panel = new StackPane();

        Rectangle bg = new Rectangle();
        bg.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/img/4_3.png"))));
        bg.setWidth(1080);
        bg.setHeight(810);

        Rectangle title = new Rectangle();
        if (numWinner == 1){
            title.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/img/winner.png"))));
            title.setWidth(327);
            title.setTranslateX(376.5);
        } else {
            title.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/img/winners.png"))));
            title.setWidth(371);
            title.setTranslateX(354.5);
        }
        title.setHeight(95);
        title.setTranslateY(70);

        Rectangle[] winner = new Rectangle[numWinner];
        Rectangle[] frame = new Rectangle[numWinner];
        for (int i=0; i < numWinner; i++){
            winner[i] = new Rectangle();
            winner[i].setHeight(240);
            winner[i].setWidth(240);

            frame[i] = new Rectangle();
            frame[i].setHeight(280);
            frame[i].setWidth(280);
            frame[i].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/img/frame.png"))));
        }

        switch (numWinner){
            case 1:
                winner[0].setTranslateX(420);
                winner[0].setTranslateY(285);

                frame[0].setTranslateX(400);
                frame[0].setTranslateY(265);
                break;
            case 2:
                winner[0].setTranslateX(255);
                winner[0].setTranslateY(285);
                winner[1].setTranslateX(585);
                winner[1].setTranslateY(285);

                frame[0].setTranslateX(235);
                frame[0].setTranslateY(265);
                frame[1].setTranslateX(565);
                frame[1].setTranslateY(265);
                break;
            case 3:
                winner[0].setTranslateX(255);
                winner[0].setTranslateY(190);
                winner[1].setTranslateX(585);
                winner[1].setTranslateY(190);
                winner[2].setTranslateX(420);
                winner[2].setTranslateY(520);

                frame[0].setTranslateX(235);
                frame[0].setTranslateY(170);
                frame[1].setTranslateX(565);
                frame[1].setTranslateY(170);
                frame[2].setTranslateX(400);
                frame[2].setTranslateY(500);
                break;
            case 4:
                winner[0].setTranslateX(255);
                winner[0].setTranslateY(190);
                winner[1].setTranslateX(585);
                winner[1].setTranslateY(190);
                winner[2].setTranslateX(255);
                winner[2].setTranslateY(520);
                winner[3].setTranslateX(585);
                winner[3].setTranslateY(520);

                frame[0].setTranslateX(235);
                frame[0].setTranslateY(170);
                frame[1].setTranslateX(565);
                frame[1].setTranslateY(170);
                frame[2].setTranslateX(235);
                frame[2].setTranslateY(500);
                frame[3].setTranslateX(565);
                frame[3].setTranslateY(500);
                break;
        }

        for (int i=0; i < numWinner; i++){
            winner[i].setFill(new ImagePattern(new Image(getClass().getResourceAsStream(String.format("/img/characters/%d-positive.png", winnerList[i])))));
        }

        Text[] txt = {new Text("Restart"), new Text("Exit")};
        for (int i=0; i < txt.length; i++){
            txt[i].setCursor(Cursor.HAND);
            txt[i].setFont(Font.font(null, FontWeight.BOLD, 48));
            txt[i].setFill(Color.WHITE);
            txt[i].setTranslateX(300 + 370 * i);
            txt[i].setTranslateY(830);
        }

        txt[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                mainStage.close();
                main.getMediaPlayer().stop();
                main.setSceneMainMenu(new Scene(main.mainMenu()));
                main.startGame();
            }
        });

        txt[1].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

        Group group = new Group(title);
        group.getChildren().addAll(winner);
        group.getChildren().addAll(txt);
        group.getChildren().addAll(frame);
        panel.getChildren().addAll(bg, group);
        Scene scene = new Scene(panel, 1080, 810);
        mainStage.setScene(scene);
        mainStage.show();

        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public Stage getMainStage() {
        return mainStage;
    }
}

