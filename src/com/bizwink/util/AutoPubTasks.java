package com.bizwink.util;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created with IntelliJ IDEA.
 * User: petersong
 * Date: 14-7-14
 * Time: 下午10:17
 * To change this template use File | Settings | File Templates.
 */
public class AutoPubTasks {
    private Timer timer;
    private final long interval = 60 * 1000;
    private LogToDbTask task;

    public AutoPubTasks(){
        timer= new Timer();
    }

    public void start(){
        task = new LogToDbTask();
        timer.schedule(task,interval,interval);
    }

    private class LogToDbTask extends TimerTask{
        public void run(){
            //saveToDb();
        }
    }

    public void main(String args[]){
        AutoPubTasks service = new AutoPubTasks();
        service.start();
    }
}
