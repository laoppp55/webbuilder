package com.heaton.bot;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class HTMLiFrame {
  protected String url;

  public HTMLiFrame() {

  }

  public HTMLiFrame(String url) {
    this.url = url;
  }

  public String getURL()
  {
    return url;
  }

  public void setURL(String url)
  {
    this.url = url;
  }
}