package com.bizwink.cms.register;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;
import java.net.URL;
import org.jdom.*;
import org.jdom.input.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.publish.*;
import com.bizwink.upload.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.viewFileManager.*;
import com.bizwink.cms.sitesetting.FtpInfo;
import com.bizwink.cms.util.StringUtil;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class W2kMkCooUserApp {
    private String root = "";
    private String sitename = "";
    private String defaultdoc = "";

    public W2kMkCooUserApp() {

    }

    public static void main(String[] args) {
        String docroot = null;
        String sitename = null;
        String defaultDocName = "index.shtml";
        Register register = new Register();
        StringBuffer tempBuf = new StringBuffer();

        try {
            FileInputStream fis = new FileInputStream("e:\\bizwink\\test.txt");
            InputStreamReader isr = new InputStreamReader(fis, "GBK");
            Reader in = new BufferedReader(isr);
            int ch;
            while ((ch = in.read()) > -1)
            {
                tempBuf.append((char)ch);
            }
            in.close();
            isr.close();
            fis.close();
        }catch (IOException ioex) {
            ioex.printStackTrace();
        }

        W2kMkCooUserApp mksiteapp = new W2kMkCooUserApp();
        resinConfig resinconfig = mksiteapp.getResinConfig();
        IRegisterManager registerMgr = RegisterPeer.getInstance();
        Pattern p = Pattern.compile("\r\n", Pattern.CASE_INSENSITIVE);
        String mailList[] = p.split(tempBuf.toString());
        System.out.println(mailList.length);
        int globalsiteid = 1;
        String appPath = "d:\\coosite\\webbuilder\\";

/*      for (int i=0; i<mailList.length; i++) {
        try {
          System.out.println(mailList[i]);
          String oneuser = mailList[i];
          int sex = Integer.parseInt(oneuser.substring(oneuser.length() - 1));
          String username = oneuser.substring(0,oneuser.indexOf("@"));
          String email = oneuser.substring(0,oneuser.indexOf(","));
          register.setUserID(username);         //用户ID
          register.setUserName(username);       //用户名称
          if (sex == 0)                         //女性网站
            register.setClassID(112);
          else                                  //男性网站
            register.setClassID(113);
          register.setBindFlag(1);              //站点是否被激活     0--没有激活  1--激活
          register.setEmail(email);             //用户的电子邮件地址
          register.setPassword("12345678");     //用户口令
          register.setProduct("test");          //生产的产品
          register.setPubFlag(1);               //是否使用自动发布   0--不使用    1--使用
          register.setExtName("shtml");         //生成文件的扩展名
          register.setCorpAddr("test");         //企业地址
          register.setCorpName("test");         //企业名称
          register.setFax("010-66666666");      //传真
          register.setTelePhone("010-88888888");//电话
          register.setTCFlag(0);                //是否有繁体版本    0--没有     1-有
          register.setGrade(0);                 //0-初级用户  1--中级用户   2--高级用户
          register.setImagesDir(0);             //0--模板图片在根目录    1--模板图片在各个栏目目录
          register.setIndustry("信息技术和互联网(计算机软硬件,通讯)");               //从事的行业
          username = StringUtil.replace(username,"_","");
          register.setSiteName(username + ".coosite.com");
          register.setMemo("test");
          register.setServiceID("");
          register.setSiteGroupID(1);
          registerMgr.createCooUser(globalsiteid,register,resinconfig,appPath);
        } catch (CmsException cmsex) {
          cmsex.printStackTrace();
        }
      }                               */

    }

    public resinConfig getResinConfig() {
        resinConfig config = new resinConfig();
        config.setErrorCode(0);

        try{
            //System.out.println(this.getClass().getClassLoader().toString());
            File file = new File("COO_SETTING.xml");
            SAXBuilder builder = new SAXBuilder();
            //URL xmlfileurl = this.getClass().getResource("COO_SETTING.xml");
            Document doc = builder.build(file);
            Element ele = doc.getRootElement().getChild("FTP_INFO");
            config.setdocpath(ele.getAttributeValue("pubbasedir"));
            config.setlogoheight(Integer.parseInt(ele.getAttributeValue("logoheight")));
            config.setlogowidth(Integer.parseInt(ele.getAttributeValue("logowidth")));
            ele = doc.getRootElement().getChild("RESIN_CONFIG");
            config.setResinConfigPath(ele.getChildText("path"));
            config.setAppPath(ele.getChildText("webapp"));
            config.setConfigContent(ele.getChildText("content"));
        }catch(Exception e){
            e.printStackTrace();
            config.setErrorCode(-1);
        }
        return config;
    }
}
