package com.bizwink.cms.server;

import com.bizwink.archiver.ArchiveArticles;

import java.io.IOException;

/**
 * User: EricDu
 * Date: 2007-8-31
 * Time: 12:39:09
 */
public class ArchiveServer implements IArchiveServer {

    private static ArchiveArticles archiveServer = null;
    PoolServer cpool;

    public void createArchiver(String logPath, PoolServer cpool) {
        this.cpool = cpool;

        try {
            archiveServer = new ArchiveArticles(logPath, cpool);
        }
        catch (IOException ioe) {
            System.err.println("Error starting Archive Server: " + ioe);
            ioe.printStackTrace();
        }
    }
}
