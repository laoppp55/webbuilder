package com.bizwink.weixin;

import java.util.Date;
import java.util.List;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.bizwink.weixin.entity.Item;
import com.bizwink.weixin.entity.ReplyTextMessage;
import com.bizwink.weixin.entity.ReplyTuwenMessage;
import com.bizwink.weixin.RequestTextMessage;

public class ReplyMessage {

    //  获取关注事件
    public static RequestTextMessage getRequestFocus(String xml){

        XStream xstream = new XStream(new DomDriver());

        xstream.alias("xml", RequestTextMessage.class);
        xstream.aliasField("ToUserName", RequestTextMessage.class, "toUserName");
        xstream.aliasField("FromUserName", RequestTextMessage.class, "fromUserName");
        xstream.aliasField("CreateTime", RequestTextMessage.class, "createTime");
        xstream.aliasField("MsgType", RequestTextMessage.class, "messageType");
        xstream.aliasField("Event", RequestTextMessage.class, "event");
        xstream.aliasField("EventKey", RequestTextMessage.class, "eventKey");
//            xstream.aliasField("Content", RequestTextMessage.class, "content");
//            xstream.aliasField("MsgId", RequestTextMessage.class, "msgId");
        RequestTextMessage requestTextMessage = (RequestTextMessage)xstream.fromXML(xml);
        return requestTextMessage;

    }

    //  获取推送文本消息
    public static RequestTextMessage getRequestTextMessage(String xml){
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("xml", RequestTextMessage.class);
        xstream.aliasField("URL", RequestTextMessage.class, "url");
        xstream.aliasField("ToUserName", RequestTextMessage.class, "toUserName");
        xstream.aliasField("FromUserName", RequestTextMessage.class, "fromUserName");
        xstream.aliasField("CreateTime", RequestTextMessage.class, "createTime");
        xstream.aliasField("MsgType", RequestTextMessage.class, "messageType");
        xstream.aliasField("Content", RequestTextMessage.class, "content");
        xstream.aliasField("MsgId", RequestTextMessage.class, "msgId");

        RequestTextMessage requestTextMessage = (RequestTextMessage)xstream.fromXML(xml);
        return requestTextMessage;
    }


    //    回复文本消息
    public static String getReplyTextMessage(String content, String fromUserName,String toUserName){
        ReplyTextMessage we = new ReplyTextMessage();
        we.setMessageType("text");
        we.setFuncFlag("0");
        we.setCreateTime(new Long(new Date().getTime()).toString());
        we.setContent(content);
        we.setToUserName(fromUserName);
        we.setFromUserName(toUserName);
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("xml", ReplyTextMessage.class);
        xstream.aliasField("ToUserName", ReplyTextMessage.class, "toUserName");
        xstream.aliasField("FromUserName", ReplyTextMessage.class, "fromUserName");
        xstream.aliasField("CreateTime", ReplyTextMessage.class, "createTime");
        xstream.aliasField("MsgType", ReplyTextMessage.class, "messageType");
        xstream.aliasField("Content", ReplyTextMessage.class, "content");
        xstream.aliasField("FuncFlag", ReplyTextMessage.class, "funcFlag");
        String xml =xstream.toXML(we);
        return xml;

    }


    //    回复图文消息
    public static String getReplyTuwenMessage(String fromUserName,String toUserName,List<Item> articleList){
//      NewsMessage newsMessage = new NewsMessage();
//      MessageUtil messageUtil = new MessageUtil();
        ReplyTuwenMessage we = new ReplyTuwenMessage();
        //  List<Item> articleList = new ArrayList<>();
        //      Articles articles = new Articles();

//        Item item = new Item();
//        Item item2 = new Item();
//        Item item3 = new Item();

        we.setMessageType("news");
        we.setCreateTime(new Long(new Date().getTime()).toString());
        we.setToUserName(fromUserName);
        we.setFromUserName(toUserName);
        we.setFuncFlag("0");
        we.setArticleCount(articleList.size());
        we.setArticles(articleList);

        XStream xstream = new XStream(new DomDriver());
        xstream.alias("xml", ReplyTuwenMessage.class);
        xstream.aliasField("ToUserName", ReplyTuwenMessage.class, "toUserName");
        xstream.aliasField("FromUserName", ReplyTuwenMessage.class, "fromUserName");
        xstream.aliasField("CreateTime", ReplyTuwenMessage.class, "createTime");
        xstream.aliasField("MsgType", ReplyTuwenMessage.class, "messageType");
        xstream.aliasField("Articles", ReplyTuwenMessage.class, "Articles");

        xstream.aliasField("ArticleCount", ReplyTuwenMessage.class, "articleCount");
        xstream.aliasField("FuncFlag", ReplyTuwenMessage.class, "funcFlag");

        //xstream.aliasField("item", Articles.class, "item");
        xstream.alias("item", new Item().getClass());
        xstream.aliasField("Title", Item.class, "title");
        xstream.aliasField("Description", Item.class, "description");
        xstream.aliasField("PicUrl", Item.class, "picUrl");
        xstream.aliasField("Url", Item.class, "url");
        String xml =xstream.toXML(we);
        return xml;
    }
}