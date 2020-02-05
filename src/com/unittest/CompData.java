package com.unittest;

import java.util.List;

public class CompData {
    private String name;                          //公司名称
    private String postcode;                     //邮政编码
    private String address;                      //公司地址
    private String telephone;                    //公司联系电话
    private String fax;                           //公司传真
    private float assets;                       //公司资产
    private float revenue;                      //公司产值
    private int staffs;                         //公司员工数量
    private int comptype;                       //公司类型      有限责任公司，股份合作，国有，私营企业，中外合资经营企业，与港澳台商合资经营，集体
    private int compscale;                      //企业规模      大型，
    private int impexp;                         //公司是否有进出口权
    private String legal;                       //公司法人
    private String website;                     //公司网址
    private String email;                       //公司电子邮件地址
    private String supplyAndSellPhone;        //公司供销电话
    private String supplyAndSellFax;           //公司供销传真
    private List<String> products;              //公司生产的主要产品

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public float getAssets() {
        return assets;
    }

    public void setAssets(float assets) {
        this.assets = assets;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public int getStaffs() {
        return staffs;
    }

    public void setStaffs(int staffs) {
        this.staffs = staffs;
    }

    public int getComptype() {
        return comptype;
    }

    public void setComptype(int comptype) {
        this.comptype = comptype;
    }

    public int getCompscale() {
        return compscale;
    }

    public void setCompscale(int compscale) {
        this.compscale = compscale;
    }

    public int getImpexp() {
        return impexp;
    }

    public void setImpexp(int impexp) {
        this.impexp = impexp;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSupplyAndSellPhone() {
        return supplyAndSellPhone;
    }

    public void setSupplyAndSellPhone(String supplyAndSellPhone) {
        this.supplyAndSellPhone = supplyAndSellPhone;
    }

    public String getSupplyAndSellFax() {
        return supplyAndSellFax;
    }

    public void setSupplyAndSellFax(String supplyAndSellFax) {
        this.supplyAndSellFax = supplyAndSellFax;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
}
