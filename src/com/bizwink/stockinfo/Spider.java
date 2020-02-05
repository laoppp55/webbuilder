//Compile by dzq.June 19,2003
// Source File Name:   Subscriber.java
package com.bizwink.stockinfo;

import java.sql.Date;
import java.sql.Timestamp;

public class Spider
{
  private String name;
  private String last_trade;
  private Timestamp trade_time;
  private String change;
  private String Prev_Close;
  private String open_money;
  private String bid;
  private String ask;
  private String target_est;
  private String day_range;
  private String wk_range;
  private String volume;
  private String Avg_Vol;
  private String Market_Cap;
  private String p_e;
  private String Div_Yield;
  private Timestamp datetime;
  private String nameid;
  private String eps;
  private String danwei;

  public Spider()
  {
  }

  //for name
  public String getName()
  {
      return name;
  }

  public void setName(String name)
  {
      this.name = name;
  }

  //for last_trade
  public String getLast_trade()
  {
      return last_trade;
  }

  public void setLast_trade(String last_trade)
  {
      this.last_trade = last_trade;
  }

  //for trade_time
  public Timestamp getTrade_time()
  {
      return trade_time;
  }

  public void setTrade_time(Timestamp trade_time)
  {
      this.trade_time = trade_time;
  }

  //for change
  public String getChange()
  {
      return change;
  }

  public void setChange(String change)
  {
      this.change = change;
  }

  //for Prev_Close
  public String getPrev_Close()
  {
      return Prev_Close;
  }

  public void setPrev_Close(String Prev_Close)
  {
      this.Prev_Close = Prev_Close;
  }

  //for open_money
  public String getOpen_money()
  {
      return open_money;
  }

  public void setOpen_money(String open_money)
  {
      this.open_money = open_money;
  }

  //for email
  public String getBid()
  {
      return bid;
  }

  public void setBid(String bid)
  {
      this.bid = bid;
  }


  //for datetime
  public Timestamp getDatetime()
  {
      return datetime;
  }

  public void setDatetime(Timestamp datetime)
  {
      this.datetime = datetime;
  }

  //for ask
  public String getAsk()
  {
      return ask;
  }

  public void setAsk(String ask)
  {
      this.ask = ask;
  }

  //for target_est
  public String getTarget_est()
  {
      return target_est;
  }

  public void setTarget_est(String target_est)
  {
      this.target_est = target_est;
  }

  //for day_range
  public String getDay_range()
  {
      return day_range;
  }

  public void setDay_range(String day_range)
  {
      this.day_range = day_range;
  }

  //for wk_range
  public String getWk_range()
  {
      return wk_range;
  }

  public void setWk_range(String wk_range)
  {
      this.wk_range = wk_range;
  }

  //for volume
  public String getVolume()
  {
      return volume;
  }

  public void setVolume(String volume)
  {
      this.volume = volume;
  }

  //for Avg_Vol
  public String getAvg_Vol()
  {
      return Avg_Vol;
  }

  public void setAvg_Vol(String Avg_Vol)
  {
      this.Avg_Vol = Avg_Vol;
  }

  //for Market_Cap
  public String getMarket_Cap()
  {
      return Market_Cap;
  }

  public void setMarket_Cap(String Market_Cap)
  {
      this.Market_Cap = Market_Cap;
  }

  //for p_e
  public String getP_E()
  {
      return p_e;
  }

  public void setP_E(String p_e)
  {
      this.p_e = p_e;
  }


  //for Div_Yield
  public String getDiv_Yield()
  {
      return Div_Yield;
  }

  public void setDiv_Yield(String Div_Yield)
  {
      this.Div_Yield = Div_Yield;
  }

  //for eps
  public String getEps()
  {
      return eps;
  }

  public void setEps(String eps)
  {
      this.eps = eps;
  }

  //for nameid
  public String getNameid()
  {
      return nameid;
  }

  public void setNameid(String nameid)
  {
      this.nameid = nameid;
  }

  //for danwei
  public String getDanWei()
  {
      return danwei;
  }

  public void setDanWei(String danwei)
  {
      this.danwei = danwei;
  }
}

