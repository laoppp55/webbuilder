package com.bizwink.service;

import com.bizwink.persistence.ArticleTypeMapper;
import com.bizwink.po.ArticleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by petersong on 17-2-24.
 */
@Service
public class ArticletypeService {
    @Autowired
    private ArticleTypeMapper articleTypeMapper;

    public List<ArticleType> getArticleTypes(BigDecimal columnid) {
        return articleTypeMapper.getArticleTypesByColumnid(columnid);
    }
}
