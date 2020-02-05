/**
 * The Parse class is the low-level text parsing class
 * that all other parsing classes are based on.
 *
 * Copyright 2001-2003 by Jeff Heaton (http://www.jeffheaton.com)
 *
 * @author Jeff Heaton
 * @version 1.2
 */
package com.heaton.bot;
import com.heaton.bot.*;

class Parse extends AttributeList {
  public StringBuffer source;
  protected int idx;
  protected char parseDelim;
  protected String parseName;
  protected String parseValue;
  public String tag;

  public static boolean isWhiteSpace(char ch)
  {
    return( "\t\n\r ".indexOf(ch) != -1 );
  }

  public void eatWhiteSpace()
  {
    while ( !eof() ) {
      if ( !isWhiteSpace(source.charAt(idx)) )
        return;
      idx++;
    }
  }

  public boolean eof()
  {
    return(idx>=source.length() );
  }

  public void parseAttributeName()
  {
    eatWhiteSpace();
    // get attribute name
    if ( (source.charAt(idx)=='\'')
       || (source.charAt(idx)=='\"') ) {
      parseDelim = source.charAt(idx);
      idx++;
      while ( source.charAt(idx)!=parseDelim ) {
        parseName+=source.charAt(idx);
        idx++;
      }
      idx++;
    } else {
      while ( !eof() ) {
        if ( isWhiteSpace(source.charAt(idx)) ||
             (source.charAt(idx)=='=') ||
             (source.charAt(idx)=='>') )
          break;
        parseName+=source.charAt(idx);
        idx++;
      }
    }
    eatWhiteSpace();
  }

  public void parseAttributeValue()
  {
    if ( parseDelim!=0 )
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
        while ( !eof() &&
                !isWhiteSpace(source.charAt(idx)) &&
                (source.charAt(idx)!='>') ) {
          parseValue+=source.charAt(idx);
          idx++;
        }
      }
      eatWhiteSpace();
    }
  }


  void addAttribute()
  {
    Attribute a = new Attribute(parseName,
      parseValue,parseDelim);
    add(a);
  }

  String getParseName()
  {
    return parseName;
  }

  void setParseName(String s)
  {
    parseName = s;
  }

  String getParseValue()
  {
    return parseValue;
  }

  void setParseValue(String s)
  {
    parseValue = s;
  }

  char getParseDelim()
  {
    return parseDelim;
  }

  void setParseDelim(char s)
  {
    parseDelim = s;
  }

}
