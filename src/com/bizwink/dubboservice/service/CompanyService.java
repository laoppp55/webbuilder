package com.bizwink.dubboservice.service;

import com.bizwink.po.Companyinfo;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: perter.song
 * Date: 16-5-17
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyService {
    public Companyinfo getCompany(BigDecimal siteid);
}
