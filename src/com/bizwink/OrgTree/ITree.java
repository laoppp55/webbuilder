package com.bizwink.OrgTree;

import com.bizwink.po.OrgPid;

import java.util.List;

public interface ITree {
	Tree getTree(List<OrgPid> pids);

   // StringBuffer getSubTree(int rootid);
}
