package com.bizwink.bbs.service;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-7
 * Time: 下午9:36
 * To change this template use File | Settings | File Templates.
 */
import com.bizwink.bbs.domain.BBS;
import com.bizwink.bbs.persistence.BBSMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BBSService {
    @Autowired
    private BBSMapper bbsMapper;

    public int createBBS() {
        bbsMapper.createBBS();
        return 0;
    }

    public int deleteBBS(int bbsid) {
        bbsMapper.deleteBBS(bbsid);
        return 0;
    }

    public List<BBS> getAllBBS() {
        return bbsMapper.getAllBBS();
    }
}
