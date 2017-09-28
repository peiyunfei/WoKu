package com.pyf.woku.constant;

import android.Manifest;
import android.os.Environment;

/**
 * 常量类
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/25
 */
public class Constant {

    /**
     * 权限常量相关
     */
    public static final int WRITE_READ_EXTERNAL_CODE = 0x01;
    /**
     * 读取磁盘和写入磁盘权限
     */
    public static final String[] WRITE_READ_EXTERNAL_PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final int HARDWEAR_CAMERA_CODE = 0x02;
    /**
     * 相机权限
     */
    public static final String[] HARDWEAR_CAMERA_PERMISSION = new String[]{
            Manifest.permission.CAMERA};

    /**
     * 整个应用文件下载保存路径
     */
    public static String APP_PHOTO_DIR = Environment.
            getExternalStorageDirectory().getAbsolutePath().
            concat("/WoKu/photo/");
}
