package tsou.cn.imagecompresscommon.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import tsou.cn.imagecompresscommon.R;


/**
 * 图片加载类封装
 *
 * @author RS
 */
public class ImageLoadUtil {

    public static ImageView display(Context context, ImageView img, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.app_loading_pic) //加载中的图片
                .error(R.drawable.app_loading_pic) //加载失败的图片
                .into(img);
        return img;
    }

    public static ImageView display(Context context, ImageView img, File file) {
        Glide.with(context)
                .load(file)
                .placeholder(R.drawable.app_loading_pic) //加载中的图片
                .error(R.drawable.app_loading_pic) //加载失败的图片
                .into(img);
        return img;
    }

    public static ImageView displayCircle(Context context, ImageView img, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.app_loading_pic_round) //加载中的图片
                .error(R.drawable.app_loading_pic_round) //加载失败的图片
                .override(150, 150)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(img);
        return img;
    }

    public static ImageView displayCircle(Context context, ImageView img, File file) {
        Glide.with(context)
                .load(file)
                .override(150, 150)
                .placeholder(R.drawable.app_loading_pic_round) //加载中的图片
                .error(R.drawable.app_loading_pic_round) //加载失败的图片
                .bitmapTransform(new CropCircleTransformation(context))
                .into(img);
        return img;
    }

    public static ImageView displayCircle(Context context, ImageView img, String url, @DrawableRes int defaultPic) {
        Glide.with(context)
                .load(url)
                .override(150, 150)
                .placeholder(defaultPic) //加载中的图片
                .error(defaultPic) //加载失败的图片
                .bitmapTransform(new CropCircleTransformation(context))
                .into(img);
        return img;
    }

    public static ImageView displayRound(Context context, ImageView img, String url, int round, int width, int height) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.app_loading_pic) //加载中的图片
                .error(R.drawable.app_loading_pic) //加载失败的图片
                .bitmapTransform(new CropTransformation(context, width, height),
                        new RoundedCornersTransformation(context, UIUtils.dip2px(context.getApplicationContext(), round), 0))
                .into(img);
        return img;
    }

    public static ImageView displaySquareRound(Context context, ImageView img, String url, int round, int length) {
        displayRound(context, img, url, round, length, length);
        return img;
    }

    public static ImageView displaySquareRound(Context context, ImageView img, String url, int length) {
        displaySquareRound(context, img, url, 4, length);
        return img;
    }

    /****************************/
    public static void display(Context context, String imgPath, ImageView imageView) {
        display(context, imgPath, imageView, R.drawable.app_loading_pic);
    }

    public static void display(Context context, String imgPath, ImageView imageView, @DrawableRes int resId) {
        Glide.with(context).load(imgPath).error(resId)
                .placeholder(resId) //加载中的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void display(Context context, @DrawableRes int imgPath, ImageView imageView) {
        display(context, imgPath, imageView, R.drawable.app_loading_pic);
    }

    public static void display(Context context, @DrawableRes int imgPath, ImageView imageView, @DrawableRes int resId) {
        Glide.with(context).load(imgPath).error(resId)
                .placeholder(resId) //加载中的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void display(Context context, String imgPath, ImageView imageView, int width, int height) {
        display(context, imgPath, imageView, R.drawable.app_loading_pic, width, height);
    }

    public static void display(Context context, String imgPath, ImageView imageView, @DrawableRes int resId, int width,
                               int height) {
        Glide.with(context).load(imgPath).error(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(width, height)
                .placeholder(resId) //加载中的图片
                .into(imageView);
    }

    public static void clearMemory(final Context mContext) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(mContext).clearDiskCache();//清理磁盘缓存需要在子线程中执行
            }
        }).start();
        Glide.get(mContext).clearMemory();//清理内存缓存可以在UI主线程中进行
    }

}
