package will.github.com.uidemo.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import will.github.com.uidemo.R;

/**
 * Created by will on 2017/12/1.
 */

public class WaterView extends View {
    /**
     * 上下文对象
     */
    Context mContext;

    /**
     * 画笔
     */
    Paint mPaint;

    /**
     * 圆环的半径
     */
    private float circleRadius = 300;

    /**
     * 内环的宽度
     */
    private int circlePaintWidth = 30;

    /**
     * 保存绘制的波纹
     */
    private ArrayList<Ripple> ripples;

    /**
     * 背景图片
     */
    private Bitmap mBackgroundBitmap = null;


    public WaterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        this.mContext = context;
        mBackgroundBitmap = BitmapFactory.decodeResource(this.mContext.getResources(), R.mipmap.bg_step_law);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawCircle(canvas);
        drawWater(canvas);
        invalidate();

    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        RectF rectF = new RectF();
        rectF.left = 0;
        rectF.right = canvas.getWidth();
        rectF.top = 0;
        rectF.bottom = canvas.getHeight();
        canvas.drawBitmap(mBackgroundBitmap, null, rectF, null);
    }

    public void drawCircle(Canvas canvas) {
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.white1));
        mPaint.setStrokeWidth(circlePaintWidth);
        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight() / 2;
        canvas.drawCircle(x, y, circleRadius, mPaint);
        canvas.restore();
    }

    /**
     * 绘制同心圆
     *
     * @param canvas
     */
    public void drawWater(Canvas canvas) {
        canvas.save();
        if (ripples == null) {
            ripples = new ArrayList<>();
            Ripple circle = new Ripple(circleRadius, 255);
            ripples.add(circle);
        }

        if (ripples.get(ripples.size() - 1).radius % 200 == 0) {
            Ripple circle = new Ripple(circleRadius, 255);
            ripples.add(circle);
        }

        for (int i = 0; i < ripples.size(); i++) {
            Ripple circle = ripples.get(i);
            int alpha = circle.alpha;
            float radius = circle.radius;
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.white1));
            mPaint.setStrokeWidth(5);
            mPaint.setAlpha(circle.alpha);
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, circle.radius, mPaint); //绘制一个圆

            circle.alpha = alpha - 1;
            circle.radius = radius + 1;

        }


        if (ripples.get(0).getAlpha() <= 0) {
            ripples.remove(0);
        }


        canvas.restore();

    }

    /**
     * 绘制的水波纹类
     */
    public class Ripple {
        /**
         * 水波纹的半径
         */
        private float radius = 0;
        /**
         * 水波纹的透明度
         */
        private int alpha = 255;

        public Ripple(float radius, int alpha) {
            this.radius = radius;
            this.alpha = alpha;
        }

        public float getRadius() {
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }
    }
}
