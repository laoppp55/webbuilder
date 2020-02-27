package com.bizwink.controller;

import com.bizwink.dubboservice.service.Users.UsersService;
import com.bizwink.po.Companyinfo;
import com.bizwink.po.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jhon on 2016/4/15.
 */
@Controller
@RequestMapping("/")
// 父request请求url
public class ResultListController {

    @Autowired
    private UsersService usersService;

    @RequestMapping("getResultList.do")
    @ResponseBody
    public PageInfo getResultList(String area, String keyword, Integer pageNo, String type) {
        int samSiteId = 0;
        if (type != null && !type.equals("")) {
            if (type.equals("njy"))
                samSiteId = 2991;
            if (type.equals("mlxc"))
                samSiteId = 4592;
            if(type.equals("czy"))
                samSiteId= 2992;
        }

        int pageSize = 12;
        Integer count = 0;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("samsiteid", samSiteId);
        if (area != null && !area.equals("")) {
            param.put("area", area);
        }
        try {
            keyword = URLDecoder.decode(keyword,"utf-8");
            if (keyword != null && !keyword.equals("") && !keyword.equals("请输入查询关键字")) {
                param.put("keyword", keyword);
            }

            count = usersService.countCompany(param);
            if (pageNo < 1)
                pageNo = 1;
            int startRec = (pageNo - 1) * pageSize + 1;
            int endRec = pageNo * pageSize;
            param.put("startRec", startRec);
            param.put("endRec", endRec);
            List<Companyinfo> corpInfoList = usersService.getCompanyInfoList(param);
            //for(int ii=0;ii<corpInfoList.size();ii++) {
            //    Companyinfo companyinfo = corpInfoList.get(ii);
            //    System.out.println(companyinfo.getCOMPANYNAME());
            //}
            PageInfo curPageInfo = new PageInfo();
            curPageInfo.setCount(count);
            curPageInfo.setCorpInfoList(corpInfoList);
            return curPageInfo;
        } catch (UnsupportedEncodingException exp) {
            exp.printStackTrace();
        }
        return null;
    }

    //分页信息类，可以移出来
    public class PageInfo {
        private int count;
        private  List<Companyinfo> corpInfoList;
        public int getCount() {
            return count;
        }
        public void setCount(int count) {
            this.count = count;
        }
        public List<Companyinfo> getCorpInfoList() {
            return corpInfoList;
        }
        public void setCorpInfoList(List<Companyinfo> corpInfoList) {
            this.corpInfoList = corpInfoList;
        }
    }

    //获取用户名
    @RequestMapping("getSiteInfo.do")
    @ResponseBody
    public Users getSiteInfo(int siteId){
        return  usersService.getBySiteId(siteId);
    }
    //获取村庄id
    @RequestMapping("getxc_siteId.do")
    @ResponseBody
    public Integer getxc_siteId(String  Village){
        return usersService.getxcSiteIdByVillage(Village);
    }
}
