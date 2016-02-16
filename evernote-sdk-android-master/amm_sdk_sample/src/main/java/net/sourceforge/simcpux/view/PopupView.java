package net.sourceforge.simcpux.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/1/7.
 */
public class PopupView extends View {
    int mWidth;
    int mHeight;
    int mRectWidth;
    int mRectHeight;
    public PopupView(Context context) {
        super(context);
    }

    public PopupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
            mRectWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
            mRectHeight = mHeight-20;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#2C97DE"));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0, 0, mRectWidth, mRectHeight), 10, 10, paint);
        Path path = new Path();
        path.moveTo(mRectWidth/2-30,mRectHeight);
        path.lineTo(mRectWidth / 2, mRectHeight+20);
        path.lineTo(mRectWidth / 2 + 30, mRectHeight);
        path.close();
        canvas.drawPath(path,paint);
    }
}
