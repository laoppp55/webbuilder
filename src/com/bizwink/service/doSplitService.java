package com.bizwink.service;

import com.bizwink.cms.news.ArticlePeer;
import com.bizwink.cms.news.ColumnPeer;
import com.bizwink.persistence.ArticleMapper;
import com.bizwink.persistence.ColumnKeywordsMapper;
import com.bizwink.persistence.ColumnMapper;
import com.bizwink.po.Article;
import com.bizwink.po.Column;
import com.bizwink.po.ColumnKeywords;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class doSplitService extends QuartzJobBean {
    @Autowired
    private ColumnKeywordsMapper columnKeywordsMapper;
    @Autowired
    private ColumnMapper columnMapper;
    @Autowired
    private ArticleMapper articleMapper;

    public void work(String dtime) {
        //实现你的业务逻辑
        List<ColumnKeywords> columnKeywords = columnKeywordsMapper.getAllKeywords();
        for(int ii=0; ii<columnKeywords.size(); ii++) {
            int tcolid = columnKeywords.get(ii).getTCOLUMNID().intValue();
            int scolid = columnKeywords.get(ii).getSCOLUMNID().intValue();
            Column column = columnMapper.selectByPrimaryKey(columnKeywords.get(ii).getTCOLUMNID());
            int siteid = column.getSITEID().intValue();
            splitArticleToColumn(siteid,scolid,tcolid,columnKeywords.get(ii).getKEYWORDS());
        }
        System.out.println("每" + dtime + "分钟修改用户登录状态!" + new Timestamp(System.currentTimeMillis()));
    }

    public void splitArticleToColumn(int siteid,int scolumn,int tcolumn,String columnKeywords) {
        Map params = new HashMap();
        params.put("SITEID",siteid);
        params.put("COLUMNID",scolumn);
        int articleNum = articleMapper.countArticleBySiteidAndColumnid(params);
        int range = 1000;
        int extra_num = articleNum % 1000;
        int count = 0;
        if (extra_num > 0)
            count = articleNum/1000 + 1;
        else
            count = articleNum/1000;
        String[] keywords = columnKeywords.split(",");

        for(int ii=0;ii<count; ii++) {
            int startrow = ii * 1000;
            int endrow = (ii+1) * 1000;
            params = new HashMap();
            params.put("SITEID",siteid);
            params.put("COLUMNID",scolumn);
            params.put("ENDROW",endrow);
            params.put("BEGINROW",startrow);
            List<Article> articles = articleMapper.getArticlesInPageBySiteidAndColumnid(params);
            for(int jj = 0; jj<articles.size(); jj++) {
                Article article = articles.get(jj);
                boolean moveFlag = false;
                for(int kk=0; kk<keywords.length; kk++) {
                    moveFlag = moveFlag || article.getMAINTITLE().contains(keywords[kk]);
                }

                //如果文章转移栏目标志为真，将其转移到目标栏目
                if (moveFlag) {
                    params = new HashMap();
                    params.put("ID",article.getID());
                    params.put("COLUMNID",tcolumn);
                    int errcode = articleMapper.updateArticleColumn(params);
                }
            }
        }
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Map properties = context.getJobDetail().getJobDataMap();
        String message = (String)properties.get("message");
        this.work(message);
    }
}
