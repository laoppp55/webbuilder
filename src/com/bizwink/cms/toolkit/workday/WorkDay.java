package com.bizwink.cms.toolkit.workday;

public class WorkDay
{
    public WorkDay()
    {

    }
    private String days;
    private int siteid;
    private int workdaysflag;
    private int dayofmonth;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getWorkdaysflag() {
        return workdaysflag;
    }

    public void setWorkdaysflag(int workdaysflag) {
        this.workdaysflag = workdaysflag;
    }

    public int getDayofmonth() {
        return dayofmonth;
    }

    public void setDayofmonth(int dayofmonth) {
        this.dayofmonth = dayofmonth;
    }
}