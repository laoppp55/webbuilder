package com.bizwink.cms.register;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-2-20
 * Time: 22:54:53
 * To change this template use File | Settings | File Templates.
 */
public class relationBetweenOldViewAndNewView {
    private int newViewid;
    private int oldViewid;

    public relationBetweenOldViewAndNewView() {
    }

    public void setNewViewID(int nViewID)
    {
      this.newViewid = nViewID;
    }

    public int getNewViewID()
    {
      return newViewid;
    }

    public void setOldViewID(int oViewID)
    {
      this.oldViewid = oViewID;
    }

    public int getOldViewID()
    {
      return oldViewid;
    }
}
