package game;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CardPopup {
    private static Stage popUpStage;
    public static void display(Image cardImg){
        popUpStage = new Stage();
        StackPane card_pane = new StackPane();
        ImageView imgView = new ImageView(cardImg);
        imgView.setFitHeight(600);
        imgView.setFitWidth(500);
        card_pane.getChildren().add(imgView);
        Scene scene = new Scene(card_pane, 500,600);
        popUpStage.setScene(card_pane.getScene());
        popUpStage.show();

        popUpStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                popUpStage.close();
            }
        });
    }

    public static Stage getPopUpStage() {
        return popUpStage;
    }
}
