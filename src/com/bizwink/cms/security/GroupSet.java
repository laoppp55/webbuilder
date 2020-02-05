package com.bizwink.cms.security;

import java.util.*;
import java.io.*;

public class GroupSet implements Serializable
{
	private TreeSet set = new TreeSet();

	public boolean add(Group group)
	{
		return set.add((Object) group);
	}

	public boolean add(GroupSet groupSet)
	{
		return set.addAll((Collection) groupSet.set);
	}

	public boolean remove(Group group)
	{
		return set.remove((Object) group);
	}

	public void clear()
	{
		set.clear();
	}

	public boolean contains(Group group)
	{
		return set.contains((Object) group);
	}

	public boolean contains(int groupID)
	{
		Iterator iter = set.iterator();
		while (iter.hasNext())
		{
			Group group = (Group) iter.next();
			if ((groupID != 0) && (groupID == group.getGroupID()))
			{
				return true;
			}
		}
		return false;
	}

	public Group getGroup(int groupID)
	{
		Iterator iter = set.iterator();
		while (iter.hasNext())
		{
			Group group = (Group) iter.next();
			if ((groupID != 0) && (groupID == group.getGroupID()))
			{
				return group;
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