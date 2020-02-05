package com.bizwink.cms.toolkit.subscribe;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-2-25
 * Time: 17:19:35
 * To change this template use File | Settings | File Templates.
 */
public interface ISubscribeManager {

    void addSubscribe(Subscribe subscribe);

    void updateSubFlag(Subscribe subscribe);

    boolean checkEmail(String email);

    int getSubFlag(String email);
}
