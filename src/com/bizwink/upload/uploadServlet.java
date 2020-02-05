package com.bizwink.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class uploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(2048*1024);
        myProgressListener getBarListener = new myProgressListener(req);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setProgressListener(getBarListener);
        try {
            List formList = upload.parseRequest(req);
            Iterator<Object> formItem = formList.iterator();
            // 将进度监听器加载进去
            while (formItem.hasNext()) {
                FileItem item = (FileItem) formItem.next();
                if (item.isFormField()) {
                    System.out.println("Field Name:" + item.getFieldName());
                } else {
                    String fileName = item.getName().substring(item.getName().lastIndexOf("\\")+1);
                    File file = new File("c:\\temp" + "\\" + fileName);
                    System.out.println("c:\\temp" + "\\" + fileName);
                    OutputStream out = item.getOutputStream();
                    InputStream in = item.getInputStream();
                    req.getSession().setAttribute("outPutStream", out);
                    req.getSession().setAttribute("inPutStream", in);
                    item.write(file);
                }
            }
        } catch (FileUploadException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        super.init(config);
    }
}
