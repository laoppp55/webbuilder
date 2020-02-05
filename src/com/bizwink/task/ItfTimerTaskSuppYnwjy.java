package com.bizwink.task;

import java.util.TimerTask;

//import com.edarong.butt.service.SuppService;
//import com.edarong.butt.service.impl.SuppServiceImpl;

/**
 * 供应商一年无交易接口TimerTask
 *
 * @author huasen.xu 2013-11-17 create
 */
public class ItfTimerTaskSuppYnwjy  extends TimerTask {
    //private SuppService srv = new SuppServiceImpl();

    /**
     * 定时轮询
     */
    @Override
    public void run() {
        //每日某个时间定时执行的接口
        //srv.syncSuppYnwjy();
    }

}
