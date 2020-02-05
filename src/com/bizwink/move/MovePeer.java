package com.bizwink.move;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.util.*;

public class MovePeer implements IMoveManager {
    PoolServer cpool;

    public MovePeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IMoveManager getInstance() {
        return CmsServer.getInstance().getFactory().getMoveManager();
    }

    private static String SQL_UPDATE_COLUMNINFO1 =
            "UPDATE TBL_Column SET SiteID = ?, ParentID = ?, Dirname = ? WHERE ID = ?";

    private static String SQL_UPDATE_COLUMNINFO2 =
            "UPDATE TBL_Column SET SiteID = ?, Dirname = ? WHERE ID = ?";

    private static String SQL_UPDATE_ARTICLEINFO1 =
            "UPDATE TBL_Article SET Dirname = ?,PubFlag = 1,Content = ? WHERE ID = ?";

    private static String SQL_UPDATE_ARTICLEINFO2 =
            "UPDATE TBL_Article SET Dirname = ?,PubFlag = 1,Content = ?,ColumnID = ? WHERE ID = ?";

    public void Moving(Move move) throws MoveException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int errorID = 0;
        int count = 0;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                if (move.getMoveType() == 1)         //移动栏目
                {
                    //更新要移动的栏目的SiteID,Dirname及ParentID
                    List columnList = new ArrayList();
                    String columnIDs = getColumnIDs(move.getOrgColumnID());
                    String SQLStr = "SELECT ID,Dirname FROM TBL_Column WHERE ID IN " + columnIDs;

                    pstmt = conn.prepareStatement(SQLStr);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        columnList.add(rs.getInt(1) + "," + rs.getString(2));
                    }
                    rs.close();
                    pstmt.close();

                    for (int i = 0; i < columnList.size(); i++) {
                        String tempstr = (String) columnList.get(i);
                        int columnID = Integer.parseInt(tempstr.substring(0, tempstr.indexOf(",")));
                        String dirName = tempstr.substring(tempstr.indexOf(",") + 1);

                        if (move.getOrgColumnID() == columnID) {
                            pstmt = conn.prepareStatement(SQL_UPDATE_COLUMNINFO1);
                            pstmt.setInt(1, move.getSiteID());
                            pstmt.setInt(2, move.getAimColumnID());
                            pstmt.setString(3, move.getDirName() + dirName);
                            pstmt.setInt(4, columnID);
                        } else {
                            pstmt = conn.prepareStatement(SQL_UPDATE_COLUMNINFO2);
                            pstmt.setInt(1, move.getSiteID());
                            pstmt.setString(2, move.getDirName() + dirName);
                            pstmt.setInt(3, columnID);
                        }
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }

                if (move.getMoveType() == 1)     //移动栏目
                {
                    pstmt = conn.prepareStatement(SQL_UPDATE_ARTICLEINFO1);
                } else {
                    pstmt = conn.prepareStatement(SQL_UPDATE_ARTICLEINFO2);
                }

                List articleList = move.getArticleList();
                for (int j = 0; j < articleList.size(); j++) {
                    Article article = (Article) articleList.get(j);

                    int articleID = article.getID();
                    errorID = articleID;
                    String dirName = article.getDirName();
                    String content = article.getContent();
                    String sitename = move.getSiteName();
                    String _sitename = StringUtil.replace(sitename, ".", "_");
                    dirName = move.getDirName() + dirName.substring(0, dirName.length() - 1);
                    if (content == null) content = "";
                    content = StringUtil.replace(content, "/webbuilder/sites/" + _sitename, "http://" + sitename);
                    if(content.indexOf("sites/") != -1) content = StringUtil.replace(content, "sites/" + _sitename, "http://" + sitename);

                    if (move.getMoveType() == 1)     //移动栏目
                    {
                        pstmt.setString(1, dirName);
                        DBUtil.setBigString(cpool.getType(), pstmt, 2, content);
                        pstmt.setInt(3, articleID);
                    } else         //移动文章
                    {
                        pstmt.setString(1, move.getDirName() + "/");
                        DBUtil.setBigString(cpool.getType(), pstmt, 2, content);
                        pstmt.setInt(3, move.getAimColumnID());
                        pstmt.setInt(4, articleID);
                    }
                    pstmt.addBatch();
                    count++;
                }

                pstmt.executeBatch();
                pstmt.close();

                conn.commit();
            }
            catch (Exception e) {
                System.out.println("Error ArticleID = " + errorID);
                System.out.println("Count = " + count);
                e.printStackTrace();
                conn.rollback();
                throw new MoveException("Database exception: create column failed.");
            }
            finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    }
                    catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Error ArticleID = " + errorID);
            throw new MoveException("Database exception: can't rollback?" + e.getMessage());
        }
    }

    public List getArticles(int columnID, int moveType) throws MoveException {
        String columnIDs = getColumnIDs(columnID);
        String SQLStr;
        if (moveType == 0)
            SQLStr = "SELECT id,columnid,maintitle,vicetitle,source,author,dirname,content from TBL_Article WHERE columnID = " + columnID;
        else
            SQLStr = "SELECT id,columnid,maintitle,vicetitle,source,author,dirname,content from TBL_Article WHERE columnID IN " + columnIDs;

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQLStr);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = load(rs);
                list.add(article);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    public int getArticlesNum(int columnID) throws MoveException {
        String columnIDs = getColumnIDs(columnID);
        String SQLStr = "SELECT Count(*) FROM TBL_Article WHERE columnID IN " + columnIDs;

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQLStr);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private int[] getSubTreeColumnIDList(node[] treenodes, int columnID) {
        int[] cid = new int[treenodes.length + 1];
        int[] pid = new int[treenodes.length];
        int nodenum = 1;
        int i;
        int j = 1;
        pid[1] = columnID;

        do {
            columnID = pid[nodenum];
            cid[j] = columnID;
            j = j + 1;
            nodenum = nodenum - 1;
            for (i = 0; i < treenodes.length; i++) {
                if (treenodes[i].getLinkPointer() == columnID) {
                    nodenum = nodenum + 1;
                    pid[nodenum] = treenodes[i].getId();
                }
            }
        } while (nodenum >= 1);
        cid[0] = j - 1;            //cid[0]元素保存找到的子节点的数目

        return cid;
    }

    private String getColumnIDs(int columnID) {
        String result = "(";
        Tree colTree = TreeManager.getInstance().getTree();
        node[] treenodes = colTree.getAllNodes();
        int[] cid = getSubTreeColumnIDList(treenodes, columnID);
        for (int i = 0; i < cid[0] - 1; i++) {
            result = result + cid[i + 1] + ",";
        }
        result = result + cid[cid[0]] + ")";

        return result;
    }

    Article load(ResultSet rs) throws SQLException {
        Article article = new Article();
        try {
            article.setID(rs.getInt("id"));
            article.setColumnID(rs.getInt("columnid"));
            article.setMainTitle(rs.getString("maintitle"));
            article.setViceTitle(rs.getString("vicetitle"));
            article.setSource(rs.getString("source"));
            article.setAuthor(rs.getString("author"));
            article.setDirName(rs.getString("dirname"));
            article.setContent(DBUtil.getBigString(cpool.getType(), rs, "content"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return article;
    }
}
