package com.bizwink.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by petersong on 16-10-22.
 */
public class Log4j {
    //Logger实例
    public Logger logger = null;

    //将Log类封装为单例模式
    private static Log4j log;

    //构造函数，用于初始化Logger配置需要的属性
    private Log4j() {
        //获得当前目录路径
        String filePath=this.getClass().getResource("/").getPath();
        //找到log4j.properties配置文件所在的目录(已经创建好)
        filePath=filePath.substring(1).replace("bin", "src");
        //获得日志类logger的实例
        logger=Logger.getLogger(this.getClass());
        //logger所需的配置文件路径
        PropertyConfigurator.configure(filePath+"log4j.properties");
    }

    public static Log4j getLogger() {
        if(log != null)
            return log;
        else
            return new Log4j();
    }
}
