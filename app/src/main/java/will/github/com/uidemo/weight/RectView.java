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
 * 绘制一个长方形
 */

public class RectView extends View {
    Paint paint = new Paint();

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLACK); //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE); //将绘制模式改成划线
        paint.setStrokeWidth(2); //设置划线的宽度（px）
        paint.setAntiAlias(true); //设置抗锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(100, 100, 200, 200, paint);
    }
}
