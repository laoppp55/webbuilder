package com.bizwink.dubboservice.service;

import com.bizwink.po.Siteinfo;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: perter.song
 * Date: 16-4-5
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 */
public interface SiteinfoService {
    public Siteinfo selectBySitename(String sitename);

    public Siteinfo getSiteinfoByID(BigDecimal id);
}
