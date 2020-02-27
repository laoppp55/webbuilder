package com.bizwink.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by petersong on 16-6-6.
 */
public class FileUpload {
    protected HttpServletRequest m_request;
    protected HttpServletResponse m_response;
    protected ServletContext m_application;

    public final void init(ServletConfig config)
            throws ServletException
    {
        m_application = config.getServletContext();
    }

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        m_request = request;
        m_response = response;
    }

    public final void initialize(ServletConfig config, HttpServletRequest request, HttpServletResponse response)
            throws ServletException
    {
        m_application = config.getServletContext();
        m_request = request;
        m_response = response;
    }

    public final void initialize(PageContext pageContext)
            throws ServletException
    {
        m_application = pageContext.getServletContext();
        m_request = (HttpServletRequest)pageContext.getRequest();
        m_response = (HttpServletResponse)pageContext.getResponse();
    }

    public final void initialize(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response, JspWriter out)
            throws ServletException
    {
        m_application = application;
        m_request = request;
        m_response = response;
    }

    public String  upload(String docpath) {
        String path = m_request.getContextPath() + "/";
        String clientPath =null;
        if(ServletFileUpload.isMultipartContent(m_request)){
            String type = "";
            if(m_request.getParameter("type") != null)//获取文件分类
                type = m_request.getParameter("type").toLowerCase() + "/";
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
            servletFileUpload.setHeaderEncoding("UTF-8");//解决文件名乱码的问题
            try {
                List<FileItem> fileItemsList = servletFileUpload.parseRequest(m_request);
                for (FileItem item : fileItemsList) {
                    if (!item.isFormField()) {
                        String fileName = item.getName();
                        fileName = "file" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
                        //定义文件路径，根据你的文件夹结构，可能需要做修改
                        clientPath = docpath + type + fileName;

                        //保存文件到服务器上
                        File file = new File(m_request.getSession().getServletContext().getRealPath(clientPath));
                        System.out.println(file.toString());
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                        item.write(file);
                    }
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }

        return path+clientPath;
    }
}
