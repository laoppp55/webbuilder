package com.bizwink.dubboservice.service.Users;

import com.bizwink.error.ErrorMessage;
import com.bizwink.po.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 16-1-23.
 */
public interface UsersService {
    public Users getByUserId(String uid);

    public int CreateGeneralAccount(Users user);

    public int UpdateGeneralAccount(Users user);

    public int updateBasicInfo(String infotype,Users user);       //同时更新用户表tbl_members和公司表tbl_companyinfo中的信息

    public int updateUserInfo(Users user);                        //单独更新用户表中某些字段的信息

    public int DeleteGeneralAccount(String userid);

    public int StopGeneralAccount(Users user);

    public List<Users> getTheUsersByArea(Map<String, Object> param);

    public Integer countUsers(Map<String, Object> param);

    public List<Companyinfo> getCompanyInfoList(Map<String, Object> param);

    public Integer countCompany (Map<String, Object> param);

    public Users getBySiteId(int siteId);

    public Integer getxcSiteIdByVillage(String Village);

    public Companyinfo getCompanyinfoBySiteid(int siteId);

    public int CheckUsername(Map<String, Object> param);

    public int CheckEmail(Map<String, Object> param);

    public int CheckMphone(Map<String, Object> param);

    public Users getPredepositAndScore(String userid);

    public String RegisterNjlUserInfo(List njluser,int TemplateNum,String contactor,int samsiteid);

    public List<CmsTemplate> getTemplates(BigDecimal siteid);

    public CmsTemplate getTemplate(BigDecimal siteid,BigDecimal tempno);

    public Siteinfo getSiteinfo(BigDecimal siteid);

    public Siteinfo getSiteinfo(String sitename);

    public Users selectByPrimaryKey(String username);

    public Users selectByEmail(String email);

    public Users selectByMphone(String mphone);

    public int getRootColumnIdBySiteid(BigDecimal siteid);
}