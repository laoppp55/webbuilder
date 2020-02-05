package com.unittest;

/**
 * Created by Administrator on 18-2-10.
 */
public class ClsAttrs {
    private String clsname;                       //小类名称
    private String templatename;                 //模板名称
    private int orderno;                         //特征量序号
    private String tzname;                        //特征量名称
    private String prefix;                        //前置符号
    private String prefixallownull;             //前置是否可以为空
    private String needflag;                     //特征量是否必填
    private String needchoiceed;                //特征量是否必选
    private String isnum;                         //特征量是否是数字
    private String max_min;                      //特征量是否使用上下限
    private String maxval;                       //上限
    private String minval;                       //下限
    private String suffix;                       //后置符号
    private String suffixallownull;             //后置是否可以为空
    private String unit;                          //计量单位
    private String link;                         //链接符号

    public String getClsname() {
        return clsname;
    }

    public void setClsname(String clsname) {
        this.clsname = clsname;
    }

    public String getTemplatename() {
        return templatename;
    }

    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }

    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    public String getTzname() {
        return tzname;
    }

    public void setTzname(String tzname) {
        this.tzname = tzname;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefixallownull() {
        return prefixallownull;
    }

    public void setPrefixallownull(String prefixallownull) {
        this.prefixallownull = prefixallownull;
    }

    public String getNeedflag() {
        return needflag;
    }

    public void setNeedflag(String needflag) {
        this.needflag = needflag;
    }

    public String getNeedchoiceed() {
        return needchoiceed;
    }

    public void setNeedchoiceed(String needchoiceed) {
        this.needchoiceed = needchoiceed;
    }

    public String getIsnum() {
        return isnum;
    }

    public void setIsnum(String isnum) {
        this.isnum = isnum;
    }

    public String getMax_min() {
        return max_min;
    }

    public void setMax_min(String max_min) {
        this.max_min = max_min;
    }

    public String getMaxval() {
        return maxval;
    }

    public void setMaxval(String maxval) {
        this.maxval = maxval;
    }

    public String getMinval() {
        return minval;
    }

    public void setMinval(String minval) {
        this.minval = minval;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffixallownull() {
        return suffixallownull;
    }

    public void setSuffixallownull(String suffixallownull) {
        this.suffixallownull = suffixallownull;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
