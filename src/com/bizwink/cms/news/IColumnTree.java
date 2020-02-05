package com.bizwink.cms.news;

public interface IColumnTree
{
	int getRootID();

	int getChildID(int parentID, int index);

	int getParentID(int ID);

	int getChildCount(int parentID);

	int getRecursiveChildCount(int parentID);

	int getIndexOfChild(int parentID, int child);

	boolean isRoot(int nodeid);

	boolean isLeaf(int nodeid);
}