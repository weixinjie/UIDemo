package will.github.com.uidemo.drawtext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.logging.Logger;

/**
 * Created by will on 2017/11/23.
 */

public class DrawText extends View {
    String testStr = "hello this is weixinjie hello this is weixinjie hello this is weixinjie hello this is weixinjie";
    Paint paint;
    Path path;
    StaticLayout staticLayout;
    TextPaint textPaint;

    public DrawText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        path = new Path();
        textPaint = new TextPaint(paint);
        initStaticLayout();
    }

    /**
     * width 是文字区域的宽度，文字到达这个宽度后就会自动换行；
     * align 是文字的对齐方向；
     * spacingmult 是行间距的倍数，通常情况下填 1 就好；
     * spacingadd 是行间距的额外增加值，通常情况下填 0 就好；
     * includeadd 是指是否在文字上下添加额外的空间，来避免某些过高的字符的绘制出现越界。
     * <p>
     */
    private void initStaticLayout() {
        textPaint.setTextSize(100);
        staticLayout = new StaticLayout(testStr, textPaint, 600,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(150);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setColor(Color.RED);
        paint.setStrikeThruText(true); //删除线
        paint.setFakeBoldText(true); //粗体
        paint.setUnderlineText(true); //下划线
        paint.setTextSkewX(-0.5f); //文字倾斜角度
        paint.setTextScaleX(0.8f); //文字横线缩放
        paint.setLetterSpacing(0.2f); //字体间距
        paint.setTextAlign(Paint.Align.CENTER); //文字对齐方式
        paint.setSubpixelText(true); //开启像素级别的抗锯齿

        canvas.drawText(testStr, 50, 100, paint); //以左下角为基准点
        canvas.drawText(testStr, 50, 100 + paint.getFontSpacing(), paint); //获取推荐的行距。
        canvas.drawText(testStr, 50, 100 + 2 * paint.getFontSpacing(), paint); //以左下角为基准点

        //开始测量
        Rect bound = new Rect();
        paint.getTextBounds(testStr, 0, testStr.length(), bound);
        //left16  right 6882 top -117 bottom 32 因为文字的坐标原点在左下角
        Log.e("--------", "left" + bound.left + "  right " + bound.right + " top " + bound.top + " bottom " + bound.bottom);
        //测量文字的宽度并返回
        float width = paint.measureText(testStr);
        Log.e("-------", String.valueOf(width));
        //分别测量每隔字符的宽度并写入widths
        float[] widths = new float[testStr.length()];
        paint.getTextWidths(testStr, widths);
        float sum = 0;
        for (int i = 0; i < widths.length; i++) {
            sum += widths[i];
        }

        //paint.breakText 这个方法也是用来测量文字宽度的。但和 measureText() 的区别是， breakText() 是在给出宽度上限的前提下测量文字的宽度。如果文字的宽度超出了上限，那么在临近超限的位置截断文字。

        paint.setColor(Color.BLACK);
        path.moveTo(50, 100);
        path.lineTo(100, 200);
        path.rLineTo(200, 200);
        canvas.drawTextOnPath(testStr, path, 10, 10, paint);

        canvas.save();
        canvas.translate(400, 400);
        staticLayout.draw(canvas);
        canvas.restore();

        //光标相关
        //todo
    }
}
