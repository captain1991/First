package com.xiaodong.tu8me;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.xiaodong.tu8me.vo.FlagOutItem;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    public MyService() {
    }

    public class MyBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }
    public MyBinder mBinder = new MyBinder();
    List<FlagOutItem> flagOutItems = new ArrayList<FlagOutItem>();
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("====onbind====","=====onbind====");
        flagOutItems = (List<FlagOutItem>) intent.getSerializableExtra("flagoutitem");
        return mBinder;
    }
//    public Context context = getApplicationContext();
    public void sendNotification(){
        Intent mIntent = new Intent(getApplicationContext(),GoodsActivity.class);
        mIntent.putExtra(MainActivity.GOODSURL,flagOutItems.get(3).getClickUrl());
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),1,mIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(getApplicationContext()).setTicker("servicenotify").setSmallIcon(R.mipmap.icon_new)
                .setContentTitle("消息标题").setContentText("消息内容").setContentIntent(pendingIntent).build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Log.e("send","send");
        manager.notify(0,notification);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("====onUnbind====","=====onUnbind====");
        return super.onUnbind(intent);
    }
}
