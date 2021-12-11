package game;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.application.*;
import javafx.util.Duration;

public class TargetPopup extends Application{
    private Stage popUpStage;
    private Button[] pbutton;
    private PlayerTable[] player;
    private Image profile[], frame[];
    private Rectangle framebox[], innerframebox[];
    private int selected,i=0, j;
    public void start(Stage popUpStage){
        //popUpStage = new Stage();
        StackPane select_pane = new StackPane();
        player = new PlayerTable[6];
        profile = new Image[6]; frame = new Image[6];
        framebox = new Rectangle[6]; innerframebox = new Rectangle[6];
        pbutton = new Button[6];
        for (i =1; i<=4;i++){
        pbutton[i] = new Button();
        player[i] = new PlayerTable(i);
        profile[i] = player[i].getIm2(); frame[i] = player[i].getIm1();
        framebox[i] = new Rectangle(); innerframebox[i] = new Rectangle();
        pbutton[i].setText("select");
        framebox[i].setHeight(170);
        framebox[i].setWidth(170);
        framebox[i].setFill(new ImagePattern(frame[i]));
        innerframebox[i].setHeight(120);
        innerframebox[i].setWidth(120);
        innerframebox[i].setFill(new ImagePattern(profile[i]));

        if (i%2 ==0){
            framebox[i].setTranslateX(200);
            innerframebox[i].setTranslateX(200);
            pbutton[i].setTranslateX(200);
            //framebox[i].setTranslateY();
        }
        else{
            framebox[i].setTranslateX(-200);
            innerframebox[i].setTranslateX(-200);
            pbutton[i].setTranslateX(-200);
        }
        if (i/3 < 1){
            framebox[i].setTranslateY(-150);
            innerframebox[i].setTranslateY(-150);
            pbutton[i].setTranslateY(-30);
        }
        else{
            framebox[i].setTranslateY(100);
            innerframebox[i].setTranslateY(100);
            pbutton[i].setTranslateY(220);
        }
        select_pane.getChildren().addAll(framebox[i], innerframebox[i], pbutton[i]);
        }
        for (j = 1;j<=4;j++) {
            pbutton[j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                String s = new String(pbutton[j].toString());
                int num = j;
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println(mouseEvent.getSource().toString().equals(s));
                    if (mouseEvent.getSource().toString().equals(s)){
                        for (int k=1;k<=4;k++){
                            pbutton[k].setDisable(true);
                        }
                        selected = num;
                        System.out.println(selected);

                    }
                }
            });

        }
        Scene scene = new Scene(select_pane, 800, 600);

        popUpStage.setScene(scene);
        popUpStage.show();

        popUpStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                popUpStage.close();
            }
        });
    }
    public int getSelected(){
        return this.selected;
    }

    public static void main(String[] args) {
        launch();
    }



//    public static Stage getPopUpStage() {
//        return popUpStage;
//    }
}
