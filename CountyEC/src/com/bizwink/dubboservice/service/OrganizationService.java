package com.bizwink.dubboservice.service;

import com.bizwink.po.OrgPid;
import com.bizwink.po.Organization;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by petersong on 16-3-30.
 */

public interface OrganizationService {
    List<Organization> getAllOrgnizations(BigDecimal siteid);

    List<OrgPid>  getAllParentID(BigDecimal siteid);
}
