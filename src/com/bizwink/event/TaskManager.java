/**
 * TaskManager.java
 */

package com.bizwink.event;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.bizwink.cms.security.*;

import com.bizwink.cms.util.*;

public interface TaskManager {

    void create(Task task) throws TaskException;

    void update(Task task) throws TaskException;

    void delete(Auth authToken, int taskID) throws TaskException;

    void remove(int taskID) throws TaskException;

    void convertLeadTask(int leadID, int accountID, int contactID, int oppID) throws TaskException;

    Task getTask(int taskID) throws  TaskException;

     public Vector getTasks_By_Date(int ownerID, String date);

    public Vector getTask_Next7Days(int ownerID, String today);


     public Vector getTasks_AllOpen(int ownerID);

     public Vector getTasks_OverDue(int ownerID);

     Vector getTasks_ThisMonth(int ownerID, String dueDate);

     public Vector getTasks_Today(int ownerID);

     public Vector getTasks_Tomorrow(int ownerID, String today);

    //void newFromRequest(HttpServletRequest request, Task task);

    //boolean editFromRequest(Auth authToken, HttpServletRequest request, int operation, Task task, SalesErrors errors);
}
