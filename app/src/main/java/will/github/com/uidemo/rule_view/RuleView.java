package will.github.com.uidemo.rule_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import will.github.com.uidemo.R;

/**
 * Created by will on 2017/12/10.
 * 外部尺子类
 */

public class RuleView extends FrameLayout {
    Drawable mCursorDrawable;
    InRuleView inRuleView;
    private int cursorWidth = 20;
    private int cursorHeight = 300;

    public RuleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDrawable();
        initRuleView(context);
    }

    private void initRuleView(Context context) {
        inRuleView = new InRuleView(context, null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        inRuleView.setLayoutParams(layoutParams);
        addView(inRuleView);
    }


    /**
     * 具体来讲，这里说的「绘制子 View」是通过另一个绘制方法的调用来发生的，这个绘制方法叫做：dispatchDraw()。
     * 也就是说，在绘制过程中，每个 View 和 ViewGroup 都会先调用 onDraw() 方法来绘制主体，再调用 dispatchDraw() 方法来绘制子 View。
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawCursor(canvas);
    }

    /**
     * 绘制光标
     */
    private void initDrawable() {
        mCursorDrawable = getResources().getDrawable(R.drawable.cursor_shape);

    }

    /**
     * 绘制标杆
     *
     * @param canvas
     */
    private void drawCursor(Canvas canvas) {
        mCursorDrawable.setBounds(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth() / 2 + cursorWidth
                , canvas.getHeight() / 2 + cursorHeight);
        mCursorDrawable.draw(canvas);
    }
}
