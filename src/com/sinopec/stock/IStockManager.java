package com.sinopec.stock;

import java.util.List;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-3-5
 * Time: 16:51:50
 */
public interface IStockManager {

    List getStockNameInfo(int idflag);

    int getStockpriceCount(Date StartDate, Date EndDate, String stockcode);

    List getStockprices(Date StartDate, Date EndDate, String stockcode, int startIndex, int numResults);

    Stock getClosePrice(String stockcode);

    int getStockpriceCountByWeeks(Date StartDate, Date EndDate, String stockcode);

    List getStockpricesByWeeks(Date StartDate, Date EndDate, String stockCode, int startIndex, int numResults);

    //int getStockpriceCountByMonths(Date StartDate, Date EndDate, String stockcode);

    List getStockpriceCountByMonths(Date StartDate, Date EndDate, String stockcode);
}
