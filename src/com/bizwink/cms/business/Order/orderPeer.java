package com.bizwink.cms.business.Order;

import com.bizwink.cms.security.IUserManager;
import com.bizwink.cms.security.User;
import com.bizwink.cms.security.UserPeer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.FileUtil;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.webapps.register.Uregister;
import com.yeepay.PaymentForOnlineService;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class orderPeer implements IOrderManager {
    PoolServer cpool;

    public orderPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IOrderManager getInstance() {
        return CmsServer.getInstance().getFactory().getOrderManager();
    }

    public void updteOldOrderPayStatus() {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count = 0;
        StringBuffer buf = new StringBuffer();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_orders where orderid<8180908111487");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Map<String, String> qr = PaymentForOnlineService.queryByOrder(String.valueOf(rs.getLong("orderid")));	            // 调用后台外理查询方法
                if (qr.get("rb_PayStatus").equals("SUCCESS")) {
                    buf.append(rs.getLong("orderid") + "=order pay status=" + qr.get("rb_PayStatus") + "=" + qr.get("r3_Amt") + "\r\n");
                }
                count = count + 1;
                System.out.println(count + "==" + rs.getLong("orderid") + "=order pay status=" + qr.get("rb_PayStatus") + "=" + qr.get("r3_Amt"));
            }
            System.out.println("订单状态查询完毕");
            FileUtil.writeFile(buf,"/usr/local/log/paystatus.txt");
            rs.close();
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

    public List getOrderList(int startindex, int range, int status, String sqlstr) throws OrderException {
        List list = new ArrayList();
        Order order;
        if (sqlstr == null || "".equals(sqlstr)) {
            sqlstr = "select o.* from tbl_orders o where o.status=" + status + " order by o.createdate desc";
        } else {
            sqlstr = sqlstr.replaceAll("@", "%");
        }

        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();
            if (range != 0) {
                for (int i = 0; i < startindex; i++) {
                    rs.next();
                }
                for (int i = 0; i < range && rs.next(); i++) {
                    order = load(rs);
                    list.add(order);
                }
            } else {
                while (rs.next()) {
                    order = load(rs);
                    list.add(order);
                }
            }
            rs.close();
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
        return list;
    }

    public List getOrderList(int startindex, int range, int siteid,int status) {
        List list = new ArrayList();
        Order order=null;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            System.out.println("startindex==" + startindex);
            System.out.println("range==" + range);
            System.out.println("siteid==" + siteid);
            System.out.println("status==" + startindex);

            conn = cpool.getConnection();
            //mysql
            //pstmt = conn.prepareStatement("select o.* from tbl_orders o where o.siteid =? and o.nouse = 1 and o.status=? order by o.createdate desc limit ?,?");
            pstmt = conn.prepareStatement("select b.* from (select a.*,ROWNUM RN from (select o.* from tbl_orders o where o.siteid =? and o.nouse = 1 and o.status=? order by o.createdate desc)  a) b where RN between ? and ? ");
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,status);
            pstmt.setInt(3,startindex);         //从那条记录开始取数据
            pstmt.setInt(4,range);              //取出数据的条数
            rs = pstmt.executeQuery();
            while (rs.next()) {
                order = load(rs);
                list.add(order);
            }
            rs.close();
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
        return list;
    }

    public List searchOrderList(int startindex, int range, int siteid,int status,String whereClause,String startday,String endday,int orgtype,int comid,int deptid,int uid,int source) {
        List list = new ArrayList();
        Order order=null;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            java.util.Date s_datetime = null;
            java.util.Date e_datetime = null;
            //设置查询开始日期，是最终支付日期
            if (startday!=null && startday!="") {
                s_datetime = format.parse(startday);
                if (cpool.getType().equalsIgnoreCase("oracle")){
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and paydate>=to_date('" + startday + "','yyyy-MM-dd hh:mm:ss')";
                    else
                        whereClause = whereClause + "paydate>=to_date('" + startday + "','yyyy-MM-dd hh:mm:ss')";
                } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and paydate>'" + startday + "'";
                    else
                        whereClause = whereClause + "paydate>'" + startday + "'";
                } else if (cpool.getType().equalsIgnoreCase("mysql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and paydate>='" + startday + "'";
                    else
                        whereClause = whereClause + "paydate>='" + startday + "'";
                }
            }
            //设置查询结束日期，是最终支付日期
            if (endday!=null && endday!="") {
                e_datetime = format.parse(endday);
                if (cpool.getType().equalsIgnoreCase("oracle")){
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and paydate<=to_date('" + endday + "','yyyy-MM-dd hh:mm:ss')";
                    else
                        whereClause = whereClause + "paydate<=to_date('" + endday + "','yyyy-MM-dd hh:mm:ss')";
                } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and paydate<'" + startday + "'";
                    else
                        whereClause = whereClause + "paydate<'" + startday + "'";
                } else if (cpool.getType().equalsIgnoreCase("mysql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and paydate<='" + endday + "'";
                    else
                        whereClause = whereClause + "paydate<='" + endday + "'";
                }
            }

            //设置基于用户ID的查询条件
            IUserManager userManager = UserPeer.getInstance();
            List<User> users = null;
            String userids = "";
            if (uid > 0) {        //查询某个用户的订单信息
                if (whereClause != null && whereClause != "") {
                    whereClause = whereClause + " and userid=" + uid;
                } else {
                    whereClause = whereClause + "userid=" + uid;
                }
            } else {              //查询某个组织的订单信息
                if (orgtype == 1) {
                    users = userManager.getUsersByCompanyid(siteid, comid);
                } else {            //orgtype=0有两种不同情况，一种是确实是部门数据管理员登录系统，另一种情况是网站管理员登录系统。
                    if (comid > 0) users = userManager.getUsersByCompanyid(siteid, comid);
                    if (deptid > 0) users = userManager.getUsersByDeptid(siteid, deptid);
                }

                //获取用户ID列表字符串
                if (users != null) {
                    for (int ii = 0; ii < users.size(); ii++) {
                        User user = users.get(ii);
                        userids = userids + user.getUid() + ",";
                    }
                    if (userids != null && userids != "") {
                        if (userids.endsWith(",")) userids = userids.substring(0, userids.length() - 1);
                        if (whereClause != null && whereClause != "") {
                            userids = "userid in (" + userids + ")";
                            whereClause = whereClause + " and " + userids;
                        } else {
                            whereClause = whereClause + " " + userids;
                        }
                    }
                }
            }

            //设置订单来源查询条件
            if (source == 2) {                      //表示订单来源于网站，数据库表中flag=1表示订单来源于网站
                if (whereClause != null && whereClause != "") {
                    whereClause = whereClause + " and flag=1";
                } else {
                    whereClause = whereClause + "flag=1";
                }
            } else if (source == 3) {              //表示订单来源于微信公众号,数据库表中flag=0表示订单来源于公众号
                if (whereClause != null && whereClause != "") {
                    whereClause = whereClause + " and flag=0";
                } else {
                    whereClause = whereClause + "flag=0";
                }
            }

            //System.out.println("select * from tbl_orders  where siteid =? and nouse = 1 and " + whereClause + " order by createdate desc limit ?,?");
            System.out.println("select b.* from (select a.*,ROWNUM RN from (select * from tbl_orders  where siteid =? and nouse = 1 and " + whereClause + " order by createdate desc) a) b where RN between ? and ?");

            conn = cpool.getConnection();
            //pstmt = conn.prepareStatement("select * from tbl_orders  where siteid =? and nouse = 1 and " + whereClause + " order by createdate desc limit ?,?");
            pstmt = conn.prepareStatement("select b.* from (select a.*,ROWNUM RN from (select * from tbl_orders  where siteid =? and nouse = 1 and " + whereClause + " order by createdate desc) a) b where RN between ? and ? ");
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,startindex);
            pstmt.setInt(3,range);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                order = load(rs);
                list.add(order);
            }
            rs.close();
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
        return list;
    }

    public int searchOrderNum(int siteid,int status,String whereClause,String startday,String endday,int orgtype,int comid,int deptid,int uid,int source) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int ordernum = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            java.util.Date s_datetime = null;
            java.util.Date e_datetime = null;
            //设置查询开始日期，是最终支付日期
            if (startday!=null && startday!="") {
                s_datetime = format.parse(startday);
                if (cpool.getType().equalsIgnoreCase("oracle")){
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and paydate>=to_date('" + startday + "','yyyy-MM-dd hh:mm:ss')";
                    else
                        whereClause = whereClause + "paydate>=to_date('" + startday + "','yyyy-MM-dd hh:mm:ss')";
                } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and paydate>'" + startday + "'";
                    else
                        whereClause = whereClause + "paydate>'" + startday + "'";
                } else if (cpool.getType().equalsIgnoreCase("mysql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and paydate>='" + startday + "'";
                    else
                        whereClause = whereClause + "paydate>='" + startday + "'";
                }
            }
            //设置查询结束日期，是最终支付日期
            if (endday!=null && endday!="") {
                e_datetime = format.parse(endday);
                if (cpool.getType().equalsIgnoreCase("oracle")){
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and paydate<=to_date('" + endday + "','yyyy-MM-dd hh:mm:ss')";
                    else
                        whereClause = whereClause + "paydate<=to_date('" + endday + "','yyyy-MM-dd hh:mm:ss')";
                } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and paydate<'" + startday + "'";
                    else
                        whereClause = whereClause + "paydate<'" + startday + "'";
                } else if (cpool.getType().equalsIgnoreCase("mysql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and paydate<='" + endday + "'";
                    else
                        whereClause = whereClause + "paydate<='" + endday + "'";
                }
            }

            //设置基于用户ID的查询条件
            IUserManager userManager = UserPeer.getInstance();
            List<User> users = null;
            String userids = "";
            if (uid > 0) {        //查询某个用户的订单信息
                if (whereClause != null && whereClause != "") {
                    whereClause = whereClause + " and userid=" + uid;
                } else {
                    whereClause = whereClause + "userid=" + uid;
                }
            } else {              //查询某个组织的订单信息
                if (orgtype == 1) {
                    users = userManager.getUsersByCompanyid(siteid,comid);
                }else {            //orgtype=0有两种不同情况，一种是确实是部门数据管理员登录系统，另一种情况是网站管理员登录系统。
                    if (comid > 0) users = userManager.getUsersByCompanyid(siteid,comid);
                    if (deptid > 0) users = userManager.getUsersByDeptid(siteid,deptid);
                }
                //获取用户ID列表字符串
                if (users!=null) {
                    for (int ii = 0; ii < users.size(); ii++) {
                        User user = users.get(ii);
                        userids = userids + user.getUid() + ",";
                    }
                    if (userids != null && userids != "") {
                        if (userids.endsWith(",")) userids = userids.substring(0, userids.length() - 1);
                        if (whereClause != null && whereClause != "") {
                            userids = "userid in (" + userids + ")";
                            whereClause = whereClause + " and " + userids;
                        } else {
                            whereClause = whereClause + " " + userids;
                        }
                    }
                }
            }

            //设置订单来源查询条件
            if (source == 2) {                      //表示订单来源于网站，数据库表中flag=1表示订单来源于网站
                if (whereClause != null && whereClause != "") {
                    whereClause = whereClause + " and flag=1";
                } else {
                    whereClause = whereClause + "flag=1";
                }
            } else if (source == 3) {              //表示订单来源于微信公众号,数据库表中flag=0表示订单来源于公众号
                if (whereClause != null && whereClause != "") {
                    whereClause = whereClause + " and flag=0";
                } else {
                    whereClause = whereClause + "flag=0";
                }
            }

            System.out.println("select count(*) from tbl_orders  where siteid =? and nouse = 1 and " + whereClause);

            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(*) from tbl_orders  where siteid =? and nouse = 1 and " + whereClause);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) ordernum = rs.getInt(1);
            rs.close();
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

        return ordernum;
    }

    public int searchSubscribeOrderNum(int siteid,int status,String whereClause,String startday,String endday,int orgtype,int comid,int deptid,int uid,int source) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int ordernum = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            java.util.Date s_datetime = null;
            java.util.Date e_datetime = null;
            //设置查询开始日期，是最终支付日期
            if (startday!=null && startday!="") {
                s_datetime = format.parse(startday);
                if (cpool.getType().equalsIgnoreCase("oracle")){
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and t.paydate>=to_date('" + startday + "','yyyy-MM-dd hh:mm:ss')";
                    else
                        whereClause = whereClause + "t.paydate>=to_date('" + startday + "','yyyy-MM-dd hh:mm:ss')";
                } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and t.paydate>'" + startday + "'";
                    else
                        whereClause = whereClause + "t.paydate>'" + startday + "'";
                } else if (cpool.getType().equalsIgnoreCase("mysql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and t.paydate>='" + startday + "'";
                    else
                        whereClause = whereClause + "t.paydate>='" + startday + "'";
                }
            }
            //设置查询结束日期，是最终支付日期
            if (endday!=null && endday!="") {
                e_datetime = format.parse(endday);
                if (cpool.getType().equalsIgnoreCase("oracle")){
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and t.paydate<=to_date('" + endday + "','yyyy-MM-dd hh:mm:ss')";
                    else
                        whereClause = whereClause + "t.paydate<=to_date('" + endday + "','yyyy-MM-dd hh:mm:ss')";
                } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and t.paydate<'" + startday + "'";
                    else
                        whereClause = whereClause + "t.paydate<'" + startday + "'";
                } else if (cpool.getType().equalsIgnoreCase("mysql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and t.paydate<='" + endday + "'";
                    else
                        whereClause = whereClause + "t.paydate<='" + endday + "'";
                }
            }

            //设置基于用户ID的查询条件
            IUserManager userManager = UserPeer.getInstance();
            List<User> users = null;
            String userids = "";
            if (uid > 0) {        //查询某个用户的订单信息
                if (whereClause != null && whereClause != "") {
                    whereClause = whereClause + " and t.userid=" + uid;
                } else {
                    whereClause = whereClause + "t.userid=" + uid;
                }
            } else {              //查询某个组织的订单信息
                if (orgtype == 1) {
                    users = userManager.getUsersByCompanyid(siteid,comid);
                }else {            //orgtype=0有两种不同情况，一种是确实是部门数据管理员登录系统，另一种情况是网站管理员登录系统。
                    if (comid > 0) users = userManager.getUsersByCompanyid(siteid,comid);
                    if (deptid > 0) users = userManager.getUsersByDeptid(siteid,deptid);
                }
                //获取用户ID列表字符串
                if (users!=null) {
                    for (int ii = 0; ii < users.size(); ii++) {
                        User user = users.get(ii);
                        userids = userids + user.getUid() + ",";
                    }
                    if (userids != null && userids != "") {
                        if (userids.endsWith(",")) userids = userids.substring(0, userids.length() - 1);
                        if (whereClause != null && whereClause != "") {
                            userids = "t.userid in (" + userids + ")";
                            whereClause = whereClause + " and " + userids;
                        } else {
                            whereClause = whereClause + " " + userids;
                        }
                    }
                }
            }

            //设置订单来源查询条件
            if (source == 2) {                      //表示订单来源于网站，数据库表中flag=1表示订单来源于网站
                if (whereClause != null && whereClause != "") {
                    whereClause = whereClause + " and t.flag=1";
                } else {
                    whereClause = whereClause + "t.flag=1";
                }
            } else if (source == 3) {              //表示订单来源于微信公众号,数据库表中flag=0表示订单来源于公众号
                if (whereClause != null && whereClause != "") {
                    whereClause = whereClause + " and t.flag=0";
                } else {
                    whereClause = whereClause + "t.flag=0";
                }
            }

            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select sum(td.ordernum) from tbl_orders t,tbl_orders_detail td where t.orderid=td.orderid and t.siteid =? and t.nouse = 1 and " + whereClause);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) ordernum = rs.getInt(1);
            rs.close();
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

        return ordernum;
    }

    public List getOrderListByUsers(int startindex, int range, int siteid,int status,List<User> users) {
        List list = new ArrayList();
        Order order=null;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String userids = "";
        try {
            //获取用户ID列表字符串
            for(int ii=0; ii<users.size(); ii++) {
                User user = users.get(ii);
                userids = userids + user.getUid() + ",";
            }
            if (userids.endsWith(",")) userids = userids.substring(0,userids.length()-1);

            conn = cpool.getConnection();
            if (userids!=null && userids!="") {
                userids = "userid in (" + userids + ")";
                pstmt = conn.prepareStatement("select * from tbl_orders where " + userids + " and siteid =? and nouse = 1 and status=? order by createdate desc limit ?,?");
                pstmt.setInt(1,siteid);
                pstmt.setInt(2,status);
                pstmt.setInt(3,startindex);
                pstmt.setInt(4,range);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    order = load(rs);
                    list.add(order);
                }
                rs.close();
                pstmt.close();
            }
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
        return list;
    }

    public int getOrderNumByStatusAndUsers(int siteid,int status,List<User> users) {
        List list = new ArrayList();
        Order order=null;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int totalnum = 0;
        String userids = "";
        try {
            //获取用户ID列表字符串
            for(int ii=0; ii<users.size(); ii++) {
                User user = users.get(ii);
                userids = userids + user.getUid() + ",";
            }
            if (userids.endsWith(",")) userids = userids.substring(0,userids.length()-1);

            conn = cpool.getConnection();
            if (userids!=null && userids!="") {
                userids = "userid in (" + userids + ")";
                pstmt = conn.prepareStatement("select count(* )from tbl_orders  where " + userids + " and siteid =? and nouse = 1 and status=?");
                pstmt.setInt(1, siteid);
                pstmt.setInt(2, status);
                rs = pstmt.executeQuery();
                if (rs.next()) totalnum = rs.getInt(1);
                rs.close();
                pstmt.close();
            }
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

        return totalnum;
    }

    public int getSubscribeNumByStatusAndUsers(int siteid,int status,List<User> users) {
        List list = new ArrayList();
        Order order=null;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int totalnum = 0;
        String userids = "";
        try {
            //获取用户ID列表字符串
            for(int ii=0; ii<users.size(); ii++) {
                User user = users.get(ii);
                userids = userids + user.getUid() + ",";
            }
            if (userids.endsWith(",")) userids = userids.substring(0,userids.length()-1);

            conn = cpool.getConnection();
            if (userids!=null && userids!="") {
                userids = "userid in (" + userids + ")";
                pstmt = conn.prepareStatement("select sum(td.ordernum) from tbl_orders t,tbl_orders_detail td  where " + userids + " and t.orderid=td.orderid and t.siteid =? and t.nouse = 1 and t.status=?");
                pstmt.setInt(1, siteid);
                pstmt.setInt(2, status);
                rs = pstmt.executeQuery();
                if (rs.next()) totalnum = rs.getInt(1);
                rs.close();
                pstmt.close();
            }
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

        return totalnum;
    }

    public int getTotalOrderNumByStatus(int siteid,int status) {
        List list = new ArrayList();
        Order order=null;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int totalnum = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(*) from tbl_orders  where siteid =? and nouse = 1 and status=?");
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,status);
            rs = pstmt.executeQuery();
            if(rs.next()) totalnum = rs.getInt(1);
            rs.close();
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

        return totalnum;
    }

    public int getTotalSubscribeNum(int siteid,int status) {
        List list = new ArrayList();
        Order order=null;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int totalnum = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select sum(td.ordernum) from tbl_orders t,tbl_orders_detail td  where t.orderid=td.orderid and t.siteid =? and t.nouse = 1 and t.status=?");
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,status);
            rs = pstmt.executeQuery();
            if(rs.next()) totalnum = rs.getInt(1);
            rs.close();
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

        return totalnum;
    }

    public Order getAOrder(long orderid) throws OrderException {
        String sqlstr = "select o.* from tbl_orders o where orderid =?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Order order = new Order();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setLong(1, orderid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                order = load(rs);
            }
            rs.close();
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
        return order;
    }

    public int exportOrdersToExcel(int status,Timestamp startdate,Timestamp enddate)  throws OrderException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Order order = new Order();
        int exportTotalRow = 0;
        String datetime_condition = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        if (startdate != null && enddate!=null)
            datetime_condition = " and createdate>'" + format.format(startdate) + "' and createdate<'" + format.format(enddate) + "'";
        else if (startdate!=null && enddate == null)
            datetime_condition = " and createdate>'" + format.format(startdate) + "'";
        else if (startdate == null && enddate!=null)
            datetime_condition = " and createdate<'" + format.format(enddate) + "'";

        try {
            conn = cpool.getConnection();
            if (status == 9)
                pstmt = conn.prepareStatement("select count(*) from tbl_orders" + datetime_condition);
            else if (status == 0)
                pstmt = conn.prepareStatement("select count(*) from tbl_orders where status=0" + datetime_condition);
            else if (status == 1)
                pstmt = conn.prepareStatement("select count(*) from tbl_orders where status=1" + datetime_condition);
            else if (status == 2)
                pstmt = conn.prepareStatement("select count(*) from tbl_orders where status=2" + datetime_condition);
            else if (status == 3)
                pstmt = conn.prepareStatement("select count(*) from tbl_orders where status=3" + datetime_condition);
            else if (status == 4)
                pstmt = conn.prepareStatement("select count(*) from tbl_orders where status=4" + datetime_condition);
            else if (status == 5)
                pstmt = conn.prepareStatement("select count(*) from tbl_orders where status=5" + datetime_condition);
            else if (status == 6)
                pstmt = conn.prepareStatement("select count(*) from tbl_orders where status=6" + datetime_condition);
            else if (status == 7)
                pstmt = conn.prepareStatement("select count(*) from tbl_orders where status=7" + datetime_condition);
            else if (status == 8)
                pstmt = conn.prepareStatement("select count(*) from tbl_orders where status=8" + datetime_condition);
            rs = pstmt.executeQuery();
            if (rs.next()) exportTotalRow = rs.getInt(1);
            rs.close();
            pstmt.close();

            //每次从数据库取出1000行记录，将取出的数据写入到EXCEL文件



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

        return 0;
    }

    //获取在一个订单中供应商的数量
    public List getSupplierNumByOrder(long orderid) throws OrderException {
        String sqlstr = "select distinct supplierid,suppliername from tbl_orders_detail where orderid =?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        supplier sp = null;
        List suppliers = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setLong(1, orderid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sp = new supplier();
                sp.setSupplierid(rs.getInt("supplierid"));
                sp.setSuppliername(rs.getString("suppliername"));
                suppliers.add(sp);
            }
            rs.close();
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
        return suppliers;
    }

    public String getCountry(int country) throws OrderException {
        switch (country) {
            case 1:
                return "中国";
            default:
                return "中国";
        }
    }

    public String getProvince(int pid) throws OrderException {
        String province = "";
        String sqlstr = "select provname from en_province where id=?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, pid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                province = rs.getString("provname");
            }
            rs.close();
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
        return province;
    }

    public String getCity(int cid) throws OrderException {
        String city = "";
        String sqlstr = "select cityname from en_city where id=?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, cid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                city = rs.getString("cityname");
            }
            rs.close();
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
        return city;
    }

    public String getZone(int zid) throws OrderException {
        String zone = "";
        String sqlstr = "select zonename from en_zone where id=?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, zid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                zone = rs.getString("zonename");
            }
            rs.close();
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
        return zone;
    }

    public String getPayWay(int payway) throws OrderException {
        switch (payway) {
            case 1:
                return "网上支付";
            default:
                return "邮局汇款";
        }
    }

    public String getPayWay(int payway, int payflag) throws OrderException {
        switch (payway) {
            case 1: // '\001'
                return "邮政汇款";

            case 2: // '\002'
                return "银行汇款";

            case 3: // '\003'
                if (payflag == 0)
                    return "支付宝网上银行(未付费)";
                if (payflag == 1)
                    return "支付宝网上银行(已付费)";
                // fall through

            case 4: // '\004'
                if (payflag == 0)
                    return "Chinapay网上银行(未付费)";
                if (payflag == 1)
                    return "Chinapay网上银行(已付费)";
                // fall through

            case 5: // '\005'
                return "现金支付";

            case 6: // '\006'
                if (payflag == 0)
                    return "快钱支付(未付费)";
                if (payflag == 1)
                    return "快钱支付(已付费)";
            case 7:
                return "首信支付";

            default:
                return "邮政汇款";
        }
    }

    public int getOrderStatus(long orderid) throws OrderException {
        String sqlstr = "select status from tbl_orders where orderid=?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int status = -1;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                status = rs.getInt(1);
            }
            rs.close();
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
        return status;
    }

    public String getPhone(long orderid) throws OrderException {
        String phone = "--";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String sqlstr = "select phone from tbl_orders where orderid=?";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setLong(1, orderid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String userp = rs.getString("phone");
                if (userp == null || "".equals(userp)) {
                    phone = "--";
                } else {
                    phone = userp;
                }
            }
            rs.close();
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
        return phone;
    }

    private Order load(ResultSet rs) throws OrderException {
        Order order = new Order();
        try {
            order.setOrderid(rs.getLong("orderid"));
            order.setUserid(rs.getInt("userid"));
            order.setName(rs.getString("name"));
            order.setSex(rs.getInt("sex"));
            order.setAddress(rs.getString("address"));
            order.setPostcode(rs.getString("postcode"));
            order.setPhone(rs.getString("phone"));
            order.setPayWay(rs.getInt("billingway"));
            order.setTotalfee(rs.getFloat("totalfee"));
            order.setDeliveryfee(rs.getFloat("deliveryfee"));
            order.setPayfee(rs.getFloat("payfee"));
            order.setCreateDate(rs.getTimestamp("createdate"));
            order.setStatus(rs.getInt("status"));
            order.setNouse(rs.getInt("nouse"));
            order.setSendWay(rs.getInt("sendway"));
            order.setPayWay(rs.getInt("payway"));
            order.setNees_invoicee(rs.getInt("need_invoice"));
            order.setUserscores(rs.getInt("usescores"));
            order.setProvince(rs.getString("province"));
            order.setCity(rs.getString("city"));
            order.setLinktime(rs.getString("linktime"));
            order.setOrderscores(rs.getInt("orderscores"));
            order.setPayflag(rs.getInt("payflag"));
            order.setR2TrxId(rs.getString("jylsh"));
            //order.setUsecard(rs.getInt("usecard"));
            order.setProjname(rs.getString("projname"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public String getSendWay(int sendway) throws OrderException {
        switch (sendway) {
            case 0:
                return "邮政包裹";
            case 1:
                return "EMS快递";
            case 2:
                return "快递公司";
            default:
                return "--";
        }
    }

    public List getDetailList(long orderid) throws OrderException {
        List list = new ArrayList();
        String sqlstr = "select * from tbl_orders_detail where orderid = ?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setLong(1, orderid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderid(rs.getLong("orderid"));
                order.setSuppliername(rs.getString("suppliername"));
                order.setNumbercopy(rs.getInt("ordernum"));            //服务订阅的期数，可以是季度数，月份数，年或者半年
                order.setSubscribenum(rs.getInt("subscribenum"));
                //order.setXiangxi(rs.getString("xiangxi"));
                //order.setRiqi(rs.getString("riqi"));
                order.setSubscribe(rs.getInt("subscribe"));
                order.setProductid(rs.getInt("productid"));
                order.setProductname(rs.getString("productname"));
                order.setOrderNum(rs.getInt("ordernum"));                   //购买份数，购买商品数量
                order.setSaleprice(rs.getFloat("SALEPRICE"));
                order.setCardid(rs.getInt("cardid"));
                order.setUserinstarttime(rs.getDate("userinstarttime"));
                order.setUserinendtime(rs.getDate("userinendtime"));
                order.setServicestarttime(rs.getDate("servicestarttime"));
                order.setServiceendtime(rs.getDate("serviceendtime"));
                order.setCreateDate(rs.getTimestamp("createdate"));

                list.add(order);
            }
            rs.close();
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
        return list;
    }

    public List getDetail(long orderid) throws OrderException {
        List list = new ArrayList();
        String sqlstr = "select * from tbl_orders_detail where orderid = ?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setLong(1, orderid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderid(rs.getLong("orderid"));
                order.setProductid(rs.getInt("productid"));
                order.setOrderNum(rs.getInt("ordernum"));
                order.setSaleprice(rs.getFloat("SALEPRICE"));
                order.setProductname(rs.getString("productname"));
                order.setServicestarttime(rs.getDate("servicestarttime"));
                order.setServiceendtime(rs.getDate("serviceendtime"));
                order.setSubscribe(rs.getInt("subscribe"));
                order.setSubscribenum(rs.getInt("subscribenum"));
                order.setCardid(rs.getInt("cardid"));
                list.add(order);
            }
            rs.close();
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
        return list;
    }

    public String getNotes(int orderid) throws OrderException {
        String notes = "";
        String sqlstr = "select notes from tbl_orders where orderid=?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, orderid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                notes = rs.getString("notes");
            }
            rs.close();
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
        return notes;
    }

    public int getStatus(long orderid) throws OrderException {
        int status = 0;
        String sqlstr = "select status from tbl_orders where orderid=?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setLong(1, orderid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                status = rs.getInt("status");
            }
            rs.close();
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
        return status;
    }

    public void addReceiveMoney(long orderid, String jingbanren) throws SQLException {
        Connection conn = cpool.getConnection();
        PreparedStatement pstmt=null;

        String getname = "SELECT o.billingway, o.totalfee FROM En_orders o WHERE o.orderid =?";
        String sqlstr = "insert into en_receivemoney(id,orderid,payer,payway,jing_ban_ren,nnumber,unit,describe,createdate) " +
                " values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ResultSet rs=null;
        int payway = 0;
        float totalfee = 0;
        String describe = "";
        String unit = "人民币";

        Timestamp thisdate = new Timestamp(System.currentTimeMillis());

        try {
            pstmt = conn.prepareStatement(getname);
            pstmt.setLong(1, orderid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                payway = rs.getInt("billingway");
                totalfee = rs.getFloat("totalfee");
            }
            rs.close();
            pstmt.close();

            Order order = new Order();
            int rid = 0;
            try {
                order = orderPeer.getInstance().getAOrder(orderid);
                rid = this.getMaxReceiveMoneyID();
            } catch (Exception e) {
                e.printStackTrace();
            }

            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, rid);
            pstmt.setLong(2, orderid);
            pstmt.setString(3, order.getName());
            pstmt.setInt(4, payway);
            pstmt.setString(5, jingbanren);
            pstmt.setFloat(6, totalfee);
            pstmt.setString(7, unit);
            pstmt.setString(8, describe);
            pstmt.setTimestamp(9, thisdate);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
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

    private int getMaxReceiveMoneyID() {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int maxid = 0;
        ResultSet rs=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select max(id) as id from en_receivemoney");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                maxid = rs.getInt(1) + 1;
            }
            rs.close();
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
        return maxid;
    }

    List getDetailList(int orderid, Connection conn, PreparedStatement pstmt) throws SQLException {
        List list = new ArrayList();
        Order order=null;
        String getarticle = "SELECT productID, ordernum FROM En_orders_detail " +
                " WHERE (OrderID = ?)";
        ResultSet rs=null;
        try {
            pstmt = conn.prepareStatement(getarticle);
            pstmt.setInt(1, orderid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                order = load_DetailNum(rs);
                list.add(order);
            }
            rs.close();
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
        return list;
    }

    Order load_DetailNum(ResultSet rs) throws SQLException {
        Order order = new Order();
        try {
            order.setProductid(rs.getInt("productid"));
            order.setOrderNum(rs.getInt("ordernum"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public void addStockNum(int orderid, int flag, Connection conn, PreparedStatement pstmt) throws SQLException {
        String sqlstr = "UPDATE tbl_article SET stocknum = stocknum + ? " +
                " WHERE id = ?";
        List list;
        Order order;
        int num;
        int articleid;
        list = getDetailList(orderid, conn, pstmt);
        try {
            for (int i = 0; i < list.size(); i++) {
                order = (Order) list.get(i);
                num = order.getOrderNum();
                articleid = order.getProductid();
                if (flag == 0) {
                    num = -num;
                }
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setInt(1, num);
                pstmt.setInt(2, articleid);
                pstmt.executeUpdate();
                pstmt.close();
            }
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

    public boolean checkAOrder(long orderid) throws OrderException {
        String sqlstr = "select orderid from tbl_orders where orderid = ?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        boolean existflag = false;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setLong(1, orderid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                existflag = true;
            }
            rs.close();
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
        return existflag;
    }

    public void updateStatus(long orderid, int status, String jingbanren) throws OrderException {
        String sqlstr = "update tbl_orders set status = ? where orderid = ?";
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setInt(1, status);
                pstmt.setLong(2, orderid);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();

                if (status == 4) {
                    List list = new ArrayList();
                    list = orderPeer.getInstance().getDetailList(orderid);
                    System.out.println("" + list.size());
                    for (int i = 0; i < list.size(); i++) {
                        Order order = (Order) list.get(i);
                        pstmt = conn.prepareStatement("update tbl_article set salesnum=" + order.getOrderNum() + "+(select salesnum from tbl_article where id=" + order.getProductid() + ") where id=" + order.getProductid());
                        pstmt.executeUpdate();
                        pstmt.close();
                        conn.commit();
                    }
                }
            } catch (NullPointerException e) {
                conn.rollback();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getUserOrderNums(int userid) throws OrderException {
        System.out.println("userid=" + userid);
        int nums = 0;
        String sqlstr = "select count(orderid) from tbl_orders where userid=?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                nums = rs.getInt(1);
            }
            rs.close();
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
        return nums;
    }

    public String getSendwayname(int sendway) throws OrderException {
        String sendwayname = "";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select name from tol_fee where id = ?");
            pstmt.setInt(1, sendway);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sendwayname = rs.getString(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sendwayname;
    }

    public List getNouse(int startindex, int range, String loginuserid) throws OrderException {
        List list = new ArrayList();
        Order order = new Order();
        Timestamp searchdate = null;
        String GETNOUSE = "";
        if (loginuserid.equals("xiaoli")) {
            searchdate = new Timestamp(System.currentTimeMillis() - 0x9a7ec800L);
            GETNOUSE = "select * from tbl_orders where nouse = 0 and createdate >= '" + searchdate + "' order by createdate desc";
        } else {
            GETNOUSE = "select * from tbl_orders where nouse = 0 order by createdate desc";
        }
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GETNOUSE);
            rs = pstmt.executeQuery();
            if (range != 0) {
                for (int i = 0; i < startindex; i++)
                    rs.next();

                for (int i = 0; i < range && rs.next(); i++) {
                    order = load(rs);
                    list.add(order);
                }

            } else {
                for (; rs.next(); list.add(order))
                    order = load(rs);

            }
            rs.close();
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

        return list;
    }

    private static String GETNOUSE = "select * from tbl_orders where nouse = 0 and siteid = ? order by createdate desc";

    public List getNouse(int startindex, int range, int siteid) throws OrderException {
        List list = new ArrayList();
        Order order = new Order();

        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GETNOUSE);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (range != 0) {
                for (int i = 0; i < startindex; i++) {
                    rs.next();
                }
                for (int i = 0; i < range && rs.next(); i++) {
                    order = load(rs);
                    list.add(order);
                }
            } else {
                while (rs.next()) {
                    order = load(rs);
                    list.add(order);
                }
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    //create fee info by feixiang 2009-03-17
    private static String CREATE_FFE = "insert into tbl_fee(id,siteid,cname,notes,fee) values(?,?,?,?,?)";

    public int createFee(Fee fee) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        int id = 0;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equals("oracle")) {
                id = sequenceMgr.getSequenceNum("Fee");
            } else {
                id = sequenceMgr.nextID("Fee");
            }
            pstmt = conn.prepareStatement(CREATE_FFE);
            pstmt.setInt(1, id);
            pstmt.setInt(2, fee.getSiteid());
            pstmt.setString(3, fee.getCname());
            pstmt.setString(4, fee.getNotes());
            pstmt.setFloat(5, fee.getFee());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //select a fee info by feixiang 2009-03-17
    private static String GET_A_FEE_INFO = "select * from tbl_fee where id = ?";

    public Fee getAFeeInfo(int id) {
        Fee fee = null;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_A_FEE_INFO);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                fee = loadFee(rs);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return fee;
    }

    //delete a fee info by feixiang 2009-03-17
    private static String DELETE_A_FEE_INFO = "delete from tbl_fee where id = ?";

    public int deleteAFeeInfo(int id) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(DELETE_A_FEE_INFO);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //select all fee info by feixiang 2009-03-17
    private static String GET_ALL_FEE_INFO = "select * from tbl_fee where siteid = ?";

    public List getAllFeeInfo(int siteid) {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_FEE_INFO);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Fee fee = loadFee(rs);
                list.add(fee);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    //update fee info by feixiang 2009-03-17
    private static String UPDATE_A_FEE_INFO = "update tbl_fee set cname = ?, notes = ?,fee = ? where id = ? ";

    public int updateAFeeInfo(Fee fee) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(UPDATE_A_FEE_INFO);
            pstmt.setString(1, fee.getCname());
            pstmt.setString(2, fee.getNotes());
            pstmt.setFloat(3, fee.getFee());
            pstmt.setInt(4, fee.getId());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //create sendway by feixiang 2009-03-17
    private static String CREATE_SENDWAY_INFO = "insert into tbl_sendway(id,siteid,cname,notes) values(?,?,?,?)";

    public int createSendWayInfo(SendWay send) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        int id = 0;
        ISequenceManager sMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equals("oracle")) {
                id = sMgr.getSequenceNum("SendWay");
            } else {
                id = sMgr.nextID("SendWay");
            }
            pstmt = conn.prepareStatement(CREATE_SENDWAY_INFO);
            pstmt.setInt(1, id);
            pstmt.setInt(2, send.getSiteid());
            pstmt.setString(3, send.getCname());
            pstmt.setString(4, send.getNotes());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //select a sendway info by feixiang 2009-03-17
    private static String GET_A_SENDWAY_INFO = "select * from tbl_sendway where id = ?";

    public SendWay getASendWayInfo(int id) {
        SendWay sendway = null;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_A_SENDWAY_INFO);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sendway = loadSendWay(rs);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return sendway;
    }

    //delete a sendway info by feixiang 2009-03-17
    private static String DELETE_A_SENDWAY_INFO = "delete from tbl_sendway where id = ?";

    public int deleteASendWayInfo(int id) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(DELETE_A_SENDWAY_INFO);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //select all sendway info by feixiang 2009-03-17
    private static String GET_ALL_SENDWAY_INFO = "select * from tbl_sendway where siteid = ?";

    public List getAllSendWayInfo(int siteid) {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_SENDWAY_INFO);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SendWay sendway = loadSendWay(rs);
                list.add(sendway);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    //update send way by feixiang 2009-03-17
    private static String UPDATE_SENDWAY = "update tbl_sendway set cname = ?, notes = ? where id = ?";

    public int updateASendWayInfo(SendWay sendway) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(UPDATE_SENDWAY);
            pstmt.setString(1, sendway.getCname());
            pstmt.setString(2, sendway.getNotes());
            pstmt.setInt(3, sendway.getId());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    private SendWay loadSendWay(ResultSet rs) throws SQLException {
        SendWay sendway = new SendWay();
        sendway.setId(rs.getInt("id"));
        sendway.setCname(rs.getString("cname"));
        sendway.setCreatedate(rs.getTimestamp("createdate"));
        sendway.setNotes(rs.getString("notes"));
        sendway.setSiteid(rs.getInt("siteid"));
        return sendway;
    }

    private Fee loadFee(ResultSet rs) {
        Fee fee = new Fee();
        try {
            fee.setCname(rs.getString("cname"));
            fee.setCreatedate(rs.getTimestamp("createdate"));
            fee.setFee(rs.getFloat("fee"));
            fee.setId(rs.getInt("id"));
            fee.setNotes(rs.getString("notes"));
            fee.setSiteid(rs.getInt("siteid"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fee;
    }

    //创建收货人信息 by feixiang  2009-03-17
    private static String CREATE_ADDRESS_INFO_ORACLE = "insert into tbl_addressinfo(userid,name,provinces,city,zone,zip,mobile,phone,address,id)" +
            " values(?,?,?,?,?,?,?,?,?,?)";

    private static String CREATE_ADDRESS_INFO_FOR_MSSQL = "insert into tbl_addressinfo(userid,name,provinces,city,zone,zip,mobile,phone,address)" +
            " values(?,?,?,?,?,?,?,?,?)";

    private static String CREATE_ADDRESS_INFO_FOR_MYSQL = "insert into tbl_addressinfo(userid,name,provinces,city,zone,zip,mobile,phone,address)" +
            " values(?,?,?,?,?,?,?,?,?)";

    public int createAddressInfo(AddressInfo address) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ISequenceManager sMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            /*System.out.println("realname=" + address.getName());
            System.out.println("userid=" + address.getUserid());
            System.out.println("Provinces=" + address.getProvinces());
            System.out.println("address=" + address);
            System.out.println("city=" + address.getCity());
            System.out.println("telephone=" + address.getPhone());
            System.out.println("mp=" + address.getMobile());
            System.out.println("zip=" + address.getZip());
            System.out.println("zone=" + address.getZone());
            */

            if (cpool.getType().equals("oracle"))
                pstmt = conn.prepareStatement(CREATE_ADDRESS_INFO_ORACLE);
            else if (cpool.getType().equals("mssql"))
                pstmt = conn.prepareStatement(CREATE_ADDRESS_INFO_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(CREATE_ADDRESS_INFO_FOR_MYSQL);
            pstmt.setInt(1, address.getUserid());
            pstmt.setString(2, address.getName());
            pstmt.setString(3, address.getProvinces());
            pstmt.setString(4, address.getCity());
            pstmt.setString(5, address.getZone());
            pstmt.setString(6, address.getZip());
            pstmt.setString(7, address.getMobile());
            pstmt.setString(8, address.getPhone());
            pstmt.setString(9, address.getAddress());
            if (cpool.getType().equals("oracle"))
                pstmt.setInt(10, sMgr.getSequenceNum("AddressInfo"));
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = -1;
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
        return code;
    }

    //select a address info by feixiang 2009-03-18
    private static String GET_A_ADDRESS_INFO_FOR_USER = "select * from tbl_addressinfo where orderid = ?";
    public AddressInfo getAAddresInfo(int id) {
        AddressInfo address = null;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_A_ADDRESS_INFO_FOR_USER);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                address = loadAddressInfo(rs);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return address;
    }

    private static String GET_A_ADDRESS_INFO_FOR_ORDER = "select * from tbl_addressinfofororder where orderid = ?";

    public AddressInfo getAAddresInfoForOrder(long orderid) {
        AddressInfo address = null;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_A_ADDRESS_INFO_FOR_ORDER);
            pstmt.setLong(1, orderid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                address = loadAddressInfo(rs);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return address;
    }

    private AddressInfo loadAddressInfo(ResultSet rs) throws SQLException {
        AddressInfo address = new AddressInfo();
        address.setAddress(rs.getString("address"));
        address.setCity(rs.getString("city"));
        address.setCreatedate(rs.getTimestamp("createdate"));
        address.setId(rs.getInt("id"));
        address.setMobile(rs.getString("mobile"));
        address.setName(rs.getString("name"));
        address.setPhone(rs.getString("phone"));
        address.setProvinces(rs.getString("provinces"));
        address.setZip(rs.getString("zip"));
        address.setZone(rs.getString("zone"));
        address.setNotes(rs.getString("notes"));
        address.setEmail(rs.getString("email"));
        return address;
    }

    //update a addreess info by feixiang 2009-03-18
    private static String UPDATE_ADDRESS_INFO = "update tbl_addressinfo set name = ?,provinces = ?,city = ?,zone = ?," +
            "zip = ?,mobile = ?,phone = ?,address = ? where id = ?";

    public int updateAddressinfo(AddressInfo address) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(UPDATE_ADDRESS_INFO);
            pstmt.setString(1, address.getName());
            pstmt.setString(2, address.getProvinces());
            pstmt.setString(3, address.getCity());
            pstmt.setString(4, address.getZone());
            pstmt.setString(5, address.getZip());
            pstmt.setString(6, address.getMobile());
            pstmt.setString(7, address.getPhone());
            pstmt.setString(8, address.getAddress());
            pstmt.setInt(9, address.getId());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //delete a address info by feixiang 2009-03-18
    private static String DELETE_ADDRESS_INFO = "delete from tbl_addressinfo where id = ?";

    public int deleteAddressInfo(int id) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(DELETE_ADDRESS_INFO);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //获得用户的最新的3个收货人的地址信息 by feixiang 2009-03-18
    private static String GET_THREE_ADDRESS_INFO = "select * from tbl_addressinfo where userid = ? order by id desc";

    public List getAllAddressInfo(int userid) {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_THREE_ADDRESS_INFO);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                AddressInfo address = loadAddressInfo(rs);
                list.add(address);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    //删除订单
    public int deleteOrder(int siteid, long orderid) throws OrderException {
        int flag = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_orders where siteid=? and orderid=?");
            pstmt.setInt(1, siteid);
            pstmt.setLong(2, orderid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            flag = 1;
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
        return flag;
    }

    //删除   修改订单delflag标识  1--删除状态  0--未删除
    public int delOrder(int siteid, long orderid) throws OrderException {
        int flag = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_orders set delflag = 1 where siteid=? and orderid=?");
            pstmt.setInt(1, siteid);
            pstmt.setLong(2, orderid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            flag = 1;
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
        return flag;
    }

    //create order info by feixiang 2009-03-19
    private static String CREATE_ORDER_INFO = "insert into tbl_orders(ORDERID,initorderid,USERID,NAME,ADDRESS,POSTCODE,PHONE,TOTALFEE,DELIVERYFEE" +
            ",PAYFEE,CREATEDATE,SITEID,STATUS,CITY,NOUSE,PROVINCE,zone,sendway,payway,usescores,orderscores,linktime,usecard) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static String CREATE_ORDER_DETAIL_INFO = "insert into TBL_ORDERS_DETAIL(ID,ORDERID,supplierid,suppliername,PRODUCTID,ORDERNUM,SALEPRICE" +
            ",CREATEDATE,productname,cardid) values(?,?,?,?,?,?,?,?,?,?)";
    //create invoice info add by feixiang 2010-12-22
    private static String CREATE_INVOICE_INFO_FOR_ORACLE = "insert into tbl_invoiceinfo(siteid,userid,orderid,invoicetype,title" +
            ",companyname,content,identification,registeraddress,phone,bankname,bankaccount,id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static String CREATE_INVOICE_INFO_FOR_MSSQL = "insert into tbl_invoiceinfo(siteid,userid,orderid,invoicetype,title" +
            ",companyname,content,identification,registeraddress,phone,bankname,bankaccount) values(?,?,?,?,?,?,?,?,?,?,?,?)";
    private static String CREATE_INVOICE_INFO_FOR_MYSQL = "insert into tbl_invoiceinfo(siteid,userid,orderid,invoicetype,title" +
            ",companyname,content,identification,registeraddress,phone,bankname,bankaccount) values(?,?,?,?,?,?,?,?,?,?,?,?)";

    public int createOrderInfo(Order order, List order_detail, Invoice invoice) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ISequenceManager sMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            //创建定单信息
            pstmt = conn.prepareStatement(CREATE_ORDER_INFO);
            pstmt.setLong(1, order.getOrderid());
            pstmt.setLong(2, 0);                                  //拆单的原始订单号，对于原始订单该项内容为0
            pstmt.setInt(3, order.getUserid());
            pstmt.setString(4, order.getName());
            pstmt.setString(5, order.getAddress());
            pstmt.setString(6, order.getPostcode());
            pstmt.setString(7, order.getPhone());
            pstmt.setFloat(8, order.getTotalfee());
            pstmt.setFloat(9, order.getDeliveryfee());
            pstmt.setFloat(10, order.getPayfee());
            pstmt.setTimestamp(11, order.getCreateDate());
            pstmt.setInt(12, order.getSiteid());
            pstmt.setInt(13, 1);
            pstmt.setString(14, order.getCity());
            pstmt.setInt(15, 1);
            pstmt.setString(16, order.getProvince());
            pstmt.setString(17, order.getZone());
            pstmt.setInt(18, order.getSendWay());
            pstmt.setInt(19, order.getPayWay());
            pstmt.setInt(20, order.getUserscores());
            pstmt.setInt(21, order.getOrderscores());
            pstmt.setString(22,order.getLinktime());
            pstmt.setInt(23,order.getUsecard());
            pstmt.executeUpdate();
            pstmt.close();

            //创建定单详细信息
            for (int i = 0; i < order_detail.size(); i++) {
                Order orderdetail = (Order) order_detail.get(i);
                int id = 0;
                if (cpool.getType().equals("oracle")) {
                    id = sMgr.getSequenceNum("OrderDetail");
                } else {
                    id = sMgr.nextID("OrderDetail");
                }
                pstmt = conn.prepareStatement(CREATE_ORDER_DETAIL_INFO);
                pstmt.setInt(1, id);
                pstmt.setLong(2, orderdetail.getOrderid());
                pstmt.setInt(3, orderdetail.getSupplierid());                             //供应商站点ID
                pstmt.setString(4, orderdetail.getSuppliername());                         //供应商名称
                pstmt.setInt(5, orderdetail.getProductid());
                pstmt.setInt(6, orderdetail.getOrderNum());
                pstmt.setFloat(7, orderdetail.getSaleprice());
                pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(9, orderdetail.getProductname());
                pstmt.setInt(10, orderdetail.getCardid());
                pstmt.executeUpdate();
                pstmt.close();
            }
            //创建发票信息
            if (invoice != null) {
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(CREATE_INVOICE_INFO_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(CREATE_INVOICE_INFO_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(CREATE_INVOICE_INFO_FOR_MYSQL);

                pstmt.setInt(1, invoice.getSiteid());
                pstmt.setInt(2, invoice.getUserid());
                pstmt.setLong(3, invoice.getOrderid());
                pstmt.setInt(4, invoice.getInvoicetype());
                pstmt.setInt(5, invoice.getTitle());
                pstmt.setString(6, invoice.getCompanyname());
                pstmt.setInt(7, invoice.getContent());
                pstmt.setString(8, invoice.getIdentification());
                pstmt.setString(9, invoice.getRegisteraddress());
                pstmt.setString(10, invoice.getPhone());
                pstmt.setString(11, invoice.getBankname());
                pstmt.setString(12, invoice.getBankaccount());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(13, sMgr.getSequenceNum("InvoiceInfo"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }
                pstmt.close();
            }
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    private static String CHECK_ORDERID = "select count(*) as num from tbl_orders where orderid = ?";
    public boolean checkOrderID(long orderID) throws OrderException {
        Connection conn = null;
        PreparedStatement pstmt= null;
        ResultSet rs = null;
        boolean existFlag = false;
        int count = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(CHECK_ORDERID);
            pstmt.setLong(1,orderID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("num");
                System.out.println("count:" + count);
            }
            if (count > 0) {
                existFlag = true;
            }
            rs.close();
            pstmt.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        return existFlag;
    }

    //分拆订单到不同的商品供应商
    private static String GET_ORDERDETAILINFO_BY_SUPPLIER = "select * from tbl_orders_detail where supplierid=? and orderid=?";

    public int createNewOrderBySupplier(int supplierid, Order order) throws OrderException {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ISequenceManager sMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            //创建定单信息
            if (order.getSiteid() != supplierid) {                                     //商品供应商ID与本站点的ID不相同
                pstmt = conn.prepareStatement(CREATE_ORDER_INFO);
                pstmt.setLong(1, sMgr.generateOrderID());                              //生成新的订单ID
                pstmt.setLong(2, order.getOrderid());                                  //拆单的原始订单号，对于原始订单该项内容为0
                pstmt.setInt(3, order.getUserid());
                pstmt.setString(4, order.getName());
                pstmt.setString(5, order.getAddress());
                pstmt.setString(6, order.getPostcode());
                pstmt.setString(7, order.getPhone());
                pstmt.setFloat(8, order.getTotalfee());
                pstmt.setFloat(9, order.getDeliveryfee());
                pstmt.setFloat(10, order.getPayfee());
                pstmt.setTimestamp(11, order.getCreateDate());
                pstmt.setInt(12, supplierid);                                            //供应商的站点ID
                pstmt.setInt(13, 1);
                pstmt.setString(14, order.getCity());
                pstmt.setInt(15, 1);
                pstmt.setString(16, order.getProvince());
                pstmt.setString(17, order.getZone());
                pstmt.setInt(18, order.getSendWay());
                pstmt.setInt(19, order.getPayWay());
                pstmt.setInt(20, order.getUserscores());
                pstmt.setInt(21, order.getOrderscores());
                pstmt.executeUpdate();
                pstmt.close();

                //创建定单详细信息
                /*for (int i = 0; i < order_detail.size(); i++) {
                    Order orderdetail = (Order) order_detail.get(i);
                    int id = 0;
                    if (cpool.getType().equals("oracle")) {
                        id = sMgr.getSequenceNum("OrderDetail");
                    } else {
                        id = sMgr.nextID("OrderDetail");
                    }
                    pstmt = conn.prepareStatement(CREATE_ORDER_DETAIL_INFO);
                    pstmt.setInt(1, id);
                    pstmt.setLong(2, orderdetail.getOrderid());
                    pstmt.setInt(3,orderdetail.getSupplierid());                             //供应商站点ID
                    pstmt.setString(4,orderdetail.getSuppliername());                         //供应商名称
                    pstmt.setInt(5, orderdetail.getProductid());
                    pstmt.setInt(6, orderdetail.getOrderNum());
                    pstmt.setFloat(7, orderdetail.getSaleprice());
                    pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                    pstmt.setString(9, orderdetail.getProductname());
                    pstmt.setInt(10,orderdetail.getCardid());
                    pstmt.executeUpdate();
                    pstmt.close();
                }*/
            }
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //获得某用户的定单 by feixiang 2009-03-22
    public List getUserOrderList(int userid) {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_orders where userid = ?");
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = load(rs);
                list.add(order);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    //积分兑换规则 add by feixiang 2009-06-23
    public int createScoresRule(int siteid, int scores) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_siteinfo set scores = ? where siteid = ?");
            pstmt.setInt(1, scores);
            pstmt.setInt(2, siteid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //get site score rule add by feixiang 2009-06-23
    public int getScoresRuleForSite(int siteid) {
        int scores = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select scores from tbl_siteinfo where siteid = ?");
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                scores = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return scores;
    }

    //根据在线支付的结果修改订单信息
    //String r2TrxId;                  //支付中心返回流水号
    //String zfmemberid;              //支付中心会员ID
    //int r2type;                     //支付中心返回类型，1浏览器重定向 2服务器点对点
    //String payresult;               //支付结果，成功/失败/其他
    //Timestamp paydate;              //订单支付时间
    public int updateOrderinfoByZhifuResult(Order order)  throws OrderException {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_orders set jylsh=?,zfmemberid=?,r2type=?,payresult=?,paydate=?,payflag=?,payway=?,status=? where orderid = ?");
            pstmt.setString(1, order.getR2TrxId());
            pstmt.setString(2, order.getZfmemberid());
            pstmt.setString(3,order.getR2type());
            pstmt.setString(4,order.getPayresult());
            pstmt.setTimestamp(5,order.getPaydate());
            pstmt.setInt(6,order.getPayflag());
            pstmt.setInt(7,order.getPayWay());
            pstmt.setInt(8,order.getStatus());
            pstmt.setLong(9,order.getOrderid());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = -1;
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
        return code;
    }

    public String getProductInfoForBBN(long orderid,int selid)  throws OrderException {
        String result = null;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt=null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("select id,username,passwd from tbl_bbn_product where id=?");
            pstmt.setInt(1, selid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getString("username") + "-" + rs.getString("passwd");
            }
            rs.close();
            pstmt.close();

            if (selid > 0) {
                //将选择的产品保存到订单明细表中
                pstmt = conn.prepareStatement("update TBL_ORDERS_DETAIL set productname = concat(productname,'" + result + "') where orderid=?");
                pstmt.setLong(1,orderid);
                pstmt.executeUpdate();
                pstmt.close();
            }
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
        return result;
    }

    public int getProductIDInfoForBBN(String mphone,int cardtype)  throws OrderException {
        int selid = 0;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt=null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("select id,username,passwd from tbl_bbn_product where flag=0 and cardtype=? and rownum = 1");
            pstmt.setInt(1, cardtype);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                selid = rs.getInt("id");
            }
            rs.close();
            pstmt.close();

            //如果选择到了一个商品，则将该商品锁定，不能被其他用户买走
            if (selid > 0) {
                pstmt = conn.prepareStatement("update tbl_bbn_product set flag=2,buyer=?,outdate=? where id=?");
                pstmt.setString(1,mphone);
                pstmt.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(3,selid);
                pstmt.executeUpdate();
                pstmt.close();
            }
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
        return selid;
    }

    public int setProductSucessSaleoutStatusForBBN(long orderid,String username,String password,int cardtype)  throws OrderException {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            //修改产品的状态为已经售出
            pstmt = conn.prepareStatement("update tbl_bbn_product set flag=1 where username=? and passwd=? and cardtype=?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3,cardtype);
            pstmt.executeUpdate();
            pstmt.close();

            //产品数据库tbl_article库存数量减少1
            pstmt = conn.prepareStatement("update tbl_article set stocknum=stocknum-1 where id=?");
            pstmt.setInt(1,cardtype);
            pstmt.executeUpdate();
            pstmt.close();

            //修改订单状态为已经完成发货
            pstmt = conn.prepareStatement("update tbl_orders set status=4 where orderid=?");
            pstmt.setLong(1,orderid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            result = -1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return result;
    }

    public int updateProductFlagForBBN(int selid) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_bbn_product set flag=0 where id = ?");
            pstmt.setInt(1,selid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = -1;
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
        return code;
    }

    //更新用户积分 by feixiang 2009-06-24
    public int updateUserScores(int userid, int scores) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_userinfo set scores = scores + ? where id = ?");
            pstmt.setInt(1, scores);
            pstmt.setInt(2, userid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //查询离线购物卷 by feixiang 2009-06-25
    public List getCardForOutLine(int start, int range, String sql) throws OrderException {
        List list = new ArrayList();
        Card card = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            for (int i = 0; i < start; i++) {
                rs.next();
            }
            for (int i = 0; i < range && rs.next(); i++) {
                card = new Card();
                card.setId(rs.getInt("id"));
                card.setCardnum(rs.getString("cardnum"));
                card.setDenomination(rs.getInt("denomination"));
                card.setBegintime(rs.getString("begintime"));
                card.setEndtime(rs.getString("endtime"));
                card.setCode(rs.getString("code"));
                card.setCreatetime(rs.getDate("createtime"));
                card.setIscheck(rs.getInt("ischeck"));
                list.add(card);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public int getCardNum(String sql) throws OrderException {
        int num = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                num = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return num;
    }

    //被获取的购物券数量
    public int getIsCheckCardNum(int siteid) throws OrderException {
        int num = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(*) from tbl_shoppingcard where ischeck = 1 and siteid = ?");
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                num = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return num;
    }

    //被激活的购物券的数量
    public int getActivationCardNum(int siteid) throws OrderException {
        int num = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(*) from tbl_shoppingcard where activation = 1 and siteid = ?");
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                num = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return num;
    }

    //创建购物券 by feixiang 2009-06-25
    public void createCard(Card card) throws OrderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        int id = 0;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement("insert into tbl_shoppingcard(siteid,cardnum,denomination,begintime,endtime,code,createtime,activation,ischeck,id) " +
                        "values(?,?,?,?,?,?,?,?,?,?)");
            else if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement("insert into tbl_shoppingcard(siteid,cardnum,denomination,begintime,endtime,code,createtime,activation,ischeck) " +
                        "values(?,?,?,?,?,?,?,?,?)");
            else
                pstmt = conn.prepareStatement("insert into tbl_shoppingcard(siteid,cardnum,denomination,begintime,endtime,code,createtime,activation,ischeck) " +
                        "values(?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, card.getSiteid());
            pstmt.setString(2, card.getCardnum());
            pstmt.setInt(3, card.getDenomination());
            pstmt.setString(4, card.getBegintime());
            pstmt.setString(5, card.getEndtime());
            pstmt.setString(6, card.getCode());
            pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(8, 0);
            pstmt.setInt(9, 0);
            if (cpool.getType().equals("oracle")) {
                pstmt.setInt(10, sequenceMgr.getSequenceNum("ShoppingCard"));
                pstmt.executeUpdate();
                pstmt.close();
            } else if (cpool.getType().equals("mssql")) {
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                pstmt.executeUpdate();
                pstmt.close();
            }
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
    }

    //自动创建购物卷 by feixiang 2009-06-25
    public void autoCreateCard(int siteid, String begintime, String endtime, int denominations, int number) throws OrderException {
        DocumentCreateCard autoCreateCard = new DocumentCreateCard(cpool, begintime, endtime, denominations, number, siteid);
        autoCreateCard.start();
    }

    //本期购物券结束时删除所有购物券
    public int deledAllCards(int siteid) throws OrderException {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_shoppingcard where siteid = ?");
            pstmt.setInt(1, siteid);
            pstmt.executeUpdate();
            pstmt.close();
            /*pstmt = conn.prepareStatement("delete from en_checkcard");
            pstmt.executeUpdate();
            pstmt.close();*/
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //删除某张购物券
    public int deleteACard(int id) throws OrderException {
        int flag = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_shoppingcard where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            flag = 1;
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
        return flag;
    }

    //获得一张购物券的信息 add by feixiang 2009-06-26
    public Card getACardInfo(int id) {
        Card card = null;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_shoppingcard where id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                card = new Card();
                card.setId(rs.getInt("id"));
                card.setCardnum(rs.getString("cardnum"));
                card.setDenomination(rs.getInt("denomination"));
                card.setBegintime(rs.getString("begintime"));
                card.setEndtime(rs.getString("endtime"));
                card.setCode(rs.getString("code"));
                card.setCreatetime(rs.getDate("createtime"));
            }
            rs.next();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return card;
    }
    public Card getACardInfo(String cardnum) {
        Card card = null;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_shoppingcard where cardnum = ?");
            pstmt.setString(1, cardnum);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                card = new Card();
                card.setId(rs.getInt("id"));
                card.setCardnum(rs.getString("cardnum"));
                card.setDenomination(rs.getInt("denomination"));
                card.setBegintime(rs.getString("begintime"));
                card.setEndtime(rs.getString("endtime"));
                card.setCode(rs.getString("code"));
                card.setCreatetime(rs.getDate("createtime"));
            }
            rs.next();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return card;
    }
    //判断购物券是否可用 add by feixiang 2009-06-26
    public int checkCard(int siteid, String cardnum, String code, int voucher) {
        int flag = -1;
        int voucher1 = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int id = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_shoppingcard where siteid = ? and cardnum = ? and code = ? and activation = 0 " +
                    "and endtime >= '" + (new Timestamp(System.currentTimeMillis())).toString().substring(0, 10) + "' " +
                    "and begintime <= '" + (new Timestamp(System.currentTimeMillis())).toString().substring(0, 10) + "'");
            pstmt.setInt(1, siteid);
            pstmt.setString(2, cardnum);
            pstmt.setString(3, code);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                flag = 0;
                voucher1 = rs.getInt("denomination");
                id = rs.getInt("id");
            }
            rs.close();
            pstmt.close();

            if (voucher1 != 0 && voucher1 <= voucher) {  //购物券面额小于等于商品允许使用的购物券面额时才可使用
                flag = id;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return flag;
    }

    //将购物券更新为已经使用状态 add by feixiang 2009-06-26
    public int updateCardUsed(int id) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_shoppingcard set activation = 1 where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //更新购物券的发放状态
    public int updateCardIscheck(int id, int ischeck) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_shoppingcard set ischeck = ? where id = ?");
            pstmt.setInt(1, ischeck);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }


    //数字表示
    private String NumberMoney;

    //整数部分允许的最大位数
    private int LongPartMaxLength = 13;

    //字典数据(数字、汉字对照表)
    private Hashtable tab = new Hashtable();

    //无参构造函数
    public orderPeer() {
    }

    //带参构造函数
    public void orderPeer(String NumberMoney) {
        this.NumberMoney = NumberMoney;

        tab.put("0", new String("零"));
        tab.put("零", new String("0"));
        tab.put("1", new String("壹"));
        tab.put("壹", new String("1"));
        tab.put("2", new String("贰"));
        tab.put("贰", new String("2"));
        tab.put("3", new String("叁"));
        tab.put("叁", new String("3"));
        tab.put("4", new String("肆"));
        tab.put("肆", new String("4"));
        tab.put("5", new String("伍"));
        tab.put("伍", new String("5"));
        tab.put("6", new String("陆"));
        tab.put("陆", new String("6"));
        tab.put("7", new String("柒"));
        tab.put("柒", new String("7"));
        tab.put("8", new String("捌"));
        tab.put("捌", new String("8"));
        tab.put("9", new String("玖"));
        tab.put("玖", new String("9"));
        tab.put("10", new String("拾"));
        tab.put("拾", new String("10"));
        tab.put("100", new String("佰"));
        tab.put("佰", new String("100"));
        tab.put("1000", new String("仟"));
        tab.put("仟", new String("1000"));
        tab.put("10000", new String("万"));
        tab.put("万", new String("10000"));
        tab.put("100000", new String("拾"));   //tab.put("拾",new String("100000"));
        tab.put("1000000", new String("佰"));  //tab.put("佰",new String("1000000"));
        tab.put("10000000", new String("仟"));  //tab.put("仟",new String("10000000"));
        tab.put("100000000", new String("亿"));
        tab.put("亿", new String("100000000"));
        tab.put("1000000000", new String("拾"));  //tab.put("拾",new String("1000000000"));
        tab.put("10000000000", new String("佰")); //tab.put("佰",new String("10000000000"));
        tab.put("100000000000", new String("仟")); //tab.put("仟",new String("100000000000"));
        tab.put("1000000000000", new String("万")); //tab.put("万",new String("1000000000000"));
        tab.put("10000000000000", new String("拾")); //tab.put("拾",new String("10000000000000"));
    }

    //将数字表示的钱转化为汉字表示形式
    public String ToChinese() {
        String result = "";
        String strNum = this.NumberMoney;

        if (strNum.indexOf(".") < 0 || strNum.substring(strNum.indexOf(".") + 1, strNum.indexOf(".") + 2).equals("0")) { //只有整数时
            if (strNum.indexOf(".") > 0) {
                return this.ToChineseLongPart(Long.parseLong(strNum.substring(0, strNum.indexOf(".")))) + "元整";
            } else {
                return this.ToChineseLongPart(Long.parseLong(strNum)) + "元整";
            }
        }

        String s1 = strNum.substring(0, strNum.indexOf("."));
        String s2 = strNum.substring(strNum.indexOf(".") + 1);

        long lngPart = Long.parseLong(strNum.substring(0, strNum.indexOf(".")));
        String fltPart = strNum.substring(strNum.indexOf(".") + 1);

        if (lngPart == 0)  //若整数为0,则只显示小数,否则都显示
            result = this.ToChineseDecimalPart(fltPart);
        else
            result = this.ToChineseLongPart(lngPart) + "元" + this.ToChineseDecimalPart(fltPart);

        return result;
    }

    //将数字表示的钱转化为汉字表示形式
    public String ToChinese(String numberMoney) {
        this.NumberMoney = numberMoney;
        return this.ToChinese();
    }

    //整数部分数字==>汉字
    private String ToChineseLongPart(long longPart) {
        String result = ""; //返回值

        if (longPart == 0)
            return (String) tab.get(Long.toString(longPart));

        String strPart = Long.toString(longPart); //转化为字符串处理
        if (strPart.length() > this.LongPartMaxLength)
            return "整数部分只支持" + this.LongPartMaxLength + "位!";
        long base = 1; //基数
        char bit;  //每位的数值

        for (int i = strPart.length() - 1; i >= 0; i--, base *= 10) {
            bit = strPart.charAt(i);

            if (bit == '0') //遇到0的处理
            {
                String strZero = "", strStep = "";

                if (base != 1) //不是个位(中间?则需加"零"
                    strZero = (String) tab.get(Long.toString(0L));
                else
                    strZero = "";
                for (; bit == '0'; i--, base *= 10, bit = strPart.charAt(i))//（跳过连续的0）
                {
                    if ((base == 10000L || base == 100000000L || base == 1000000000000L))
                        strStep = (String) tab.get(Long.toString(base));
                }

                i++;
                base /= 10; //向后退一位,
                result = strStep + strZero + result;
            } else {
                if (i != strPart.length() - 1)
                    result = (String) tab.get(Character.toString(bit)) + (String) tab.get(Long.toString(base)) + result;
                else
                    result = (String) tab.get(Character.toString(bit)) + result;
            }
        }

        return result;
    }

    //小数部分数字==>汉字
    private String ToChineseDecimalPart(String floatPart) {
        String result = "";
        //String fltPart = Long.toString(floatPart);
        String fltPart = floatPart;
        if (floatPart.length() == 0) //小数为0
            return result;

        char bit;

        if ((bit = fltPart.charAt(0)) != '0')
//   result += (String)tab.get(Character.toString(bit));
//  else
            result += (String) tab.get(Character.toString(bit)) + "角";

        /*if((bit = fltPart.charAt(1)) != '0')
 //   result += (String)tab.get(Character.toString(bit));
 //  else
    result += (String)tab.get(Character.toString(bit)) + "分";*/

        return result;
    }

    //  添加InterfaceParam
    public int addInterfaceParam(InterfaceParam iparam) {
        int rel = iparam.getPaywayId();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equalsIgnoreCase("oracle")) {
                pstmt = conn.prepareStatement("insert into tbl_paywayinterface(paywayid,accountnumber,paywaykey,alipayaccount,createdate,id) values(?,?,?,?,?,?)");
            } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                pstmt = conn.prepareStatement("insert into tbl_paywayinterface(paywayid,accountnumber,paywaykey,alipayaccount,createdate) values(?,?,?,?,?)");
            } else {
                pstmt = conn.prepareStatement("insert into tbl_paywayinterface(paywayid,accountnumber,paywaykey,alipayaccount,createdate) values(?,?,?,?,?)");
            }
            pstmt.setInt(1, iparam.getPaywayId());
            pstmt.setString(2, iparam.getAccountNumber());
            pstmt.setString(3, iparam.getPaywayKey());
            pstmt.setString(4, iparam.getAlipayAccount());
            pstmt.setTimestamp(5, iparam.getCreateDate());
            if (cpool.getType().equalsIgnoreCase("oracle")) {
                pstmt.setInt(6, sequenceMgr.getSequenceNum("PayWayInterface"));
                pstmt.executeUpdate();
                pstmt.close();
            } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception ex) {
            rel = 0;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return rel;
    }

    //根据支付方式id获得InterfaceParam
    public InterfaceParam getInterfaceParam(int paywayid) {
        InterfaceParam iParam = null;
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            psmt = conn.prepareStatement("select * from tbl_paywayinterface where paywayid=?");
            psmt.setInt(1, paywayid);
            rs = psmt.executeQuery();
            while (rs.next()) {
                iParam = new InterfaceParam();
                iParam.setPaywayKey(rs.getString("paywaykey"));
                iParam.setAccountNumber(rs.getString("accountnumber"));
                iParam.setAlipayAccount(rs.getString("alipayaccount"));
                iParam.setPaywayId(rs.getInt("paywayid"));
                iParam.setPaywayKey(rs.getString("paywaykey"));
                iParam.setId(rs.getInt("id"));
            }

        } catch (Exception ex) {
            cpool.freeConnection(conn);
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return iParam;
    }

    //修改接口线参数
    public int updateInterfaceParam(InterfaceParam iParam) {
        int rel = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("update tbl_paywayinterface set  paywaykey=?,accountnumber=?,alipayaccount=? where paywayid=?");
            pstmt.setString(1, iParam.getPaywayKey());
            pstmt.setString(2, iParam.getAccountNumber());
            pstmt.setString(3, iParam.getAlipayAccount());
            pstmt.setInt(4, iParam.getPaywayId());
            rel = pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception ex) {
            rel = 0;
            cpool.freeConnection(conn);
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return rel;
    }

    //获得某用户的定单 by feixiang 2010-12-17
    public List getUserOrderList(int userid, int range, int startrow) {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_orders where userid = ? order by createdate desc");
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startrow; i++) {
                rs.next();
            }
            for (int i = 0; i < range && rs.next(); i++) {
                Order order = load(rs);
                list.add(order);
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public int getUserOrderNum(int userid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int num = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(orderid) from tbl_orders where userid = ?");
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                num = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return num;
    }

    public void cancelOrder(long orderid) throws OrderException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_orders set nouse = 0 where orderid = ? ");
            pstmt.setLong(1, orderid);
            pstmt.executeUpdate();

            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }

    // create invoice content add by feixiang 2010-12-22
    private static String CREATE_INVOICE_CONTENT_FOR_ORACLE = "insert into tbl_invoicecontent(siteid,content,id) values(?,?,?)";
    private static String CREATE_INVOICE_CONTENT_FOR_MSSQL = "insert into tbl_invoicecontent(siteid,content) values(?,?)";
    private static String CREATE_INVOICE_CONTENT_FOR_MYSQL = "insert into tbl_invoicecontent(siteid,content) values(?,?)";

    public int createInvoiceConent(Invoice invoice) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(CREATE_INVOICE_CONTENT_FOR_ORACLE);
            else if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(CREATE_INVOICE_CONTENT_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(CREATE_INVOICE_CONTENT_FOR_MYSQL);
            pstmt.setInt(1, invoice.getSiteid());
            pstmt.setString(2, invoice.getContentinfo());
            if (cpool.getType().equals("oracle")) {
                pstmt.setInt(3, sMgr.getSequenceNum("InvoiceContent"));
                pstmt.executeUpdate();
            } else if (cpool.getType().equals("mssql")) {
                pstmt.executeUpdate();
            } else {
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //get invoice content add by feixiang 2010-12-22
    public List getInvoiceContentList(int siteid) {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_invoicecontent where siteid = ? order by createdate desc");
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setContentinfo(rs.getString("content"));
                invoice.setCreatedate(rs.getTimestamp("createdate"));
                list.add(invoice);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    //update invoice content add by feixiang 2010-12-22
    public int updateInvoicContnet(Invoice invoice) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_invoicecontent set content = ? where id = ?");
            pstmt.setString(1, invoice.getContentinfo());
            pstmt.setInt(2, invoice.getId());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //delete invoice content by id add by feixiang 2010-12-22
    public int deleteInvoiceConenteById(int id) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_invoicecontent where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    //get invoice content by id add by feixiang 2010-12-22
    public Invoice getInvoiceConenteById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Invoice invoice = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_invoicecontent where id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setContentinfo(rs.getString("content"));
                invoice.setCreatedate(rs.getTimestamp("createdate"));
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return invoice;
    }

    //get invoice info for user add by feixiang 2010-12-22
    public Invoice getInvoiceInfoForUser(int userid) {
        Invoice invoice = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_invoiceinfo where userid = ? order by createdate asc");
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setCreatedate(rs.getTimestamp("createdate"));
                invoice.setBankaccount(rs.getString("bankaccount"));
                invoice.setBankname(rs.getString("bankname"));
                invoice.setCompanyname(rs.getString("companyname"));
                invoice.setContent(rs.getInt("content"));
                invoice.setIdentification(rs.getString("identification"));
                invoice.setInvoicetype(rs.getInt("invoicetype"));
                invoice.setOrderid(rs.getLong("orderid"));
                invoice.setPhone(rs.getString("phone"));
                invoice.setRegisteraddress(rs.getString("registeraddress"));
                invoice.setTitle(rs.getInt("title"));
                invoice.setUserid(rs.getInt("userid"));
                invoice.setSiteid(rs.getInt("siteid"));
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return invoice;
    }

    //get invoice info for order add by feixiang 2010-12-22
    public Invoice getInvoiceInfoForOrder(long orderid) {
        Invoice invoice = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select t.id,t.createdate,t.bankaccount,t.bankname,t.companyname,t1.content,t.identification,t.invoicetype,t.orderid," +
                    "t.phone,t.registeraddress,t.title,t.userid,t.siteid from tbl_invoiceinfofororder t,tbl_invoicecontentfororder t1 where t.id=t1.invoiceid and t.orderid = ?");
            pstmt.setLong(1, orderid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setCreatedate(rs.getTimestamp("createdate"));
                invoice.setBankaccount(rs.getString("bankaccount"));
                invoice.setBankname(rs.getString("bankname"));
                invoice.setCompanyname(rs.getString("companyname"));
                invoice.setContentinfo(rs.getString("content"));
                invoice.setIdentification(rs.getString("identification"));
                invoice.setInvoicetype(rs.getInt("invoicetype"));
                invoice.setOrderid(rs.getLong("orderid"));
                invoice.setPhone(rs.getString("phone"));
                //invoice.setEmail(rs.getString("email"));
                invoice.setRegisteraddress(rs.getString("registeraddress"));
                invoice.setTitle(rs.getInt("title"));
                invoice.setUserid(rs.getInt("userid"));
                invoice.setSiteid(rs.getInt("siteid"));
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return invoice;
    }
    //update order payflag
    public int updateOrderPayflag(long orderid,int payflag){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_orders set payflag = ? where orderid = ?");
            pstmt.setInt(1, payflag);
            pstmt.setLong(2,orderid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
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
        return code;
    }

    public int getCityId(String cityname) throws OrderException {
        int cityid = 0;
        String sqlstr = "select id from en_city where cityname=?";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setString(1, cityname);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cityid = rs.getInt("id");
            }
            rs.close();
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
        return cityid;
    }

    //get shoppingcar fee
    public float getShoppingFee(int sendway,int payway,int cityid,String productids,String nowdata,float totalfee,int siteid)//nowdate 格式yyyy-mm-dd
    {
        float fee = 0;

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            //获得送货方式运费
            float defaultfee = -1;
            pstmt = conn.prepareStatement("select * from tbl_fee where id = "+sendway);
            rs = pstmt.executeQuery();
            while(rs.next()){
                defaultfee = rs.getFloat("fee");
            }
            rs.close();
            pstmt.close();

            //获得城市运费
            List cityfee = new ArrayList();
            pstmt = conn.prepareStatement("select * from tbl_cityfee where siteid = "+siteid+" and cityid = "+cityid + " and begintime <='"+nowdata+"' and endtime >='"+nowdata+"'");
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                cityfee.add(String.valueOf(rs.getFloat("fee")));
            }
            rs.close();
            pstmt.close();

            //获得支付方式运费
            List paywayfee =  new ArrayList();
            pstmt = conn.prepareStatement("select * from tbl_paywayfee where siteid = "+siteid+" and paywayid = "+payway + " and begintime <='"+nowdata+"' and endtime >='"+nowdata+"'");
            rs = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getInt("totaltype") == 1 && rs.getFloat("totalfee") <= totalfee){
                    paywayfee.add("0");
                }else{
                    paywayfee.add(String.valueOf(rs.getFloat("fee")));
                }

            }
            rs.close();
            pstmt.close();

            //获得商品运费
            List productfee = new ArrayList();
            pstmt = conn.prepareStatement("select * from tbl_feeforproduct where siteid = "+siteid+" and pid in("+productids+")" + " and begintime <='"+nowdata+"' and endtime >='"+nowdata+"'");
            rs = pstmt.executeQuery();
            while(rs.next()){
                productfee.add(String.valueOf(rs.getFloat("fee")));
            }
            rs.close();
            pstmt.close();
            //获得购物运费 购物运费为以上4种运费中最小的运费
            if(defaultfee > -1){
                fee = defaultfee;
            }
            for(int i = 0;i < cityfee.size(); i ++){
                float f = Float.parseFloat((String)cityfee.get(i));
                if(f < fee){
                    fee = f;
                }
            }
            for(int i = 0;i < paywayfee.size(); i ++){
                float f = Float.parseFloat((String)paywayfee.get(i));
                if(f < fee){
                    fee = f;
                }
            }
            for(int i = 0;i < productfee.size(); i ++){
                float f = Float.parseFloat((String)productfee.get(i));
                if(f < fee){
                    fee = f;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return fee;
    }

    //报社
    public int createOrderList(Order order,List list,Uregister ure){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager seqMgr = SequencePeer.getInstance();
        int id = seqMgr.getSequenceNum("Orders");
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("insert into tbl_orders(orderid,name,telephone,phone,address,postcode,email,siteid,status,nouse,payfee,createdate) values(?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1,id);
            pstmt.setString(2,order.getName());
            pstmt.setString(3,order.getTelephone());
            pstmt.setString(4,order.getPhone());
            pstmt.setString(5,order.getAddress());
            pstmt.setString(6,order.getPostcode());
            pstmt.setString(7,order.getEmail());
            pstmt.setInt(8,order.getSiteid());
            pstmt.setInt(9,order.getStatus());
            pstmt.setInt(10,order.getNouse());
            pstmt.setFloat(11,order.getPayfee());
            pstmt.setTimestamp(12,new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            pstmt.close();

            if(list != null){
                for(int i = 0; i < list.size(); i++){
                    order = (Order)list.get(i);
                    pstmt = conn.prepareStatement("insert into tbl_orders_detail(id,orderid,suppliername,subscribe,numbercopy,riqi,xiangxi,saleprice,createdate) values(?,?,?,?,?,?,?,?,?)");
                    pstmt.setInt(1,seqMgr.getSequenceNum("OrderDetail"));
                    pstmt.setInt(2,id);
                    pstmt.setString(3,order.getName());
                    pstmt.setInt(4,order.getSubscribe());
                    pstmt.setInt(5,order.getNumbercopy());
                    pstmt.setString(6,order.getRiqi());
                    pstmt.setString(7,order.getXiangxi());
                    if(ure == null){
                        if(order.getSubscribe()==1){
                            pstmt.setFloat(8,order.getYprice());
                        }
                        if(order.getSubscribe()==3){
                            pstmt.setFloat(8,order.getQprice());
                        }
                        if(order.getSubscribe()==4){
                            pstmt.setFloat(8,order.getMprice());
                        }
                    }else{
                        if(order.getSubscribe()==1){
                            pstmt.setFloat(8,order.getVipy());
                        }
                        if(order.getSubscribe()==3){
                            pstmt.setFloat(8,order.getVipq());
                        }
                        if(order.getSubscribe()==4){
                            pstmt.setFloat(8,order.getVipm());
                        }
                    }
                    pstmt.setTimestamp(9,new Timestamp(System.currentTimeMillis()));
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            code = 1;
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return code;
    }


    public List<Order> getOrderListToExcel(String whereClause,String startday,String endday,int siteid,int comid,int deptid,int source,int orgtype){
        List list = new ArrayList();
        Order order=null;
        //if (sqlstr == null || "".equals(sqlstr)) {
        //    sqlstr = "select o.*,od.* from tbl_orders o,tbl_orders_detail od where  o.ORDERID = od.ORDERID order by o.createdate desc";
        //} else {
        //    sqlstr = sqlstr.replaceAll("@", "%");
        //}

        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            java.util.Date s_datetime = null;
            java.util.Date e_datetime = null;
            //设置查询开始日期，是最终支付日期
            if (startday!=null && startday!="") {
                s_datetime = format.parse(startday);
                if (cpool.getType().equalsIgnoreCase("oracle")){
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and o.paydate>=to_date('" + startday + "','yyyy-MM-dd hh:mm:ss')";
                    else
                        whereClause = whereClause + "o.paydate>=to_date('" + startday + "','yyyy-MM-dd hh:mm:ss')";
                } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and o.paydate>'" + startday + "'";
                    else
                        whereClause = whereClause + "o.paydate>'" + startday + "'";
                } else if (cpool.getType().equalsIgnoreCase("mysql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and o.paydate>='" + startday + "'";
                    else
                        whereClause = whereClause + "o.paydate>='" + startday + "'";
                }
            }
            //设置查询结束日期，是最终支付日期
            if (endday!=null && endday!="") {
                e_datetime = format.parse(endday);
                if (cpool.getType().equalsIgnoreCase("oracle")){
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and o.paydate<=to_date('" + endday + "','yyyy-MM-dd hh:mm:ss')";
                    else
                        whereClause = whereClause + "o.paydate<=to_date('" + endday + "','yyyy-MM-dd hh:mm:ss')";
                } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and o.paydate<'" + startday + "'";
                    else
                        whereClause = whereClause + "o.paydate<'" + startday + "'";
                } else if (cpool.getType().equalsIgnoreCase("mysql")) {
                    if (whereClause != null && whereClause != "")
                        whereClause = whereClause + " and o.paydate<='" + endday + "'";
                    else
                        whereClause = whereClause + "o.paydate<='" + endday + "'";
                }
            }

            //设置基于用户ID的查询条件
            IUserManager userManager = UserPeer.getInstance();
            List<User> users = null;
            String userids = "";
            if (orgtype == 1) {
                users = userManager.getUsersByCompanyid(siteid, comid);
            } else {            //orgtype=0有两种不同情况，一种是确实是部门数据管理员登录系统，另一种情况是网站管理员登录系统。
                if (comid > 0) users = userManager.getUsersByCompanyid(siteid, comid);
                if (deptid > 0) users = userManager.getUsersByDeptid(siteid, deptid);
            }

            //获取用户ID列表字符串
            if (users != null) {
                for (int ii = 0; ii < users.size(); ii++) {
                    User user = users.get(ii);
                    userids = userids + user.getUid() + ",";
                }
                if (userids != null && userids != "") {
                    if (userids.endsWith(",")) userids = userids.substring(0, userids.length() - 1);
                    if (whereClause != null && whereClause != "") {
                        userids = "o.userid in (" + userids + ")";
                        whereClause = whereClause + " and " + userids;
                    } else {
                        whereClause = whereClause + " " + userids;
                    }
                }
            }

            //设置订单来源查询条件
            if (source == 2) {                      //表示订单来源于网站，数据库表中flag=1表示订单来源于网站
                if (whereClause != null && whereClause != "") {
                    whereClause = whereClause + " and o.flag=1";
                } else {
                    whereClause = whereClause + "o.flag=1";
                }
            } else if (source == 3) {              //表示订单来源于微信公众号,数据库表中flag=0表示订单来源于公众号
                if (whereClause != null && whereClause != "") {
                    whereClause = whereClause + " and o.flag=0";
                } else {
                    whereClause = whereClause + "o.flag=0";
                }
            }

            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select o.*,od.* from tbl_orders o,tbl_orders_detail od  where o.ORDERID = od.ORDERID and o.siteid =? and o.nouse = 1 and " + whereClause + " order by o.createdate");
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                order = loadToExcel(rs);
                list.add(order);
            }

            rs.close();
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
        return list;
    }

    private Order loadToExcel(ResultSet rs) throws OrderException {
        Order order = new Order();
        try {
            order.setOrderid(rs.getLong("orderid"));
            order.setUserid(rs.getInt("userid"));
            order.setName(rs.getString("name"));
            order.setSex(rs.getInt("sex"));
            order.setAddress(rs.getString("address"));
            order.setPostcode(rs.getString("postcode"));
            order.setPhone(rs.getString("phone"));
            order.setPayWay(rs.getInt("billingway"));
            order.setTotalfee(rs.getFloat("totalfee"));
            order.setDeliveryfee(rs.getFloat("deliveryfee"));
            order.setPayfee(rs.getFloat("payfee"));
            order.setCreateDate(rs.getTimestamp("createdate"));
            order.setStatus(rs.getInt("status"));
            order.setNouse(rs.getInt("nouse"));
            order.setSendWay(rs.getInt("sendway"));
            order.setPayWay(rs.getInt("payway"));
            order.setNees_invoicee(rs.getInt("need_invoice"));
            order.setUserscores(rs.getInt("usescores"));
            order.setProvince(rs.getString("province"));
            order.setCity(rs.getString("city"));
            order.setLinktime(rs.getString("linktime"));
            order.setOrderscores(rs.getInt("orderscores"));
            order.setPayflag(rs.getInt("payflag"));
            order.setR2TrxId(rs.getString("jylsh"));
            //order.setUsecard(rs.getInt("usecard"));

            //订报
            order.setSuppliername(rs.getString("suppliername"));
            order.setNumbercopy(rs.getInt("ordernum"));                 //定几份报纸(与Ordernum重复)
            order.setSubscribenum(rs.getInt("subscribenum"));          //服务订阅的期数，可以是季度数，月份数，年或者半年
            order.setSubscribe(rs.getInt("subscribe"));
            order.setProductid(rs.getInt("productid"));
            order.setProductname(rs.getString("productname"));
            order.setOrderNum(rs.getInt("ordernum"));                   //购买份数，购买商品数量
            order.setSaleprice(rs.getFloat("SALEPRICE"));
            order.setCardid(rs.getInt("cardid"));
            order.setUserinstarttime(rs.getDate("userinstarttime"));
            order.setUserinendtime(rs.getDate("userinendtime"));
            order.setServicestarttime(rs.getDate("servicestarttime"));
            order.setServiceendtime(rs.getDate("serviceendtime"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }
}