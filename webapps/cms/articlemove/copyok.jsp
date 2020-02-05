<%@ page import="java.util.*,
                 java.io.File,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.extendAttr.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=GBK"%>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.Map" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=ϵͳ��ʱ�������µ�½!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();

    int scopeval = ParamUtil.getIntParameter(request, "scope", 0);                 //0����ѡ������µ�Ŀ����Ŀ��1��������Ŀ�µ��������µ�Ŀ����Ŀ
    int tcolumnID = ParamUtil.getIntParameter(request, "tcolumn", 0);              //����Ҫ��������Ŀ����ĿID
    int columnID = ParamUtil.getIntParameter(request, "column", 0);                //����ԭ��������ĿID
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
            boolean isSame = extendMgr.querySameExtendAttr(columnID, tcolumnID);   //�ж�ԭ��Ŀ��Ŀ����Ŀ�����Զ����Ƿ���ͬ����ִͬ�����¿�������������ͬ��ִ�п�������
            if (isSame)
                errcode = articleMgr.copyArticlesToColumn(scopeval,columnID, tcolumnID, selArticleIds, siteid, appPath, username);
            else
                errcode = 2;                                                        //��������ԭ��Ŀ��Ŀ����Ŀ������������Բ�ͬ������ִ�����¿�������
        } else {                                                                    //������ԭ��Ŀ��Ŀ����Ŀ�����Զ����Ƿ���ͬ����ִ�����¿������������ܶ�ʧ���µĲ������Զ���
            errcode = articleMgr.copyArticlesToColumn(scopeval,columnID, tcolumnID, selArticleIds, siteid, appPath, username);
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