package com.bizwink.dubboservice.service;

import com.bizwink.po.AddressInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Jhon on 2016/12/9.
 */
public interface AddressInfoService {

    int insertAddress(AddressInfo addressInfo);

    List<AddressInfo> getAddressList(Map<String, Object> param);

    int deleteAddress (int id);

    int updateAddress(AddressInfo addressInfo);
}
