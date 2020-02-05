package com.bizwink.cms.tree;

import java.io.*;
import java.util.*;

import com.bizwink.cms.news.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.viewFileManager.*;
import com.bizwink.cms.server.CmsServer;

public class Tree {
    private int treeRoot;
    private int nodeNum;
    private int pidNum;
    private String errorMsg;
    private node[] allNodes;
    private int[] pid;
    private typenode[] typeNodes;

    public Tree() {
    }

    public Tree(int num, String err) {
        this.nodeNum = num;
        this.errorMsg = err;
    }

    public Tree(int num, String err, node[] arr, int[] p) {
        this.nodeNum = num;
        this.errorMsg = err;
        this.allNodes = arr;
        this.pid = p;
    }

    public Tree(int num, String err, int treeRoot, node[] arr, int[] p) {
        this.nodeNum = num;
        this.errorMsg = err;
        this.treeRoot = treeRoot;
        this.allNodes = arr;
        this.pid = p;
    }

    public Tree(int num, String err, int treeRoot, typenode[] arr, int[] p) {
        this.nodeNum = num;
        this.errorMsg = err;
        this.treeRoot = treeRoot;
        this.typeNodes = arr;
        this.pid = p;
    }

    public Tree(int num, String err, node[] arr) {
        this.nodeNum = num;
        this.errorMsg = err;
        this.allNodes = arr;
    }

    private int findNodeInTree(node[] arr, int nodeID) {
        int nodenum = 0;
        while (arr[nodenum].getId() != nodeID)
            nodenum++;

        return nodenum;
    }

    public String getFileSystemDirName(Tree columnTree, int columnID) {
        StringBuffer buf = new StringBuffer();
        node[] treeNodes = columnTree.getAllNodes();
        int nodenum;
        int parentColumnID;

        do {
            nodenum = findNodeInTree(treeNodes, columnID);
            parentColumnID = treeNodes[nodenum].getLinkPointer();
            if (parentColumnID >= 0) {
                buf.insert(0, "/");
                buf.insert(0, treeNodes[nodenum].getEnName());
            }
            columnID = parentColumnID;
        } while (parentColumnID > 0);

        return buf.toString();
    }

    public String getDirName(Tree columnTree, int columnID) {
        StringBuffer buf = new StringBuffer();
        node[] treeNodes = columnTree.getAllNodes();
        int nodenum;
        int parentColumnID;

        do {
            nodenum = findNodeInTree(treeNodes, columnID);
            parentColumnID = treeNodes[nodenum].getLinkPointer();
            if (parentColumnID >= 0) {
                buf.insert(0, "/");
                buf.insert(0, treeNodes[nodenum].getEnName());
            }
            columnID = parentColumnID;
        } while (parentColumnID > 0);

        return buf.toString().substring(1, buf.length());
    }

    public String getCopyColumnDirName(Tree columnTree, int columnID) {
        StringBuffer buf = new StringBuffer();
        node[] treeNodes = columnTree.getAllNodes();
        int nodenum;
        int parentColumnID;

        do {
            nodenum = findNodeInTree(treeNodes, columnID);
            parentColumnID = treeNodes[nodenum].getLinkPointer();
            if (parentColumnID >= 0) {
                buf.insert(0, "/");
                buf.insert(0, treeNodes[nodenum].getEnName());
            }
            columnID = parentColumnID;
        } while (parentColumnID > 0);

        buf.insert(0, "/");
        return buf.toString();
    }

    public int getNodeDepth(Tree columnTree, int columnID) {
        int depth = 0;
        node[] treeNodes = columnTree.getAllNodes();
        int nodenum;
        int parentColumnID;

        while (columnID != 0) {
            nodenum = findNodeInTree(treeNodes, columnID);
            parentColumnID = treeNodes[nodenum].getLinkPointer();
            columnID = parentColumnID;
            depth = depth + 1;
        }

        return depth;
    }

    public int getChildCount(Tree columnTree, int columnID) {
        int count = 0;
        node[] treeNodes = columnTree.getAllNodes();

        for (int i = 0; i < columnTree.getNodeNum(); i++) {
            if (columnID == treeNodes[i].getLinkPointer())
                count = count + 1;
        }

        return count;
    }

    public int isLeaf(Tree columnTree, int columnID) {
        int count = 0;
        node[] treeNodes = columnTree.getAllNodes();

        for (int i = 0; i < treeNodes.length; i++) {
            if (columnID == treeNodes[i].getLinkPointer())
                count = count + 1;
        }

        return count;
    }

    public String getChineseDir(Tree columnTree, int columnID) {
        StringBuffer buf = new StringBuffer();
        node[] treeNodes = columnTree.getAllNodes();
        int nodenum;
        int parentColumnID;

        while (columnID != 0) {
            nodenum = findNodeInTree(treeNodes, columnID);
            buf.insert(0, "/");
            buf.insert(0, treeNodes[nodenum].getChName());
            parentColumnID = treeNodes[nodenum].getLinkPointer();
            columnID = parentColumnID;
        }
        buf.insert(0, "/");
        return buf.toString();
    }

    public String getChineseDirForArticle(Tree columnTree, int columnID,String sitename) {
        StringBuffer buf = new StringBuffer();
        node[] treeNodes = columnTree.getAllNodes();
        int nodenum;
        int parentColumnID;
        String tbuf = "";

        while (columnID != 0) {
            nodenum = findNodeInTree(treeNodes, columnID);
            buf.insert(0, ">");
            buf.insert(0, treeNodes[nodenum].getChName());
            parentColumnID = treeNodes[nodenum].getLinkPointer();
            columnID = parentColumnID;
        }
        buf.insert(0, ">");
        tbuf = buf.toString();
        tbuf = StringUtil.replace(tbuf,">"+sitename + ">","");
        return tbuf;
    }

    public String getColumnids(Tree columnTree, int columnID) {
        StringBuffer buf = new StringBuffer();
        node[] treeNodes = columnTree.getAllNodes();
        int nodenum;
        int parentColumnID;

        while (columnID != 0) {
            nodenum = findNodeInTree(treeNodes, columnID);
            buf.insert(0, treeNodes[nodenum].getId());
            parentColumnID = treeNodes[nodenum].getLinkPointer();
            columnID = parentColumnID;
        }

        return buf.toString();
    }

    public String getExtName(int columnID) {
        String ext = "";

        IColumnManager columnMgr = ColumnPeer.getInstance();
        try {
            ext = columnMgr.getColumn(columnID).getExtname();
        }
        catch (ColumnException e) {
            e.printStackTrace();
        }
        return ext;
    }

    public String getStyleContent(String filename) {
        String content = "";

        try {
            File file = new File(filename);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                String temp;
                while ((temp = br.readLine()) != null) {
                    content = content + temp + "\r\n";
                }
                br.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    public String getChineseNavBar(Tree columnTree, int columnID, int styleID, int modeltype) {
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        ViewFile viewfile = new ViewFile();
        try {
            viewfile = viewfileMgr.getAViewFile(styleID);
        }
        catch (viewFileException e) {
            e.printStackTrace();
        }

        StringBuffer buf = new StringBuffer();
        String ext;
        String url;
        String dirName;
        int initColumnID = columnID;

        //读出路径样式内容
        String content = viewfile.getContent();
        if (content == null) content = "";
        String seperator = "";
        String general_item;
        String last_item = "";
        int posi = content.indexOf("$");
        if (posi >= 0) {
            general_item = content.substring(0, posi);
            content = content.substring(posi + 1);
            posi = content.indexOf("$");
            if (posi >= 0) {
                seperator = content.substring(0, posi);
                last_item = content.substring(posi + 1);
            } else {
                seperator = content;
            }
        } else {
            general_item = content;
        }

        String temp=null;
        if (general_item.indexOf("<%%CHINESE_PATH%%>") != -1) {
            int parentColumnID;
            node[] treeNodes = columnTree.getAllNodes();

            while (columnID != 0) {
                int nodenum = findNodeInTree(treeNodes, columnID);
                parentColumnID = treeNodes[nodenum].getLinkPointer();
                if (parentColumnID > 0) {
                    dirName = getDirName(columnTree, columnID);
                    ext = getExtName(columnID);
                    url = dirName + "index." + ext;

                    if (initColumnID != columnID) {
                        temp = StringUtil.replace(general_item, "<%%CHINESE_PATH%%>", treeNodes[nodenum].getChName());
                        temp = StringUtil.replace(temp, "<%%URL%%>", url);
                        temp = temp + seperator;
                    } else {
                        if (last_item != null && !last_item.equals("")) {
                            temp = StringUtil.replace(last_item, "<%%LAST_ITEM%%>", treeNodes[nodenum].getChName());
                            temp = StringUtil.replace(temp, "<%%URL%%>", url);
                        } else {
                            temp = StringUtil.replace(general_item, general_item, treeNodes[nodenum].getChName());
                        }
                    }

                    buf.insert(0, temp);
                }
                columnID = parentColumnID;
            }

            String tt1 = "首页";
            if (CmsServer.getInstance().getDBtype().equalsIgnoreCase("mssql"))  {
                temp = "";
                try {
                    temp = StringUtil.replace(general_item, "<%%CHINESE_PATH%%>", new String(tt1.getBytes("GB2312"), "iso-8859-1"));
                    //temp = StringUtil.replace(general_item, "<%%CHINESE_PATH%%>", tt1);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            } else {
                try{
                    temp = StringUtil.replace(general_item, "<%%CHINESE_PATH%%>", tt1);
                } catch (Exception expq) {
                    expq.printStackTrace();
                }
            }

            ext = getExtName(columnTree.getTreeRoot());
            temp = StringUtil.replace(temp, "<%%URL%%>", "/index." + ext);
            buf.insert(0, temp + seperator);
        }

        return buf.toString();
    }

    public String getEnglishNavBar(Tree columnTree, int columnID, int styleID, int modeltype) {
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        ViewFile viewfile = new ViewFile();
        try {
            viewfile = viewfileMgr.getAViewFile(styleID);
        }
        catch (viewFileException e) {
            e.printStackTrace();
        }

        StringBuffer buf = new StringBuffer();
        String ext;
        String url;
        String dirName;
        int initColumnID = columnID;

        //读出路径样式内容
        String content = viewfile.getContent();
        String seperator = "";
        String general_item;
        String last_item = "";
        int posi = content.indexOf("$");
        if (posi >= 0) {
            general_item = content.substring(0, posi);
            content = content.substring(posi + 1);
            posi = content.indexOf("$");
            if (posi >= 0) {
                seperator = content.substring(0, posi);
                last_item = content.substring(posi + 1);
            } else {
                seperator = content;
            }
        } else {
            general_item = content;
        }

        if (general_item.indexOf("<%%ENGLISH_PATH%%>") != -1) {
            String temp;
            int parentColumnID;
            node[] treeNodes = columnTree.getAllNodes();

            while (columnID != 0) {
                int nodenum = findNodeInTree(treeNodes, columnID);
                parentColumnID = treeNodes[nodenum].getLinkPointer();
                if (parentColumnID > 0) {
                    dirName = getDirName(columnTree, columnID);
                    ext = getExtName(columnID);
                    url = dirName + "index." + ext;

                    if (initColumnID != columnID) {
                        temp = StringUtil.replace(general_item, "<%%ENGLISH_PATH%%>", treeNodes[nodenum].getChName());
                        temp = StringUtil.replace(temp, "<%%URL%%>", url);
                        temp = temp + seperator;
                    } else {
                        if (last_item != null && !last_item.equals("")) {
                            temp = StringUtil.replace(last_item, "<%%LAST_ITEM%%>", treeNodes[nodenum].getChName());
                            temp = StringUtil.replace(temp, "<%%URL%%>", url);
                        } else {
                            temp = StringUtil.replace(general_item, general_item, treeNodes[nodenum].getChName());
                        }
                    }

                    buf.insert(0, temp);
                }
                columnID = parentColumnID;
            }

            if (CmsServer.getInstance().getDBtype().equalsIgnoreCase("mssql"))
                temp = StringUtil.replace(general_item, "<%%ENGLISH_PATH%%>", StringUtil.gb2iso("Home"));
            else
                temp = StringUtil.replace(general_item, "<%%ENGLISH_PATH%%>", StringUtil.gb2iso4View("Home"));

            ext = getExtName(columnTree.getTreeRoot());
            temp = StringUtil.replace(temp, "<%%URL%%>", "/index." + ext);
            buf.insert(0, temp + seperator);
        }

        return buf.toString();
    }

    /*public String getEnglishNavBar(Tree columnTree, int columnID, int styleID, int modeltype) {
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        ViewFile viewfile = new ViewFile();
        try {
            viewfile = viewfileMgr.getAViewFile(styleID);
        }
        catch (viewFileException e) {
            e.printStackTrace();
        }

        StringBuffer buf = new StringBuffer();
        String ext;
        String url;
        String dirName;
        int initColumnID = columnID;

        //读出路径样式内容
        String content = viewfile.getContent();

        if (content.indexOf("<%%ENGLISH_PATH%%>") != -1) {
            String temp;
            int parentColumnID;
            node[] treeNodes = columnTree.getAllNodes();

            while (columnID != 0) {
                int nodenum = findNodeInTree(treeNodes, columnID);
                parentColumnID = treeNodes[nodenum].getLinkPointer();
                if (parentColumnID > 0) {
                    dirName = getDirName(columnTree, columnID);
                    ext = getExtName(columnID);
                    url = dirName + "index." + ext;

                    if (initColumnID != columnID) {
                        temp = StringUtil.replace(content, "<%%ENGLISH_PATH%%>", treeNodes[nodenum].getChName());
                        temp = StringUtil.replace(temp, "<%%URL%%>", url);
                    } else {
                        temp = StringUtil.replace(content, content, treeNodes[nodenum].getChName());
                    }

                    //temp = StringUtil.replace(content,"<%%ENGLISH_PATH%%>",treeNodes[nodenum].getChName());
                    //temp = StringUtil.replace(temp, "<%%URL%%>", url);
                    buf.insert(0, temp);
                }
                columnID = parentColumnID;
            }

            ext = getExtName(columnTree.getTreeRoot());
            temp = StringUtil.replace(content, "<%%ENGLISH_PATH%%>", StringUtil.gb2iso("Home"));
            temp = StringUtil.replace(temp, "<%%URL%%>", "/index." + ext);
            buf.insert(0, temp);
        }

        return buf.toString();
    }*/

    public String getChinesePath(Tree columnTree, Article article) {
        StringBuffer buf = new StringBuffer();
        int columnID = article.getColumnID();

        String temp;
        int parentColumnID;
        node[] treeNodes = columnTree.getAllNodes();

        while (columnID != 0) {
            int nodenum = findNodeInTree(treeNodes, columnID);
            parentColumnID = treeNodes[nodenum].getLinkPointer();
            if (parentColumnID > 0) {
                temp = " > " + treeNodes[nodenum].getChName();
                buf.insert(0, temp);
            }
            columnID = parentColumnID;
        }

        return buf.toString();
    }

    public List getColumnNameList(Tree columnTree, int columnID) {
        List columnList = new ArrayList();
        int parentColumnID;
        node[] treeNodes = columnTree.getAllNodes();

        while (columnID != 0) {
            int nodenum = findNodeInTree(treeNodes, columnID);
            parentColumnID = treeNodes[nodenum].getLinkPointer();
            if (parentColumnID > 0)
                columnList.add(treeNodes[nodenum].getChName());
            columnID = parentColumnID;
        }

        return columnList;
    }

    //从columnID往上的节点是否存在全局节点
    public boolean existGlobalColumn(Tree columnTree, int columnID) {
        boolean exist = false;
        int parentColumnID;
        node[] treeNodes = columnTree.getAllNodes();
        int globalFlag;

        while (columnID != 0) {
            int nodenum = findNodeInTree(treeNodes, columnID);
            parentColumnID = treeNodes[nodenum].getLinkPointer();
            globalFlag = treeNodes[nodenum].getGlobalColumn();
            if (globalFlag == 1) {
                exist = true;
                break;
            }
            columnID = parentColumnID;
        }

        return exist;
    }

    //获取子树下的所有子栏目ID
    //cid数组的第一个元素保存的是获取子栏目的ID的数量
    //cid数组第二个元素以后保存的是实际获取的子栏目ID列表
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

    public int getGlobalColumnInSubtree(Tree coltree, int columnID) {
        node[] treenodes = coltree.getAllNodes();
        int[] pid = new int[treenodes.length];
        int globalColumn = 0;
        int nodenum = 1;
        int i;
        int j = 1;

        pid[1] = columnID;

        do {
            columnID = pid[nodenum];
            j = j + 1;

            nodenum = nodenum - 1;

            for (i = 0; i < treenodes.length; i++) {
                if (treenodes[i].getLinkPointer() == columnID) {
                    nodenum = nodenum + 1;
                    pid[nodenum] = treenodes[i].getId();

                    if (treenodes[i].getGlobalColumn() == 1) {
                        globalColumn = treenodes[i].getId();
                        break;
                    }
                }
            }
        } while (nodenum >= 1);

        return globalColumn;
    }

    public String getSubTree_NoArticleModelColumnIDList(node[] treenodes, int columnID) {
        int[] cid = new int[treenodes.length + 1];
        int[] pid = new int[treenodes.length];
        String cidStr = "(";
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
                if (treenodes[i] != null) {
                    //System.out.println(i + "====" + treenodes[i].getHasArticleModel() + "====" + treenodes[i].getLinkPointer() + StringUtil.gb2iso4View(treenodes[i].getChName()));
                    if (treenodes[i].getLinkPointer() == columnID && treenodes[i].getHasArticleModel() == 0) {
                        nodenum = nodenum + 1;
                        pid[nodenum] = treenodes[i].getId();
                    }
                }
            }
        } while (nodenum >= 1);

        for (i = 1; i < j; i++)
            cidStr = cidStr + cid[i] + ",";

        cidStr = cidStr.substring(0, cidStr.length() - 1);
        cidStr = cidStr + ")";

        return cidStr;
    }

    public void setErrorMsg(String err) {
        this.errorMsg = err;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setNodeNum(int num) {
        this.nodeNum = num;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public void setPidNum(int num) {
        this.pidNum = num;
    }

    public int getPidNum() {
        return pidNum;
    }

    public void setPid(int[] arr) {
        this.pid = arr;
    }

    public int[] getPid() {
        return pid;
    }

    public node[] getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(node[] nodes) {
        this.allNodes = nodes;
    }

    public int getTreeRoot() {
        return treeRoot;
    }

    public typenode[] getTypeNodes() {
        return typeNodes;
    }

    public void setTypeNodes(typenode[] typeNodes) {
        this.typeNodes = typeNodes;
    }
}