/**
 * Group.java
 */
package com.bizwink.cms.security;
public class Right implements Comparable{
    private int  rightID;
    private String  rightName;
    private String  rightCat;
    private String  rightDesc;


    public int getRightID() {
        return rightID;
    }

    public void setRightID(int rightID) {
        this.rightID = rightID;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

     public String getRightCat() {
        return rightCat;
    }

    public void setRightCat(String rightCat) {
        this.rightCat = rightCat;
    }

    public String getRightDesc() {
        return rightDesc;
    }

    public void setRightDesc(String rightDesc) {
        this.rightDesc = rightDesc;
    }

    public int compareTo(Object obj) {
        int rid1 = ((Right)obj).getRightID();
        int rid2 = this.getRightID();

        if (rid1 == rid2)
          return 0;
        else
          return 1;
    }
}