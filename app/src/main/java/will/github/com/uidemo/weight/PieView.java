package will.github.com.uidemo.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by will on 2017/11/22.
 * 绘制一个饼图
 */

public class PieView extends View {
    Paint paint = new Paint();
    Path path = new Path();

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true); //设置抗锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(100, 100, 400, 400, 180, 120, true, paint);
    }
}
