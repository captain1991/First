package com.xiaodong.myview.colorview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.xiaodong.myview.R;

/**
 * Created by Administrator on 2016/1/22.
 */
public class MatrixImage extends ImageView {
    Context mContext;
    int imageId;
    int MODE;
    private final static int MODE_ONE = 0;
    private final static int MODE_TWO = 1;
    Matrix currentmatrix, savedmatrix;
    PointF start, moved;

    public MatrixImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public MatrixImage(Context context, AttributeSet attrs, int de) {
        super(context, attrs, de);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MatrixImage, de, 0);
        init();
    }

    private void init() {
        currentmatrix = new Matrix();
        savedmatrix = new Matrix();
        start = new PointF();
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        Drawable drawable = getDrawable();
        Bitmap bitmap = null;
        if (drawable == null) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.desert);
        } else {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            bitmap = bitmapDrawable.getBitmap();
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, dm.widthPixels, dm.heightPixels / 2, true);
        setImageBitmap(bitmap);
    }

    float spac;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
//                currentmatrix.set(savedmatrix);
                if (MODE == MODE_ONE) {
                    currentmatrix.set(savedmatrix);
                    float x = event.getX() - start.x;
                    float y = event.getY() - start.y;
                    currentmatrix.postTranslate(x, y);
                }else if(MODE == MODE_TWO&&event.getPointerCount()==2){
                    float scaSpac=calSpace(event);
                    PointF pointF = new PointF();
                    calMidPoint(pointF,event);
                    currentmatrix.set(savedmatrix);
                    currentmatrix.postScale(scaSpac/spac,scaSpac/spac,pointF.x,pointF.y);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                MODE = MODE_ONE;
                savedmatrix.set(currentmatrix);
                start.set(event.getX(), event.getY());
                Log.e("wwwwww", "sssssss");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                spac = calSpace(event);
                if (Math.abs(spac) > 10) {
                    MODE = MODE_TWO;
                    savedmatrix.set(currentmatrix);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                MODE = 3;
                break;
        }
        setImageMatrix(currentmatrix);
        return true;
    }

    private float calSpace(MotionEvent event) {
        float space = 0;
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        space = (float) Math.sqrt(x * x + y * y);
        return space;
    }
    private void calMidPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
