package com.bizwink.cms.toolkit.company;

import java.util.List;


public interface ICompanyManager{


    List getCompanyInfo(int start, int range,String sql); //根据comapnytype得到中文公司列表还是英文公司列表 companytype=0为中文 companytype=1为英文

    int getCompanyNum(String sql);                       //根据companytype=0得到所有中文公司的数量，companytype=1得到英文公司的数量

    Company getACompanyInfo(int id);                          //根据ID得到公司的一个对象

    void addCompany(Company company);                         //添加公司

    void delCompany(int id);                                  //删除公司

    void modifyCompany(Company company);                               //修改公司信息

}