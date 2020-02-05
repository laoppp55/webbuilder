package com.bizwink.service;

import com.bizwink.error.ErrorMessage;
import com.bizwink.persistence.*;
import com.bizwink.po.*;
import com.bizwink.util.Encrypt;
import com.bizwink.util.SpringInit;
import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-12-30.
 */
@Service
public class UsersService {
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
    private OrganizationMapper organizationMapper;
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


    public int addUser(Users user) {
        int errcode = 0;
        Users t_user = usersMapper.selectByPrimaryKey(user.getUSERID());
        if (t_user!=null) {
            errcode = -1;
        }

        t_user = usersMapper.selectByEmail(user.getEMAIL());
        if (t_user != null) {
            errcode = -2;
        }

        t_user = usersMapper.selectByMphone(user.getMPHONE());
        if (t_user!=null) {
            errcode = -3;
        }

        if (errcode==0){
            BigDecimal uid = usersMapper.getMainKey();
            user.setID(uid);
            errcode = usersMapper.insert(user);
        }

        return  errcode;
    }

    public boolean checkName(BigDecimal siteid,String username) {
        Users user = usersMapper.selectByPrimaryKey(username);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkEmail(BigDecimal siteid,String email) {
        Users user = usersMapper.selectByEmail(email);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkCellphone(BigDecimal siteid,String cellphone) {
        Users user = usersMapper.selectByMphone(cellphone);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    public Users getUserinfoByUserid(String userid) {
        return usersMapper.selectByPrimaryKey(userid);
    }

    public Users getUserinfoByEmail(String email) {
        return usersMapper.selectByPrimaryKey(email);
    }

    public Users getUserinfoByMphone(String mphone) {
        return usersMapper.selectByPrimaryKey(mphone);
    }

    public boolean changePassword(String username,String email,String cellphone) {

        return false;
    }

    public int getTotalUsersCount(BigDecimal siteid) {
        return usersMapper.getUsersCountByCustomer(siteid);
    }

    public List<Users> getUsersByCustomer(BigDecimal customer,BigDecimal startrow,BigDecimal endrow) {
        Map params = new HashMap();
        params.put("SITEID",customer);
        params.put("BEGINROW",startrow);
        params.put("ENDROW",endrow);
        params.put("PAGESIZE",endrow.intValue()-startrow.intValue());
        return usersMapper.getUsersByCustomer(params);
    }

    public int getUsersCountByParentOrgID(BigDecimal siteid,List<Integer> orgidList) {
        Map params = new HashMap();
        params.put("SITEID",siteid);
        params.put("idList",orgidList);
        return usersMapper.getUsersCountByParentOrgID(params);
    }

    public List<Users> getUsersByParentOrgID(BigDecimal customer,List orgidList,BigDecimal startrow,BigDecimal endrow) {
        Map params = new HashMap();
        params.put("SITEID",customer);
        params.put("idList",orgidList);
        params.put("BEGINROW",startrow);
        params.put("ENDROW",endrow);
        params.put("PAGESIZE",endrow.intValue()-startrow.intValue());
        return usersMapper.getUsersByParentOrgID(params);
    }

    public List<Users> searchUsersByKeyword(String keyword,BigDecimal customer,List orgidList,BigDecimal startrow,BigDecimal endrow) {
        Map params = new HashMap();
        params.put("SITEID",customer);
        params.put("KEYWORD",keyword);
        params.put("IDLIST",orgidList);
        params.put("BEGINROW",startrow);
        params.put("ENDROW",endrow);
        params.put("PAGESIZE",endrow.intValue()-startrow.intValue());
        return usersMapper.searchUsersByKeyword(params);
    }

    public int searchUsersCountByKeyword(String keyword,BigDecimal customer,List orgidList) {
        Map params = new HashMap();
        params.put("SITEID",customer);
        params.put("KEYWORD",keyword);
        params.put("IDLIST",orgidList);

        int retval = usersMapper.searchUsersCountByKeyword(params);

        return retval;
    }

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
    public Map RegisterUserInfo(List user,List<Template> templates,int TemplateNum,String contactor,int samsiteid) {
        int reg_dns_flag = 0;
        int errcode = 0;
        int siteinfo_error = 0;
        int siteipinfo_error = 0;
        int column_error = 0;
        int org_error = 0;
        int company_error = 0;
        int user_error = 0;
        int memberright_error = 0;
        int models_error = 0;
        int queue_error = 0;
        String www_ip = null;
        String ftp_ip = null;
        String docroot = null;
        String ftpuser = null;
        String ftppasswd = null;
        int flag = 0;
        Siteinfo siteInfo = null;
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrcode(0);
        errorMessage.setErrmsg("用户注册执行成功");
        errorMessage.setModelname("用户注册");

        Users theuser = (Users)user.get(0);
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
            //Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            //jdbc:mysql://localhost:3306/cms?useUnicode=true&characterEncoding=utf8
            //mysql_conn = DriverManager.getConnection("jdbc:mysql://116.90.87.233:3306/dns?characterEncoding=gbk", "dns", "1qaz2wsx");

            ApplicationContext appContext = SpringInit.getApplicationContext();
            BoneCPDataSource dataSource = (BoneCPDataSource)appContext.getBean("mySqlDS");
            mysql_conn = DataSourceUtils.getConnection(dataSource);

            mysql_conn.setAutoCommit(false);
            mysql_pstmt = mysql_conn.prepareStatement(SQL_INSERT_DNS_RECORD);
            mysql_pstmt.setString(1, "show.caiwuzi.com");
            mysql_pstmt.setString(2, theuser.getUSERID());
            mysql_pstmt.setString(3, "A");
            mysql_pstmt.setString(4, www_ip);
            mysql_pstmt.setInt(5, 86400);
            mysql_pstmt.setNull(6, Types.VARCHAR);
            mysql_pstmt.setInt(7, 14400);
            mysql_pstmt.setInt(8, 10);
            mysql_pstmt.setInt(9, 604800);
            mysql_pstmt.setInt(10, 800);
            mysql_pstmt.setNull(11, Types.BIGINT);
            mysql_pstmt.setString(12, "show.caiwuzi.com");
            mysql_pstmt.setString(13, "ns1.show.caiwuzi.com");
            mysql_pstmt.setString(14, "ns2.show.caiwuzi.com");
            mysql_pstmt.setInt(15, 0);
            mysql_pstmt.executeUpdate();
            mysql_pstmt.close();
            mysql_conn.commit();
            mysql_conn.close();
        } catch (Exception sqlexp) {
            sqlexp.printStackTrace();
            reg_dns_flag = -1;
            errcode = -11;
            errorMessage.setErrcode(errcode);
            errorMessage.setErrmsg("增加DNS记录出现错误");
            errorMessage.setModelname("用户注册");
            System.out.println("增加DNS记录出现错误");
        }

        if (reg_dns_flag == 0 && errcode == 0) {
            //设置站点信息
            //try{
            System.out.println("Service userid=" + theuser.getUSERID() + "====" + samsiteid + "====" + TemplateNum);
            BigDecimal siteid =siteinfoMapper.getSiteinfoMainKey();
            siteInfo = new Siteinfo();
            siteInfo.setSITENAME(theuser.getUSERID() + ".show.caiwuzi.com");
            siteInfo.setSITEIP(www_ip);
            siteInfo.setIMAGESDIR((short) 0);
            siteInfo.setCSSJSDIR((short) 0);
            siteInfo.setTCFLAG((short) 0);
            siteInfo.setWAPFLAG((short) 0);
            siteInfo.setPUBFLAG((short) 0);
            siteInfo.setBINDFLAG((short) 1);
            siteInfo.setBEREFERED((short) 0);
            siteInfo.setCOPYCOLUMN((short) 0);
            siteInfo.setBECOPYCOLUMN((short) 0);
            siteInfo.setPUSHARTICLE((short) 0);
            siteInfo.setMOVEARTICLE((short) 0);
            siteInfo.setCREATEDATE(now);
            siteInfo.setSITEVALID(BigDecimal.valueOf(1));
            siteInfo.setSAMSITEID(BigDecimal.valueOf(samsiteid));
            siteInfo.setENCODEFLAG(BigDecimal.valueOf(1));
            siteInfo.setSHARETEMPLATENUM(BigDecimal.valueOf(TemplateNum));
            siteInfo.setSITEID(siteid);
            siteinfo_error = siteinfoMapper.insert(siteInfo);

            //设置站点发布主机信息
            if (siteinfo_error > 0) {
                Siteipinfo siteipinfo = new Siteipinfo();
                siteipinfo.setSITEID(siteid);
                siteipinfo.setSITEIP(ftp_ip);
                siteipinfo.setSITENAME(theuser.getUSERID() + ".show.caiwuzi.com");
                siteipinfo.setDOCPATH(docroot + "/" + theuser.getUSERID());
                siteipinfo.setFTPUSER(ftpuser);
                siteipinfo.setFTPPASSWD(ftppasswd);
                siteipinfo.setFTPTYPE(BigDecimal.valueOf(flag));
                siteipinfo.setPUBLISHWAY((short)0);
                siteipinfo.setSTATUS((short)1);
                siteipinfo.setID(siteipinfoMapper.getSiteipinfoMainKey());
                siteipinfo_error = siteipinfoMapper.insert(siteipinfo);
            } else {
                errorMessage.setErrcode(-12);
                errorMessage.setErrmsg("增加客户虚拟站点记录出现错误");
                errorMessage.setModelname("用户注册");
            }

            //设置站点根栏目
            BigDecimal rootColumnID = columnMapper.getColumnMainKey();
            if (siteipinfo_error > 0) {
                Column column = new Column();
                column.setID(rootColumnID);
                column.setSITEID(siteid);
                column.setPARENTID(BigDecimal.valueOf(0));
                column.setORDERID(BigDecimal.valueOf(1));
                column.setCNAME(theuser.getUSERID() + ".show.caiwuzi.com");
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
                column.setARCHIVINGRULES((short)0);
                column.setUSEARTICLETYPE((short)0);
                column.setISTYPE((short)0);
                column.setPUBLICFLAG(BigDecimal.valueOf(0));
                column.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                column.setLASTUPDATED(new Timestamp(System.currentTimeMillis()));
                column_error = columnMapper.insert(column);
            } else {
                errorMessage.setErrcode(-13);
                errorMessage.setErrmsg("增加站点发布主机记录出现错误");
                errorMessage.setModelname("用户注册");
            }

            //向组织架构表插入数据
            BigDecimal orgid = usersMapper.getMainKey();
            if (column_error > 0) {
                Organization organization = new Organization();
                organization.setID(orgid);
                organization.setCOTYPE(BigDecimal.valueOf(1));
                organization.setCUSTOMERID(siteid);
                organization.setNAME(theuser.getCOMPANY());
                organization.setPARENT(BigDecimal.valueOf(-1));
                organization.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                organization.setLASTUPDATE(new Timestamp(System.currentTimeMillis()));
                organization.setCREATEUSER(theuser.getUSERID());
                organization.setUPDATEUSER(theuser.getUSERID());
                organization.setISLEAF((short)0);
                organization.setLLEVEL(BigDecimal.valueOf(1));
                organization.setORDERID(BigDecimal.valueOf(1));
                org_error = organizationMapper.insert(organization);
            } else {
                errorMessage.setErrcode(-14);
                errorMessage.setErrmsg("增加站点的根栏目记录出现错误");
                errorMessage.setModelname("用户注册");
            }

            //向tbl_companyinfo表增加注册信息
            BigDecimal companyid = companyinfoMapper.getCompanyMainKey();
            if (org_error > 0) {
                Companyinfo companyinfo = new Companyinfo();
                companyinfo.setCOMPANYNAME(theuser.getCOMPANY());
                companyinfo.setCOMPANYADDRESS(theuser.getADDRESS());
                companyinfo.setCOMPANYPHONE(theuser.getMPHONE());
                companyinfo.setCOMPANYEMAIL(theuser.getEMAIL());
                companyinfo.setCREATEUSER(theuser.getUSERID());
                companyinfo.setSAMSITEID(BigDecimal.valueOf(samsiteid));
                companyinfo.setCONTACTOR(contactor);
                companyinfo.setCOUNTRY(theuser.getCOUNTRY());
                companyinfo.setPROVINCE(theuser.getPROVINCE());
                companyinfo.setCITY(theuser.getCITY());                                        //用户所在市
                companyinfo.setZONE(theuser.getAREA());                                        //用户所在县
                companyinfo.setTOWN(theuser.getJIEDAO());                                    //用户所在乡镇、街道
                companyinfo.setVILLAGE(theuser.getSHEQU());                                      //用户所在村、社区
                companyinfo.setPUBLISHFLAG(BigDecimal.valueOf(0));
                companyinfo.setSTATUS(BigDecimal.valueOf(0));
                companyinfo.setCOMPANYCLASSID(BigDecimal.valueOf(2963));
                companyinfo.setCLASSIFICATION("企业信息展示平台");
                companyinfo.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                companyinfo.setUPDATEDATE(new Timestamp(System.currentTimeMillis()));
                companyinfo.setSITEID(siteid);
                companyinfo.setORGID(orgid);
                companyinfo.setID(companyid);
                company_error = companyinfoMapper.insert(companyinfo);
            } else {
                errorMessage.setErrcode(-15);
                errorMessage.setErrmsg("创建组织架构记录出现错误");
                errorMessage.setModelname("用户注册");
            }

            //设置用户信息
            System.out.println("company_error==" + column_error);
            if (company_error>0) {
                try {
                    BigDecimal id = usersMapper.getMainKey();
                    Users myuser = new Users();
                    myuser.setID(id);
                    myuser.setUSERID(theuser.getUSERID());
                    myuser.setSITEID(siteid);
                    myuser.setORGID(orgid);
                    myuser.setCOMPANYID(companyid);
                    myuser.setDEPTID(BigDecimal.valueOf(0));
                    myuser.setUSERPWD(Encrypt.md5(theuser.getUSERPWD().getBytes()));
                    myuser.setNICKNAME(contactor);                                         //用户联系人信息
                    myuser.setMPHONE(theuser.getMPHONE());                                    //用户的手机号码
                    myuser.setEMAIL(theuser.getEMAIL());                                      //用户的电子邮件地址
                    myuser.setCOMPANY(theuser.getCOMPANY());
                    myuser.setCOUNTRY(theuser.getCOUNTRY());                                  //用户所在国家
                    myuser.setPROVINCE(theuser.getPROVINCE());                                //用户所在省
                    myuser.setCITY(theuser.getCITY());                                        //用户所在市
                    myuser.setAREA(theuser.getAREA());                                        //用户所在县
                    myuser.setJIEDAO(theuser.getJIEDAO());                                    //用户所在乡镇、街道
                    myuser.setSHEQU(theuser.getSHEQU());                                      //用户所在村、社区
                    myuser.setADDRESS(theuser.getADDRESS());                                  //用户详细地址
                    myuser.setPOSTCODE(theuser.getPOSTCODE());                                //用户的邮政编码
                    myuser.setUSERTYPE(theuser.getUSERTYPE());                                //用户类型 0企业用户  1-个人用户
                    myuser.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    user_error = usersMapper.insert(myuser);
                    System.out.println("user is created");
                } catch(Exception exp) {
                    System.out.println("user failed");
                    exp.printStackTrace();
                }
            } else {
                System.out.println("user failed");
                errorMessage.setErrcode(-16);
                errorMessage.setErrmsg("创建公司信息记录出现错误");
                errorMessage.setModelname("用户注册");
            }

            //设置用户的权限信息
            System.out.println("user_error==" + user_error);
            if (user_error >0) {
                MembersRights membersRights = new MembersRights();
                membersRights.setUSERID(theuser.getUSERID());
                membersRights.setCOLUMNID(BigDecimal.valueOf(0));
                membersRights.setRIGHTID((short)54);
                memberright_error = membersRightsMapper.insert(membersRights);
            } else {
                errorMessage.setErrcode(-15);
                errorMessage.setErrmsg("创建用户记录出现错误");
                errorMessage.setModelname("用户注册");
            }


            //定义存储发布作业的列表变量queueList和作业变量publish
            List<PublishQueue> queueList = new ArrayList();
            PublishQueue publish = null;
            if (memberright_error > 0) {
                for(int ii=0;ii<templates.size();ii++) {
                    Template template = templates.get(ii);
                    Template model = new Template();
                    model.setSITEID(siteid);
                    model.setCOLUMNID(template.getCOLUMNID());
                    model.setCHNAME(template.getCHNAME());
                    model.setTEMPLATENAME(template.getTEMPLATENAME());
                    model.setISARTICLE((short)3);
                    model.setCONTENT("");
                    model.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                    model.setLASTUPDATED(new Timestamp(System.currentTimeMillis()));
                    model.setEDITOR(theuser.getUSERID());
                    model.setCREATOR(theuser.getUSERID());
                    model.setSTATUS((short)0);
                    model.setLOCKSTATUS((short)0);
                    model.setDEFAULTTEMPLATE((short)0);
                    model.setREFERMODELID(BigDecimal.valueOf(0));
                    model.setISINCLUDED((short)0);
                    model.setTEMPNUM(BigDecimal.valueOf(TemplateNum));
                    model.setID(templateMapper.getTemplateMainKey());
                    models_error = models_error + templateMapper.insert(model);

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
                }
            } else {
                errorMessage.setErrcode(-17);
                errorMessage.setErrmsg("创建公司基本信息记录出现错误");
                errorMessage.setModelname("用户注册");
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

            if (models_error > 0) {
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
                        queue_error = publishQueueMapper.insert(publish);
                    }
                }
            } else {
                errorMessage.setErrcode(-18);
                errorMessage.setErrmsg("创建公司网站模板记录出现错误");
                errorMessage.setModelname("用户注册");
            }
        }

        Map result = new HashMap();
        result.put("site",siteInfo);
        result.put("error",errorMessage);

        return result;
    }

}
