package com.xiaodong.myview.colorview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.xiaodong.myview.R;

/**
 * Created by Administrator on 2016/1/21.
 */
public class ReflectView extends View {
    public ReflectView(Context context) {
        super(context);
    }

    public ReflectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }


    Paint paint;
    Bitmap srcbitmap;
    Bitmap reBitmap;
    int screenW;
    int screenH;
    PorterDuffXfermode mXfermode;
    private void initPaint(){
        paint = new Paint();
        srcbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        Matrix matrix = new Matrix();
        screenW = getWidth();
        screenH = getHeight();
        matrix.setScale(1F,-1F);
        reBitmap = Bitmap.createBitmap(srcbitmap, 0, 0, srcbitmap.getWidth(),srcbitmap.getHeight(),matrix,true);
//      paint.setShader(new LinearGradient(0, getHeight(), 0, getHeight() *2 / 4, 0xEE000000, Color.TRANSPARENT, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(srcbitmap, null, new RectF(0, 0, getWidth(), (getHeight() * 3) / 4), null);
        canvas.drawBitmap(reBitmap, null, new RectF(0, (getHeight() * 3) / 4, getWidth(), (getHeight() * 6) / 4), null);
        paint.setShader(new LinearGradient(0, getHeight(), 0, getHeight() * 2 / 4, 0xEE000000, Color.TRANSPARENT, Shader.TileMode.CLAMP));
        canvas.drawRect(0, (getHeight() * 3) / 4, getWidth(), getHeight(), paint);
    }
}
