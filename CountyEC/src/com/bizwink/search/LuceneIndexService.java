package com.bizwink.search;

import com.bizwink.cms.DBDocument;
import com.bizwink.po.Companyinfo;
import com.bizwink.util.DBUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 18-4-12.
 */
public class LuceneIndexService {
    private static String indexPath="C:\\cms\\indexer\\111";
    private static DataSource connPool;
    private static IndexWriter writer;		        // new index being built

    public LuceneIndexService(){

    }

    public static void main(String[] args) {
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        connPool = (DataSource)ctx.getBean("myDataSource");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int errorcode = 0;

        if (args.length==1 && args[0]!=null) indexPath = args[0];

        while(true) {
            try {
                File file = new File(indexPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                // 配置索引
                //Path path = Paths.get(new URI(indexPath));
                //Directory directory = new SimpleFSDirectory(path);
                //Analyzer analyzer = new SmartChineseAnalyzer();
                Analyzer analyzer = new IKAnalyzer();
                Directory directory = new SimpleFSDirectory(new File(indexPath));
                writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_45,analyzer));

                // 删除所有document
                //writer.deleteAll();
            } catch (Exception ioexp) {
                errorcode = -1;
                System.out.println("打开索引的写操作："+ioexp.getMessage());
            }

            try{
                if (errorcode == 0) {
                    boolean doflag = true;
                    conn = connPool.getConnection();
                    do {
                        List<Companyinfo> companyinfos = new ArrayList<Companyinfo>();
                        Companyinfo companyinfo = null;
                        int rownum = 0;
                        pstmt = conn.prepareStatement("select t.*, t.rowid from tbl_companyinfo t where t.samsiteid in (2991,4592,2992) and t.indexflag=0 and rownum <= 200");
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            companyinfo = new Companyinfo();
                            companyinfo.setID(BigDecimal.valueOf(rs.getInt("id")));
                            companyinfo.setSITEID(BigDecimal.valueOf(rs.getInt("siteid")));
                            companyinfo.setCOMPANYNAME(rs.getString("companyname"));
                            companyinfo.setCOMPANYADDRESS(rs.getString("companyaddress"));
                            companyinfo.setSUMMARY(rs.getString("summary"));
                            companyinfo.setBIZPHONE(rs.getString("bizphone"));
                            companyinfo.setCOMPANYPHONE(rs.getString("companyphone"));
                            companyinfo.setCOMPANYEMAIL(rs.getString("companyemail"));
                            companyinfo.setCOMPANYFAX(rs.getString("companyfax"));
                            companyinfo.setCOMPANYPIC(rs.getString("companypic"));
                            companyinfo.setCOMPANYWEBSITE(rs.getString("companywebsite"));
                            companyinfo.setSAMSITEID(BigDecimal.valueOf(rs.getInt("samsiteid")));
                            companyinfo.setPROVINCE(rs.getString("province"));
                            companyinfo.setCITY(rs.getString("city"));
                            companyinfo.setTOWN(rs.getString("town"));
                            companyinfo.setZONE(rs.getString("zone"));
                            companyinfo.setVILLAGE(rs.getString("village"));
                            companyinfo.setCOMPANYLATITUDE(BigDecimal.valueOf(rs.getDouble("COMPANYLATITUDE")));
                            companyinfo.setCOMPANYLONGITUDE(BigDecimal.valueOf(rs.getDouble("COMPANYLONGITUDE")));
                            companyinfo.setCREATEDATE(rs.getTimestamp("createdate"));
                            companyinfo.setUPDATEDATE(rs.getTimestamp("updatedate"));
                            companyinfos.add(companyinfo);
                            rownum = rownum + 1;
                        }
                        rs.close();
                        pstmt.close();

                        //建立索引
                        System.out.println("获取信息数量" + companyinfos.size());
                        for(int ii=0;ii<companyinfos.size(); ii++) {
                            companyinfo = companyinfos.get(ii);
                            if (companyinfo.getCOMPANYNAME()!=null && companyinfo.getSITEID().intValue()!=0){
                                int siteid = companyinfo.getSITEID().intValue();
                                StringBuffer buf = new StringBuffer();
                                pstmt = conn.prepareStatement("select content from tbl_template t where t.siteid=?");
                                pstmt.setInt(1,siteid);
                                rs = pstmt.executeQuery();
                                while (rs.next()){
                                    buf.append(DBUtil.getBigString("oracle", rs, "content"));
                                }
                                rs.close();
                                pstmt.close();

                                String zh_province = getChineseAreaName("province",companyinfo.getPROVINCE());
                                String zh_city = getChineseAreaName("city",companyinfo.getCITY());
                                String zh_zone = getChineseAreaName("zone",companyinfo.getZONE());
                                String zh_town = getChineseAreaName("town",companyinfo.getTOWN());
                                String zh_village = getChineseAreaName("village", companyinfo.getVILLAGE());
                                System.out.println(zh_province +"="+zh_city+"="+zh_zone +"="+zh_village + "=" +zh_town + "==" + companyinfo.getCOMPANYNAME());

                                writer.addDocument(DBDocument.Document(companyinfo, buf.toString(), zh_province, zh_city, zh_zone, zh_town, zh_village));

                                //修改信息为已经被索引状态
                                pstmt = conn.prepareStatement("update tbl_companyinfo t set t.indexflag=1 where t.siteid=? and t.indexflag=0");
                                pstmt.setInt(1,siteid);
                                pstmt.executeUpdate();
                                pstmt.close();
                            }
                        }
                        //如果获取的需要索引的数据少于200行，则停止索引
                        if (rownum < 200)  doflag = false;
                    } while (doflag == true);
                } else {
                    System.out.println("创建索引的WRITER过程报错");
                }
            } catch(Exception exp) {
                exp.printStackTrace();
            } finally {
                try{
                    conn.close();
                } catch (SQLException exp) {
                    exp.printStackTrace();
                }
            }

            //系统睡眠半个小时，然后开始重新扫描文章，对新文章建立索引
            try {
                System.out.println("系统睡眠1分钟，重新开始检查是否有文章需要进行索引！！！");
                writer.commit();
                writer.close();
                Thread.sleep(1000*60);
            } catch(InterruptedException e) {
                System.out.println("抛出异常中断问题！！！");
            } catch(IOException exp) {
                exp.printStackTrace();
            }

        }
    }

    private static String getChineseAreaName(String areatype,String code) {
        String zhName = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = connPool.getConnection();
            if (areatype.equalsIgnoreCase("province"))
                pstmt = conn.prepareStatement("select provname from en_province t where t.code=?");
            else if (areatype.equalsIgnoreCase("city"))
                pstmt = conn.prepareStatement("select cityname from en_city t where t.code=?");
            else if (areatype.equalsIgnoreCase("zone"))
                pstmt = conn.prepareStatement("select zonename from en_zone t where t.code=?");
            else if (areatype.equalsIgnoreCase("village"))
                pstmt = conn.prepareStatement("select villagename from en_village t where t.code=?");
            else if (areatype.equalsIgnoreCase("town"))
                pstmt = conn.prepareStatement("select townname from en_town t where t.code=?");

            if (pstmt!=null) {
                pstmt.setString(1,code);
                rs = pstmt.executeQuery();
                if (rs.next()) zhName = rs.getString(1);
                rs.close();
                pstmt.close();
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try{
                conn.close();
            } catch(SQLException exp) {
                exp.printStackTrace();
            }
        }

        return zhName;
    }
}