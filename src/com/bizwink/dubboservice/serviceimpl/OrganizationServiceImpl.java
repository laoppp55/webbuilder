package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.po.OrgPid;
import com.bizwink.dubboservice.service.OrganizationService;
import com.bizwink.persistence.OrganizationMapper;
import com.bizwink.po.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by petersong on 16-3-31.
 */

@Service
public class OrganizationServiceImpl implements OrganizationService{
    @Autowired
    private OrganizationMapper organizationMapper;

    public List<Organization> getAllOrgnizations(BigDecimal siteid) {
        return organizationMapper.getAllOrgnizations(siteid);
    }

    public List<OrgPid> getAllParentID(BigDecimal siteid) {
        List<OrgPid> orgs = organizationMapper.getAllParentID(siteid);

        return orgs;
    }
}
