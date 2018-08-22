package net.bither.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class CompressFileUtils {
    public static String SDPATH = Environment.getExternalStorageDirectory() + "/huangxiaoguo/";

    public static String createFile(Context context, String picName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dirFile = new File(SDPATH);  //目录转化成文件夹
            if (!dirFile.exists()) {  //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }
            // 在SDcard的目录下创建图片文,以当前时间为其命名
            File file = new File(SDPATH, picName + ".jpg");
            return file.getAbsolutePath();
        } else {
            File file = new File(context.getExternalCacheDir(), picName + ".jpg");
            return file.getAbsolutePath();
        }

    }
}
