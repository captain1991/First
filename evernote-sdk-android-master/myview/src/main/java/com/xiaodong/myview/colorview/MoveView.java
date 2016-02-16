package com.xiaodong.myview.colorview;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.TimerTask;

/**
 * Created by Administrator on 2016/1/27.
 */
public class MoveView extends View {
    public MoveView(Context context) {
        this(context, null);
    }

    public MoveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        percent = 45;
        init();
    }

    int width;
    int height;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);

    }

    int percent;
    Paint mPaint;
    Paint liPaint;
    Paint showPaint;
    Camera mCamera;
    Matrix mMatrix;

    private void init() {
        liPaint = new Paint();
        liPaint.setAntiAlias(true);
        liPaint.setStyle(Paint.Style.STROKE);
        liPaint.setStrokeWidth(20);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        showPaint = new Paint();
        mCamera = new Camera();
        mMatrix = new Matrix();
    }

    int i = 30;
    boolean isclose;
    float cameraRotateX;
    float cameraRotateY;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mPaint.setColor(Color.parseColor("#9F0B05"));
        canvas.drawRect(new RectF(0, height / 2 - 300, width, height), mPaint);
        canvas.restore();
        canvas.save();
        mPaint.setColor(Color.WHITE);
        canvas.drawOval(new RectF(width / 2 - 400, height / 2 + 80, width / 2 + 400, height / 2 + 800), mPaint);
        canvas.restore();
        mMatrix.reset();
        mCamera.save();
        mCamera.rotateX(cameraRotateX);
        mCamera.rotateY(cameraRotateY);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();
//
        mMatrix.preTranslate(-width / 2 - 10, -height / 2);
        mMatrix.postTranslate(width / 2 + 10, height / 2);
        canvas.concat(mMatrix);
        mPaint.setColor(Color.parseColor("#ffffff"));
//        canvas.drawOval(new RectF(width / 2 - 200, height / 2 - 100, width / 2 + 200, height / 2 + 100), mPaint);
        canvas.drawRoundRect(new RectF(width / 2 - 200, height / 2 - 100, width / 2 + 200, height / 2 + 100), 150, 100, mPaint);

        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(width / 2 - 80, height / 2, i, mPaint);
        canvas.drawLine(width / 2 - 95, height / 2, width / 2 + 95, height / 2, liPaint);
        canvas.drawCircle(width / 2 + 80, height / 2, i, mPaint);
        canvas.save();
        showPaint.setShader(new LinearGradient(0, height / 2 + 110, 0, height / 2 + 80, 0xEE000000, Color.TRANSPARENT, Shader.TileMode.CLAMP));
//        canvas.drawRect(width / 2 - 200, height / 2 + 80, width / 2 + 200, height / 2 + 110, showPaint);
        canvas.drawArc(new RectF(width / 2 - 250, height / 2 - 100, width / 2 + 250, height / 2 + 105), 40, 100, false, showPaint);
        canvas.restore();


        if (!isclose) {
            if (i > 15) {
                i--;
                postInvalidateDelayed(5);
            } else {
                isclose = true;
                postInvalidateDelayed(1);
            }
        } else {
            if (i < 30) {
                i++;
                postInvalidateDelayed(5);
            } else {
                isclose = false;
                postInvalidateDelayed(10000);
            }
        }

//        mPaint.setColor(Color.parseColor("#ffffff"));
//        canvas.save();
//        canvas.drawArc(new RectF(width / 2 - 300, height / 2 - 100, width / 2 + 300, height / 2 + 100), 225, 90, false, mPaint);
//        canvas.drawArc(new RectF(width / 2 - 300, height / 2 - 100, width / 2 + 300, height / 2 + 100), 45, 90, false, mPaint);
//        canvas.drawArc(new RectF(width / 2 - 100 - 139, height / 2 - 100, width / 2 + 100 - 139, height / 2 + 100), 135, 90, false, mPaint);
//        canvas.drawArc(new RectF(width / 2 - 100 + 139, height / 2 - 100, width / 2 + 100 + 139, height / 2 + 100), 315, 90, false, mPaint);
//        canvas.drawRect(new RectF((float) (width / 2 - 100 - 110), (float) (height / 2 - 70), (float) (width / 2 + 100 + 110), (float) (height / 2 + 70)), mPaint);
//        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
                rotateCanvas(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                cameraRotateX = 0;
                cameraRotateY = 0;
                invalidate();
                break;
            case MotionEvent.ACTION_DOWN:
                rotateCanvas(x, y);
                invalidate();
                break;
        }
        return true;
    }

    private void rotateCanvas(float x, float y) {
        float disY = (y - height / 2) / (height / 2);
        float disX = (x - width / 2) / (width / 2);
        if (disY < 0) {
            cameraRotateX = (Math.abs(disY)) * percent;
        } else {
            cameraRotateX = -(Math.abs(disY)) * percent;
        }
        if (disX < 0) {
            cameraRotateY = -(Math.abs(disX)) * percent;
        } else {
            cameraRotateY = (Math.abs(disX)) * percent;
        }
    }

}
