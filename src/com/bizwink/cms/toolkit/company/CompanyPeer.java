package com.bizwink.cms.toolkit.company;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;

import java.sql.PreparedStatement;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CompanyPeer implements ICompanyManager {
    PoolServer cpool;

    public CompanyPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ICompanyManager getInstance() {
        return CmsServer.getInstance().getFactory().getCompanyManager();
    }

    //向表中插入记录
    private static String SQL_INSERTSINOCOMPANY = "insert into sino_company (id,companyname,companyaddress,companyphone,companyfax,companywebsite,companyemail,postcode,districtnumber,classification,area,summary,station) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public void addCompany(Company company) {
        Connection conn1 = null;
        Connection conn2 = null;
        PreparedStatement pstmt1;
        PreparedStatement pstmt2;
        ISequenceManager seqMgr = SequencePeer.getInstance();
        String seqFlag = "Company";
        int id = seqMgr.getSequenceNum(seqFlag);

        try {
            try {
                Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            String url = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=sino_company";
            String user = "sa";
            String password = "123456";
            boolean commitflag = false;
            try {
                conn2 = DriverManager.getConnection(url, user, password);
                conn2.setAutoCommit(false);
                pstmt2 = conn2.prepareStatement(SQL_INSERTSINOCOMPANY);
                pstmt2.setInt(1, id);
                pstmt2.setString(2, company.getCompanyname());
                pstmt2.setString(3, company.getCompanyaddress());
                pstmt2.setString(4, company.getCompanyphone());
                pstmt2.setString(5, company.getCompanyfax());
                pstmt2.setString(6, company.getCompanywebsite());
                pstmt2.setString(7, company.getCompanyemail());
                pstmt2.setString(8, company.getPostcode());
                pstmt2.setString(9, company.getDistrictnumber());
                pstmt2.setString(10, company.getClsaaification());
                pstmt2.setString(11, company.getArea());
                //pstmt2.setString(12, company.getSummary());
                DBUtil.setBigString("mssql", pstmt2, 12, company.getSummary());
                pstmt2.setInt(13, company.getStation());
                pstmt2.executeUpdate();
                pstmt2.close();
                commitflag = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (commitflag) {
                try {
                    conn1 = cpool.getConnection();
                    conn1.setAutoCommit(false);
                    pstmt1 = conn1.prepareStatement(SQL_INSERTSINOCOMPANY);
                    pstmt1.setInt(1, id);
                    pstmt1.setString(2, company.getCompanyname());
                    pstmt1.setString(3, company.getCompanyaddress());
                    pstmt1.setString(4, company.getCompanyphone());
                    pstmt1.setString(5, company.getCompanyfax());
                    pstmt1.setString(6, company.getCompanywebsite());
                    pstmt1.setString(7, company.getCompanyemail());
                    pstmt1.setString(8, company.getPostcode());
                    pstmt1.setString(9, company.getDistrictnumber());
                    pstmt1.setString(10, company.getClsaaification());
                    pstmt1.setString(11, company.getArea());
                    DBUtil.setBigString("oracle", pstmt1, 12, company.getSummary());
                    pstmt1.setInt(13, company.getStation());
                    pstmt1.executeUpdate();
                    pstmt1.close();

                    conn2.commit();
                    conn1.commit();
                } catch (SQLException e) {
                    conn2.rollback();
                    conn1.rollback();
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn2 != null) {
                try {
                    conn2.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //删除公司
    private static String SQL_DELETECOMPANY = "delete from sino_company where id=?";

    public void delCompany(int id) {
        Connection conn = null;
        Connection conn2 = null;
        PreparedStatement pstmt;
        PreparedStatement pstmt2;
        try {
            try {
                Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            String url = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=sino_company";
            String user = "sa";
            String password = "123456";
            boolean commitflag = false;

            try {
                conn2 = DriverManager.getConnection(url, user, password);
                conn2.setAutoCommit(false);
                pstmt2 = conn2.prepareStatement(SQL_DELETECOMPANY);
                pstmt2.setInt(1, id);
                pstmt2.executeUpdate();
                pstmt2.close();
                commitflag = true;
                if (commitflag) {
                    conn = cpool.getConnection();
                    conn.setAutoCommit(false);
                    pstmt = conn.prepareStatement(SQL_DELETECOMPANY);
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();

                    pstmt.close();
                    conn2.commit();
                    conn.commit();
                }
            } catch (SQLException e) {
                conn2.rollback();
                conn.rollback();
                e.printStackTrace();
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
            if (conn2 != null) {
                try {
                    conn2.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //根据comapnytype得到中文公司列表还是英文公司列表 companytype=0为中文 companytype=1为英文
    public List getCompanyInfo(int start, int range, String sql) {
        List list = new ArrayList();
        Company company = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (!sql.equals("")) {
            sql = sql.replaceAll("@", "%");
        }
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            for (int i = 0; i < start; i++) {
                rs.next();
            }
            for (int i = 0; i < range && rs.next(); i++) {
                company = new Company();
                company.setId(rs.getInt("id"));
                company.setCompanyname(rs.getString("companyname"));
                company.setCompanyaddress(rs.getString("companyaddress"));
                company.setCompanyphone(rs.getString("companyphone"));
                company.setCompanyfax(rs.getString("companyfax"));
                company.setCompanywebsite(rs.getString("companywebsite"));
                company.setCompanyemail(rs.getString("companyemail"));
                company.setPostcode(rs.getString("postcode"));
                company.setDistrictnumber(rs.getString("districtnumber"));
                company.setClsaaification(rs.getString("classification"));
                company.setArea(rs.getString("area"));
                company.setSummary(rs.getString("summary"));
                company.setStation(rs.getInt("station"));

                list.add(company);
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
        return list;
    }

    //根据companytype=0得到所有中文公司的数量，companytype=1得到英文公司的数量
    public int getCompanyNum(String sql) {
        int num = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (!sql.equals("")) {
            sql = sql.replaceAll("@", "%");
        }
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
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return num;
    }

    //根据ID得到公司的一个对象

    public Company getACompanyInfo(int id) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Company company = new Company();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from sino_company where id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                company = new Company();
                company.setId(rs.getInt("id"));
                company.setCompanyname(rs.getString("companyname"));
                company.setCompanyaddress(rs.getString("companyaddress"));
                company.setCompanyphone(rs.getString("companyphone"));
                company.setCompanyfax(rs.getString("companyfax"));
                company.setCompanywebsite(rs.getString("companywebsite"));
                company.setCompanyemail(rs.getString("companyemail"));
                company.setPostcode(rs.getString("postcode"));
                company.setDistrictnumber(rs.getString("districtnumber"));
                company.setClsaaification(rs.getString("classification"));
                company.setArea(rs.getString("area"));
                company.setSummary(rs.getString("summary"));
                company.setStation(rs.getInt("station"));
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
        return company;
    }

    private static String SQL_UPDATESINOCOMPANY = "update sino_company set companyname=?,companyaddress=?,companyphone=?,companyfax=?,companywebsite=?,companyemail=?,postcode=?,districtnumber=?,classification=?,area=?,summary=? where id=?";

    public void modifyCompany(Company company) {
        Connection conn = null;
        Connection conn2 = null;
        PreparedStatement pstmt;
        PreparedStatement pstmt2;
        try {
            try {
                Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            String url = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=sino_company";
            String user = "sa";
            String password = "123456";
            boolean commitflag = false;
            try {
                conn2 = DriverManager.getConnection(url, user, password);
                conn2.setAutoCommit(false);
                pstmt2 = conn2.prepareStatement(SQL_UPDATESINOCOMPANY);
                pstmt2.setString(1, company.getCompanyname());
                pstmt2.setString(2, company.getCompanyaddress());
                pstmt2.setString(3, company.getCompanyphone());
                pstmt2.setString(4, company.getCompanyfax());
                pstmt2.setString(5, company.getCompanywebsite());
                pstmt2.setString(6, company.getCompanyemail());
                pstmt2.setString(7, company.getPostcode());
                pstmt2.setString(8, company.getDistrictnumber());
                pstmt2.setString(9, company.getClsaaification());
                pstmt2.setString(10, company.getArea());
                pstmt2.setString(11, company.getSummary());
                pstmt2.setInt(12, company.getId());
                pstmt2.executeUpdate();
                pstmt2.close();
                commitflag = true;
            } catch (SQLException e) {

                e.printStackTrace();
            }
            if (commitflag) {
                try {
                    conn = cpool.getConnection();
                    conn.setAutoCommit(false);
                    pstmt = conn.prepareStatement(SQL_UPDATESINOCOMPANY);
                    pstmt.setString(1, company.getCompanyname());
                    pstmt.setString(2, company.getCompanyaddress());
                    pstmt.setString(3, company.getCompanyphone());
                    pstmt.setString(4, company.getCompanyfax());
                    pstmt.setString(5, company.getCompanywebsite());
                    pstmt.setString(6, company.getCompanyemail());
                    pstmt.setString(7, company.getPostcode());
                    pstmt.setString(8, company.getDistrictnumber());
                    pstmt.setString(9, company.getClsaaification());
                    pstmt.setString(10, company.getArea());
                    pstmt.setString(11, company.getSummary());
                    pstmt.setInt(12, company.getId());
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn2.commit();
                    conn.commit();

                } catch (SQLException e) {
                    conn2.rollback();
                    conn.rollback();
                    e.printStackTrace();
                }
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
            if (conn2 != null) {
                try {
                    conn2.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** private void getSqlConnection() {
     ResultSet rs = null;
     Statement stmt = null;
     Connection conn = null;
     try {
     Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
     } catch (InstantiationException e1) {
     e1.printStackTrace();
     } catch (IllegalAccessException e1) {
     e1.printStackTrace();
     } catch (ClassNotFoundException e1) {
     e1.printStackTrace();
     }
     String url = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=sino_company";
     String user = "sa";
     String password = "123456";
     try {
     conn = DriverManager.getConnection(url, user, password);
     stmt = conn.createStatement();
     rs = stmt.executeQuery("select * from sino_company");
     while (rs.next()) {
     System.out.print("用户名：" + rs.getString("id"));
     System.out.print("密　码：" + rs.getString("companyname"));
     System.out.println();
     }
     } catch (SQLException e) {
     e.printStackTrace();
     }
     }**/
}