

/**
 * The HTMLParse class is used to parse an HTML page.  It is
 * just a utility class, and does NOT store any values.
 *
 * Copyright 2001-2003 by Jeff Heaton (http://www.jeffheaton.com)
 *
 * @author Jeff Heaton
 * @version 1.2
 */
package com.heaton.bot;
import com.heaton.bot.*;

public class HTMLParser extends Parse {
  public HTMLTag getTag()
  {
    int i;

    HTMLTag tag = new HTMLTag();
    tag.setName(this.tag);

    for ( i=0;i<vec.size();i++ )
      tag.add( (Attribute)get(i).clone() );
    return tag;
  }

  public String buildTag()
  {
    String buffer="<";
    buffer+=tag;
    int i=0;
    while ( get(i)!=null ) {// has attributes
      buffer+=" ";
      if ( get(i).getValue() == null ) {
        if ( get(i).getDelim()!=0 )
          buffer+=get(i).getDelim();
        buffer+=get(i).getName();
        if ( get(i).getDelim()!=0 )
          buffer+=get(i).getDelim();
      } else {
        buffer+=get(i).getName();
        if ( get(i).getValue()!=null ) {
          buffer+="=";
          if ( get(i).getDelim()!=0 )
            buffer+=get(i).getDelim();
          buffer+=get(i).getValue();
          if ( get(i).getDelim()!=0 )
            buffer+=get(i).getDelim();
        }
      }
      i++;
    }
    buffer+=">";
    return buffer;
  }




  protected void parseTag()
  {
    idx++;
    tag="";
    clear();

    // Is it a comment?
    if ( (source.charAt(idx)=='!') &&
         (source.charAt(idx+1)=='-')&&
         (source.charAt(idx+2)=='-') ) {
      while ( !eof() ) {
        if ( (source.charAt(idx)=='-') &&
             (source.charAt(idx+1)=='-')&&
             (source.charAt(idx+2)=='>') )
          break;
        if ( source.charAt(idx)!='\r' )
          tag+=source.charAt(idx);
        idx++;
      }
      tag+="--";
      idx+=3;
      parseDelim=0;
      return;
    }

    // Find the tag name
    while ( !eof() ) {
      if ( isWhiteSpace(source.charAt(idx)) || (source.charAt(idx)=='>') )
        break;
      tag+=source.charAt(idx);
      idx++;
    }

    eatWhiteSpace();

    // get the attributes
    while ( source.charAt(idx)!='>' ) {
      parseName = "";
      parseValue = "";
      parseDelim=0;

      parseAttributeName();

      if( eof() )
          break;
      
      if ( source.charAt(idx)=='>' ) {
        addAttribute();
        break;
      }

      // get the value(if any)
      parseAttributeValue();
      addAttribute();
    }
    idx++;
  }


  public char get()
  {
    if ( source.charAt(idx)=='<' ) {
      char ch=Character.toUpperCase(source.charAt(idx+1));
      if ( (ch>='A') && (ch<='Z') || (ch=='!') || (ch=='/') ) {
        parseTag();
        return 0;
      } else return(source.charAt(idx++));
    } else return(source.charAt(idx++));
  }

}
