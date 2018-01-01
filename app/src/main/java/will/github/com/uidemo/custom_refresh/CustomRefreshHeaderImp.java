package will.github.com.uidemo.custom_refresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import will.github.com.uidemo.R;

/**
 * Created by will on 2017/12/26.
 */

public class CustomRefreshHeaderImp extends FrameLayout implements IRefreshHeader {
    LayoutInflater layoutInflater;
    View headerView;
    TextView tv_head;

    public CustomRefreshHeaderImp(Context context) {
        this(context, null);
    }

    public CustomRefreshHeaderImp(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        layoutInflater = LayoutInflater.from(context);
        headerView = layoutInflater.inflate(R.layout.header_view, this, false);
        addView(headerView, 0);
        tv_head = headerView.findViewById(R.id.tv_head);
    }

    @Override
    public void OnRefreshing() {
        tv_head.setText("刷新中");
    }

    @Override
    public void ReleaseToRefresh() {
        tv_head.setText("松手以刷新");
    }

    @Override
    public void PullToRefresh() {
        tv_head.setText("下拉以刷新");
    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }

    @Override
    public void StateNormal() {
        tv_head.setText("刷新完毕");
    }

}
