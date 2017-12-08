package will.github.com.uidemo.ripple_view;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by will on 2017/12/6.
 */

public class Utils {
    /**
     * dp转px
     *
     * @param context
     * @param dpVal   dp value
     * @return px value
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return width of the screen.
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏高度
     *
     * @param context
     * @return width of the screen.
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * sp转化成px
     *
     * @param sp
     * @param context
     * @return
     */
    public static int sp2px(float sp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return px;
    }

    /**
     * 将毫秒时间转换成时分秒
     *
     * @param totalTime
     * @return
     */
    public static String formatTime(int totalTime) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = totalTime / 1000;
        if (totalTime <= 1000 && totalTime > 0) {
            second = 1;
        }
        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        // 转换时分秒 00:00:00
        String duration = (minute >= 10 ? minute : "0" + minute) + ":" + (second >= 10 ? second : "0" + second);

        return duration;
    }

}
