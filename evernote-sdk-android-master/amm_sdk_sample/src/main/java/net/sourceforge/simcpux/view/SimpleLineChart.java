package net.sourceforge.simcpux.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/7.
 */
public class SimpleLineChart extends View {
    public SimpleLineChart(Context context) {
        super(context);
    }

    public SimpleLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int mWidth;
    int mHeight;
    private String[] mXAxis = {};
    private String[] mYAxis = {};
    private float axispaintSize = 30;
    private Map<Integer,Integer> pointMap;
    private String mNoDataMsg = "no data";
    private int mLineColor = Color.parseColor("#00BCD4");
    private float mStrokeWidth = 8.0f;
    private float mPointRadius = 10;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }else if(widthMode == MeasureSpec.AT_MOST){
            throw new IllegalArgumentException("width must be EXACTLY,you should set like android:width=\"200dp\"");
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }else if(widthMeasureSpec == MeasureSpec.AT_MOST){

            throw new IllegalArgumentException("height must be EXACTLY,you should set like android:height=\"200dp\"");
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mXAxis.length==0||mYAxis.length==0){
            throw  new IllegalArgumentException("X or Y items is null");
        }
        Paint axisPaint = new Paint();
        axisPaint.setTextSize(axispaintSize);
        axisPaint.setColor(Color.parseColor("#3F51B5"));
        if(pointMap==null||pointMap.size()==0){
            int textLength = (int) axisPaint.measureText(mNoDataMsg);
            canvas.drawText(mNoDataMsg,mWidth/2 - textLength/2, mHeight/2, axisPaint);
        }else {
            int[] yPoints = new int[mYAxis.length];


            //计算Y轴 每个刻度的间距
            int yInterval = (int) ((mHeight - axispaintSize - 2) / (mYAxis.length));

            //测量Y轴文字的高度 用来画第一个数
            Paint.FontMetrics fm = axisPaint.getFontMetrics();
            int yItemHeight = (int) Math.ceil(fm.descent - fm.ascent);

            Log.e("wing", mHeight + "");
            for (int i = 0; i < mYAxis.length; i++) {
                canvas.drawText(mYAxis[i], 0, axispaintSize + i * yInterval, axisPaint);
                yPoints[i] = (int) (axispaintSize + i * yInterval);
            }

            int[] xPoints = new int[mXAxis.length];

            Log.e("wing", xPoints.length + "");
            //计算Y轴开始的原点坐标
            int xItemX = (int) axisPaint.measureText(mYAxis[1]);

            //X轴偏移量
            int xOffset = 50;
            //计算x轴 刻度间距
            int xInterval = (int) ((mWidth - xOffset) / (mXAxis.length));
            //获取X轴刻度Y坐标
            int xItemY = (int) (axispaintSize + mYAxis.length * yInterval);

            for (int i = 0; i < mXAxis.length; i++) {
                canvas.drawText(mXAxis[i], i * xInterval + xItemX + xOffset, xItemY, axisPaint);
                xPoints[i] = (int) (i * xInterval + xItemX + axisPaint.measureText(mXAxis[i]) / 2 + xOffset + 10);
//            Log.e("wing", xPoints[i] + "");
            }
            Paint pointPaint = new Paint();

            pointPaint.setColor(mLineColor);

            Paint linePaint = new Paint();

            linePaint.setColor(mLineColor);
            linePaint.setAntiAlias(true);
            //设置线条宽度
            linePaint.setStrokeWidth(mStrokeWidth);
            pointPaint.setStyle(Paint.Style.FILL);


            for (int i = 0; i < mXAxis.length; i++) {
                if (pointMap.get(i) == null) {
                    throw new IllegalArgumentException("PointMap has incomplete data!");
                }

                //画点
                canvas.drawCircle(xPoints[i], yPoints[pointMap.get(i)], mPointRadius, pointPaint);
                if (i > 0) {
                    canvas.drawLine(xPoints[i - 1], yPoints[pointMap.get(i - 1)], xPoints[i], yPoints[pointMap.get(i)], linePaint);
                }
            }
        }

    }

    /**
     * 设置map数据
     * @param data
     */
    public void setData(HashMap<Integer,Integer> data){
        pointMap = data;
        invalidate();
    }

    /**
     * 设置Y轴文字
     * @param yItem
     */
    public void setYItem(String[] yItem){
        mYAxis = yItem;
    }

    /**
     * 设置X轴文字
     * @param xItem
     */
    public void setXItem(String[] xItem){
        mXAxis = xItem;
    }

    public void setLineColor(int color){
        mLineColor = color;
        invalidate();
    }
}
