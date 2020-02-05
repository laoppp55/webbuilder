/**
 * IMarkerManager.java
 */
package com.bizwink.webtrend;

import java.util.*;
import java.sql.*;
import com.bizwink.cms.util.*;
import com.bizwink.webtrend.*;

/**
 * Title:        cms
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      bizwink
 * @author Peter Song
 * @version 1.0
 */

public interface IDataServerManager {
    List getData() throws com.bizwink.webtrend.WebtrendException;
}