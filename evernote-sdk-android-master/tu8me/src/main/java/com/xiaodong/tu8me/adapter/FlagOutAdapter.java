package com.xiaodong.tu8me.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dashuai.core.DBitmap;
import com.xiaodong.tu8me.R;
import com.xiaodong.tu8me.vo.FlagOutItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2015/12/22.
 */
public class FlagOutAdapter extends BaseAdapter {
    public List<FlagOutItem> flagOutItems;
    public Context context;
    public DBitmap dBitmap;
    public FlagOutAdapter(Context context) {
        this.context = context;
        dBitmap = DBitmap.create(context);
    }

    public List<FlagOutItem> getFlagOutItems() {
        return flagOutItems;
    }

    public void setFlagOutItems(List<FlagOutItem> flagOutItems) {
        this.flagOutItems = flagOutItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return flagOutItems == null ? 0 : flagOutItems.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (i == 0){
            view = LayoutInflater.from(context).inflate(R.layout.page_item, null);
            ViewPager pager = (ViewPager) view.findViewById(R.id.viewpager);
//            String[] strings = new String[]{};
            List<String> strings1 = new ArrayList<String>();
//            Set<String> strings2 = new TreeSet<String>();
            strings1.add(flagOutItems.get(0).imgUrl);
            strings1.add(flagOutItems.get(1).imgUrl);
//            strings[0]=flagOutItems.get(0).getImgUrl();
//            strings[1]=flagOutItems.get(1).getImgUrl();
            MyPagerAdapter adapter = new MyPagerAdapter(context,strings1);
            pager.setAdapter(adapter);
        }else if(i==1){
            view = LayoutInflater.from(context).inflate(R.layout.seconditem, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.second_img);
            ImageView imageView1 = (ImageView) view.findViewById(R.id.second_img_1);
            ImageView imageView2 = (ImageView) view.findViewById(R.id.second_img_2);
            ImageView imageView3 = (ImageView) view.findViewById(R.id.second_img_3);
            ImageView imageView4 = (ImageView) view.findViewById(R.id.second_img_4);
            ImageView imageView5 = (ImageView) view.findViewById(R.id.second_img_5);
            ImageView imageView6 = (ImageView) view.findViewById(R.id.second_img_6);
            dBitmap.display(imageView,"http://www.tu8.me/uploads/misc/567409612dcc4.png");
            dBitmap.display(imageView1,"http://www.tu8.me/uploads/misc/567409e5e9578.jpg");
            dBitmap.display(imageView2,"http://www.tu8.me/uploads/misc/566e8fbac5210.jpg");
            dBitmap.display(imageView3,"http://www.tu8.me/uploads/misc/566e8fc1a3374.jpg");
            dBitmap.display(imageView4,"http://www.tu8.me/uploads/misc/566e8fead6ec5.jpg");
            dBitmap.display(imageView5,"http://www.tu8.me/uploads/misc/566e8ff5ea4cb.jpg");
            dBitmap.display(imageView6,"http://www.tu8.me/uploads/misc/567408fe3a295.png");
        }else {
            if (view == null || view.getTag() == null) {
                view = LayoutInflater.from(context).inflate(R.layout.flagout_item, null);
                holder = new ViewHolder();
                holder.textView = (TextView) view.findViewById(R.id.flag_outitemtext);
                holder.imageView = (ImageView) view.findViewById(R.id.flag_outitemimg);
                holder.price = (TextView) view.findViewById(R.id.price);
                holder.delprice = (TextView) view.findViewById(R.id.delprice);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            FlagOutItem flagOutItem = flagOutItems.get(i-2);
            TextView textView = holder.textView;
            ImageView imageView = holder.imageView;
            textView.setText(flagOutItem.getDetailName());
            holder.price.setText(flagOutItem.getPrice());
            holder.delprice.setText(flagOutItem.getDelPrice());
//        imageView.setImageBitmap(flagOutItem.bitmap);
            dBitmap.display(imageView, flagOutItem.getImgUrl());
//        URL url = null;
//        try {
//            url = new URL(flagOutItem.getImgUrl());
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            InputStream is = connection.getInputStream();
//            Bitmap bitmap = BitmapFactory.decodeStream(is);
//            imageView.setImageBitmap(bitmap);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        }
        return view;
    }


    class ViewHolder {

        ImageView imageView;
        TextView textView;
        TextView price;
        TextView delprice;
    }

}
