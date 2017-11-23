package will.github.com.uidemo.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by will on 2017/11/22.
 * 颜色着色
 */

public class ShaderPaint extends View {
    Paint paint;
    Shader shader;

    public ShaderPaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);

    }

    /**
     * LinearGradient线性渐变
     * x0 y0 x1 y1：渐变的两个端点的位置color0 color1 是端点的颜色tile：
     * 端点范围之外的着色规则，类型是 TileMode。TileMode 一共有 3 个值可选：
     * CLAMP, MIRROR 和 REPEAT。CLAMP （夹子模式？？？算了这个词我不会翻）会在端点之外延续端点处的颜色；MIRROR 是镜像模式；REPEAT 是重复模式
     */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shader = new LinearGradient(10, 30, 50, 20, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRect(10, 80, 80, 160, paint);

        shader = new LinearGradient(10, 30, 50, 20, Color.RED, Color.GREEN, Shader.TileMode.REPEAT);
        paint.setShader(shader);
        canvas.drawRect(10, 170, 80, 300, paint);

        shader = new LinearGradient(10, 30, 50, 20, Color.RED, Color.GREEN, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        canvas.drawRect(10, 310, 80, 450, paint);

        shader = new RadialGradient(500, 500, 50, Color.RED, Color.GREEN, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        canvas.drawCircle(500, 500, 100, paint);

        //扫描渐变
        Shader shader = new SweepGradient(600, 600, Color.RED,
                Color.GREEN);
        paint.setShader(shader);
        canvas.drawCircle(600, 600, 200, paint);

    }
}
