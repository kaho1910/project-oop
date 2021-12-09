module com.example.snakedemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.snakedemo to javafx.fxml;
    exports com.example.snakedemo;
}