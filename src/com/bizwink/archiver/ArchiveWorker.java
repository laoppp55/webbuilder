package com.bizwink.archiver;

import java.io.*;
import java.util.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.news.*;

public class ArchiveWorker extends Thread {

    PoolServer cpool;
    private List artList = null;

    public ArchiveWorker(List artList, PoolServer cpool) throws IOException {
        this.cpool = cpool;
        this.artList = artList;
    }

    public void run() {
        Article article;

        try {
            if (artList != null) {
                for (int i = 0; i < artList.size(); i++) {
                    article = (Article) artList.get(i);
                    System.out.println("栏目：" + article.getColumnID() + " 文章：" + article.getID() + " " + article.getMainTitle() + " 归档完成");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
