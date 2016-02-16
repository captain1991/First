package com.xiaodong.tu8me.vo;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/22.
 */
public class FlagOutItem implements Serializable{

    public String clickUrl;
    public String imgUrl;
    public String detailName;
    public String price;
    public String delPrice;
    public Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDelPrice() {
        return delPrice;
    }

    public void setDelPrice(String delPrice) {
        this.delPrice = delPrice;
    }
}
