package com.bizwink.cms.tree;

import java.sql.*;
import java.io.*;
import java.util.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.util.*;

public class TreeManager implements ITree {
    PoolServer cpool;

    public TreeManager(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ITree getInstance() {
        return CmsServer.getInstance().getFactory().getTreeManager();
    }

    private static final String GET_CHILD =
            "SELECT id FROM tbl_column WHERE parentID=? ORDER BY id";

    private static final String CHILD_COUNT =
            "SELECT count(*) FROM tbl_column WHERE parentID=?";

    private static final String INDEX_OF_CHILD =
            "SELECT id FROM tbl_column WHERE parentID=? ORDER BY id";

    private static final String GET_PARENTID = "SELECT parentID FROM tbl_column WHERE ID=?";

    private int getNodeNum(String rootPath) {
        int nodenum = 1;
        int totalNodeNum = 0;
        int i;
        List dirList = new ArrayList();
        String fileName;
        String rootPathName;

        dirList.add(0, rootPath);
        if (dirList!=null) {
            while (!dirList.isEmpty()) {
                nodenum = nodenum - 1;

                fileName = (String) dirList.get(0);
                dirList.remove(0);

                File file = new File(fileName);
                String[] dirName = file.list();

                if (dirName != null) {
                    for (i = 0; i < dirName.length; i++) {
                        File dir = new File(fileName + dirName[i]);
                        if (dir.isDirectory()) {
                            totalNodeNum = totalNodeNum + 1;
                            rootPathName = fileName + dirName[i] + java.io.File.separator;
                            dirList.add(nodenum, rootPathName);
                            nodenum = nodenum + 1;
                        }
                    }
                }
            }
        }

        return totalNodeNum;
    }

    public Tree getDirTree(String rootdir) {
        int nodenum = 1;
        int parentNodeNum;
        int id = 0;
        int i;
        List dirList = new ArrayList();
        node[] dirTreeNode = new node[getNodeNum(rootdir) + 1];
        int[] rpid = new int[getNodeNum(rootdir) + 1];
        String fileName;
        String rootPathName;

        //定义第一个结点
        dirList.add(id, rootdir);
        dirTreeNode[id] = new node();
        dirTreeNode[id].setEnName("原始模板");
        dirTreeNode[id].setColumnURL(rootdir);
        dirTreeNode[id].setLinkPointer(-1);
        dirTreeNode[id].setId(id);
        rpid[id] = id;

        //循环通过所有的结点
        while (!dirList.isEmpty()) {
            nodenum = nodenum - 1;

            fileName = (String) dirList.get(nodenum);
            dirList.remove(nodenum);

            File file = new File(fileName);
            String[] dirName = file.list();
            rootPathName = fileName;
            parentNodeNum = rpid[nodenum];

            if (dirName!=null) {
                for (i = 0; i < dirName.length; i++) {
                    File dir = new File(fileName + dirName[i]);
                    if (dir.isDirectory()) {
                        id = id + 1;
                        dirTreeNode[id] = new node();
                        dirTreeNode[id].setEnName(dirName[i]);
                        dirTreeNode[id].setColumnURL(rootPathName + dirName[i]);
                        dirTreeNode[id].setLinkPointer(parentNodeNum);
                        dirTreeNode[id].setId(id);
                        rootPathName = fileName + dirName[i] + java.io.File.separator;
                        dirList.add(nodenum, rootPathName);
                        rpid[nodenum] = id;
                        nodenum = nodenum + 1;
                    }
                }
            }
        }

        return new Tree(id + 1, "", dirTreeNode);
    }

    private static final String SELECTNODE = "SELECT * FROM tbl_column order by ParentID,orderid";

    private static final String PID = "SELECT DISTINCT ParentID from tbl_column";

    public Tree getTree() {
        Connection con = null;
        PreparedStatement pstmt;
        int rowNum = 0;
        int pidNum = 0;
        Tree err = new Tree(1, "error");

        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(SELECTNODE);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rowNum = rowNum + 1;
            }
            rs.close();
            pstmt.close();

            int[] pID = new int[rowNum];
            int[] id = new int[rowNum];
            String[] cname = new String[rowNum];
            String[] ename = new String[rowNum];
            int[] orderID = new int[rowNum];
            String[] links = new String[rowNum];
            node[] arr = new node[rowNum];
            int i = 0;
            int parentID;

            pstmt = con.prepareStatement(SELECTNODE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pID[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderID[i] = rs.getInt("orderID");
                links[i] = rs.getString("dirname");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = con.prepareStatement(PID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pidNum = pidNum + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = con.prepareStatement(PID);
            rs = pstmt.executeQuery();
            int k = 0;
            int j = 0;
            int ordernum;
            int[] realPID = new int[pidNum];
            while (rs.next()) {
                ordernum = 0;
                parentID = rs.getInt("parentID");
                realPID[j] = parentID;
                //寻找该父节点下的所有子节点
                for (i = 0; i < rowNum; i++) {
                    if (parentID == pID[i]) {
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);
                        arr[k].setOrderNum(orderID[i]);
                        arr[k].setColumnURL(links[i]);
                        k = k + 1;
                        ordernum = ordernum + 1;
                    }
                }
                j = j + 1;
            }
            rs.close();
            pstmt.close();

            Tree columnTree = new Tree(rowNum, "", 0, arr, realPID);
            columnTree.setPidNum(pidNum);
            return columnTree;
        }
        catch (Exception e) {
            System.err.println("Error" + e);
            e.printStackTrace();
        }
        finally {
            cpool.freeConnection(con);
        }

        return err;
    }

    private static final String SELECT_NODE =
            "SELECT a.id,a.ParentID,a.Cname,a.Ename,a.orderID,a.Dirname FROM tbl_column a,tbl_siteinfo b where " +
                    "a.siteID=b.siteID and (b.berefered=1 or a.siteID=?) order by a.ParentID,a.orderid";

    private static final String SELECT_PID =
            "SELECT DISTINCT a.ParentID from tbl_column a,tbl_siteinfo b where a.siteID=b.siteID and (b.berefered=1 or a.siteID=?)";

    public Tree getGlobalTree(int siteID) {
        Connection con = null;
        PreparedStatement pstmt;
        int rowNum = 0;
        int pidNum = 0;
        Tree err = new Tree(1, "error");

        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(SELECT_NODE);
            pstmt.setInt(1, siteID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rowNum = rowNum + 1;
            }
            rs.close();

            int[] pID = new int[rowNum];
            int[] id = new int[rowNum];
            String[] cname = new String[rowNum];
            String[] ename = new String[rowNum];
            int[] orderID = new int[rowNum];
            String[] links = new String[rowNum];
            node[] arr = new node[rowNum];
            int i = 0;
            int parentID;

            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pID[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderID[i] = rs.getInt("orderID");
                links[i] = rs.getString("dirname");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = con.prepareStatement(SELECT_PID);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pidNum = pidNum + 1;
            }
            rs.close();

            rs = pstmt.executeQuery();
            int k = 0;
            int j = 0;
            int ordernum;
            int[] realPID = new int[pidNum];
            while (rs.next()) {
                ordernum = 0;
                parentID = rs.getInt("parentID");
                realPID[j] = parentID;
                //寻找该父节点下的所有子节点
                for (i = 0; i < rowNum; i++) {
                    if (parentID == pID[i]) {
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);
                        arr[k].setOrderNum(orderID[i]);
                        arr[k].setColumnURL(links[i]);
                        k = k + 1;
                        ordernum = ordernum + 1;
                    }
                }
                j = j + 1;
            }
            rs.close();
            pstmt.close();

            Tree columnTree = new Tree(rowNum, "", 0, arr, realPID);
            columnTree.setPidNum(pidNum);
            return columnTree;
        }
        catch (Exception e) {
            System.err.println("Error" + e);
            e.printStackTrace();
        }
        finally {
            cpool.freeConnection(con);
        }
        return err;
    }

    //获得允许被复制栏目、文章推送、文章移动的所有站点  1-允许站间复制栏目 2-允许站间文章推送 3-允许站间文章移动
    public Tree getGlobalTree(int siteID, int flag) {
        Connection con = null;
        PreparedStatement pstmt;
        int rowNum = 0;
        int pidNum = 0;
        Tree err = new Tree(1, "error");

        String SELECT_NODE2 = "";
        String SELECT_PID2 = "";
        if (flag == 1) {
            SELECT_NODE2 = "SELECT a.id,a.ParentID,a.Cname,a.Ename,a.orderID,a.Dirname FROM tbl_column a,tbl_siteinfo b where " +
                    "a.siteID=b.siteID and (b.becopycolumn=1 or a.siteID=?) order by a.ParentID,a.orderid";

            SELECT_PID2 =
                    "SELECT DISTINCT a.ParentID from tbl_column a,tbl_siteinfo b where a.siteID=b.siteID and (b.becopycolumn=1 or a.siteID=?)";
        } else if (flag == 2) {
            SELECT_NODE2 = "SELECT a.id,a.ParentID,a.Cname,a.Ename,a.orderID,a.Dirname FROM tbl_column a,tbl_siteinfo b where " +
                    "a.siteID=b.siteID and (b.pushArticle=1 or a.siteID=?) order by a.ParentID,a.orderid";

            SELECT_PID2 =
                    "SELECT DISTINCT a.ParentID from tbl_column a,tbl_siteinfo b where a.siteID=b.siteID and (b.pushArticle=1 or a.siteID=?)";
        } else if (flag == 3) {
            SELECT_NODE2 = "SELECT a.id,a.ParentID,a.Cname,a.Ename,a.orderID,a.Dirname FROM tbl_column a,tbl_siteinfo b where " +
                    "a.siteID=b.siteID and (b.moveArticle=1 or a.siteID=?) order by a.ParentID,a.orderid";

            SELECT_PID2 =
                    "SELECT DISTINCT a.ParentID from tbl_column a,tbl_siteinfo b where a.siteID=b.siteID and (b.moveArticle=1 or a.siteID=?)";
        }

        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(SELECT_NODE2);
            pstmt.setInt(1, siteID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rowNum = rowNum + 1;
            }
            rs.close();

            int[] pID = new int[rowNum];
            int[] id = new int[rowNum];
            String[] cname = new String[rowNum];
            String[] ename = new String[rowNum];
            int[] orderID = new int[rowNum];
            String[] links = new String[rowNum];
            node[] arr = new node[rowNum];
            int i = 0;
            int parentID;

            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pID[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderID[i] = rs.getInt("orderID");
                links[i] = rs.getString("dirname");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = con.prepareStatement(SELECT_PID2);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pidNum = pidNum + 1;
            }
            rs.close();

            rs = pstmt.executeQuery();
            int k = 0;
            int j = 0;
            int ordernum;
            int[] realPID = new int[pidNum];
            while (rs.next()) {
                ordernum = 0;
                parentID = rs.getInt("parentID");
                realPID[j] = parentID;
                //寻找该父节点下的所有子节点
                for (i = 0; i < rowNum; i++) {
                    if (parentID == pID[i]) {
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);
                        arr[k].setOrderNum(orderID[i]);
                        arr[k].setColumnURL(links[i]);
                        k = k + 1;
                        ordernum = ordernum + 1;
                    }
                }
                j = j + 1;
            }
            rs.close();
            pstmt.close();

            Tree columnTree = new Tree(rowNum, "", 0, arr, realPID);
            columnTree.setPidNum(pidNum);
            return columnTree;
        }
        catch (Exception e) {
            System.err.println("Error" + e);
            e.printStackTrace();
        }
        finally {
            cpool.freeConnection(con);
        }
        return err;
    }

    //获得公司分类信息
    private static final String SQL_CompanyTypeCount = "SELECT count(*) FROM tbl_companyclass where siteid = ?";
    private static final String SQL_CompanyType = "SELECT * FROM tbl_companyclass where siteid = ? and parentID > 0 order by orderID";
    private static final String SQL_ComapnyTypeRoot = "SELECT * FROM tbl_companyclass where siteid = ? and parentid = 0";

    public Tree getComapnyTypeTree(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        Tree err = new Tree(1, "error");
        int treeRoot;
        int parentID;
        int nodenum = 0;
        int ordernum;
        int rownum = 0;
        int i = 0;
        int k = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_CompanyTypeCount);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rownum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();


            int[] pid = new int[rownum];
            int[] realpid = new int[rownum];
            int[] id = new int[rownum];
            int[] orderid = new int[rownum];
            String[] cname = new String[rownum];
            String[] ename = new String[rownum];
            String[] links = new String[rownum];

            node[] arr = new node[rownum];

            pstmt = conn.prepareStatement(SQL_ComapnyTypeRoot);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            //获取该站点根的ID
            arr[k] = new node();
            arr[k].setChName(cname[0]);
            arr[k].setEnName(ename[0]);
            arr[k].setId(id[0]);
            arr[k].setLinkPointer(0);
            arr[k].setOrderNum(orderid[0]);
            arr[k].setColumnURL(links[0]);
            k = k + 1;
            treeRoot = id[0];
            realpid[nodenum] = id[0];
            nodenum = nodenum + 1;

            pstmt = conn.prepareStatement(SQL_CompanyType);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            //生成该站点的目录树结构
            while (nodenum > 0) {
                ordernum = 0;
                nodenum = nodenum - 1;
                parentID = realpid[nodenum];

                for (i = 0; i < pid.length; i++) {
                    if (pid[i] == parentID) {
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);    //如果父节点不是根节点，所有节点的LinkPointer设置为父节点的ID
                        arr[k].setOrderNum(orderid[i]);
                        arr[k].setColumnURL(links[i]);
                        k = k + 1;
                        ordernum = ordernum + 1;
                        realpid[nodenum] = id[i];
                        nodenum = nodenum + 1;
                    }
                }
            }

            return new Tree(k, "", treeRoot, arr, realpid);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return err;
    }

    private static final String SELECTNODE_FOR_WENBA =
            "SELECT * FROM fawu_wenti_column order by ParentID,orderid";

    private static final String PID_FOR_WENBA =
            "SELECT DISTINCT ParentID from fawu_wenti_column";

    public Tree getWenbaTree() {
        Connection con = null;
        PreparedStatement pstmt;
        int rowNum = 0;
        int pidNum = 0;
        Tree err = new Tree(1, "error");

        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(SELECTNODE_FOR_WENBA);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rowNum = rowNum + 1;
            }
            rs.close();
            pstmt.close();

            int[] pID = new int[rowNum];
            int[] id = new int[rowNum];
            String[] cname = new String[rowNum];
            String[] ename = new String[rowNum];
            int[] orderID = new int[rowNum];
            String[] links = new String[rowNum];
            node[] arr = new node[rowNum];
            int i = 0;
            int parentID;

            pstmt = con.prepareStatement(SELECTNODE_FOR_WENBA);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pID[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderID[i] = rs.getInt("orderID");
                links[i] = rs.getString("dirname");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = con.prepareStatement(PID_FOR_WENBA);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pidNum = pidNum + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = con.prepareStatement(PID_FOR_WENBA);
            rs = pstmt.executeQuery();
            int k = 0;
            int j = 0;
            int ordernum;
            int[] realPID = new int[pidNum];
            while (rs.next()) {
                ordernum = 0;
                parentID = rs.getInt("parentID");
                realPID[j] = parentID;
                //寻找该父节点下的所有子节点
                for (i = 0; i < rowNum; i++) {
                    if (parentID == pID[i]) {
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);
                        arr[k].setOrderNum(orderID[i]);
                        arr[k].setColumnURL(links[i]);
                        k = k + 1;
                        ordernum = ordernum + 1;
                    }
                }
                j = j + 1;
            }
            rs.close();
            pstmt.close();

            Tree columnTree = new Tree(rowNum, "", 0, arr, realPID);
            columnTree.setPidNum(pidNum);
            return columnTree;
        }
        catch (Exception e) {
            System.err.println("Error" + e);
            e.printStackTrace();
        }
        finally {
            cpool.freeConnection(con);
        }

        return err;
    }

    //获得公司分类信息
    private static final String SQL_WebsiteTypeCount = "SELECT count(*) FROM tbl_websiteclass where siteid = ?";
    private static final String SQL_WebsiteType = "SELECT * FROM tbl_websiteclass where siteid = ? and parentID > 0 order by orderID";
    private static final String SQL_WebsiteTypeRoot = "SELECT * FROM tbl_websiteclass where siteid = ? and parentid = 0";

    public Tree getWebsiteTypeTree(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        Tree err = new Tree(1, "error");
        int treeRoot;
        int parentID;
        int nodenum = 0;
        int ordernum;
        int rownum = 0;
        int i = 0;
        int k = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_WebsiteTypeCount);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rownum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();


            int[] pid = new int[rownum];
            int[] realpid = new int[rownum];
            int[] id = new int[rownum];
            int[] orderid = new int[rownum];
            String[] cname = new String[rownum];
            String[] ename = new String[rownum];
            String[] links = new String[rownum];

            node[] arr = new node[rownum];

            pstmt = conn.prepareStatement(SQL_WebsiteTypeRoot);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            //获取该站点根的ID
            arr[k] = new node();
            arr[k].setChName(cname[0]);
            arr[k].setEnName(ename[0]);
            arr[k].setId(id[0]);
            arr[k].setLinkPointer(0);
            arr[k].setOrderNum(orderid[0]);
            arr[k].setColumnURL(links[0]);
            k = k + 1;
            treeRoot = id[0];
            realpid[nodenum] = id[0];
            nodenum = nodenum + 1;

            pstmt = conn.prepareStatement(SQL_WebsiteType);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            //生成该站点的目录树结构
            while (nodenum > 0) {
                ordernum = 0;
                nodenum = nodenum - 1;
                parentID = realpid[nodenum];

                for (i = 0; i < pid.length; i++) {
                    if (pid[i] == parentID) {
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);    //如果父节点不是根节点，所有节点的LinkPointer设置为父节点的ID
                        arr[k].setOrderNum(orderid[i]);
                        arr[k].setColumnURL(links[i]);
                        k = k + 1;
                        ordernum = ordernum + 1;
                        realpid[nodenum] = id[i];
                        nodenum = nodenum + 1;
                    }
                }
            }

            return new Tree(k, "", treeRoot, arr, realpid);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return err;
    }

    private static final String SQL_Site_ProductColumnCount = "SELECT count(*) FROM tbl_column where siteid = ? and isproduct=1";

    private static final String SQL_Site_ProductColumn = "SELECT * FROM tbl_column where siteid = ? and parentID > 0 and isproduct=1 order by orderID";

    public Tree getProductTree(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        Tree err = new Tree(1, "error");
        int treeRoot;
        int parentID;
        int nodenum = 0;
        int ordernum;
        int rownum = 0;
        int i = 0;
        int k = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_Site_ProductColumnCount);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rownum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();


            rownum = rownum + 1;
            int[] pid = new int[rownum];
            int[] realpid = new int[rownum];
            int[] id = new int[rownum];
            int[] orderid = new int[rownum];
            int[] audited = new int[rownum];
            int[] hasArticleModel = new int[rownum];
            String[] cname = new String[rownum];
            String[] ename = new String[rownum];
            String[] links = new String[rownum];

            node[] arr = new node[rownum];

            pstmt = conn.prepareStatement(SQL_SITE_ROOT);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                audited[i] = rs.getInt("isAudited");
                hasArticleModel[i] = rs.getInt("hasArticleModel");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            //获取该站点根的ID
            arr[k] = new node();
            arr[k].setChName(cname[0]);
            arr[k].setEnName(ename[0]);
            arr[k].setId(id[0]);
            arr[k].setLinkPointer(0);
            arr[k].setOrderNum(orderid[0]);
            arr[k].setAudited(0);
            arr[k].setHasArticleModel(0);
            arr[k].setColumnURL(links[0]);
            k = k + 1;
            treeRoot = id[0];
            realpid[nodenum] = id[0];
            nodenum = nodenum + 1;

            pstmt = conn.prepareStatement(SQL_Site_ProductColumn);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                audited[i] = rs.getInt("isAudited");
                hasArticleModel[i] = rs.getInt("hasArticleModel");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            //生成该站点的目录树结构
            while (nodenum > 0) {
                ordernum = 0;
                nodenum = nodenum - 1;
                parentID = realpid[nodenum];

                for (i = 0; i < pid.length; i++) {
                    if (pid[i] == parentID) {
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);    //如果父节点不是根节点，所有节点的LinkPointer设置为父节点的ID
                        arr[k].setOrderNum(orderid[i]);
                        arr[k].setColumnURL(links[i]);
                        arr[k].setAudited(audited[i]);
                        arr[k].setHasArticleModel(hasArticleModel[i]);
                        k = k + 1;
                        ordernum = ordernum + 1;
                        realpid[nodenum] = id[i];
                        nodenum = nodenum + 1;
                    }
                }
            }

            return new Tree(k, "", treeRoot, arr, realpid);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return err;
    }

    private static final String SQL_User_ColumnCount = "SELECT count(*) FROM tbl_column where siteid = ?";
    private static final String SQL_User_Column = "SELECT * FROM tbl_column where siteid = ? order by ParentID,orderid";

    public Tree getUserTree(String userID, int siteid, List clist,int rightid) {
        try {
            for (int i = 0; i < clist.size(); i++)
                if (((clist.get(i)) != null) && (((Column) clist.get(i)).getParentID() == 0))
                    return getSiteTree(siteid);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        Tree err = new Tree(1, "error");
        int treeRoot=0;
        int parentID=0;
        int nodenum = 0;
        int ordernum = 0;
        int rownum = 0;
        int i = 0;
        int k = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_User_ColumnCount);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rownum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            int[] pid = new int[rownum];
            int[] realpid = new int[rownum];
            int[] id = new int[rownum];
            int[] orderid = new int[rownum];
            int[] audited = new int[rownum];
            int[] hasArticleModel = new int[rownum];
            String[] cname = new String[rownum];
            String[] ename = new String[rownum];
            String[] links = new String[rownum];

            node[] arr = new node[rownum];

            pstmt = conn.prepareStatement(SQL_User_Column);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                hasArticleModel[i] = rs.getInt("hasArticleModel");
                audited[i] = rs.getInt("isAudited");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            //获取该站点根的ID
            arr[k] = new node();
            arr[k].setChName(cname[0]);
            arr[k].setEnName(ename[0]);
            arr[k].setId(id[0]);
            arr[k].setLinkPointer(-1);
            arr[k].setOrderNum(orderid[0]);
            arr[k].setColumnURL(links[0]);
            arr[k].setHasArticleModel(0);
            arr[k].setAudited(0);
            k = k + 1;
            ordernum = ordernum + 1;
            realpid[nodenum] = id[0];
            nodenum = nodenum + 1;

            Column column=null;
            treeRoot = 0;
            for (i = 0; i < clist.size(); i++) {
                column = (Column) clist.get(i);
                realpid[nodenum] = column.getID();
                arr[k] = new node();
                arr[k].setChName(column.getCName());
                arr[k].setEnName(column.getEName());
                arr[k].setId(column.getID());
                arr[k].setLinkPointer(0);
                arr[k].setOrderNum(column.getOrderID());
                arr[k].setColumnURL(column.getDirName());
                arr[k].setAudited(column.getIsAudited());
                arr[k].setHasArticleModel(column.getHasArticleModel());
                k = k + 1;
                ordernum = ordernum + 1;
                nodenum = nodenum + 1;
            }

            while (nodenum > 1) {
                ordernum = 0;
                nodenum = nodenum - 1;
                parentID = realpid[nodenum];

                for (i = 0; i < pid.length; i++) {
                    if (pid[i] == parentID) {
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);
                        arr[k].setOrderNum(orderid[i]);
                        arr[k].setColumnURL(links[i]);
                        arr[k].setAudited(audited[i]);
                        arr[k].setHasArticleModel(hasArticleModel[i]);
                        k = k + 1;
                        ordernum = ordernum + 1;
                        realpid[nodenum] = id[i];
                        nodenum = nodenum + 1;
                    }
                }
            }

            return new Tree(k, "", treeRoot, arr, realpid);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return err;
    }

    private static final String SQL_GetSiteRootColumn = "SELECT * FROM tbl_column where siteid=? order by id asc";

    public Tree getUserTreeIncludeSampleSiteColumn(String userID, int siteid,int samsiteid,List clist) {
        Connection conn = null;
        PreparedStatement pstmt;
        Tree err = new Tree(1, "error");
        int treeRoot;
        int parentID;
        int nodenum = 0;
        int ordernum = 0;
        int rownum = clist.size() + 1;
        int i = 0;
        int k = 0;

        try {
            int[] pid = new int[rownum];
            int[] realpid = new int[rownum];
            int[] id = new int[rownum];
            int[] orderid = new int[rownum];
            int[] audited = new int[rownum];
            int[] hasArticleModel = new int[rownum];
            String[] cname = new String[rownum];
            String[] ename = new String[rownum];
            String[] links = new String[rownum];
            node[] arr = new node[rownum];
            conn = cpool.getConnection();

            //获取站点根栏目
            pstmt = conn.prepareStatement(SQL_GetSiteRootColumn);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("parentid");
                cname[i] = rs.getString("cname");
                ename[i] = rs.getString("ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                hasArticleModel[i] = rs.getInt("hasarticlemodel");
                audited[i] = rs.getInt("isaudited");
            }
            rs.close();
            pstmt.close();

            for(i=1; i<clist.size(); i++) {
                Column column = (Column)clist.get(i);
                id[i] = column.getID();
                pid[i] = column.getParentID();
                cname[i] = column.getCName();
                ename[i] = column.getEName();
                orderid[i] = column.getOrderID();
                links[i] = column.getDirName();
                hasArticleModel[i] = column.getHasArticleModel();
                audited[i] = column.getIsAudited();
            }

            //获取该站点根的ID
            arr[k] = new node();
            arr[k].setChName(cname[0]);
            arr[k].setEnName(ename[0]);
            arr[k].setId(id[0]);
            arr[k].setLinkPointer(-1);
            arr[k].setOrderNum(orderid[0]);
            arr[k].setColumnURL(links[0]);
            arr[k].setHasArticleModel(0);
            arr[k].setAudited(0);
            k = k + 1;
            ordernum = ordernum + 1;
            realpid[nodenum] = id[0];
            nodenum = nodenum + 1;

            Column column;
            treeRoot = 0;
            for (i = 0; i < clist.size(); i++) {
                column = (Column) clist.get(i);
                realpid[nodenum] = column.getID();
                arr[k] = new node();
                arr[k].setChName(column.getCName());
                arr[k].setEnName(column.getEName());
                arr[k].setId(column.getID());
                arr[k].setLinkPointer(0);
                arr[k].setOrderNum(column.getOrderID());
                arr[k].setColumnURL(column.getDirName());
                arr[k].setAudited(column.getIsAudited());
                arr[k].setHasArticleModel(column.getHasArticleModel());
                k = k + 1;
                ordernum = ordernum + 1;
                nodenum = nodenum + 1;
            }

            while (nodenum > 1) {
                ordernum = 0;
                nodenum = nodenum - 1;
                parentID = realpid[nodenum];

                for (i = 0; i < pid.length; i++) {
                    if (pid[i] == parentID) {
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);
                        arr[k].setOrderNum(orderid[i]);
                        arr[k].setColumnURL(links[i]);
                        arr[k].setAudited(audited[i]);
                        arr[k].setHasArticleModel(hasArticleModel[i]);
                        k = k + 1;
                        ordernum = ordernum + 1;
                        realpid[nodenum] = id[i];
                        nodenum = nodenum + 1;
                    }
                }
            }

            return new Tree(k, "", treeRoot, arr, realpid);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null)

                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return err;
    }

    public Tree getUserDirTree(String userID, String rootDir, int siteid, List clist) {
        int nodenum = 0;
        int ordernum = 0;
        int i, j, k = 0;
        String dirName;
        Column column;
        Tree[] tree = new Tree[clist.size()];
        List pq = new ArrayList();

        for (i = 0; i < clist.size(); i++) {
            column = (Column) clist.get(i);
            dirName = column.getDirName();
            dirName = StringUtil.replace(dirName, "/", File.separator);
            tree[i] = getDirTree(rootDir.substring(0, rootDir.length() - 1) + dirName);
            nodenum = nodenum + tree[i].getNodeNum();
        }

        //定义虚构的用户树拥有的节点的数目
        node[] nodearr = new node[nodenum + 1];
        int[] rpid = new int[nodenum + 1];

        //获取该用户树的根节点
        nodearr[k] = new node();
        nodearr[k].setChName(userID);
        nodearr[k].setEnName(userID);
        nodearr[k].setId(0);
        nodearr[k].setLinkPointer(-1);
        nodearr[k].setOrderNum(ordernum);
        nodearr[k].setColumnURL("#");
        rpid[k] = k;
        pq.add(nodearr[k]);
        nodenum = 1;

        while (!pq.isEmpty()) {
            pq.remove(nodenum);
            for (i = 0; i < tree.length; i++) {
                for (j = 0; j < tree[i].getAllNodes().length; j++) {
                    k = k + 1;
                    nodearr[k] = new node();
                    nodearr[k] = tree[i].getAllNodes()[0];
                    nodearr[k].setId(k);
                    nodearr[k].setEnName(nodearr[k].getColumnURL());
                    nodearr[k].setLinkPointer(nodearr[k].getLinkPointer() + 1);
                    rpid[k] = k;
                }
            }
        }

        return new Tree(k + 1, "", nodearr, rpid);
    }

    private static final String SQL_Site_ColumnCount = "SELECT count(*) FROM tbl_column where siteid = ?";

    private static final String SQL_Site_Column = "SELECT * FROM tbl_column where siteid = ? and parentID > 0 order by orderID";

    private static final String SQL_SITE_ROOT = "SELECT * FROM tbl_column where siteid = ? and parentid = 0";

    public Tree getSiteTree(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        Tree err = new Tree(1, "error");
        int treeRoot;
        int parentID;
        int nodenum = 0;
        int ordernum;
        int rownum = 0;
        int i = 0;
        int k = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_Site_ColumnCount);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rownum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();


            int[] pid = new int[rownum];
            int[] realpid = new int[rownum];
            int[] id = new int[rownum];
            int[] orderid = new int[rownum];
            int[] audited = new int[rownum];
            int[] hasArticleModel = new int[rownum];
            String[] cname = new String[rownum];
            String[] ename = new String[rownum];
            String[] links = new String[rownum];

            node[] arr = new node[rownum];

            pstmt = conn.prepareStatement(SQL_SITE_ROOT);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                audited[i] = rs.getInt("isAudited");
                hasArticleModel[i] = rs.getInt("hasArticleModel");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            //获取该站点根的ID
            arr[k] = new node();
            arr[k].setChName(cname[0]);
            arr[k].setEnName(ename[0]);
            arr[k].setId(id[0]);
            arr[k].setLinkPointer(0);
            arr[k].setOrderNum(orderid[0]);
            arr[k].setAudited(0);
            arr[k].setHasArticleModel(0);
            arr[k].setColumnURL(links[0]);
            k = k + 1;
            treeRoot = id[0];
            realpid[nodenum] = id[0];
            nodenum = nodenum + 1;

            pstmt = conn.prepareStatement(SQL_Site_Column);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                audited[i] = rs.getInt("isAudited");
                hasArticleModel[i] = rs.getInt("hasArticleModel");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            //生成该站点的目录树结构
            while (nodenum > 0) {
                ordernum = 0;
                nodenum = nodenum - 1;
                parentID = realpid[nodenum];

                for (i = 0; i < pid.length; i++) {
                    if (pid[i] == parentID) {
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);    //如果父节点不是根节点，所有节点的LinkPointer设置为父节点的ID
                        arr[k].setOrderNum(orderid[i]);
                        arr[k].setColumnURL(links[i]);
                        arr[k].setAudited(audited[i]);
                        arr[k].setHasArticleModel(hasArticleModel[i]);
                        k = k + 1;
                        ordernum = ordernum + 1;
                        realpid[nodenum] = id[i];
                        nodenum = nodenum + 1;
                    }
                }
            }
            return new Tree(k, "", treeRoot, arr, realpid);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs!=null) rs.close();
                if (pstmt!=null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return err;
    }

    //获取样本站点栏目的数量
    private static final String Sample_SiteColumnCount =
            "SELECT count(*) FROM tbl_column where siteid = ? and parentid!=0";

    //获取样本站点的所有栏目
    private static final String Sample_SiteColumn =
            "SELECT * FROM tbl_column where siteid = ? order by ID asc";

    //获取本站点栏目树的根节点
    private static final String SQL_GetSiteTreeRoot =
            "SELECT * FROM tbl_column where siteid = ? and parentid = 0";

    //获取本站点自有栏目的数量
    private static final String Self_SQL_SiteColumnCount =
            "SELECT count(*) FROM tbl_column where siteid = ?";

    //获取本站点自有栏目
    private static final String Self_SQLSiteColumn =
            "SELECT * FROM tbl_column where siteid = ?  order by id asc";

    //获取本站点的栏目树和样本站点的栏目树合并而成的栏目树
    public Tree getSiteTreeIncludeSampleSite(int siteid,int SampleSiteID) {
        Connection conn = null;
        PreparedStatement pstmt = null,cpstmt = null;
        ResultSet rs = null,rs1=null;
        Column column = null;
        Tree err=new Tree(1,"error");
        List allNotes = new ArrayList();
        int totalNodeNum = 0;
        int nodenum = 0;
        int treeRoot = 0;
        int sampleColumnCount = 0;
        int rownum = 0;
        int k = 0;
        int parentID = 0;

        try
        {
            conn = cpool.getConnection();
            //获取样本站点栏目的数目
            pstmt = conn.prepareStatement(Sample_SiteColumnCount);
            pstmt.setInt(1,SampleSiteID);
            rs = pstmt.executeQuery();
            while (rs.next() ) {
                sampleColumnCount = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            //获取样本站点的所有栏目
            pstmt = conn.prepareStatement(Sample_SiteColumn);
            pstmt.setInt(1,SampleSiteID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                column = load(rs);
                allNotes.add(column);
            }
            rs.close();
            pstmt.close();
            //获取本站点的节点数目
            pstmt = conn.prepareStatement(Self_SQL_SiteColumnCount);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rownum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            //获取栏目树的总节点数
            totalNodeNum = rownum + sampleColumnCount;
            node[] arr = new node[totalNodeNum];

            //获取树的根节点
            pstmt = conn.prepareStatement(SQL_GetSiteTreeRoot);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                column = load(rs);
                allNotes.add(column);
            }
            rs.close();
            pstmt.close();

            //构造树的根节点
            //column = (Column)allNotes.get(totalNodeNum);   //第一个节点
            treeRoot = column.getID();
            arr[k] = new node();
            arr[k].setChName(column.getCName());
            arr[k].setEnName(column.getEName());
            arr[k].setId(column.getID());
            arr[k].setLinkPointer(0);
            arr[k].setDefineAttr(column.getDefineAttr());
            arr[k].setHasArticleModel(column.getHasArticleModel());
            arr[k].setLanguageType(column.getLanguageType());
            arr[k].setOrderNum(column.getOrderID());
            arr[k].setColumnURL(column.getDirName());
            k = k + 1;

            //构造样本站点中的每个节点
            column = (Column)allNotes.get(0);
            int sampleTreeRootID = column.getID();
            for (int j=1; j<allNotes.size() - 1; j++) {
                column = (Column)allNotes.get(j);
                arr[k] = new node();
                arr[k].setChName(column.getCName());
                arr[k].setEnName(column.getEName());
                arr[k].setId(column.getID());
                if (sampleTreeRootID == column.getParentID())
                    arr[k].setLinkPointer(treeRoot);
                else
                    arr[k].setLinkPointer(column.getParentID());
                arr[k].setDefineAttr(column.getDefineAttr());
                arr[k].setGlobalColumn(1);
                arr[k].setLanguageType(column.getLanguageType());
                arr[k].setHasArticleModel(column.getHasArticleModel());
                arr[k].setOrderNum(column.getOrderID());
                arr[k].setColumnURL(column.getDirName());
                k = k + 1;
            }

            //处理本本站点的每个节点
            int[] pid = new int[rownum];
            int[] realpid = new int[rownum];
            int[] id = new int[rownum];
            int[] orderid = new int[rownum];
            int[] defineAttr = new int[rownum];
            int[] hasArticleModel = new int[rownum];
            int[] globalColumn = new int[rownum];
            int[] languageType = new int[rownum];
            String[] cname = new String[rownum];
            String[] ename = new String[rownum];
            String[] code = new String[rownum];
            String[] links = new String[rownum];
            int i = 0;
            int ordernum = 0;

            pstmt = conn.prepareStatement(Self_SQLSiteColumn);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                defineAttr[i] = rs.getInt("isDefineAttr");
                languageType[i] = rs.getInt("languagetype");
                hasArticleModel[i] = rs.getInt("hasArticleModel");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            ordernum = ordernum + 1;
            realpid[nodenum] = pid[0];
            nodenum = nodenum + 1;
            while (nodenum > 0){
                ordernum = 0;
                nodenum = nodenum - 1;
                parentID = realpid[nodenum];
                for(i=0; i<pid.length; i++){
                    if (pid[i] == parentID){
                        if (parentID>0) {
                            arr[k] = new node();
                            arr[k].setChName(cname[i]);
                            arr[k].setEnName(ename[i]);
                            arr[k].setId(id[i]);
                            arr[k].setLinkPointer(parentID);                          //如果父节点不是根节点，所有节点的LinkPointer设置为父节点的ID
                            arr[k].setOrderNum(orderid[i]);
                            arr[k].setDefineAttr(defineAttr[i]);
                            arr[k].setGlobalColumn(globalColumn[i]);
                            arr[k].setLanguageType(languageType[i]);
                            arr[k].setHasArticleModel(hasArticleModel[i]);
                            arr[k].setGlobalColumn(0);                               //0代表本站点的私有节点
                            arr[k].setColumnURL(links[i]);
                            k = k + 1;
                            ordernum = ordernum + 1;
                            realpid[nodenum] = id[i];
                            nodenum = nodenum + 1;
                        }else{
                            realpid[nodenum] = id[i];
                            nodenum = nodenum + 1;
                        }
                    }
                }
            }

            Tree columnTree = new Tree(k,"",treeRoot,arr,realpid);
            return columnTree;
        }catch (Exception e){
            System.err.println("Error" + e);
            e.printStackTrace();
        }finally{
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }

        return err;
    }

    public Tree getSiteTree(int siteid, Connection conn) {
        PreparedStatement pstmt;
        Tree err = new Tree(1, "error");
        int treeRoot;
        int parentID;
        int nodenum = 0;
        int ordernum;
        int rownum = 0;
        int i = 0;
        int k = 0;

        try {
            pstmt = conn.prepareStatement(SQL_Site_ColumnCount);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rownum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            int[] pid = new int[rownum];
            int[] realpid = new int[rownum];
            int[] id = new int[rownum];
            int[] orderid = new int[rownum];
            int[] audited = new int[rownum];
            int[] hasArticleModel = new int[rownum];
            String[] cname = new String[rownum];
            String[] ename = new String[rownum];
            String[] links = new String[rownum];

            node[] arr = new node[rownum];

            pstmt = conn.prepareStatement(SQL_SITE_ROOT);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                audited[i] = rs.getInt("isAudited");
                hasArticleModel[i] = rs.getInt("hasArticleModel");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            //获取该站点根的ID
            arr[k] = new node();
            arr[k].setChName(cname[0]);
            arr[k].setEnName(ename[0]);
            arr[k].setId(id[0]);
            arr[k].setLinkPointer(0);
            arr[k].setOrderNum(orderid[0]);
            arr[k].setAudited(0);
            arr[k].setHasArticleModel(0);
            arr[k].setColumnURL(links[0]);
            k = k + 1;
            treeRoot = id[0];
            realpid[nodenum] = id[0];
            nodenum = nodenum + 1;

            pstmt = conn.prepareStatement(SQL_Site_Column);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                audited[i] = rs.getInt("isAudited");
                hasArticleModel[i] = rs.getInt("hasArticleModel");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            //生成该站点的目录树结构
            while (nodenum > 0) {
                ordernum = 0;
                nodenum = nodenum - 1;
                parentID = realpid[nodenum];

                for (i = 0; i < pid.length; i++) {
                    if (pid[i] == parentID) {
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);    //如果父节点不是根节点，所有节点的LinkPointer设置为父节点的ID
                        arr[k].setOrderNum(orderid[i]);
                        arr[k].setColumnURL(links[i]);
                        arr[k].setAudited(audited[i]);
                        arr[k].setHasArticleModel(hasArticleModel[i]);
                        k = k + 1;
                        ordernum = ordernum + 1;
                        realpid[nodenum] = id[i];
                        nodenum = nodenum + 1;
                    }
                }
            }

            return new Tree(k, "", treeRoot, arr, realpid);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        /*finally {
            try {
                if (conn != null)

                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } */

        return err;
    }

    //生成<文章发布>和<栏目发布>权限里的栏目树,求其并
    public Tree getPublish_UserTree(String userID, String rightID) {
        final String User_Publish_NodesSQL =
                "SELECT DISTINCT a.Ename as ColumnEname,a.Cname as ColumnChname," +
                        "a.ID as ColumnID,b.UserID as UserID " +
                        "FROM TBL_Column a,TBL_Members b " +
                        "WHERE b.UserID = '" + userID + "' AND a.ID IN " +
                        "(SELECT DISTINCT ColumnID FROM TBL_Members_Rights " +
                        "WHERE UserID = '" + userID + "' AND RightID IN (" + rightID + ") " +
                        "AND ColumnID IS NOT NULL " +
                        "UNION " +
                        "SELECT DISTINCT ColumnID FROM TBL_Group WHERE GroupID IN " +
                        "(SELECT DISTINCT GroupID FROM TBL_MemberGroup WHERE UserID = '" + userID + "') " +
                        "AND RightID IN (" + rightID + ") AND ColumnID IS NOT NULL)";

        Connection conn = null;
        PreparedStatement pstmt;
        Tree err = new Tree(1, "error");
        int parentID;
        int nodenum = 0;
        int ordernum = 0;
        int rownum = 0;
        int i = 0;
        int k = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SELECTNODE);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                rownum = rownum + 1;
            }
            rs.close();
            pstmt.close();

            int[] pid = new int[rownum];
            int[] realpid = new int[rownum];
            int[] id = new int[rownum];
            int[] orderid = new int[rownum];
            String[] cname = new String[rownum];
            String[] ename = new String[rownum];
            String[] links = new String[rownum];

            node[] arr = new node[rownum];

            pstmt = conn.prepareStatement(SELECTNODE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(User_Publish_NodesSQL);
            rs = pstmt.executeQuery();
            IColumnManager columnMgr = ColumnPeer.getInstance();
            Column column;
            while (rs.next()) {
                realpid[nodenum] = rs.getInt("columnID");
                column = columnMgr.getColumn(rs.getInt("columnID"));
                //k = k + 1;
                arr[k] = new node();
                arr[k].setChName(column.getCName());
                arr[k].setEnName(column.getEName());
                arr[k].setId(column.getID());
                arr[k].setLinkPointer(0);
                arr[k].setOrderNum(column.getOrderID());
                arr[k].setColumnURL(column.getDirName());
                k = k + 1;
                ordernum = ordernum + 1;
                nodenum = nodenum + 1;
            }
            rs.close();
            pstmt.close();

            while (nodenum > 0) {
                ordernum = 0;
                nodenum = nodenum - 1;
                parentID = realpid[nodenum];

                for (i = 0; i < pid.length; i++) {
                    if (pid[i] == parentID) {
                        //k = k + 1;
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);
                        arr[k].setOrderNum(orderid[i]);
                        arr[k].setColumnURL(links[i]);
                        k = k + 1;
                        ordernum = ordernum + 1;
                        realpid[nodenum] = id[i];
                        nodenum = nodenum + 1;
                    }
                }
            }

            return new Tree(k, "", arr, realpid);
        }
        catch (Exception e) {
            System.err.println("Error" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null)

                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return err;
    }

    public int getRootID() {
        return 0;
    }

    public int getChildID(int parentID, int index) {
        int columnID = -1;
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(GET_CHILD);
            pstmt.setInt(1, parentID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next() && index > 0) {
                index--;
            }
            if (index == 0) {
                columnID = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cpool.freeConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return columnID;
    }

    public int getParentID(int ID) {
        int columnID = -1;
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(GET_PARENTID);
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                columnID = rs.getInt(1);

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cpool.freeConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return columnID;
    }

    public int getChildCount(int parentID) {
        int childCount = 0;
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(CHILD_COUNT);
            pstmt.setInt(1, parentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                childCount = rs.getInt(1);

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.err.println("Error in ColumnTree:getChildCount()-" + e);
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    cpool.freeConnection(con);
                    //con.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return childCount;
    }

    public int getRecursiveChildCount(int parentID) {
        int numChildren = 0;
        int num = getChildCount(parentID);
        numChildren += num;
        for (int i = 0; i < num; i++) {
            int childID = getChildID(parentID, i);
            if (childID != -1) {
                numChildren += getRecursiveChildCount(childID);
            }
        }
        return numChildren;
    }

    public int getIndexOfChild(int parentID, int childID) {
        int index = 0;
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(INDEX_OF_CHILD);
            pstmt.setInt(1, parentID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == childID) {
                    break;
                }
                index++;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.err.println("Error in ColumnTree:getIndexOfChild()-" + e);
        } finally {
            try {
                if (con != null) {
                    cpool.freeConnection(con);
                    //con.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return index;
    }

    public boolean isRoot(int nodeid) {
        int columnID = -1;
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(GET_PARENTID);
            pstmt.setInt(1, nodeid);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
                columnID = rs.getInt(1);
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.err.println("Error in ColumnTree:isRoot" + e);
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    cpool.freeConnection(con);
                    //con.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (columnID == 0);
    }

    public boolean isLeaf(int nodeID) {
        return (getChildCount(nodeID) == 0);
    }

    //创建文章分类树 by feixiang 2008-01-25
    public Tree getAtricleTypeTree(String columnIDs) {
        Connection con = null;
        PreparedStatement pstmt;
        int rowNum = 0;
        int pidNum = 0;
        Tree err = new Tree(1, "error");

        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("SELECT COUNT(*) FROM tbl_type where columnid in(" + columnIDs + ") and referid = 0");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rowNum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            int[] pID = new int[rowNum];
            int[] id = new int[rowNum];
            String[] cname = new String[rowNum];
            typenode[] arr = new typenode[rowNum];
            int i = 0;
            int parentID;

            pstmt = con.prepareStatement("SELECT * FROM tbl_type where columnid in(" + columnIDs + ") and referid = 0");
            //pstmt.setString(1,columnIDs);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pID[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = con.prepareStatement("SELECT DISTINCT ParentID from tbl_type where columnid in(" + columnIDs + ") and referid = 0");
            //pstmt.setString(1,columnIDs);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pidNum = pidNum + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = con.prepareStatement("SELECT DISTINCT ParentID from tbl_type where columnid in(" + columnIDs + ") and referid = 0");
            rs = pstmt.executeQuery();
            int k = 0;
            int j = 0;
            int ordernum;
            int[] realPID = new int[pidNum];
            while (rs.next()) {
                ordernum = 0;
                parentID = rs.getInt("parentID");
                realPID[j] = parentID;
                //寻找该父节点下的所有子节点
                for (i = 0; i < rowNum; i++) {
                    if (parentID == pID[i]) {
                        arr[k] = new typenode();
                        arr[k].setCname(cname[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);
                        k = k + 1;
                        ordernum = ordernum + 1;
                    }
                }
                j = j + 1;
            }
            rs.close();
            pstmt.close();

            Tree columnTree = new Tree(rowNum, "", 0, arr, realPID);
            columnTree.setPidNum(pidNum);
            return columnTree;
        }
        catch (Exception e) {
            System.err.println("Error" + e);
            e.printStackTrace();
        }
        finally {
            cpool.freeConnection(con);
        }

        return err;
    }

    //创建被复制的栏目树
    public Tree getCopyColumnTree(String columnIDs) {
        Connection con = null;
        PreparedStatement pstmt;
        int rowNum = 0;
        int pidNum = 0;
        Tree err = new Tree(1, "error");

        try {
            con = cpool.getConnection();

            rowNum = columnIDs.split(",").length;
            int[] pID = new int[rowNum];
            int[] id = new int[rowNum];
            String[] ename = new String[rowNum];
            node[] arr = new node[rowNum];
            int i = 0;
            int parentID;
            int k = 0;
            int j = 0;

            pstmt = con.prepareStatement("SELECT id,parentid,ename FROM tbl_column where id in(" + columnIDs + ") order by id");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                if (i == 0)
                    pID[0] = 0;
                else
                    pID[i] = rs.getInt("ParentID");
                ename[i] = rs.getString("ename");
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = con.prepareStatement("SELECT DISTINCT ParentID from tbl_column where id in (" + columnIDs + ")");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pidNum = pidNum + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = con.prepareStatement("SELECT DISTINCT ParentID from tbl_column where id in (" + columnIDs + ")");
            rs = pstmt.executeQuery();

            int[] realPID = new int[pidNum];
            while (rs.next()) {
                if (j == 0)
                    parentID = 0;
                else
                    parentID = rs.getInt("parentID");
                realPID[j] = parentID;

                //寻找该父节点下的所有子节点
                for (i = 0; i < rowNum; i++) {
                    if (parentID == pID[i]) {
                        arr[k] = new node();
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);
                        k = k + 1;
                    }
                }
                j = j + 1;
            }
            rs.close();
            pstmt.close();

            Tree columnTree = new Tree(rowNum, "", 0, arr, realPID);
            columnTree.setPidNum(pidNum);
            return columnTree;
        }
        catch (Exception e) {
            System.err.println("Error" + e);
            e.printStackTrace();
        }
        finally {
            cpool.freeConnection(con);
        }

        return err;
    }

    private static String SQL_GET_CHILDS_OF_COLUMNID = "select id from tbl_column where parentid = ? order by orderid";

    private static String SQL_GET_CHILDSCOUNT_OF_COLUMNID = "select count(id) from tbl_column where parentid = ?";

    public int[] getChildsOfColumnId(int parentID) {
        Connection con = null;
        PreparedStatement pstmt;
        int[] childs = null;
        int i = 0;

        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(SQL_GET_CHILDSCOUNT_OF_COLUMNID);
            pstmt.setInt(1, parentID);
            ResultSet rs = pstmt.executeQuery();
            int childsCount = 0;
            if (rs.next()) {
                childsCount = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            childs = new int[childsCount];
            pstmt = con.prepareStatement(SQL_GET_CHILDS_OF_COLUMNID);
            pstmt.setInt(1, parentID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                childs[i] = rs.getInt(1);
                i++;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cpool.freeConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return childs;
    }

    Column load(ResultSet rs) throws SQLException
    {
        Column column = new Column();

        column.setID(rs.getInt("ID"));
        column.setSiteID(rs.getInt("siteid"));
        column.setDirName(rs.getString("dirname"));
        column.setOrderID(rs.getInt("orderid"));
        column.setParentID(rs.getInt("parentid"));
        column.setEName(rs.getString("ename"));
        column.setExtname(rs.getString("extname"));
        column.setCreateDate(rs.getTimestamp("createdate"));
        column.setLastUpdated(rs.getTimestamp("lastupdated"));
        column.setCName(rs.getString("cname"));
        column.setEditor(rs.getString("editor"));
        column.setDefineAttr(rs.getInt("isDefineAttr"));
        column.setXMLTemplate(rs.getString("XMLTemplate"));
        column.setIsAudited(rs.getInt("IsAudited"));
        column.setDesc(rs.getString("ColumnDesc"));
        column.setIsProduct(rs.getInt("IsProduct"));
        column.setIsPublishMoreArticleModel(rs.getInt("IsPublishMore"));
        column.setLanguageType(rs.getInt("LanguageType"));

        return column;
    }

    public Tree getSubTreeFromColumn(int siteid, int columnid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Tree err = new Tree(1, "error");

        int nodenum = 0;
        int rownum = 0;
        int i = 0;
        int k = 0;
        try
        {
            conn = this.cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT count(*) FROM tbl_column where siteid = ?");
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rownum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            int[] pid = new int[rownum];
            int[] realpid = new int[rownum];
            int[] id = new int[rownum];
            int[] orderid = new int[rownum];
            int[] audited = new int[rownum];
            int[] hasArticleModel = new int[rownum];
            String[] cname = new String[rownum];
            String[] ename = new String[rownum];
            String[] links = new String[rownum];

            node[] arr = new node[rownum];

            arr[k] = new node();
            pstmt = conn.prepareStatement("SELECT * FROM tbl_column where siteid = ? and id = ?  order by orderID asc");
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, columnid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                arr[k].setId(rs.getInt("id"));
                arr[k].setLinkPointer(0);
                arr[k].setChName(rs.getString("Cname"));
                arr[k].setEnName(rs.getString("Ename"));
                arr[k].setOrderNum(rs.getInt("orderid"));
                arr[k].setColumnURL(rs.getString("dirname"));
                arr[k].setAudited(0);
            }
            rs.close();
            pstmt.close();

            k += 1;
            int treeRoot = columnid;
            realpid[nodenum] = columnid;
            nodenum += 1;

            pstmt = conn.prepareStatement("SELECT * FROM tbl_column where siteid = ? and parentID > 0 order by orderID");
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id[i] = rs.getInt("id");
                pid[i] = rs.getInt("ParentID");
                cname[i] = rs.getString("Cname");
                ename[i] = rs.getString("Ename");
                orderid[i] = rs.getInt("orderid");
                links[i] = rs.getString("dirname");
                audited[i] = rs.getInt("isAudited");
                hasArticleModel[i] = rs.getInt("hasArticleModel");
                i += 1;
            }
            rs.close();
            pstmt.close();

            if (nodenum > 0) {
                int ordernum = 0;
                nodenum -= 1;
                int parentID = realpid[nodenum];
                for (i = 0; ; ++i) { if (i < pid.length);
                    if (pid[i] == parentID) {
                        if (parentID == 1040) System.out.println(cname[i]);
                        arr[k] = new node();
                        arr[k].setChName(cname[i]);
                        arr[k].setEnName(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkPointer(parentID);
                        arr[k].setOrderNum(orderid[i]);
                        arr[k].setColumnURL(links[i]);
                        arr[k].setAudited(audited[i]);
                        arr[k].setHasArticleModel(hasArticleModel[i]);
                        k += 1;
                        ordernum += 1;
                        realpid[nodenum] = id[i];
                        nodenum += 1;
                    } }

            }
            Tree localTree1 = new Tree(k, "", treeRoot, arr, realpid);

            return localTree1;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) this.cpool.freeConnection(conn);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return err;
    }

    //获取子树下的所有子栏目ID
    public int[] getSubTreeColumnIDList(node[] treenodes, int columnID) {
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

    //获得站点栏目信息
    private int getColumnIDForSite(int siteid) {
        int id = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id from tbl_column where siteid = ? and parentid = 0");
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {

                cpool.freeConnection(conn);

            }
        }
        return id;
    }


}