package will.github.com.uidemo.ripple_view;

/**
 * Created by will on 2017/12/6.
 * 水波纹的数据存储bean
 */

public class RippleBean {
    /**
     * 水波纹的半径
     */
    private float radius = 0;
    /**
     * 水波纹的透明度
     */
    private float alpha = 255;

    public RippleBean(float radius, float alpha) {
        this.radius = radius;
        this.alpha = alpha;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
