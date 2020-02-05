package com.bizwink.cms.server;

import com.bizwink.mediaconvert.*;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-10-25
 * Time: 10:26:23
 * To change this template use File | Settings | File Templates.
 */
public class MultimediaServer implements IMultimediaServer{

    public static MultimediaThread multimediapeer = null;
    PoolServer cpool;

    public void createMultimedia(String logPath, PoolServer cpool) {
        this.cpool = cpool;

        try {
            System.out.println("启动多媒体转换系统！！！");
            multimediapeer = new MultimediaThread(logPath, cpool);
        } catch (IOException ioe) {
            System.err.println("Error starting Multimedia Server: " + ioe);
            ioe.printStackTrace();
        }
    }
}
