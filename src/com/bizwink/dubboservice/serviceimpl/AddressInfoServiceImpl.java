package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.AddressInfoService;
import com.bizwink.persistence.AddressInfoMapper;
import com.bizwink.po.AddressInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Jhon on 2016/12/9.
 */
public class AddressInfoServiceImpl implements AddressInfoService {
    @Autowired
    private AddressInfoMapper addressInfoMapper;

    private static Logger logger = Logger.getLogger(AreaServiceImpl.class.getName());

    public  int insertAddress(AddressInfo addressInfo){
        return addressInfoMapper.insert(addressInfo);
    }

    public List<AddressInfo> getAddressList(Map<String, Object> param){
        return addressInfoMapper.getAddressList(param);
    }

    public int deleteAddress (int id){
        return  addressInfoMapper.deleteAddress(id);
    }

    public int updateAddress(AddressInfo addressInfo){
        return addressInfoMapper.updateByPrimaryKey(addressInfo);
    }
}
