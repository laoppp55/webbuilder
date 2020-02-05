package com.bizwink.cms.tree;

import java.util.*;
import java.sql.Connection;

public interface ITree {

	Tree getTree();

	Tree getDirTree(String rootdir);

    Tree getWenbaTree();
    
	Tree getUserDirTree(String userID,String rootDir,int siteid,List clist);

	Tree getComapnyTypeTree(int siteid);

    Tree getWebsiteTypeTree(int siteid);

	Tree getUserTree(String userID,int siteid,List clist,int rightid);

	Tree getSiteTree(int siteid);

    Tree getProductTree(int siteid);

    Tree getSiteTreeIncludeSampleSite(int siteid,int SampleSiteID);

    Tree getUserTreeIncludeSampleSiteColumn(String userID, int siteid,int samsiteid,List clist);

    Tree getSiteTree(int siteid, Connection conn);

    Tree getPublish_UserTree(String userID,String rightID);

    Tree getGlobalTree(int siteID);

    Tree getGlobalTree(int siteID, int flag);

    Tree getAtricleTypeTree(String columnIDs);

    Tree getCopyColumnTree(String columnIDs);

    Tree getSubTreeFromColumn(int siteid, int columnid);

	int getRootID();

	int getChildID(int parentID, int index);

	int getParentID(int ID);

	int getChildCount(int parentID);

	int getRecursiveChildCount(int parentID);

	int getIndexOfChild(int parentID, int child);

	boolean isRoot(int nodeid);

	boolean isLeaf(int nodeid);

    int[] getChildsOfColumnId(int parentID);

    public int[] getSubTreeColumnIDList(node[] treenodes, int columnID);
}
