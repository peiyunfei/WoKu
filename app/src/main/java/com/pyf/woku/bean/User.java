package com.pyf.woku.bean;

/**
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/25
 */

public class User {
    /**
     * ecode : 0
     * emsg :
     * data : {"userId":"0001","photoUrl":"","name":"任志强","tick":"任志强","mobile":"18734924592","platform":"youku"}
     */

    public int ecode;
    public String emsg;
    public DataBean data;

    public static class DataBean {
        /**
         * userId : 0001
         * photoUrl :
         * name : 任志强
         * tick : 任志强
         * mobile : 18734924592
         * platform : youku
         */

        public String userId;
        public String photoUrl;
        public String name;
        public String tick;
        public String mobile;
        public String platform;
    }
}
