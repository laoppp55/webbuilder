package com.bizwink.mupload;

import java.applet.AppletContext;
import java.awt.Component;
import java.awt.Container;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.contrib.ssl.EasySSLProtocolSocketFactory;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import sun.misc.BASE64Encoder;
import sun.misc.CharacterEncoder;

// Referenced classes of package JUpload:
//            HTTPRequest, Configurator, UploadStatus, JUpload, 
//            startup, LoginFrame, MimeHeader, ProxyConfig, 
//            MyFile

public class HTTPPostRequest extends Thread
    implements HTTPRequest
{

    protected static int threadCounter = 0;
    String boundary;
    String strHostname;
    String tagName;
    URL actionURL;
    Vector arrFilenames;
    int counter;
    int iPort;
    private DefaultListModel tModel;
    private InputStream httpIn;
    private InputStreamReader reader;
    private JUpload applet;
    private OutputStream httpOut;
    private OutputStreamWriter writer;
    private Socket socket;
    private String newline;
    private String response;
    private String strHeader;
    private UploadStatus myStatus;
    private Vector arrMimeHeaders;
    private boolean LastRequest;
    private boolean finished;
    private boolean running;
    private boolean status;
    private int contentLength;

    public HTTPPostRequest(JUpload parent, DefaultListModel tModel)
    {
        boundary = "-----------------------v7sdfosd7idf9wkfzh7ylqa8DyIq";
        tagName = Configurator.getHTTPTagName();
        arrFilenames = new Vector();
        counter = 0;
        newline = System.getProperty("line.separator");
        arrMimeHeaders = new Vector();
        LastRequest = false;
        finished = false;
        running = false;
        status = false;
        debug("HTTPPostRequest() Constructor. parent is " + parent);
        setPriority(1);
        this.tModel = tModel;
        applet = parent;
    }

    public void setActionURL(URL url)
    {
        debug("HTTPPostRequest() setting action URL");
        actionURL = url;
        if(url.getPort() != -1)
            iPort = url.getPort();
        else
            iPort = 80;
        strHostname = url.getHost();
    }

    public boolean isFinished()
    {
        return finished;
    }

    public void setLastRequest(boolean b)
    {
        LastRequest = b;
    }

    public boolean isRunning()
    {
        return running;
    }

    public void addFile(File fileToUpload)
    {
        arrFilenames.addElement(fileToUpload);
    }

    public void debug(String s)
    {
        if(Configurator.getDebug())
            System.out.println(s);
    }

    public boolean performRequest()
        throws IOException
    {
        debug("HTTPPostRequest() starting REQUEST");
        myStatus = new UploadStatus();
        applet.statuspanel.add(myStatus);
        applet.controlPanel.validate();
        MultipartPostMethod filePost = new MultipartPostMethod(actionURL.toString());
        addFilesToPost(filePost);
        if(actionURL.getProtocol().equalsIgnoreCase("https"))
        {
            debug("HTTPPostRequest() performRequest() registering Secure protocol");
            try
            {
                Protocol.registerProtocol("https", new Protocol("https", new EasySSLProtocolSocketFactory(), 443));
            }
            catch(Exception e)
            {
                debug("***PROBLEM REGISTERING SECURE HTTP***");
                e.printStackTrace();
            }
        }
        if(!createHttpConnection())
        {
            debug("HTTPPostRequest() could not create connection to webserver.");
            applet.statuspanel.remove(myStatus);
            applet.controlPanel.repaint();
            return false;
        }
        debug("HTTPPostRequest() performRequest() creating files headers");
        createHeaders();
        debug("HTTPPostRequest() performRequest() calculating sizes of files");
        calculateSizes();
        debug("HTTPPostRequest() performRequest() creating http header string");
        createHTTPHeader();
        debug("HTTPPostRequest() performRequest() creating streams");
        createStreams(httpOut, httpIn);
        debug("HTTPPostRequest() performRequest() sending http header");
        sendHeader();
        debug("HTTPPostRequest() performRequest() checking for writer=" + writer);
        if(writer == null)
        {
            applet.statuspanel.remove(myStatus);
            applet.controlPanel.repaint();
            return false;
        }
        writer.write("--" + boundary);
        debug("HTTPPostRequest() performRequest() iterating files");
        iterateFiles();
        debug("HTTPPostRequest() performRequest() flushing writer buffer");
        writer.flush();
        receiveCheckResponse();
        debug("HTTPPostRequest() removing status bar and repainting");
        applet.statuspanel.remove(myStatus);
        applet.controlPanel.repaint();
        applet.validate();
        applet.doLayout();
        debug("HTTPPostRequest() REQUEST ends");
        debug("Upload finished.");
        if(LastRequest)
        {
            debug("HTTPPostRequest() performRequest() uploadapplet is " + applet.applet);
            URL urlCompleteURL = Configurator.getCompleteURL();
            if(urlCompleteURL != null)
            {
                debug("HTTPPostRequest() performRequest() completeURL is not empty. Redirecting user...to=" + urlCompleteURL);
                applet.applet.context.showDocument(urlCompleteURL);
            }
        }
        debug("HTTPPostRequest() performRequest() finished.");
        return true;
    }

    public void run()
    {
        running = true;
        debug("Upload thread started and running...");
        try
        {
            performRequest();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        debug("Upload thread ended...");
        applet.validate();
        running = false;
        finished = true;
    }

    private String WWWAuthentificationHeader()
    {
        LoginFrame loginframe = new LoginFrame();
        StringBuffer theUID = (new StringBuffer()).append(loginframe.getUsername());
        if(loginframe.getPassword() != null)
            theUID.append(":").append(loginframe.getPassword());
        String encoding = (new BASE64Encoder()).encode(theUID.toString().getBytes());
        String strAuthHeader = "Authorization: Basic " + encoding + newline;
        debug("HTTPPostRequest() WWWAuthentificationHeader() is [" + strAuthHeader + "]");
        return strAuthHeader;
    }

    private void addFilesToPost(MultipartPostMethod filePost)
    {
        try
        {
            File targetFile;
            for(Iterator iter = arrMimeHeaders.iterator(); iter.hasNext(); filePost.addParameter(targetFile.getName(), targetFile))
            {
                MimeHeader mimeheader = (MimeHeader)iter.next();
                targetFile = mimeheader.getFile();
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void calculateSizes()
    {
        contentLength = 0;
        for(Iterator iter = arrMimeHeaders.iterator(); iter.hasNext();)
        {
            MimeHeader element = (MimeHeader)iter.next();
            contentLength += element.getHeader().length();
            contentLength += element.getContentLength();
            contentLength += element.getFooter().length();
        }

    }

    private boolean checkSuccess(String response)
    {
        StringTokenizer tok = new StringTokenizer(response);
        String strHTTPVersion = tok.nextToken();
        String strStatusCode = tok.nextToken();
        String strReasonPhrase = tok.nextToken("\r\n");
        int iStatusCode = Integer.parseInt(strStatusCode);
        if(iStatusCode >= 500)
        {
            showErrorMessage(strStatusCode + ": Server Error - The server failed to fulfill an apparently valid request\n" + "Reason: " + strReasonPhrase);
            return false;
        }
        if(iStatusCode >= 400)
        {
            showErrorMessage(strStatusCode + ": Client Error - The request contains bad syntax or cannot be fulfilled\n" + "Reason: " + strReasonPhrase);
            return false;
        }
        if(iStatusCode >= 300)
        {
            showErrorMessage(strStatusCode + ": Redirection - Further action must be taken in order to complete the request\n" + "Reason: " + strReasonPhrase);
            return false;
        }
        if(iStatusCode == 200 && Configurator.getShowSuccessDialog())
            JOptionPane.showMessageDialog(applet, Configurator.getSuccessDialogMessage(), Configurator.getSuccessDialogTitle(), 1);
        if(iStatusCode >= 100 && iStatusCode < 200)
        {
            showErrorMessage(strStatusCode + ": Informational - Request received, continuing process\n" + "Reason: " + strReasonPhrase);
            return false;
        } else
        {
            return true;
        }
    }

    private void closeStreams()
        throws IOException
    {
        writer.close();
        try
        {
            reader.close();
            socket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void createHTTPHeader()
    {
        String strQueryString = actionURL.getQuery();
        if(strQueryString == null)
            strQueryString = "";
        if(strQueryString.equalsIgnoreCase(""))
        {
            debug("HTTPPostRequest() createHTTPHeader() actionURL.getQuery is empty.");
            strQueryString = "";
        } else
        {
            debug("HTTPPostRequest() createHTTPHeader() query string added.");
            strQueryString = "?" + actionURL.getQuery();
        }
        if(ProxyConfig.useProxy)
            strHeader = "POST " + actionURL + " HTTP/1.1";
        else
            strHeader = "POST " + actionURL.getPath() + strQueryString + " HTTP/1.1";
        strHeader += newline;
        strHeader += "Host: " + actionURL.getHost();
        strHeader += newline;
        strHeader += "Connection: close";
        strHeader += newline;
        if(!Configurator.getBrowserCookie().equalsIgnoreCase(""))
        {
            debug("HTTPPostRequest() createHTTPHeader() adding Cookie header from parameter");
            strHeader += "Cookie: " + Configurator.getBrowserCookie();
            strHeader += newline;
        }
        strHeader += "User-agent: JUpload Applet (http://www.haller-systemservice.net/jupload/)";
        strHeader += newline;
        try
        {
            if(Configurator.getAskAuthentificate())
            {
                debug("HTTPPostRequest() createHTTPHeader() WWW-Authentification is configured as 'obligatory'. User must enter username and password.");
                strHeader += WWWAuthentificationHeader();
            } else
            {
                debug("HTTPPostRequest() createHTTPHeader() No WWW-Authentification is needed.");
            }
        }
        catch(NullPointerException e)
        {
            debug("HTTPPostRequest() createHTTPHeader() Could not find WWW-Authentification option.");
        }
        strHeader += "Content-length: " + contentLength;
        strHeader += newline;
        strHeader += "Content-Type: multipart/form-data; boundary=" + boundary;
        strHeader += newline + newline;
        debug("HTTPPostRequest() http header=" + strHeader);
    }

    private void createHeaders()
    {
        for(int i = 0; i < arrFilenames.size(); i++)
        {
            MyFile f = (MyFile)arrFilenames.elementAt(i);
            arrMimeHeaders.addElement(new MimeHeader(f, tagName + String.valueOf(i), boundary));
        }

    }

    private boolean createHttpConnection()
    {
        HttpClient client = new HttpClient();
        debug("performRequest() HttpClient client = " + client);
        client.setConnectionTimeout(5000);
        HttpConnectionManager hcm = client.getHttpConnectionManager();
        debug("performRequest() hcm=" + hcm);
        HostConfiguration hconf = client.getHostConfiguration();
        hconf.setHost(actionURL.getHost(), actionURL.getPort(), actionURL.getProtocol());
        if(ProxyConfig.useProxy)
        {
            debug("HTTPPostRequest() using proxy settings.");
            hconf.setProxy(ProxyConfig.proxyHostname, Integer.parseInt(ProxyConfig.proxyPort));
        }
        debug("performRequest() hconf=" + hconf);
        HttpConnection hconn = hcm.getConnection(hconf);
        debug("performRequest() hconn=" + hconn);
        try
        {
            debug("performRequest() opening hconn connection");
            hconn.open();
        }
        catch(ConnectException e5)
        {
            JOptionPane.showMessageDialog(null, "Could not connect to server", "Could not connect to server.", 0);
            return false;
        }
        catch(IOException e4)
        {
            e4.printStackTrace();
        }
        httpOut = null;
        httpIn = null;
        try
        {
            httpOut = hconn.getRequestOutputStream();
            httpIn = hconn.getResponseInputStream();
        }
        catch(IllegalStateException e1)
        {
            e1.printStackTrace();
        }
        catch(IOException e1)
        {
            e1.printStackTrace();
        }
        return true;
    }

    private void createStreams(OutputStream outputstream, InputStream inputstream)
    {
        debug("HTTPPostRequest() createStreams() outputstream=" + outputstream);
        debug("HTTPPostRequest() createStreams() inputstream=" + inputstream);
        writer = new OutputStreamWriter(outputstream);
        debug("HTTPPostRequest() createStreams() writer=" + writer);
        reader = new InputStreamReader(inputstream);
        debug("HTTPPostRequest() createStreams() reader=" + reader);
    }

    private void iterateFiles()
        throws IOException
    {
        for(Iterator iter = arrMimeHeaders.iterator(); iter.hasNext();)
        {
            MimeHeader mimeheader = (MimeHeader)iter.next();
            debug("HTTPPostRequest() iterateFiles() file header: [" + mimeheader.getHeader() + "]");
            writer.write(mimeheader.getHeader());
            sendFileData(mimeheader);
            debug("HTTPPostRequest() iterateFiles() file content sent. removing file from queue.");
            tModel.removeElement(mimeheader.getFile());
            applet.doLayout();
            debug("HTTPPostRequest() iterateFiles() sending mime footer.");
            if(iter.hasNext())
                writer.write(mimeheader.getFooter());
            else
                writer.write(mimeheader.getLastFooter());
        }

    }

    private void receiveCheckResponse()
    {
        if(reader != null)
        {
            response = receiveResponse();
            debug(response);
            if(Configurator.getCheckResponse() && Configurator.getShowSuccessDialog())
                checkSuccess(response);
        }
    }

    private String receiveResponse()
    {
        String line = null;
        String response = new String();
        int i = 0;
        BufferedReader br = new BufferedReader(reader);
        do
            try
            {
                line = br.readLine();
                if(line != null)
                    response = response + line + newline;
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        while(line != null);
        return response;
    }

    private void sendFileData(MimeHeader mimeheader)
        throws IOException
    {
        myStatus.setFilename(mimeheader.getFile().getName());
        writer.flush();
        FileInputStream fin = null;
        FileReader fir = null;
        try
        {
            fin = new FileInputStream(mimeheader.getFile());
            fir = new FileReader(mimeheader.getFile());
        }
        catch(FileNotFoundException e1)
        {
            e1.printStackTrace();
            return;
        }
        byte buffer[] = new byte[1024];
        try
        {
            int c;
            while((c = fin.read(buffer)) != -1) 
            {
                httpOut.write(buffer, 0, c);
                httpOut.flush();
                counter += c;
                myStatus.setValue(counter, contentLength);
            }
            fin.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void sendHeader()
        throws IOException
    {
        debug("HTTPPostRequest() sendHeader() sending header to writer");
        debug("HTTPPostRequest() sendHeader() strHeader has " + strHeader.length() + " bytes");
        debug("HTTPPostRequest() sendHeader() writer=" + writer);
        writer.write(strHeader);
    }

    private void showErrorMessage(String string)
    {
        JOptionPane.showMessageDialog(applet, string, "JUpload Error Status", 0);
    }

}
