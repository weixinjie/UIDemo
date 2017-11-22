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
 * 绘制一个扇形或弧形
 * drawArc() 是使用一个椭圆来描述弧形的。
 * left, top, right, bottom 描述的是这个弧形所在的椭圆；
 * startAngle 是弧形的起始角度（x 轴的正向，即正右的方向，是 0 度的位置；顺时针为正角度，逆时针为负角度），
 * sweepAngle 是弧形划过的角度；useCenter 表示是否连接到圆心，如果不连接到圆心，就是弧形，如果连接到圆心，就是扇形。
 */

public class ArcView extends View {
    Paint paint = new Paint();

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLACK); //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE); //将绘制模式改成填充/线
        paint.setStrokeWidth(2); //设置划线的宽度（px）
        paint.setAntiAlias(true); //设置抗锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(50, 50, 350, 200, 0, 200, false, paint);
    }
}
