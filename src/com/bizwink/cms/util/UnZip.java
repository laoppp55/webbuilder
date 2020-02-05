package com.bizwink.cms.util;

import com.bizwink.cms.pic.PicPeer;
import com.bizwink.cms.pic.IPicManager;
import com.bizwink.cms.pic.Pic;

import java.io.*;
import java.util.*;
import java.sql.Timestamp;

import com.bizwink.cms.sitesetting.FtpInfo;
import com.bizwink.cms.sitesetting.FtpSetting;
import com.bizwink.cms.sitesetting.IFtpSetManager;
import com.bizwink.cms.sitesetting.SiteInfoException;
import com.bizwink.net.ftp.BizBatchFtp;
import org.apache.tools.zip.*;

public class UnZip {
    public ZipFile zf;
    public final int EOF = -1;
    public int hostID = 0;
    private List fileNameList = new ArrayList();

    public List getFileNameList(){
        return  this.fileNameList;
    }

    public void UnZip(String sourceFile, String targetDir, String sitename, int siteid, int big5flag) {
        Enumeration enums;

        try {
            zf = new ZipFile(sourceFile);
            enums = zf.getEntries();
            while (enums.hasMoreElements()) {
                try {
                    ZipEntry target = (ZipEntry) enums.nextElement();
                    saveEntry(targetDir, target, sitename);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            zf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dispatchFileToRunEnv(siteid, big5flag);
    }

    private int dispatchFileToRunEnv(int siteid, int big5flag) {
        int errcode = 0;
        List<FtpInfo> list=new ArrayList<FtpInfo>();
        IFtpSetManager siteMgr = FtpSetting.getInstance();
        try {
            list = siteMgr.getFtpInfos(siteid);
        } catch (SiteInfoException e) {
            errcode = -31;
            e.printStackTrace();
            return errcode;
        }

        for (int i = 0; i < list.size(); i++) {
            FtpInfo ftpInfo = (FtpInfo) list.get(i);
            String siteIP = ftpInfo.getIp();
            String remoteDocRoot = ftpInfo.getDocpath();
            String ftpUser = ftpInfo.getFtpuser();
            String ftpPasswd = ftpInfo.getFtppwd();

            if (ftpInfo.getFtptype() == 0) {
                com.bizwink.net.ftp.BatchFtpFile batchFtpHandle = new com.bizwink.net.ftp.BatchFtpFile();
                //BizBatchFtp batchFtpHandle = new BizBatchFtp();
                //hostID表示需要发布到哪个主机上
                errcode = batchFtpHandle.ZipFtpFile(hostID, fileNameList,siteIP,ftpUser,ftpPasswd,remoteDocRoot,ftpInfo.getPublishway(),big5flag);
                if (errcode != 0) System.out.println("web content publishing failed");
            } else {
                com.bizwink.net.sftp.BatchFtpFile batchFtpHandle = new com.bizwink.net.sftp.BatchFtpFile();
                //hostID表示需要发布到哪个主机上
                errcode = batchFtpHandle.ZipFtpFile(hostID, fileNameList,siteIP,ftpUser,ftpPasswd,remoteDocRoot,ftpInfo.getPublishway(),big5flag);
                if (errcode != 0) System.out.println("web content publishing failed");
            }
        }

        return errcode;
    }

    public void UnZipAnywhere(String sourceFile, String targetDir, String sitename, int siteid, int big5flag) {
        Enumeration enums;

        try {
            targetDir = StringUtil.replace(targetDir, "/", File.separator);
            if (!targetDir.endsWith(File.separator)) targetDir = targetDir + File.separator;

            zf = new ZipFile(sourceFile);
            enums = zf.getEntries();
            while (enums.hasMoreElements()) {
                List dirList = new ArrayList();
                ZipEntry target = (ZipEntry) enums.nextElement();

                String filename = targetDir + StringUtil.gb2iso4View(target.getName());
                File file = new File(filename);
                String canonicalDestinationFile = file.getCanonicalPath();

                if (canonicalDestinationFile.toLowerCase().startsWith(targetDir.toLowerCase())) {
                    if (target.isDirectory()) {
                        dirList.add(file);
                        fileNameList = getSubDirectoryFiles(fileNameList, dirList, sitename);
                    } else {
                        InputStream is = zf.getInputStream(target);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        File dir = new File(file.getParent());
                        dir.mkdirs();
                        FileOutputStream fos = new FileOutputStream(file);
                        BufferedOutputStream bos = new BufferedOutputStream(fos);

                        int c;
                        while ((c = bis.read()) != EOF) {
                            bos.write((byte) c);
                        }
                        bos.close();
                        fos.close();
                        is.close();

                        String filePath = StringUtil.replace(file.getPath(), "/", File.separator);
                        String fileDir = filePath.substring(filePath.indexOf(sitename) + sitename.length(), filePath.lastIndexOf(File.separator) + 1);
                        fileNameList.add(filePath + "," + fileDir);
                    }
                } else {
                    System.out.println("解压文件在目标目录之外");
                }
            }
            zf.close();
        } catch (FileNotFoundException e) {
            System.out.println("zipfile not found");
        } catch (IOException e) {
            System.out.println("IO error...");
        }

        dispatchFileToRunEnv(siteid, big5flag);
    }

    public void UnZipNoDeleteSource(String sourceFile, String targetDir, String sitename, int siteid, int big5flag) {
        Enumeration enums;

        try {
            zf = new ZipFile(sourceFile);
            enums = zf.getEntries();
            while (enums.hasMoreElements()) {
                try {
                    ZipEntry target = (ZipEntry) enums.nextElement();
                    saveEntry(targetDir, target, sitename);
                }
                catch (FileNotFoundException e) {
                    System.out.println("zipfile not found");
                }
                catch (IOException e) {
                    System.out.println("IO error...");
                }
            }
            zf.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("zipfile not found");
        }
        catch (IOException e) {
            System.out.println("IO error...");
        }

        dispatchFileToRunEnv(siteid, big5flag);
    }

    private List getSubDirectoryFiles(List fileNameList, List dirList, String sitename) {
        for (int i = 0; i < dirList.size(); i++) {
            File file = (File) dirList.get(i);
            dirList.remove(i);
            File[] files = file.listFiles();
            if (files != null) {
                for (int j = 0; j < files.length; j++) {
                    if (files[j].isDirectory()) {
                        dirList.add(files[j]);
                    } else {
                        String filePath = StringUtil.replace(files[j].getPath(), "/", File.separator);
                        String fileDir = filePath.substring(filePath.indexOf(sitename) + sitename.length(), filePath.lastIndexOf(File.separator) + 1);
                        fileNameList.add(filePath + "," + fileDir);
                    }
                }
            } else {
                file.mkdirs();
            }
        }

        if (dirList.size() > 0)
            getSubDirectoryFiles(fileNameList, dirList, sitename);
        return fileNameList;
    }

    public void saveEntry(String s, ZipEntry target, String sitename) throws IOException {
        try {
            s = StringUtil.replace(s, "/", File.separator);
            if (!s.endsWith(File.separator)) s = s + File.separator;
            String filename = s + StringUtil.gb2iso4View(target.getName());
            String fileDir = s.substring(s.indexOf(sitename) + sitename.length());

            File file = new File(filename);
            String canonicalDestinationFile = file.getCanonicalPath();

            if (canonicalDestinationFile.toLowerCase().startsWith(s.toLowerCase())) {
                if (target.isDirectory()) {
                    file.mkdirs();
                } else {
                    InputStream is = zf.getInputStream(target);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);

                    int c;
                    while ((c = bis.read()) != EOF) {
                        bos.write((byte) c);
                    }
                    bos.close();
                    fos.close();
                    is.close();

                    fileNameList.add(filename + "," + fileDir);
                }
            } else {
                System.out.println("解压文件在目标目录之外");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List UnZipTemplatePictures(String sourceFile, String targetDir, String sitename, int siteid, String fileDir, int columnid, int big5flag) {
        Enumeration enums;
        List doubleFileNameList = new ArrayList();
        int retcode = 0;

        try {
            zf = new ZipFile(sourceFile);
            enums = zf.getEntries();
            ZipEntry target;

            while (enums.hasMoreElements()) {
                target = (ZipEntry) enums.nextElement();
                try {

                    retcode = saveEntryTemplatePic(siteid, columnid, targetDir, target, sitename, fileDir, big5flag);
                    //在服务器上有重名的文件
                    if (retcode == -1) {
                        doubleFileNameList.add(StringUtil.gb2iso4View(target.getName()));
                    }
                } catch (Exception pex) {
                    pex.printStackTrace();
                }
            }
            zf.close();

            //发送文件到WEB服务器
            dispatchTemplatePictureToRunEnv(siteid, sitename, big5flag, fileDir);
        } catch (FileNotFoundException e) {
            System.out.println("zipfile not found");
        } catch (IOException e) {
            System.out.println("IO error...");
        } finally {
            //删除文件
            if (big5flag == 1 && doubleFileNameList.size() == 0) {
                File file = new File(sourceFile);
                file.delete();
            }
        }

        return doubleFileNameList;
    }

    public int saveEntryTemplatePic(int siteid, int columnid, String s, ZipEntry target, String sitename, String fileDir, int big5flag) throws IOException {
        int errcode = 0;

        try {
            s = StringUtil.replace(s, fileDir, File.separator);
            if (!s.endsWith(File.separator)) s = s + File.separator;
            String filename = s + StringUtil.gb2iso4View(target.getName());
            File file = new File(filename);
            String canonicalDestinationFile = file.getCanonicalPath();

            if (canonicalDestinationFile.toLowerCase().startsWith(s.toLowerCase())) {
                if (target.isDirectory()) {
                    file.mkdirs();
                } else {
                    IPicManager pictureMgr = PicPeer.getInstance();
                    boolean retcode = pictureMgr.existThePicture(StringUtil.gb2iso4View(target.getName()), siteid);
                    if (!retcode) {
                        String imgurl = "";
                        sitename = StringUtil.replace(sitename, ".", "_");
                        imgurl = "/webbuilder/sites/" + sitename + fileDir + StringUtil.gb2iso4View(target.getName());
                        //将picture文件存到图片文件列表中
                        if ((filename.toLowerCase().lastIndexOf(".jpg") > 0 || filename.toLowerCase().lastIndexOf(".jpeg") > 0 ||
                                filename.toLowerCase().lastIndexOf(".gif") > 0 || filename.toLowerCase().lastIndexOf(".png") > 0 ||
                                filename.toLowerCase().lastIndexOf(".bmp") > 0) && big5flag == 0) {
                            Pic pic = new Pic();
                            try {
                                pic.setSiteid(siteid);
                                pic.setColumnid(columnid);
                                pic.setHeight(0);
                                pic.setWidth(0);
                                pic.setPicsize((int) file.length());
                                pic.setPicname(StringUtil.gb2iso4View(target.getName()));
                                pic.setImgurl(imgurl);
                                pic.setCreatedate(new Timestamp(System.currentTimeMillis()));
                                pictureMgr.saveOnePicture(pic);
                            } catch (Exception pex) {
                                pex.printStackTrace();
                            }
                        }
                    } else {
                        errcode = -1;
                    }

                    //如果文件在目标目录不存在，则发布该文件
                    if (!file.exists()) {
                        InputStream is = zf.getInputStream(target);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        File dir = new File(file.getParent() + fileDir);
                        dir.mkdirs();
                        int posi = filename.lastIndexOf(File.separator);
                        if (posi > -1) filename = filename.substring(posi+1);
                        //System.out.println(dir.toString() + filename);
                        FileOutputStream fos = new FileOutputStream(dir.toString() + File.separator + filename);
                        BufferedOutputStream bos = new BufferedOutputStream(fos);

                        int c;
                        while ((c = bis.read()) != EOF) {
                            bos.write((byte) c);
                        }
                        bos.close();
                        fos.close();
                        is.close();

                        fileNameList.add(dir.toString() + File.separator + filename);
                    }
                }
            } else {
                System.out.println("解压文件在目标目录之外");
            }
        } catch (IOException e) {
            throw e;
        }

        return errcode;
    }

    private int dispatchTemplatePictureToRunEnv(int siteid, String sitename, int big5flag, String fileDir) {
        int errcode = 0;

        List<FtpInfo> list=new ArrayList<FtpInfo>();
        IFtpSetManager siteMgr = FtpSetting.getInstance();
        try {
            list = siteMgr.getFtpInfos(siteid);
        } catch (SiteInfoException e) {
            errcode = -31;
            e.printStackTrace();
            return errcode;
        }

        for (int i = 0; i < list.size(); i++) {
            FtpInfo ftpInfo = (FtpInfo) list.get(i);
            String siteIP = ftpInfo.getIp();
            String remoteDocRoot = ftpInfo.getDocpath();
            String ftpUser = ftpInfo.getFtpuser();
            String ftpPasswd = ftpInfo.getFtppwd();

            if (ftpInfo.getFtptype() == 0) {
                //com.bizwink.net.ftp.BatchFtpFile batchFtpHandle = new com.bizwink.net.ftp.BatchFtpFile();
                BizBatchFtp batchFtpHandle = new BizBatchFtp();
                //hostID表示需要发布到哪个主机上
                errcode = batchFtpHandle.ZipFtpFile(hostID, fileNameList,siteIP,ftpUser,ftpPasswd,remoteDocRoot,fileDir,ftpInfo.getPublishway(),big5flag);
                if (errcode != 0) System.out.println("web content publishing failed");
            } else {
                com.bizwink.net.sftp.BatchFtpFile batchFtpHandle = new com.bizwink.net.sftp.BatchFtpFile();
                //hostID表示需要发布到哪个主机上
                errcode = batchFtpHandle.ZipFtpFile(hostID, fileNameList,siteIP,ftpUser,ftpPasswd,remoteDocRoot,ftpInfo.getPublishway(),big5flag);
                if (errcode != 0) System.out.println("web content publishing failed");
            }
        }

        return errcode;
    }
}
