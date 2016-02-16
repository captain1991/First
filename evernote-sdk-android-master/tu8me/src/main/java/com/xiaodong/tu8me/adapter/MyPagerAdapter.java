package com.xiaodong.tu8me.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dashuai.core.DBitmap;
import com.xiaodong.tu8me.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class MyPagerAdapter extends PagerAdapter {
    Context context;
    List<String> imgUrl;
    ImageView[] imageViews1 = new ImageView[2];
    List<ImageView> imageViews = new ArrayList<ImageView>();
    ImageView imageView;
    DBitmap dBitmap;
    public MyPagerAdapter(Context context, List<String> imgUrl) {
        this.context = context;
        this.imgUrl = imgUrl;
        dBitmap = DBitmap.create(context);
        initData();
    }
    public void initData(){
        imageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        dBitmap.display(imageView, imgUrl.get(0), 2524, 1080);
        imageViews1[0]=imageView;
        ImageView imageView1 = new ImageView(context);
        imageView1.setLayoutParams(params);
        dBitmap.display(imageView1, imgUrl.get(1), 2524, 1080);
        imageViews1[1]=imageView1;
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews1[position]);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews1[position]);
        return imageViews1[position];
    }
}
