package com.bizwink.util;

import net.sf.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 17-12-9.
 */
public class JSON_Str_To_ObjArray {
    public static List Transfer_JsonStr_To_ObjArray(String jsonstr) {
        JSONArray json = JSONArray.fromObject(jsonstr);
        List ztreenodes = (List)JSONArray.toCollection(json, zTreeNodeObj.class);
        return ztreenodes;
    }

    public static List Transfer_JsonStr_To_ObjArray(JSONArray jsonArray) {
        List ztreenodes = (List)JSONArray.toCollection(jsonArray, zTreeNodeObj.class);
        return ztreenodes;
    }

    public static List<String> genOptionsStr(List nodes) {
        //获取列表中的根节点
        Integer rootId = 1;
        int num = 1;
        List<Integer> Ids = new ArrayList<Integer>();        //被处理节点的ID列表
        List<String> results = new ArrayList<String>();      //返回结果列表变量
        StringBuffer tbuf = null;                            //存储每个节点的处理结果
        Ids.add(rootId);
        zTreeNodeObj current_node = null;
        while (Ids.size()>0) {
            num = num -1;
            tbuf = new StringBuffer();
            //当前处理的节点的父ID
            Integer cid = Ids.get(0);
            //移走当前节点的父ID
            Ids.remove(0);
            List<zTreeNodeObj> subnodes = new ArrayList<zTreeNodeObj>();
            for(int ii=0;ii<nodes.size();ii++) {
                zTreeNodeObj nodeObj = (zTreeNodeObj)nodes.get(ii);
                //寻找当前节点的子节点，并把这些节点的ID保存到待处理的节点ID列表中
                if (nodeObj.getpId()==cid){
                    //Ids.add(0,nodeObj.getId());
                    subnodes.add(nodeObj);
                    num = num + 1;
                }
                //获取当前处理的节点
                if (nodeObj.getId() == cid) current_node = nodeObj;
            }

            //对当前节点的子节点按照ID从低到高的顺序进行排序
            SortList<zTreeNodeObj> sortList = new SortList<zTreeNodeObj>();
            sortList.Sort(subnodes, "getId", "desc");

            //将当前节点的子节点ID按照从低到高的顺序保存到Ids列表中
            for(int ii=0;ii<subnodes.size();ii++) {
                zTreeNodeObj nodeObj = subnodes.get(ii);
                Ids.add(0, nodeObj.getId());
            }

            //将当前节点的值保存到返回列表变量results中
            for(int jj=0;jj<current_node.getLevel();jj++) {
                tbuf.append("&nbsp;&nbsp;");
            }

            tbuf.append(current_node.getName()+"\r\n");

            results.add(tbuf.toString());
        }

        return results;
    }
}
