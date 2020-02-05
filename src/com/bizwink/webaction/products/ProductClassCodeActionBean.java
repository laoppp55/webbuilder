package com.bizwink.webaction.products;

import com.bizwink.po.Materialcode;
import com.bizwink.service.MaterialcodeService;
import com.bizwink.webaction.AbstractActionBean;
import com.google.gson.Gson;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by petersong on 16-8-18.
 */
@SessionScope
public class ProductClassCodeActionBean extends AbstractActionBean {
    @SpringBean
    private transient MaterialcodeService materialcodeService;

    @DefaultHandler
    public Resolution getMaterialcodes(BigDecimal siteid) {
        List<Materialcode> param = materialcodeService.getMaterialcodesBySiteid(siteid);
        Gson gson = new Gson();
        String json =  gson.toJson(param);
        //return    new StreamingResolution("application/json", json);
        StreamingResolution rs = new StreamingResolution("application/json", json);
        rs.setCharacterEncoding("utf-8");
        return rs;
    }
}
