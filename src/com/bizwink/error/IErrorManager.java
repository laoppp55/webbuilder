package com.bizwink.error;

import java.util.*;
import java.sql.*;
import com.bizwink.cms.util.*;
import java.io.*;
/**
 * Title:        IErrorManager.java
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      bizwink
 * @author EricDu
 * @version 1.0
 */

public interface IErrorManager {

    abstract List reportPerDay(String theDate, String id) throws ErrorException;

    abstract List getCurrentErrorList(String theDate, int startIndex, int numResult, String id) throws ErrorException;
}