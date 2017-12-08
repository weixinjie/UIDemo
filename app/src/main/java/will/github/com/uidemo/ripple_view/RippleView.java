package will.github.com.uidemo.ripple_view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

import will.github.com.uidemo.R;

/**
 * Created by will on 2017/12/1.
 */

public class RippleView extends View {
    /**
     * 上下文对象
     */
    Context mContext;

    /**
     * 画笔
     */
    Paint mPaint;

    /**
     * 文字画笔
     */
    Paint mTextPaint;

    //------------------圆环的属性定义-------------------
    /**
     * 圆心X轴坐标
     */
    private int mCenterX;
    /**
     * 圆心Y轴坐标
     */
    private int mCenterY;
    /**
     * 内环的半径
     */
    private float circleRadius;

    /**
     * 内环的宽度
     */
    private int circlePaintWidth;

    //------------------动画的属性定义-------------------
    /**
     * 属性动画类
     */
    private ObjectAnimator mObjectAnimator;

    /**
     * 动画进度
     */
    private float mProgress;

    /**
     * 动画的持续事件
     */
    private float mDuration;

    //------------------水波纹的属性定义-------------------
    /**
     * 需要绘制的波纹
     */
    private ArrayList<RippleBean> ripples;

    /**
     * 水波纹的宽度
     */
    private int ripplePaintWidth;
    /**
     * 水波纹的扩散的最大半径
     */
    private float mRippleMaxRadius;
    /**
     * 水波纹扩散的最小半径
     */
    private float mRippleMinRadius;
    /**
     * 水波纹初始透明度
     */
    private float mRippleMaxAlpha;


    //------------------字体的属性定义-------------------
    /**
     * 文本字体大小
     */
    private float mTextSize;

    private ArrayList<ObjectAnimator> animators;

    /**
     * 是否正在运行
     */
    private boolean isStart = false;

    private OnCountCompleteListener onCountCompleteListener;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView);
        mCenterX = typedArray.getInteger(R.styleable.RippleView_centerX, Utils.getScreenWidth(context) / 2);
        mCenterY = typedArray.getInteger(R.styleable.RippleView_centerY, Utils.getScreenHeight(context) / 2);
        circleRadius = typedArray.getFloat(R.styleable.RippleView_circleRadius, Utils.dp2px(context, 200));
        circlePaintWidth = typedArray.getInt(R.styleable.RippleView_circleWidth, Utils.dp2px(context, 10));
        mDuration = typedArray.getFloat(R.styleable.RippleView_progressDuration, 1000 * 60);
        ripplePaintWidth = typedArray.getInt(R.styleable.RippleView_rippleWidth, Utils.dp2px(context, 2));

        mRippleMaxRadius = typedArray.getFloat(R.styleable.RippleView_rippleMaxRadius, 2 * circleRadius);
        mRippleMinRadius = typedArray.getFloat(R.styleable.RippleView_rippleMaxRadius, circleRadius);
        mRippleMaxAlpha = typedArray.getFloat(R.styleable.RippleView_rippleMaxAlpha, 200);

        mTextSize = typedArray.getFloat(R.styleable.RippleView_textSize, 10);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(ContextCompat.getColor(mContext, R.color.white1));
        mTextPaint.setTextSize(Utils.sp2px(mTextSize, mContext));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        if (isStart) {
            drawRipper(canvas);
        }
        drawSchedule(canvas);
        drawText(canvas);
        invalidate();
    }


    /**
     * 绘制底层圆环
     *
     * @param canvas
     */
    public void drawCircle(Canvas canvas) {
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.white2));
        mPaint.setStrokeWidth(circlePaintWidth);

        canvas.drawCircle(mCenterX, mCenterY, circleRadius, mPaint);
        canvas.restore();
    }

    /**
     * 绘制进度
     */
    public void drawSchedule(Canvas canvas) {
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.white1));
        mPaint.setStrokeWidth(circlePaintWidth);
        RectF rectF = new RectF(mCenterX - circleRadius, mCenterY - circleRadius, mCenterX + circleRadius, mCenterY + circleRadius);
        canvas.drawArc(rectF, 315, 360 * (mProgress / 100), false, mPaint);
        canvas.restore();
    }

    /**
     * 绘制水波纹,并扩散水波纹
     *
     * @param canvas
     */
    public void drawRipper(Canvas canvas) {
        canvas.save();
        if (ripples == null) {
            ripples = new ArrayList<>();
            RippleBean circle = new RippleBean(circleRadius, mRippleMaxAlpha);
            ripples.add(circle);
        }

        for (int i = 0; i < ripples.size(); i++) {
            RippleBean circle = ripples.get(i);
            int alpha = (int) circle.getAlpha();
            float radius = circle.getRadius();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.white1));
            mPaint.setStrokeWidth(ripplePaintWidth);
            mPaint.setAlpha(alpha);
            canvas.drawCircle(mCenterX, mCenterY, circle.getRadius(), mPaint); //绘制一个圆
            circle.setAlpha(alpha - 1);
            circle.setRadius(radius + 1);
        }

        if (ripples.size() <= 0 || ripples.get(0).getRadius() % ((mRippleMaxRadius - mRippleMinRadius) / 3) < 1) {
            RippleBean circle = new RippleBean(circleRadius, mRippleMaxAlpha);
            ripples.add(circle);
        }

        if (ripples.get(0).getAlpha() < 0) {
            ripples.remove(0);
        }

        canvas.restore();
    }

    /**
     * 绘制中间的文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        canvas.save();
        String currentTime = Utils.formatTime((int) (mDuration * (1 - mProgress / 100)));
        Rect rect = new Rect();
        mTextPaint.getTextBounds(String.valueOf(currentTime), 0, String.valueOf(currentTime).length(), rect);
        canvas.drawText(String.valueOf(currentTime), mCenterX - rect.width() / 2, mCenterY + rect.height() / 2, mTextPaint);
        canvas.restore();
    }

    /**
     * 开启动画效果
     */
    public void start() {
        isStart = true;
        if (mObjectAnimator == null) {
            mObjectAnimator = ObjectAnimator.ofFloat(this, "mProgress", 0, 100);
            mObjectAnimator.setDuration((long) mDuration);
            mObjectAnimator.setInterpolator(new LinearInterpolator());
            mObjectAnimator.start();
            mObjectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (onCountCompleteListener != null) {
                        onCountCompleteListener.onComplete();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else {
            mObjectAnimator.resume();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        isStart = false;
        if (mObjectAnimator != null) {
            mObjectAnimator.pause();
        }
    }

    /**
     * 启动了属性动画之后,ObjectAnimator会不断的调用这个方法
     *
     * @param mProgress
     */
    public void setMProgress(float mProgress) {
        this.mProgress = mProgress;
    }

    /**
     * 设置监听器
     *
     * @param onCountCompleteListener
     */
    public void setOnCountCompleteListener(OnCountCompleteListener onCountCompleteListener) {
        this.onCountCompleteListener = onCountCompleteListener;
    }

    public void setmCenterX(int mCenterX) {
        this.mCenterX = mCenterX;
    }

    //------------------all SettingMethod-------------------

    public void setmCenterY(int mCenterY) {
        this.mCenterY = mCenterY;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public void setCirclePaintWidth(int circlePaintWidth) {
        this.circlePaintWidth = circlePaintWidth;
    }

    public void setmDuration(float mDuration) {
        this.mDuration = mDuration;
    }

    public void setRipplePaintWidth(int ripplePaintWidth) {
        this.ripplePaintWidth = ripplePaintWidth;
    }

    public void setmRippleMaxRadius(float mRippleMaxRadius) {
        this.mRippleMaxRadius = mRippleMaxRadius;
    }

    public void setmRippleMinRadius(float mRippleMinRadius) {
        this.mRippleMinRadius = mRippleMinRadius;
    }

    public void setmRippleMaxAlpha(float mRippleMaxAlpha) {
        this.mRippleMaxAlpha = mRippleMaxAlpha;
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    public interface OnCountCompleteListener {
        void onComplete();
    }


}
