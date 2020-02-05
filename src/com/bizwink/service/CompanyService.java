package com.bizwink.service;

import com.bizwink.persistence.CompanyinfoMapper;
import com.bizwink.po.Companyinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-11-1.
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyinfoMapper companyinfoMapper;

    public List<Companyinfo> getCompanyinfo(BigDecimal siteid, BigDecimal orgid) {
        Map params = new HashMap();
        params.put("SITEID",siteid);
        params.put("ORGID",orgid);
        return companyinfoMapper.getMainCompanyInfosByOrgid(params);
    }

    public Companyinfo getCompanyinfoByCompid(BigDecimal companyid) {
        return companyinfoMapper.selectByPrimaryKey(companyid);
    }

}
