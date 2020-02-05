package com.bizwink.cms.news;

import java.sql.*;
import com.bizwink.cms.server.*;

public class ColumnTree implements IColumnTree
{
	PoolServer cpool;

	public ColumnTree(PoolServer cpool) {
		this.cpool = cpool;
	}

	public static IColumnTree getInstance() {
		return (IColumnTree)CmsServer.getInstance().getFactory().getColumnTree();
	}

	private static final String GET_CHILD =
	        "SELECT id FROM tbl_column WHERE parentID = ? ORDER BY id";

	private static final String CHILD_COUNT =
	        "SELECT count(*) FROM tbl_column WHERE parentID = ?";

	private static final String INDEX_OF_CHILD =
	        "SELECT id FROM tbl_column WHERE parentID = ? ORDER BY id";

	private static final String GET_PARENTID =
	        "SELECT parentID FROM tbl_column WHERE ID = ?";

	public int getRootID()
	{
		return 0;
	}

	public int getChildID(int parentID, int index)
	{
		int columnID = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = cpool.getConnection();
			pstmt = con.prepareStatement(GET_CHILD);
			pstmt.setInt(1, parentID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next() && index > 0) {
				index--;
			}
            if (index == 0) {
				columnID = rs.getInt(1);
			}
            //rs.close();
        }
		catch (Exception e) {
			System.err.println("Error in ColumnTree:getChild("
			        + index + ")-" + e);
			e.printStackTrace();
		}
		finally {
			try {
                pstmt.close();
            }
			catch (Exception e) {
                e.printStackTrace();
            }
			try {
                cpool.freeConnection(con);
            }
			catch (Exception e) { e.printStackTrace(); }
		}
		return columnID;
	}

	public int getParentID(int ID)
	{
		int columnID = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = cpool.getConnection();
			pstmt = con.prepareStatement(GET_PARENTID);
			pstmt.setInt(1, ID);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			columnID = rs.getInt(1);
            //rs.close();
        }
		catch (Exception e) {
			System.err.println("Error in ColumnTree:getParentID" + e);
			e.printStackTrace();
		}
		finally {
			try {
                pstmt.close();
            }
			catch (Exception e) {
                e.printStackTrace();
            }
			try {
                cpool.freeConnection(con);
            }
			catch (Exception e) { e.printStackTrace(); }
		}
		return columnID;
	}

	public int getChildCount(int parentID)
	{
		int childCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = cpool.getConnection();
			pstmt = con.prepareStatement(CHILD_COUNT);
			pstmt.setInt(1, parentID);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			childCount = rs.getInt(1);
            //rs.close();
        }
		catch (Exception e) {
			System.err.println("Error in ColumnTree:getChildCount()-" + e);
			e.printStackTrace();
		}
		finally {
			try {
                pstmt.close();
            }
			catch (Exception e) {
                e.printStackTrace();
            }
			try {
                cpool.freeConnection(con);
            }
			catch (Exception e) { e.printStackTrace(); }
		}
		return childCount;
	}

	public int getRecursiveChildCount(int parentID)
	{
		int numChildren = 0;
		int num = getChildCount(parentID);
		numChildren += num;
		for (int i=0; i<num; i++) {
			int childID = getChildID(parentID,i);
			if (childID != -1) {
				numChildren += getRecursiveChildCount(childID);
			}
		}
		return numChildren;
	}

	public int getIndexOfChild(int parentID, int childID) {
		int index = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = cpool.getConnection();
			pstmt = con.prepareStatement(INDEX_OF_CHILD);
			pstmt.setInt(1, parentID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == childID) {
					break;
				}
				index++;
			}
            rs.close();
        }
		catch (Exception e) {
			System.err.println("Error in ColumnTree:getIndexOfChild()-" + e);
		}
		finally {
			try {
                pstmt.close();
            }
			catch (Exception e) {
                e.printStackTrace();
            }
			try {
                cpool.freeConnection(con);
            }
			catch (Exception e) { e.printStackTrace(); }
		}
		return index;
	}

	public boolean isRoot(int nodeid)
	{
		int columnID=-1;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = cpool.getConnection();
			pstmt = con.prepareStatement(GET_PARENTID);
			pstmt.setInt(1, nodeid);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			columnID = rs.getInt(1);
            rs.close();
        }

		catch (Exception e) {
			System.err.println("Error in ColumnTree:isRoot" + e);
			e.printStackTrace();
		}
		finally {
			try {
                pstmt.close();
            }
			catch (Exception e) {
                e.printStackTrace();
            }
			try {
                cpool.freeConnection(con);
            }
			catch (Exception e) { e.printStackTrace(); }
		}
		return (columnID==0);
	}

	public boolean isLeaf(int nodeID) {
		return (getChildCount(nodeID) == 0);
	}
}
