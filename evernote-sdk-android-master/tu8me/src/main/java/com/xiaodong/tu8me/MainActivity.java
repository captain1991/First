package com.xiaodong.tu8me;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.dataprovider.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xiaodong.tu8me.adapter.FlagOutAdapter;
import com.xiaodong.tu8me.vo.FlagOutItem;
import com.ypy.eventbus.EventBus;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xiaodong.dui.KJListView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import libcore.io.DiskLruCache;
import libcore.io.DiskLruUtils;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, KJListView.KJListViewListener {
    public static final int EVENT_TEXT_REFRESH = 1;
    public static final int EVENT_TEXT_LOADMORE = 2;
    public static final int EVENT_CONNECT_FAILED = 3;
    public List<FlagOutItem> flagOutItems = new ArrayList<FlagOutItem>();
    public FlagOutItem flagOutitem;
    private KJListView listView;
    public FlagOutAdapter adapter;
    public LinearLayout left_layout;
    public DrawerLayout drawerLayout;
    public List<FlagOutItem> tempflagOutItems = new ArrayList<FlagOutItem>();
    //    private ImageView leftImage;
    private Boolean isopend = false;
    private Context context;
    FloatingActionButton fab;
    public Tencent mtencent;
    public MuiListener muiListener;
    public TextView chiep;
    public TextView text_girl_shoes;
    public TextView package_gril;
    public TextView neiyi;
    public TextView text_huazhuangpin;
    private String sortId = "";
    private String baseURL = "";
    int page = 1;
    private ImageView progress;
    private IWXAPI api;
    private RelativeLayout share_layout;
    private Animation share_in;
    private Animation share_out;
    private Animation fab_up;
    private Animation fab_down;
    private boolean isUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        api = WXAPIFactory.createWXAPI(this,"wx16db892e53f30664",true);
        api.registerApp("wx16db892e53f30664");

        mtencent = Tencent.createInstance("1104951525", this.getApplicationContext());
        muiListener = new MuiListener();
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        share_in = AnimationUtils.loadAnimation(this,R.anim.share_in);
        share_out = AnimationUtils.loadAnimation(this,R.anim.share_out);
        fab_up = AnimationUtils.loadAnimation(this,R.anim.fab_up);
        fab_down = AnimationUtils.loadAnimation(this,R.anim.fab_dowm);
        share_layout = (RelativeLayout) findViewById(R.id.share_layout);
        share_layout.setVisibility(View.GONE);
        progress = (ImageView) findViewById(R.id.progressbar);
        chiep = (TextView) findViewById(R.id.chiep);
        text_girl_shoes = (TextView) findViewById(R.id.text_girl_shoes);
        package_gril = (TextView) findViewById(R.id.package_gril);
        neiyi = (TextView) findViewById(R.id.neiyi);
        text_huazhuangpin = (TextView) findViewById(R.id.text_huazhuangpin);
//        leftImage = (ImageView) findViewById(R.id.left_img);
        left_layout = (LinearLayout) findViewById(R.id.left_drawer_layout);
        listView = (KJListView) findViewById(R.id.flag_out);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setKJListViewListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        adapter = new FlagOutAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                shareToQQ();
//                sendWxUrl(0);
                workswevice();
                share_layout.setVisibility(View.VISIBLE);
                if (isUp) {
                    share_layout.startAnimation(share_out);
                    mHandler.sendEmptyMessageDelayed(1, 300);
                    isUp = false;
                } else {
                    share_layout.startAnimation(share_in);
                    isUp = true;
                }
            }
        });
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
        baseURL = "http://www.tu8.me/index.php?m=wap&a=index&p=";
        initDataFromCacheOrNet();

    }
//    public void sendNotification(){
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        PendingIntent pendingIntent3 = PendingIntent.getActivity(this, 0,
//                new Intent(this, MainActivity.class), 0);
//        Notification notify3 = new Notification.Builder(this)
//                .setSmallIcon(R.drawable.icon48_appwx_logo)
//                .setTicker("TickerText:" + "您有新短消息，请注意查收！")
//                .setContentTitle("Notification Title")
//                .setContentText("This is the notification message")
//                .setContentIntent(pendingIntent3).setNumber(1).build();
//        notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
//        manager.notify(1, notify3);
//    }
    public void workswevice(){
        Log.e("===workswevice========", "=========workswevice===");
        Intent intent = new Intent(this,MyService.class);
        intent.putExtra("flagoutitem", (Serializable) flagOutItems);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.MyBinder myBinder = (MyService.MyBinder) iBinder;
            MyService service = myBinder.getService();
            service.sendNotification();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
//分享按钮
    public void share(View v){
        if (v.getId()==R.id.wx_frend){
            sendWxUrl(0);
        }else if(v.getId()==R.id.wx_moments){
            sendWxUrl(1);
        }else if(v.getId()==R.id.wx_collect){
            sendWxUrl(2);
        }else if(v.getId()==R.id.qq_friend){
            shareToQQ();
        }
    }

    public void initDataFromCacheOrNet() {
        progress.setVisibility(View.VISIBLE);
        AnimationDrawable anim = (AnimationDrawable) progress.getDrawable();
        anim.start();
        final File file = DiskLruUtils.getDiskCacheDir(context, "response");
//先判断是否有缓存，有就先加载缓存，没有就写入缓存
        if (file.exists() && getSnapshot(file) != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    initCacheData(getSnapshot(file));
                }
            }).start();
        } else {
            doCache();
            initData("refresh");
        }
    }

    public void leftClick(View view) {
        page = 1;
        drawerLayout.closeDrawer(left_layout);
//        进入不同模块要先清空
        tempflagOutItems.clear();
        switch (view.getId()) {
            case R.id.chiep:
                sortId = "";
                initDataFromCacheOrNet();
                break;
            case R.id.text_girl_shoes:
                sortId = "&sort_id=7";
                initDataFromCacheOrNet();
                break;
            case R.id.package_gril:
                sortId = "&sort_id=11";
                initDataFromCacheOrNet();
                break;
            case R.id.neiyi:
                sortId = "&sort_id=9";
                initDataFromCacheOrNet();
                break;
            case R.id.text_huazhuangpin:
                sortId = "&sort_id=10";
                initDataFromCacheOrNet();
                break;
            default:
                break;
        }

    }

    public void initData(final String type) {
        if (type.equals("refresh")) {
            listView.setPullLoadEnable(false);
        }

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Document document = null;
                try {
                    document = Jsoup.connect(baseURL + page + sortId).timeout(5000).get();
//                    Log.e("===doc=====", document.toString());
                    Elements elements = document.getElementsByClass("flag_out");
                    flagOutItems.clear();
//                    Log.e("===elements=====",""+elements.size());
                    for (Element e : elements) {
                        flagOutitem = new FlagOutItem();
//                        Log.e("===elements=====",e.toString());
                        Element link = e.select("a").first();
                        Element img = e.select("img").first();
                        Element delPrice = e.select("del").first();
                        Elements price = e.getElementsByClass("price");
                        String priceText = price.get(0).text();
                        String delPricetext = delPrice.text();
                        String imgHref = img.attr("src");
                        String linkHref = link.attr("href");
                        Elements ele = e.getElementsByClass("pad10");
                        Element element = ele.get(0);
                        flagOutitem.setPrice(priceText);
                        flagOutitem.setDelPrice(delPricetext);
                        flagOutitem.setClickUrl(linkHref);
                        flagOutitem.setImgUrl(imgHref);
                        flagOutitem.setDetailName(element.text());
                        flagOutItems.add(flagOutitem);

                    }
//                    listView.setPullLoadEnable(true);
                    tempflagOutItems.addAll(flagOutItems);
                    if (type.equals("refresh")) {
                        EventBus.getDefault().post(
                                MainActivity.EVENT_TEXT_REFRESH);
                    } else {
                        EventBus.getDefault().post(
                                MainActivity.EVENT_TEXT_LOADMORE);
                    }
                } catch (IOException e) {
                    EventBus.getDefault().post(
                            MainActivity.EVENT_CONNECT_FAILED);
                }
            }
        });
        thread.start();
    }

    public boolean checkIsdataChanged(File file){
        String html =  getCatchData(getSnapshot(file));
        List<String> strings = new ArrayList<String>();
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByClass("flag_out");
        for (Element element:elements){
            Element link = element.select("a").first();
            String linkhref = link.attr("href");
            strings.add(linkhref);
        }
        if(!(strings.get(0).equals(flagOutItems.get(0).getClickUrl()))){
            return true;
        }
        return false;
    }

    public void doCache() {
        final File file = DiskLruUtils.getDiskCacheDir(context, "response");
        if (!file.exists()) {
            file.mkdir();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DiskLruCache cache = DiskLruCache.open(file, DiskLruUtils.getAppVersion(context), 1, 50 * 1024 * 1024);
                    String key = DiskLruUtils.hashKeyForDisk(baseURL + sortId);
//                    先移除已经存在的缓存
                    cache.remove(key);
                    DiskLruCache.Editor editor = cache.edit(key);
                    OutputStream os = editor.newOutputStream(0);
                    if (output(os)) {
                        editor.commit();
                    } else {
                        editor.abort();
                    }
                    os.flush();
                    os.close();
                    cache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public Boolean output(OutputStream os) {
        try {
            URL url = new URL(baseURL + "1" + sortId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream(), 1024 * 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024 * 1024);
            int i;
            while ((i = bis.read()) != -1) {
                bos.write(i);
            }
            bos.close();
            bis.close();
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public DiskLruCache.Snapshot getSnapshot(File file) {
        DiskLruCache cache = null;
        try {
            cache = DiskLruCache.open(file, DiskLruUtils.getAppVersion(context), 1, 50 * 1024 * 1024);
            String key = DiskLruUtils.hashKeyForDisk(baseURL + sortId);
            DiskLruCache.Snapshot snapshot = cache.get(key);
            return snapshot;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initCacheData(DiskLruCache.Snapshot snapshot) {
        String html = getCatchData(snapshot);
        Document doc = Jsoup.parse(html);
//        Log.e("===doc=====", doc.toString());
        Elements elements = doc.getElementsByClass("flag_out");
        flagOutItems.clear();
        for (Element e : elements) {
            flagOutitem = new FlagOutItem();
            Element link = e.select("a").first();
            Element img = e.select("img").first();
            Element delPrice = e.select("del").first();
            Elements price = e.getElementsByClass("price");
            String priceText = price.get(0).text();
            String delPricetext = delPrice.text();
            String imgHref = img.attr("src");
            String linkHref = link.attr("href");
            Elements ele = e.getElementsByClass("pad10");
            Element element = ele.get(0);
            flagOutitem.setPrice(priceText);
            flagOutitem.setDelPrice(delPricetext);
            flagOutitem.setClickUrl(linkHref);
            flagOutitem.setImgUrl(imgHref);
            flagOutitem.setDetailName(element.text());
            flagOutItems.add(flagOutitem);
        }
//                    listView.setPullLoadEnable(true);
        tempflagOutItems.addAll(flagOutItems);

        EventBus.getDefault().post(
                MainActivity.EVENT_TEXT_REFRESH);

    }

    public String getCatchData(DiskLruCache.Snapshot snapshot){
        String html=null;
        InputStream in = snapshot.getInputStream(0);
        InputStreamReader isReader = new InputStreamReader(in);
        BufferedReader bfReader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String line;
        try {
            while ((line = bfReader.readLine()) != null) {
                sb.append(line);
            }
            html = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    public void onEventMainThread(Integer i) {
        progress.setVisibility(View.GONE);
        switch (i) {
            case MainActivity.EVENT_TEXT_REFRESH:
                listView.stopRefreshData();
                listView.setPullLoadEnable(true);
                adapter.setFlagOutItems(flagOutItems);
                File file = DiskLruUtils.getDiskCacheDir(context, "response");
                if(checkIsdataChanged(file)){
                 doCache();
                }
                break;
            case MainActivity.EVENT_TEXT_LOADMORE:
                listView.stopLoadMore();
                adapter.setFlagOutItems(tempflagOutItems);
                break;
            case MainActivity.EVENT_CONNECT_FAILED:
//                Toast.makeText(context,"网络差",Toast.LENGTH_SHORT).show();
                listView.stopLoadMore();
                listView.stopRefreshData();
                Snackbar.make(fab, "没有网络连接", Snackbar.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        api.unregisterApp();
        super.onDestroy();
    }

    public static final String GOODSURL = "goodsurl";
    boolean isNeted = false;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ConnectivityManager manger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manger != null) {
            NetworkInfo[] networkInfos = manger.getAllNetworkInfo();
            if (networkInfos != null && networkInfos.length > 0)
                for (NetworkInfo info : networkInfos) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        isNeted = true;
                    }
                }
        }
        if (isNeted) {
            if (i > 2) {
                Intent mIntent = new Intent(this, GoodsActivity.class);
                mIntent.putExtra(MainActivity.GOODSURL, tempflagOutItems.get(i - 3).getClickUrl());
                startActivity(mIntent);
            }
        }
    }

    @Override
    public void onRefresh() {
//        long time = System.currentTimeMillis();
        page = 1;
        tempflagOutItems.clear();
        String time = new SimpleDateFormat("MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        listView.setRefreshTime(time);
        initData("refresh");
    }

    @Override
    public void onLoadMore() {
        page++;
        initData("loadmore");
    }

    public void shareToQQ() {
        final Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        // 这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.tu8.me/index.php?m=wap&p=1");
//				"http://connect.qq.com/");
        // 分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_ SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, "我在测试标题");
        // 分享的图片URL
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, flagOutItems.get(0).getImgUrl());
//				"http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
        // 分享的消息摘要，最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, "测试正文内容");
//		 手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        // 标识该消息的来源应用，值为应用名称+AppId。
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试分享");

        mtencent.shareToQQ(MainActivity.this, bundle, muiListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, muiListener);
    }

    class MuiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendWxUrl(int scene) {

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "www.tu8.me";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "分享微信测试";
        msg.description = "分享微信测试descri";
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction(webpage);
        req.message = msg;
        if (scene == 0) {
// 分享到微信会话
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if(scene == 1){
// 分享到微信朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }else{
// 微信收藏（我猜到的）
            req.scene = 2;
        }
        api.sendReq(req);
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    share_layout.clearAnimation();
                    share_layout.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    //获得bitmap，体验效果不好，不需要
    class MyTask extends AsyncTask<List<FlagOutItem>, Void, List<FlagOutItem>> {
        @Override
        protected List<FlagOutItem> doInBackground(List<FlagOutItem>... lists) {
            List<FlagOutItem> flagOutItems1 = lists[0];
            for (FlagOutItem flagOutItem : flagOutItems1) {
                Bitmap bitmap = null;
                try {
                    URL url = new URL(flagOutItem.getImgUrl());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream is = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                flagOutItem.setBitmap(bitmap);
            }

            return flagOutItems1;
        }

        @Override
        protected void onPostExecute(List<FlagOutItem> flos) {
            Log.e("====newflagOutItem====", "" + flos.size());

        }
    }

}
