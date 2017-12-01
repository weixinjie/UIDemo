package will.github.com.uidemo.canvas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import will.github.com.uidemo.R;
import will.github.com.uidemo.utils.ScreenUtils;

/**
 * Created by will on 2017/11/24.
 * Canvas对绘制的辅助作用
 */

public class CanvasDraw extends View {
    Bitmap bitmap = null;
    Paint paint;
    Bitmap bitmap2 = null;
    float rotateDegree;
    Context context;

    public CanvasDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.test);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.test);
        paint = new Paint();
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.save();
//        Path path = new Path();
//        path.addCircle(150, 150, 400, Path.Direction.CW);
//        canvas.clipPath(path);
//        canvas.drawBitmap(bitmap, 120, 120, paint);
//        canvas.restore();
//
//        canvas.save();
//        canvas.rotate(30, 300, 300); //必须先旋转在绘制
//        canvas.drawBitmap(bitmap2, 100, 100, paint);
//        canvas.restore();
//
//        canvas.save();
//        canvas.scale(0.6f, 0.6f, bitmap2.getWidth() / 2 + 100, 100 + bitmap2.getHeight() / 2);
//        canvas.drawBitmap(bitmap2, 100, 100, paint);
//        canvas.restore();
//
//        canvas.save();
//        canvas.skew(0, 0.5f); //错切
//        canvas.drawBitmap(bitmap2, 200, 200, paint);
//        canvas.restore();
//
//        //使用matrix做几何变换
//        canvas.save();
//        Matrix matrix = new Matrix();
//        matrix.postTranslate(300, 300);
//        canvas.setMatrix(matrix);
//        canvas.drawBitmap(bitmap, 100, 100, paint);
//        canvas.restore();
//
//        //实现任意扭曲
//        float pointsSrc[] = {100, 100, 600, 100, 100, 500, 600, 500};
//        float pointsDst[] = {100 - 10, 100 + 50, 600 + 120, 100 - 90, 100 + 20, 500 + 30, 600 + 20, 500 + 60};
//        matrix.reset();
//        matrix.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 4);
//        canvas.save();
//        canvas.concat(matrix);
//        canvas.drawBitmap(bitmap, 100, 100, paint);
//        canvas.restore();
//
//        //使用camera
//        canvas.save();
//        Camera camera = new Camera();
//        camera.save();
//        camera.rotateX(30);
//        canvas.translate(300, 300); //顺序要倒着写
////        camera.setLocation(0, 0, 1000);
//        camera.applyToCanvas(canvas);
//        canvas.translate(-300, -300);
//        camera.restore();
//        canvas.scale(0.5f, 0.5f);
//        canvas.drawBitmap(bitmap2, 200, 200, paint);
//        canvas.restore();

        canvas.clipRect(0, 0, ScreenUtils.getScreenWidth((Activity) context), ScreenUtils.getScreenHeight((Activity) context)); //范围裁切
        rotateDegree += 3;
        canvas.rotate(rotateDegree % 360, ScreenUtils.getScreenWidth((Activity) context) / 2, ScreenUtils.getScreenHeight((Activity) context) / 2);
        canvas.drawBitmap(bitmap, 100, 100, paint);
        Random random = new Random();
        int x = random.nextInt(300);
        int y = random.nextInt(300);
        paint.setStrokeWidth(60);
        paint.setColor(Color.BLUE);
        canvas.drawPoint(x, y, paint);
        invalidate();
    }
}
