package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int TileSize = 80;
    private static final int Width = 10;
    private static final int Height = 10;

    private Group tileGroup = new Group();

    private Parent createContent(){
        StackPane root = new StackPane();
        root.setPrefSize(Width * TileSize + 960, Height * TileSize + 160);
        root.getChildren().addAll(tileGroup);

        for(int i=0; i < Height; i++){
            for(int j=0; j < Width; j++){
                Tile tile = new Tile(TileSize);
                tile.setTranslateX(j * TileSize + 480);
                tile.setTranslateY(i * TileSize + 80);
                tileGroup.getChildren().add(tile);
            }
        }

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Snake and Ladder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
