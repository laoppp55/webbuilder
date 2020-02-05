package com.unittest;

/*import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ImmediateRefreshHandler;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
*/
import javax.script.ScriptException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Administrator on 18-5-13.
 */

public class BasicScripting {
    public void greet() throws ScriptException {
        /*try {
            WebClient webclient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
            webclient.getOptions().setJavaScriptEnabled(true);
            webclient.getOptions().setThrowExceptionOnScriptError(false);
            webclient.getOptions().setCssEnabled(false);
            webclient.getCookieManager().clearCookies();
            webclient.getCache().clear();
            webclient.setRefreshHandler(new ImmediateRefreshHandler());
            webclient.getOptions().setTimeout(600*1000);
            webclient.setJavaScriptTimeout(600*1000);
            webclient.setAjaxController(new NicelyResynchronizingAjaxController());
            webclient.getOptions().setRedirectEnabled(true);
            webclient.waitForBackgroundJavaScript(60*1000);
            webclient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webclient.getOptions().setUseInsecureSSL(true);
            String url = "http://jswater.jiangsu.gov.cn/col/col42984/index.html";
            HtmlPage htmlPage = webclient.getPage(new URL(url));
            // 等待JS驱动dom完成获得还原后的网页
            webclient.waitForBackgroundJavaScript(10000);
            // 网页内容
            System.out.println(htmlPage.asXml());
            webclient.close();
        }catch(IOException exp) {
            exp.printStackTrace();
        }*/
   }

    /**
     * 从输入流中获取字符串
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        System.out.println(new String(bos.toByteArray(),"utf-8"));
        return new String(bos.toByteArray(),"utf-8");
    }

    public static void main(String[] args) {
        try {
            new BasicScripting().greet();
        } catch (ScriptException ex) {
            Logger.getLogger(BasicScripting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}