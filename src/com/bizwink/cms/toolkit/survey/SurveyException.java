package com.bizwink.cms.toolkit.survey;

public class SurveyException extends Exception
{
  int error;
  int errorDetail;
  Object arg;


  //Create a simple exception.
  public SurveyException(String msg)
  {
    this(0, -1, null, msg);
  }


  //Create a new detailed exception with an argument.
  public SurveyException(int error, String msg)
  {
    this(error, -1, null, msg);
  }


  //Create a new detailed exception with an argument.
  public SurveyException(int error, int errorDetail, Object arg, String msg)
  {
    super(msg);
    this.error = error;
    this.errorDetail = errorDetail;
    this.arg = arg;
  }

  //Get major error code.
  public int getError()
  {
    return error;
  }


  //Get error detail code.
  public int getDetail()
  {
    return errorDetail;
  }

  //Get the argument.
  public Object getArgument()
  {
    return arg;
  }

  //Convert to a string for debugging.
  public String toString()
  {
    if (getMessage() != null)
    {
      return super.toString();
    }
    else
    {
      return getClass().getName() + "[" + error + "/" + errorDetail + ": " + arg + "]";
    }
  }
}