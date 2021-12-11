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
import javafx.application.*;

public class TargetPopup extends Application{
    private Button[] pbutton;
    private Player[] player;
    private Image profile[], frame[];
    private Rectangle framebox[], innerframebox[];
    private int selected,i=0, j;
    public void start(Stage popUpStage){
        StackPane select_pane = new StackPane();
        player = new Player[4];
        profile = new Image[4];
        frame = new Image[4];
        framebox = new Rectangle[4];
        innerframebox = new Rectangle[4];
        pbutton = new Button[4];
        for (i=0; i<=3;i++){
            player[i] = new Player(i + 1);
            pbutton[i] = new Button();
            profile[i] = player[i].getPlayerTable().getIm2();
            frame[i] = player[i].getPlayerTable().getIm1();
            framebox[i] = new Rectangle();
            innerframebox[i] = new Rectangle();
            pbutton[i].setText("select");
            framebox[i].setHeight(170);
            framebox[i].setWidth(170);
            framebox[i].setFill(new ImagePattern(frame[i]));
            innerframebox[i].setHeight(120);
            innerframebox[i].setWidth(120);
            innerframebox[i].setFill(new ImagePattern(profile[i]));

            if ((i + 1)%2 ==0){
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
            if ((i + 1) / 3 < 1){
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
        for (j=0;j<=3;j++) {
            pbutton[j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                String s = new String(pbutton[j].toString());
                int num = j;
                @Override
                public void handle(MouseEvent mouseEvent) {
//                    System.out.println(mouseEvent.getSource().toString().equals(s));
                    if (mouseEvent.getSource().toString().equals(s)){
                        for (int k=0;k<=3;k++){
                            pbutton[k].setDisable(true);
                        }
                        selected = num;
                        System.out.println("Target: " + selected);
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
}
