package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.CompanyService;
import com.bizwink.persistence.CompanyinfoMapper;
import com.bizwink.po.Companyinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: perter.song
 * Date: 16-5-17
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CompanyServiceImpl implements CompanyService{
    @Autowired
    private CompanyinfoMapper companyinfoMapper;

   public Companyinfo getCompany(BigDecimal siteid) {
       return companyinfoMapper.getCompanyinfoBySiteid(siteid.intValue());
   }
}
