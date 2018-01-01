package will.github.com.uidemo.custom_refresh;

/**
 * Created by will on 2017/12/26.
 */

public interface IRefreshFooter {
    void OnRefreshing();

    void ReleaseToRefresh();

    void PullToRefresh();

    int getHeaderHeight();

    void StateNormal();
}
