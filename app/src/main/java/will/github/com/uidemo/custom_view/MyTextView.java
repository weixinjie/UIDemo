package will.github.com.uidemo.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by will on 2017/11/25.
 */

public class MyTextView extends AppCompatTextView {
    Paint paint;

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 10, 400, 30, paint);
        super.onDraw(canvas);

    }
}
