package net.sourceforge.simcpux.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2016/1/8.
 */
public class ErrorView extends View {
    public ErrorView(Context context) {
        super(context);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Context mContext;

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    int mWidth;
    int mHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = 300;
//            mWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300,mContext.getResources().getDisplayMetrics());
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;

        } else if (heightMode == MeasureSpec.AT_MOST) {
            mHeight = 300;
//            mHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300,mContext.getResources().getDisplayMetrics());
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    int progress;
    int i;
    int j;
    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.parseColor("#CC1C09"));
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(10);

        canvas.drawArc(new RectF(10, 10, mWidth - 10, mHeight - 10), 0, 360 * progress / 100, false, p);
        progress += 5;

        if (progress <= 100) {
            postInvalidateDelayed(10);
        } else {
            canvas.drawLine(mWidth / 4, mHeight / 4, mWidth / 4 + mWidth/40*i, mHeight/4+mHeight/40*i, p);
            if(i<=20){
                i++;
                postInvalidateDelayed(10);
            }else {
                canvas.drawLine(mWidth / 4, mHeight - mHeight / 4,mWidth / 4 + mWidth/40*j,  mHeight - mHeight / 4-mHeight/40*j, p);
                if(j<=20){
                    j++;
                    postInvalidateDelayed(10);
                }
            }

        }
    }
}
