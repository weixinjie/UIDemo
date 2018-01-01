package will.github.com.uidemo.touch_event_demo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by will on 2017/12/28.
 */

public class MLinearLayout extends LinearLayout {
    public MLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("--------", "MLinearLayout dispatchTouchEvent " + "  " + EventNameUtils.getActionName(ev));
        boolean dispatchTouchEvent = super.dispatchTouchEvent(ev);
        return dispatchTouchEvent;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("--------", "MLinearLayout onInterceptTouchEvent " + "  " + EventNameUtils.getActionName(ev));
        boolean dispatchTouchEvent = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dispatchTouchEvent = false;
                break;
            case MotionEvent.ACTION_MOVE:
                dispatchTouchEvent = false;
                break;
            case MotionEvent.ACTION_UP:
                dispatchTouchEvent = false;
                break;
        }
        return dispatchTouchEvent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("--------", "MLinearLayout onTouchEvent " + "  " + EventNameUtils.getActionName(ev));
        boolean dispatchTouchEvent = super.onTouchEvent(ev);
        return true;
    }
}
