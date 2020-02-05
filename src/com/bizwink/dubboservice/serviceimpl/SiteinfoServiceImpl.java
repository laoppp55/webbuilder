package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.SiteinfoService;
import com.bizwink.persistence.SiteinfoMapper;
import com.bizwink.po.Siteinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: perter.song
 * Date: 16-4-5
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SiteinfoServiceImpl implements SiteinfoService{
    @Autowired
    private SiteinfoMapper siteinfoMapper;

    public Siteinfo selectBySitename(String sitename) {
         return siteinfoMapper.selectBySitename(sitename);
    }

    public Siteinfo getSiteinfoByID(BigDecimal siteid) {
        return siteinfoMapper.selectByPrimaryKey(siteid);
    }
}
