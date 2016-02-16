package com.xiaodong.myview.colorview;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.xiaodong.myview.R;

/**
 * Created by Administrator on 2016/1/20.
 */
public class ColorView extends View {
    public ColorView(Context context) {
        this(context, null);
    }

    public ColorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    Context mContext;

    public ColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDstPaint = new Paint();
        mSrcPaint = new Paint();
        mDstPaint.setColor(Color.YELLOW);
        mSrcPaint.setColor(Color.BLUE);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        this.mContext = context;
        initPaint();
    }


    Paint paint;
    Paint mDstPaint;
    Paint mSrcPaint;
    Bitmap bitmap;
    ColorMatrix colorMatrix;
    AvoidXfermode avoidXfermode;
    private void initPaint(){
        paint = new Paint();
//        mDstPaint = new Paint();
//        mSrcPaint = new Paint();
//        mDstPaint.setColor(Color.YELLOW);
//        mSrcPaint.setColor(Color.BLUE);
//        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        paint.setStyle(Paint.Style.FILL);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.desert);
        colorMatrix = new ColorMatrix(new float[]{
                0.33F, 0.59F, 0.11F, 0, 0,
                0.33F, 0.59F, 0.11F, 0, 0,
                0.33F, 0.59F, 0.11F, 0, 0,
                0, 0, 0, 1, 0,
        });
//        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
//        paint.setColorFilter(new LightingColorFilter(0xFFFFFFFF,0x00FF0000));
//        paint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN));

        avoidXfermode = new AvoidXfermode(0xFFFFFFFF,255, AvoidXfermode.Mode.TARGET);


    }

    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawBitmap(bitmap,null,new RectF(0,0,getWidth(),getHeight()),paint);
//        paint.setARGB(255, 21, 250, 243);
//        paint.setXfermode(avoidXfermode);
//        canvas.drawRect(0,0,getWidth(),getHeight(),paint);
        mDstPaint.setShader(new LinearGradient(0,getHeight(),0,getHeight()*3/4,Color.RED,Color.YELLOW, Shader.TileMode.CLAMP));
        canvas.drawRect(0, getHeight()*3/4, getWidth(),getHeight(), mDstPaint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
//        canvas.drawCircle(30, 30, 30, mSrcPaint);

    }
}
