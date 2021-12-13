module game.projectoop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens game to javafx.fxml;
    exports game;
}