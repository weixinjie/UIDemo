package will.github.com.uidemo.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by will on 2017/11/21.
 * 绘制一个点
 */

public class PointView extends View {
    Paint paint = new Paint();

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLACK); //设置画笔颜色
        paint.setStrokeCap(Paint.Cap.ROUND); //设置点的形状
        paint.setStrokeWidth(20); //设置划线的宽度（px）
        paint.setAntiAlias(true); //设置抗锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPoint(100, 100, paint);
    }
}
