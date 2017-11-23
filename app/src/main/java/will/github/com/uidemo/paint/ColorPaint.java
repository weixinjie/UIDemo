package will.github.com.uidemo.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by will on 2017/11/22.
 * 颜色着色
 */

public class ColorPaint extends View {
    Paint paint;

    public ColorPaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(10, 80, 80, 160, paint);
        paint.setColor(Color.parseColor("#FF9800"));
        canvas.drawLine(300, 30, 450, 180, paint);

        paint.setColor(Color.parseColor("#E91E63"));
        paint.setTextSize(40);
        canvas.drawText("weixinjie", 500, 130, paint);

    }
}
