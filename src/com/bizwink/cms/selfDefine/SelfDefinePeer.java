package com.bizwink.cms.selfDefine;

import java.sql.*;
import java.util.List;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;

public class SelfDefinePeer implements ISelfDefineManager {
    PoolServer cpool;

    public SelfDefinePeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ISelfDefineManager getInstance() {
        return CmsServer.getInstance().getFactory().getSelfDefineManager();
    }

    public void insertData(List nv) throws SelfDefineException {
        Connection conn = null;
        PreparedStatement pstmt;
        SelfDefine sd = null;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("insert into smp1(id,");
            int i=0;
            for(i=0; i<nv.size()-1; i++) {
                sd = new SelfDefine();
                sd = (SelfDefine)nv.get(i);
                sql.append(sd.getName()+",");
            }
            if (nv.size()>0) {
                sd = (SelfDefine)nv.get(i);
                sql.append(sd.getName());
            }
            sql.append(") values (");

            for(i=0; i<nv.size()-1; i++) {
                sql.append("?,");
            }
            if (nv.size()>0) {
                sql.append("?,?)");
            }

            System.out.println(sql.toString());
            ISequenceManager sequnceMgr = SequencePeer.getInstance();

            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1,sequnceMgr.getSequenceNum("SelfDefine"));
            for(i=1; i<=nv.size(); i++) {
                sd = new SelfDefine();
                sd = (SelfDefine)nv.get(i-1);
                if (sd.getType().equals("text") || sd.getType().equals("password")) {
                    pstmt.setString(i+1,sd.getValue());
                }
                if (sd.getType().equals("radio") || sd.getType().equals("select")) {
                    pstmt.setInt(i+1,Integer.parseInt(sd.getValue()));
                }
            }
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int createTable(String sql) throws SelfDefineException {
        Connection conn = null;
        PreparedStatement pstmt;
        int retcode = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            retcode = -10;
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return retcode;
    }

    public int editTable(List newcol,List delcol) throws SelfDefineException {
        Connection conn = null;
        PreparedStatement pstmt;
        int retcode = 0;
        StringBuffer sql = new StringBuffer();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            retcode = -10;
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return retcode;
    }

    public int delTable(String sql) throws SelfDefineException {
        Connection conn = null;
        PreparedStatement pstmt;
        int retcode = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            retcode = -10;
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return retcode;
    }
}
