package game;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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

public class Info {
    private Stage stage;

    public Info(String checker) {
        if (checker.equals("How to play")) {
            checker = "htp";
        } else if (checker.equals("Cards")) {
            checker = "cardinfo";
        } else if (checker.equals("Developers")) {
            checker = "dev";
        }
        stage = new Stage();
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
        stage.setScene(scene);
        stage.show();
    }

    public Info(int[] winnerList, Menu menu){
        menu.getMediaPlayer().stop();
        menu.setMedia(new Media(getClass().getResource("/sound/Victory.mp3").toExternalForm()));
        menu.setMediaPlayer(new MediaPlayer(menu.getMedia()));
        menu.getMediaPlayer().setVolume(0.4);
        menu.getMediaPlayer().play();

        int numWinner = winnerList.length;

        stage = new Stage();

        Rectangle bg = new Rectangle();
        bg.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/img/4_3.png"))));
        bg.setWidth(1080);
        bg.setHeight(810);

        Rectangle title = new Rectangle();
        if (numWinner == 1){
            title.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/img/winner.png"))));
            title.setWidth(327);
            title.setTranslateX(-163.5);
        } else {
            title.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/img/winners.png"))));
            title.setWidth(371);
            title.setTranslateX(-185.5);
        }
        title.setHeight(95);
        title.setTranslateY(-340);

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
                winner[0].setTranslateX(-120);
                winner[0].setTranslateY(-180);

                frame[0].setTranslateX(-140);
                frame[0].setTranslateY(-200);
                break;
            case 2:
                winner[0].setTranslateX(-285);
                winner[0].setTranslateY(-180);
                winner[1].setTranslateX(45);
                winner[1].setTranslateY(-180);

                frame[0].setTranslateX(-305);
                frame[0].setTranslateY(-200);
                frame[1].setTranslateX(25);
                frame[1].setTranslateY(-200);
                break;
            case 3:
                title.setTranslateY(-425);
                winner[0].setTranslateX(-270);
                winner[0].setTranslateY(-285);
                winner[1].setTranslateX(30);
                winner[1].setTranslateY(-285);

                winner[2].setTranslateX(-120);
                winner[2].setTranslateY(15);

                frame[0].setTranslateX(-290);
                frame[0].setTranslateY(-305);
                frame[1].setTranslateX(10);
                frame[1].setTranslateY(-305);

                frame[2].setTranslateX(-140);
                frame[2].setTranslateY(-5);
                break;
            case 4:
                title.setTranslateY(-425);
                winner[0].setTranslateX(-270);
                winner[0].setTranslateY(-285);
                winner[1].setTranslateX(30);
                winner[1].setTranslateY(-285);

                winner[2].setTranslateX(-270);
                winner[2].setTranslateY(15);
                winner[3].setTranslateX(30);
                winner[3].setTranslateY(15);

                frame[0].setTranslateX(-290);
                frame[0].setTranslateY(-305);
                frame[1].setTranslateX(10);
                frame[1].setTranslateY(-305);

                frame[2].setTranslateX(-290);
                frame[2].setTranslateY(-5);
                frame[3].setTranslateX(10);
                frame[3].setTranslateY(-5);
                break;
        }

        for (int i=0; i < numWinner; i++){
            winner[i].setFill(new ImagePattern(new Image(getClass().getResourceAsStream(String.format("/img/characters/%d-positive.png", winnerList[i])))));
        }

        Text txt = new Text("BACK TO MENU");
        txt.setCursor(Cursor.HAND);
        txt.setFont(Font.font(null, FontWeight.BOLD, 28));
        txt.setFill(Color.WHITE);
        txt.setTranslateX(-400);
        txt.setTranslateY(370);

        txt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                stage.close();
                menu.getMediaPlayer().stop();
                menu.setSceneMainMenu(new Scene(menu.mainMenu()));
                menu.startGame();
            }
        });

        Group group = new Group(title);
        group.getChildren().addAll(winner);
        group.getChildren().addAll(frame);
        StackPane panel = new StackPane();
        panel.getChildren().addAll(bg, group);
        panel.getChildren().addAll(txt);
        Scene scene = new Scene(panel, 1080, 810);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                stage.close();
                menu.getMediaPlayer().stop();
                menu.setSceneMainMenu(new Scene(menu.mainMenu()));
                menu.startGame();
            }
        });
    }

    public Stage getStage() {
        return stage;
    }
}

