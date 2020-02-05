<%@ page import="java.util.*,
                 java.io.File,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.extendAttr.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.bizwink.util.JSON" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();

    int scopeval = ParamUtil.getIntParameter(request, "scope", 0);                 //0拷贝选择的文章到目的栏目，1拷贝本栏目下的所有文章到目的栏目
    int tcolumnID = ParamUtil.getIntParameter(request, "tcolumn", 0);              //文章要被拷贝的目的栏目ID
    int columnID = ParamUtil.getIntParameter(request, "column", 0);                //文章原来所在栏目ID
    int execute = ParamUtil.getIntParameter(request, "execute", 0);
    String selArticleIds = ParamUtil.getParameter(request,"articles");
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int pageno = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request, "searchFlag");
    int errcode = 0;
    String appPath = application.getRealPath("/") + "sites" + File.separator + authToken.getSitename();

    try {
        IArticleManager articleMgr = ArticlePeer.getInstance();
        if(execute == 0) {
            IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
            boolean isSame = extendMgr.querySameExtendAttr(columnID, tcolumnID);   //判断原栏目与目标栏目的属性定义是否相同，相同执行文章移动操作，不相同则不执行拷贝操作
            if (isSame)
                errcode = articleMgr.moveArticlesToColumn(scopeval,columnID, tcolumnID, selArticleIds, siteid, appPath, username);
            else
                errcode = 2;                                                         //文章所在原栏目与目的栏目定义的文章属性不同，不能执行文章迁移操作
        } else {                                                                    //不考虑原栏目与目标栏目的属性定义是否相同，都执行文章移动操作，可能丢失文章的部分属性定义
            errcode = articleMgr.moveArticlesToColumn(scopeval,columnID, tcolumnID, selArticleIds, siteid, appPath, username);
        }
    } catch (ArticleException e) {
        errcode = -1;
        e.printStackTrace();
    }

    Map data = new HashMap<>();
    data.put("COLUMNID",columnID);
    data.put("ERRCODE",errcode);
    data.put("PAGENO",pageno);
    data.put("RANGE",range);
    data.put("FLAG",flag);
    data.put("ASCDESC",ascdesc);
    data.put("SEARCHFLAG",doSearchFlag);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>