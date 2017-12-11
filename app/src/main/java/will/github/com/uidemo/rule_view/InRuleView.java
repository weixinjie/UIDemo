package will.github.com.uidemo.rule_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

/**
 * Created by will on 2017/12/10.
 */

public class InRuleView extends View {
    Paint linePaint;
    Paint textPaint;
    //刻度间隔
    int interval = 25;
    int max = 1000;
    int min = 0;
    /**
     * 一个大刻度下面有多少小刻度
     */
    int contain = 10;
    private Context mContext;
    private OverScroller mScroller;

    private VelocityTracker mVelocityTracker;


    private float mLastX;
    private float mLastY;

    private int mMinimumVelocity;
    private int mMaximumVelocity;

    public InRuleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
        initScroller();
        initTracker();
    }

    /**
     * 初始化速度检测器
     */
    private void initTracker() {
        mVelocityTracker = VelocityTracker.obtain();
    }

    /**
     * 初始化画笔工具类
     */
    private void initPaint() {
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(3);
        linePaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setStrokeWidth(3);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setTextSize(20);
    }


    private void initScroller() {
        mScroller = new OverScroller(getContext());
        setFocusable(true);
        setWillNotDraw(false);
        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
//        mTouchSlop = configuration.getScaledTouchSlop();
//        mOverscrollDistance = configuration.getScaledOverscrollDistance();
//        mOverflingDistance = configuration.getScaledOverflingDistance();
//        mScrollFactor = configuration.getScaledScrollFactor();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        for (int i = 1; i <= max; i++) {
            if (i % contain == 0) {
                linePaint.setColor(Color.RED);
                canvas.drawLine(i * interval, 100, i * interval, 200, linePaint);
                String text = String.valueOf(i);
                Rect rect = new Rect();
                textPaint.getTextBounds(text, 0, text.length(), rect);
                canvas.drawText(String.valueOf(i), i * interval - rect.width() / 2, 200 + 30, textPaint);
            } else {
                linePaint.setColor(Color.BLACK);
                canvas.drawLine(i * interval, 100, i * interval, 150, linePaint);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //初始化速度检测
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        float currentX = event.getX();
        float currentY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScroller.abortAnimation();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) (mLastX - currentX);
                scrollBy(moveX, 0);
                break;
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int initialVelocity = (int) velocityTracker.getXVelocity();

                if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
                    mScroller.fling(getScrollX(), 0, -initialVelocity, 0, -5, 10000000, 0, 0);
                    invalidate();
                }

                mVelocityTracker.recycle();
                mVelocityTracker = null;
                break;
        }

        mLastX = currentX;
        mLastY = currentY;

        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
