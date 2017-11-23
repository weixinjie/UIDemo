package will.github.com.uidemo.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by will on 2017/11/22.
 */

public class TextView extends View {
    Paint paint;
    String test = "this is weixinjie";

    public TextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(20);
        canvas.drawText(test, 10, 10, paint);
        paint.setTextSize(30);
        canvas.drawText(test, 10, 30, paint);
        paint.setTextSize(40);
        canvas.drawText(test, 10, 50, paint);
    }
}
