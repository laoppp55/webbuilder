package com.bizwink.stockinfo;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.sql.*;

public class crudeoil {
  private String name;
  private String unit;
  private float c_price;
  private float change;
  private Timestamp trade_time;

  public crudeoil() {

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
  public String getUnit()
  {
      return unit;
  }

  public void setUnit(String unit)
  {
      this.unit = unit;
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

  //for trade_time
  public float getCprice()
  {
      return c_price;
  }

  public void setCprice(float c_price)
  {
      this.c_price = c_price;
  }

  public float getChange()
  {
      return change;
  }

  public void setChange(float change)
  {
      this.change = change;
  }
}