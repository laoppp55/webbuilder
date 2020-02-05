<%@ page import="com.bizwink.cms.extendAttr.ExtendAttrPeer,
                 com.bizwink.cms.extendAttr.IExtendAttrManager,
                 com.bizwink.cms.news.Article,
                 com.bizwink.cms.news.ArticlePeer,
                 com.bizwink.cms.news.IArticleManager,
                 com.bizwink.cms.news.relatedArticle,
                 com.bizwink.cms.security.Auth"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.bizwink.cms.util.filter" %>
<%@ page import="java.util.*" %>
<%@ page import="org.json.*" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();
    int articleid = ParamUtil.getIntParameter(request, "article", 0);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    String datainfos = ParamUtil.getParameter(request, "infos");
    if (datainfos!=null) datainfos = URLDecoder.decode(datainfos,"utf-8");
    datainfos =  filter.excludeHTMLCode(datainfos);
    int useArticleType = ParamUtil.getIntParameter(request,"useArticleType",0);
    int errcode = 0;

    IArticleManager articleManager = ArticlePeer.getInstance();
    Article article = articleManager.getArticle(articleid);
    String maintitle = article.getMainTitle();
    maintitle = StringUtil.gb2iso4View(maintitle);
    List relArticle = articleManager.getRelatedArticles(articleid);
    if (doCreate) {
        List rlist = new ArrayList();
        if (datainfos!=null) {
            JSONObject jsonObject = new JSONObject(datainfos.substring(1,datainfos.length()-1));
            JSONArray jsonArray = jsonObject.getJSONArray("DATA");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                int columnid = jsonObj.getInt("id");
                String columnname = jsonObj.getString("name");
                relatedArticle relatedArticle = new relatedArticle();
                relatedArticle.setJointedID(columnid);
                relatedArticle.setChineseName(columnname);
                relatedArticle.setTitle(maintitle);
                relatedArticle.setContenttype(useArticleType);
                rlist.add(relatedArticle);
            }
        }
        IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
        errcode = extendMgr.addRelatedArticles(rlist,username,articleid,siteid);
    } else {
        errcode = 1;
    }

    Map data = new HashMap<>();
    data.put("RESULTDATA",relArticle);
    data.put("ERRCODE",errcode);
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    //System.out.println(jsondata);
    JSON.setPrintWriter(response, jsondata, "utf-8");
%>