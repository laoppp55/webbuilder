package com.bizwink.dubboservice.service;

import com.bizwink.po.Siteipinfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by petersong on 16-6-5.
 */
public interface SiteipinfoService {
    List<Siteipinfo> getSiteipinfoBySiteid(BigDecimal siteid);
}
