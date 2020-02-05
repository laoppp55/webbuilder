package com.bizwink.dubboservice;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;

public class dubboService {
    private static Logger logger = LogManager.getLogger(dubboService.class);
    public static ClassPathXmlApplicationContext  context= null;

    public dubboService() {
    }

    public static void main(String[] args)  throws IOException {
        context=new ClassPathXmlApplicationContext(new String[] {"dubbo-provider.xml"});
        /*if (context != null) {
            context.start();
            System.out.println("common service started ...");
            System.in.read();
            context.close();
        }*/

        logger.info("starting bidding service ...");
        // TODO Auto-generated method stub
        long begin = System.currentTimeMillis();
        context.start();
        long end = System.currentTimeMillis();
        logger.info("bidding service started in "+(end-begin)+" ms.");
        while(true){
            try {
                Thread.sleep(60000l);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
