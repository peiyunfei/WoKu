package com.pyf.woku.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.pyf.woku.bean.Home;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Util {

    public static Bitmap createQRCode(int width, int height, String source) {
        try {
            if (TextUtils.isEmpty(source)) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(source, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // sheng chen de er wei ma
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName(Context context) {
        String versionName = "1.0.0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static Uri createQQUrl(String qq) {
        String result = "mqqwpa://im/chat?chat_type=wpa&uin=".concat(qq);
        return Uri.parse(result);
    }

    //为ViewPager结构化数据
    public static List<Home.DataBean.ListBean> handleData(Home.DataBean.ListBean value) {
        List<Home.DataBean.ListBean> values = new ArrayList<>();
        String[] titles = value.getTitle().split("@");
        String[] infos = value.getInfo().split("@");
        String[] prices = value.getPrice().split("@");
        String[] texts = value.getText().split("@");
        List<String> urls = value.getUrl();
        int start = 0;
        for (int i = 0; i < titles.length; i++) {
            Home.DataBean.ListBean tempValue = new Home.DataBean.ListBean();
            tempValue.setText(titles[i]);
            tempValue.setInfo(infos[i]);
            tempValue.setPrice(prices[i]);
            tempValue.setText(texts[i]);
            tempValue.setUrl(extractData(urls, start, 3));
            start += 3;
            values.add(tempValue);
        }
        return values;
    }

    private static ArrayList<String> extractData(List<String> source, int start, int interval) {
        ArrayList<String> tempUrls = new ArrayList<>();
        for (int i = start; i < start + interval; i++) {
            tempUrls.add(source.get(i));
        }
        return tempUrls;
    }

    /**
     * 这个是真正的获取指定包名的应用程序是否在运行(无论前台还是后台)
     */
    public static boolean getCurrentTask(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> appProcessInfos = activityManager.getRunningTasks(50);
        for (ActivityManager.RunningTaskInfo process : appProcessInfos) {

            if (process.baseActivity.getPackageName().equals(context.getPackageName())
                    || process.topActivity.getPackageName().equals(context.getPackageName())) {

                return true;
            }
        }
        return false;
    }

    /**
     * 显示系统软件盘
     * 传入的View必须是EditText及其子类才可以强制显示出
     */
    public static void showSoftInputMethod(Context context, View v) {
        /* 隐藏软键盘 */
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInputMethod(Context context, View v) {
        /* 隐藏软键盘 */
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void hasError() {
        String error = "error";
        Log.e("Util", error);
    }
}
