<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.jolbox.bonecp.BoneCPDataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.bizwink.util.NewArticle" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-3-25
  Time: 下午9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
    List<NewArticle> articles = new ArrayList<NewArticle>();
    if (context!=null) {
        BoneCPDataSource dataSource = (BoneCPDataSource)context.getBean("myDataSource");
        Connection conn= null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        SimpleDateFormat formatter=new SimpleDateFormat ("yyyyMMdd"); ;

        try {
            conn= dataSource.getConnection();
            if (conn!=null) {
                pstmt = conn.prepareStatement("select ta.id, ta.maintitle,ta.createdate,ta.dirname,st.sitename from tbl_article ta,tbl_siteinfo st " +
                        "where rownum<5 and ta.siteid=st.siteid and ta.pubflag=0 and st.siteid in(379,380,381,382,383,384,385,386,387) " +
                        "order by ta.createdate desc");
                rs = pstmt.executeQuery();
                while (rs.next()){
                    NewArticle newArticle = new NewArticle();
                    newArticle.setId(rs.getInt("id"));
                    newArticle.setMainttile(rs.getString("maintitle"));
                    String createdate = formatter.format(rs.getTimestamp("createdate"));
                    newArticle.setCreatedate(createdate);
                    newArticle.setDirname(rs.getString("dirname"));
                    newArticle.setSitename(rs.getString("sitename"));
                    newArticle.setIamgefile(rs.getString("articlepic"));
                    articles.add(newArticle);
                }
            }
        }catch(SQLException exp) {
            exp.printStackTrace();
        } finally {
            rs.close();
            pstmt.close();
            conn.close();
        }

        Gson gson = new Gson();
        String jsondata=null;
        if (articles.size() > 0){
            jsondata = gson.toJson(articles);
            System.out.println(jsondata);
            out.print(jsondata);
            out.flush();
        } else {
            out.print("nodata");
            out.flush();
        }
        return;
    }
%>