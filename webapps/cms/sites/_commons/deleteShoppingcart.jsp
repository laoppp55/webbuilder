<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    int proid = ParamUtil.getIntParameter(request, "pid", -1);
    String sitename = ParamUtil.getParameter(request,"sitename");
    List list = new ArrayList();
    list = (List) session.getAttribute("en_list");
    if (list == null) {
        List glist = new ArrayList();
        session.setAttribute("en_list", glist);
    }

    if (proid > 0) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String buystr = (String) list.get(i);
                String pid = buystr.substring(0, buystr.indexOf("_"));
                int productid = Integer.parseInt(pid);
                if(productid == proid){
                    list.remove(i);
                    break;
                }
            }

        }
    }
    response.sendRedirect("/"+sitename+"/_prog/shoppingcar.jsp");
%>