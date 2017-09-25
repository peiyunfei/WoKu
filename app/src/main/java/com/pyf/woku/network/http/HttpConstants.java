package com.pyf.woku.network.http;

/**
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/17
 */
public class HttpConstants {

    private static final String ROOT_URL = "http://192.168.1.228:8080/server";
    public static final String HOME = ROOT_URL + "/home_data.json";
    public static final String UPDATE = ROOT_URL + "/update.json";
    /**
     * 请求本地产品列表
     */
    public static String PRODUCT_LIST = ROOT_URL + "/fund/search.php";

    /**
     * 本地产品列表更新时间措请求
     */
    public static String PRODUCT_LATESAT_UPDATE = ROOT_URL + "/fund/upsearch.php";

    /**
     * 登陆接口
     */
    public static String LOGIN = ROOT_URL + "/user/login_phone.php";

    /**
     * 检查更新接口
     */
    public static String CHECK_UPDATE = ROOT_URL + "/config/check_update.php";

    /**
     * 首页产品请求接口
     */
    public static String HOME_RECOMMAND = ROOT_URL + "/product/home_recommand.php";
}
