package will.github.com.uidemo.custom_refresh;

/**
 * Created by will on 2017/12/26.
 */

public interface IRefreshHeader {
    void OnRefreshing();

    void ReleaseToRefresh();

    void PullToRefresh();

    int getHeaderHeight();

    void StateNormal();
}
