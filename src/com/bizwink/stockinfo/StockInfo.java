package com.bizwink.stockinfo;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-2-18
 * Time: 上午9:12
 * To change this template use File | Settings | File Templates.
 */
public class StockInfo {
    private int id;                       //主键ID
    private String name;                  //股票名称
    private float last_trade;             //当前交易价
    private float percentpricechange;     //交易价变化量百分比，和前收盘比
    private Timestamp trade_time;         //当前交易时间
    private float thechange;              //交易价变化量，和前收盘比
    private float prev_close;             //前收盘价
    private float open_money;             //今日开盘价
    private float bid;                    //当前最高应价
    private float ask;                    //当前最高询价
    private float target_est;             //本年最高估算价
    private float day_range_low;          //目前为止当天最低价
    private float day_range_high;         //目前为止当天最高价
    private float wk52_range_low;         //52周最低价
    private float wk52_range_high;        //52周最高价
    private long volume;                  //股票交易量
    private long m3_avg_vol;              //3个月内平均交易量
    private String market_cap;            //股票市值
    private float p_e;                    //股票市盈率
    private float eps;                    //每股收益
    private String div_yield;             //股息收益率
    private String nameid;                //股票ID
    private String danwei;                //股价单位
    private Timestamp thedate;            //信息采集时间
    private float beta;                   //股票贝塔系数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLast_trade() {
        return last_trade;
    }

    public void setLast_trade(float last_trade) {
        this.last_trade = last_trade;
    }

    public float getPercentpricechange() {
        return percentpricechange;
    }

    public void setPercentpricechange(float percentpricechange) {
        this.percentpricechange = percentpricechange;
    }

    public Timestamp getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(Timestamp trade_time) {
        this.trade_time = trade_time;
    }

    public float getThechange() {
        return thechange;
    }

    public void setThechange(float thechange) {
        this.thechange = thechange;
    }

    public float getPrev_close() {
        return prev_close;
    }

    public void setPrev_close(float prev_close) {
        this.prev_close = prev_close;
    }

    public float getOpen_money() {
        return open_money;
    }

    public void setOpen_money(float open_money) {
        this.open_money = open_money;
    }

    public float getBid() {
        return bid;
    }

    public void setBid(float bid) {
        this.bid = bid;
    }

    public float getAsk() {
        return ask;
    }

    public void setAsk(float ask) {
        this.ask = ask;
    }

    public float getTarget_est() {
        return target_est;
    }

    public void setTarget_est(float target_est) {
        this.target_est = target_est;
    }

    public float getDay_range_low() {
        return day_range_low;
    }

    public void setDay_range_low(float day_range_low) {
        this.day_range_low = day_range_low;
    }

    public float getDay_range_high() {
        return day_range_high;
    }

    public void setDay_range_high(float day_range_high) {
        this.day_range_high = day_range_high;
    }

    public float getWk52_range_low() {
        return wk52_range_low;
    }

    public void setWk52_range_low(float wk52_range_low) {
        this.wk52_range_low = wk52_range_low;
    }

    public float getWk52_range_high() {
        return wk52_range_high;
    }

    public void setWk52_range_high(float wk52_range_high) {
        this.wk52_range_high = wk52_range_high;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getM3_avg_vol() {
        return m3_avg_vol;
    }

    public void setM3_avg_vol(long m3_avg_vol) {
        this.m3_avg_vol = m3_avg_vol;
    }

    public String getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(String market_cap) {
        this.market_cap = market_cap;
    }

    public float getP_e() {
        return p_e;
    }

    public void setP_e(float p_e) {
        this.p_e = p_e;
    }

    public float getEps() {
        return eps;
    }

    public void setEps(float eps) {
        this.eps = eps;
    }

    public String getDiv_yield() {
        return div_yield;
    }

    public void setDiv_yield(String div_yield) {
        this.div_yield = div_yield;
    }

    public String getNameid() {
        return nameid;
    }

    public void setNameid(String nameid) {
        this.nameid = nameid;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }

    public Timestamp getThedate() {
        return thedate;
    }

    public void setThedate(Timestamp thedate) {
        this.thedate = thedate;
    }

    public float getBeta() {
        return beta;
    }

    public void setBeta(float beta) {
        this.beta = beta;
    }
}
