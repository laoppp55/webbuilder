package com.bizwink.cms.tree;

import com.bizwink.util.GeneralMethod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 17-3-11.
 */
public class BizTreeManager {
    public static List compareBetweenTree(ClassTree tree1,ClassTree treeforexcel) {
        List double_items = new ArrayList();
        Map maptree1 = tree1.getMaptree();
        Map mapforexceltree = treeforexcel.getMaptree();
        List nodes1 = tree1.makeTreeTable(maptree1,"");
        List nodes2 = treeforexcel.makeTreeTable(mapforexceltree,"");
        String[] nodes1_id = new String[tree1.getNodeNum()];
        String[] nodes2_id = new String[treeforexcel.getNodeNum()];

        for(int ii=0;ii<tree1.getNodeNum();ii++) {
            snode node = (snode)nodes1.get(ii);
            nodes1_id[ii] = node.getId();
        }

        for(int ii=0;ii<treeforexcel.getNodeNum();ii++) {
            snode node = (snode)nodes2.get(ii);
            nodes2_id[ii] = node.getId();
        }

        for(int ii=0;ii<nodes1_id.length;ii++) {
            String key = nodes1_id[ii];
            if (key != null && key != "") {
                int key_num = GeneralMethod.binSearchForString(nodes2_id, key);
                if (key_num != -1) {
                    double_items.add(mapforexceltree.get(key));
                }
            }
        }

        return double_items;
    }

    public static ClassTree getClassesTreeByNodes(List nodes) {
        int rownum = nodes.size()+1;
        int i = 0;
        int k = 0;
        String parentID = null;
        int nodenum = 0;
        int ordernum = 0;
        snode[] arr = null;
        Map maptree = new HashMap();

        String[] pid = new String[rownum];
        String[] realpid = new String[rownum];
        String[] id = new String[rownum];
        int[] orderid = new int[rownum];
        int[] audited = new int[rownum];
        int[] hasArticleModel = new int[rownum];
        String[] cname = new String[rownum];
        String[] ename = new String[rownum];
        String[] links = new String[rownum];
        arr = new snode[rownum+1];

        //初始化根节点
        id[i] = "";
        pid[i] = "0";
        cname[i] = "根节点";
        ename[i] = "my root";
        orderid[i] = 0;
        links[i] = "";
        audited[i] = 0;
        hasArticleModel[i] = 0;
        i = i + 1;

        //获取该站点根的ID
        arr[k] = new snode();
        arr[k].setChName(cname[0]);
        arr[k].setEnName(ename[0]);
        arr[k].setId(id[0]);
        arr[k].setLinkPointer("0");
        arr[k].setOrderNum(orderid[0]);
        arr[k].setAudited(0);
        arr[k].setHasArticleModel(0);
        arr[k].setColumnURL(links[0]);
        k = k + 1;
        realpid[nodenum] = id[0];
        nodenum = nodenum + 1;
        maptree.put(id[0],arr[k]);

        //nodes列表取值从0开始，ID[]、PID[]等数组的赋值从1开始
        for (i=1;i<=nodes.size();i++) {
            snode node1 = (snode)nodes.get(i-1);
            id[i] = node1.getId();     //rs.getString("C_CODE");
            pid[i] = node1.getLinkPointer();    //rs.getString("C_PAR_CODE");
            cname[i] = node1.getChName();           //rs.getString("C_NAME");
            ename[i] = node1.getUnit();             //rs.getString("C_UNIT");
            orderid[i] = i;                            //rs.getInt("C_ORDER_BY");
            links[i] = node1.getDesc();                      //rs.getString("C_DESC");
        }

        //生成该站点的目录树结构
        List<String> subnodes = null;
        while (nodenum > 0) {
            ordernum = 0;
            nodenum = nodenum - 1;
            parentID = realpid[nodenum];
            snode s1 = null;
            if (parentID.equalsIgnoreCase(""))
                s1 = arr[0];
            else
                s1 = (snode)maptree.get(parentID);
            subnodes = new ArrayList<String>();
            for (i = 0; i < pid.length; i++) {
                if (pid[i]==null) pid[i] = "";
                if (pid[i].equalsIgnoreCase(parentID)) {
                    arr[k] = new snode();
                    arr[k].setChName(cname[i]);
                    arr[k].setEnName(ename[i]);
                    arr[k].setId(id[i]);
                    arr[k].setLinkPointer(parentID);    //如果父节点不是根节点，所有节点的LinkPointer设置为父节点的ID
                    arr[k].setOrderNum(orderid[i]);
                    arr[k].setColumnURL(links[i]);
                    arr[k].setAudited(audited[i]);
                    arr[k].setHasArticleModel(hasArticleModel[i]);
                    subnodes.add(id[i]);
                    maptree.put(id[i],arr[k]);
                    k = k + 1;
                    ordernum = ordernum + 1;
                    realpid[nodenum] = id[i];
                    nodenum = nodenum + 1;
                }
            }
            s1.setSubnodes(subnodes);
            maptree.put(parentID,s1);
        }

        return new ClassTree(k,"", arr,maptree);
    }
}
