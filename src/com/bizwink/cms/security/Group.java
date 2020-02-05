package com.bizwink.cms.security;

import java.util.*;

public class Group implements Comparable
{
    private int groupID;
    private int columnID;
    private int rightID;
    private int siteid;
    private String groupName;
    private String groupDesc;
    private List rightList = new ArrayList();        //用户组可以管理的权限id

    public int getGroupID()
    {
        return groupID;
    }

    public void setGroupID(int groupID)
    {
        this.groupID = groupID;
    }

    public int getSiteID()
    {
        return siteid;
    }

    public void setSiteID(int siteid)
    {
        this.siteid = siteid;
    }

    public List getRightList()
    {
        return rightList;
    }

    public void setRightList(List rightList)
    {
        this.rightList = rightList;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getGroupDesc()
    {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc)
    {
        this.groupDesc = groupDesc;
    }

    public int compareTo(Object obj)
    {
        int rid1 = ((Group) obj).getGroupID();
        int rid2 = this.getGroupID();

        if (rid1 == rid2)
            return 0;
        else
            return 1;
    }

    public void setColumnID(int columnID)
    {
        this.columnID = columnID;
    }

    public int getColumnID()
    {
        return columnID;
    }

    public void setRightID(int rightID)
    {
        this.rightID = rightID;
    }

    public int getRightID()
    {
        return rightID;
    }
}