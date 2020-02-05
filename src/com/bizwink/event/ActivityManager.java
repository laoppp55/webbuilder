/**
 * ActivityManager.java
 */

package com.bizwink.event;

import java.util.*;

import com.bizwink.cms.util.*;

public interface ActivityManager {
    List getActivities(int belongType, int belongID, int closed, int order) throws  ActivityException;
    int getActivityCount(int belongType, int belongID, int closed) throws  ActivityException;
    List getActivities(int belongType, int belongID, int start, int count, int closed, int order) throws  ActivityException;
}
