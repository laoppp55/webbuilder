<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.news.IArticleManager" %>
<%@ page import="com.bizwink.cms.news.ArticlePeer" %>
<%@ page import="com.bizwink.cms.viewFileManager.IViewFileManager" %>
<%@ page import="com.bizwink.cms.viewFileManager.viewFilePeer" %>
<%@ page import="com.bizwink.cms.viewFileManager.ViewFile" %>
<%@ page import="com.bizwink.cms.news.Article" %>
<%@ page import="com.zuijinsee.ZuiJinSee" contentType="text/html;charset=gbk" %>
<%
    Cookie cookies[] = request.getCookies();
    String sitename = request.getServerName();
    int viewid = ParamUtil.getIntParameter(request, "viewid", -1);
    int num = ParamUtil.getIntParameter(request, "num", 4);
    String getarticleids[] = null;
    if (cookies != null) {
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(sitename)) {
                String getarticleidstr = URLDecoder.decode(cookies[i].getValue(), "UTF-8");
                getarticleids = getarticleidstr.split("_");
            }
        }
    }
    IArticleManager articlepeer= ArticlePeer.getInstance();
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    ViewFile viewfile =viewfileMgr.getAViewFile(viewid);
    ZuiJinSee  zjs=new ZuiJinSee();
    String content=viewfile.getContent();
    String stylestr1="";
    String stylestr2="";
    String stylestr3="";
    String stylecontent="";

    //先取上面部分
    if(content!=null){
        if(content.indexOf("<!--ROW-->")!=-1)
        {
            stylestr1=content.substring(0,content.indexOf("<!--ROW-->"));
            stylestr2=content.substring(content.indexOf("<!--ROW-->")+"<!--ROW-->".length(),content.lastIndexOf("<!--ROW-->"));
            stylestr3=content.substring(content.lastIndexOf("<!--ROW-->"),content.length());
        }
    }

    if (getarticleids != null) {
        int articleid = -1;
        int articlecount = 0;
        for (int i = getarticleids.length; i > 0; i--) {
            try {
                articleid = Integer.parseInt(getarticleids[i]);
                articlecount = articlecount + 1;
            } catch (Exception e) {
                articleid = -1;
            }
            if (articleid != -1) {
                Article article=articlepeer.getArticle(articleid);
                if (articlecount < num) {
                        stylecontent=stylecontent+stylestr2;
                        stylecontent=zjs.replaceAllContent(stylecontent,article,sitename);
                } else {
                    break;
                }
            }
        }
    }

    String buffer = stylestr1+stylecontent+stylestr3;
    buffer = StringUtil.replace(buffer,"/webbuilder/sites/www_stenders_cn","");

    out.write(buffer);
    //删除

/*Cookie cookie = new Cookie(sitename, null);
cookie.setMaxAge(-1);
response.addCookie(cookie);*/


%>