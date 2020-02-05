package com.unittest;

import com.alibaba.fastjson.JSONObject;
import com.bizwink.cms.util.DBUtil;
import com.bizwink.cms.util.FileUtil;
import com.bizwink.po.City;
import com.bizwink.po.Town;
import com.bizwink.po.Village;
import com.bizwink.po.Zone;

import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-3-14
 * Time: 14:09:42
 * To change this template use File | Settings | File Templates.
 */
public class HelloWordJWS {
    /**
     * @param addr
     * 查询的地址
     * @return
     * @throws IOException
     */
    public static String[] getCoordinate(String addr) {
        String lng = null;//经度
        String lat = null;//纬度
        String address = null;
        try {
            address = java.net.URLEncoder.encode(addr, "UTF-8");
        }catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        //System.out.println(address);
        String url = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=AvbEjHSzXW19XZWXwRUT8kLvFExFEfku&address="+address;
        URL myURL = null;

        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStreamReader insr = null;
        BufferedReader br = null;
        try {
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) {
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8");
                br = new BufferedReader(insr);
                String data = null;
                while((data= br.readLine())!=null){
                    JSONObject json = JSONObject.parseObject(data);
                    if (json.getJSONObject("result")!=null) {
                        lng = json.getJSONObject("result").getJSONObject("location").getString("lng");
                        lat = json.getJSONObject("result").getJSONObject("location").getString("lat");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (insr != null) {
                    insr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException exp) {

            }
        }
        return new String[]{lng,lat};
    }

    public static String[] getAddr(String lng,String lat) throws IOException {
        System.out.println("lng==" + lng);
        System.out.println("lat==" + lat);

        String url = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=AvbEjHSzXW19XZWXwRUT8kLvFExFEfku&location="+lat+","+lng;
        URL myURL = null;
        String city = "";
        String qx = "";
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStreamReader insr = null;
        BufferedReader br = null;
        try {
            httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
            if (httpsConn != null) {
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8");
                br = new BufferedReader(insr);
                String data = null;
                while((data= br.readLine())!=null){
                    System.out.println("data=="+data);
                    if (data!=null) {
                        JSONObject json = JSONObject.parseObject(data);
                        city = json.getJSONObject("result").getJSONObject("addressComponent").getString("city");
                        qx = json.getJSONObject("result").getJSONObject("addressComponent").getString("district");
                        System.out.println("city==" + city);
                        System.out.println("qx==" + qx);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(insr!=null){
                insr.close();
            }
            if(br!=null){
                br.close();
            }
        }
        return new String[]{city,qx};
    }


    public static void main(String args[])
    {
        PreparedStatement pstmt = null,pstmt1 = null;
        ResultSet rs = null;
        List<String> pl = new ArrayList();
        Connection conn = DBUtil.createConnection("localhost", "suppdbadmin", "qazwsxokm", "orcl11g", 1522, 1);
        try {
            //String[] o1 = getAddr(o[0], o[1]);
            //System.out.println(o1[0]);
            //System.out.println(o1[1]);

            pstmt = conn.prepareStatement("select c_id,c_strcorpname,c_strurl,c_gszcd,c_jyfw from t_suppinfo t where t.c_gszcd is not null and t.lng is null");
            pstmt1 = conn.prepareStatement("update t_suppinfo t set t.lng=?,t.lat=? where t.c_id=?");
            rs = pstmt.executeQuery();
            int count = 0;
            while(rs.next() && count<10000) {
                    String[] o = getCoordinate(rs.getString("c_gszcd"));
                    pl.add(rs.getString("c_strcorpname") + "||" + rs.getString("c_gszcd") + "||" + rs.getString("c_strurl") + "||" + rs.getString("c_jyfw")
                            + "||" + o[0] + "||" + o[1]);
                    System.out.println(rs.getString("c_strcorpname") + "||" + rs.getString("c_gszcd"));
                    if (o[0]!=null && o[1]!=null) {
                        pstmt1.setDouble(1, Double.parseDouble(o[0]));
                        pstmt1.setDouble(2, Double.parseDouble(o[1]));
                        pstmt1.setLong(3,rs.getLong("c_id"));
                        pstmt1.executeUpdate();
                    }
                    count = count + 1;
            }
            rs.close();
            pstmt.close();
            pstmt1.close();
            conn.commit();
            conn.close();

            /*List<String> villages =  FileUtil.readFileByLines("C:\\projects\\webbuilder\\document\\村.txt");
            Village village = null;
            int cityisnull = 0;
            StringBuffer unvalid = new StringBuffer();
            for(int ii=0; ii<villages.size(); ii++) {
                String buf = villages.get(ii);
                buf = buf.replaceAll(", null, null, null, null, null","");
                int posi = buf.indexOf("VALUES");
                buf = buf.substring(posi+"VALUES".length());
                buf = buf.trim().substring(1,buf.trim().length()-2);
                String[] tt = buf.split(",");
                String towncode = tt[2].trim().substring(1, tt[2].trim().length() - 1);
                String villagename = tt[1].trim().substring(1, tt[1].trim().length() - 1);
                String villagecode = tt[0].trim().substring(1, tt[0].trim().length() - 1);

                BigDecimal townid = null;
                String p_selfcode = null;
                for(int jj=0;jj<pl.size();jj++) {
                    String tbuf = pl.get(jj);
                    p_selfcode = tbuf.substring(0,tbuf.indexOf("-"));
                    int stownid = Integer.parseInt(tbuf.substring(tbuf.indexOf("-")+1));
                    if (towncode.equalsIgnoreCase(p_selfcode)) {
                        townid = BigDecimal.valueOf(stownid);
                        break;
                    }
                }

                if (townid!=null) {
                    village = new Village();
                    village.setORDERID(BigDecimal.valueOf(ii + 1));
                    village.setVALID(BigDecimal.valueOf(0));
                    village.setTOWNID(townid);
                    village.setVILLAGENAME(villagename);
                    village.setSELFCODE(villagecode);
                    village.setPSELFCODE(p_selfcode);
                    village.setID(BigDecimal.valueOf(ii + 1));
                    pstmt = conn.prepareStatement("insert into EN_Village(id,orderid,townid,villagename,valid,selfcode,pselfcode) values(?, ?, ?, ?, ?, ?, ?)");
                    pstmt.setInt(1, village.getID().intValue());
                    pstmt.setInt(2, village.getORDERID().intValue());
                    pstmt.setInt(3, village.getTOWNID().intValue());
                    pstmt.setString(4, village.getVILLAGENAME());
                    pstmt.setInt(5, village.getVALID().intValue());
                    pstmt.setString(6, village.getSELFCODE());
                    pstmt.setString(7, village.getPSELFCODE());
                    pstmt.executeUpdate();
                    pstmt.close();

                    System.out.println(village.getID().intValue() + "==" + village.getVILLAGENAME() + "==" + village.getTOWNID().intValue() + "==" + village.getSELFCODE() + "==" + village.getPSELFCODE() + "===" + village.getVALID());
                } else {
                    unvalid.append(villagecode + "=" + villagename + "=" + towncode + "\r\n");
                    cityisnull=cityisnull+1;
                }
            }*/

            //lA1OUxeHEBISZVGtj2k0hP16

            try {
                File fout = new File("c:\\data\\out.txt");
                FileOutputStream fos = new FileOutputStream(fout);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                for (int i = 0; i < pl.size(); i++) {
                    bw.write(pl.get(i));
                    bw.newLine();
                }
                bw.close();
                fos.close();
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        } catch(SQLException exp) {
            exp.printStackTrace();
        }
    }
}
