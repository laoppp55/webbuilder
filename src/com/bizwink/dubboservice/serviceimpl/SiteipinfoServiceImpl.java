package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.SiteipinfoService;
import com.bizwink.persistence.SiteipinfoMapper;
import com.bizwink.po.Siteipinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by petersong on 16-6-5.
 */
@Service
public class SiteipinfoServiceImpl implements SiteipinfoService{
    @Autowired
    private SiteipinfoMapper siteipinfoMapper;

    public List<Siteipinfo> getSiteipinfoBySiteid(BigDecimal siteid) {
        return siteipinfoMapper.selectBySiteid(siteid);
    }
}
