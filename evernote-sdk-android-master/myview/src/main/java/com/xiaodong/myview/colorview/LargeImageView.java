package com.xiaodong.myview.colorview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

import com.xiaodong.myview.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/1/26.
 */
public class LargeImageView extends View {

    int imgWidth;
    int imgHeight;
    int startX;
    int startY;
    int realWidth;
    int realHeight;
    GestureDetector gestureDetector;
    Bitmap drawBitmap=null;
    public LargeImageView(Context context) {
        this(context, null);
    }

    public LargeImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LargeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        gestureDetector = new GestureDetector(context,new mGestureListener());
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (widthMode==MeasureSpec.EXACTLY){
            imgWidth = widthSize;
        }
        if(heightMode==MeasureSpec.EXACTLY){
            imgHeight = heightSize;
        }
        setMeasuredDimension(imgWidth,imgHeight);
        rect = new Rect(startX, startY, startX+imgWidth, startY+imgHeight);
    }

    InputStream inputStream;
    BitmapRegionDecoder bmpDecoder;
    BitmapFactory.Options mOption;
    private void init(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.desert);
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        inputStream = new ByteArrayInputStream(bos.toByteArray());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),R.drawable.desert,options);
        realWidth=options.outWidth;
        realHeight = options.outHeight;
        try {
            mOption = new BitmapFactory.Options();
            mOption.inPreferredConfig = Bitmap.Config.RGB_565;
            bmpDecoder = BitmapRegionDecoder.newInstance(inputStream, false);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    Rect rect;
    @Override
    protected void onDraw(Canvas canvas) {
//        new Rect(startX, startY, startX+imgWidth, startY+imgHeight)
        drawBitmap = bmpDecoder.decodeRegion(rect,mOption);
        canvas.drawBitmap(drawBitmap,0,0,null);
    }
    PointF start = new PointF();
    int savedstartX;
    int savedstartY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        gestureDetector.onTouchEvent(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:
                startX = savedstartX;
                startY = savedstartY;
                startX+=-(event.getX()-start.x);
                Log.e("onScroll","onscroll="+startX);
                startY+=-(event.getY()-start.y);
                rect.set(startX, startY, startX+imgWidth, startY+imgHeight);
                invalidate();
                break;
            case MotionEvent.ACTION_DOWN:
                savedstartX = startX;
                savedstartY = startY;
                start.set(event.getX(), event.getY());
                break;

        }
        return true;
    }

    class mGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            startX+=-(e2.getX()-e1.getX());
            startY+=-(e2.getY()-e1.getY());
//            rect.offset(startX,0);
//            testWidthHeight();
            invalidate();
            return true;
        }

//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            Log.e("onScroll","onscroll="+startX);
//            startX+=-(e2.getX()-e1.getX());
//            startY+=-(e2.getY()-e1.getY());
//            testWidthHeight();
//            invalidate();
//            return true;
//        }
    }

    private void testWidthHeight(){
        if(rect.right>realWidth){
            rect.right = realWidth;
            rect.left = realWidth-imgWidth;
        }
//        if( startY+imgHeight>realHeight){
//            startY= realHeight-imgHeight;
//        }
    }
}
