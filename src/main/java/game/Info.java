package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Info extends Application {

    public void start(Stage mainStage){
        Rectangle out = new Rectangle();
        Rectangle in = new Rectangle();
        out.setWidth(1080);
        out.setHeight(810);
        in.setWidth(1080);
        in.setHeight(810);
        StackPane panel = new StackPane();
        Image out_img = new Image(getClass().getResourceAsStream("/img/htp.png"));
        Image in_img = new Image(getClass().getResourceAsStream("/img/4_3.png"));
        in.setFill(new ImagePattern(in_img));
        out.setFill(new ImagePattern(out_img));
        panel.getChildren().addAll(in, out);
        Scene scene = new Scene(panel, 1080,810);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}

