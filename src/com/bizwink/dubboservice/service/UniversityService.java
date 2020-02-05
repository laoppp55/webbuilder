package com.bizwink.dubboservice.service;

import com.bizwink.po.University;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 16-10-8.
 */
public interface UniversityService {
    public List<University> getUniversitys(String keyword);

    public University getUniversityByID(BigDecimal uid);
}
