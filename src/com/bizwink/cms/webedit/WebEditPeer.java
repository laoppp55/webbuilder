package com.bizwink.cms.webedit;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;
import com.bizwink.net.ftp.*;
import com.bizwink.cms.sitesetting.*;
import org.apache.regexp.*;

public class WebEditPeer implements IWebEditManager
{
    PoolServer cpool;

    public WebEditPeer(PoolServer cpool)
    {
        this.cpool = cpool;
    }

    public static IWebEditManager getInstance()
    {
        return (IWebEditManager)CmsServer.getInstance().getFactory().getWebEditManager();
    }

    public List getFiles(String filePath) throws CmsException
    {
        List filesList = new ArrayList();

        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                return filesList;
            }

            List dirList = new ArrayList();
            List fileList = new ArrayList();
            List tempList = new ArrayList();
            File[] list = file.listFiles();

            if (list != null)
            {
                for (int i=0; i<list.length; i++)
                {
                    if (list[i].isDirectory())
                        dirList.add(list[i].getName());
                    else
                        fileList.add(list[i].getName());
                }
            }

            //排序
            String[] dirarr = new String[dirList.size()];
            String[] filearr = new String[fileList.size()];
            for (int i=0; i<dirarr.length; i++)
                dirarr[i] = (String)dirList.get(i);
            for (int i=0; i<filearr.length; i++)
                filearr[i] = (String)fileList.get(i);

            Arrays.sort(dirarr);
            Arrays.sort(filearr);

            for (int i=0; i<dirarr.length; i++)
                tempList.add(dirarr[i]);
            for (int i=0; i<filearr.length; i++)
                tempList.add(filearr[i]);

            //获得文件属性
            WebEdit webedit = null;
            for (int i=0; i<tempList.size(); i++)
            {
                String filename = (String)tempList.get(i);
                file = new File(filePath + filename);

                webedit = new WebEdit();
                webedit.setFileName(filename);
                webedit.setFileSize(file.length());
                webedit.setIsDirectory(file.isDirectory());
                webedit.setCanWrite(file.canWrite());
                webedit.setFileDate(new Timestamp(file.lastModified()));

                filesList.add(webedit);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return filesList;
    }

    private void syncRemoteFile(String filename,String newFilename,String dirName,int siteID)
    {
        String name = filename.substring(filename.lastIndexOf(File.separator) + 1);
        List siteList = new ArrayList();
        IFtpSetManager siteMgr = FtpSetting.getInstance();

        try
        {
            siteList = siteMgr.getFtpInfos(siteID);
        }
        catch (SiteInfoException e)
        {
            e.printStackTrace();
        }

        for (int i=0; i<siteList.size(); i++)
        {
            FtpInfo ftpInfo = (FtpInfo)siteList.get(i);
            String remoteDocRoot = ftpInfo.getDocpath();
            if (remoteDocRoot == null) return;
            if (remoteDocRoot.endsWith("/") || remoteDocRoot.endsWith(File.separator))
                remoteDocRoot = remoteDocRoot.substring(0,remoteDocRoot.length()-1);

            if (ftpInfo.getPublishway() == 1 || ftpInfo.getPublishway() == 2)  //本地发布
            {
                String oldRemoteFile = remoteDocRoot + StringUtil.replace(dirName,"/",File.separator) + name;
                File oldFile = new File(oldRemoteFile);
                if (oldFile.exists())
                {
                    if (newFilename == null)    //删除
                    {
                        oldFile.delete();
                    }
                    else                        //改名
                    {
                        String newRemoteFile = remoteDocRoot + StringUtil.replace(dirName,"/",File.separator) + newFilename;
                        File newFile = new File(newRemoteFile);
                        oldFile.renameTo(newFile);
                    }
                }
            }

            if (ftpInfo.getPublishway() == 0)
            {
                String siteIP = ftpInfo.getIp();
                String ftpUser = ftpInfo.getFtpuser();
                String ftpPasswd = ftpInfo.getFtppwd();

                String[] remoteDir = null;
                try
                {
                    RE regExp = new RE("/");
                    remoteDir = regExp.split(dirName);
                }
                catch (org.apache.regexp.RESyntaxException e)
                {
                    e.printStackTrace();
                }

                FTPClient ftp = null;
                try
                {
                    ftp = new FTPClient(siteIP, 21);
                    ftp.setTimeout(20000);
                    ftp.login(ftpUser, ftpPasswd);
                    ftp.setTimeout(0);
                    ftp.setType(FTPTransferType.ASCII);
                    ftp.setConnectMode(FTPConnectMode.PASV);

                    ftp.chdir("/");
                    ftp.chdir(remoteDocRoot);

                    //在这儿测试远成的目录是否存在，如果不存在，则创建，并进入该层目录
                    for (int j=0; j<remoteDir.length; j++)
                    {
                        if (remoteDir[j].length() > 0)
                        {
                            ftp.mkdir(remoteDir[j]);
                            ftp.chdir(remoteDir[j]);
                        }
                    }

                    if (newFilename == null)          //删除
                        ftp.delete(name);
                    else                              //改名
                        ftp.rename(name, newFilename);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                catch (FTPException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if (ftp != null)
                    {
                        try
                        {
                            ftp.quit();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void RenameFile(String filename,String newFilename,String siteName,int siteID)
    {
        if (filename == null) return;
        String dirName = filename.substring(filename.indexOf(siteName)+siteName.length(),filename.lastIndexOf("/")+1);
        filename = StringUtil.replace(filename,"/",File.separator);

        syncRemoteFile(filename,newFilename,dirName,siteID);
    }

    public void DeleteFile(String filename,String sitename,int siteID,boolean delete)
    {
        if (filename == null) return;
        String dirName = filename.substring(filename.indexOf(sitename)+sitename.length(),filename.lastIndexOf("/")+1);
        filename = StringUtil.replace(filename,"/",File.separator);

        try
        {
            File file = new File(filename);
            if (file.exists()) file.delete();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (delete)
        {
            syncRemoteFile(filename,null,dirName,siteID);
        }
    }
}