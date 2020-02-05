package com.bizwink.cms.register;

import com.bizwink.cms.sitesetting.FtpInfo;
import com.bizwink.cms.util.StringUtil;
import java.io.*;
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
public class W2kMkCooUser {
    private String root = "";
    private String sitename = "";
    private String defaultdoc = "";
    private String cmsroot = "";

    public W2kMkCooUser(String docroot,String sitename,String defaultDocumentName,String cmsroot) {
        this.root = docroot;
        this.sitename = sitename;
        //this.defaultdoc = defaultDocumentName + ",index.jsp,index.asp";
        this.defaultdoc = "index.shtml,index.shtm,index.jsp,index.html,index.htm";
        this.cmsroot = cmsroot;
    }

    public boolean build(){
        boolean retval = false;
        String exec = checkScript();

        exec = "cmd /c cscript " + exec;
        exec = exec + " -r " + root + " -t " + sitename + " -h " + sitename + " -d " + defaultdoc + " -w " + cmsroot;
        System.out.println(exec);
        try {
            Process prc = Runtime.getRuntime().exec(exec);
            String in = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(prc.getInputStream()));
            String temp = "";
            StringBuffer sb = new StringBuffer();
            while ( (temp = br.readLine()) != null) {
                sb.append(temp);
            }
            in = sb.toString();
            System.out.println(in);

            retval = true;
        }
        catch (Exception e) {
            System.out.println(""+e.toString());
            e.printStackTrace();
        }

        return retval;
    }

    private String checkScript(){
        String path = "";
        String packagename = W2kMkCooUser.class.getPackage().getName();
        String classname = W2kMkCooUser.class.getName();
        classname = classname.substring(classname.indexOf(packagename)+packagename.length()+1,classname.length())+".class";
        path = W2kMkCooUser.class.getResource(classname).toString();
        path = path.substring(path.indexOf("/")+1,path.lastIndexOf("/"));
        path = path + File.separator + "mkcoosite.vbs";
        //System.out.println("path=" + path);
        File script = new File(path);
        if(script.exists()){
            return path;
        }else{
            //may build the script
            return null;
        }
    }
}
