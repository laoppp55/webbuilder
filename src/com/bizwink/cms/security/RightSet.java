package com.bizwink.cms.security;

/**
 * Title:        cms
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      bizwink
 * @author
 * @version 1.0
 */

import java.util.*;
import java.io.*;

public class RightSet implements Serializable
{

    private TreeSet set = new TreeSet();

    public boolean add(Right right)
    {
        return set.add( (Object)right );
    }

    public boolean add(RightSet rightSet)
    {
        return set.addAll(  (Collection) rightSet.set );
    }

    public boolean remove(Right right)
    {
        return set.remove( (Object)right );
    }

    public void clear()
    {
        set.clear();
    }

    public boolean contains(Right right)
    {
        return set.contains( (Object)right );
    }

    public boolean contains(int rightID)
    {
        Iterator iter = set.iterator();
        while ( iter.hasNext() )
        {
            Right right = (Right)iter.next();
            if ( rightID !=0 && rightID == right.getRightID())
            {
                return true;
            }
        }
        return false;
    }

    public Right getRight(int rightID)
    {
        Iterator iter = set.iterator();
        while ( iter.hasNext() )
        {
            Right right = (Right)iter.next();
            if ( rightID != 0 && rightID == right.getRightID())
            {
                return right;
            }
        }
        return null;
    }

    public Iterator elements()
    {
        return set.iterator();
    }

    public int size()
    {
        return set.size();
    }
}