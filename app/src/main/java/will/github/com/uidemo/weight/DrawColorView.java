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
 * 绘制一背景色
 */

public class DrawColorView extends View {

    public DrawColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRGB(100, 200, 100);
        canvas.drawARGB(100, 100, 200, 100);
//        canvas.drawColor(Color.RED);

    }
}
