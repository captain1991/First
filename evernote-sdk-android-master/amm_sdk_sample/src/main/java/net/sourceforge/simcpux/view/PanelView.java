package net.sourceforge.simcpux.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/1/8.
 */
public class PanelView extends View {
    public PanelView(Context context) {
        super(context);
    }

    public PanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Context mContext;

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int mWidth;
    int mHeigth;

    public void setmContext(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }else if (widthMode == MeasureSpec.AT_MOST) {
//            mWidth = PxUtil.dpToPx(300, mContext);
            mWidth = 600;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeigth = heightSize;
        }else if (heightMode == MeasureSpec.AT_MOST) {
            mHeigth = 600;
        }
        setMeasuredDimension(mWidth, mHeigth);
    }

    int progress = 25;

    @Override
    protected void onDraw(Canvas canvas) {

        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(50);
        p.setColor(Color.parseColor("#ffffff"));
        canvas.drawArc(new RectF(50, 50, mWidth - 50, mHeigth - 50), 135, 270, false, p);
        p.setColor(Color.parseColor("#2F87D7"));
        canvas.drawArc(new RectF(50, 50, mWidth - 50, mHeigth - 50), 135, 270 * progress / 100, false, p);

        p.setStrokeWidth(10);
        canvas.rotate(270 * progress / 100 - 270 / 2, mWidth / 2, mHeigth / 2);
        canvas.drawLine(mWidth / 2, mHeigth / 2, mWidth / 2, 60, p);
        canvas.rotate(-(270 * progress / 100 - 270 / 2), mWidth / 2, mHeigth / 2);

        p.setStrokeWidth(3);
        canvas.drawLine(mWidth / 2, 5, mWidth / 2, 20, p);
        for(int i=1;i<=5;i++){
            canvas.rotate(27*i, mWidth / 2, mHeigth / 2);
            canvas.drawLine(mWidth / 2, 5, mWidth / 2,20, p);
            canvas.rotate(-27 * i, mWidth / 2, mHeigth / 2);
        }
        for(int i=1;i<=5;i++){
            canvas.rotate(-27*i, mWidth / 2, mHeigth / 2);
            canvas.drawLine(mWidth / 2, 5, mWidth / 2, 20, p);
            canvas.rotate(27 * i, mWidth / 2, mHeigth / 2);
        }
//        Log.e("math.sin", (mWidth / 2*(Math.sin(270 * progress / 100))+""));
//                canvas.drawLine(mWidth / 2, mHeigth / 2, mWidth / 2 - (int)(mWidth / 2*(Math.sin(270 * progress / 100))) , mHeigth / 2 - (int)(mHeigth / 2*(Math.cos(270 * progress / 100))) , p);
//        canvas.rotate(270*progress,mWidth/2,mHeigth/2);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
