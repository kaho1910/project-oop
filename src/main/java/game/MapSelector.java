package game;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MapSelector {
    private static final int mapNum = 3;
    private Button[] btn = new Button[mapNum];
    private String[] mapName = {"Yume no Taiki", "Judai in The City", "Yuji on Train"};

    private int[][] map1Ladder = {{14, 7}, {16, 25}, {32, 49}, {60, 42}, {46, 66}, {89, 72}, {77, 84}, {98, 78}};
    private int[][] map2Ladder = {{3, 18}, {30, 11}, {22, 37}, {66, 46}, {80, 61}, {90, 73}, {74, 87}, {77, 97}};
    private int[][] map3Ladder = {{18, 3}, {11, 30}, {25, 36}, {41, 61}, {68, 53}, {63, 78}, {92, 72}, {99, 82}};

    private Rectangle rect = new Rectangle();
    private Group group = new Group();
    private Stage popUpStage;
    private Scene scene;

    private Parent mapSelect(){
        StackPane root = new StackPane();
        root.getChildren().add(group);
        root.setAlignment(Pos.CENTER);

        Image bg = new Image(getClass().getResourceAsStream("/img/menuSqr.gif"));
        ImagePattern bgPattern = new ImagePattern(bg);
        rect.setFill(bgPattern);
//        rect.setFill(Color.TRANSPARENT);
        rect.setWidth(400);
        rect.setHeight(400);

        Image[] img = new Image[mapNum];
        ImagePattern[] bgImg = new ImagePattern[mapNum];

        for(int i=0; i < mapNum; i++){
            img[i] = new Image(getClass().getResourceAsStream(String.format("/img/map/preview%d.jpg", i + 1)));
            bgImg[i] = new ImagePattern(img[i]);
        }

        Image title = new Image(getClass().getResourceAsStream("/img/select.png"));
        ImageView container = new ImageView(title);
        container.setFitWidth(240);
        container.setFitHeight(42.5);

        for(int i=0; i < mapNum; i++){
            btn[i] = new Button(mapName[i]);
            btn[i].setFont(Font.font(16));
        }

        btn[0].setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                rect.setFill(new ImagePattern(img[0]));
                scene.setCursor(Cursor.HAND);
            }
        });

        btn[1].setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                rect.setFill(new ImagePattern(img[1]));
                scene.setCursor(Cursor.HAND);
            }
        });

        btn[2].setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                rect.setFill(new ImagePattern(img[2]));
                scene.setCursor(Cursor.HAND);
            }
        });

        btn[0].setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });

        btn[1].setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });

        btn[2].setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });

        VBox vBox = new VBox(btn[0], btn[1], btn[2]);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
//        vBox.setPrefSize(400, 400);

        VBox box = new VBox(container, vBox);
        box.setSpacing(70);
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(400, 400);

        root.setPrefSize(400, 400);

        group.getChildren().addAll(rect, box);

        return root;
    }
    public void display() {
        popUpStage = new Stage();
        scene = new Scene(mapSelect());
        popUpStage.setTitle("Select map");
        popUpStage.setScene(scene);
        popUpStage.show();

        popUpStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                popUpStage.close();
            }
        });
    }

    public Button[] getBtn() {
        return btn;
    }

    public static int getMapNum(){
        return mapNum;
    }

    public Stage getPopUpStage() {
        return popUpStage;
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
