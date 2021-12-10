package game;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MapSelector {
    private static final int mapNum = 3;
    private Button[] btn = new Button[mapNum];

    private boolean selected = false;

    private Group group = new Group();

    private Parent mapSelect(){
        StackPane root = new StackPane();
        root.getChildren().add(group);

        btn[0] = new Button("Yume no Taiki");
        btn[1] = new Button("Judai in The City");
        btn[2] = new Button("Yuji on Train");

        VBox vBox = new VBox(btn[0], btn[1], btn[2]);
        vBox.setSpacing(20);

        root.setPrefSize(400, 400);

        group.getChildren().add(vBox);

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
}
