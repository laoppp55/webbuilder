package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.AreaService;
import com.bizwink.persistence.*;
import com.bizwink.po.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AreaServiceImpl implements AreaService {
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

    private static Logger logger = Logger.getLogger(AreaServiceImpl.class.getName());

    public List<Province> getProvince(){

        return provinceMapper.seelctProvincesByValid();
    }

    public List<City> getCity(String provinceid){

        return cityMapper.selectCitysByStrcode(provinceid);
    }

    public List<Zone> getZones(String cityid){

        return zoneMapper.selectZonesByStrCode(cityid);
    }

    public List<Town>  getTowns(String zoneid){

        return townMapper.selectTownsByStrCode(zoneid);
    }

    public List<Village> getVillages(String townid){

        return villageMapper.selectVillagesByStrCode(townid);
    }

    //返回省的编码
    public String getProvCodeByName(String name) {
            return provinceMapper.getProvCodeByName(name);
    }

    //返回市的编码
    public String getCityCodeByName(Map params) {
        return cityMapper.getCityCodeByName(params);
    }

    //返回区县的编码
    public String getZoneCodeByName(Map params) {
        return zoneMapper.getZoneCodeByName(params);
    }

    //返回乡镇编码
    public String getTownCodeByName(Map params) {
        return townMapper.getTownCodeByName(params);
    }

    //返回村的编码
    public String getVillageCodeByName(Map params) {
        return villageMapper.getVillageCodeByName(params);
    }

    public String getCityName(String cityCode){
        return cityMapper.getCityName(cityCode);
    }

    public String getZoneName(String zoneCode){
        return  zoneMapper.getZoneName(zoneCode);
    }

    public String getTownName(String townCode){
       return  townMapper.getTownName(townCode);
    }

    public String getVillageName(String villageCode){
       return villageMapper.getVillageName(villageCode);
    }
}
