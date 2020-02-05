package com.heaton.bot;

import java.util.zip.*;
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.Calendar;
import java.util.Locale;


/**
 * This class implements an HTTP handler.  The low-level
 * details are left to a derived class.  Most likely you
 * will be using the HTTPSocket class, which derives from
 * this one.  This class handles these specific issues:
 *
 * + Cookies
 * + The referrer tag
 * + HTTP User Authentication
 * + Automatic Redirection
 * + Parsing headers
 *
 * Copyright 2001-2003 by Jeff Heaton (http://www.jeffheaton.com)
 *
 * @author Jeff Heaton
 * @version 1.0
 */
public abstract class HTTP {

  /**
   * The body data that was downloaded during the last send.
   */
  protected byte body[];

  /**
   * The raw header text that was recieved during the last
   * send.
   */
  protected StringBuffer header = new StringBuffer();

  /**
   * The URL that was ultimately used during the last send.
   */
  protected String url;

  protected String proxyhost;

  protected String proxyport;

  /**
   * True if the HTTP class should automatically follow HTTP
   * redirects.
   */
  protected boolean autoRedirect = true;

  /**
   * The cookies that are being tracked by this HTTP session.
   */
  protected AttributeList cookieStore = new AttributeList();

  /**
   * The client headers, these are the headers that are sent
   * to the server.
   */
  protected AttributeList clientHeaders = new AttributeList();

  /**
   * The server headers, these are the the headers that the
   * server sends back.
   */
  protected AttributeList serverHeaders = new AttributeList();

  /**
   * True if session cookies should be used.
   */
  protected boolean useCookies = false;

  /**
   * True if permanent cookies should be used.
   */
  protected boolean usePermCookies = false;

  /**
   * The HTTP response(i.e. OK).  If NOT okay, this response is
   * thrown as an exception.
   */
  protected String response;

  /**
   * The timeout in milliseconds.
   */
  protected int timeout = 30000;

  /**
   * The page previously on.  Used to accurately represent the
   * referrer header.
   */
  protected String referrer = null;

  /**
   * The agent(or browser) that the HTTP class is reporting
   * to the server.
   */
  protected String agent = "Mozilla/4.0";

  /**
   * The user being reported for HTTP authentication.
   */
  protected String user = "";

  /**
   * The password being reported for user authentication.
   */
  protected String password = "";

  /**
   * The max size that a HTTP body can be.
   */
  protected int maxBodySize = -1;

  /**
   * The actual page that was requested, URL may have been redirected
   * elsewhere.
   */
  protected String rootURL;
  protected int err;

  /**
   * The copy method is used to create a new copy of this HTTP handler.
   * This method is implemented abstract and should be implemented by
   * derived classes.
   *
   * @return A new HTTP handler.
   */
  abstract HTTP copy();

  /**
   * This method actually sends the data.  This is where derived
   * classes implement their own low-level send.
   *
   * @param url The URL to send data to.
   * @param post Any data that is to be posted.  If no data is posted, this method
   * does an HTTP GET.
   * @exception java.net.UnknownHostException Thrown if the host can not be located.
   * @exception java.io.IOException Thrown if a network error occurs.
   */
  abstract protected void lowLevelSend(String url,String post) throws java.net.UnknownHostException, java.io.IOException;

  /**
   * This method is called to clear any cookies that this HTTP
   * object might be holding.
   */
  public void clearCookies()
  {
    cookieStore.clear();
  }

  /**
   * This method is called to add any applicable cookies to the
   * header.
   */
  protected void addCookieHeader()
  {
    int i=0;
    String str="";

    if ( cookieStore.get(0)==null )
      return;

    while ( cookieStore.get(i) != null ) {
      CookieParse cookie;
      if ( str.length()!=0 )
        str+="; ";// add on ; if needed
      cookie = (CookieParse)cookieStore.get(i);
      str+=cookie.toString();
      i++;
    }

    clientHeaders.set("Cookie",str);
    Log.log(Log.LOG_LEVEL_TRACE,"Send cookie: " + str );
  }

  /**
   * This method is called to add the user authorization headers
   * to the HTTP request.
   */
  protected void addAuthHeader()
  {
    if ( (user.length()==0) || (password.length()==0) )
      return;
    String hdr = user + ":" + password;
    String encode = URLUtility.base64Encode(hdr);
    clientHeaders.set("Authorization","Basic " + encode );
  }

  /**
   * Send is the public interface to this class that actually sends
   * an HTTP request.
   *
   * @paramurl The URL being sent to.
   * @param post Any data to post.
   * @exception java.net.UnknownHostException
   * @exception java.io.IOException
   */
  public void send(String requestedURL,String post) throws HTTPException,java.net.UnknownHostException,java.io.IOException
  {
    int rtn; // the return value

    java.util.Date today = new java.util.Date(System.currentTimeMillis());
    java.util.Date lastdate = new java.util.Date(today.getYear(),today.getMonth(),today.getDate()-1,0,0,0);
    //Fri, 12 May 2006 18:53:33 GMT
    Locale localeEN = new Locale("en", "US" );
    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("E, MMM dd yyyy hh:mm:ss G",localeEN);
    //DateFormat dateFormat =DateFormat.getDateTimeInstance(DateFormat.MEDIUM ,DateFormat.ERA_FIELD,localeEN);
    Calendar cal = Calendar.getInstance();
    cal.set(today.getYear()+1900,today.getMonth(),today.getDate()-1,0,0,0);
    //System.out.println(StringUtil.replace(df.format(cal.getTime()),"AD","GMT"));

    //Fri, 12 May 2006 18:53:33 GMT
    java.text.SimpleDateFormat hh_f = new java.text.SimpleDateFormat();

    if ( post==null )
      Log.log(Log.LOG_LEVEL_NORMAL,"HTTP GET " + requestedURL );
    else
      Log.log(Log.LOG_LEVEL_NORMAL,"HTTP POST " + requestedURL );

    rootURL = requestedURL;
    setURL(requestedURL);

    if ( clientHeaders.get("referrer")==null )
      if ( referrer!=null )
        clientHeaders.set("referrer",referrer.toString());

    if ( clientHeaders.get("Accept")==null )
      clientHeaders.set("Accept","image/gif,"
                        + "image/x-xbitmap,image/jpeg,"
                        + "image/pjpeg, application/vnd.ms-excel,"
                        + "application/msword,"
                        + "application/vnd.ms-powerpoint, */*");

    if ( clientHeaders.get("Accept-Language")==null )
      clientHeaders.set("Accept-Language","en-us");
    if ( clientHeaders.get("User-Agent")==null )
      clientHeaders.set("User-Agent",agent);
    if (clientHeaders.get("If-Modified-Since")==null )
      clientHeaders.set("If-Modified-Since",StringUtil.replace(df.format(cal.getTime()),"AD","GMT"));

    while ( true ) {
      if ( useCookies ) addCookieHeader();
      addAuthHeader();
      lowLevelSend(this.url,post);

      if ( useCookies ) parseCookies();
      Attribute encoding = serverHeaders.get("Content-Encoding");
      if ( (encoding!=null) && "gzip".equalsIgnoreCase(encoding.getValue()) )
        processGZIP();

      Attribute a = serverHeaders.get("Location");

      if ( (a==null) || !autoRedirect ) {
        referrer = getURL();
        return;
      }

      URL u = new URL(new URL(url),a.getValue());
      Log.log(Log.LOG_LEVEL_NORMAL,"HTTP REDIRECT to " + u.toString() );
      setURL(u.toString());
    }
  }

  /**
   * This method is called to get the body data that was returned
   * by this request as a string.
   *
   * @return The body data for the last request.
   */
  public String getBody()
  {
    return new String(body);
  }


  /**
   * This method is called to get the body data that was returned
   * by this request as an encoded string.
   * @param enc How to encode the string.
   * @exception UnsupportedEncodingException
   * @return The body data for the last request.
   */
  public String getBody(String enc)
  throws UnsupportedEncodingException
  {
    return new String(body,enc);
  }

  /**
   * This method is called to get the body data that was returned
   * by this request as a byte-array. This is more efficent than
   * getting the data as a sting, as the data is stored internally
   * as a byte-array.
   *
   * @return The body data for the last request.
   */
  public byte[] getBodyBytes()
  {
    return body;
  }

  /**
   * Get the URL that was ultimately requested. You might get a
   * different URL than was requested if auto redirection is
   * enabled.
   */
  public String getURL()
  {
    return url;
  }

  /**
   * Called to set the internal URL that is kept for the last
   * request.
   *
   * @param u The new URL.
   */
  public void setURL(String u)
  {
    url = u;
  }

  public void setProxyHost(String host) {
    this.proxyhost = host;
  }

  public void setProxyPort(String port) {
    this.proxyport = port;
  }

  /**
   * Used to determine if the HTTP class should automatically
   * resolve any HTTP redirects.
   *
   * @param b True to resolve redirects, false otherwise.
   */
  public void SetAutoRedirect(boolean b)
  {
    autoRedirect = b;
  }

  /**
   * This method will return an AttributeLIst of client headers.
   *
   * @return An AttributeList of client headers.
   */
  public AttributeList getClientHeaders()
  {
    return clientHeaders;
  }
  /**
   * This method will return an AttributeList of server headers.
   *
   * @return An AttributeList of server headers.
   */

  public AttributeList getServerHeaders()
  {
    return serverHeaders;
  }

  /**
   * This method is called internally to parse any cookies and add
   * them to the cookie store.
   */
  protected void parseCookies()
  {
    Attribute a;

    int i=0;
    while ( (a = serverHeaders.get(i++)) != null ) {
      if ( a.getName().equalsIgnoreCase("set-cookie") ) {
        CookieParse cookie = new CookieParse();

        cookie.source=new StringBuffer(a.getValue());
        cookie.get();
        cookie.setName(cookie.get(0).getName());
        if ( cookieStore.get(cookie.get(0).getName())==null ) {
          if ( (cookie.get("expires")==null) ||
               ( cookie.get("expires")!=null) && usePermCookies )
            cookieStore.add(cookie);
        }
        Log.log(Log.LOG_LEVEL_TRACE,"Got cookie: " + cookie.toString() );
      }
    }
  }

  /**
   * This method is called uncompress a GZIP encoded document.
   */
  protected void processGZIP()
  throws IOException
  {
    ByteArrayInputStream bis = new ByteArrayInputStream(body);
    GZIPInputStream is = new GZIPInputStream(bis);

    StringBuffer newBody = new StringBuffer();
    ByteList byteList = new ByteList();
    byteList.read(is,-1);
    body = byteList.detach();
  }

  /**
   * This method is used to access a specific cookie.
   *
   * @param name The name of the cookie being sought.
   * @return The cookie being sought, or null.
   */

  public CookieParse getCookie(String name)
  {
    return((CookieParse)cookieStore.get(name));
  }
  /**
   * This method controls the use of cookies by this HTTP object.
   *
   * @param session True to use session cookies, false not to.
   * @param perm True to use permanent cookies, false not to.
   */

  public void setUseCookies(boolean session,boolean perm)
  {
    useCookies = session;
    usePermCookies = perm;
  }
  /**
   * This method allows you to determine if session cookies
   * are being used.
   *
   * @return True if session cookies are being used.
   */

  public boolean getUseCookies()
  {
    return useCookies;
  }
  /**
   * This method allows you to determine if permanent cookies
   * are being used.
   *
   * @return Returns true if permanent cookies are being used, false otherwise.
   */

  public boolean getPerminantCookies()
  {
    return usePermCookies;
  }

  protected void processResponse(String name)
  throws java.io.IOException
  {
    int i1,i2;
    response = name;
    i1 = response.indexOf(' ');
    if ( i1!=-1 ) {
      i2 = response.indexOf(' ',i1+1);
      if ( i2!=-1 ) {
        try {
          err=Integer.parseInt(response.substring(i1+1,i2));
        } catch ( Exception e ) {
        }
      }
    }
    Log.log(Log.LOG_LEVEL_TRACE,"Response: " + name );
  }

  /**
   * This method is called internally to process user headers.
   *
   * @exception java.io.IOException Thrown if a network error occurs.
   */
  protected void parseHeaders() throws java.io.IOException
  {
    boolean first;
    String name,value;
    int l;

    first = true;

    serverHeaders.clear();
    name = "";

    String parse = new String(header);

    for ( l=0;l<parse.length();l++ ) {
      if ( parse.charAt(l)=='\n' ) {
        if ( name.length()==0 )
          return;
        else {
          if ( first ) {
            first = false;
            processResponse(name);
          } else {
            int ptr=name.indexOf(':');
            if ( ptr!=-1 ) {
              value = name.substring(ptr+1).trim();
              name = name.substring(0,ptr);
              //System.out.println(name + "=====" + value);
              Attribute a = new Attribute(name,value);
              serverHeaders.add(a);
              //Log.log(Log.LOG_LEVEL_TRACE,"Sever Header:" + name + "=" + value);
            }
          }
        }
        name = "";
      } else if ( parse.charAt(l)!='\r' )
        name+=parse.charAt(l);
    }
  }

  /**
   * This method is called to set the amount of time that the
   * HTTP class will wait for the requested resource.
   *
   * @param i The timeout in milliseconds.
   */
  public void setTimeout(int i)
  {
    timeout = i;
  }

  /**
   * This method will return the amount of time in milliseconds
   * that the HTTP object will wait for a timeout.
   */
  public int getTimeout()
  {
    return timeout;
  }

  /**
   * This method will set the maximum body size
   * that will be downloaded.
   *
   * @param i The maximum body size, or -1 for unlimted.
   */
  public void setMaxBody(int i)
  {
    maxBodySize = i;
  }

  /**
   * This method will return the maximum body size
   * that will be downloaded.
   *
   * @return The maximum body size, or -1 for unlimted.
   */
  public int getMaxBody()
  {
    return maxBodySize;
  }


  /**
   * Returns the user agent being reported by this HTTP class.
   * The user agent allows the web server to determine what
   * software the web-browser is using.
   *
   * @return The agent string being presented to web servers.
   */
  public String getAgent()
  {
    return agent;
  }

  /**
   * This method is called to set the agent reported to the browser.
   *
   * @param a The user agent string to be reported to servers.
   */
  public void setAgent(String a)
  {
    agent = a;
  }

  /**
   * This method is called to set the user id for HTTP user
   * authentication.
   *
   * @param u The user id.
   */
  public void setUser(String u)
  {
    user = u;
  }

  /**
   * This method is used to set the user's password for HTTP
   * user authentificaiton.
   *
   * @param p The password.
   */
  public void setPassword(String p)
  {
    password = p;
  }

  /**
   * This method is used to return the user id that is being
   * used for HTTP authentification.
   *
   * @return The user id.
   */
  public String getUser()
  {
    return user;
  }

  /**
   * This method is used to get the password that is being used
   * for user authentication.
   *
   * @return The password.
   */
  public String getPassword()
  {
    return password;
  }

  /**
   * This method is used to get the referrer header
   *
   * @return The referrer header.
   */
  public String getReferrer()
  {
    return referrer;
  }

  /**
   * This method is used to set the referrer header
   *
   * @param p The referrer header.
   */
  public void setReferrer(String p)
  {
    referrer = p;
  }

  /**
   * This method will return an AttributeList of cookies.
   *
   * @return An AttributeList of server headers.
   */
  public AttributeList getCookies()
  {
    return cookieStore;
  }

  /**
   * This method will return the first URL that was requested. This
   * will be different than the current URL when redirection has occured.
   *
   * @return The root URL.
   */
  public String getRootURL()
  {
    return rootURL;
  }

  public String getContentType()
  {
      Attribute a = serverHeaders.get("Content-Type");
      if( a==null )
          return null;
      else
          return a.getValue();
  }
}
