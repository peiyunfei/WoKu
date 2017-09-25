package com.pyf.woku.bean;

/**
 * 更新实体类
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/25
 */
public class Update {

    /**
     * ecode : 0
     * emsg :
     * data : {"currentVersion":2}
     */
    public int ecode;
    public String emsg;
    public DataBean data;

    public static class DataBean {

        public int currentVersion;
    }
}
