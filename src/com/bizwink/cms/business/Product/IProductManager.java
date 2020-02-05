package com.bizwink.cms.business.Product;

import java.util.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.audit.*;
import java.sql.*;

public interface IProductManager
{
    List getProductList()throws ProductException;

    Product getAProduct(int articleid) throws ProductException;

    int checkStatus(int pid) throws ProductException;

    int checkStockForBBN(int pid) throws ProductException;
}
