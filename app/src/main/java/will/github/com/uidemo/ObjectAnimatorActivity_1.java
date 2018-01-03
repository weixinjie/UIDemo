package will.github.com.uidemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import will.github.com.uidemo.animator.SportView;

public class ObjectAnimatorActivity_1 extends AppCompatActivity {
    SportView sport;
    Button bt_start;
    Button bt_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        sport = findViewById(R.id.sport);
        bt_start = findViewById(R.id.bt_start);
        bt_stop = findViewById(R.id.bt_stop);
        sport.setLayerType(View.LAYER_TYPE_HARDWARE, null); //打开硬件加速
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(sport, "mProgress", 0, 80);
        objectAnimator.setInterpolator(new AnticipateOvershootInterpolator()); //回弹插值器
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); //默认插值器,先加速再减速
        objectAnimator.setInterpolator(new LinearInterpolator()); //匀速插值器
//        objectAnimator.setInterpolator(new AccelerateInterpolator()); //加速插值器
//        objectAnimator.setInterpolator(new DecelerateInterpolator()); //减速插值器
//        objectAnimator.setInterpolator(new AnticipateInterpolator()); //蓄力插值器
//        objectAnimator.setInterpolator(new AnticipateOvershootInterpolator()); //蓄力 到终点后回弹一下的插值器
//        objectAnimator.setInterpolator(new BounceInterpolator()); //到终点附近跳跃的插值器

//        Path interpolatorPath = new Path();  //自定义动画
//// 先以「动画完成度 : 时间完成度 = 1 : 1」的速度匀速运行 25%
//        interpolatorPath.lineTo(0.25f, 0.25f);
//// 然后瞬间跳跃到 150% 的动画完成度
//        interpolatorPath.moveTo(0.25f, 1.5f);
//// 再匀速倒车，返回到目标点
//        interpolatorPath.lineTo(1, 1);
//        objectAnimator.setInterpolator(new PathInterpolator(interpolatorPath));

        //进阶用法
        //同时改变多个属性的值
        PropertyValuesHolder holder = PropertyValuesHolder.ofFloat("scaleX", 0, 1);
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 0.5f, 1);
//        PropertyValuesHolder holder4 = PropertyValuesHolder.ofFloat("color", 0xffff0000, 0xff00ff00);
        final ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(sport, holder, holder1, holder3);
//        objectAnimator1.setEvaluator(new HsvEvaluator());

        //使用关键帧
        Keyframe keyframe = Keyframe.ofFloat(0, 0);
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 100); //进度为一半的时候绘制完成动画
        Keyframe keyframe3 = Keyframe.ofFloat(1.0f, 70); //进度为100的时候回退到70的动画
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("mProgress", keyframe, keyframe2, keyframe3);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(sport, propertyValuesHolder);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(ObjectAnimatorActivity_1.this, "动画完成", Toast.LENGTH_SHORT).show();
            }
        });

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(objectAnimator1, objectAnimator, objectAnimator2);
        animatorSet.setDuration(3000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //整组动画完成的时候才会调用
                Toast.makeText(ObjectAnimatorActivity_1.this, "AnimatorSet 动画完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatorSet.start();
            }
        });

        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectAnimator.cancel();
            }
        });
    }


    // 自定义 HslEvaluator
    private class HsvEvaluator implements TypeEvaluator<Integer> {
        float[] startHsv = new float[3];
        float[] endHsv = new float[3];
        float[] outHsv = new float[3];

        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            // 把 ARGB 转换成 HSV
            Color.colorToHSV(startValue, startHsv);
            Color.colorToHSV(endValue, endHsv);

            // 计算当前动画完成度（fraction）所对应的颜色值
            if (endHsv[0] - startHsv[0] > 180) {
                endHsv[0] -= 360;
            } else if (endHsv[0] - startHsv[0] < -180) {
                endHsv[0] += 360;
            }
            outHsv[0] = startHsv[0] + (endHsv[0] - startHsv[0]) * fraction;
            if (outHsv[0] > 360) {
                outHsv[0] -= 360;
            } else if (outHsv[0] < 0) {
                outHsv[0] += 360;
            }
            outHsv[1] = startHsv[1] + (endHsv[1] - startHsv[1]) * fraction;
            outHsv[2] = startHsv[2] + (endHsv[2] - startHsv[2]) * fraction;

            // 计算当前动画完成度（fraction）所对应的透明度
            int alpha = startValue >> 24 + (int) ((endValue >> 24 - startValue >> 24) * fraction);

            // 把 HSV 转换回 ARGB 返回
            return Color.HSVToColor(alpha, outHsv);
        }
    }


}
