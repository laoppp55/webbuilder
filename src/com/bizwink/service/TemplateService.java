package com.bizwink.service;

import com.bizwink.persistence.TemplateMapper;
import com.bizwink.po.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TemplateService {
    @Autowired
    private TemplateMapper templateMapper;

    public List<Template> getTemplatesByColumnid(BigDecimal siteid,BigDecimal columnid) {
        Map params = new HashMap();
        params.put("SITEID",siteid);
        params.put("COLUMNID",columnid);

        return templateMapper.getTemplateByColumnid(params);
    }
}
