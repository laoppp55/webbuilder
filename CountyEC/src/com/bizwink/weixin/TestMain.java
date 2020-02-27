package com.bizwink.weixin;

/**
 * Created by Administrator on 17-6-4.
 */
import java.util.HashMap;
import java.util.Map;

//对接口进行测试
public class TestMain {
    private String url = "https://192.168.1.101/";
    private String charset = "utf-8";
    private HttpUtility httpUtility = null;

    public TestMain(){
        httpUtility = new HttpUtility();
    }

    public void test(){
        String httpOrgCreateTest = url + "httpOrg/create";
        Map<String,String> createMap = new HashMap<String,String>();
        createMap.put("authuser","*****");
        createMap.put("authpass","*****");
        createMap.put("orgkey","****");
        createMap.put("orgname","****");
        String httpOrgCreateTestRtn = httpUtility.httpSendPost(httpOrgCreateTest,createMap,charset);
        System.out.println("result:"+httpOrgCreateTestRtn);
    }

    public static void main(String[] args){
        TestMain main = new TestMain();
        main.test();
    }
}

