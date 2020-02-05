package com.bizwink.upload;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;

import com.oreilly.servlet.MultipartRequest;
import com.bizwink.cms.server.CmsServer;

public class Upload {
    MultipartRequest mrequest = null;

    private String uploaddir = "";

    public void setUploaddir(String uploaddir) {
        this.uploaddir = uploaddir;
    }

    private int size = 5120000;
    private int uploadsize = CmsServer.getInstance().getUploadSize();

    public void setSize(int size) {
        this.size = size;
    }

    public void startUpload(HttpServletRequest request) throws Exception {
        File dir = new File(uploaddir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (uploaddir == null) {
            throw new Exception("No Upload directory!");
        }
        if (dir.length() > size * uploadsize) {
            throw new Exception("This file's size up to max size allow!");
        }
        try {
            mrequest = new MultipartRequest(request, uploaddir, size * uploadsize,"utf-8");
        }
        catch (Exception ex) {
            throw new Exception("MultipartRequest()" + ex.getMessage());
        }
    }

    public Vector dealAllUploadImg() throws Exception {
        Vector vector = new Vector();
        Enumeration e = mrequest.getFileNames();

        while (e.hasMoreElements()) {
            String imgfile = mrequest.getFilesystemName((String) e.nextElement());
            if (imgfile != null) {
                vector.addElement(imgfile);
            }
        }
        return vector;
    }

    public Hashtable dealAllUploadImg2() throws Exception {
        Hashtable hashtable = new Hashtable();

        Enumeration e = mrequest.getFileNames();
        while (e.hasMoreElements()) {
            String imgname = (String) e.nextElement();
            String imgfile = mrequest.getFilesystemName(imgname);
            if (imgfile != null) {
                hashtable.put(imgname, imgfile);
            }
        }
        return hashtable;
    }

    public Hashtable dealAllPara() throws Exception {
        Hashtable hashtable = new Hashtable();

        Enumeration e = mrequest.getParameterNames();
        while (e.hasMoreElements()) {
            String paraname = (String) e.nextElement();
            String para = mrequest.getParameter(paraname);
            if ((paraname != null) && (para != null)) {
                hashtable.put(paraname, para);
            }
        }
        return hashtable;
    }

    //删除所有上传文件*/
    public void deleteAllUploadImg() throws Exception {
        Enumeration e1 = mrequest.getFileNames();
        while (e1.hasMoreElements()) {
            String imgfile = mrequest.getFilesystemName((String) e1.nextElement());
            if (imgfile != null) {
                File imgfile1 = new File(uploaddir, imgfile);
                if (imgfile1.exists()) {
                    imgfile1.delete();
                }
            }
        }
    }

    public void clear() {
        uploaddir = "";
        mrequest = null;
    }
}