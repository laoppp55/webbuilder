package com.heaton.bot;

import java.util.*;

/**
 * This interface defines a class that can
 * be used to store a spider's workload.
 * The Bot package currently supports two
 * different workload stores:
 * <p/>
 * SpiderInternalWorkload - Stores the
 * contents of the workload in memory.
 * <p/>
 * SpiderSQLWorkload - Stores the contents
 * of the workload in an SQL database.
 * <p/>
 * Copyright 2001-2003 by Jeff Heaton (http://www.jeffheaton.com)
 *
 * @author Jeff Heaton
 * @version 1.2
 */
public interface IWorkloadStorable {

    /**
     * A workload entry has a status of running
     * if the spider worker is opening or downloading
     * that page. This state usually goes to COMPLETE
     * or ERROR.
     */
    public static final char RUNNING = 'R';

    /**
     * Processing of this URL resulted in an
     * error.
     */
    public static final char ERROR = 'E';

    /**
     * This URL is waiting for a spider
     * worker to take it on.
     */
    public static final char WAITING = 'W';

    /**
     * This page is complete and should not
     * be redownloaded.
     */
    public static final char COMPLETE = 'C';

    /**
     * The status is unknown.
     */
    public static final char UNKNOWN = 'U';

    /**
     * Call this method to request a URL
     * to process. This method will return
     * a WAITING URL and mark it as RUNNING.
     *
     * @return The URL that was assigned.
     */
    public jobinfo assignWorkload(int siteid);

    public int getSiteID();

    public List getSites();

    public siteinfo getSite();

    public void batchAddWorkload(List urls, int siteid);

    public void setURL_LastUpdate(int urlid, int year, int month, int day, int siteid);

    public int updateSiteStatus(int siteid, String status);

    /**
     * Add a new URL to the workload, and
     * assign it a status of WAITING.
     *
     * @param url The URL to be added.
     */
    public void addWorkload(String url, int siteid);

    /**
     * Called to mark this URL as either
     * COMPLETE or ERROR.
     *
     * @param url   The URL to complete.
     * @param error true - assign this workload a status of ERROR.
     *              false - assign this workload a status of COMPLETE.
     */
    public void completeWorkload(String url,int urlid, boolean error, int siteid,int urltype);

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
    public char getURLStatus(String url, int siteid);

    /**
     * Clear the contents of the workload store.
     */
    public void clear();

  /**
   * Clear the contents of the workload store.
   */
    public void clearIndexURL();

    public proxySetup getProxySetting();

    public String[] getKeywords(int siteid);
}
