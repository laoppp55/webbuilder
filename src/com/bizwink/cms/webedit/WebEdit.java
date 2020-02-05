package com.bizwink.cms.webedit;

import java.sql.Timestamp;

public class WebEdit
{
  private long fileSize;
  private String fileName;
  private Timestamp fileDate;
  private boolean canWrite;
  private boolean isDirectory;

  public void setFileSize(long fileSize)
  {
    this.fileSize = fileSize;
  }

  public long getFileSize()
  {
    return fileSize;
  }

  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }

  public String getFileName()
  {
    return fileName;
  }

  public void setFileDate(Timestamp fileDate)
  {
    this.fileDate = fileDate;
  }

  public Timestamp getFileDate()
  {
    return fileDate;
  }

  public void setCanWrite(boolean canWrite)
  {
    this.canWrite = canWrite;
  }

  public boolean getCanWrite()
  {
    return canWrite;
  }

  public void setIsDirectory(boolean isDirectory)
  {
    this.isDirectory = isDirectory;
  }

  public boolean getIsDirectory()
  {
    return isDirectory;
  }
}