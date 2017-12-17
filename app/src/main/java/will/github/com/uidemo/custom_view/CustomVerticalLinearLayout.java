package will.github.com.uidemo.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by will on 2017/12/13.
 * 自定义垂直方向的LinearLayout
 */

public class CustomVerticalLinearLayout extends ViewGroup {

    public CustomVerticalLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //触发测量子View
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST
                && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getMaxWidth(), getTotalHeight());
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getMaxWidth(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, getTotalHeight());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childViewCount = getChildCount();
        int currentTotalHeight = getPaddingTop();
        for (int i = 0; i < childViewCount; i++) {
            View childView = getChildAt(i);
            childView.layout(getPaddingLeft(), currentTotalHeight, childView.getMeasuredWidth(), currentTotalHeight + childView.getMeasuredHeight());
            currentTotalHeight += childView.getMeasuredHeight();
        }
    }

    /**
     * 获取整体高度
     *
     * @return
     */
    private int getTotalHeight() {
        int childeViewCount = getChildCount();
        int totalHeight = 0;
        for (int i = 0; i < childeViewCount; i++) {
            View childView = getChildAt(i);
            totalHeight += childView.getMeasuredHeight();
        }
        return totalHeight;
    }

    /**
     * 获取最宽的宽度
     *
     * @return
     */
    private int getMaxWidth() {
        int childViewCount = getChildCount();
        int maxWidth = 0;

        for (int i = 0; i < childViewCount; i++) {
            View childView = getChildAt(i);
            if (maxWidth < childView.getMeasuredWidth()) {
                maxWidth = childView.getMeasuredWidth();
            }
        }
        return maxWidth;
    }


}
