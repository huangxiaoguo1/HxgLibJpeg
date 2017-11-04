package tsou.cn.imagecompresscommon.utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class UIUtils {
    /**
     * dip转换px
     */
    public static int dip2px(Context context,int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
