package com.bizwink.mysql.po;

import java.io.Serializable;
import java.util.Date;

public class Organization implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.siteid
     *
     * @mbggenerated
     */
    private Integer siteid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.parent
     *
     * @mbggenerated
     */
    private Integer parent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.cotype
     *
     * @mbggenerated
     */
    private Integer cotype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.customerid
     *
     * @mbggenerated
     */
    private Integer customerid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.orderid
     *
     * @mbggenerated
     */
    private Integer orderid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.nodeid
     *
     * @mbggenerated
     */
    private Integer nodeid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.ename
     *
     * @mbggenerated
     */
    private String ename;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.llevel
     *
     * @mbggenerated
     */
    private Integer llevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.orgcode
     *
     * @mbggenerated
     */
    private String orgcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.isleaf
     *
     * @mbggenerated
     */
    private Short isleaf;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.lastupdate
     *
     * @mbggenerated
     */
    private Date lastupdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.createdate
     *
     * @mbggenerated
     */
    private Date createdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.createuser
     *
     * @mbggenerated
     */
    private String createuser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_organization.updateuser
     *
     * @mbggenerated
     */
    private String updateuser;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.id
     *
     * @return the value of tbl_organization.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.id
     *
     * @param id the value for tbl_organization.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.siteid
     *
     * @return the value of tbl_organization.siteid
     *
     * @mbggenerated
     */
    public Integer getSiteid() {
        return siteid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.siteid
     *
     * @param siteid the value for tbl_organization.siteid
     *
     * @mbggenerated
     */
    public void setSiteid(Integer siteid) {
        this.siteid = siteid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.parent
     *
     * @return the value of tbl_organization.parent
     *
     * @mbggenerated
     */
    public Integer getParent() {
        return parent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.parent
     *
     * @param parent the value for tbl_organization.parent
     *
     * @mbggenerated
     */
    public void setParent(Integer parent) {
        this.parent = parent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.cotype
     *
     * @return the value of tbl_organization.cotype
     *
     * @mbggenerated
     */
    public Integer getCotype() {
        return cotype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.cotype
     *
     * @param cotype the value for tbl_organization.cotype
     *
     * @mbggenerated
     */
    public void setCotype(Integer cotype) {
        this.cotype = cotype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.customerid
     *
     * @return the value of tbl_organization.customerid
     *
     * @mbggenerated
     */
    public Integer getCustomerid() {
        return customerid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.customerid
     *
     * @param customerid the value for tbl_organization.customerid
     *
     * @mbggenerated
     */
    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.orderid
     *
     * @return the value of tbl_organization.orderid
     *
     * @mbggenerated
     */
    public Integer getOrderid() {
        return orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.orderid
     *
     * @param orderid the value for tbl_organization.orderid
     *
     * @mbggenerated
     */
    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.nodeid
     *
     * @return the value of tbl_organization.nodeid
     *
     * @mbggenerated
     */
    public Integer getNodeid() {
        return nodeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.nodeid
     *
     * @param nodeid the value for tbl_organization.nodeid
     *
     * @mbggenerated
     */
    public void setNodeid(Integer nodeid) {
        this.nodeid = nodeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.status
     *
     * @return the value of tbl_organization.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.status
     *
     * @param status the value for tbl_organization.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.name
     *
     * @return the value of tbl_organization.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.name
     *
     * @param name the value for tbl_organization.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.ename
     *
     * @return the value of tbl_organization.ename
     *
     * @mbggenerated
     */
    public String getEname() {
        return ename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.ename
     *
     * @param ename the value for tbl_organization.ename
     *
     * @mbggenerated
     */
    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.llevel
     *
     * @return the value of tbl_organization.llevel
     *
     * @mbggenerated
     */
    public Integer getLlevel() {
        return llevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.llevel
     *
     * @param llevel the value for tbl_organization.llevel
     *
     * @mbggenerated
     */
    public void setLlevel(Integer llevel) {
        this.llevel = llevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.orgcode
     *
     * @return the value of tbl_organization.orgcode
     *
     * @mbggenerated
     */
    public String getOrgcode() {
        return orgcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.orgcode
     *
     * @param orgcode the value for tbl_organization.orgcode
     *
     * @mbggenerated
     */
    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode == null ? null : orgcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.isleaf
     *
     * @return the value of tbl_organization.isleaf
     *
     * @mbggenerated
     */
    public Short getIsleaf() {
        return isleaf;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.isleaf
     *
     * @param isleaf the value for tbl_organization.isleaf
     *
     * @mbggenerated
     */
    public void setIsleaf(Short isleaf) {
        this.isleaf = isleaf;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.lastupdate
     *
     * @return the value of tbl_organization.lastupdate
     *
     * @mbggenerated
     */
    public Date getLastupdate() {
        return lastupdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.lastupdate
     *
     * @param lastupdate the value for tbl_organization.lastupdate
     *
     * @mbggenerated
     */
    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.createdate
     *
     * @return the value of tbl_organization.createdate
     *
     * @mbggenerated
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.createdate
     *
     * @param createdate the value for tbl_organization.createdate
     *
     * @mbggenerated
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.createuser
     *
     * @return the value of tbl_organization.createuser
     *
     * @mbggenerated
     */
    public String getCreateuser() {
        return createuser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.createuser
     *
     * @param createuser the value for tbl_organization.createuser
     *
     * @mbggenerated
     */
    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_organization.updateuser
     *
     * @return the value of tbl_organization.updateuser
     *
     * @mbggenerated
     */
    public String getUpdateuser() {
        return updateuser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_organization.updateuser
     *
     * @param updateuser the value for tbl_organization.updateuser
     *
     * @mbggenerated
     */
    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }
}