package com.bizwink.service;

import com.bizwink.persistence.*;

import com.bizwink.po.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class AreaService {
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private ZoneMapper zoneMapper;
    @Autowired
    private TownMapper townMapper;
    @Autowired
    private VillageMapper villageMapper;


    private static Logger logger = Logger.getLogger(AreaService.class.getName());

    public List<Province> getProvince(){

        return provinceMapper.seelctProvincesByValid();
    }

    public List<City> getCity(BigDecimal provinceid){
        return cityMapper.selectCitysByCode(provinceid);
    }

    //返回省的编码
    public String getProvCodeByName(String name) {
            return provinceMapper.getProvCodeByName(name);
    }

    //返回市的编码
    public String getCityCodeByName(Map params) {
        return cityMapper.getCityCodeByName(params);
    }

    public String getCityName(String cityCode){
        return cityMapper.getCityName(cityCode);
    }

    public List<Zone> getZones(BigDecimal cityid){
        return zoneMapper.selectZonesByCode(cityid);
    }

    public List<Town>  getTowns(BigDecimal zoneid){
        return townMapper.selectTownsByCode(zoneid);
    }

    public List<Village> getVillages(BigDecimal townid){
        return villageMapper.selectVillagesByCode(townid);
    }

}
