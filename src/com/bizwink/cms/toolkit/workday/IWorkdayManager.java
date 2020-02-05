package com.bizwink.cms.toolkit.workday;

import java.util.List;

public interface IWorkdayManager{

    void createWorkdayInfo(String Month, List workDaysList);

    List getWorkDaysInfoForMonth(String month, int siteid);
}