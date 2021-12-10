package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CardPopup {

    public static void display(String card_url){
        Stage stage = new Stage();
        //stage.initModality(Modality.APPLICATION_MODAL);
        StackPane card_pane = new StackPane();
        Image img = new Image(CardPopup.class.getResourceAsStream(card_url));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(600);
        imgView.setFitWidth(500);
        card_pane.getChildren().add(imgView);
        Scene scene = new Scene(card_pane, 500,600);
        stage.setScene(card_pane.getScene());
        stage.show();
    }


}
