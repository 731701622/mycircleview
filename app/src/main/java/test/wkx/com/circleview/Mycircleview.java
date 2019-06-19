package test.wkx.com.circleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class Mycircleview extends AppCompatImageView {


    private Paint mBitmapPaint;
    private int mRadius;
    private Matrix mMatrix;
    private BitmapShader mBitmapShader;
    private int mWidth;

    public Mycircleview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = mWidth / 2;
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        setUpShader();

        canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
    }

    /**
     * 初始化BitmapShader
     */
    private void setUpShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        Bitmap bmp = drawableToBitmap(drawable);
        if (bmp == null) {
            return;
        }
        // 将bmp作为着色器，就是在指定区域内绘制bmp

        if (mBitmapShader == null) {
            mBitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }

        float scale = getScale(bmp);
        // 对图片进行缩放
        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);

        mBitmapPaint.setShader(mBitmapShader);
    }

    /**
     * 获取缩放比例
     */
    private float getScale(Bitmap bmp) {
        float scale;
        int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
        scale = mWidth * 1.0f / bSize;
        return scale;
    }

    /**
     * drawable转bitmap
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        // 如果drawable是BitmapDrawable的一个实例，则直接返回getBitmap
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }

        // 获取drawable固有的宽高
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 创建一个bitmap为canvas的操作对象，绘制的内容显示在bitmap上
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 确定绘制的区域
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}