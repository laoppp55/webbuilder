package com.bizwink.cms.register;

public class resinConfig
{
  private String resinConfigPath;
  private String appPath;
  private String configContent;
  private int errcode = 0;
  private int logowidth;
  private int logoheight;
  private String docpath;

  public String getResinConfigPath()
  {
    return resinConfigPath;
  }

  public void setResinConfigPath(String resinConfigPath)
  {
    this.resinConfigPath  = resinConfigPath;
  }

  public String getAppPath()
  {
    return appPath;
  }

  public void setAppPath(String appPath)
  {
    this.appPath  = appPath;
  }

  public String getConfigContent()
  {
    return configContent;
  }

  public void setConfigContent(String configContent)
  {
    this.configContent = configContent;
  }

  public String getdocpath()
  {
    return docpath;
  }

  public void setdocpath(String docpath)
  {
    this.docpath = docpath;
  }

  public int getlogowidth()
  {
    return logowidth;
  }

  public void setlogowidth(int logowidth)
  {
    this.logowidth = logowidth;
  }

  public int getlogoheight()
  {
    return logoheight;
  }

  public void setlogoheight(int logoheight)
  {
    this.logoheight = logoheight;
  }

  public int getErrorCode()
  {
    return errcode;
  }

  public void setErrorCode(int ErrorCode)
  {
    this.errcode = ErrorCode;
  }
}