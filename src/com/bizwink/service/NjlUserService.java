package com.bizwink.service;

import com.bizwink.cms.util.Encrypt;
import com.bizwink.persistence.*;
import com.bizwink.po.*;
import com.heaton.bot.siteinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 15-12-27.
 */
@Service
public class NjlUserService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private ColumnMapper columnMapper;
    @Autowired
    private SiteinfoMapper siteinfoMapper;
    @Autowired
    private SiteipinfoMapper siteipinfoMapper;
    @Autowired
    private CompanyinfoMapper companyinfoMapper;
    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private SitesNumberMapper sitesNumberMapper;
    @Autowired
    private CmsTemplateMapper cmsTemplateMapper;
    @Autowired
    private MembersRightsMapper membersRightsMapper;
    @Autowired
    private PublishQueueMapper publishQueueMapper;

    private static final String SQL_INSERT_DNS_RECORD = "INSERT INTO records(zone,host,type,data,ttl,mx_priority,refresh,retry," +
            "expire,minimum,serial,resp_contact,primary_ns,second_ns,data_count) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    //records                   创建DNS记录
    //TBL_SiteInfo              创建站点
    //TBL_SiteIPInfo            创建网站所在的主机
    //TBL_Column                创建网站根节点
    //TBL_Members               创建用户
    //TBL_Members_Rights        写入用户权限数据
    //tbl_companyclass          不用
    //tbl_websiteclass          不用
    //tbl_template              选择需要发布的作业
    //tbl_new_publish_queue     将需要发布的作业写入作业队列
    //tbl_sites_number          修改站点列表中相关主机站点的数量

    @Transactional
    public Siteinfo RegisterNjlUserInfo(List njluser,int TemplateNum,String contactor,int samsiteid) {
        int reg_dns_flag = 0;
        int errcode = 0;
        String www_ip = null;
        String ftp_ip = null;
        String docroot = null;
        String ftpuser = null;
        String ftppasswd = null;
        int flag = 0;
        Siteinfo siteInfo = null;

        Users user = (Users)njluser.get(0);
        Timestamp now = new Timestamp(System.currentTimeMillis());

        List<SitesNumber> sitesNumbers = sitesNumberMapper.selectMinSiteNumberRecord();
        SitesNumber sitesNumber = (SitesNumber)sitesNumbers.get(0);
        www_ip = sitesNumber.getWWWIP();
        ftp_ip = sitesNumber.getIPADDRESS();
        docroot = sitesNumber.getDOCROOT();
        ftpuser = sitesNumber.getFTPUSER();
        ftppasswd = sitesNumber.getFTPPASSWD();
        flag = sitesNumber.getFLAG().intValue();

        try {
            Connection mysql_conn = null;
            PreparedStatement mysql_pstmt =  null;

            //'gugulx.com', 'petersong', 'A', '211.154.43.210', '800', '28800' , '14400' , '10', NULL ,NULL , NULL , NULL , NULL;
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            //jdbc:mysql://localhost:3306/cms?useUnicode=true&characterEncoding=utf8
            mysql_conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dns?characterEncoding=gbk", "dns", "1qaz2wsx");
            mysql_conn.setAutoCommit(false);
            mysql_pstmt = mysql_conn.prepareStatement(SQL_INSERT_DNS_RECORD);
            mysql_pstmt.setString(1, "gugulx.com");
            mysql_pstmt.setString(2, user.getUSERID());
            mysql_pstmt.setString(3, "A");
            mysql_pstmt.setString(4, www_ip);
            mysql_pstmt.setInt(5, 86400);
            mysql_pstmt.setNull(6, java.sql.Types.VARCHAR);
            mysql_pstmt.setInt(7, 14400);
            mysql_pstmt.setInt(8, 10);
            mysql_pstmt.setInt(9, 604800);
            mysql_pstmt.setInt(10, 800);
            mysql_pstmt.setNull(11, java.sql.Types.BIGINT);
            mysql_pstmt.setString(12, "gugulx.com");
            mysql_pstmt.setString(13, "dns1.gugulx.com");
            mysql_pstmt.setString(14, "dns2.gugulx.com");
            mysql_pstmt.setInt(15, 0);
            mysql_pstmt.executeUpdate();
            mysql_pstmt.close();
            mysql_conn.commit();
            mysql_conn.close();
        } catch (Exception sqlexp) {
            sqlexp.printStackTrace();
            reg_dns_flag = -1;
            errcode = -11;
            System.out.println("增加DNS记录出现错误");
        }

        if (reg_dns_flag == 0 && errcode == 0) {
            //设置站点信息
            try{
                System.out.println("Service userid=" + user.getUSERID() + "====" + samsiteid + "====" + TemplateNum);
                BigDecimal siteid =siteinfoMapper.getSiteinfoMainKey();
                siteInfo = new Siteinfo();
                siteInfo.setSITENAME(user.getUSERID() + ".gugulx.com");
                siteInfo.setSITEIP(www_ip);
                siteInfo.setIMAGESDIR((short) 0);
                siteInfo.setCSSJSDIR((short) 0);
                siteInfo.setTCFLAG((short) 0);
                siteInfo.setWAPFLAG((short) 0);
                siteInfo.setPUBFLAG((short) 0);
                siteInfo.setBINDFLAG((short) 1);
                siteInfo.setBEREFERED((short) 0);
                siteInfo.setCREATEDATE(now);
                siteInfo.setSITEVALID(BigDecimal.valueOf(1));
                siteInfo.setSAMSITEID(BigDecimal.valueOf(samsiteid));
                siteInfo.setENCODEFLAG(BigDecimal.valueOf(1));
                siteInfo.setSHARETEMPLATENUM(BigDecimal.valueOf(TemplateNum));
                siteInfo.setSITEID(siteid);
                errcode = siteinfoMapper.insert(siteInfo);

                //设置站点发布主机信息
                Siteipinfo siteipinfo = new Siteipinfo();
                siteipinfo.setSITEID(siteid);
                siteipinfo.setSITEIP(ftp_ip);
                siteipinfo.setSITENAME(user.getUSERID() + ".gugulx.com");
                siteipinfo.setDOCPATH(docroot + "/" + user.getUSERID());
                siteipinfo.setFTPUSER(ftpuser);
                siteipinfo.setFTPPASSWD(ftppasswd);
                siteipinfo.setFTPTYPE(BigDecimal.valueOf(flag));
                siteipinfo.setPUBLISHWAY((short)0);
                siteipinfo.setSTATUS((short)1);
                siteipinfo.setID(siteipinfoMapper.getSiteipinfoMainKey());
                int id = siteipinfoMapper.insert(siteipinfo);


                //设置站点根栏目
                BigDecimal rootColumnID = columnMapper.getColumnMainKey();
                Column column = new Column();
                column.setID(rootColumnID);
                column.setSITEID(siteid);
                column.setPARENTID(BigDecimal.valueOf(0));
                column.setORDERID(BigDecimal.valueOf(1));
                column.setCNAME(user.getUSERID() + ".gugulx.com");
                column.setENAME("/");
                column.setDIRNAME("/");
                column.setEDITOR("admin");
                column.setEXTNAME("shtml");
                column.setISDEFINEATTR((short)0);
                column.setHASARTICLEMODEL((short)0);
                column.setISAUDITED((short)0);
                column.setISPRODUCT((short)0);
                column.setISPUBLISHMORE((short)0);
                column.setLANGUAGETYPE((short)0);
                column.setISRSS((short)0);
                column.setCREATEDATE(now);
                column.setLASTUPDATED(now);
                errcode = columnMapper.insert(column);

                //设置用户信息
                Users myuser = new Users();
                myuser.setUSERID(user.getUSERID());
                myuser.setSITEID(siteid);
                myuser.setUSERPWD(Encrypt.md5(user.getUSERPWD().getBytes()));
                myuser.setNICKNAME(contactor);                                         //用户联系人信息
                myuser.setMPHONE(user.getMPHONE());                                    //用户的手机号码
                myuser.setEMAIL(user.getEMAIL());                                      //用户的电子邮件地址
                myuser.setCOMPANY(user.getCOMPANY());
                myuser.setCOUNTRY(user.getCOUNTRY());                                  //用户所在国家
                myuser.setPROVINCE(user.getPROVINCE());                                //用户所在省
                myuser.setCITY(user.getCITY());                                        //用户所在市
                myuser.setAREA(user.getAREA());                                        //用户所在县
                myuser.setJIEDAO(user.getJIEDAO());                                    //用户所在乡镇、街道
                myuser.setSHEQU(user.getSHEQU());                                      //用户所在村、社区
                myuser.setADDRESS(user.getADDRESS());                                  //用户详细地址
                myuser.setPOSTCODE(user.getPOSTCODE());                                //用户的邮政编码
                myuser.setUSERTYPE(user.getUSERTYPE());                                //用户类型 0企业用户  1-个人用户
                myuser.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                usersMapper.insert(myuser);

                //设置用户的权限信息
                MembersRights membersRights = new MembersRights();
                membersRights.setUSERID(user.getUSERID());
                membersRights.setCOLUMNID(BigDecimal.valueOf(0));
                membersRights.setRIGHTID((short)54);
                membersRightsMapper.insert(membersRights);

                //向tbl_companyinfo表增加注册信息
                Companyinfo companyinfo = new Companyinfo();
                companyinfo.setCOMPANYNAME(user.getCOMPANY());
                companyinfo.setCOMPANYADDRESS(user.getADDRESS());
                companyinfo.setSITEID(siteid);
                companyinfo.setCOMPANYPHONE(user.getMPHONE());
                companyinfo.setCOMPANYEMAIL(user.getEMAIL());
                companyinfo.setCREATEUSER(user.getUSERID());
                companyinfo.setSAMSITEID(BigDecimal.valueOf(samsiteid));
                companyinfo.setCONTACTOR(contactor);
                companyinfo.setCOUNTRY(user.getCOUNTRY());
                companyinfo.setPROVINCE(user.getPROVINCE());
                companyinfo.setCITY(user.getCITY());                                        //用户所在市
                companyinfo.setZONE(user.getAREA());                                        //用户所在县
                companyinfo.setTOWN(user.getJIEDAO());                                    //用户所在乡镇、街道
                companyinfo.setVILLAGE(user.getSHEQU());                                      //用户所在村、社区
                companyinfo.setPUBLISHFLAG(BigDecimal.valueOf(0));
                companyinfo.setSTATUS(BigDecimal.valueOf(0));
                if (samsiteid == 4592) {                                                //njy04.coosite.com
                    companyinfo.setCOMPANYCLASSID(BigDecimal.valueOf(2963));
                    companyinfo.setCLASSIFICATION("美丽乡村");
                } else if (samsiteid == 2991) {                                        //njy01.coosite.com
                    companyinfo.setCOMPANYCLASSID(BigDecimal.valueOf(1163));
                    companyinfo.setCLASSIFICATION("农家院");
                } else if (samsiteid == 2992) {                                        //njy03.coosite.com
                    companyinfo.setCOMPANYCLASSID(BigDecimal.valueOf(2983));
                    companyinfo.setCLASSIFICATION("采摘园");
                } else if (samsiteid == 2993) {                                        //njy02.coosite.com
                    companyinfo.setCOMPANYCLASSID(BigDecimal.valueOf(2984));
                    companyinfo.setCLASSIFICATION("景区");
                }
                companyinfo.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                companyinfo.setUPDATEDATE(new Timestamp(System.currentTimeMillis()));
                companyinfo.setID(companyinfoMapper.getCompanyMainKey());
                int comid = companyinfoMapper.insert(companyinfo);
                System.out.println("comid===" + comid);

                //定义存储发布作业的列表变量queueList和作业变量publish
                List<PublishQueue> queueList = new ArrayList();
                PublishQueue publish = null;

                //向tbl_template表中增加专题模板内容
                if (samsiteid == 4592) {                                                      //njy04.coosite.com   美丽乡村
                    Template model = new Template();
                    model.setSITEID(siteid);
                    model.setCOLUMNID(BigDecimal.valueOf(63357));
                    model.setCHNAME("美丽乡村简介");
                    model.setTEMPLATENAME("brief");
                    model.setISARTICLE((short)3);
                    model.setCONTENT("");
                    model.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    model.setLASTUPDATED(new Timestamp(System.currentTimeMillis()));
                    model.setEDITOR(user.getUSERID());
                    model.setCREATOR(user.getUSERID());
                    model.setSTATUS((short)0);
                    model.setLOCKSTATUS((short)0);
                    model.setDEFAULTTEMPLATE((short)0);
                    model.setREFERMODELID(BigDecimal.valueOf(0));
                    model.setISINCLUDED((short)0);
                    model.setTEMPNUM(BigDecimal.valueOf(TemplateNum));
                    model.setID(templateMapper.getTemplateMainKey());
                    templateMapper.insert(model);

                    publish = new PublishQueue();
                    publish.setSITEID(siteid);
                    publish.setTARGETID(model.getID());
                    if (model.getISARTICLE() == 2)  {           //首页模板使用站点根栏目ID
                        publish.setCOLUMNID(rootColumnID);
                    }else {                                           //非首页模板使用样例站点的栏目ID
                        publish.setCOLUMNID(model.getCOLUMNID());
                    }
                    publish.setTYPE(BigDecimal.valueOf(model.getISARTICLE()));
                    publish.setSTATUS((short) 1);
                    publish.setPUBLISHDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setTITLE(model.getCHNAME());
                    publish.setERRNUM(BigDecimal.valueOf(0));
                    publish.setPRIORITY(BigDecimal.valueOf(1));
                    queueList.add(publish);

                    model = new Template();
                    model.setSITEID(siteid);
                    model.setCOLUMNID(BigDecimal.valueOf(63360));
                    model.setCHNAME("联系方式");
                    model.setTEMPLATENAME("contactus");
                    model.setISARTICLE((short)3);
                    model.setCONTENT("");
                    model.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    model.setLASTUPDATED(new Timestamp(System.currentTimeMillis()));
                    model.setEDITOR(user.getUSERID());
                    model.setCREATOR(user.getUSERID());
                    model.setSTATUS((short)0);
                    model.setLOCKSTATUS((short)0);
                    model.setDEFAULTTEMPLATE((short)0);
                    model.setREFERMODELID(BigDecimal.valueOf(0));
                    model.setISINCLUDED((short)0);
                    model.setTEMPNUM(BigDecimal.valueOf(TemplateNum));
                    model.setID(templateMapper.getTemplateMainKey());
                    templateMapper.insert(model);

                    publish = new PublishQueue();
                    publish.setSITEID(siteid);
                    publish.setTARGETID(model.getID());
                    if (model.getISARTICLE() == 2)  {           //首页模板使用站点根栏目ID
                        publish.setCOLUMNID(rootColumnID);
                    }else {                                           //非首页模板使用样例站点的栏目ID
                        publish.setCOLUMNID(model.getCOLUMNID());
                    }
                    publish.setTYPE(BigDecimal.valueOf(model.getISARTICLE()));
                    publish.setSTATUS((short) 1);
                    publish.setPUBLISHDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setTITLE(model.getCHNAME());
                    publish.setERRNUM(BigDecimal.valueOf(0));
                    publish.setPRIORITY(BigDecimal.valueOf(1));
                    queueList.add(publish);
                } else if (samsiteid == 2991) {                                              //njy01.coosite.com    农家院
                    int js_columnid = 52719;                               //农家院简介
                    Template model = new Template();
                    model.setSITEID(siteid);
                    model.setCOLUMNID(BigDecimal.valueOf(js_columnid));
                    model.setCHNAME("农家院简介");
                    model.setTEMPLATENAME("brief");
                    model.setISARTICLE((short)3);
                    model.setCONTENT("");
                    model.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    model.setLASTUPDATED(new Timestamp(System.currentTimeMillis()));
                    model.setEDITOR(user.getUSERID());
                    model.setCREATOR(user.getUSERID());
                    model.setSTATUS((short)0);
                    model.setLOCKSTATUS((short)0);
                    model.setDEFAULTTEMPLATE((short)0);
                    model.setREFERMODELID(BigDecimal.valueOf(0));
                    model.setISINCLUDED((short)0);
                    model.setTEMPNUM(BigDecimal.valueOf(TemplateNum));
                    model.setID(templateMapper.getTemplateMainKey());
                    templateMapper.insert(model);

                    publish = new PublishQueue();
                    publish.setSITEID(siteid);
                    publish.setTARGETID(model.getID());
                    if (model.getISARTICLE() == 2)  {           //首页模板使用站点根栏目ID
                        publish.setCOLUMNID(rootColumnID);
                    }else {                                           //非首页模板使用样例站点的栏目ID
                        publish.setCOLUMNID(model.getCOLUMNID());
                    }
                    publish.setTYPE(BigDecimal.valueOf(model.getISARTICLE()));
                    publish.setSTATUS((short) 1);
                    publish.setPUBLISHDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setTITLE(model.getCHNAME());
                    publish.setERRNUM(BigDecimal.valueOf(0));
                    publish.setPRIORITY(BigDecimal.valueOf(1));
                    queueList.add(publish);

                    int note_columnid = 52737;                             //注意事项
                    model = new Template();
                    model.setSITEID(siteid);
                    model.setCOLUMNID(BigDecimal.valueOf(note_columnid));
                    model.setCHNAME("注意事项");
                    model.setTEMPLATENAME("notes");
                    model.setISARTICLE((short)3);
                    model.setCONTENT("");
                    model.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    model.setLASTUPDATED(new Timestamp(System.currentTimeMillis()));
                    model.setEDITOR(user.getUSERID());
                    model.setCREATOR(user.getUSERID());
                    model.setSTATUS((short)0);
                    model.setLOCKSTATUS((short)0);
                    model.setDEFAULTTEMPLATE((short)0);
                    model.setREFERMODELID(BigDecimal.valueOf(0));
                    model.setISINCLUDED((short)0);
                    model.setTEMPNUM(BigDecimal.valueOf(TemplateNum));
                    model.setID(templateMapper.getTemplateMainKey());
                    templateMapper.insert(model);

                    publish = new PublishQueue();
                    publish.setSITEID(siteid);
                    publish.setTARGETID(model.getID());
                    if (model.getISARTICLE() == 2)  {           //首页模板使用站点根栏目ID
                        publish.setCOLUMNID(rootColumnID);
                    }else {                                           //非首页模板使用样例站点的栏目ID
                        publish.setCOLUMNID(model.getCOLUMNID());
                    }
                    publish.setTYPE(BigDecimal.valueOf(model.getISARTICLE()));
                    publish.setSTATUS((short) 1);
                    publish.setPUBLISHDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setTITLE(model.getCHNAME());
                    publish.setERRNUM(BigDecimal.valueOf(0));
                    publish.setPRIORITY(BigDecimal.valueOf(1));
                    queueList.add(publish);

                    int lx_columnid = 52738;                               //农家院联系信息
                    model = new Template();
                    model.setSITEID(siteid);
                    model.setCOLUMNID(BigDecimal.valueOf(lx_columnid));
                    model.setCHNAME("联系方式");
                    model.setTEMPLATENAME("contactus");
                    model.setISARTICLE((short)3);
                    model.setCONTENT("");
                    model.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    model.setLASTUPDATED(new Timestamp(System.currentTimeMillis()));
                    model.setEDITOR(user.getUSERID());
                    model.setCREATOR(user.getUSERID());
                    model.setSTATUS((short)0);
                    model.setLOCKSTATUS((short)0);
                    model.setDEFAULTTEMPLATE((short)0);
                    model.setREFERMODELID(BigDecimal.valueOf(0));
                    model.setISINCLUDED((short)0);
                    model.setTEMPNUM(BigDecimal.valueOf(TemplateNum));
                    model.setID(templateMapper.getTemplateMainKey());
                    templateMapper.insert(model);

                    publish = new PublishQueue();
                    publish.setSITEID(siteid);
                    publish.setTARGETID(model.getID());
                    if (model.getISARTICLE() == 2)  {           //首页模板使用站点根栏目ID
                        publish.setCOLUMNID(rootColumnID);
                    }else {                                           //非首页模板使用样例站点的栏目ID
                        publish.setCOLUMNID(model.getCOLUMNID());
                    }
                    publish.setTYPE(BigDecimal.valueOf(model.getISARTICLE()));
                    publish.setSTATUS((short) 1);
                    publish.setPUBLISHDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setTITLE(model.getCHNAME());
                    publish.setERRNUM(BigDecimal.valueOf(0));
                    publish.setPRIORITY(BigDecimal.valueOf(1));
                    queueList.add(publish);

                    int tsms_columnid = 52757;                             //农家院特色美食
                    model = new Template();
                    model.setSITEID(siteid);
                    model.setCOLUMNID(BigDecimal.valueOf(tsms_columnid));
                    model.setCHNAME("特色美食");
                    model.setTEMPLATENAME("tesems");
                    model.setISARTICLE((short) 3);
                    model.setCONTENT("");
                    model.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    model.setLASTUPDATED(new Timestamp(System.currentTimeMillis()));
                    model.setEDITOR(user.getUSERID());
                    model.setCREATOR(user.getUSERID());
                    model.setSTATUS((short)0);
                    model.setLOCKSTATUS((short) 0);
                    model.setDEFAULTTEMPLATE((short) 0);
                    model.setREFERMODELID(BigDecimal.valueOf(0));
                    model.setISINCLUDED((short) 0);
                    model.setTEMPNUM(BigDecimal.valueOf(TemplateNum));
                    model.setID(templateMapper.getTemplateMainKey());
                    templateMapper.insert(model);

                    publish = new PublishQueue();
                    publish.setSITEID(siteid);
                    publish.setTARGETID(model.getID());
                    if (model.getISARTICLE() == 2)  {           //首页模板使用站点根栏目ID
                        publish.setCOLUMNID(rootColumnID);
                    }else {                                           //非首页模板使用样例站点的栏目ID
                        publish.setCOLUMNID(model.getCOLUMNID());
                    }
                    publish.setTYPE(BigDecimal.valueOf(model.getISARTICLE()));
                    publish.setSTATUS((short) 1);
                    publish.setPUBLISHDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setTITLE(model.getCHNAME());
                    publish.setERRNUM(BigDecimal.valueOf(0));
                    publish.setPRIORITY(BigDecimal.valueOf(1));
                    queueList.add(publish);

                    int tshd_columnid = 52758;                             //农家院特色活动
                    model = new Template();
                    model.setSITEID(siteid);
                    model.setCOLUMNID(BigDecimal.valueOf(tshd_columnid));
                    model.setCHNAME("特色活动");
                    model.setTEMPLATENAME("tesehd");
                    model.setISARTICLE((short) 3);
                    model.setCONTENT("");
                    model.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    model.setLASTUPDATED(new Timestamp(System.currentTimeMillis()));
                    model.setEDITOR(user.getUSERID());
                    model.setCREATOR(user.getUSERID());
                    model.setSTATUS((short)0);
                    model.setLOCKSTATUS((short) 0);
                    model.setDEFAULTTEMPLATE((short) 0);
                    model.setREFERMODELID(BigDecimal.valueOf(0));
                    model.setISINCLUDED((short) 0);
                    model.setTEMPNUM(BigDecimal.valueOf(TemplateNum));
                    model.setID(templateMapper.getTemplateMainKey());
                    templateMapper.insert(model);

                    publish = new PublishQueue();
                    publish.setSITEID(siteid);
                    publish.setTARGETID(model.getID());
                    if (model.getISARTICLE() == 2)  {           //首页模板使用站点根栏目ID
                        publish.setCOLUMNID(rootColumnID);
                    }else {                                           //非首页模板使用样例站点的栏目ID
                        publish.setCOLUMNID(model.getCOLUMNID());
                    }
                    publish.setTYPE(BigDecimal.valueOf(model.getISARTICLE()));
                    publish.setSTATUS((short) 1);
                    publish.setPUBLISHDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setTITLE(model.getCHNAME());
                    publish.setERRNUM(BigDecimal.valueOf(0));
                    publish.setPRIORITY(BigDecimal.valueOf(1));
                    queueList.add(publish);
                } else if (samsiteid == 2992) {                                        //njy03.coosite.com    采摘园
                    int js_columnid = 63336;                               //农家院简介
                    Template model = new Template();
                    model.setSITEID(siteid);
                    model.setCOLUMNID(BigDecimal.valueOf(js_columnid));
                    model.setCHNAME("采摘园简介");
                    model.setTEMPLATENAME("brief");
                    model.setISARTICLE((short)3);
                    model.setCONTENT("");
                    model.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    model.setLASTUPDATED(new Timestamp(System.currentTimeMillis()));
                    model.setEDITOR(user.getUSERID());
                    model.setCREATOR(user.getUSERID());
                    model.setSTATUS((short)0);
                    model.setLOCKSTATUS((short)0);
                    model.setDEFAULTTEMPLATE((short)0);
                    model.setREFERMODELID(BigDecimal.valueOf(0));
                    model.setISINCLUDED((short)0);
                    model.setTEMPNUM(BigDecimal.valueOf(TemplateNum));
                    model.setID(templateMapper.getTemplateMainKey());
                    templateMapper.insert(model);

                    publish = new PublishQueue();
                    publish.setSITEID(siteid);
                    publish.setTARGETID(model.getID());
                    if (model.getISARTICLE() == 2)  {           //首页模板使用站点根栏目ID
                        publish.setCOLUMNID(rootColumnID);
                    }else {                                           //非首页模板使用样例站点的栏目ID
                        publish.setCOLUMNID(model.getCOLUMNID());
                    }
                    publish.setTYPE(BigDecimal.valueOf(model.getISARTICLE()));
                    publish.setSTATUS((short) 1);
                    publish.setPUBLISHDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setTITLE(model.getCHNAME());
                    publish.setERRNUM(BigDecimal.valueOf(0));
                    publish.setPRIORITY(BigDecimal.valueOf(1));
                    queueList.add(publish);

                    int lx_columnid = 64276;                               //农家院联系信息
                    model = new Template();
                    model.setSITEID(siteid);
                    model.setCOLUMNID(BigDecimal.valueOf(lx_columnid));
                    model.setCHNAME("联系方式");
                    model.setTEMPLATENAME("contactus");
                    model.setISARTICLE((short)3);
                    model.setCONTENT("");
                    model.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    model.setLASTUPDATED(new Timestamp(System.currentTimeMillis()));
                    model.setEDITOR(user.getUSERID());
                    model.setCREATOR(user.getUSERID());
                    model.setSTATUS((short)0);
                    model.setLOCKSTATUS((short)0);
                    model.setDEFAULTTEMPLATE((short)0);
                    model.setREFERMODELID(BigDecimal.valueOf(0));
                    model.setISINCLUDED((short)0);
                    model.setTEMPNUM(BigDecimal.valueOf(TemplateNum));
                    model.setID(templateMapper.getTemplateMainKey());
                    templateMapper.insert(model);

                    publish = new PublishQueue();
                    publish.setSITEID(siteid);
                    publish.setTARGETID(model.getID());
                    if (model.getISARTICLE() == 2)  {           //首页模板使用站点根栏目ID
                        publish.setCOLUMNID(rootColumnID);
                    }else {                                           //非首页模板使用样例站点的栏目ID
                        publish.setCOLUMNID(model.getCOLUMNID());
                    }
                    publish.setTYPE(BigDecimal.valueOf(model.getISARTICLE()));
                    publish.setSTATUS((short) 1);
                    publish.setPUBLISHDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setTITLE(model.getCHNAME());
                    publish.setERRNUM(BigDecimal.valueOf(0));
                    publish.setPRIORITY(BigDecimal.valueOf(1));
                    queueList.add(publish);
                } else if (samsiteid == 2993) {                                        //njy02.coosite.com    景区

                }

                //修改服务器支持虚拟站点的数目
                sitesNumber.setSITESNUM(BigDecimal.valueOf(sitesNumber.getSITESNUM().intValue() + 1));
                sitesNumber.setIPADDRESS(ftp_ip);
                sitesNumberMapper.updateByPrimaryKey(sitesNumber);

                //向发布队列中增加发布作业
                CmsTemplate cmsTemplate = null;
                List<CmsTemplate> templatesList = new ArrayList();
                Map params = new HashMap();
                params.put("SITEID",samsiteid);
                params.put("TEMPNUM",TemplateNum);
                templatesList = cmsTemplateMapper.selectPublishTemplate(params);

                for(int ii=0; ii<templatesList.size();ii++) {
                    cmsTemplate = new CmsTemplate();
                    cmsTemplate = templatesList.get(ii);
                    publish = new PublishQueue();
                    publish.setSITEID(siteid);
                    publish.setTARGETID(cmsTemplate.getID());
                    if (cmsTemplate.getISARTICLE() == 2)  {           //首页模板使用站点根栏目ID
                        publish.setCOLUMNID(rootColumnID);
                    }else {                                           //非首页模板使用样例站点的栏目ID
                        publish.setCOLUMNID(cmsTemplate.getCOLUMNID());
                    }
                    publish.setTYPE(BigDecimal.valueOf(cmsTemplate.getISARTICLE()));
                    publish.setSTATUS((short) 1);
                    publish.setPUBLISHDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    publish.setTITLE(cmsTemplate.getCHNAME());
                    publish.setERRNUM(BigDecimal.valueOf(0));
                    publish.setPRIORITY(BigDecimal.valueOf(1));
                    queueList.add(publish);
                }

                for (int ii=0; ii<queueList.size(); ii++) {
                    publish = (PublishQueue) queueList.get(ii);
                    PublishQueue pq =new PublishQueue();
                    BigDecimal pqid = publishQueueMapper.getPublishQueueMainKey();
                    pq.setSITEID(publish.getSITEID());
                    pq.setCOLUMNID(publish.getCOLUMNID());
                    pq.setTARGETID(publish.getTARGETID());
                    pq.setTYPE(publish.getTYPE());

                    pq =publishQueueMapper.GetOneJob(pq);
                    if (pq==null) {
                        publish.setID(pqid);
                        publishQueueMapper.insert(publish);
                    }
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }

        return siteInfo;
    }

    public List<CmsTemplate> getTemplates(BigDecimal siteid) {
        return cmsTemplateMapper.selectBySiteID(siteid);
    }

    public CmsTemplate getTemplate(BigDecimal siteid,BigDecimal tempno) {
        CmsTemplate ct = new CmsTemplate();
        ct.setSITEID(siteid);
        ct.setTEMPNUM(tempno);
        ct = cmsTemplateMapper.getTemplate(ct);
        System.out.println("internal====" + ct.getCONTENT());
        return ct;
    }

    public Siteinfo getSiteinfo(BigDecimal siteid) {
        return siteinfoMapper.selectByPrimaryKey(siteid);
    }

    public Siteinfo getSiteinfo(String sitename) {
        return siteinfoMapper.selectBySitename(sitename);
    }

    public Users selectByPrimaryKey(String username) {
        return usersMapper.selectByPrimaryKey(username);
    }

    public Users selectByEmail(String email) {
        return usersMapper.selectByEmail(email);
    }

    public int getRootColumnIdBySiteid(BigDecimal siteid) {
        return columnMapper.getRootColumnIdBySiteid(siteid);
    }
}
