<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.modelManager.IModelManager" %>
<%@ page import="com.bizwink.cms.modelManager.ModelPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.modelManager.Model" %>
<%@ page contentType="text/html;charset=gbk" %>
<script language="Javascript">


        function select_the_template(id) {
            var value=0;
            for(var i=0; i<form.site_radio.length; i++) {
                if (form.site_radio[i].checked) {
                    value = form.site_radio[i].value;
                    break;
                }
            }

            //alert(value);
            if (value==-1) {
                alert("ÇëÑ¡ÔñÒ»Ì×ÍøÕ¾Ä£°å");
                return false;
            } else {
                alert(value);
                alert(opener.opener.document.regform.sitetemplate.value);
                opener.opener.document.regform.SHARETEMPLATENUM.value =value;

               opener.window.close();
                window.close();


            }
        }
    </script><form name="form" >
<%
    int siteid= ParamUtil.getIntParameter(request,"id",-1);
    IModelManager modelpeer= ModelPeer.getInstance();
    List list= modelpeer.getSiteidModel(siteid);
    System.out.println("list size="+list.size());
    for(int i=0;i<list.size();i++)
    {
        Model model=(Model)list.get(i);
%>
  <input type="radio" name="site_radio" onclick="javascript:select_the_template(<%=model.getTempnum()%>)" value="<%=model.getTempnum()%>"  /> <%=model.getID()%>&nbsp;<%=model.getTempnum()%>&nbsp;&nbsp;&nbsp;
<%}%> </form>