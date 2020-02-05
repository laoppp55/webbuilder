package com.bizwink.cms.bjhqfw.jiben;

import java.util.List;

public interface IJiBenManager {

    List getAllListJiBen();

    List getAllJiBen(String sql,int startIndex,int range);

    int getAllJiBenNum(String sql);

    int createJiBen(JiBen jb);

    JiBen getByIdJiBen(int id);

    void updateJiBen(JiBen jb,int id);

    void delJiBen(int id);

    int createActivity(Activity activity);

    int existInActivityByUserid(String userid,int activityid);

    List getActivitys(int start, int range);

    int getActivitysCount();

    List getUsersByActivity(int activityid,int siteid,int start, int range);

    int getUsersCountByActivity(int activityid,int siteid);
}
