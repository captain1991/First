package com.xiaodong.myview.gameview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/1/13.
 */
public class Game2048Layout extends RelativeLayout {
    int count = 4;
    int itemWidth;
    int mMargin = 10;
    GestureDetector gestureDetector;
    Game2048Item[] game2048Items;

    public Game2048Layout(Context context) {
        this(context, null);
    }

    public Game2048Layout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Game2048Layout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gestureDetector = new GestureDetector(context, new MyGuesture());
    }
    int length;
    boolean once=false;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        length = Math.min(getMeasuredHeight(), getMeasuredWidth());
        itemWidth = (length - (count + 1) * mMargin) / count;
        if(!once) {
            if (game2048Items == null) {
                game2048Items = new Game2048Item[count * count];
            }
            setChild();
            int changedItem = (int) (Math.random()*count*count);
            game2048Items[changedItem].setmNumber(2);
            once=true;
        }
        setMeasuredDimension(length, length + 300);
        Log.e("onMeasure","onMeasure");

    }

    public void setChild() {
//        for (int i = 0; i < game2048Items.length; i++)
//        {
//            Game2048Item item = new Game2048Item(getContext());
//
//            game2048Items[i] = item;
//            item.setId(i + 1);
//            RelativeLayout.LayoutParams lp = new LayoutParams(itemWidth,
//                    itemWidth);
//            // 设置横向边距,不是最后一列
//            if ((i + 1) % count != 0)
//            {
//                lp.rightMargin = mMargin;
//            }
//            // 如果不是第一列
//            if (i % count != 0)
//            {
//                lp.addRule(RelativeLayout.RIGHT_OF,//
//                        game2048Items[i - 1].getId());
//            }
//            // 如果不是第一行，//设置纵向边距，非最后一行
//            if ((i + 1) > count)
//            {
//                lp.topMargin = mMargin;
//                lp.addRule(RelativeLayout.BELOW,//
//                        game2048Items[i - count].getId());
//            }
//            addView(item, lp);
//        }
        for (int i = 0; i < count * count; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(itemWidth, itemWidth);
            Game2048Item item = new Game2048Item(getContext());
            game2048Items[i] = item;
            item.setId(i + 1);
//            item.setmNumber(i);
            if ((item.getId() - 1) % count == 0) {

            } else if ((item.getId() - 1) % count == 1) {
                lp.leftMargin = itemWidth + mMargin;
            } else if ((item.getId() - 1) % count == 2) {
                lp.leftMargin = (itemWidth + mMargin) * 2;
            } else if ((item.getId() - 1) % count == 3) {
                lp.leftMargin = (itemWidth + mMargin) * 3;
            }
            lp.topMargin = ((item.getId() - 1) / 4) * (itemWidth + mMargin);
            addView(item, lp);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private enum ACTION {
        left, up, right, down
    }

    class MyGuesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                if (e2.getX() - e1.getX() > 100) {
                    action(ACTION.down);
                } else if (e2.getX() - e1.getX() < -100) {
                    action(ACTION.left);
                }
            } else if (Math.abs(velocityY) > Math.abs(velocityX)) {
                if (e2.getY() - e1.getY() > 80) {
                    action(ACTION.down);
                } else if (e2.getY() - e1.getY() < -80) {
                    action(ACTION.up);
                }
            }
            return true;
        }
    }

    private void generateNum(ACTION action){
        int changedItem = (int) (Math.random()*count*4);
        while(game2048Items[changedItem].getmNumber() != 0){
            changedItem = (int) (Math.random()*4*4);
        }
        Log.e("setNumber",""+changedItem);
        Log.e("game2048Items", "" + game2048Items[changedItem].getId());
        game2048Items[changedItem].setmNumber(2);
    }


    public void action(ACTION action) {
        generateNum(action);
    }

}
