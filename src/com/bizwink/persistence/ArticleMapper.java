package com.bizwink.persistence;

import com.bizwink.po.Article;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ArticleMapper {
    List<Article> getNeedIndex(int siteid);

    List<Article> getDeleteIndex();

    //将该文章所在栏目及该栏目的所有父节点的id取出来拼成逗号字符串
    String getColumnParents(int id); //其父亲栏目id以逗号分隔组成一个字符串

    String getColumnDirname(int columnid);        //获取文章所造栏目的目录路径

    void updateIdxFlagByids(@Param(value = "ids") String  ids);

    BigDecimal getMainKey();

    Integer getArticlesCountByColumnids(Map<String, Object> params);

    List<Article> getArticlesInPageByColumnids(Map<String, Object> params);

    Integer getArticlesCountByDeptAndColumnids(Map<String, Object> params);

    List<Article> getArticlesInPageByDeptAndColumnids(Map<String, Object> params);

    List<Article> getSearchArticlesInPage(Map<String, Object> params);

    int getSearchArticlesCount(Map<String, Object> params);

    List<Article> getSearchArticlesInPageByDept(Map<String, Object> params);

    int getSearchArticlesCountByDept(Map<String, Object> params);

    Integer getArticlesCountByColumnidsForMove(Map<String, Object> params);

    List<Article> getArticlesInPageByColumnidsForMove(Map<String, Object> params);

    List<Article> getSearchArticlesInPageForMove(Map<String, Object> params);

    int getSearchArticlesCountForMove(Map<String, Object> params);

    Integer getArticlesCountByColumnidsForPublish(Map<String, Object> params);

    List<Article> getArticlesInPageByColumnidsForPublish(Map<String, Object> params);

    List<Article> getSearchArticlesInPageForPublish(Map<String, Object> params);

    int getSearchArticlesCountForPublish(Map<String, Object> params);

    List<Article> getArticlesBySiteidAndColumnid(Map<String, Object> params);

    List<Article> getArticlesInPageBySiteidAndColumnid(Map<String, Object> params);

    List<Article> getArticleList(Map<String, Object> params);

    List<Article> getArticlesByColumnids(Map<String, Object> params);

    List<Article> getArticlesByColumnid();

    List<Article>  getArticleListbyCreator(Map<String, Object> param);

    List<Article>  getArticleListbyDeptid(Map<String, Object> param);

    List<Article>  searchArticleListbyDeptidAndKeyword(Map<String, Object> param);

    BigDecimal countArticlebyDeptidAndKeyword(Map<String, Object> param);

    BigDecimal  countArticlebyDeptid(Map<String, Object> param);

    List<Article> getOrderByBuyname(Map<String, Object> param);

    Integer countArticleBySiteidAndColumnid(Map<String, Object> params);

    Article selectByPrimaryKey(BigDecimal ID);

    Article getArticleByUseridAndArticleid(Map<String, Object> params);

    List<Article> getArticleByUseridAndStatus(Map<String, Object> params);

    List<Article> getRejectArticlesbyDeptid(Map<String, Object> params);

    BigDecimal countRejectArticlebyDeptid(Map<String, Object> params);

    List<Article> searchRejectArticlesbyDeptidAndKeyword(Map<String, Object> params);

    BigDecimal countSearchRejectArticlebyDeptidAndKeyword(Map<String, Object> params);

    int deleteByPrimaryKey(BigDecimal ID);

    int deleteByArticleIDS(Map<String, Object> params);

    int insert(Article record);

    int insertNoContent(Article record);

    int updateByPrimaryKey(Article record);

    int updateNoContent(Article record);

    int updateArticleColumn(Map<String, Object> params);

    int UpdateArticleStatus(Map<String, Object> param);
}