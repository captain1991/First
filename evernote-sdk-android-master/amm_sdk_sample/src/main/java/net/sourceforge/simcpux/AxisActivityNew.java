package net.sourceforge.simcpux;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.SeekBar;

import net.sourceforge.simcpux.view.PanelView;
import net.sourceforge.simcpux.view.SimpleLineChart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/7.
 */
public class AxisActivityNew extends Activity {
    SimpleLineChart simpleLine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.axis_layout);
        simpleLine = (SimpleLineChart) findViewById(R.id.simpleLine);

        mHandler.sendEmptyMessageDelayed(1,1);
//        SimpleLineChart mSimpleLineChart = (SimpleLineChart) findViewById(R.id.simpleLine);
//        String[] xItem = {"1","2","3","4","5","6","7"};
//        String[] yItem = {"10k","20k","30k","40k","50k"};
//        if(mSimpleLineChart == null)
//            Log.e("wing", "null!!!!");
//        mSimpleLineChart.setXItem(xItem);
//        mSimpleLineChart.setYItem(yItem);
//        HashMap<Integer,Integer> pointMap = new HashMap();
//        for(int i = 0;i<xItem.length;i++){
//            pointMap.put(i, (int) (Math.random()*5));
//        }
//        mSimpleLineChart.setData(pointMap);
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            simpleLine.setXItem(new String[]{"1", "2", "3", "4", "5"});
            simpleLine.setYItem(new String[]{"10", "20", "30", "40", "50"});
            Map<Integer,Integer> pointMap = new HashMap<Integer,Integer>();
            for(int i = 0;i<5;i++){
                pointMap.put(i, (int) (Math.random()*5));
            }
            simpleLine.setData((HashMap<Integer, Integer>) pointMap);
        }
    };

}
