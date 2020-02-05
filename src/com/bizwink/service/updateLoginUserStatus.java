package com.bizwink.service;

import com.bizwink.cms.security.AuthPeer;
import com.bizwink.cms.security.IAuthManager;
import com.bizwink.cms.security.UnauthedException;
import com.bizwink.cms.sjswsbs.IWsbsManager;
import com.bizwink.cms.sjswsbs.WsbsPeer;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Map;

/**
 * Created by Administrator on 15-5-25.
 */

@Service
public class updateLoginUserStatus extends QuartzJobBean {
    private static Connection createConnection(String ip, String port, String dbinstance, String username, String password, int flag) {
        Connection conn = null;
        String dbip = "";
        String dbusername = "";
        String dbpassword = "";

        try {
            dbip = ip;
            dbusername = username;
            dbpassword = password;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (flag == 0) {
                Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                conn = DriverManager.getConnection("jdbc:weblogic:mssqlserver4:" + dbip + ":1433", dbusername, dbpassword);
            } else if (flag == 1) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":" + port + ":" + dbinstance, dbusername, dbpassword);
            } else if (flag == 2) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms?useUnicode=true&characterEncoding=GBK", dbusername, dbpassword);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }

    public static void updateIndexflag() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String s_dbip = "192.166.96.6";
            String s_username = "webbuildercms";
            String s_password = "bizwinkcms";
            String s_port = "1521";
            String s_dbinstance = "oracle10g";
            conn = createConnection(s_dbip, s_port, s_dbinstance, s_username, s_password, 1);
            if (conn != null) {
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("select t.maintitle,t.id,t.createdate from TBL_ARTICLE t where to_char(t.createdate,'yyyy-MM-dd')>'2019-08-01' and t.indexflag=2 and t.siteid=40 and t.pubflag=0");
                rs = pstmt.executeQuery();
                while(rs.next()) {
                    System.out.println(rs.getInt("id") + "==" + rs.getString("maintitle") + "==" + rs.getTimestamp("createdate").toString());
                }
                rs.close();;
                pstmt.close();

                pstmt = conn.prepareStatement("update TBL_ARTICLE t set t.indexflag=0 where to_char(t.createdate,'yyyy-MM-dd')>'2019-08-01' and t.indexflag=2 and t.siteid=40 and t.pubflag=0");
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException exp){}
        }
    }

    public void work(String dtime){
        //实现你的业务逻辑
        System.out.println("每" + dtime + "分钟修改用户登录状态!,并修改2019年8月1号后indexflag=2的文章的indexflag=0" + new Timestamp(System.currentTimeMillis()));
        updateIndexflag();
        IAuthManager authMgr = AuthPeer.getInstance();
        int i_dtime = Integer.parseInt(dtime);
        try {
            authMgr.removeAllNoActionUsers(i_dtime);
        } catch (UnauthedException exp) {
            exp.printStackTrace();
        }
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Map properties = context.getJobDetail().getJobDataMap();
        String message = (String)properties.get("message");
        this.work(message);
    }
}
