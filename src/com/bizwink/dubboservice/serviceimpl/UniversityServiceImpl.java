package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.UniversityService;
import com.bizwink.persistence.UniversityMapper;
import com.bizwink.po.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 16-10-8.
 */
@Service
public class UniversityServiceImpl implements UniversityService{
    @Autowired
    private UniversityMapper universityMapper;

    public List<University> getUniversitys(String keyword) {
        return universityMapper.getUniversitys(keyword);
    }

    public University getUniversityByID(BigDecimal uid) {
        return universityMapper.selectByPrimaryKey(uid);
    }
}
