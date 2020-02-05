package com.bizwink.cms.register;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class relationBetweenOldColumnAndNewColumn {
  private int newColumnid;
  private int oldColumnid;

  public relationBetweenOldColumnAndNewColumn() {

  }

  public void setNewColumnID(int nColumnID)
  {
    this.newColumnid = nColumnID;
  }

  public int getNewColumnID()
  {
    return newColumnid;
  }

  public void setOldColumnID(int oColumnID)
  {
    this.oldColumnid = oColumnID;
  }

  public int getOldColumnID()
  {
    return oldColumnid;
  }
}