
/**
 * The HTMLTag class is used to store an HTML tag.  This
 * includes the tag name and any attributes.
 *
 * Copyright 2001-2003 by Jeff Heaton (http://www.jeffheaton.com)
 *
 * @author Jeff Heaton
 * @version 1.2
 */
package com.heaton.bot;
import com.heaton.bot.*;

public class HTMLTag extends AttributeList implements Cloneable {
  protected String name;

  public Object clone()
  {
    int i;
    AttributeList rtn = new AttributeList();
    for ( i=0;i<vec.size();i++ )
      rtn.add( (Attribute)get(i).clone() );
    rtn.setName(name);

    return rtn;
  }

  public void setName(String s)
  {
    name = s;
  }

  public String getName()
  {
    return name;
  }

  public String getAttributeValue(String name)
  {
    Attribute a = get(name);
    if ( a==null )
      return null;
    return a.getValue();
  }
}
