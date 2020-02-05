<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.extendAttr.*"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="java.util.Map" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    int errcode = 0;
    int columnID = ParamUtil.getIntParameter(request, "column", 0);  //目的栏目ID
    int currentPage = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 40);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request, "searchFlag");
    String articleIDs = ParamUtil.getParameter(request, "articles");
    String[] tt = articleIDs.split(",");

    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    List<Integer> articleList =new ArrayList();
    for(int ii=0;ii<tt.length;ii++) {

        articleList.add(Integer.parseInt(tt[ii]));
    }

    int useArticleType = ParamUtil.getIntParameter(request, "useArticleType", 0);
    try {
        extendMgr.referArticle(articleList,username,columnID, authToken.getSiteID(), useArticleType);
    } catch (ExtendAttrException e) {
        errcode = -1;
        e.printStackTrace();
    }

    Map data = new HashMap<>();
    data.put("COLUMNID",columnID);
    data.put("ERRCODE",errcode);
    data.put("START",currentPage);
    data.put("RANGE",range);
    data.put("FLAG",flag);
    data.put("ASCDESC",ascdesc);
    data.put("SEARCHFLAG",doSearchFlag);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>