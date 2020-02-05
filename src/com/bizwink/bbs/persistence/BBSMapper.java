package com.bizwink.bbs.persistence;

import com.bizwink.bbs.domain.BBS;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-7
 * Time: 下午9:31
 * To change this template use File | Settings | File Templates.
 */
public interface BBSMapper {
    int createBBS();
    int deleteBBS(int bbsid);
    List<BBS> getAllBBS();
}
