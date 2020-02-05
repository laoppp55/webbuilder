package com.bizwink.util;

import com.bizwink.cms.tree.snode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by petersong on 17-3-11.
 */
public class GeneralMethod {
    //数字序列二分查找递归实现
    public static int binSearch(int srcArray[], int start, int end, int key) {
        int mid = (end - start) / 2 + start;
        if (srcArray[mid] == key) {
            return mid;
        }
        if (start >= end) {
            return -1;
        } else if (key > srcArray[mid]) {
            return binSearch(srcArray, mid + 1, end, key);
        } else if (key < srcArray[mid]) {
            return binSearch(srcArray, start, mid - 1, key);
        }
        return -1;
    }

    //数字序列二分查找普通循环实现
    public static int binSearch(int srcArray[], int key) {
        int mid = srcArray.length / 2;
        if (key == srcArray[mid]) {
            return mid;
        }

        int start = 0;
        int end = srcArray.length - 1;
        while (start <= end) {
            mid = (end - start) / 2 + start;
            if (key < srcArray[mid]) {
                end = mid - 1;
            } else if (key > srcArray[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    //字符序列二分查找递归实现
    public static int binSearchForString(String srcArray[], int start, int end, String key) {
        int mid = (end - start) / 2 + start;
        if (srcArray[mid] == key) {
            return mid;
        }
        if (start >= end) {
            return -1;
        } else if (key.compareTo(srcArray[mid]) > 0) {
            return binSearchForString(srcArray, mid + 1, end, key);
        } else if (key.compareTo(srcArray[mid]) < 0) {
            return binSearchForString(srcArray, start, mid - 1, key);
        }
        return -1;
    }

    //字符序列二分查找普通循环实现
    public static int binSearchForString(String srcArray[], String key) {
        int mid = srcArray.length / 2;
        if (key == srcArray[mid]) {
            return mid;
        }

        int start = 0;
        int end = srcArray.length - 1;
        while (start <= end) {
            mid = (end - start) / 2 + start;
            if (srcArray[mid] == null) srcArray[mid] = "";
            if (key.compareTo(srcArray[mid]) < 0) {
                end = mid - 1;
            } else if (key.compareTo(srcArray[mid]) > 0){
                start = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static List orderItems(List items,String orderType) {
        if (orderType.equalsIgnoreCase("desc")) {
            //降序排列
            for(int ii=0;ii<items.size()-1;ii++) {
                //获取列表中的最小值
                snode current_node = (snode)items.get(ii);
                snode t_max_node = current_node;
                int kk = 0;
                for(int jj=ii+1;jj<items.size();jj++) {
                    snode t_node = (snode)items.get(jj);
                    if (t_node.getId().compareTo(t_max_node.getId())>0) {
                        t_max_node = t_node;
                        kk = jj;
                    }
                }

                if (t_max_node.getId().compareTo(current_node.getId())>0) {
                    items.set(ii,t_max_node);
                    items.set(kk,current_node);
                }
            }
        } else {
            //升序排列
            for(int ii=0;ii<items.size()-1;ii++) {
                //获取列表中的最小值
                snode current_node = (snode)items.get(ii);
                snode t_min_node = current_node;
                int kk = 0;
                for(int jj=ii+1;jj<items.size();jj++) {
                    snode t_node = (snode)items.get(jj);
                    if (t_node.getId().compareTo(t_min_node.getId())<0) {
                        t_min_node = t_node;
                        kk = jj;
                    }
                }

                if (t_min_node.getId().compareTo(current_node.getId())>0) {
                    items.set(ii,t_min_node);
                    items.set(kk,current_node);
                }
            }
        }

        return items;
    }

    public static DeReplication deDualReplication(List<snode> messages) {
        DeReplication drresult = new DeReplication();
        List listTemp = new ArrayList();
        List<String> ids = new ArrayList<String>();
        List errors = new ArrayList();

        for(snode i:messages){
            if(!ids.contains(i.getId())){
                listTemp.add(i);
                ids.add(i.getId());
            } else {
                errors.add(i);
            }
        }

        drresult.setErrors(errors);
        drresult.setMessages(listTemp);

        return drresult;
    }

    public static DeReplication deReplication(List messages) {
        DeReplication drresult = new DeReplication();
        List result = new ArrayList();
        List<String> ids = new ArrayList<String>();
        List errors = new ArrayList();
        Iterator it=messages.iterator();
        while(it.hasNext()){
            snode a=(snode)it.next();
            if(ids.contains(a.getId())){
                it.remove();
                a.setAudited(2);        //EXCEL文件中重复的节点
                errors.add(a);
            } else{
                if (a.getLinkPointer()!=null && a.getLinkPointer()!="")
                    if (a.getId().startsWith(a.getLinkPointer())) {
                        if (a.getChName() != null && a.getChName() != "") {
                            if (a.getChName().length()<200) {
                                if(a.getId().length()<16) {
                                    if (a.getLinkPointer().length()<16) {
                                        result.add(a);
                                        ids.add(a.getId());
                                    } else {
                                        it.remove();
                                        a.setAudited(7);         //本节点分类编码的父类编码超长
                                        errors.add(a);
                                    }
                                } else {
                                    it.remove();
                                    a.setAudited(6);         //本节点分类编码超长
                                    errors.add(a);
                                }
                            } else {
                                it.remove();
                                a.setAudited(5);         //本节点中文名称超长
                                errors.add(a);
                            }
                        } else {
                            it.remove();
                            a.setAudited(4);         //本节点中文名称为空
                            errors.add(a);
                        }
                    } else {                       //本节点的父节点ID错误
                        it.remove();
                        a.setAudited(3);
                        errors.add(a);
                    }
                else {                            //第一层节点
                    result.add(a);
                    ids.add(a.getId());
                }
            }
        }

        drresult.setErrors(errors);
        drresult.setMessages(result);

        return drresult;
    }
}
