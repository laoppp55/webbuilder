package com.bizwink.dubboservice.service;

import com.bizwink.po.Companyinfo;

import java.math.BigDecimal;

/**
 * Created by lenovo on 16-5-20.
 */
public interface CompanyService {
    public Companyinfo getCompany(BigDecimal siteid);

}
