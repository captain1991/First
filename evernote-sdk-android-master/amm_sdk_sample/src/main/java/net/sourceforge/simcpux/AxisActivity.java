package net.sourceforge.simcpux;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import net.sourceforge.simcpux.view.PanelView;
import net.sourceforge.simcpux.view.SimpleLineChart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/7.
 */
public class AxisActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    PanelView panelView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_layout);
        panelView = (PanelView) findViewById(R.id.panel);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        seekBar.getProgress();
        panelView.setProgress( seekBar.getProgress());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
