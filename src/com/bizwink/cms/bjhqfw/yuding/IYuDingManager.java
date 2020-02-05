package com.bizwink.cms.bjhqfw.yuding;

import java.sql.Timestamp;
import java.util.List;

public interface IYuDingManager {

    List getAllYuDing(Timestamp ksdate,Timestamp jsdate,int startIndex,int range);

    List getAllYuDing(String sql,int startIndex,int range);

    int getAllYuDingNum(String sql);

    int createYuDing(YuDing yd);

    YuDing getByIdYuDing(int id);

    void updateYuDing(YuDing yd,int id);

    int updateYuDing(YuDing yd);

    void shenheYuDing(YuDing yd,int id);

    void delYuDing(int id);

    int delYuDing(YuDing yd);

    void updateshenheYuDing(YuDing yd, int id);

    int getCountYuDing(String jbxinxiid,Timestamp ksdate,Timestamp jsdate);
}
