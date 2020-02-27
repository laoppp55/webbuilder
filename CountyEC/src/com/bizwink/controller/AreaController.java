package com.bizwink.controller;

import com.bizwink.dubboservice.service.AreaService;
import com.bizwink.po.*;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Jhon on 2016/4/18.
 */

@Controller
@RequestMapping("/")
public class AreaController {
    @Autowired
    private AreaService areaService;
    @Autowired
    HttpServletResponse response;


    //获取省份直辖市
    @RequestMapping("getProvinces.do")
    @ResponseBody
    public List<Province> getProvinces(String cityid){
       return areaService.getProvince();
    }

    //获取城市
    @RequestMapping("getCitys.do")
    @ResponseBody
    public List<City> getCitys(String provinceid){
        return areaService.getCity(provinceid);
    }

    //获取区
    @RequestMapping("getZones.do")
    @ResponseBody
    public List<Zone> getZones(String cityid){
        return areaService.getZones(cityid);
    }

    //获取乡镇
    @RequestMapping("getTowns.do")
    @ResponseBody
    public List<Town> getTowns(String zoneid){
        return areaService.getTowns(zoneid);
    }

    //获取街道
    @RequestMapping("getVillages.do")
    @ResponseBody
    public List<Village> getVillages(String townid){
        return areaService.getVillages(townid);
    }


    @RequestMapping("getCityName.do")
    @ResponseBody
    public  String getCityName(String code){
       return areaService.getCityName(code);
    }

    @RequestMapping("getZoneName.do")
    @ResponseBody
    public  String getZoneName(String code) throws UnsupportedEncodingException {
        response.setContentType("application/json;charset=UTF-8");//防止数据传递乱码
        return areaService.getZoneName(code);
    }

    @RequestMapping("getTownName.do")
    @ResponseBody
    public  String getTownName(String code){
        response.setContentType("application/json;charset=UTF-8");//防止数据传递乱码
        return areaService.getTownName(code);
    }

    @RequestMapping("getVillageName.do")
    @ResponseBody
    public  String getVillageName(String code){
        response.setContentType("application/json;charset=UTF-8");//防止数据传递乱码
        return areaService.getVillageName(code);
    }
}
