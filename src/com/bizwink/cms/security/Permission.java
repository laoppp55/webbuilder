package com.bizwink.cms.security;

import java.util.*;

public class Permission implements Comparable
{
    private int rightid;
    private List cList = new ArrayList();

    public int getRightID() {
        return rightid;
    }

    public void setRightID( int rightid) {
        this.rightid = rightid ;
    }

    public List getColumnListOnRight() {
        return cList;
    }

    public List getProductListOnRight() {
        return cList;
    }

    public void setColumnListOnRight(List list) {
        //System.out.println(list.size());
        this.cList = list;
    }

    public int compareTo(Object obj)
    {
        int rightID1 = ((Permission)obj).getRightID();
        int rightID2 = this.getRightID();

        if (rightID1 == rightID2)
            return 0;               //权限ID相同
        else
            return 1;               //权限ID不相同
    }
}
