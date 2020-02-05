package com.bizwink.cms.bjhqfw.shetuan;

import java.util.List;

public interface ISheTuanManager {

    public List getAllSheTuan(String sql,int startIndex,int range);

    public int getAllSheTuanNum(String sql);

    public int createSheTuan(SheTuan st);

    public SheTuan getByIdSheTuan(int id);

    public void updateSheTuan(SheTuan st,int id);

    public void delShetuan(int id);

    public SheTuan getSysLogin(String userid,String passwd);

    public int checkSheTuanUserExist(String username);
}
