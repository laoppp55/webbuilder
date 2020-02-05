<%@ page import="com.xml.TreatmentXML" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.modelManager.IModelManager" %>
<%@ page import="com.bizwink.cms.modelManager.ModelPeer" %>
<%@ page import="com.bizwink.cms.modelManager.Model" %>
<%@ page import="com.bizwink.cms.sitesetting.ISiteInfoManager" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoPeer" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xml.Form" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.xml.FormPeer" %>
<%@ page import="com.xml.IFormManager" %>
<%@ page contentType="text/html;charset=gbk" %>
<%

    String templatestr= ParamUtil.getParameter(request,"createform");
    int templateid=0;
    String str="";
    int siteid=0;
    String sitename="";
    String baseDir = application.getRealPath("/");
    //System.out.println("templatestr=");
    if(templatestr.indexOf("_")!=-1)
    {
        str=templatestr.substring(0,templatestr.indexOf("_"));
        System.out.println("str="+str);
        try{
        templateid=Integer.parseInt(str);
        }catch(Exception e){
            templateid=0;
        }
        if(templateid>0)
        {
            IModelManager modelpeer= ModelPeer.getInstance();
            Model model=modelpeer.getModel(templateid);
            siteid=model.getSiteID();
            if(siteid>0)
            {
                ISiteInfoManager sitepeer= SiteInfoPeer.getInstance();
                SiteInfo site=sitepeer.getSiteInfo(siteid);
                sitename=site.getDomainName();
                sitename=sitename.replaceAll("\\.","_");
                TreatmentXML treatxml=new TreatmentXML();
                List list=treatxml.getForm(baseDir+"\\"+sitename+"\\_prog\\tbl_"+sitename+"_"+templatestr+".xml");
                List insertvaluelist=new ArrayList();
                String tablename="";
                for(int i=0;i<list.size();i++)
                {
                    Form form=(Form)list.get(i);
                    String inputname=form.getInputname();
                    tablename=form.getCreateformname();
                    String bianliang=ParamUtil.getParameter(request,inputname);
                    insertvaluelist.add(inputname+"_"+bianliang);
                    //System.out.println("bianlian="+bianliang);
                   // System.out.println("=========================");
                   // System.out.println("field="+form.getInputname());
                }
                IFormManager formpeer=FormPeer.getInstance();

                formpeer.insertForm(insertvaluelist,tablename,siteid,templateid, list);
                //System.out.println("sitename="+sitename+"   tablename="+tablename);
            }
        }
    }

%>