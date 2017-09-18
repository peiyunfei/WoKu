package com.pyf.woku.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pyf.woku.R;

/**
 * 图片请求框架的封装
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/17
 */
public class ImageLoaderManager {

    // 线程池的大小
    private static final int THREAD_COUNT = 4;
    // 线程优先级
    private static final int PRIORITY = 2;
    // 内存缓存的大小
    private static final int MEMORY_CACHE_SIZE = 2 * 1024 * 1024;
    // 磁盘缓存的大小
    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    // 连接超时时间
    private static final int CONNECTION_TIME_OUT = 5 * 1000;
    // 读取超时时间
    private static final int READ_TIME_OUT = 30 * 1000;

    private static ImageLoaderManager mInstance = null;
    private static ImageLoader mLoader = null;

    public static ImageLoaderManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ImageLoaderManager.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 私有构造方法完成初始化工作
     */
    private ImageLoaderManager(Context context) {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                // 线程池的大小
                .threadPoolSize(THREAD_COUNT)
                // 线程优先级
                .threadPriority(Thread.NORM_PRIORITY - PRIORITY)
                // 禁止缓存多张相同的图片
                .denyCacheImageMultipleSizesInMemory()
                // 内存缓存的大小
                .memoryCache(new UsingFreqLimitedMemoryCache(MEMORY_CACHE_SIZE))
                // 使用弱引用
                .memoryCache(new WeakMemoryCache())
                // 磁盘缓存的大小
                .diskCacheSize(DISK_CACHE_SIZE)
                // 将保存的时候的URI名称用MD5 加密
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                // 加载方式，后进先出
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 设置图片加载参数
                .defaultDisplayImageOptions(getDefaultOptions())
                // 设置图片下载器
                .imageDownloader(new BaseImageDownloader(context, CONNECTION_TIME_OUT, READ_TIME_OUT))
                // 测试模式下输出日志
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
        mLoader = ImageLoader.getInstance();
    }

    /**
     * 显示图片
     */
    public void displayImage(ImageView imageView, String path, ImageLoadingListener listener) {
        if (mLoader != null) {
            mLoader.displayImage(path, imageView, listener);
        }
    }

    /**
     * 显示图片
     */
    public void displayImage(ImageView imageView, String path) {
        displayImage(imageView, path, null);
    }

    /**
     * 默认的图片显示Options,可设置图片的缓存策略，编解码方式等，非常重要
     */
    private DisplayImageOptions getDefaultOptions() {
        DisplayImageOptions options = new
                DisplayImageOptions.Builder()
                // url为空时加载的图片
                .showImageForEmptyUri(R.drawable.default_user_avatar)
                // 加载失败显示的图片
                .showImageOnFail(R.drawable.default_user_avatar)
                // 设置下载的图片是否缓存在内存中, 重要，否则图片不会缓存到内存中
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在SD卡中, 重要，否则图片不会缓存到硬盘中
                .cacheOnDisk(true)
                // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .considerExifParams(true)
                // 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                // 设置图片的解码类型
                .bitmapConfig(Bitmap.Config.RGB_565)
                // 设置图片的解码配置
                .decodingOptions(new BitmapFactory.Options())
                // 设置图片在下载前是否重置，复位
                .resetViewBeforeLoading(true)
                .build();
        return options;
    }
}
