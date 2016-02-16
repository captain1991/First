package org.xiaodong.dui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * ScrollView和ListView，解决冲突问题。
 * @author yinxiaodong
 *
 */
public class KJScrollListView extends ListView {

	public KJScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public KJScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public KJScrollListView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
