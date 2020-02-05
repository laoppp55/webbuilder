package com.bizwink.stockinfo;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface ISpiderManager {

    public void insertStock(String name, String nameid, String danwei, String[][] stock) throws SpiderException;

    public void insertSS_ClosePrice(String[][] stock, String stockcode,int maxid) throws SpiderException;

    public void insertStockcode(String companyname,String stockcode) throws SpiderException;

    public void updateStock(String[][] stock, String stockcode) throws SpiderException;

    public List getSpiderInfo() throws SpiderException;

    public List getSpiderInfo(String stockcode) throws SpiderException;

    public StockInfo getSpiderInfoBycode(String stockcode) throws SpiderException;

    public List getSstockbaseInfo() throws SpiderException;

    public boolean getExist(String stockcode,StockInfo stock) throws SpiderException;

    public boolean getClosePriceExist(String stockcode) throws SpiderException;

    public void insertClosePrice(String[][] stock, String stockcode, int nameid) throws SpiderException;

    public int getMaxClosePriceID(String stockcode) throws SpiderException;

    public String getMaxDate(String stockcode) throws SpiderException;

    public void deleteYesterdayData(String stockcode) throws SpiderException;

    public void insertOilDate(crudeoil co, java.sql.Date date) throws SpiderException;

    public boolean getOilExist(String oilname, java.sql.Date date) throws SpiderException;

    public void updateOilDate(crudeoil co, java.sql.Date date) throws SpiderException;

    int getStockpriceCount(java.sql.Date StartDate, java.sql.Date EndDate, String stockcode);

    List getStockprices(java.sql.Date StartDate, java.sql.Date EndDate, String stockcode, int startIndex, int numResults);

    int getStockpriceCountByWeeks(java.sql.Date StartDate, java.sql.Date EndDate, String stockcode);

    List getStockpricesByWeeks(java.sql.Date StartDate, java.sql.Date EndDate, String stockCode, int startIndex, int numResults);

    //int getStockpriceCountByMonths(Date StartDate, Date EndDate, String stockcode);

    List getStockpriceCountByMonths(java.sql.Date StartDate, java.sql.Date EndDate, String stockcode);

    long getStockpriceFormOraAndMysql(String stockcode);
}