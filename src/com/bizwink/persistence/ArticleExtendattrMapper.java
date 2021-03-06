package com.bizwink.persistence;

import com.bizwink.po.ArticleExtendattr;
import java.math.BigDecimal;
import java.util.List;

public interface ArticleExtendattrMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_ARTICLE_EXTENDATTR
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    BigDecimal getMainKey();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_ARTICLE_EXTENDATTR
     *
     * @mbggenerated
     */
    int insert(ArticleExtendattr record);

    int addArticleBatch(List<ArticleExtendattr> extendattrs);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_ARTICLE_EXTENDATTR
     *
     * @mbggenerated
     */
    int insertSelective(ArticleExtendattr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_ARTICLE_EXTENDATTR
     *
     * @mbggenerated
     */
    ArticleExtendattr selectByPrimaryKey(BigDecimal ID);

    ArticleExtendattr selectAttrValByEname(String ename);

    List<ArticleExtendattr> getArticleExtendAttr(BigDecimal articleid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_ARTICLE_EXTENDATTR
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ArticleExtendattr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_ARTICLE_EXTENDATTR
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(ArticleExtendattr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_ARTICLE_EXTENDATTR
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ArticleExtendattr record);
}