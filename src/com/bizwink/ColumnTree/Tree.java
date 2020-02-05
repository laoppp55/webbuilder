package com.bizwink.ColumnTree;

import com.bizwink.po.Column;

import java.io.*;
import java.util.*;

public class Tree {
    private int treeRoot;
    private int nodeNum;
    private int pidNum;
    private String errorMsg;
    private node[] allNodes;
    private List<Column> pid;
    private typenode[] typeNodes;

    public Tree() {
    }

    public Tree(int num, String err) {
        this.nodeNum = num;
        this.errorMsg = err;
    }

    public Tree(int num, String err, node[] arr, List<Column> p) {
        this.nodeNum = num;
        this.errorMsg = err;
        this.allNodes = arr;
        this.pid = p;
    }

    public Tree(int num, String err, int treeRoot, node[] arr, List<Column> p) {
        this.nodeNum = num;
        this.errorMsg = err;
        this.treeRoot = treeRoot;
        this.allNodes = arr;
        this.pid = p;
    }

    public Tree(int num, String err, int treeRoot, typenode[] arr, List<Column> p) {
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

    public int getDepthBetweenNode(Tree columnTree, int columnID,int topID) {
        int depth = 0;
        node[] treeNodes = columnTree.getAllNodes();
        int nodenum;
        int parentColumnID;

        while (columnID != topID) {
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

    public List<Column> getPid() {
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