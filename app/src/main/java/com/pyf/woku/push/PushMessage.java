package com.pyf.woku.push;

import java.io.Serializable;

/**
 * 极光推送消息实体类
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/28
 */
public class PushMessage implements Serializable{

    // 消息类型
    public String messageType = null;
    // 连接
    public String messageUrl = null;
    // 详情内容
    public String messageContent = null;
}
