package will.github.com.uidemo.custom_view;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

import java.util.ArrayList;
import java.util.Random;

import will.github.com.uidemo.R;

/**
 * Created by will on 2017/11/26.
 */

public class MiSportView extends View {
    private Context context;
    /**
     * 绘制外层圆圈的PaintF
     */
    private Paint circlePaint;
    /**
     * 外层圆圈的数量
     */
    private int circleCount = 10;

    /**
     * 星星的数量
     */
    private int starCount = 10;

    /**
     * 圆环半径占Canvas的比例
     */
    private float circleRate = 0.72f;

    private boolean isConnect = false;

    private boolean isAnimator = false;
    private int colorEnd = Color.parseColor("#00000000");
    private int colorBegin = Color.parseColor("#FFFFFF");
    private int degress = 0;
    private int blueDegress = 0;
    private Bitmap backgroundBitmap = null;
    private Paint pointPaint;
    private Paint starPaint;

    private float mScaleX;
    private float mScaleY;

    private ArrayList<MCustomLine> mCustomLines;
    private ArrayList<MStar> mStars;

    public MiSportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public float getMScaleX() {
        return mScaleX;
    }

    /**
     * 注意 这里的方法名一定得是大写
     *
     * @param mScaleX
     */
    public void setMScaleX(float mScaleX) {
        this.mScaleX = mScaleX;
    }

    public float getMScaleY() {
        return mScaleY;
    }

    public void setMScaleY(float mScaleY) {
        this.mScaleY = mScaleY;
    }

    private void init(Context context) {
        this.context = context;
        initBaseParameter();
        initCircleParameter();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        if (isConnect) {
            startAnimator();
            drawBigCircle(canvas);
            drawVirtul(canvas);
            drawText(canvas);
        } else {
            drawCircle(canvas);
            drawText(canvas);
        }

        invalidate();
    }

    /**
     * 设置是否已经连接
     *
     * @param connect
     */
    public void setConnect(boolean connect) {
        if (connect) {
            isAnimator = false;
        }
        isConnect = connect;
    }

    /**
     * 初始化基本参数
     */
    private void initBaseParameter() {
        backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg_step_law);
    }

    /**
     * 初始化外层Circle的相关参数
     */
    private void initCircleParameter() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(6);
        circlePaint.setColor(colorBegin);

        pointPaint = new Paint();
        pointPaint.setColor(Color.WHITE); //设置画笔颜色
        pointPaint.setStrokeCap(Paint.Cap.ROUND); //设置点的形状
        pointPaint.setStrokeWidth(20); //设置划线的宽度（px）
        pointPaint.setAntiAlias(true); //设置抗锯齿
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
        canvas.drawBitmap(backgroundBitmap, null, rectF, null);
    }

    /**
     * 绘制外层的圆圈与小圆点
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        canvas.save();
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        //这个地方的颜色渐变需要begin跟end需要更换位置
        SweepGradient sweepGradient = new SweepGradient(canvasWidth / 2, canvasHeight / 2, colorEnd, colorBegin); //绘制颜色渐变
        circlePaint.setShader(sweepGradient);
        Random random = new Random();

        canvas.rotate(degress % 360, canvasWidth / 2, canvasHeight / 2); //一定要先旋转后绘制
        if (mCustomLines == null) {
            mCustomLines = new ArrayList<>();
            for (int i = 0; i < circleCount; i++) {
                MCustomLine mCustomLine = new MCustomLine(canvasWidth / 2, canvasHeight / 2, (int) (canvasWidth * circleRate) / 2);
                mCustomLines.add(mCustomLine);
            }
        }

        for (MCustomLine mCustomLine : mCustomLines) {
            RectF rectF = mCustomLine.getRectF();
            //绘制N个弧形形成大圆环
            canvas.drawArc(rectF, mCustomLine.getStartAngle(), 330, false, circlePaint);
        }

        float pointX = canvasWidth / 2 + (canvasWidth * circleRate) / 2;
        float pointY = canvasHeight / 2;
        starPaint = new Paint();
        starPaint.setAntiAlias(true);
        starPaint.setStrokeCap(Paint.Cap.ROUND);

        //开始绘制star
        if (mStars == null) {
            mStars = new ArrayList<>();
            for (int i = 0; i < starCount; i++) {
                MStar mStar = new MStar(pointX, pointY);
                mStars.add(mStar);
            }
        }
        for (MStar mStar : mStars) {
            starPaint.setStrokeWidth(mStar.getmPaintWidth());
            starPaint.setAlpha((int) mStar.getmAlpha());
            starPaint.setColor(Color.WHITE);
            canvas.drawPoint(mStar.getmPointX(), mStar.getmPointY(), starPaint);
        }
        mStars = null;

//        for (int i = 0; i < starCount; i++) {
//            int strokeWidth = random.nextInt(5);
//            int x = random.nextInt(20) - 10;
//            int y = -random.nextInt(100 + 30);
//            float paintWidth = 50 + y / 2;
//            starPaint.setStrokeWidth(paintWidth);
//            float alpha = 1 - (300 + y) / 300;
//            int alphaMask = ((int) (alpha * 0xff)) << 24;
//            int transparentColor = (Color.WHITE & 0x00ffffff) + alphaMask;
//            starPaint.setColor(transparentColor);
//            canvas.drawPoint(pointX + x, pointY + y, starPaint);
//        }
        pointPaint.setStrokeWidth(MStar.MAX_POINT);
        canvas.drawPoint(pointX, pointY, pointPaint);

        degress += 3;
        canvas.restore();
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        canvas.save(); //每次进行绘制的时候需要先点用save再调用restore方法，这样不会影响后期内容的绘制
        Paint paint = new Paint();
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setTextSize(150);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(20);
        String testStr = "2714";
        Rect rect = new Rect();
        paint.getTextBounds(testStr, 0, testStr.length(), rect);
        int textWidth = rect.width();
        int testHeight = rect.height();
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.drawText(testStr, width / 2 - textWidth / 2, width / 2 + testHeight / 2, paint);

        paint.setTextSize(50);
        paint.setColor(Color.WHITE);
        testStr = "115km | 20''";
        paint.getTextBounds(testStr, 0, testStr.length(), rect);  //测量文字，注意传入的rect测量的是文字实际的宽高，gettextwidth方法是有留白的宽高

        canvas.drawText(testStr, width / 2 - rect.width() / 2, width / 2 + testHeight + rect.height() / 2, paint);
        canvas.restore();

    }

    /**
     * 绘制外层的光晕圆环
     *
     * @param canvas
     */
    private void drawBigCircle(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.save();
        canvas.scale(mScaleX, mScaleY, width / 2, height / 2);
        canvas.rotate(blueDegress % 360, width / 2, height / 2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        float rate = width * circleRate / 2; //半径

        //绘制5个椭圆
        RadialGradient radialGradient = new RadialGradient(width / 2 + rate, height / 2, rate
                , ContextCompat.getColor(context, R.color.white1)
                , ContextCompat.getColor(context, R.color.white2), Shader.TileMode.CLAMP);
        paint.setStrokeWidth(50);
        paint.setShader(radialGradient);

        //绘制一个弧形
        canvas.drawArc((float) width / 2 - rate, (float) height / 2 - rate
                , (float) width / 2 + rate, (float) height / 2 + rate
                , 180, 360, false, paint);

        SweepGradient sweepGradient = new SweepGradient(width / 2, height / 2
                , ContextCompat.getColor(context, R.color.white1), ContextCompat.getColor(context, R.color.white2));
        paint.setStrokeWidth(9);
        //绘制光晕效果
        for (int i = 0; i < 16; i++) {
            paint.setAlpha(0xff * (16 - i) / (16 * 3));
            RectF rectF = new RectF();
            rectF.left = width / 2 - rate;
            rectF.right = width / 2 + rate - 25 + i * 9;  //加入25的原因是下面绘制了一个大的圆环，需要将圆环的宽度的一半加入进去
            rectF.top = height / 2 - rate;
            rectF.bottom = height / 2 + rate;
            canvas.drawArc(rectF, 300, 105, false, paint);
        }
        //绘制一个圆环
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(ContextCompat.getColor(context, R.color.white3));
        paint2.setStrokeWidth(50);
        canvas.drawCircle(width / 2, height / 2, rate, paint2);
        blueDegress += 1;

        canvas.restore();
    }

    private void drawVirtul(Canvas canvas) {
        int smaller = 60; //绘制的虚线的圆圈比大圆圈小多少
        canvas.save();
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        float rate = width * circleRate / 2; //上面圆的半径

        //绘制里面的虚线
        Paint paint_virtul = new Paint();
        paint_virtul.setStyle(Paint.Style.STROKE);
        paint_virtul.setStrokeWidth(8);
        paint_virtul.setColor(ContextCompat.getColor(context, R.color.white1));

        RectF virtul_rect = new RectF();
        virtul_rect.left = width / 2 - rate + smaller;
        virtul_rect.right = width / 2 + rate - smaller;
        virtul_rect.top = height / 2 - rate + smaller;
        virtul_rect.bottom = height / 2 + rate - smaller;
        canvas.drawArc(virtul_rect, 270, 270, false, paint_virtul);

        //第一个参数 intervals 是一个数组，它指定了虚线的格式：数组中元素必须为偶数（最少是 2 个），按照「画线长度、空白长度、画线长度、空白长度」……的顺序排列，例如上面代码中的 20, 5, 10, 5 就表示虚线是按照「画 20 像素、空 5 像素、画 10 像素、空 5 像素」的模式来绘制；第二个参数 phase 是虚线的偏移量。
        //绘制虚线
        PathEffect pathEffect = new DashPathEffect(new float[]{5.0f, 5.0f}, 0);
        paint_virtul.setPathEffect(pathEffect);
        canvas.drawArc(virtul_rect, 180, 90, false, paint_virtul);

        paint_virtul.setStrokeCap(Paint.Cap.ROUND);
        paint_virtul.setStrokeWidth(20);
        canvas.drawPoint(virtul_rect.left, height / 2, paint_virtul);
        canvas.restore();
    }

    /**
     * 开启动画效果
     */
    private void startAnimator() {
        if (isAnimator) {
            return;
        }
        isAnimator = true;
        Keyframe keyframe = Keyframe.ofFloat(0, 0.7f);
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 1.3f); //进度为一半的时候绘制完成动画
        Keyframe keyframe3 = Keyframe.ofFloat(1.0f, 1.0f); //进度为100的时候回退到70的动画
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("mScaleX", keyframe, keyframe2, keyframe3);
        PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofKeyframe("mScaleY", keyframe, keyframe2, keyframe3);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(this, propertyValuesHolder, propertyValuesHolder2);
        objectAnimator2.setInterpolator(new AnticipateOvershootInterpolator()); //回弹插值器
        objectAnimator2.setDuration(1000);
        objectAnimator2.start();
    }


    /**
     * 第一个页面的弧形
     */

    public class MCustomLine {
        private int centerYOffset = 0;
        /**
         * 随机数生成算法
         */
        private Random random = null;

        private RectF rectF = null;

        private float startAngle = 0;

        /**
         * @param centerX 圆心X坐标
         * @param centerY 圆心Y坐标
         * @param rate    半径
         */
        public MCustomLine(int centerX, int centerY, int rate) {
            random = new Random();

            rate += random.nextInt(60) - 30; //利用随机数算法把弧形打散
            rectF = new RectF();
            rectF.left = centerX - rate;
            rectF.right = centerX + rate;
            rectF.top = centerY - rate;
            rectF.bottom = centerY + rate;
            startAngle = random.nextInt(30);
        }

        public RectF getRectF() {
            return rectF;
        }

        public float getStartAngle() {
            return startAngle;
        }
    }

    /**
     * 定义的外层星状尾焰
     */
    public class MStar {
        public static final float MAX_POINT = 30; //最大点的大小为20
        public static final float MIN_POINT = 5; //最小点的大小为5
        /**
         * Y轴的最大间距为50
         */
        public static final float MAX_OFFSET_Y = 300;
        /**
         * X offset最大
         */
        public static final float MAX_OFFET_X = 150;

        /**
         * 最大透明度
         */
        public static final float MAX_ALPHA = 255;

        /**
         * 最小透明度
         */
        public static final float MIN_ALPHA = 10;
        Random random;
        private float mPointX;
        private float mPointY;
        private float mPaintWidth;
        private double mAlpha;

        /**
         * 1.Y距离目标点越远 半径越小;X的偏移量越大
         *
         * @param pointX 核心点X轴坐标
         * @param pointY 核心点Y轴坐标
         */
        public MStar(float pointX, float pointY) {
            random = new Random();
            int offsetY = random.nextInt((int) (MAX_OFFSET_Y + 1));
            mPointY = pointY - offsetY;
            //X的尾焰应该在核心点的两边
            int currentMaxX = (int) (MAX_OFFET_X / MAX_OFFSET_Y * offsetY + 1);
            mPointX = pointX + random.nextInt(currentMaxX * 2) - currentMaxX;

            mPaintWidth = MAX_POINT;
            mAlpha = MIN_ALPHA + ((MAX_ALPHA - MIN_ALPHA) / MAX_OFFSET_Y) * offsetY;
        }

        public float getmPointX() {
            return mPointX;
        }

        public void setmPointX(float mPointX) {
            this.mPointX = mPointX;
        }

        public float getmPointY() {
            return mPointY;
        }

        public void setmPointY(float mPointY) {
            this.mPointY = mPointY;
        }

        public float getmPaintWidth() {
            return mPaintWidth;
        }

        public void setmPaintWidth(float mPaintWidth) {
            this.mPaintWidth = mPaintWidth;
        }

        public double getmAlpha() {
            return mAlpha;
        }

        public void setmAlpha(double mAlpha) {
            this.mAlpha = mAlpha;
        }
    }

}
