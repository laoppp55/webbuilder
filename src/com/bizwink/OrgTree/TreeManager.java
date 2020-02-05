package com.bizwink.OrgTree;

import com.bizwink.po.OrgPid;
import com.bizwink.po.Organization;

import java.util.*;

public class TreeManager implements ITree {
    List nodeList = new ArrayList();

    public TreeManager() {
    }

    public TreeManager(List columns) {
        this.nodeList = columns;
    }

    public Tree getTree(List<OrgPid> pid) {
        Organization organization = null;
        int rowNum = nodeList.size();
        Tree err = new Tree(1, "error");

        try {
            int[] pID = new int[rowNum];
            int[] id = new int[rowNum];
            String[] cname = new String[rowNum];
            String[] ename = new String[rowNum];
            int[] orderID = new int[rowNum];
            String[] links = new String[rowNum];
            node[] arr = new node[rowNum];
            int parentID=-1;

            for (int k=0; k<nodeList.size(); k++) {
                organization = new Organization();
                organization = (Organization)nodeList.get(k);
                id[k] = organization.getID().intValue();
                pID[k] = organization.getPARENT().intValue();
                cname[k] = organization.getNAME();
                ename[k] = organization.getENAME();
                orderID[k] = organization.getORDERID().intValue();
            }

            int k = 0;
            int ordernum = 0;
            for (int l=0; l<pid.size(); l++) {
                OrgPid cpid = (OrgPid)pid.get(l);
                ordernum = 0;
                parentID = cpid.getParentid();
                for (int i = 0; i < rowNum; i++) {
                    organization = (Organization)nodeList.get(i);
                    if (parentID == pID[i]) {
                        arr[k] = new node();
                        arr[k].setName(cname[i]);
                        arr[k].setEnname(ename[i]);
                        arr[k].setId(id[i]);
                        arr[k].setLinkpointer(parentID);
                        arr[k].setOrderid(orderID[i]);
                        arr[k].setIsleaf(true);
                        k = k + 1;
                        ordernum = ordernum + 1;
                    }
                }
            }

            //设置叶节点和非页节点标识
            for (int l=0; l<pid.size(); l++) {
                OrgPid cpid = (OrgPid)pid.get(l);
                parentID = cpid.getParentid();
                for(int i=0; i<k; i++) {
                    if (arr[i].getId() == parentID) {
                        arr[i].setIsleaf(false);
                        break;
                    }
                }
            }

            Tree columnTree = new Tree(rowNum, "", 0, arr, pid);
            columnTree.setPidNum(pid.size());
            return columnTree;
        }
        catch (Exception e) {
            System.err.println("Error" + e);
            e.printStackTrace();
        }

        return err;
    }

/*    public StringBuffer getSubTree(int rootid) {
        StringBuffer buf = new StringBuffer();                        //存储生成的菜单树

        if (Server.colTree.getNodeNum() > 1) {
            node[] treeNodes = Server.colTree.getAllNodes();                     //获取该树的所有节点
            int node[] = new int[Server.colTree.getNodeNum()];                   //遍历树所需要的节点数组，存储当前未处理的节点
            int subnode_num[] = new int[Server.colTree.getNodeNum()];            //存储每个节点子节点的个数
            int subnodenum = 0;                                           //记录当前节点的子节点数
            int totalsubnodenum = 1;                                      //记录某个节点下面所有子节点的数量
            int currentID = 0;                                            //当前正在处理的节点,当前节点的ID
            int i = 0;                                                    //循环变量
            int nodenum = 1;                                              //当前被处理节点的初始值
            int process_node_total = 0;                                   //当前处理节点的总数量
            //int[] ordernum = new int[Server.colTree.getNodeNum()];               //当前节点所有子节点的顺序号
            //int orderNumber = 0;                                          //当前节点在同级节点的顺序号
            int depth = 0;                                              //最深的节点层次
            node[1] = rootid;

            buf.append("var zNodes =[\r\n");
            do {
                currentID = node[nodenum];
                process_node_total = process_node_total + 1;

                //从处理的节点数组中取出当前正在处理的元素，查找该元素下的子元素
                //设置所有子节点的父菜单名称，设置所有子节点的序列号，把所有的子节点存入pid数组中
                subnodenum = 0;
                nodenum = nodenum - 1;
                int current_nodeid = 0;
                for (i = 0; i < Server.colTree.getNodeNum(); i++) {
                    if (treeNodes[i].getLinkPointer() == currentID) {
                        nodenum = nodenum + 1;
                        node[nodenum] = treeNodes[i].getId();
                        //统计当前节点的页节点数量
                        subnodenum = subnodenum + 1;
                    }

                    if (treeNodes[i].getId() == currentID) {
                        current_nodeid = i;
                    }
                }

                //记录当前节点的子节点数
                subnode_num[current_nodeid] = subnodenum;

                //寻找当前节点的父节点
                int parent_nodeid=0;
                for (i = 0; i < Server.colTree.getNodeNum(); i++) {
                    if (treeNodes[i].getId() == treeNodes[current_nodeid].getLinkPointer()) {
                        parent_nodeid = i;
                        break;
                    }
                }

                StringBuffer prefix_space = new StringBuffer();

                //如果当前节点有子节点，生成当前节点的菜单
                if (treeNodes[current_nodeid].isLeaffalg()==false) {
                    subnode_num[parent_nodeid] = subnode_num[parent_nodeid] - 1;
                    buf.append(prefix_space).append("{ name:\"" + treeNodes[current_nodeid].getChName() + " - 展开\",id:" + treeNodes[current_nodeid].getId() + ",open:true,\r\n");
                    buf.append(prefix_space).append("   children: [\r\n");
                    depth = depth + 1;
                } else {
                    if (subnode_num[parent_nodeid] > 1) {
                        subnode_num[parent_nodeid] = subnode_num[parent_nodeid] - 1;
                        buf.append(prefix_space).append("{ name:\"" + treeNodes[current_nodeid].getChName() + "\",id:" + treeNodes[current_nodeid].getId() + "},\r\n");
                    } else if (subnode_num[parent_nodeid] == 1){
                        subnode_num[parent_nodeid] = subnode_num[parent_nodeid] - 1;
                        buf.append(prefix_space).append("{ name:\"" + treeNodes[current_nodeid].getChName() + "\",id:" + treeNodes[current_nodeid].getId() + "},\r\n");
                        if (nodenum > 0)  {    //不是最后一个节点
                            buf.append(prefix_space).append("]},\r\n");
                            depth = depth - 1;
                        } else {                                               //最后一个节点
                            buf.append(prefix_space).append("]}\r\n");
                            depth = depth - 1;
                        }
                    } else if (subnode_num[parent_nodeid] == 0){
                        buf.append(prefix_space).append("{ name:\"" + treeNodes[current_nodeid].getChName() + "\",id:" + treeNodes[current_nodeid].getId() + "},\r\n");
                    }
                }

                //处理完最后一个节点,写结尾标识
                //if (process_node_total == Server.colTree.getNodeNum()) {
                System.out.println("depth=" + depth);
                if (nodenum == 0) {
                    for(i=0; i<depth; i++)  buf.append("    ]}\r\n");
                    buf.append("];");
                }
            } while (nodenum >= 1);
            //直到pid数组中没有待处理的节点为止
        }

        return buf;
    }*/
}