package com.bizwink.stockinfo;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class stockBaseinfo {
  private int id;
  private String jysname;
  private String ticker;
  private String currency;
  private String companyname;

  public stockBaseinfo() {

  }

  //for name
  public int getID()
  {
      return id;
  }

  public void setID(int id)
  {
      this.id = id;
  }

  //for last_trade
  public String getJysName()
  {
      return jysname;
  }

  public void setJysName(String name)
  {
      this.jysname = name;
  }

  //for trade_time
  public String getTicker()
  {
      return ticker;
  }

  public void setTicker(String ticker)
  {
      this.ticker = ticker;
  }

  //for change
  public String getCurrency()
  {
      return currency;
  }

  public void setCurrency(String currency)
  {
      this.currency = currency;
  }

  //for change
  public String getComponyName()
  {
      return companyname;
  }

  public void setCompanyname(String companyname)
  {
      this.companyname = companyname;
  }
}