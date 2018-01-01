package will.github.com.uidemo.touch_event_demo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by will on 2017/12/28.
 */

public class MTextView extends android.support.v7.widget.AppCompatTextView {
    public MTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("--------", "MTextView dispatchTouchEvent " + "  " + EventNameUtils.getActionName(ev));

        boolean dispatchTouchEvent = super.dispatchTouchEvent(ev);
        return dispatchTouchEvent;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("--------", "MTextView onTouchEvent " + "  " + EventNameUtils.getActionName(ev));
        boolean dispatchTouchEvent = super.onTouchEvent(ev);
        return true;
    }

}
