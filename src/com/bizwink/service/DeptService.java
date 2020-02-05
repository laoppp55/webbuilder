package com.bizwink.service;

import com.bizwink.persistence.DepartmentMapper;
import com.bizwink.po.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeptService {
    @Autowired
    private DepartmentMapper departmentMapper;

    public Department getDepartment(BigDecimal siteid,BigDecimal deptid) {
        return departmentMapper.selectByPrimaryKey(deptid);
    }

    public List<Department> getDepartmentByOrgid(BigDecimal siteid, BigDecimal orgid) {
        Map params = new HashMap();
        params.put("SITEID",siteid);
        params.put("ORGID",orgid);
        return departmentMapper.getDepartmentsByOrgid(params);
    }

    public Department getDepartmentByDeptID(BigDecimal deptid) {
        return departmentMapper.selectByPrimaryKey(deptid);
    }

}
