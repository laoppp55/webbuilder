package com.bizwink.ColumnTree;


import com.bizwink.po.Column;
import java.util.List;

public interface ITree {
	Tree getSiteTree(int rownum,Column rootColumn,List<Column> nodeList);

    StringBuffer getSubTree(int rootid,Tree tree);
}
