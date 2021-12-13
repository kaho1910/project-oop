package game;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Info {
    private Stage mainStage;
    private String checker;
    public Info(String checker){
        if (checker.equals("How to play")){
            checker = "htp";
        } else if (checker.equals("Cards")){
            checker = "cardinfo";
        }
        else if (checker.equals("Developers")) {
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
        Image in_img = new Image(getClass().getResourceAsStream("/img/4_3.png"));
        in.setFill(new ImagePattern(in_img));
        out.setFill(new ImagePattern(out_img));
        panel.getChildren().addAll(in, out);
        Scene scene = new Scene(panel, 1080,810);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public Stage getMainStage() {
        return mainStage;
    }
}

