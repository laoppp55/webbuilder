package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.ArticleService;
import com.bizwink.persistence.*;
import com.bizwink.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 16-6-19.
 */
@Service
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleExtendattrMapper articleExtendattrMapper;

    @Autowired
    private ColumnMapper columnMapper;

    @Autowired
    private TurpicMapper turpicMapper;

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private PublishQueueMapper publishQueueMapper;

    @Autowired
    private TemplateMapper templateMapper;

    public List<Article> getArticles(BigDecimal siteid,BigDecimal columnid){
        Map<String, Object> params =new HashMap<String, Object>();
        params.put("SITEID",siteid);
        params.put("COLUMNID",columnid);
        return articleMapper.getArticlesBySiteidAndColumnid(params);
    }

    public List<Article> getArticlesByPage(BigDecimal siteid,BigDecimal columnid,int startrow,int pagesize) {
        Map<String, Object> params =new HashMap<String, Object>();
        params.put("SITEID",siteid);
        params.put("COLUMNID",columnid);
        params.put("ENDROW",startrow + pagesize);
        params.put("BEGINROW",startrow+1);

        return articleMapper.getArticlesInPageBySiteidAndColumnid(params);
    }

    public int countArticles(BigDecimal siteid,BigDecimal columnid) {
        Map<String, Object> params =new HashMap<String, Object>();
        params.put("SITEID",siteid);
        params.put("COLUMNID",columnid);

        return articleMapper.countArticleBySiteidAndColumnid(params);
    }

    public Article getArticle(BigDecimal articleid) {
        return articleMapper.selectByPrimaryKey(articleid);
    }

    public Article getArticleByUseridAndArticleid(String userid,BigDecimal articleid) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("ID",articleid);
        params.put("CREATOR",userid);
        return articleMapper.getArticleByUseridAndArticleid(params);
    }


    public int CreateArticle(Article record) {
        return articleMapper.insert(record);
    }

    @Transactional
    public int CreateArticleWithExtendAttr(Article article,List<ArticleExtendattr> extendattrs,List<Turpic> turnpics,List<Template> templates) {
        //文章信息入库
        BigDecimal articleid = articleMapper.getMainKey();
        article.setID(articleid);
        int article_count = articleMapper.insert(article);

        if (articleid.intValue() > 0) {
            //文章扩展属性信息入库
            List<ArticleExtendattr> articleExtendattrs = new ArrayList<ArticleExtendattr>();
            for(int ii=0;ii<extendattrs.size();ii++) {
                ArticleExtendattr articleExtendattr = extendattrs.get(ii);
                articleExtendattr.setARTICLEID(articleid);
                articleExtendattrMapper.insert(articleExtendattr);
            }

            //文章附属图片信息入库
            List<Turpic> turpics_list = new ArrayList<Turpic>();
            for(int ii=0;ii<turnpics.size();ii++) {
                Turpic turpic = turnpics.get(ii);
                turpic.setARTICLEID(articleid);
                turpicMapper.insert(turpic);
            }

            //需要发布的模板文件信息进入发布队列库
            PublishQueue publishQueue = null;
            Timestamp createdate = new Timestamp(System.currentTimeMillis());
            for(int ii=0;ii<templates.size();ii++) {
                Template template = templates.get(ii);
                Template t_for_update_status = new Template();
                t_for_update_status.setID(template.getID());
                t_for_update_status.setSTATUS((short)0);
                templateMapper.updateByPrimaryKeySelective(t_for_update_status);
                publishQueue = new PublishQueue();
                publishQueue.setSITEID(template.getSITEID());
                publishQueue.setCOLUMNID(template.getCOLUMNID());
                publishQueue.setTARGETID(template.getID());
                publishQueue.setTITLE(template.getCHNAME());
                publishQueue.setSTATUS((short) 1);
                publishQueue.setPRIORITY(BigDecimal.valueOf(2));                                                        //栏目模板发布作业的优先级设置为2
                if (template.getISARTICLE() == 0 || template.getISARTICLE()==3)   //栏目模板和专题模板
                    publishQueue.setTYPE(BigDecimal.valueOf(3));
                else if (template.getISARTICLE() == 1)                              //首页模板
                    publishQueue.setTYPE(BigDecimal.valueOf(2));
                publishQueue.setCREATEDATE(createdate);
                publishQueue.setPUBLISHDATE(createdate);
                publishQueueMapper.insert(publishQueue);
            }
            //向发布队列增加本文章的发布作业信息
            publishQueue = new PublishQueue();
            publishQueue.setSITEID(article.getSITEID());
            publishQueue.setCOLUMNID(article.getCOLUMNID());
            publishQueue.setTARGETID(articleid);
            publishQueue.setTITLE(article.getMAINTITLE());
            publishQueue.setSTATUS((short) 1);
            publishQueue.setTYPE(BigDecimal.valueOf(1));
            publishQueue.setPRIORITY(BigDecimal.valueOf(1));
            publishQueue.setCREATEDATE(createdate);
            publishQueue.setPUBLISHDATE(createdate);
            publishQueueMapper.insert(publishQueue);

            //写文章信息入库的LOG信息
            Log log = new Log();
            log.setSITEID(article.getSITEID());
            log.setCOLUMNID(article.getCOLUMNID());
            log.setARTICLEID(articleid);
            log.setMAINTITLE(article.getMAINTITLE());
            log.setACTTIME(createdate);
            log.setCREATEDATE(createdate);
            log.setACTTYPE((short)1);
            log.setEDITOR(article.getEDITOR());
            int log_count = logMapper.insert(log);
        }

        return article_count;
    }


    public int UpdateArticle(Article record) {
        return articleMapper.updateByPrimaryKey(record);
    }

    public int RemoveArticle(BigDecimal articleid) {
        return articleMapper.deleteByPrimaryKey(articleid);
    }

    public List<Article> getArticleList(Map<String, Object> param){
        return articleMapper.getArticleList(param);
    }

    public List<Article> getOrderByBuyname(Map<String, Object> param){
        return  articleMapper.getOrderByBuyname(param);
    }

    public List<Article>  getArticleListbyCreator(Map<String, Object> param){
        return articleMapper.getArticleListbyCreator(param);
    }

    //根据图书的状态取出图书列表
    //status=2：归档，商品被销售
    //status=0：下架，商品不再销售
    //status=1：新品，正在销售
    public List<Article> getArticleByUseridAndStatus(String userid,BigDecimal status) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("STATUS",status);
        params.put("CREATOR",userid);
        return articleMapper.getArticleByUseridAndStatus(params);
    }

    //status=2：归档，商品被销售
    //status=0：下架，商品不再销售
    //status=1：新品，正在销售
    public int UpdateArticleStatus(BigDecimal status,BigDecimal articleid){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("STATUS",status);
        params.put("ID",articleid);
        return articleMapper.UpdateArticleStatus(params);
    }

    public String getXMLTemplate(BigDecimal columnid) {
        String xmlTemplate = null;
        Column column = null;

        while (columnid.intValue() != 0) {
            column = columnMapper.selectByPrimaryKey(columnid);
            xmlTemplate = column.getXMLTEMPLATE();
            int isDefine = column.getISDEFINEATTR();
            if (isDefine == 1 && xmlTemplate != null && xmlTemplate.trim().length() > 0)
                break;
            else
                columnid = column.getPARENTID();
        }

        return xmlTemplate;
    }
}
