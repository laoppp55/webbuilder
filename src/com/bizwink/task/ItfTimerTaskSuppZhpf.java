package com.bizwink.task;

import java.util.Date;
import java.util.TimerTask;

//import com.edarong.butt.pub.ItfConsts;
//import com.edarong.butt.pub.ItfSchedule;
//import com.edarong.butt.service.SuppService;
//import com.edarong.butt.service.impl.SuppServiceImpl;

/**
 * 供应商综合评分接口TimerTask
 *
 * @author huasen.xu 2013-11-17 create
 */
public class ItfTimerTaskSuppZhpf  extends TimerTask {
    //private SuppService srv = new SuppServiceImpl();

    /**
     * 定时轮询
     */
    @Override
    public void run() {
/*
        try {
            //每月某日某个时间定时执行的接口（定时间隔设为28天）、判断是否到达时间点
            Date d = ItfSchedule.getTimePointPerMonth(ItfConsts.TYPE_SUPP_ZHPF_INFO);
            //如果当月时间点还没到，则重新定义定时器
            if(d.after(new Date())) {
                ItfSchedule.resetMonthTimer(ItfConsts.TYPE_SUPP_ZHPF_INFO, d);
            } else {
                //执行接口处理
                srv.syncSuppZhpf();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
    }

}
