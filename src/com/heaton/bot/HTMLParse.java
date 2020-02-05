package com.heaton.bot;
import javax.swing.text.html.*;

/**
 * A VERY simple class meant only to subclass the
 * HTMLEditorKit class to make the getParser method
 * public so that we can gain access to an
 * HTMLEditorKit.Parser object.
 *
 * Copyright 2001-2003 by Jeff Heaton (http://www.jeffheaton.com)
 *
 * @author Jeff Heaton
 * @version 1.2
 */
public class HTMLParse extends HTMLEditorKit {

  /**
   * Call to obtain a HTMLEditorKit.Parser object.
   *
   * @return A new HTMLEditorKit.Parser object.
   */
  public HTMLEditorKit.Parser getParser()
  {
    return super.getParser();
  }
}
