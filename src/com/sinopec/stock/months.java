package com.sinopec.stock;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-3-18
 * Time: 22:13:52
 * To change this template use File | Settings | File Templates.
 */
public class months {
    private int start_year;
    private int start_month;
    private int start_day;
    private int end_year;
    private int end_month;
    private int end_day;

    public void setStartYear(int year) {
        this.start_year = year;
    }

    public int getStartYear() {
        return start_year;
    }

    public void setStartMonth(int month) {
        this.start_month = month;
    }

    public int getStartMonth() {
        return start_month;
    }

    public void setStartDay(int day) {
        this.start_day = day;
    }

    public int getStartDay() {
        return start_day;
    }

    public void setEndYear(int year) {
        this.end_year = year;
    }

    public int getEndYear() {
        return end_year;
    }

    public void setEndMonth(int month) {
        this.end_month = month;
    }

    public int getEndMonth() {
        return end_month;
    }

    public void setEndDay(int day) {
        this.end_day = day;
    }

    public int getEndDay() {
        return end_day;
    }
}
