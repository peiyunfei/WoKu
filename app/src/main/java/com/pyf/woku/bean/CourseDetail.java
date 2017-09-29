package com.pyf.woku.bean;

import java.util.List;

/**
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/29
 */

public class CourseDetail {

    /**
     * ecode : 0
     * emsg :
     * data : {"head":{"text":"在当今的互联网时代，数据是一切应用的核心和基础，有数据的地方就存在安全隐患。在Java领域，我们可以使用Java加密解密技术来提高数据的安全性。本计划从基础入手，为小伙伴们带来了多种Java加解密算法的实现。还等什么，赶快来参加吧！","name":"狼在北京","logo":"http://img3.duitang.com/uploads/item/201407/01/20140701222607_AnKfj.thumb.224_0.jpeg","photoUrls":["http://img.mukewang.com/5465af0c0001bb6706000338-590-330.jpg","http://edu.sxgov.cn/image/attachement/png/site2/20160125/5cac4c73d2dd1810106661.png","http://ww1.sinaimg.cn/large/c5131475jw1ez7hdfvu87j20fk078myc.jpg"],"oldPrice":"￥30.00","newPrice":"￥15.00","zan":"3人点赞","scan":"506人浏览","hotComment":"热门留言(7)","from":"来自北京","dayTime":"3天前","video":{"resource":"http://fairee.vicp.net:83/2016rm/0116/baishi160116.mp4","adid":"00000001112","clickUrl":"http://www.imooc.com/","clickMonitor":[{"ver":"0","url":"http://imooc.com/click??click=1"},{"ver":"0","url":"http://imooc.com/click?click=2"}],"startMonitor":[{"ver":"0","url":"http://imooc.com/show?impression=1"},{"ver":"0","url":"http: //imooc.com/show?impression=2"}],"middleMonitor":[{"ver":"0","url":"http://imooc.com/show?SU=1","time":5},{"ver":"0","url":"http: //imooc.com/show?impression=2","time":5}],"endMonitor":[{"ver":"0","url":"http://imooc.com/show?end=1","time":5},{"ver":"0","url":"http: //imooc.com/show?end=2","time":5}]}},"body":[{"type":0,"name":"狼在北京","logo":"http://img3.duitang.com/uploads/item/201407/01/20140701222607_AnKfj.thumb.224_0.jpeg","text":"回复@偏执的柔情:多谢支持，继续支持我哈","userId":"0001"},{"type":1,"name":"偏执的柔情","logo":"http://tupian.qqjay.com/tou3/2016/0619/33ce4ff3e468e1c27535ea0c9cb424d5.jpg","text":"课程非常好","userId":"0002"},{"type":1,"name":"哈雷路亚","logo":"http://img2.imgtn.bdimg.com/it/u=477212778,1276839182&fm=21&gp=0.jpg","text":"良心课程,老师多多出课啊","userId":"0003"},{"type":1,"name":"麿麿816","logo":"http://img1.2345.com/duoteimg/qqTxImg/2/785e8d5ad17c0fadcc9595b4795d4e20.png","text":"老师学习遇到点问题，可以加您QQ吗","userId":"0004"},{"type":0,"name":"狼在北京","logo":"http://img3.duitang.com/uploads/item/201407/01/20140701222607_AnKfj.thumb.224_0.jpeg","text":"回复@jialiyuan333:可以的。","userId":"0001"},{"type":1,"name":"jialiyuan333","logo":"http://www.th7.cn/d/file/p/2016/07/26/22cfcf2f8cd0906976e99ec2a7434e92.jpg","text":"老师有没有demo,发我一份"},{"type":1,"name":"帅帅","logo":"http://img0.pconline.com.cn/pconline/1308/07/3417256_co130i1141025-171.jpg","text":"求源码,求源码,求源码","userId":"0005"}],"footer":{"recommand":[{"imageUrl":"http://it.enorth.com.cn/pic2014/002/000/092/00200009288_502cc21c.png","name":"5天学会IM","price":"￥23","zan":"3赞    5评论","courseId":"01112"},{"imageUrl":"http://img1.cache.netease.com/hebei/2014/3/25/20140325092345399eb_550.jpg","name":"MySQL从入门到精通","price":"￥54","zan":"10赞   4评论","courseId":"01114"}],"time":[{"dt":"1473264000","count":"40"},{"dt":"1473177600","count":"30"},{"dt":"1473091200","count":"70"},{"dt":"1473004800","count":"90"},{"dt":"1472918400","count":"70"},{"dt":"1472745600","count":"101"},{"dt":"1472659200","count":"20"}]}}
     */

    public String ecode;
    public String emsg;
    public DataBean data;

    public static class DataBean {
        /**
         * head : {"text":"在当今的互联网时代，数据是一切应用的核心和基础，有数据的地方就存在安全隐患。在Java领域，我们可以使用Java加密解密技术来提高数据的安全性。本计划从基础入手，为小伙伴们带来了多种Java加解密算法的实现。还等什么，赶快来参加吧！","name":"狼在北京","logo":"http://img3.duitang.com/uploads/item/201407/01/20140701222607_AnKfj.thumb.224_0.jpeg","photoUrls":["http://img.mukewang.com/5465af0c0001bb6706000338-590-330.jpg","http://edu.sxgov.cn/image/attachement/png/site2/20160125/5cac4c73d2dd1810106661.png","http://ww1.sinaimg.cn/large/c5131475jw1ez7hdfvu87j20fk078myc.jpg"],"oldPrice":"￥30.00","newPrice":"￥15.00","zan":"3人点赞","scan":"506人浏览","hotComment":"热门留言(7)","from":"来自北京","dayTime":"3天前","video":{"resource":"http://fairee.vicp.net:83/2016rm/0116/baishi160116.mp4","adid":"00000001112","clickUrl":"http://www.imooc.com/","clickMonitor":[{"ver":"0","url":"http://imooc.com/click??click=1"},{"ver":"0","url":"http://imooc.com/click?click=2"}],"startMonitor":[{"ver":"0","url":"http://imooc.com/show?impression=1"},{"ver":"0","url":"http: //imooc.com/show?impression=2"}],"middleMonitor":[{"ver":"0","url":"http://imooc.com/show?SU=1","time":5},{"ver":"0","url":"http: //imooc.com/show?impression=2","time":5}],"endMonitor":[{"ver":"0","url":"http://imooc.com/show?end=1","time":5},{"ver":"0","url":"http: //imooc.com/show?end=2","time":5}]}}
         * body : [{"type":0,"name":"狼在北京","logo":"http://img3.duitang.com/uploads/item/201407/01/20140701222607_AnKfj.thumb.224_0.jpeg","text":"回复@偏执的柔情:多谢支持，继续支持我哈","userId":"0001"},{"type":1,"name":"偏执的柔情","logo":"http://tupian.qqjay.com/tou3/2016/0619/33ce4ff3e468e1c27535ea0c9cb424d5.jpg","text":"课程非常好","userId":"0002"},{"type":1,"name":"哈雷路亚","logo":"http://img2.imgtn.bdimg.com/it/u=477212778,1276839182&fm=21&gp=0.jpg","text":"良心课程,老师多多出课啊","userId":"0003"},{"type":1,"name":"麿麿816","logo":"http://img1.2345.com/duoteimg/qqTxImg/2/785e8d5ad17c0fadcc9595b4795d4e20.png","text":"老师学习遇到点问题，可以加您QQ吗","userId":"0004"},{"type":0,"name":"狼在北京","logo":"http://img3.duitang.com/uploads/item/201407/01/20140701222607_AnKfj.thumb.224_0.jpeg","text":"回复@jialiyuan333:可以的。","userId":"0001"},{"type":1,"name":"jialiyuan333","logo":"http://www.th7.cn/d/file/p/2016/07/26/22cfcf2f8cd0906976e99ec2a7434e92.jpg","text":"老师有没有demo,发我一份"},{"type":1,"name":"帅帅","logo":"http://img0.pconline.com.cn/pconline/1308/07/3417256_co130i1141025-171.jpg","text":"求源码,求源码,求源码","userId":"0005"}]
         * footer : {"recommand":[{"imageUrl":"http://it.enorth.com.cn/pic2014/002/000/092/00200009288_502cc21c.png","name":"5天学会IM","price":"￥23","zan":"3赞    5评论","courseId":"01112"},{"imageUrl":"http://img1.cache.netease.com/hebei/2014/3/25/20140325092345399eb_550.jpg","name":"MySQL从入门到精通","price":"￥54","zan":"10赞   4评论","courseId":"01114"}],"time":[{"dt":"1473264000","count":"40"},{"dt":"1473177600","count":"30"},{"dt":"1473091200","count":"70"},{"dt":"1473004800","count":"90"},{"dt":"1472918400","count":"70"},{"dt":"1472745600","count":"101"},{"dt":"1472659200","count":"20"}]}
         */

        public HeadBean head;
        public FooterBean footer;
        public List<BodyBean> body;

        public static class HeadBean {
            /**
             * text : 在当今的互联网时代，数据是一切应用的核心和基础，有数据的地方就存在安全隐患。在Java领域，我们可以使用Java加密解密技术来提高数据的安全性。本计划从基础入手，为小伙伴们带来了多种Java加解密算法的实现。还等什么，赶快来参加吧！
             * name : 狼在北京
             * logo : http://img3.duitang.com/uploads/item/201407/01/20140701222607_AnKfj.thumb.224_0.jpeg
             * photoUrls : ["http://img.mukewang.com/5465af0c0001bb6706000338-590-330.jpg","http://edu.sxgov.cn/image/attachement/png/site2/20160125/5cac4c73d2dd1810106661.png","http://ww1.sinaimg.cn/large/c5131475jw1ez7hdfvu87j20fk078myc.jpg"]
             * oldPrice : ￥30.00
             * newPrice : ￥15.00
             * zan : 3人点赞
             * scan : 506人浏览
             * hotComment : 热门留言(7)
             * from : 来自北京
             * dayTime : 3天前
             * video : {"resource":"http://fairee.vicp.net:83/2016rm/0116/baishi160116.mp4","adid":"00000001112","clickUrl":"http://www.imooc.com/","clickMonitor":[{"ver":"0","url":"http://imooc.com/click??click=1"},{"ver":"0","url":"http://imooc.com/click?click=2"}],"startMonitor":[{"ver":"0","url":"http://imooc.com/show?impression=1"},{"ver":"0","url":"http: //imooc.com/show?impression=2"}],"middleMonitor":[{"ver":"0","url":"http://imooc.com/show?SU=1","time":5},{"ver":"0","url":"http: //imooc.com/show?impression=2","time":5}],"endMonitor":[{"ver":"0","url":"http://imooc.com/show?end=1","time":5},{"ver":"0","url":"http: //imooc.com/show?end=2","time":5}]}
             */

            public String text;
            public String name;
            public String logo;
            public String oldPrice;
            public String newPrice;
            public String zan;
            public String scan;
            public String hotComment;
            public String from;
            public String dayTime;
            public VideoBean video;
            public List<String> photoUrls;

            public static class VideoBean {
                /**
                 * resource : http://fairee.vicp.net:83/2016rm/0116/baishi160116.mp4
                 * adid : 00000001112
                 * clickUrl : http://www.imooc.com/
                 * clickMonitor : [{"ver":"0","url":"http://imooc.com/click??click=1"},{"ver":"0","url":"http://imooc.com/click?click=2"}]
                 * startMonitor : [{"ver":"0","url":"http://imooc.com/show?impression=1"},{"ver":"0","url":"http: //imooc.com/show?impression=2"}]
                 * middleMonitor : [{"ver":"0","url":"http://imooc.com/show?SU=1","time":5},{"ver":"0","url":"http: //imooc.com/show?impression=2","time":5}]
                 * endMonitor : [{"ver":"0","url":"http://imooc.com/show?end=1","time":5},{"ver":"0","url":"http: //imooc.com/show?end=2","time":5}]
                 */

                public String resource;
                public String adid;
                public String clickUrl;
                public List<ClickMonitorBean> clickMonitor;
                public List<StartMonitorBean> startMonitor;
                public List<MiddleMonitorBean> middleMonitor;
                public List<EndMonitorBean> endMonitor;

                public static class ClickMonitorBean {
                    /**
                     * ver : 0
                     * url : http://imooc.com/click??click=1
                     */

                    public String ver;
                    public String url;
                }

                public static class StartMonitorBean {
                    /**
                     * ver : 0
                     * url : http://imooc.com/show?impression=1
                     */

                    public String ver;
                    public String url;
                }

                public static class MiddleMonitorBean {
                    /**
                     * ver : 0
                     * url : http://imooc.com/show?SU=1
                     * time : 5
                     */

                    public String ver;
                    public String url;
                    public int time;
                }

                public static class EndMonitorBean {
                    /**
                     * ver : 0
                     * url : http://imooc.com/show?end=1
                     * time : 5
                     */

                    public String ver;
                    public String url;
                    public int time;
                }
            }
        }

        public static class FooterBean {
            public List<RecommandBean> recommand;
            public List<TimeBean> time;

            public static class RecommandBean {
                /**
                 * imageUrl : http://it.enorth.com.cn/pic2014/002/000/092/00200009288_502cc21c.png
                 * name : 5天学会IM
                 * price : ￥23
                 * zan : 3赞    5评论
                 * courseId : 01112
                 */

                public String imageUrl;
                public String name;
                public String price;
                public String zan;
                public String courseId;
            }

            public static class TimeBean {
                /**
                 * dt : 1473264000
                 * count : 40
                 */

                public String dt;
                public String count;
            }
        }

        public static class BodyBean {
            /**
             * type : 0
             * name : 狼在北京
             * logo : http://img3.duitang.com/uploads/item/201407/01/20140701222607_AnKfj.thumb.224_0.jpeg
             * text : 回复@偏执的柔情:多谢支持，继续支持我哈
             * userId : 0001
             */

            public int type;
            public String name;
            public String logo;
            public String text;
            public String userId;
        }
    }
}
