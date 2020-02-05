/**
 * EventManager.java
 */

package com.bizwink.event;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.util.*;

public interface EventManager {

    void create(Event event) throws EventException;

    void update(Event event) throws EventException;

    void delete(Auth authToken, int eventID) throws EventException;

    void remove(int eventID) throws EventException;

    void convertLeadEvent(int leadID, int accountID, int contactID, int oppID) throws EventException;

    Event getEvent(int eventID) throws  EventException;

    Vector getEvents(int ownerID) throws EventException;

    Vector getEvents_Next7Days(int ownerID, String today) throws Exception;

    public Vector getEventsByDate(int ownerID, String dueDate);

    public Vector getEvents_Tomorrow(int ownerID, String dueDate);

    public Vector getEvents_ByMonth(int ownerID, String dueDate);

    //void newFromRequest(HttpServletRequest request, Event event);

    //boolean editFromRequest(Auth authToken, HttpServletRequest request, int operation, Event event, SalesErrors errors);
}
