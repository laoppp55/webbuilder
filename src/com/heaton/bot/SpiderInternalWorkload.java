package com.heaton.bot;

import java.util.*;
import java.net.*;

/**
 * This class is used to maintain an internal,
 * memory based workload store for a spider. This
 * workload store will be used by default, if no
 * other is specified.
 * <p/>
 * Copyright 2001-2003 by Jeff Heaton (http://www.jeffheaton.com)
 *
 * @author Jeff Heaton
 * @version 1.2
 */
public class SpiderInternalWorkload implements IWorkloadStorable {

    /**
     * A list of complete workload items.
     */
    Hashtable complete = new Hashtable();

    /**
     * A list of waiting workload items.
     */
    Vector waiting = new Vector();

    /**
     * A list of running workload items.
     */
    Vector running = new Vector();

    /**
     * Call this method to request a URL
     * to process. This method will return
     * a WAITING URL and mark it as RUNNING.
     *
     * @return The URL that was assigned.
     */
    synchronized public jobinfo assignWorkload(int siteid) {
        jobinfo ji = new jobinfo();

        if (waiting.size() < 1)
            return null;

        String w = (String) waiting.firstElement();
        ji.setID(0);
        ji.setJoburl(w);
        if (w != null) {
            waiting.remove(w);
            running.addElement(w);
        }
        Log.log(Log.LOG_LEVEL_TRACE, "Spider workload assigned:" + w);
        return ji;
    }

    synchronized public proxySetup getProxySetting() {
        proxySetup proxy = new proxySetup();

        proxy.setProxyHost("");
        proxy.setProxyPort("");

        return proxy;
    }

    /**
     * This is an internal method used to set the status
     * of a given URL. This method will create a record
     * for the URL of one does not currently exist.
     *
     */
    synchronized public void batchAddWorkload(List urls, int siteid) {
        URL url = null;
    }

    synchronized public int getSiteID() {
        return 0;
    }

    synchronized public List getSites() {
        return null;
    }

    synchronized public siteinfo getSite() {
        return null;
    }

    synchronized public void setURL_LastUpdate(int urlid, int year, int month, int day, int siteid) {

    }

    synchronized public int updateSiteStatus(int siteid, String status) {

        return 0;
    }

    /**
     * Add a new URL to the workload, and
     * assign it a status of WAITING.
     *
     * @param url The URL to be added.
     */
    synchronized public void addWorkload(String url, int siteid) {
        if (getURLStatus(url, siteid) != IWorkloadStorable.UNKNOWN)
            return;
        waiting.addElement(url);
        Log.log(Log.LOG_LEVEL_TRACE, "Spider workload added:" + url);
    }

    /**
     * Called to mark this URL as either
     * COMPLETE or ERROR.
     *
     * @param url   The URL to complete.
     * @param error true - assign this workload a status of ERROR.
     *              false - assign this workload a status of COMPLETE.
     */
    synchronized public void completeWorkload(String url,int urlid, boolean error, int siteid,int urltype) {
        running.remove(url);

        if (error) {
            Log.log(Log.LOG_LEVEL_TRACE, "Spider workload ended in error:" + url);
            complete.put(url, "e");
        } else {
            Log.log(Log.LOG_LEVEL_TRACE, "Spider workload complete:" + url);
            complete.put(url, "c");
        }
    }

    /**
     * Get the status of a URL.
     *
     * @param url Returns either RUNNING, ERROR
     *            WAITING, or COMPLETE. If the URL
     *            does not exist in the database,
     *            the value of UNKNOWN is returned.
     * @return Returns either RUNNING,ERROR,
     *         WAITING,COMPLETE or UNKNOWN.
     */
    synchronized public char getURLStatus(String url, int siteid) {
        if (complete.get(url) != null)
            return COMPLETE;

        if (waiting.size() > 0) {
            for (Enumeration e = waiting.elements(); e.hasMoreElements();) {
                String w = (String) e.nextElement();
                if (w.equals(url))
                    return WAITING;
            }
        }

        if (running.size() > 0) {
            for (Enumeration e = running.elements(); e.hasMoreElements();) {
                String w = (String) e.nextElement();
                if (w.equals(url))
                    return RUNNING;
            }
        }

        return UNKNOWN;
    }

    /**
     * Clear the contents of the workload store.
     */
    synchronized public void clear() {
        waiting.clear();
        complete.clear();
        running.clear();
    }

  /**
   * Clear the contents of the workload store.
   */
  synchronized public void clearIndexURL()
  {
    waiting.clear();
    complete.clear();
    running.clear();
  }

    synchronized public String[] getKeywords(int siteid) {
        return null;
    }
}
