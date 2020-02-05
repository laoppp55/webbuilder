package com.bizwink.cms.server;

import com.heaton.bot.test.SpiderNews;

/**
 * User: EricDu
 * Date: 2007-8-31
 * Time: 12:39:09
 */
public class SpiderServer implements ISpiderServer {
    private static SpiderNews spiderServer = null;
    PoolServer cpool;

    public void createSpiderServer(PoolServer cpool) {
        this.cpool = cpool;
        spiderServer = new SpiderNews(cpool);
    }
}
