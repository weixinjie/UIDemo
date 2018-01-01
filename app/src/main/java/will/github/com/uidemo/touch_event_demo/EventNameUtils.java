package will.github.com.uidemo.touch_event_demo;

import android.view.MotionEvent;

/**
 * Created by will on 2017/12/28.
 */

public class EventNameUtils {

    public static String getActionName(MotionEvent motionEvent) {
        String name = "";
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                name = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                name = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                name = "ACTION_UP";
                break;
            default:
                break;
        }
        return name;
    }

}
