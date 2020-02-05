package com.bizwink.update;

import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.util.*;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class UpdatePeer implements IUpdateManager {
    PoolServer cpool;

    public UpdatePeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IUpdateManager getInstance() {
        return CmsServer.getInstance().getFactory().getUpdateManager();
    }

    private static String SQL_GET_ALL_TEMPLATES = "select id,columnid,content from tbl_template order by id asc";

    /**
     * 获得所有的模板内容
     *
     * @return 所有的模板内容
     * @throws CmsException 异常
     */
    public List getAllTemplateInfos() throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ALL_TEMPLATES);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Update update = new Update();
                update.setId(rs.getInt("id"));
                update.setColumnid(rs.getInt("columnid"));
                update.setContent(DBUtil.getBigString(cpool.getType(), rs, "content"));
                list.add(update);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String SQL_GET_ALL_COLUMN_MARKS = "select id,content from tbl_mark where marktype=2";

    /**
     * 获得所有的栏目列表的标记
     *
     * @return 所有栏目列表标记
     * @throws CmsException 异常
     */
    public List getAllColumnMark() throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ALL_COLUMN_MARKS);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Update update = new Update();
                update.setId(rs.getInt("id"));
                if (cpool.getType().equals("oracle"))
                    update.setContent(DBUtil.getBigString(cpool.getType(), rs, "content"));
                else
                    update.setContent(rs.getString("content"));
                list.add(update);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 替换模板表中content字段中的[TAG][MARKID]markid[/MARKID][TAG]字符串为[TAG][MARKID]markid_columnid[/MARKID][TAG]
     *
     * @param id       tbl_template表中的id
     * @param columnid 模板所属的栏目的id
     * @param content  模板内容
     * @return true-成功;false-未成功
     * @throws CmsException 异常
     */
    public boolean replaceMark(int id, int columnid, String content) throws CmsException {
        boolean doflag1 = false;
        boolean doflag2 = false;
        Pattern p = Pattern.compile("\\[TAG\\]\\[MARKID\\][0-9]*\\[/MARKID\\]\\[/TAG\\]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(content);

        if ((content != null) && (content.indexOf("[TAG][MARKID]") != -1) && (content.indexOf("[/MARKID][/TAG]") != -1)) {
            while ((m != null) && (m.find())) {
                String tag = content.substring(m.start(), m.end());
                String markinner = tag.substring(tag.indexOf("[MARKID]") + 8, tag.indexOf("[/MARKID]")) + "_" + columnid;
                content = content.substring(0, m.start()) + "[TAG][MARKID]" + markinner + "[/MARKID][/TAG]" + content.substring(m.end());
                m = p.matcher(content);

                doflag1 = true;
            }
        }

        p = Pattern.compile("\\[TAG\\]\\[ORDERNUM\\][0-9]*\\[/ORDERNUM\\]\\[/TAG\\]", Pattern.CASE_INSENSITIVE);
        m = p.matcher(content);
        if ((content != null) && (content.indexOf("[TAG][ORDERNUM]") != -1) && (content.indexOf("[/ORDERNUM][/TAG]") != -1)) {
            while ((m != null) && (m.find())) {
                String tag = content.substring(m.start(), m.end());
                String markinner = tag.substring(tag.indexOf("[ORDERNUM]") + 10, tag.indexOf("[/ORDERNUM]")) + "_" + columnid;
                content = content.substring(0, m.start()) + "[TAG][MARKID]" + markinner + "[/MARKID][/TAG]" + content.substring(m.end());
                m = p.matcher(content);

                doflag2 = true;
            }
        }

        if (doflag1 || doflag2)
            updateTemplateContent(id, content);

        return true;
    }

    private static String SQL_UPDATE_TEMPLATE_CONTENT = "update tbl_template set content = ? where id = ?";

    /**
     * 更新模板表tbl_template中的content字段
     *
     * @param id      模板表中的id
     * @param content 模板表中的content
     * @throws CmsException 异常
     */
    public void updateTemplateContent(int id, String content) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_UPDATE_TEMPLATE_CONTENT);
                if (cpool.getType().equals("oracle"))
                    DBUtil.setBigString(cpool.getType(), pstmt, 1, content);
                else
                    pstmt.setString(1, content);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String SQL_UPDATE_MARK_CONTENT = "update tbl_mark set content = ? where id = ?";

    /**
     * 更新tbl_mark表的content字段，在content中增加[SELECTWAY]0[/SELECTWAY]
     *
     * @param id      tbl_mark表中的id
     * @param content tbl_mark表中的content
     * @throws CmsException 异常
     */
    public void updateMarkContent(int id, String content) throws CmsException {
        content = replaceContent(content);

        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_UPDATE_MARK_CONTENT);
                if (cpool.getType().equals("oracle"))
                    DBUtil.setBigString(cpool.getType(), pstmt, 1, content);
                else
                    pstmt.setString(1, content);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在栏目列表样式中增加[SELECTWAY]0[/SELECTWAY]字符串
     *
     * @param content 要替换的标记内容
     * @return 替换后的标记内容
     */
    private String replaceContent(String content) {
        if (content.indexOf("[/COLUMNIDS]") != -1) {
            String beginContent = content.substring(0, content.indexOf("[/COLUMNIDS]") + 12);
            String middleContent = "[SELECTWAY]0[/SELECTWAY]";
            String endContent = content.substring(content.indexOf("[/COLUMNIDS]") + 12);

            content = beginContent + middleContent + endContent;
        }
        return content;
    }
}
