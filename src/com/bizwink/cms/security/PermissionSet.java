package com.bizwink.cms.security;

import java.util.*;
import java.io.*;

public class PermissionSet implements Serializable
{
    private TreeSet set = new TreeSet();

    public boolean add(Permission permission)
    {
        return set.add(permission);
    }

    public boolean add(PermissionSet permissionSet)
    {
        return set.addAll(permissionSet.set);
    }

    public boolean remove(Permission permission)
    {
        return set.remove(permission);
    }

    public void clear()
    {
        set.clear();
    }

    public boolean contains(Permission permission)
    {
        return set.contains(permission);
    }

    public boolean contains(int pid)
    {
        Iterator iter = set.iterator();
        while (iter.hasNext())
        {
            Permission permission = (Permission)iter.next();
            if (pid == permission.getRightID())
                return true;
        }
        return false;
    }

    public Permission getPermission(int permissionID)
    {
        Iterator iter = set.iterator();
        while (iter.hasNext())
        {
            Permission permission = (Permission)iter.next();
            if (permissionID ==  permission.getRightID())
            {
                //System.out.println("ll" + permission.getColumnListOnRight().size());
                return permission;
            }
        }
        return null;
    }

    public Iterator elements()
    {
        return set.iterator();
    }

    public int size()
    {
        return set.size();
    }
}