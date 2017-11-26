package will.github.com.uidemo.animator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import will.github.com.uidemo.utils.ScreenUtils;

/**
 * Created by will on 2017/11/25.
 */

public class SportView extends View {
    private float mProgress = 1;
    private int color = Color.GREEN;
    private Paint paint;

    private int screenWidth;
    private int screenHeight;

    public SportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setStrokeCap(Paint.Cap.ROUND);

        screenWidth = ScreenUtils.getScreenWidth((Activity) context);
        screenHeight = ScreenUtils.getScreenHeight((Activity) context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(120);
        canvas.drawArc(screenWidth / 4, screenWidth / 4, (screenWidth / 4) * 3, (screenWidth / 4) * 3, 0, (mProgress / 100) * 360, false, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setTextSize(60);
        String progressStr = ((int) mProgress) + "%";
        float textWidth = paint.measureText(progressStr);
        Rect rect = new Rect();
        paint.getTextBounds(progressStr, 0, progressStr.length(), rect);
        canvas.drawText(progressStr, screenWidth / 2 - textWidth / 2, screenWidth / 2 + Math.abs((rect.bottom - rect.top)) / 2, paint);
    }

    public float getMProgress() {
        return mProgress;
    }

    public void setMProgress(float mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }
}
