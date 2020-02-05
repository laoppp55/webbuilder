package com.bizwink.cms.security;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-23
 * Time: 21:28:36
 * To change this template use File | Settings | File Templates.
 */
public class RolesSet {
    private TreeSet set = new TreeSet();

    public boolean add(Role role)
    {
        return set.add(role);
    }

    public boolean add(RolesSet rolesSet)
    {
        return set.addAll(rolesSet.set);
    }

    public boolean remove(Role role)
    {
        return set.remove(role);
    }

    public void clear()
    {
        set.clear();
    }

    public boolean contains(Role role)
    {
        return set.contains(role);
    }

    public boolean contains(String rolename)
    {
        boolean existflag = false;
        Iterator iter = set.iterator();
        while (iter.hasNext())
        {
            Role role = (Role)iter.next();
            if (rolename.equalsIgnoreCase(role.getRolename().trim())) {
                existflag =  true;
                break;
            }
        }

        return existflag;
    }

    public Role getRole(String rolename)
    {
        Iterator iter = set.iterator();
        while (iter.hasNext())
        {
            Role role = (Role)iter.next();
            if (rolename.equalsIgnoreCase(role.getRolename().trim()))
            {
                return role;
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
