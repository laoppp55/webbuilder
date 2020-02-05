package com.bizwink.cms.business.Order;

import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;

import java.awt.*;
import java.io.InputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.Sheet;
import jxl.Cell;

public class DocumentCreateCard extends Thread {
    private PoolServer cpool;
    private String begintime;
    private String endtime;
    private int denominations;
    private int number;
    private int siteid;

    public DocumentCreateCard(PoolServer cpool, String begintime, String endtime, int denominations, int number, int siteid) {
        this.cpool = cpool;
        this.begintime = begintime;
        this.endtime = endtime;
        this.denominations = denominations;
        this.number = number;
        this.siteid = siteid;
    }

    public DocumentCreateCard() {

    }

    public void run() {
        //生成序列号
        String randomnum = "OO";
        String random = "";


        randomnum = "";
        random = String.valueOf(Math.random());
        random = random.substring(random.indexOf(".") + 1, random.indexOf(".") + 11);
        randomnum = randomnum + random;

        //生成激活码
        String randomnumcode = "";
        String randomcode = "";
        randomnumcode = "";
        randomcode = String.valueOf(Math.random());
        randomcode = randomcode.substring(randomcode.indexOf(".") + 1, randomcode.indexOf(".") + 11);
        randomnumcode = randomnumcode + randomcode;

        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        int i = 0;
        while (i < number) {
            int id = 0;
            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = cpool.getConnection();
                if (cpool.getType().equals("oracle")) {
                    id = sequenceMgr.getSequenceNum("ShoppingCard");
                } else {
                    id = sequenceMgr.nextID("ShoppingCard");
                }
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("insert into tbl_shoppingcard(id,siteid,cardnum,denomination,begintime,endtime,code,createtime,activation,ischeck) " +
                        "values(?,?,?,?,?,?,?,?,?,?)");
                pstmt.setInt(1,id);
                pstmt.setInt(2, siteid);
                pstmt.setString(3, randomnum);
                pstmt.setInt(4, denominations);
                pstmt.setString(5, begintime);
                pstmt.setString(6, endtime);
                pstmt.setString(7, randomnumcode);
                pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(9, 0);
                pstmt.setInt(10, 0);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            catch (Exception e) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
            i++;
            randomnum = "";
            random = String.valueOf(Math.random());
            random = random.substring(random.indexOf(".") + 1, random.indexOf(".") + 11);
            randomnum = randomnum + random;

            randomnumcode = "";
            randomcode = String.valueOf(Math.random());
            randomcode = randomcode.substring(randomcode.indexOf(".") + 1, randomcode.indexOf(".") + 11);
            randomnumcode = randomnumcode + randomcode;
        }
    }
}