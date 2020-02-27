package com.bizwink.weixin.entity;

public class ReplyTextMessage {
    private String MessageType;
    private String FuncFlag;
    private String CreateTime;
    private String Content;
    private String ToUserName;
    private String FromUserName;

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getFuncFlag() {
        return FuncFlag;
    }

    public void setFuncFlag(String funcFlag) {
        FuncFlag = funcFlag;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }
}
