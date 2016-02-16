package net.sourceforge.simcpux.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;

/**
 * Created by Administrator on 2016/1/11.
 */
public class StatelLiteView extends ViewGroup implements View.OnClickListener {
    public StatelLiteView(Context context) {
        super(context,null);
    }

    public StatelLiteView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }
    Context mContext;
    AttributeSet attributeSet;
    public StatelLiteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        attributeSet = attrs;
    }



    int mWidth;
    int mHeight;
    int count;
    int radius;
    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        count = getChildCount();
        int widthSize = MeasureSpec.getSize(widthSpec);
        int widthMode = MeasureSpec.getMode(widthSpec);
        int heightSize = MeasureSpec.getSize(heightSpec);
        int heightMode = MeasureSpec.getMode(heightSpec);

        if(widthMode==MeasureSpec.EXACTLY)
            mWidth = widthSize;
        else
            mWidth = 600;
        if (heightMode == MeasureSpec.EXACTLY){
            mHeight = heightSize;
        }else {
            mHeight = 600;
        }

        if(mWidth<mHeight){
            radius=mWidth;
        }else {
            radius=mHeight;
        }
        setMeasuredDimension(mWidth, mHeight);
        for(int i = 0;i<count;i++) {
            measureChild(getChildAt(i), widthSpec, heightSpec);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        gravityChild();
        childStatelLite();
    }

    private void childStatelLite(){
        Log.e("count","count="+count);
        if(count>1) {
            for (int i = 1; i < count; i++) {
                View lite = getChildAt(i);
                int lite_width = lite.getMeasuredWidth();
                int lite_height = lite.getMeasuredHeight();
                if(i==1){
                lite.layout(0,mHeight-lite_height,lite_width,mHeight);
                }else{
//                    Log.e("i","i="+i);
//                    Log.e("PI","PI="+Math.PI);
//                    Log.e("sin","sinA="+Math.sin((Math.PI/2)/(count-1-1)*(i-1)));
                    lite.layout((int)(mWidth - (radius -lite_width)*(Math.cos((Math.PI/2) / (count - 1-1)*(i-1)))-lite_width),
                        (int)(mHeight - (radius -lite_height)*(Math.sin((Math.PI/2)/(count-1-1)*(i-1)))-lite_height),
                        (int)(mWidth - (radius -lite_width)*(Math.cos((Math.PI/2)/ (count - 1-1)*(i-1)))),
                        (int)(mHeight - (radius -lite_height)*(Math.sin((Math.PI/2)/(count-1-1)*(i-1)))));
                }
                if(isVisible) {
                    lite.setVisibility(View.VISIBLE);
                }else {
                    lite.setVisibility(View.GONE);
                }
            }
        }
    }

    private void gravityChild(){
        View btn1 = getChildAt(0);
        int btn1_width = btn1.getMeasuredWidth();
        int btn1_height = btn1.getMeasuredHeight();
        btn1.setOnClickListener(this);
        btn1.layout(mWidth-btn1_width, mHeight-btn1_height, mWidth,mHeight);
    }
boolean isOn=false;
    boolean isVisible =false;
    @Override
    public void onClick(View view) {
        if(!isOn) {
            for (int i = 1; i < count; i++) {
                View lite = getChildAt(i);
                lite.setVisibility(View.VISIBLE);
                TranslateAnimation animation=null;
                if(i==1){
                    animation = new TranslateAnimation(mWidth-lite.getMeasuredWidth(),0,0,0);
                }else{
                    animation = new TranslateAnimation((float) ((radius -lite.getMeasuredWidth())*(Math.cos((Math.PI/2) / (count - 1-1)*(i-1)))),0, (float) ((radius -lite.getMeasuredWidth())*(Math.sin((Math.PI/2)/(count-1-1)*(i-1)))),0);
                }

                RotateAnimation rotateAnimation = new RotateAnimation(0,225,lite.getMeasuredWidth()/2,lite.getMeasuredHeight()/2);
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(rotateAnimation);
                animationSet.addAnimation(animation);
                animationSet.setDuration(500);
                animationSet.setFillAfter(true);
                lite.startAnimation(animationSet);
                lite.setVisibility(View.VISIBLE);
                isVisible = true;
                isOn=true;
            }
        }else{
            for (int i = 1; i < count; i++) {
                View lite = getChildAt(i);
                TranslateAnimation animation=null;
                if(i==1){
                    animation = new TranslateAnimation(0,mWidth-lite.getMeasuredWidth(),0,0);
                }else {
                    animation = new TranslateAnimation(0,(float) ((radius -lite.getMeasuredWidth())*(Math.cos((Math.PI/2) / (count - 1-1)*(i-1)))),0,(float) ((radius -lite.getMeasuredWidth())*(Math.sin((Math.PI/2)/(count-1-1)*(i-1)))));
                }

                RotateAnimation rotateAnimation = new RotateAnimation(225,0,lite.getMeasuredWidth()/2,lite.getMeasuredHeight()/2);
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(rotateAnimation);
                animationSet.addAnimation(animation);
                animationSet.setDuration(500);
                lite.startAnimation(animationSet);
                Log.e("Log", "Log");
                lite.setVisibility(View.GONE);
                isVisible = false;
                isOn=false;
            }
        }
    }
}
