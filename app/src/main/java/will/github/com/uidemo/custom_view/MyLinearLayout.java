package will.github.com.uidemo.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.Random;

/**
 * Created by will on 2017/11/25.
 */

public class MyLinearLayout extends LinearLayout {
    Paint paint;
    Random random;

    public MyLinearLayout(Context context
            , @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(60);

        random = new Random();
    }

    //绘制子view的时候会调用这个方法
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        canvas.drawPoint(initPoint(paint)[0], initPoint(paint)[1], paint);
        canvas.drawPoint(initPoint(paint)[0], initPoint(paint)[1], paint);
        canvas.drawPoint(initPoint(paint)[0], initPoint(paint)[1], paint);
        canvas.drawPoint(initPoint(paint)[0], initPoint(paint)[1], paint);
    }

    private float[] initPoint(Paint paint) {
        float[] points = new float[]{random.nextInt(600), random.nextInt(600)};
        paint.setStrokeWidth(20 + random.nextInt(50));
        return points;
    }


}
