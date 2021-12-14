package game;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Notice {
    public Notice(String title, String text){
        Platform.runLater(new Runnable(){
            public void run() {
                Notifications notice = Notifications.create().title(title).text(text).graphic(null).hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
                notice.show();
            }
        });
    }
}
