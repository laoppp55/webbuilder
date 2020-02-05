package com.bizwink.upload;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.bizwink.cms.util.ParamUtil;

public class MultipartFormServ extends HttpServlet {
    static final private String CONTENT_TYPE = "text/html;charset=utf-8";

    private static MultipartFormHandle mf = MultipartFormHandle.getInstance();

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String baseDir = request.getSession().getServletContext().getRealPath("/");
            String sitename = ParamUtil.getParameter(request, "sitename");
            String s_siteid = ParamUtil.getParameter(request, "siteid");

            int siteid = 0;
            if (s_siteid!=null)  siteid = Integer.parseInt(s_siteid);

            mf.init(request,baseDir,sitename,siteid);
            String forward = mf.getForwardProgram();
            if (forward.equals("")) {
                forward = "error.jsp";
            }

            String param = mf.getForwardProgramParam();
            uploaderrormsg errormsg = mf.getErrorMsg();
            if (errormsg.getErrorPics().size()>0) errormsg.setErrorCode(1);    //有重复的文件名
            request.setAttribute("UploadMsg",errormsg);
            mf.clear();
            if (errormsg.getErrorPics().size() == 0) {
                getServletConfig().getServletContext().getRequestDispatcher("/" + forward + "?errcode=0&" + param).forward(request, response);
            }else
                getServletConfig().getServletContext().getRequestDispatcher("/upload/errorupload.jsp?errcode=" + errormsg.getErrorCode() + "&" +param).forward(request, response);
        }
        catch (Exception Ex) {
            Ex.printStackTrace();
        }
    }

    public void destroy() {
    }
}