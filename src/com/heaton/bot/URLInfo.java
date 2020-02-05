package com.heaton.bot;

import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class URLInfo {
  private int id;
  private URL url_s;
  private int urltype;
  private String title;

  public int getID() {
    return id;
  }

  public void setID(int id) {
    this.id = id;
  }

  public URL getURLInfo() {
    return url_s;
  }

  public void setURLInfo(URL url) {
    this.url_s = url;
  }

  public int getURLType() {
    return urltype;
  }

  public void setURLType(int type) {
    this.urltype = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}