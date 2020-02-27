package com.bizwink.weixin;

import com.bizwink.weixin.entity.Item;

import java.util.List;

public class ReplyTuwenMessage {
    private String MessageType;
    private String CreateTime;
    private String ToUserName;
    private String FromUserName;
    private String FuncFlag;
    private int ArticleCount;
    private List<Item> articleList;

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
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

    public String getFuncFlag() {
        return FuncFlag;
    }

    public void setFuncFlag(String funcFlag) {
        FuncFlag = funcFlag;
    }

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Item> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Item> articleList) {
        this.articleList = articleList;
    }
}
