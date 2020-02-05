package com.bizwink.cms.security;

public class RightsArr {

  private String[] rightIDs;

  public RightsArr(String[] rightIDs) {
    this.rightIDs = rightIDs;
  }

  public boolean hasRightID ( String rightID ){
    boolean flag = false;

    if (rightIDs == null) return flag;

    for (int i = 0; i < rightIDs.length; i++ ){
      if (rightIDs[i] == null) continue;
      if(rightIDs[i].equalsIgnoreCase(rightID)){
        flag = true;
        break;
      }
    }
    return flag;
  }
}