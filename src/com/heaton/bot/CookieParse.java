package com.heaton.bot;

/**
 * This class is used to parse cookies that are transmitted
 * with the HTTP headers.
 *
 * Copyright 2001-2003 by Jeff Heaton (http://www.jeffheaton.com)
 *
 * @author Jeff Heaton
 * @version 1.2
 */
public class CookieParse extends Parse {

  /**
   * Special version of the parseAttribute method.
   */
  public void parseAttributeValue()
  {
    eatWhiteSpace();
    if ( eof() )
      return;
    if ( source.charAt(idx)=='=' ) {
      idx++;
      eatWhiteSpace();
      if ( (source.charAt(idx)=='\'') ||
           (source.charAt(idx)=='\"') ) {
        parseDelim = source.charAt(idx);
        idx++;
        while ( source.charAt(idx)!=parseDelim ) {
          parseValue+=source.charAt(idx);
          idx++;
        }
        idx++;
      } else {
        while ( !eof() && (source.charAt(idx)!=';') ) {
          parseValue+=source.charAt(idx);
          idx++;
        }
      }
      eatWhiteSpace();
    }
  }
  /**
   * Called to parse this cookie.
   *
   * @return The return value is unused.
   */

  public boolean get()
  {
    // get the attributes
    while ( !eof() ) {
      parseName="";
      parseValue="";

      parseAttributeName();

      if ( !eof() && (source.charAt(idx)==';') ) {
        addAttribute();
        break;
      }

      // get the value(if any)
      parseAttributeValue();
      addAttribute();

      // move forward to the ; if there is one
      eatWhiteSpace();
      while ( !eof() ) {
        if ( source.charAt(idx++)==';' )
          break;
      }
    }
    idx++;
    return false;
  }

  /**
   * Convert this cookie to a string to be sent as
   * an HTTP header.
   *
   * @return This cookie as a string.
   */
  public String toString()
  {
    String str;
    str = get(0).getName();
    str+= "=";
    str+= get(0).getValue();
    return str;

  }
}
