package com.bizwink.cms.security;

import java.util.*;
import com.bizwink.cms.news.*;

public class ColumnCheck
{
	public static boolean hasPermission(Auth authToken, int columnID) throws Exception
	{
		PermissionSet columns = authToken.getPermissionSet();
		boolean value = false;
		if (authToken.getUserID().toLowerCase().equals("admin"))
			return true;
		if (columns.contains(columnID))
			return true;
		IColumnManager columnMgr = ColumnPeer.getInstance();
		List parentList = columnMgr.getParentColumns(columnID);
		int parentCount = parentList.size();
		int i = 0;
		while (i < parentCount)
		{
			Column column = (Column) parentList.get(i);
			columnID = column.getID();
			if (columnID > -1)
			{
				if (columns.contains(columnID))
				{
					value = true;
					break;
				}
			}
			i++;
		}
		return value;
	}
}
