package game;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Notice {
    public Notice(String title, String text){
        Notifications notice = Notifications.create().title(title).text(text).graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
        notice.show();
    }
}
