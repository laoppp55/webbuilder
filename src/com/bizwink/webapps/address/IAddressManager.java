package com.bizwink.webapps.address;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-12-9
 * Time: 8:34:48
 * To change this template use File | Settings | File Templates.
 */
public interface IAddressManager {

    List getProvinceList();

    List getProvinceListByCountry(String code);

    List getCityListByProvid(int provid);

    List getCityList(String provincename);

    List getZoneListByCityid(int cityid);

    List getZoneList(String cityname);
}
