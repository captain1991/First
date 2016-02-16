package net.sourceforge.simcpux.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import net.sourceforge.simcpux.R;

/**
 * Created by Administrator on 2016/1/12.
 */
public class BerView extends View {
    public BerView(Context context) {
        super(context,null);
    }

    public BerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BerView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }
    int mWidth;
    int mHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode==MeasureSpec.EXACTLY)
            mWidth = widthSize;
        else
            mWidth = 600;
        if (heightMode == MeasureSpec.EXACTLY){
            mHeight = heightSize;
        }else {
            mHeight = 600;
        }
        setMeasuredDimension(mWidth,mHeight);
        berX=mWidth/2;
        berY=mHeight/2;
    }

    float berX;
    float berY;
    float lionY=mHeight/2-80;
    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setStrokeWidth(3);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.parseColor("#1785FA"));
        canvas.drawOval(new RectF(0, mHeight / 2 - 20, 40, mHeight / 2 + 20), p);
        canvas.drawOval(new RectF(mWidth-40,mHeight/2-20,mWidth,mHeight/2+20),p);
        Path path = new Path();
        path.moveTo(0 + 20, mHeight / 2);
        path.quadTo(berX, berY, mWidth - 20, mHeight / 2);
        canvas.drawPath(path, p);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lion);
        canvas.drawBitmap(bitmap,mWidth/2-20,lionY,p);
        canvas.drawPoint(berX, berY,p);
        if(isUp){
            lionY-=40;
            if((int)(berY-mHeight/2)>20){
                berY-=20;

                postInvalidateDelayed(10);
            }else  if((int)(berY-mHeight/2)<-20){
                berY+=20;
                postInvalidateDelayed(10);
            }else{
                berY=0;
            }
        }
    }
    boolean isUp=false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            berX = event.getX();
            berY = event.getY();
            lionY = mHeight/2+(berY-mHeight/2)/2-80;
            isUp=false;
        }else if(event.getAction()==MotionEvent.ACTION_UP){
//            berY=mHeight/2;
//            berX=mWidth/2;
            isUp=true;

        }
        invalidate();
        return true;
    }
}
