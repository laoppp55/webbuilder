package com.bizwink.service;

import com.bizwink.cms.server.MyConstants;
import com.bizwink.persistence.ColumnMapper;
import com.bizwink.persistence.SiteinfoMapper;
import com.bizwink.po.Article;
import com.bizwink.persistence.ArticleMapper;
import com.bizwink.po.Column;
import com.bizwink.po.Siteinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-4-4.
 */
@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ColumnMapper columnMapper;

    @Autowired
    private SiteinfoMapper siteinfoMapper;

    public List<Article> getArticlesInPageByColumnids(String editor,BigDecimal siteid,BigDecimal columnid,BigDecimal auditflag,BigDecimal arttype,BigDecimal startnum,BigDecimal endnum,int flag,int ascdesc){
        Map<String,Object> params = new HashMap<String,Object>();
        //List<String> subnodes = getSubnodes(rcolumnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();

        params.put("mssflag",mssflag);
        params.put("columnid",columnid);
        params.put("beginrow",startnum);
        params.put("endrow",endnum);
        params.put("siteid",siteid);
        params.put("editor",editor);
        params.put("flag",flag);
        params.put("ascdesc",ascdesc);
        params.put("auditflag",auditflag);
        params.put("arttype",arttype);

        return articleMapper.getArticlesInPageByColumnids(params);
    }

    public Integer getArticlesCountByColumnids(String editor,BigDecimal siteid,BigDecimal columnid,BigDecimal auditflag,BigDecimal arttype) {
        Map<String,Object> params = new HashMap<String,Object>();
        //List<String> subnodes = getSubnodes(columnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();

        /*System.out.println("计算文章总数量");
        System.out.println("mssflag=" + mssflag);
        System.out.println("columnid=" + columnid);
        System.out.println("editor=" + editor);
        System.out.println("auditflag=" + auditflag);
        System.out.println("arttype=" + arttype);*/

        params.put("siteid",siteid);
        params.put("mssflag",mssflag);
        params.put("columnid",columnid);
        params.put("auditflag",auditflag);
        params.put("arttype",arttype);
        params.put("editor",editor);

        return  articleMapper.getArticlesCountByColumnids(params);
    }

    public List<Article> getSearchArticlesInPage(String editor, BigDecimal siteid, BigDecimal columnid, BigDecimal startnum, BigDecimal endnum,BigDecimal articleid,
                                                 String maintitle,String vicetitle,String author,String source, String spubdate,String epubdate,BigDecimal arttype,BigDecimal auditflag,BigDecimal flag, BigDecimal ascdesc) {

        Map<String,Object> params = new HashMap<String,Object>();
        //List<String> subnodes = getSubnodes(rcolumnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();

        params.put("mssflag",mssflag);
        params.put("columnid",columnid);
        params.put("beginrow",startnum);
        params.put("endrow",endnum);
        params.put("siteid",siteid);
        params.put("editor",editor);
        params.put("flag",flag);
        params.put("ascdesc",ascdesc);

        params.put("articleid",articleid);
        params.put("maintitle",maintitle);
        params.put("vicetitle",vicetitle);
        params.put("author",author);
        params.put("source",source);
        params.put("spubdate",spubdate);
        params.put("epubdate",epubdate);
        params.put("arttype",arttype);
        params.put("auditflag",auditflag);

        return articleMapper.getSearchArticlesInPage(params);

    }

    public int getSearchArticlesCount(String editor,BigDecimal siteid, BigDecimal columnid,BigDecimal articleid,String maintitle, String vicetitle,String author,String source,
                                      String spubdate,String epubdate,BigDecimal arttype,BigDecimal auditflag) {
        Map<String,Object> params = new HashMap<String,Object>();
        //List<String> subnodes = getSubnodes(columnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();
        params.put("mssflag",mssflag);
        params.put("siteid",siteid);
        params.put("columnid",columnid);
        params.put("editor",editor);

        params.put("articleid",articleid);
        params.put("maintitle",maintitle);
        params.put("vicetitle",vicetitle);
        params.put("author",author);
        params.put("source",source);
        params.put("spubdate",spubdate);
        params.put("epubdate",epubdate);
        params.put("arttype",arttype);
        params.put("auditflag",auditflag);

        return articleMapper.getSearchArticlesCount(params);
    }

    public List<Article> getArticlesInPageByDeptAndColumnids(String deptname,BigDecimal siteid,BigDecimal columnid,BigDecimal auditflag,BigDecimal arttype,BigDecimal startnum,BigDecimal endnum,int flag,int ascdesc){
        Map<String,Object> params = new HashMap<String,Object>();
        //List<String> subnodes = getSubnodes(rcolumnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();

        params.put("mssflag",mssflag);
        params.put("columnid",columnid);
        params.put("beginrow",startnum);
        params.put("endrow",endnum);
        params.put("siteid",siteid);
        params.put("deptname",deptname);
        params.put("flag",flag);
        params.put("ascdesc",ascdesc);
        params.put("auditflag",auditflag);
        params.put("arttype",arttype);

        return articleMapper.getArticlesInPageByDeptAndColumnids(params);
    }

    public Integer getArticlesCountByDeptAndColumnids(String deptname,BigDecimal siteid,BigDecimal columnid,BigDecimal auditflag,BigDecimal arttype) {
        Map<String,Object> params = new HashMap<String,Object>();
        //List<String> subnodes = getSubnodes(columnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();

        /*System.out.println("计算文章总数量");
        System.out.println("mssflag=" + mssflag);
        System.out.println("columnid=" + columnid);
        System.out.println("editor=" + editor);
        System.out.println("auditflag=" + auditflag);
        System.out.println("arttype=" + arttype);*/

        params.put("siteid",siteid);
        params.put("mssflag",mssflag);
        params.put("columnid",columnid);
        params.put("auditflag",auditflag);
        params.put("arttype",arttype);
        params.put("deptname",deptname);

        return  articleMapper.getArticlesCountByDeptAndColumnids(params);
    }

    public List<Article> getSearchArticlesInPageByDept(String deptname, BigDecimal siteid, BigDecimal columnid, BigDecimal startnum, BigDecimal endnum,BigDecimal articleid,
                                                 String maintitle,String vicetitle,String author,String source, String spubdate,String epubdate,BigDecimal arttype,BigDecimal auditflag,BigDecimal flag, BigDecimal ascdesc) {

        Map<String,Object> params = new HashMap<String,Object>();
        //List<String> subnodes = getSubnodes(rcolumnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();

        params.put("mssflag",mssflag);
        params.put("columnid",columnid);
        params.put("beginrow",startnum);
        params.put("endrow",endnum);
        params.put("siteid",siteid);
        params.put("deptname",deptname);
        params.put("flag",flag);
        params.put("ascdesc",ascdesc);

        params.put("articleid",articleid);
        params.put("maintitle",maintitle);
        params.put("vicetitle",vicetitle);
        params.put("author",author);
        params.put("source",source);
        params.put("spubdate",spubdate);
        params.put("epubdate",epubdate);
        params.put("arttype",arttype);
        params.put("auditflag",auditflag);

        return articleMapper.getSearchArticlesInPage(params);

    }

    public int getSearchArticlesCountByDept(String deptname,BigDecimal siteid, BigDecimal columnid,BigDecimal articleid,String maintitle, String vicetitle,String author,String source,
                                      String spubdate,String epubdate,BigDecimal arttype,BigDecimal auditflag) {
        Map<String,Object> params = new HashMap<String,Object>();
        //List<String> subnodes = getSubnodes(columnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();
        params.put("mssflag",mssflag);
        params.put("siteid",siteid);
        params.put("columnid",columnid);
        params.put("deptname",deptname);

        params.put("articleid",articleid);
        params.put("maintitle",maintitle);
        params.put("vicetitle",vicetitle);
        params.put("author",author);
        params.put("source",source);
        params.put("spubdate",spubdate);
        params.put("epubdate",epubdate);
        params.put("arttype",arttype);
        params.put("auditflag",auditflag);

        return articleMapper.getSearchArticlesCount(params);
    }

    public List<Article> getArticlesInPageByColumnidsForMove(String editor,BigDecimal siteid,BigDecimal columnid,BigDecimal auditflag,BigDecimal status,BigDecimal startnum,BigDecimal endnum,int flag,int ascdesc){
        Map<String,Object> params = new HashMap<String,Object>();
        //List<String> subnodes = getSubnodes(rcolumnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();

        params.put("mssflag",mssflag);
        params.put("columnid",columnid);
        params.put("beginrow",startnum);
        params.put("endrow",endnum);
        params.put("siteid",siteid);
        params.put("editor",editor);
        params.put("flag",flag);
        params.put("ascdesc",ascdesc);
        params.put("auditflag",auditflag);
        params.put("status",status);

        return articleMapper.getArticlesInPageByColumnidsForMove(params);
    }

    public Integer getArticlesCountByColumnidsForMove(BigDecimal siteid,BigDecimal columnid,BigDecimal auditflag,BigDecimal status) {
        Map<String,Object> params = new HashMap<String,Object>();
        //List<String> subnodes = getSubnodes(columnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();
        params.put("mssflag",mssflag);
        params.put("columnid",columnid);
        params.put("auditflag",auditflag);
        params.put("status",status);

        return  articleMapper.getArticlesCountByColumnidsForMove(params);
    }

    public List<Article> getSearchArticlesInPageForMove(String editor, BigDecimal siteid, BigDecimal columnid, BigDecimal startnum, BigDecimal endnum,BigDecimal articleid,
                                                 String maintitle,String vicetitle,String author,String source, String spubdate,String epubdate,BigDecimal status,BigDecimal auditflag,BigDecimal flag, BigDecimal ascdesc) {

        Map<String,Object> params = new HashMap<String,Object>();
        //List<String> subnodes = getSubnodes(rcolumnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();

        params.put("mssflag",mssflag);
        params.put("columnid",columnid);
        params.put("beginrow",startnum);
        params.put("endrow",endnum);
        params.put("siteid",siteid);
        params.put("editor",editor);
        params.put("flag",flag);
        params.put("ascdesc",ascdesc);

        params.put("articleid",articleid);
        params.put("maintitle",maintitle);
        params.put("vicetitle",vicetitle);
        params.put("author",author);
        params.put("source",source);
        params.put("spubdate",spubdate);
        params.put("epubdate",epubdate);
        params.put("status",status);
        params.put("auditflag",auditflag);

        return articleMapper.getSearchArticlesInPageForMove(params);

    }

    public int getSearchArticlesCountForMove(BigDecimal siteid, BigDecimal columnid,BigDecimal articleid,String maintitle, String vicetitle,String author,String source,
                                      String spubdate,String epubdate,BigDecimal status,BigDecimal auditflag) {
        Map<String,Object> params = new HashMap<String,Object>();
        //List<String> subnodes = getSubnodes(columnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();
        params.put("mssflag",mssflag);
        params.put("siteid",siteid);
        params.put("columnid",columnid);

        params.put("articleid",articleid);
        params.put("maintitle",maintitle);
        params.put("vicetitle",vicetitle);
        params.put("author",author);
        params.put("source",source);
        params.put("spubdate",spubdate);
        params.put("epubdate",epubdate);
        params.put("status",status);
        params.put("auditflag",auditflag);

        return articleMapper.getSearchArticlesCountForMove(params);
    }

    public List<Article> getArticlesInPageByColumnidsForPublish(String editor,BigDecimal siteid,BigDecimal columnid,BigDecimal auditflag,BigDecimal status,BigDecimal startnum,BigDecimal endnum,int flag,int ascdesc){
        Map<String,Object> params = new HashMap<String,Object>();
        List<String> subnodes = getSubnodes(columnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();

        params.put("mssflag",mssflag);
        params.put("columnids",subnodes);
        params.put("beginrow",startnum);
        params.put("endrow",endnum);
        params.put("siteid",siteid);
        params.put("editor",editor);
        params.put("flag",flag);
        params.put("ascdesc",ascdesc);
        params.put("auditflag",auditflag);
        params.put("status",status);

        return articleMapper.getArticlesInPageByColumnidsForPublish(params);
    }

    public Integer getArticlesCountByColumnidsForPublish(BigDecimal siteid,BigDecimal columnid,BigDecimal auditflag,BigDecimal status) {
        Map<String,Object> params = new HashMap<String,Object>();
        List<String> subnodes = getSubnodes(columnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();
        params.put("mssflag",mssflag);
        params.put("columnids",subnodes);
        params.put("auditflag",auditflag);
        params.put("status",status);
        params.put("siteid",siteid);

        return  articleMapper.getArticlesCountByColumnidsForPublish(params);
    }

    public List<Article> getSearchArticlesInPageForPublish(String editor, BigDecimal siteid, BigDecimal columnid, BigDecimal startnum, BigDecimal endnum,BigDecimal articleid,
                                                        String maintitle,String vicetitle,String author,String source, String spubdate,String epubdate,BigDecimal status,BigDecimal auditflag,BigDecimal flag, BigDecimal ascdesc) {

        Map<String,Object> params = new HashMap<String,Object>();
        List<String> subnodes = getSubnodes(columnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();

        System.out.println("spubdate:" + spubdate);
        System.out.println("epubdate:" + epubdate);

        params.put("mssflag",mssflag);
        params.put("columnids",subnodes);
        params.put("beginrow",startnum);
        params.put("endrow",endnum);
        params.put("siteid",siteid);
        params.put("editor",editor);
        params.put("flag",flag);
        params.put("ascdesc",ascdesc);

        params.put("articleid",articleid);
        params.put("maintitle",maintitle);
        params.put("vicetitle",vicetitle);
        params.put("author",author);
        params.put("source",source);
        params.put("spubdate",spubdate);
        params.put("epubdate",epubdate);
        params.put("status",status);
        params.put("auditflag",auditflag);

        return articleMapper.getSearchArticlesInPageForPublish(params);

    }

    public int getSearchArticlesCountForPublish(BigDecimal siteid, BigDecimal columnid,BigDecimal articleid,String maintitle, String vicetitle,String author,String source,
                                             String spubdate,String epubdate,BigDecimal status,BigDecimal auditflag) {
        Map<String,Object> params = new HashMap<String,Object>();
        List<String> subnodes = getSubnodes(columnid);
        Siteinfo siteinfo = siteinfoMapper.selectByPrimaryKey(siteid);
        int mssflag = siteinfo.getMANAGESHARESITE().intValue();
        params.put("mssflag",mssflag);
        params.put("siteid",siteid);
        params.put("columnids",subnodes);

        //System.out.println("spubdate:" + spubdate);
        //System.out.println("epubdate:" + epubdate);

        params.put("articleid",articleid);
        params.put("maintitle",maintitle);
        params.put("vicetitle",vicetitle);
        params.put("author",author);
        params.put("source",source);
        params.put("spubdate",spubdate);
        params.put("epubdate",epubdate);
        params.put("status",status);
        params.put("auditflag",auditflag);

        return articleMapper.getSearchArticlesCountForPublish(params);
    }

    public List<Article> getArticlesByColumnids(BigDecimal siteid,BigDecimal rcolumnid,String keyword,BigDecimal startnum,BigDecimal endnum) {
        Map<String,Object> params = new HashMap<String,Object>();
        List<String> subnodes = MyConstants.getColumns();  //getSubnodes(rcolumnid);
        params.put("list",subnodes);
        params.put("keyword",keyword);
        params.put("beginrow",startnum);
        params.put("endrow",endnum);
        return articleMapper.getArticlesByColumnids(params);
    }

    public List<String> getSubnodes(BigDecimal  rcolumnid) {
        List<BigDecimal> parents = new ArrayList<BigDecimal>();
        List<String> results = new ArrayList<String>();
        parents.add(rcolumnid);
        int i = 0;

        do {
            BigDecimal pid = parents.get(i);
            results.add(String.valueOf(pid.intValue()));
            parents.remove(i);
            i = i - 1;
            List<Column> items = columnMapper.getSubColumnsByParentID(pid);
            for(int j=0; j<items.size(); j++) {
                Column column = (Column)items.get(j);
                parents.add(column.getID());
                i = i + 1;
            }
        } while (parents.size() > 0);

        return results;
    }

    public List<Article> getArticleByDept(String deptid,BigDecimal siteid,BigDecimal startno,BigDecimal endno){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("deptid",deptid);
        params.put("siteid",siteid);
        params.put("BEGINROW",startno);
        params.put("ENDROW",endno);

        return articleMapper.getArticleListbyDeptid(params);
    }

    public BigDecimal countArticleByDeptid(String deptid,BigDecimal siteid) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("deptid",deptid);
        params.put("siteid",siteid);

        return articleMapper.countArticlebyDeptid(params);
    }

    public List<Article> searchArticleByDeptAndKeyword(String deptid,String keyword,BigDecimal status,BigDecimal pubflag,BigDecimal siteid,BigDecimal startno,BigDecimal endno){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("deptid",deptid);
        params.put("siteid",siteid);
        params.put("searchword",keyword);
        params.put("status",status);
        params.put("pubflag",pubflag);
        params.put("BEGINROW",startno);
        params.put("ENDROW",endno);

        return articleMapper.searchArticleListbyDeptidAndKeyword(params);
    }

    public BigDecimal countArticlebyDeptidAndKeyword(String deptid,String keyword,BigDecimal status,BigDecimal pubflag,BigDecimal siteid) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("deptid",deptid);
        params.put("siteid",siteid);
        params.put("searchword",keyword);
        params.put("status",status);
        params.put("pubflag",pubflag);

        return articleMapper.countArticlebyDeptidAndKeyword(params);
    }

    public int deleteArticleByAidList(BigDecimal siteid,String artids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("artids",artids);
        params.put("siteid",siteid);

        return articleMapper.deleteByArticleIDS(params);
    }

    public List<Article> getRejectArticlesbyDeptid(String deptid,BigDecimal status,BigDecimal pubflag,BigDecimal auditflag,BigDecimal siteid,BigDecimal startno,BigDecimal endno) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("deptid",deptid);
        params.put("siteid",siteid);
        params.put("status",status);
        params.put("pubflag",pubflag);
        params.put("auditflag",auditflag);
        params.put("BEGINROW",startno);
        params.put("ENDROW",endno);

        return articleMapper.getRejectArticlesbyDeptid(params);
    }

    public BigDecimal countRejectArticlebyDeptid(String deptid,BigDecimal status,BigDecimal pubflag,BigDecimal auditflag,BigDecimal siteid) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("deptid",deptid);
        params.put("siteid",siteid);
        params.put("status",status);
        params.put("pubflag",pubflag);
        params.put("auditflag",auditflag);

        return articleMapper.countRejectArticlebyDeptid(params);
    }

    public List<Article> searchRejectArticlesbyDeptidAndKeyword(String deptid,String keyword,BigDecimal status,BigDecimal pubflag,BigDecimal auditflag,BigDecimal siteid,BigDecimal startno,BigDecimal endno) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("deptid",deptid);
        params.put("siteid",siteid);
        params.put("searchword",keyword);
        params.put("status",status);
        params.put("pubflag",pubflag);
        params.put("auditflag",auditflag);
        params.put("BEGINROW",startno);
        params.put("ENDROW",endno);

        return articleMapper.searchRejectArticlesbyDeptidAndKeyword(params);
    }

    public BigDecimal countSearchRejectArticlebyDeptidAndKeyword(String deptid,String keyword,BigDecimal status,BigDecimal pubflag,BigDecimal auditflag,BigDecimal siteid) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("deptid",deptid);
        params.put("siteid",siteid);
        params.put("searchword",keyword);
        params.put("status",status);
        params.put("pubflag",pubflag);
        params.put("auditflag",auditflag);

        return articleMapper.countSearchRejectArticlebyDeptidAndKeyword(params);
    }

    public Article getArticleByID(BigDecimal articleid) {
        return articleMapper.selectByPrimaryKey(articleid);
    }

    public int saveArticleNoContent(Article article) {
        return articleMapper.insertNoContent(article);
    }

    public int updateArticleNoContent(Article article) {
        return articleMapper.updateNoContent(article);
    }

    public BigDecimal getMainKey() {
        return articleMapper.getMainKey();
    }
}
