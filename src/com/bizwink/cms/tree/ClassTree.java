package com.bizwink.cms.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 17-3-3.
 */
public class ClassTree {
    private int nodeNum;
    private String errorMsg;
    private snode[] allNodes;
    private Map maptree;

    public ClassTree(int num, String err, snode[] arr,Map map) {
        this.nodeNum = num;
        this.errorMsg = err;
        this.allNodes = arr;
        this.maptree = map;
    }

    public snode[] getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(snode[] nodes) {
        this.allNodes = nodes;
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

    public Map getMaptree() {
        return maptree;
    }

    public void setMaptree(Map maptree) {
        this.maptree = maptree;
    }

    public int getChildCount(String columnID) {
        snode thenode = (snode)maptree.get(columnID);
        if (thenode != null)
            return thenode.getSubnodes().size();
        else
            return 0;
    }

    public boolean isLeaf(String columnID) {
        snode thenode = (snode)maptree.get(columnID);
        if (thenode != null)
            if (thenode.getSubnodes().size() == 0)
                return true;
            else
                return false;
        else
            return false;
    }

    public int findNodeInTree(String nodeID) {
        snode thenode = (snode)maptree.get(nodeID);
        if (thenode!=null)
            return 1;
        else
            return 0;
    }

    public snode findNodeinfoInTree(String nodeID) {
        return (snode)maptree.get(nodeID);
    }

    public List makeTreeTable(Map treemap,String model_str){
        List items = new ArrayList();                             //存储生成的菜单树
        if (this.nodeNum > 0) {
            String pid[] = new String[this.nodeNum];                    //遍历树所需要的节点数组，存储当前未处理的节点
            String parentMenuName[] = new String[this.nodeNum];   //存储某个节点的所有子节点的父菜单名称
            String menuName = "menu";                                     //存储当前节点的菜单名称
            String currentID = "0";                                            //当前正在处理的节点
            int i = 0;                                                      //循环变量
            int[] ordernum = new int[this.nodeNum];               //当前节点所有子节点的顺序号
            int nodenum = 1;                                              //当前被处理节点的初始值
            int subflag = 1;                                              //判断当前节点是否有子节点

            pid[nodenum-1] = this.allNodes[0].getId();

            do {
                currentID = pid[nodenum-1];

                //设置当前处理节点的初始值
                if (currentID.equalsIgnoreCase("-1")) {
                    //处理根节点的程序
                } else {
                    //处理其他节点的程序
                    //int kk = 0;
                    //while (!this.allNodes[kk].getId().equalsIgnoreCase(currentID))
                    //    kk++;
                    snode thenode = (snode)treemap.get(currentID);
                    items.add(thenode);
                }

                //从处理的节点数组中取出当前正在处理的元素，查找该元素下的子元素
                //设置所有子节点的父菜单名称，设置所有子节点的序列号，把所有的子节点存入pid数组中
                subflag = 0;
                nodenum = nodenum - 1;
                for (i = 0; i < this.nodeNum; i++) {
                    if (this.allNodes[i].getLinkPointer().equalsIgnoreCase(currentID)) {
                        nodenum = nodenum + 1;
                        pid[nodenum-1] = this.allNodes[i].getId();
                        parentMenuName[nodenum-1] = menuName;
                        ordernum[nodenum-1] = subflag;
                        subflag = subflag + 1;
                    }
                }
            } while (nodenum >= 1);
            //直到pid数组中没有待处理的节点为止
        }

        return items;
    }
}
