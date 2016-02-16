package com.xiaodong.myview.gameview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/1/13.
 */
public class Game2048Item extends View {
    public Game2048Item(Context context) {
        this(context, null);
    }

    public Game2048Item(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    Paint paint;
    Rect bundle;
    public Game2048Item(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public int getmNumber(){
       return mNumber;
    }
    public void setmNumber(int mNumber){
        this.mNumber=mNumber;
        String numberstr = mNumber+"";
        paint.setTextSize(30.0f);
        bundle = new Rect();
        paint.getTextBounds(numberstr,0,numberstr.length(),bundle);
        invalidate();
    }
    int mNumber;
    @Override
    protected void onDraw(Canvas canvas) {

        String mBgColor = "#EA7821";
        switch (mNumber)
        {
            case 0:
                mBgColor = "#CCC0B3";
                break;
            case 2:
                mBgColor = "#EEE4DA";
                break;
            case 4:
                mBgColor = "#EDE0C8";
                break;
            case 8:
                mBgColor = "#F2B179";// #F2B179
                break;
            case 16:
                mBgColor = "#F49563";
                break;
            case 32:
                mBgColor = "#F5794D";
                break;
            case 64:
                mBgColor = "#F55D37";
                break;
            case 128:
                mBgColor = "#EEE863";
                break;
            case 256:
                mBgColor = "#EDB04D";
                break;
            case 512:
                mBgColor = "#ECB04D";
                break;
            case 1024:
                mBgColor = "#EB9437";
                break;
            case 2048:
                mBgColor = "#EA7821";
                break;
            default:
                mBgColor = "#EA7821";
                break;
        }
        paint.setColor(Color.parseColor(mBgColor));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        if(mNumber!=0){
            setText(canvas);
        }
    }

    private void setText(Canvas canvas){
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        int startX = (getWidth()-bundle.width())/2;
        int startY = getHeight()/2+bundle.height()/2;
        canvas.drawText(mNumber+"",startX,startY,paint);
    }

}
