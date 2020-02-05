package com.bizwink.cms.security;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;

/**
 * An implementation of user database peer using instantdb, an embedded java
 * SQL database.
 *
 */

public class RightPeer implements IRightManager {
	PoolServer cpool;

	public RightPeer(PoolServer cpool) {
		this.cpool = cpool;
	}

	public static IRightManager getInstance() {
		return CmsServer.getInstance().getFactory().getRightManager();
	}

	/** SQL statement for getting an right. */
	private static final String SQL_GETRIGHT =
	        "SELECT rightid, rightname, rightcat,rightdesc FROM tbl_right WHERE rightID=?";

	public Right getRight(String rightID) throws CmsException {
		Connection conn = null;
		PreparedStatement pstmt;
		ResultSet rs;
		Right right = null;
		try {
			conn = cpool.getConnection();
			pstmt = conn.prepareStatement(SQL_GETRIGHT);
			pstmt.setString(1, rightID);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				right = load(rs);
			}
			rs.close();
			pstmt.close();

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					 // close the pooled connection
                    cpool.freeConnection(conn);
                } catch (Exception e) {
					System.out.println( "Error in closing the pooled connection "+e.toString());
				}
			}
		}
		return right;
	}

	/** SQL statement for creating a right record.*/
	private static final String SQL_CREATERIGHT =
	        "INSERT INTO tbl_right(rightid, rightname,rightcat, rightdesc) " +
	        "VALUES (?, ?, ?,?)";

	public void create(Right right) throws CmsException {
		Connection conn = null;
		PreparedStatement pstmt;

		try {
			try {
				conn = cpool.getConnection();
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(SQL_CREATERIGHT);
				String rightName = StringUtil.iso2gb(right.getRightName());
				String rightCat = StringUtil.iso2gb(right.getRightCat());
				String rightDesc = StringUtil.iso2gb(right.getRightDesc());
				pstmt.setInt(1, right.getRightID());
				pstmt.setString(2, rightName);
				pstmt.setString(3, rightCat);
				pstmt.setString(4, rightDesc);
				pstmt.executeUpdate();
				pstmt.close();

				conn.commit();
			} catch (NullPointerException e) {
				e.printStackTrace();
				conn.rollback();
				throw new CmsException("Database exception: create Right failed.");
			} finally {
				if (conn != null) {
					try {
						 // close the pooled connection
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
						System.out.println( "Error in closing the pooled connection "+e.toString());
					}
				}
			}
		} catch (SQLException e) {
			throw new CmsException("Database exception: can't rollback?");
		}
	}

	/** SQL statement for updating an right. */
	private static final String SQL_UPDATERIGHT =
	        "UPDATE tbl_right set rightname=?, rightcat=?, rightdesc=? WHERE rightID=?";

	public void update(Right right) throws CmsException {
		Connection conn = null;
		PreparedStatement pstmt;

		try {
			try {
				conn = cpool.getConnection();
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(SQL_UPDATERIGHT);
				String rightName = StringUtil.iso2gb(right.getRightName());
				String rightCat = StringUtil.iso2gb(right.getRightCat());
				String rightDesc = StringUtil.iso2gb(right.getRightDesc());
				pstmt.setString(1, rightName);
				pstmt.setString(2, rightCat);
				pstmt.setString(3, rightDesc);
				pstmt.setInt(4, right.getRightID());
				pstmt.executeUpdate();
				pstmt.close();

				conn.commit();
			} catch (NullPointerException e) {
				e.printStackTrace();
				conn.rollback();
				throw new CmsException("Database exception: update Right failed.");
			} finally {
				if (conn != null) {
					try {
						 // close the pooled connection
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
						System.out.println( "Error in closing the pooled connection "+e.toString());
					}
				}
			}
		} catch (SQLException e) {
			throw new CmsException("Database exception: can't rollback?");
		}
	}
	/** SQL statement for deleting a right. */
	private static final String SQL_DELETERIGHT =
	        "DELETE from tbl_right WHERE rightID=?";

	public void remove(Right right) throws CmsException {
		Connection conn = null;
		PreparedStatement pstmt;

		try {
			try {
				conn = cpool.getConnection();
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(SQL_DELETERIGHT);
				pstmt.setInt(1, right.getRightID());
				pstmt.executeUpdate();
				pstmt.close();

				conn.commit();
			} catch (NullPointerException e) {
				e.printStackTrace();
				conn.rollback();
				throw new CmsException("Database exception: delete Right failed.");
			} finally {
				if (conn != null) {
					try {
						 // close the pooled connection
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
						System.out.println( "Error in closing the pooled connection "+e.toString());
					}
				}
			}
		} catch (SQLException e) {
			throw new CmsException("Database exception: can't rollback?");
		}
	}

	private static final String SQL_GETRIGHTCOUNT =
	        "SELECT count(rightid) FROM tbl_right ";

	public int getRightCount() throws CmsException {
		Connection conn = null;
		PreparedStatement pstmt;
		ResultSet rs;
		int count = 0;

		try {
			conn = cpool.getConnection();
			pstmt = conn.prepareStatement(SQL_GETRIGHTCOUNT);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CmsException("Database exception: get right count failed.");
		} finally {
			if (conn != null) {
				try {
					 // close the pooled connection
                    cpool.freeConnection(conn);
                } catch (Exception e) {
					System.out.println( "Error in closing the pooled connection "+e.toString());
				}
			}
		}
		return count;
	}

	private static final String SQL_GETRIGHTS =
	        "SELECT rightid, rightname, rightcat, rightdesc FROM tbl_right";

	public List getRights() throws CmsException {
		Connection conn = null;
		PreparedStatement pstmt;
		ResultSet rs;

		List list = new ArrayList();
		Right right;

		try {
			conn = cpool.getConnection();
			pstmt = conn.prepareStatement(SQL_GETRIGHTS);
			rs = pstmt.executeQuery();

			while (rs.next()){
				right = load(rs);
				list.add(right);
			}

			rs.close();
			pstmt.close();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					 // close the pooled connection
                    cpool.freeConnection(conn);
                } catch (Exception e) {
					System.out.println( "Error in closing the pooled connection "+e.toString());
				}
			}
		}

		return list;
	}

	public List getRights(int startIndex, int numResults) throws CmsException {
		Connection conn = null;
		PreparedStatement pstmt;
		ResultSet rs;

		List list = new ArrayList();
		Right right;

		try {
			conn = cpool.getConnection();
			pstmt = conn.prepareStatement(SQL_GETRIGHTS);
			rs = pstmt.executeQuery();

			for (int i=0; i<startIndex; i++) {
				rs.next();
			}

			for (int i=0; i<numResults; i++) {
				if (rs.next()) {
					right = load(rs);
					list.add(right);
				}
				else {
					break;
				}
			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CmsException("Database exception: get Right permission failed.");
		} finally {
			if (conn != null) {
				try {
					 // close the pooled connection
                    cpool.freeConnection(conn);
                } catch (Exception e) {
					System.out.println( "Error in closing the pooled connection "+e.toString());
				}
			}
		}

		return list;
	}

	Right load(ResultSet rs) throws SQLException {
		Right right = new Right();
		right.setRightID(rs.getInt("rightid"));
		try {
			right.setRightName(rs.getString("rightname"));
			right.setRightCat(rs.getString("rightCat"));
			right.setRightDesc(rs.getString("rightdesc"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return right;
	}
}
