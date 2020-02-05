package com.heaton.bot;
import java.net.*;

/**
 * The HTMLForm class is used to create a response to an HTML form,
 * and then transmit it to a web server.
 *
 * Copyright 2001-2003 by Jeff Heaton (http://www.jeffheaton.com)
 *
 * @author Jeff Heaton
 * @version 1.2
 */
public class HTMLForm extends AttributeList {

  /**
   * The method(i.e. GET or POST)
   */
  protected String method;

  /**
   * The method(i.e. GET or POST)
   */
  protected String post_data;

  /**
   * The action, or the site to post the form to.
   */
  protected String action;

  /**
   * Construct an HTMLForm object.
   *
   * @param method The method(i.e. GET or POST)
   * @param action The action, or the site that the result
   * should be posted to.
   */
  public HTMLForm(String method,String action)
  {
    this.method = method;
    this.action = action;
  }
  /**
   * Call to get the URL to post to.
   *
   * @return The URL to post to.
   */

  public String getAction()
  {
    return action;
  }

  /**
   * @return The method(GET or POST)
   */
  public String getMethod() {
    return method;
  }

  public String getPostData() {
    return post_data;
  }

  public void setPostData(String pdata) {
    this.post_data = pdata;
  }

  /**
   * Add a HTML INPUT item to this form.
   *
   * @param name The name of the input item.
   * @param value The value of the input item.
   * @param type The type of input item.
   */
  public void addInput(String name,String value,String type,String prompt,AttributeList options)
  {
    FormElement e = new FormElement();
    e.setName(name);
    e.setValue(value);
    e.setType(type.toUpperCase());
    e.setOptions(options);
    e.setPrompt(prompt);
    add(e);
  }

  /**
   * Convert this form into the string that would be posted
   * for it.
   */
  public String toString()
  {
    String postdata;

    postdata = "";
    int i=0;
    while ( get(i)!=null ) {
      Attribute a = get(i);
      if ( postdata.length()>0 )
        postdata+="&";
      postdata+=a.getName();
      postdata+="=";
      if ( a.getValue()!=null )
        postdata+=URLEncoder.encode(a.getValue());
      i++;
    }
    return postdata;
  }

  public class FormElement extends Attribute {
    protected String type;
    protected AttributeList options;
    protected String prompt;

    public void setOptions(AttributeList options)
    {
      this.options = options;
    }

    public AttributeList getOptions()
    {
      return options;
    }

    public void setType(String t)
    {
      type = t;
    }

    public String getType()
    {
      return type;
    }

    public String getPrompt()
    {
      return prompt;
    }

    public void setPrompt(String str)
    {
      prompt = str;
    }
  }
}
