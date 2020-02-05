package com.transferChinadrtvData;

import com.bizwink.cms.business.Order.AddressInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import com.bizwink.cms.tree.snode;
import com.bizwink.cms.util.FileUtil;
import com.bizwink.mysql.po.OrderDetail;
import com.bizwink.mysql.po.Orders;
import com.bizwink.po.Users;
import com.bizwink.util.DateUtil;
import com.bizwink.util.Encrypt;
import com.bizwink.util.GeneralMethod;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;

public class transferBjrabData {
    static Connection createConnection(String ip, String username, String password, int flag) {
        Connection conn = null;
        String dbip = ip;
        String dbusername = username;
        String dbpassword = password;

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
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:oracle11g", dbusername, dbpassword);

            } else if (flag == 2) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + dbip + ":3306/ec", username, password);
            } else if (flag == 3){
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                String strurl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=e:\\dataforonegoo\\wsjc10-08-02-1.mdb";
                conn = DriverManager.getConnection(strurl,username,password);

            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }

    public static List<Users> transferUserinfos(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Users user = null;
        List<Users> usersList = new ArrayList();

        try{
            conn = createConnection("localhost", "jbfxdbadmin", "qazwsxokm", 1);
            pstmt = conn.prepareStatement("select memberid,password,address,phone,mobilephone,email from tbl_userinfo t");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                user = new Users();
                user.setSITEID(BigDecimal.valueOf(1));
                user.setUSERID(rs.getString("memberid"));
                user.setUSERPWD(rs.getString("password"));
                user.setADDRESS(rs.getString("address"));
                user.setPHONE(rs.getString("phone"));
                user.setMPHONE(rs.getString("mobilephone"));
                user.setEMAIL(rs.getString("email"));
                if (existTheUserInList(usersList,user.getUSERID()) == 0)
                    usersList.add(user);
            }
            rs.close();
            pstmt.close();
            conn.close();

            conn = createConnection("116.90.87.233", "ecdbadmin", "Zaq!2wsx", 2);
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO tbl_members(siteid,userid,nickname,userpwd,address,phone,mphone,email,usertype,createdate,lastupdate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for(int ii=0; ii<usersList.size(); ii++) {
                user = usersList.get(ii);
                if (user.getUSERPWD()!=null) {
                    String password = Encrypt.md5(user.getUSERPWD().getBytes());
                    System.out.println(ii + "转注册用户信息，==userid==" + user.getUSERID() + "==" + user.getUSERPWD() + "==" + user.getADDRESS());
                    pstmt.setInt(1,user.getSITEID().intValue());
                    pstmt.setString(2,user.getUSERID());
                    pstmt.setString(3,user.getUSERID());
                    pstmt.setString(4,password);
                    pstmt.setString(5,user.getADDRESS());
                    pstmt.setString(6,user.getPHONE());
                    if (user.getMPHONE()==null)
                        pstmt.setString(7,"13801381000");
                    else
                        pstmt.setString(7,user.getMPHONE());
                    pstmt.setString(8,user.getEMAIL());
                    pstmt.setInt(9,5);
                    pstmt.setTimestamp(10,new Timestamp(System.currentTimeMillis()));
                    pstmt.setTimestamp(11,new Timestamp(System.currentTimeMillis()));
                    pstmt.executeUpdate();
                }
            }
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn !=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return usersList;
    }

    public static int existTheUser(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            conn = createConnection("116.90.87.233", "ecdbadmin", "Zaq!2wsx", 2);
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("select count(*) from tbl_members where userid=?");
            pstmt.setString(1,username);
            rs = pstmt.executeQuery();
            if (rs.next()) count = rs.getInt(1);
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn !=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return count;
    }

    public static int existTheUserInList(List<Users> users,String username) {
        int count = 0;
        Users user = null;
        for(int ii=0; ii<users.size();ii++) {
            user = users.get(ii);
            if (username.equals(user.getUSERID())) {
                count = 1;
                break;
            }
        }
        return count;
    }

    public static int existTheOrderidInList(List<Orders> orderses,long orderid) {
        int count = 0;
        Orders order = null;
        for(int ii=0; ii<orderses.size();ii++) {
            order = orderses.get(ii);
            if (orderid == order.getORDERID()) {
                count = 1;
                break;
            }
        }
        return count;
    }

    public static void createUser() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Users user = null;
        List<Users> usersList = new ArrayList();

        try{
            conn = createConnection("localhost", "jbfxdbadmin", "qazwsxokm", 1);
            pstmt = conn.prepareStatement("select distinct t.name,t.phone,t.telephone,t.postcode,t.address,t.email from tbl_orders t where t.name not in (select memberid from tbl_userinfo)");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                user = new Users();
                user.setSITEID(BigDecimal.valueOf(1));
                user.setUSERID(rs.getString("name").trim());
                user.setUSERPWD(Encrypt.md5(rs.getString("name").getBytes()));
                user.setADDRESS(rs.getString("address"));
                user.setPHONE(rs.getString("telephone"));
                user.setMPHONE(rs.getString("phone"));
                user.setADDRESS(rs.getString("address"));
                user.setEMAIL(rs.getString("email"));
                if (existTheUserInList(usersList,user.getUSERID()) == 0)
                    usersList.add(user);
            }
            rs.close();
            pstmt.close();
            conn.close();

            conn = createConnection("116.90.87.233", "ecdbadmin", "Zaq!2wsx", 2);
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO tbl_members(siteid,userid,nickname,userpwd,address,phone,mphone,email,usertype,createdate,lastupdate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for(int ii=0; ii<usersList.size(); ii++) {
                user = usersList.get(ii);
                if (existTheUser(user.getUSERID())==0) {
                    if (user.getUSERPWD()!=null) {
                        String password = Encrypt.md5(user.getUSERPWD().getBytes());
                        System.out.println(ii + "注册匿名订报的用户，==userid==" + user.getUSERID() + "==" + user.getUSERPWD() + "==" + user.getADDRESS());
                        pstmt.setInt(1,user.getSITEID().intValue());
                        pstmt.setString(2,user.getUSERID());
                        pstmt.setString(3,user.getUSERID());
                        pstmt.setString(4,password);
                        pstmt.setString(5,user.getADDRESS());
                        pstmt.setString(6,user.getPHONE());
                        if (user.getMPHONE()==null)
                            pstmt.setString(7,"13801381000");
                        else
                            pstmt.setString(7,user.getMPHONE());
                        pstmt.setString(8,user.getEMAIL());
                        pstmt.setInt(9,5);
                        pstmt.setTimestamp(10,new Timestamp(System.currentTimeMillis()));
                        pstmt.setTimestamp(11,new Timestamp(System.currentTimeMillis()));
                        pstmt.executeUpdate();
                    }
                }
            }
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn !=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void transferOrders() {
        Connection conn = null;
        PreparedStatement pstmt = null,pstmt1 = null;
        ResultSet rs = null;
        Orders order = null;
        OrderDetail orderDetail = null;
        List<Orders> ordersList = new ArrayList();
        List<OrderDetail> orderDetailList = new ArrayList();

        try{
            conn = createConnection("localhost", "jbfxdbadmin", "qazwsxokm", 1);
            pstmt = conn.prepareStatement("select t.orderid,t.name,t.address,t.postcode,t.phone,t.payfee,t.status,t.nouse,t.payflag,t.need_invoice,t.email,t.telephone,t.createdate," +
                    " t1.suppliername,t1.saleprice,t1.subscribe,t1.riqi,t1.numbercopy,t1.xiangxi,t1.productname,t1.ordertype from tbl_orders t,tbl_orders_detail t1 where t.orderid=t1.orderid");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                long orderid = rs.getLong("orderid");
                order = new Orders();
                String riqi = rs.getString("riqi");
                if (riqi!=null && orderid!=1802 && orderid!=1788 && orderid!=1789) {
                    String[] tt = riqi.split("-");
                    if (tt[1].length() == 1) tt[1] = "0" + tt[1];
                    if (tt[2].length() == 1) tt[2] = "0" + tt[2];
                    riqi = tt[0] + "-" + tt[1] + "-" + tt[2];
                }
                String subscibeType = rs.getString("subscribe");
                if (riqi!=null && subscibeType!=null && orderid!=1802 && orderid!=1788 && orderid!=1789) {
                    order.setSITEID(1);
                    order.setORDERID(rs.getLong("orderid"));
                    order.setNAME(rs.getString("name"));
                    order.setADDRESS(rs.getString("address"));
                    order.setPOSTCODE(rs.getString("postcode"));
                    order.setPHONE(rs.getString("phone"));
                    order.setTOTALFEE(rs.getDouble("payfee"));
                    order.setPAYFEE(rs.getDouble("payfee"));
                    order.setCREATEDATE(rs.getTimestamp("createdate"));
                    order.setSTATUS(rs.getInt("status"));
                    order.setNOUSE(rs.getInt("nouse"));
                    order.setPayflag(rs.getShort("payflag"));
                    order.setNeed_invoice(rs.getInt("need_invoice"));

                    if (existTheOrderidInList(ordersList,order.getORDERID())==0) {
                        ordersList.add(order);
                        Timestamp startDate = rs.getTimestamp("createdate");
                        Timestamp endDate = Timestamp.valueOf(riqi + " 00:00:00");
                        int subscribeType_i = 0;
                        int subscribenum = 0;
                        String unit = "";
                        if (subscibeType.equals("年订")) {
                            subscribenum = 1;
                            subscribeType_i = 1;
                            unit = "年";
                        } else if (subscibeType.equals("半年订")) {
                            subscribenum = 1;
                            subscribeType_i = 2;
                            unit = "半年";
                        } else if (subscibeType.equals("季订")){
                            Calendar s = Calendar.getInstance();
                            s.setTime(startDate);
                            Calendar e = Calendar.getInstance();
                            e.setTime(endDate);
                            int days = DateUtil.differentDays(s.getTime(),e.getTime());
                            subscribenum = (int)Math.ceil(days/30);
                            subscribeType_i = 3;
                            unit = "季";
                            String difDate = s.get(Calendar.YEAR) + "-" + (s.get(Calendar.MONTH) + 1) + "-" + s.get(Calendar.DAY_OF_MONTH) + "到" + e.get(Calendar.YEAR) + "_" + (e.get(Calendar.MONTH) + 1) + "-" + e.get(Calendar.DAY_OF_MONTH);
                        } else if (subscibeType.equals("月订")){
                            Calendar s = Calendar.getInstance();
                            s.setTime(startDate);
                            Calendar e = Calendar.getInstance();
                            e.setTime(endDate);
                            int days = DateUtil.differentDays(s.getTime(),e.getTime());
                            subscribenum = (int)Math.ceil(days/30);
                            subscribeType_i = 4;
                            unit = "月";
                            String difDate = s.get(Calendar.YEAR) + "-" + (s.get(Calendar.MONTH) + 1) + "-" + s.get(Calendar.DAY_OF_MONTH) + "到" + e.get(Calendar.YEAR) + "_" + (e.get(Calendar.MONTH) + 1) + "-" + e.get(Calendar.DAY_OF_MONTH);
                        }

                        orderDetail = new OrderDetail();
                        orderDetail.setORDERID(rs.getLong("orderid"));
                        orderDetail.setProductname(rs.getString("suppliername"));
                        orderDetail.setSubscribe(subscribeType_i);
                        orderDetail.setServicestarttime(rs.getTimestamp("createdate"));
                        orderDetail.setServiceendtime(endDate);
                        orderDetail.setORDERNUM(rs.getInt("numbercopy"));
                        orderDetail.setSubscribenum(subscribenum);
                        orderDetail.setUnit(unit);
                        orderDetail.setProductname(rs.getString("suppliername"));
                        orderDetail.setZenpin(rs.getString("xiangxi"));
                        orderDetail.setCREATEDATE(rs.getTimestamp("createdate"));
                        orderDetail.setSALEPRICE(rs.getDouble("saleprice"));
                        orderDetailList.add(orderDetail);
                    }
                }
            }
            rs.close();
            pstmt.close();
            conn.close();

            conn = createConnection("116.90.87.233", "ecdbadmin", "Zaq!2wsx", 2);
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO tbl_orders(orderid,name,address,postcode,phone,totalfee,payfee,payway,payflag,flag,valid,status,nouse,need_invoice,createdate,lastupdate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt1 = conn.prepareStatement("INSERT INTO tbl_orders_detail(orderid,suppliername,productid,productname,ordernum,saleprice,servicestarttime,serviceendtime,userinstarttime,unit,subscribe,subscribenum,zenpin,createdate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for(int ii=0; ii<ordersList.size(); ii++) {
                order = ordersList.get(ii);
                System.out.println(ii + "转订单信息，===orderid==" + order.getORDERID() + "==" + orderDetail.getProductname());
                pstmt.setLong(1,order.getORDERID());
                pstmt.setString(2, order.getNAME());
                pstmt.setString(3,order.getADDRESS());
                pstmt.setString(4,order.getPOSTCODE());
                pstmt.setString(5,order.getPHONE());
                pstmt.setDouble(6, order.getTOTALFEE());
                pstmt.setDouble(7, order.getPAYFEE());
                if (order.getPayway() != null)
                    pstmt.setInt(8, order.getPayway());
                else
                    pstmt.setNull(8, Types.NULL);
                pstmt.setInt(9,order.getPayflag());
                pstmt.setInt(10,0);
                pstmt.setInt(11,0);
                pstmt.setInt(12,order.getSTATUS());
                pstmt.setInt(13,order.getNOUSE());
                pstmt.setInt(14,0);
                pstmt.setTimestamp(15,new Timestamp(order.getCREATEDATE().getTime()));
                pstmt.setTimestamp(16,new Timestamp(order.getCREATEDATE().getTime()));
                pstmt.executeUpdate();

                orderDetail = orderDetailList.get(ii);
                pstmt1.setLong(1,order.getORDERID());
                pstmt1.setString(2, orderDetail.getSuppliername());
                if (orderDetail.getSuppliername() != null)
                    if (orderDetail.getSuppliername().equals("北京晚报"))
                        pstmt1.setInt(3,6);
                    else
                        pstmt1.setInt(3, 5);
                else
                    pstmt1.setInt(3, 0);
                pstmt1.setString(4,orderDetail.getProductname());
                pstmt1.setInt(5, orderDetail.getORDERNUM());
                pstmt1.setDouble(6, orderDetail.getSALEPRICE());
                pstmt1.setTimestamp(7, new Timestamp(orderDetail.getServicestarttime().getTime()));
                pstmt1.setTimestamp(8, new Timestamp(orderDetail.getServiceendtime().getTime()));
                if (orderDetail.getUserinstarttime()!=null)
                    pstmt1.setTimestamp(9, new Timestamp(orderDetail.getUserinstarttime().getTime()));
                else
                    pstmt1.setNull(9,Types.NULL);
                pstmt1.setString(10, orderDetail.getUnit());
                pstmt1.setInt(11,orderDetail.getSubscribe());
                pstmt1.setInt(12,orderDetail.getSubscribenum());
                pstmt1.setString(13, orderDetail.getZenpin());
                pstmt1.setTimestamp(14, new Timestamp(orderDetail.getCREATEDATE().getTime()));
                pstmt1.executeUpdate();
            }
            pstmt.close();
            pstmt1.close();

            //修改订单的用户ID
            pstmt = conn.prepareStatement("select id from tbl_members where userid=?");
            pstmt1 = conn.prepareStatement("update tbl_orders set userid=? where orderid=?");
            for(int ii=0; ii<ordersList.size(); ii++) {
                order = ordersList.get(ii);
                orderDetail = orderDetailList.get(ii);
                int uid = 0;
                pstmt.setString(1,order.getNAME());
                rs = pstmt.executeQuery();
                if (rs.next()) uid = rs.getInt("id");
                rs.close();

                pstmt1.setInt(1,uid);
                pstmt1.setLong(2,order.getORDERID());
                pstmt1.executeUpdate();
            }
            pstmt.close();
            pstmt1.close();
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn !=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<AddressInfo> transferAddressinfos(){
        Connection conn = null;
        PreparedStatement pstmt = null,pstmt1=null;
        ResultSet rs = null,rs1=null;
        AddressInfo addressInfo = null;
        List<AddressInfo> addressInfoList = new ArrayList();

        try{
            //从ORACLE的订单表获取送货地址信息，将送货地址信息保存到订单表中
            //name,phone,address,
            conn = createConnection("localhost", "jbfxdbadmin", "qazwsxokm", 1);
            pstmt = conn.prepareStatement("select t.orderid,t.name,t.address,t.postcode,t.phone,t.telephone,t.payfee,t.status,t.nouse,t.payflag,t.need_invoice,t.email,t.telephone,t.createdate," +
                    " t1.suppliername,t1.saleprice,t1.subscribe,t1.riqi,t1.numbercopy,t1.xiangxi,t1.productname,t1.ordertype from tbl_orders t,tbl_orders_detail t1 where t.orderid=t1.orderid");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                addressInfo = new AddressInfo();
                long orderid = rs.getLong("orderid");
                if (orderid!=1802 && orderid!=1788 && orderid!=1789) {
                    addressInfo.setSiteid(1);
                    addressInfo.setOrderid(rs.getLong("orderid"));
                    addressInfo.setName(rs.getString("name"));
                    addressInfo.setAddress(rs.getString("address"));
                    addressInfo.setZip(rs.getString("postcode"));
                    addressInfo.setPhone(rs.getString("telephone"));
                    addressInfo.setMobile(rs.getString("phone"));
                    addressInfo.setEmail(rs.getString("email"));
                    addressInfo.setCreatedate(rs.getTimestamp("createdate"));
                    addressInfo.setUserid(0);
                    addressInfoList.add(addressInfo);
                }
            }
            rs.close();
            pstmt.close();
            conn.close();

            conn = createConnection("116.90.87.233", "ecdbadmin", "Zaq!2wsx", 2);
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO tbl_addressinfofororder(siteid,orderid,userid,name,mobile,phone,zip,address,email,createdate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for(int ii=0; ii<addressInfoList.size(); ii++) {
                addressInfo = addressInfoList.get(ii);
                System.out.println(ii + "==转送报地址信息， userid==" + addressInfo.getName() + "==" + addressInfo.getAddress() + "==" + addressInfo.getOrderid());
                pstmt.setInt(1, addressInfo.getSiteid());
                pstmt.setLong(2, addressInfo.getOrderid());
                pstmt.setInt(3, addressInfo.getUserid());
                pstmt.setString(4, addressInfo.getName());
                pstmt.setString(5,addressInfo.getMobile());
                pstmt.setString(6,addressInfo.getPhone());
                pstmt.setString(7,addressInfo.getZip());
                pstmt.setString(8, addressInfo.getAddress());
                pstmt.setString(9, addressInfo.getEmail());
                pstmt.setTimestamp(10, addressInfo.getCreatedate());
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.commit();

            //修改订单的用户ID
            pstmt = conn.prepareStatement("select id from tbl_members where userid=?");
            pstmt1 = conn.prepareStatement("update tbl_addressinfofororder set userid=? where orderid=?");
            for(int ii=0; ii<addressInfoList.size(); ii++) {
                addressInfo = addressInfoList.get(ii);
                int uid = 0;
                pstmt.setString(1,addressInfo.getName());
                rs = pstmt.executeQuery();
                if (rs.next()) uid = rs.getInt("id");
                rs.close();

                pstmt1.setInt(1,uid);
                pstmt1.setLong(2,addressInfo.getOrderid());
                pstmt1.executeUpdate();
            }
            pstmt.close();
            pstmt1.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn !=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return addressInfoList;
    }

    /**
     *
     * @Title: getWeebWork
     * @Description: TODO(根据传入的文件名获取工作簿对象(Workbook))
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    public static Workbook getWeebWork(String filename) throws IOException {
        Workbook workbook=null;
        if(null!=filename){
            String fileType=filename.substring(filename.lastIndexOf("."),filename.length());
            FileInputStream fileStream = new FileInputStream(new File(filename));
            if(".xls".equals(fileType.trim().toLowerCase())){
                workbook = new HSSFWorkbook(fileStream);// 创建 Excel 2003 工作簿对象
            }else if(".xlsx".equals(fileType.trim().toLowerCase())){
                workbook = new XSSFWorkbook(fileStream);//创建 Excel 2007 工作簿对象
            }
        }

        return workbook;
    }

    public static List getInfolistFromExcel(String excelFilename) {
        int  numSheets = 0;
        Sheet aSheet = null;
        List messages = new ArrayList();
        try {
            Workbook workbook = getWeebWork(excelFilename);
            aSheet = workbook.getSheetAt(numSheets);//获得一个sheet
            if (aSheet != null) {
                for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                    Row aRow = aSheet.getRow(rowNumOfSheet); //获得一个行
                    snode thenode = new snode();
                    for (short cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
                        String buf = "";
                        if (null != aRow.getCell(cellNumOfRow)) {
                            Cell aCell = aRow.getCell(cellNumOfRow);//获得列值
                            if (aCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                buf = aCell.getStringCellValue();
                                buf = buf.trim();
                            } else if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                                buf = String.valueOf((long)aCell.getNumericCellValue());
                        }

                        //序号
                        if (cellNumOfRow == 0) {
                            thenode.setId(buf.trim());
                        }

                        //分公司
                        if (cellNumOfRow == 1) {
                            thenode.setChName(buf.trim());
                        }

                        //分站
                        if (cellNumOfRow == 2) {
                            //System.out.println(buf);
                            thenode.setEnName(buf.trim());
                        }

                        //姓名
                        if (cellNumOfRow == 3) {
                            thenode.setDesc(buf.trim());
                        }

                        //岗位
                        if (cellNumOfRow == 4) {
                            thenode.setKeyword(buf.trim());
                        }

                        //手机号
                        if (cellNumOfRow == 5) {
                            thenode.setUnit(buf.trim());
                        }

                    }

                    messages.add(thenode);
                }

                //messages列表数据进行逆序排序
                //List messages_bysort = orderItems(messages, "desc");
                //ClassTree in_tree = getClassesTreeByNodes(messages_bysort);
            }
        } catch (IOException exp) {
        }

        return messages;
        //return GeneralMethod.orderItems(messages, "desc");
    }

    private static int getOrgidByChname(String cname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int orgid = 0;
        try{
            conn = createConnection("116.90.87.233", "ecdbadmin", "Zaq!2wsx", 2);
            if (cname.equals("二分"))
                cname = "二分公司";
            else if (cname.equals("三分"))
                cname = "三分公司";
            else if (cname.equals("四分"))
                cname = "四分公司";
            else if (cname.equals("五分"))
                cname = "五分公司";
            pstmt = conn.prepareStatement("select id from tbl_organization where name=?");
            pstmt.setString(1,cname);
            rs = pstmt.executeQuery();
            if (rs.next()) orgid = rs.getInt("id");
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn !=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return orgid;
    }

    private static int getCompidByChname(String cname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int compid = 0;
        try{
            conn = createConnection("116.90.87.233", "ecdbadmin", "Zaq!2wsx", 2);
            if (cname.equals("二分"))
                cname = "二分公司";
            else if (cname.equals("三分"))
                cname = "三分公司";
            else if (cname.equals("四分"))
                cname = "四分公司";
            else if (cname.equals("五分"))
                cname = "五分公司";
            pstmt = conn.prepareStatement("select id from tbl_companyinfo where companyname=?");
            pstmt.setString(1,cname);
            rs = pstmt.executeQuery();
            if (rs.next()) compid = rs.getInt("id");
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn !=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return compid;
    }

    private static int getDeptidByEnname(String ename) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int deptid = 0;
        try{
            conn = createConnection("116.90.87.233", "ecdbadmin", "Zaq!2wsx", 2);
            pstmt = conn.prepareStatement("select id from tbl_department where cname=?");
            pstmt.setString(1,ename);
            rs = pstmt.executeQuery();
            if (rs.next()) deptid = rs.getInt("id");
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn !=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return deptid;
    }

    public static void main(String[] args) {
        //转注册用户信息
        //transferUserinfos();

        //为老系统匿名订报的订户创建用户
        //createUser();

        //转订单信息
        //transferOrders();

        //转送货地址信息
        //transferAddressinfos();

        //生成送报员用户
        List messages = getInfolistFromExcel("C:\\Document\\leads\\北京晚报\\微信公众号订报\\扫码订报系统使用人员表20181025.xlsx");
        StringBuffer buf = new StringBuffer();
        for(int ii=0; ii<messages.size(); ii++) {
            snode thenode = (snode)messages.get(ii);
            String cname = thenode.getChName();
            int orgid= getOrgidByChname(cname);
            int compid = getCompidByChname(cname);
            int deptid = getDeptidByEnname(thenode.getEnName());
            buf.append("insert into tbl_members (userid,siteid,userpwd,nickname,usertype,email,mphone,phone,sex,orgid,companyid,deptid,emailaccount,createdate,lastupdate) values('" + thenode.getUnit() + "',1,'6F2740F9D54E78691831CE4111AB515D','" + thenode.getDesc() + "',6,'','" + thenode.getUnit() + "','',0," + orgid + "," + compid + "," + deptid + ",'" + thenode.getKeyword() + "','2018-10-25 21:51:00','2018-10-25 21:51:00');\r\n");
            int width = 300; // 图像宽度
            int height = 300; // 图像高度
            String format = "png";// 图像类型
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            try {
                BitMatrix bitMatrix = new MultiFormatWriter().encode("测试我的二维码生成", BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
                // 输出图象到页面
                //ServletOutputStream stream = response.getOutputStream();
                //MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
                //输出到文件
                MatrixToImageWriter.writeToFile(bitMatrix, format, new File("C:\\Document\\leads\\北京晚报\\微信公众号订报\\td\\" + thenode.getUnit() + ".png" ));
            } catch (WriterException exp) {

            } catch (IOException exp) {

            }
            System.out.println(orgid + "=" + compid + "=" + deptid + "=" + thenode.getChName() + "==" + thenode.getEnName() + "==" + thenode.getDesc() + "==" + thenode.getKeyword() + "==" + thenode.getUnit());
        }

        FileUtil.writeFile(buf,"C:\\Document\\leads\\北京晚报\\微信公众号订报\\createuser.txt");
    }
}