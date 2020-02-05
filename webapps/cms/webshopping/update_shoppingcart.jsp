<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    List oldlist = (List) session.getAttribute("en_list");
    if (oldlist == null) {
        response.sendRedirect("/index.jsp");
        return;
    }
    int pid = ParamUtil.getIntParameter(request, "pid", 0);
    int num = ParamUtil.getIntParameter(request, "num", 0);

    String outstr = "false";

    if ((pid != 0) && (num != 0)) {
        String insertstr = "";

        if (oldlist.size() > 0) {
            String str = "";
            String proidstr = "";
            String pronumstr = "";
            String typestr = "";
            int proid = 0;
            int pronum = 0;
            int sort = 0;
            boolean existflag = false;

            for (int i = 0; i < oldlist.size(); i++) {
                str = (String) oldlist.get(i);
                proidstr = str.substring(0, str.indexOf("_"));
                proid = Integer.parseInt(proidstr);

                if (pid == proid) {
                    existflag = true;
                    pronumstr = str.substring(str.indexOf("_") + 1, str.lastIndexOf("_"));
                    typestr = str.substring(str.lastIndexOf("_") + 1, str.length());
                    pronum = Integer.parseInt(pronumstr);
                    sort = i;
                    break;
                }
            }
            if (existflag) {
                pronum = num;
                insertstr = String.valueOf(proid) + "_" + String.valueOf(pronum) + "_" + typestr;
                oldlist.set(sort, insertstr);
                outstr = "true";
            }
        }
    }
    else{
        outstr = "false";
    }
    out.write(outstr);
%>