package com.bizwink.cms.security;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-9-7
 * Time: 20:17:17
 * To change this template use File | Settings | File Templates.
 */
public class Role  implements Comparable{
    private int id;
    private int siteid;
    private String userid;
    private String rolename;
    private int rolelevel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getRolelevel() {
        return rolelevel;
    }

    public void setRolelevel(int rolelevel) {
        this.rolelevel = rolelevel;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int compareTo(Object obj)
    {
        String rn1 = ((Role)obj).getRolename();
        String rn2 = this.getRolename();

        if (rn1 == rn2)
            return 0;               //权限ID相同
        else
            return 1;               //权限ID不相同
    }

}
