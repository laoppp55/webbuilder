package com.bizwink.ColumnTree;

import com.bizwink.po.Column;

import java.util.*;

public class TreeManager implements ITree {
    public TreeManager() {
    }

    public Tree getSiteTree(int rownum,Column rootColumn,List<Column> nodeList) {
        Column column = null;
        Tree err = new Tree(1, "error");
        int treeRoot=0;
        int nodenum = 0;
        int ordernum=0;
        int k = 0;
        int parentID=-1;

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

            if (rootColumn != null) {
                id[0] = rootColumn.getID().intValue();
                pid[0] = rootColumn.getPARENTID().intValue();
                cname[0] = rootColumn.getCNAME();
                ename[0] = rootColumn.getENAME();
                orderid[0] = rootColumn.getORDERID().intValue();
                links[0] = rootColumn.getDIRNAME();
                audited[0] = rootColumn.getISAUDITED();
                hasArticleModel[0] = rootColumn.getHASARTICLEMODEL();

                //获取该站点根的ID
                arr[k] = new node();
                arr[k].setChName(rootColumn.getCNAME());
                arr[k].setEnName(rootColumn.getENAME());
                arr[k].setId(rootColumn.getID().intValue());
                arr[k].setLinkPointer(0);
                arr[k].setOrderNum(rootColumn.getORDERID().intValue());
                arr[k].setAudited(0);
                arr[k].setHasArticleModel(0);
                arr[k].setColumnURL(rootColumn.getDIRNAME());

                for (int i=1; i<nodeList.size()+1; i++) {
                    column = new Column();
                    column = (Column)nodeList.get(i-1);
                    id[i] = column.getID().intValue();
                    pid[i] = column.getPARENTID().intValue();
                    cname[i] = column.getCNAME();
                    ename[i] = column.getENAME();
                    orderid[i] = column.getORDERID().intValue();
                    links[i] = column.getDIRNAME();
                    audited[i] = column.getISAUDITED();
                    hasArticleModel[i] = column.getHASARTICLEMODEL();
                }

                //生成该站点的目录树结构
                k = k + 1;
                treeRoot = id[0];
                realpid[nodenum] = id[0];
                nodenum = nodenum + 1;
                while (nodenum > 0) {
                    ordernum = 0;
                    nodenum = nodenum - 1;
                    parentID = realpid[nodenum];
                    for (int i = 0; i < pid.length; i++) {
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

                Tree columnTree = new Tree(rownum,"",treeRoot,arr,nodeList);
                return columnTree;
            }
        }
        catch (Exception e) {
            System.err.println("Error" + e);
            e.printStackTrace();
        }

        return err;
    }

    public StringBuffer getSubTree(int rootid,Tree colTree) {
        StringBuffer buf = new StringBuffer();                        //存储生成的菜单树

        if (colTree.getNodeNum() > 1) {
            node[] treeNodes = colTree.getAllNodes();                     //获取该树的所有节点
            int node[] = new int[colTree.getNodeNum()];                   //遍历树所需要的节点数组，存储当前未处理的节点
            int subnode_num[] = new int[colTree.getNodeNum()];            //存储每个节点子节点的个数
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
                for (i = 0; i < colTree.getNodeNum(); i++) {
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
                for (i = 0; i < colTree.getNodeNum(); i++) {
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
                if (nodenum == 0) {
                    for(i=0; i<depth; i++)  buf.append("    ]}\r\n");
                    buf.append("];");
                }
            } while (nodenum >= 1);
            //直到pid数组中没有待处理的节点为止
        }

        return buf;
    }
}