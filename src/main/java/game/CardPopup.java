package game;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CardPopup {
    private static Stage popUpStage;
    private static Button useCardBtn;
    private static boolean flag;
    public static void display(Image cardImg){
        popUpStage = new Stage();
        StackPane card_pane = new StackPane();
        ImageView imgView = new ImageView(cardImg);
        imgView.setFitHeight(600);
        imgView.setFitWidth(500);

//        card_pane.getChildren().add(imgView);

        useCardBtn = new Button("USE THIS CARD");
        useCardBtn.setFont(Font.font(24));

        VBox box = new VBox(imgView, useCardBtn);
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(500, 660);
        box.setSpacing(10);
        card_pane.getChildren().add(box);

//        card_pane.getChildren().add(useCardBtn);

        Scene scene = new Scene(card_pane, 500,690);
        popUpStage.setScene(scene);
        popUpStage.show();

        popUpStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                popUpStage.close();
                flag = true;
            }
        });
    }

    public static Stage getPopUpStage() {
        return popUpStage;
    }

    public static Button getUseCardBtn() {
        return useCardBtn;
    }

    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        CardPopup.flag = flag;
    }
}
