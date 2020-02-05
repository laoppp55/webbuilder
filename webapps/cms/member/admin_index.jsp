<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.audit.*,
                 com.bizwink.cms.register.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.bizwink.vo.VoUser" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect( "../login.jsp" );
        return;
    }
    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message","无系统管理员的权限");
        response.sendRedirect("../index.jsp");
        return;
    }

    int resultnum = ParamUtil.getIntParameter(request, "resultnum", 20);
    int pageno = ParamUtil.getIntParameter(request, "pageno", 0);
    String keyword = ParamUtil.getParameter(request, "search");
    int searchflag=ParamUtil.getIntParameter(request,"searchflag",-1);
    int doSearchFlag=ParamUtil.getIntParameter(request,"doSearch",-1);

    IRegisterManager registerMgr = RegisterPeer.getInstance();
    IAuditManager auditMgr = AuditPeer.getInstance();
    List userList = new ArrayList();
    int userCount = 0;
    int startnum = 0 ;
    startnum = (pageno-1)*resultnum;

    if(searchflag == -1){
        userCount = auditMgr.getUsersCount("");
        userList = auditMgr.getUsers(resultnum,startnum,"");
    }else{
        if(searchflag==1) {
            userCount = auditMgr.getUsersCount(keyword);
            userList = auditMgr.getUsers(resultnum,startnum,keyword);
        }
        if(searchflag==2) {
            userCount = auditMgr.getSitenameCount(keyword);
            userList = auditMgr.getSitename(resultnum,startnum,keyword);
        }
        if(searchflag==3) {
            userCount = auditMgr.getNicknameCount(keyword);
            userList = auditMgr.getNickname(resultnum,startnum,keyword);
        }
        if(searchflag==4) {
            userCount = auditMgr.getEmailCount(keyword);
            userList = auditMgr.getEmail(resultnum,startnum,keyword);
        }
    }

    List<VoUser> voUserList = new ArrayList<>();
    if(searchflag==2) {
        for (int i = 0; i < userList.size(); i++) {
            String sitename = "&nbsp;";
            VoUser voUser = new VoUser();
            Register user = (Register) userList.get(i);
            sitename = user.getSiteName();
            int siteID = user.getSiteID();
            if (user != null) {
                User users = auditMgr.getUserOne(siteID);
                if (users != null) {
                    voUser.setUSERID(users.getID());
                    voUser.setNICKNAME(StringUtil.gb2iso4View(users.getNickName()));
                    voUser.setEMAIL(users.getEmail());
                    voUser.setPHONE(users.getPhone());
                    voUser.setSITENAME(sitename);
                    voUserList.add(voUser);
                }
            }
        }
    } else {
        for(int ii=0; ii<userList.size(); ii++) {
            User user = (User)userList.get(ii);
            int siteID = user.getSiteID();
            String userID = user.getUserID();
            String sitename = "&nbsp;";
            VoUser voUser = new VoUser();
            voUser.setUSERID(user.getUserID());
            voUser.setNICKNAME(StringUtil.gb2iso4View(user.getNickName()));
            voUser.setEMAIL(user.getEmail());
            voUser.setPHONE(user.getPhone());
            if (!userID.equalsIgnoreCase("admin") ) {
                Register reg = registerMgr.getSite(siteID);
                if(reg != null) sitename = reg.getSiteName();
            }
            voUser.setSITENAME(sitename);
            voUserList.add(voUser);
        }
    }

    Map data = new HashMap<>();
    data.put("DOSEARCH",doSearchFlag);
    data.put("COUNT",userCount);
    data.put("DATA",voUserList);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    //System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>