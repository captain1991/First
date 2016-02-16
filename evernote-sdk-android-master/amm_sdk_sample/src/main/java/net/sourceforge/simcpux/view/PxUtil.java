package net.sourceforge.simcpux.view;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Administrator on 2016/1/8.
 */
public class PxUtil {
    public static int dpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(int sp,Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

}
