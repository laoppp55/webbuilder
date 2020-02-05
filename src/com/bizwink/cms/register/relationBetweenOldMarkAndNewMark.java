package com.bizwink.cms.register;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class relationBetweenOldMarkAndNewMark {

  private int newMarkid;
  private int oldMarkid;

  public relationBetweenOldMarkAndNewMark() {
  }

  public void setNewMarkID(int nMarkID)
  {
    this.newMarkid = nMarkID;
  }

  public int getNewMarkID()
  {
    return newMarkid;
  }

  public void setOldMarkID(int oMarkID)
  {
    this.oldMarkid = oMarkID;
  }

  public int getOldMarkID()
  {
    return oldMarkid;
  }
}