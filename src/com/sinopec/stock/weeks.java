package com.sinopec.stock;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-3-18
 * Time: 22:07:13
 * To change this template use File | Settings | File Templates.
 */
public class weeks {
    private int monday_year;
    private int monday_month;
    private int monday_day;
    private int friday_year;
    private int friday_month;
    private int friday_day;

    public void setMondayYear(int year) {
        this.monday_year = year;
    }

    public int getMondayYear() {
        return monday_year;
    }

    public void setMondayMonth(int month) {
        this.monday_month = month;
    }

    public int getMondayMonth() {
        return monday_month;
    }

    public void setMondayDay(int day) {
        this.monday_day = day;
    }

    public int getMondayDay() {
        return monday_day;
    }

    public void setFridayYear(int year) {
        this.friday_year = year;
    }

    public int getFridayYear() {
        return friday_year;
    }

    public void setFridayMonth(int month) {
        this.friday_month = month;
    }

    public int getFridayMonth() {
        return friday_month;
    }

    public void setFridayDay(int day) {
        this.friday_day = day;
    }

    public int getFridayDay() {
        return friday_day;
    }
}
