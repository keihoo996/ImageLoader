package com.example.imageloader.loader.picasso;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.squareup.picasso.Transformation;

/**
 * 圆形图片转换器
 */
public class CircleTransform implements Transformation {

    public CircleTransform() {
    }

    @Override
    public Bitmap transform(Bitmap source) {
        //2.圆形处理
        Bitmap bitmap = circleBitmap(source);
        //必须要回收source，否则会报错
        source.recycle();
        return bitmap;//返回圆形的Bitmap对象
    }

    @Override
    public String key() {
        return "circle";
    }

    /**
     * 对bitmap进行压缩处理
     *
     * @param source ：需要被处理的Bitmap
     * @param width  需要压缩成的宽度 必须为浮点型
     * @param height 需要压缩成的高度 必须为浮点型
     * @return 返回压缩后的Bitmap
     * 注意！必须提供参数2，3为浮点型。
     */
    public static Bitmap zoom(Bitmap source, float width, float height) {
        Matrix matrix = new Matrix();
        float scaleX = width / source.getWidth();
        float scaleY = height / source.getHeight();
        matrix.postScale(scaleX, scaleY);

        Bitmap bitmap = Bitmap.createBitmap(source, 0, 0,
                source.getWidth(), source.getHeight(), matrix, true);
        return bitmap;
    }

    private Bitmap circleBitmap(Bitmap source) {
        //获取Bitmap的宽度
        int width = source.getWidth();
        //返回一个正方形的Bitmap对象
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        //提供指定宽高的canvas
        Canvas canvas = new Canvas(bitmap);
        //提供画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //背景：在画布上绘制一个圆
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);

        //设置图片相交情况下的处理方式
        //setXfermode：设置当绘制的图像出现相交情况时候的处理方式的,它包含的常用模式有哪几种
        //PorterDuff.Mode.SRC_IN 取两层图像交集部门,只显示上层图像,注意这里是指取相交叉的部分,然后显示上层图像
        //PorterDuff.Mode.DST_IN 取两层图像交集部门,只显示下层图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //前景：在画布上绘制一个bitmap
        canvas.drawBitmap(source, 0, 0, paint);
        return bitmap;
    }
}
