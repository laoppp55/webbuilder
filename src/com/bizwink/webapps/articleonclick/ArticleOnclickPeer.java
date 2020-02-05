package com.bizwink.webapps.articleonclick;

import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-10-31
 * Time: 14:43:51
 * To change this template use File | Settings | File Templates.
 */
public class ArticleOnclickPeer implements IArticleOnclickManager{

    PoolServer cpool;

    public ArticleOnclickPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IArticleOnclickManager getInstance() {
        return CmsServer.getInstance().getFactory().getArticleOnclickManager();
    }
    public int updateArticleOnclick(int articleid)
    {
        int i=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        try{
            con=cpool.getConnection();
            con.setAutoCommit(false);
            String sql="update tbl_article set clicknum=(select (clicknum+1) from tbl_article where id="+articleid+")  where id="+articleid;
            pstmt=con.prepareStatement(sql);
            i=pstmt.executeUpdate();
            pstmt.close();
            con.commit();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }

        return i;
    }
    public int getArticleClicknum(int articleid)
    {
        int i=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        try{
            con=cpool.getConnection();
            String sql="select clicknum from tbl_article where id="+articleid;
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            if(res.next())
            {
                i=res.getInt("clicknum");
            }
            res.close();
            pstmt.close();

        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return i;
    }
}
