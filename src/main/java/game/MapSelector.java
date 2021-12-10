package game;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MapSelector {
    private static final int mapNum = 3;
    private Button[] btn = new Button[mapNum];

    private boolean selected = false;
    private int[][] map1Ladder = {{14, 7}, {16, 25}, {32, 49}, {60, 42}, {46, 66}, {89, 72}, {77, 84}, {98, 78}};
    private int[][] map2Ladder = {{3, 18}, {30, 11}, {22, 37}, {66, 46}, {80, 61}, {90, 73}, {74, 87}, {77, 97}};
    private int[][] map3Ladder = {{18, 3}, {11, 30}, {25, 36}, {41, 61}, {68, 53}, {63, 78}, {92, 72}, {99, 82}};

    private Rectangle rect = new Rectangle();
    private Group group = new Group();

    private Parent mapSelect(){
        StackPane root = new StackPane();
        root.getChildren().add(group);
        root.setAlignment(Pos.CENTER);

        rect.setFill(Color.TRANSPARENT);
        rect.setWidth(400);
        rect.setHeight(400);

        Image[] img = new Image[mapNum];
        ImagePattern[] bgImg = new ImagePattern[mapNum];

        for(int i=0; i < mapNum; i++){
            img[i] = new Image(getClass().getResourceAsStream(String.format("/img/map/preview%d.jpg", i + 1)));
            bgImg[i] = new ImagePattern(img[i]);
        }

        btn[0] = new Button("Yume no Taiki");
        btn[1] = new Button("Judai in The City");
        btn[2] = new Button("Yuji on Train");

        btn[0].setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                rect.setFill(new ImagePattern(img[0]));
            }
        });

        btn[1].setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                rect.setFill(new ImagePattern(img[1]));
            }
        });

        btn[2].setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                rect.setFill(new ImagePattern(img[2]));
            }
        });

        VBox vBox = new VBox(btn[0], btn[1], btn[2]);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(400, 400);

        root.setPrefSize(400, 400);

        group.getChildren().addAll(rect, vBox);

        return root;
    }
    public void display() {
        Stage popUpStage = new Stage();
        Scene scene = new Scene(mapSelect());
        popUpStage.setTitle("Select map");
        popUpStage.setScene(scene);
        popUpStage.show();
    }

    public Button[] getBtn() {
        return btn;
    }

    public static int getMapNum(){
        return mapNum;
    }

    public boolean isSelected() {
        return selected;
    }

    public int[][] getMap1Ladder() {
        return map1Ladder;
    }

    public int[][] getMap2Ladder() {
        return map2Ladder;
    }

    public int[][] getMap3Ladder() {
        return map3Ladder;
    }
}
