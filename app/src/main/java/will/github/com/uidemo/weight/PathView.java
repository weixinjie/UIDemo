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
 * Created by will on 2017/11/21.
 * 绘制一个path
 */

public class PathView extends View {
    Paint paint = new Paint();
    Path path
            = new Path();

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLACK); //设置画笔颜色
        paint.setStyle(Paint.Style.FILL); //将绘制模式改成划线/填充
        paint.setStrokeWidth(2); //设置划线的宽度（px）
        paint.setAntiAlias(true); //设置抗锯齿

        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }
}
