package com.xiaodong.myview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class BitmapReginDecoderActivity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_regin_decoder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        imageView = (ImageView) findViewById(R.id.test_img);
//        imageView.setImageResource(R.drawable.desert);
//        initImg();
    }

    private void initImg(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.desert);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        InputStream is = new ByteArrayInputStream(bos.toByteArray());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), R.drawable.desert, options);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        int biwidth = options.outWidth;
        int biheight = options.outHeight;

        try {
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is,false);
            BitmapFactory.Options mOptions = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap1 = decoder.decodeRegion(new Rect(biwidth/2-width/2, biheight/2-height/2, biwidth/2+width/2 , biheight/2+height/2), mOptions);
            imageView.setImageBitmap(bitmap1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
