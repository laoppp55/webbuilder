package com.bizwink.webservice;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-12-7
 * Time: 22:22:26
 * To change this template use File | Settings | File Templates.
 */
public class hello {
    private int requestCount = 0;

    public String helloword(String name) {
        requestCount++;
        System.out.println("requestCount=" + requestCount);
        System.out.println("Received:" + name);

        return "Hello " + name;
    }

    public Float add(Float a,float b) {
        requestCount++;
        String result="a=" + a + ",b=" + b;
        System.out.println("requestCount=" + requestCount);
        System.out.println("Received: " + result);

        return a + b;
    }
}
