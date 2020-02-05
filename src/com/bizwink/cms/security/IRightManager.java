/**
 * IUserManager.java
 */

package com.bizwink.cms.security;

import java.util.*;

import com.bizwink.cms.util.*;

public interface IRightManager {

    void create(Right right) throws CmsException;

    void update(Right right) throws CmsException;

    void remove(Right right) throws CmsException;

    int  getRightCount() throws CmsException;

    Right getRight(String rightID) throws  CmsException;

    List getRights(int startIndex, int numResults) throws  CmsException;

    List getRights() throws  CmsException;

}
