package com.bizwink.dubboservice.service;

import com.bizwink.po.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: perter.song
 * Date: 16-2-4
 * Time: 下午2:10
 * To change this template use File | Settings | File Templates.
 */
public interface AreaService {
    public List<Province> getProvince();

    public List<City> getCity(String provinceid);

    public List<Zone> getZones(String cityid);

    public List<Town>  getTowns(String zoneid);

    public List<Village> getVillages(String townid);

    public String getCityName(String cityCode);

    public String getZoneName(String zoneCode);

    public String getTownName(String townCode);

    public String getVillageName(String villageCode);
}
