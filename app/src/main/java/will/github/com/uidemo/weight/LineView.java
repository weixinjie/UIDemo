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
 * 绘制一个椭圆
 */

public class LineView extends View {
    Paint paint = new Paint();

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLACK); //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE); //将绘制模式改成划线
        paint.setStrokeWidth(2); //设置划线的宽度（px）
        paint.setAntiAlias(true); //设置抗锯齿
        requestFocus();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(50, 50, 350, 200, paint);
    }
}
