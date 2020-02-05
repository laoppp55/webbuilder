package com.bizwink.webservice;

import com.bizwink.cms.publish.IPublishManager;
import com.bizwink.cms.publish.PublishException;
import com.bizwink.cms.publish.PublishPeer;
import com.bizwink.cms.util.CmsException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by petersong on 16-1-12.
 */
public class PublishSamsiteFileToTargetSite {
    //拷贝CSS文件和脚本文件到WEB
    public void copy_CSS_AND_SCRIPT_ToCMS_AND_WEB(String userid,String sampleSiteName,int siteid, String sitename, String appPath) throws CmsException {
        //源文件路径
        String srcPathIcon = appPath + "sites" + File.separator + sampleSiteName + File.separator;
        //目标文件路径
        String objPathIcon = appPath + "sites" + File.separator + sitename + File.separator;

        String srcfile = null;
        FileInputStream source = null;
        FileOutputStream destination = null;

        //拷贝文章列表图标
        File file = new File(srcPathIcon);
        int index = 0;
        List dirs = new ArrayList();
        dirs.add(file);

        try {
            do {
                file = (File) dirs.get(index);
                File[] fs = file.listFiles();
                dirs.remove(index);
                index = index - 1;
                if (fs != null) {
                    for (int i = 0; i < fs.length; i++) {
                        if (fs[i].isDirectory()) {
                            dirs.add(fs[i]);
                            index = index + 1;
                        } else {
                            srcfile = fs[i].getPath();
                            String extname = srcfile.substring(srcfile.lastIndexOf(".") + 1);
                            //if (extname.equalsIgnoreCase("gif") || extname.equalsIgnoreCase("jpg") || extname.equalsIgnoreCase("jpeg") ||
                            //        extname.equalsIgnoreCase("swf") || extname.equalsIgnoreCase("css") || extname.equalsIgnoreCase("js") ||
                            //        extname.equalsIgnoreCase("vbs")) {
                            if (extname.equalsIgnoreCase("css") || extname.equalsIgnoreCase("js") || extname.equalsIgnoreCase("vbs")) {
                                int posi = srcfile.indexOf(srcPathIcon);
                                String objfile = objPathIcon + srcfile.substring(posi + srcPathIcon.length());
                                String objpath = objfile.substring(0, objfile.lastIndexOf(File.separator));
                                File dir = new File(objpath);
                                if (!dir.exists()) dir.mkdirs();
                                source = new FileInputStream(srcfile);
                                destination = new FileOutputStream(objfile);
                                byte[] buffer = new byte[1024];
                                int bytes_read;

                                while (true) {
                                    bytes_read = source.read(buffer);
                                    if (bytes_read == -1) break;
                                    destination.write(buffer, 0, bytes_read);
                                }

                                source.close();
                                destination.close();

                                //发布文件到所有的WEB服务器
                                try {
                                    IPublishManager publishMgr = PublishPeer.getInstance();
                                    String localFileName = srcfile;
                                    String dirName = srcfile.substring(posi + srcPathIcon.length()-1);
                                    posi = dirName.lastIndexOf(File.separator);
                                    dirName = dirName.substring(0,posi+1);
                                    int errcode = publishMgr.publish(userid, localFileName, siteid, dirName, 0);
                                } catch (PublishException pex) {
                                    pex.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } while (dirs.size() != 0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            srcPathIcon = null;
            objPathIcon = null;
            file = null;
        }
    }

}
