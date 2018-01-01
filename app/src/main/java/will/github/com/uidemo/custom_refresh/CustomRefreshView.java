package will.github.com.uidemo.custom_refresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by will on 2017/12/17.
 */

public class CustomRefreshView extends ViewGroup {
    /**
     * 滑动工具类
     */
    Scroller scroller;
    float currentX;
    float currentY;
    float mLastX;
    float mLastY;
    private CustomRefreshHeaderImp headerView = null;
    private CustomRefreshFooterImp footerView = null;
    private float touchX;
    private float touchY;
    /**
     * 阻尼系数,滑动距离/实际滑动距离
     */
    private float mDragRate = 0.5f;
    /**
     * 系统所能识别的最小滑动距离
     */
    private int touchSlop;
    private View mRefreshContent = null;
    private OnRefreshListener onRefreshListener;

    public CustomRefreshView(Context context) {
        this(context, null);
    }

    public CustomRefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        headerView = new CustomRefreshHeaderImp(context);
        footerView = new CustomRefreshFooterImp(context);
        addView(headerView);
        addView(footerView);

        scroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        touchSlop = viewConfiguration.getScaledTouchSlop();


    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView instanceof IRefreshHeader) {
                childView.layout(0, -headerView.getMeasuredHeight(), headerView.getMeasuredWidth(), 0);
            } else if (childView instanceof IRefreshFooter) {
                childView.layout(0, getMeasuredHeight(), footerView.getMeasuredWidth(), getMeasuredHeight() + footerView.getMeasuredHeight());
            } else {
                mRefreshContent = childView;
                childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        currentX = event.getRawX();
        currentY = event.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                touchX = currentX;
                touchY = currentY;

                mLastX = currentX;
                mLastY = currentY;
                super.dispatchTouchEvent(event);
                return true;
            case MotionEvent.ACTION_MOVE:

                int degreeX = (int) (mLastX - currentX);
                int degreeY = (int) (mLastY - currentY);

                // degreeY>0上滑
                //-1 无法下拉false  1无法上拉false
                if (mRefreshContent.canScrollVertically(1) && degreeY > 0
                        || mRefreshContent.canScrollVertically(-1) && degreeY < 0) {
                    return super.dispatchTouchEvent(event);
                } else {
                    sendCancelEvent(event);
                    scrollBy(0, degreeY);
                    if (Math.abs(getScrollY()) < headerView.getMeasuredHeight()) {
                        headerView.PullToRefresh();
                    } else {
                        headerView.ReleaseToRefresh();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (Math.abs(getScrollY()) < headerView.getMeasuredHeight()) {
                    startScroll(-getScrollY());
                } else {
                    headerView.OnRefreshing();
                    //上拉加载
                    if (getScrollY() > 0) {
                        startScroll(footerView.getMeasuredHeight() - Math.abs(getScrollY()));
                        if (onRefreshListener != null) {
                            onRefreshListener.onLoadMore();
                        }
                    } else {
                        startScroll(Math.abs(getScrollY()) - headerView.getMeasuredHeight());
                        if (onRefreshListener != null) {
                            onRefreshListener.onRefresh();
                        }
                    }

                }
                break;
        }

        mLastX = currentX;
        mLastY = currentY;
        ViewCompat.postInvalidateOnAnimation(this);
        return super.dispatchTouchEvent(event);
    }

    /**
     * 结束刷新
     */
    public void finishComplete() {
        startScroll(-getScrollY());
        headerView.StateNormal();
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 需要重新发送CANCEL时间打断本次序列
     */
    private void sendCancelEvent(MotionEvent last) {
        MotionEvent e = MotionEvent.obtain(
                last.getDownTime(),
                last.getEventTime()
                        + ViewConfiguration.getLongPressTimeout(),
                MotionEvent.ACTION_CANCEL, last.getX(), last.getY(),
                last.getMetaState());
        super.dispatchTouchEvent(e);
    }


    private void startScroll(int deY) {
        int scrollY = getScrollY();
        this.startScroll(scrollY, deY);
    }

    private void startScroll(int startY, int deY) {
        scroller.startScroll(0, startY, 0, deY);
    }

    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();
    }

}
