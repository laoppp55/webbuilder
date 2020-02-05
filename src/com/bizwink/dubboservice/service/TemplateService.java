package com.bizwink.dubboservice.service;

import com.bizwink.po.Template;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 16-6-19.
 */
public interface TemplateService {
    List<Template> getTemplateByColumnidAndSiteidAndTemplateType(BigDecimal siteid,BigDecimal columnid,BigDecimal isarticle);

    List<Template> getTemplatesByRelatedColumnid(BigDecimal siteid,BigDecimal columnid);

    List<Template> getTemplateByColumnidAndSiteidAndTemplateTypeAndTempno(BigDecimal siteid,BigDecimal columnid,BigDecimal isarticle,BigDecimal tempno);

    int UpdateTemplate(Template template);

    int CreateTemplate(Template template);

    Template getTemplate(int siteid,int columnid,int isarticle);
}