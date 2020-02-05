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
//            ProxyConfig, startup, MimeHeader, LoginFrame, 
//            MyFile

public class HTTPPutRequest extends Thread
    implements HTTPRequest
{

    protected static int threadCounter = 0;
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

    public HTTPPutRequest(JUpload parent, DefaultListModel tModel)
    {
        tagName = Configurator.getHTTPTagName();
        arrFilenames = new Vector();
        counter = 0;
        newline = System.getProperty("line.separator");
        arrMimeHeaders = new Vector();
        LastRequest = false;
        finished = false;
        running = false;
        status = false;
        debug("HTTPPutRequest() Constructor. parent is " + parent);
        setPriority(1);
        this.tModel = tModel;
        applet = parent;
    }

    public void setActionURL(URL url)
    {
        debug("HTTPPutRequest() setting action URL");
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
        debug("HTTPPutRequest() starting REQUEST");
        myStatus = new UploadStatus();
        applet.statuspanel.add(myStatus);
        applet.controlPanel.validate();
        if(actionURL.getProtocol().equalsIgnoreCase("https"))
        {
            debug("HTTPPutRequest() performRequest() registering Secure protocol");
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
            debug("HTTPPutRequest() could not create connection to webserver: " + actionURL.getHost());
            applet.statuspanel.remove(myStatus);
            applet.controlPanel.repaint();
            running = false;
            finished = true;
            return false;
        }
        debug("HTTPPutRequest() create header");
        createHeaders();
        debug("HTTPPutRequest() performRequest() creating streams");
        createStreams(httpOut, httpIn);
        if(writer == null)
        {
            applet.statuspanel.remove(myStatus);
            applet.controlPanel.repaint();
            running = false;
            finished = true;
            return false;
        }
        debug("HTTPPutRequest() performRequest() iterating files");
        iterateFiles();
        debug("HTTPPutRequest() performRequest() flushing writer buffer");
        writer.flush();
        debug("HTTPPutRequest() REQUEST ends");
        debug("Upload finished.");
        if(LastRequest)
        {
            String strRequest;
            if(ProxyConfig.useProxy)
                strRequest = "HEAD " + actionURL + " HTTP/1.1\r\nHost: " + actionURL.getHost() + "\r\n" + "Connection: close\r\n\r\n";
            else
                strRequest = "HEAD " + actionURL.getPath() + " HTTP/1.1\r\nHost: " + actionURL.getHost() + "\r\n" + "Connection: close\r\n\r\n";
            debug("HTTPPostRequest() performRequest() closing http connection...");
            writer.write(strRequest);
            writer.flush();
            httpOut.flush();
            String strResponse = receiveResponse();
            debug("HTTPPutRequest() performRequest() uploadapplet is " + applet.applet);
            URL urlCompleteURL = Configurator.getCompleteURL();
            if(urlCompleteURL != null)
            {
                debug("HTTPPutRequest() performRequest() completeURL is not empty. Redirecting user...to=" + urlCompleteURL);
                applet.applet.context.showDocument(urlCompleteURL);
            }
        }
        debug("HTTPPutRequest() performRequest() finished.");
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
        debug("HTTPPutRequest() Upload thread ended...");
        debug("HTTPPutRequest() removing status bar and repainting");
        applet.statuspanel.remove(myStatus);
        applet.controlPanel.repaint();
        applet.doLayout();
        applet.validate();
        running = false;
        finished = true;
    }

    private long getRemoteFilesize(MimeHeader mimeheader)
        throws IOException, SocketException
    {
        String strHeadHeader = mimeheader.getHeadHeader(actionURL.getHost());
        writer.write(strHeadHeader);
        debug("HTTPPutRequest() flushing output buffer");
        writer.flush();
        debug("HTTPPutRequest() receiving answer...");
        String strResponse = receiveResponse();
        if(strResponse.indexOf("Connection: close") > 0)
        {
            debug("HTTPPutRequest() server requested CLOSE. re-opening streams");
            writer.close();
            reader.close();
            createHttpConnection();
            createStreams(httpOut, httpIn);
            debug("HTTPPutRequest() re-opened streams");
        }
        long remoteSize = 0L;
        int status = 0;
        StringTokenizer st1 = new StringTokenizer(strResponse, "\r\n");
        String strReasonPhrase = null;
        while(st1.hasMoreTokens()) 
        {
            StringTokenizer st2 = new StringTokenizer(st1.nextToken());
            String strKey = st2.nextToken();
            String strValue = st2.nextToken();
            if(strKey.equalsIgnoreCase("content-length:"))
                remoteSize = Long.parseLong(strValue);
            if(strKey.equalsIgnoreCase("http/1.1"))
            {
                status = Integer.parseInt(strValue);
                strReasonPhrase = st2.nextToken("\r\n");
            }
            if(strKey.equalsIgnoreCase("http/1.0"))
            {
                status = Integer.parseInt(strValue);
                strReasonPhrase = st2.nextToken("\r\n");
            }
        }
        if(status >= 500)
        {
            showErrorMessage(status + ": Server Error - The server failed to fulfill an apparently valid request\n" + "Reason: " + strReasonPhrase);
            return 0L;
        }
        if(status >= 400 && status != 404)
        {
            showErrorMessage(status + ": Client Error - The request contains bad syntax or cannot be fulfilled\n" + "Reason: " + strReasonPhrase);
            return 0L;
        }
        if(status == 200)
            return remoteSize;
        else
            return 0L;
    }

    private String WWWAuthentificationHeader()
    {
        LoginFrame loginframe = new LoginFrame();
        StringBuffer theUID = (new StringBuffer()).append(loginframe.getUsername());
        if(loginframe.getPassword() != null)
            theUID.append(":").append(loginframe.getPassword());
        String encoding = (new BASE64Encoder()).encode(theUID.toString().getBytes());
        String strAuthHeader = "Authorization: Basic " + encoding + newline;
        debug("HTTPPutRequest() WWWAuthentificationHeader() is [" + strAuthHeader + "]");
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
        return true;
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

    private void createHeaders()
    {
        for(int i = 0; i < arrFilenames.size(); i++)
        {
            MyFile f = (MyFile)arrFilenames.elementAt(i);
            arrMimeHeaders.addElement(new MimeHeader(f));
        }

    }

    private boolean createHttpConnection()
    {
        HttpClient client = null;
        client = new HttpClient();
        debug("performRequest() HttpClient client = " + client);
        client.setConnectionTimeout(5000);
        HttpConnectionManager hcm = null;
        hcm = client.getHttpConnectionManager();
        debug("performRequest() hcm=" + hcm);
        HostConfiguration hconf = null;
        hconf = client.getHostConfiguration();
        debug("performRequest() hconf=" + hconf);
        hconf.setHost(actionURL.getHost(), actionURL.getPort(), actionURL.getProtocol());
        if(ProxyConfig.useProxy)
        {
            debug("HTTPPostRequest() using proxy settings.");
            try
            {
                hconf.setProxy(ProxyConfig.proxyHostname, Integer.parseInt(ProxyConfig.proxyPort));
            }
            catch(NumberFormatException nfe)
            {
                debug("*** Error: could not set proxy settings.");
                ProxyConfig.useProxy = false;
            }
        }
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
        debug("HTTPPutRequest() createStreams() outputstream=" + outputstream);
        debug("HTTPPutRequest() createStreams() inputstream=" + inputstream);
        writer = null;
        reader = null;
        writer = new OutputStreamWriter(outputstream);
        debug("HTTPPutRequest() createStreams() writer=" + writer);
        reader = new InputStreamReader(inputstream);
        debug("HTTPPutRequest() createStreams() reader=" + reader);
    }

    private void iterateFiles()
        throws IOException, SocketException
    {
        debug("HTTPPutRequest() iterateFiles()");
        for(Iterator iter = arrMimeHeaders.iterator(); iter.hasNext(); debug("  next file..."))
        {
            MimeHeader mimeheader = (MimeHeader)iter.next();
            long remoteSize = 0L;
            boolean stop = false;
            int retryCounter = 0;
            long lastOffset = 0L;
            do
            {
                if(retryCounter > 3)
                    stop = true;
                remoteSize = getRemoteFilesize(mimeheader);
                writer.flush();
                httpOut.flush();
                debug("  getRemoteFilesize() said " + remoteSize);
                if(remoteSize == mimeheader.getFile().length())
                {
                    debug(" iterateFiles() File already on server with correct file size. Skipping");
                    debug(" doing layout");
                    tModel.removeElement(mimeheader.getFile());
                    applet.doLayout();
                    break;
                }
                long offset = remoteSize;
                if(offset == lastOffset)
                    retryCounter++;
                long maxlength = 0x10000L;
                String putHeader = mimeheader.getPutHeader(actionURL.getHost(), maxlength, offset);
                writer.write(putHeader);
                long bytesSent = sendFileData(mimeheader, offset, maxlength);
                debug("HTTPPutRequest() iterateFiles() file content sent:" + bytesSent + " bytes");
                String strResponse = receiveResponse();
                if(strResponse.indexOf("Connection: close") > 0)
                {
                    debug("HTTPPutRequest() server requested CLOSE. re-opening streams");
                    writer.close();
                    reader.close();
                    httpIn.close();
                    httpOut.close();
                    createHttpConnection();
                    createStreams(httpOut, httpIn);
                    debug("HTTPPutRequest() re-opened streams");
                }
            } while(remoteSize != mimeheader.getFile().length() && !stop);
        }

    }

    private void receiveCheckResponse()
        throws IOException
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
        throws IOException
    {
        String line = null;
        String response = new String();
        int i = 0;
        debug("HTTPPutRequest() receiveResponse() creating buffered reader from " + reader);
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(reader);
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
                    debug("HTTPPutRequest() receiveResponse() re-opening http connection and streams");
                    createHttpConnection();
                    createStreams(httpOut, httpIn);
                    return null;
                }
            while(line != null && br.ready());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return response;
    }

    private long sendFileData(MimeHeader mimeheader, long offset, long maxlength)
        throws IOException
    {
        myStatus.setFilename(mimeheader.getFile().getName());
        writer.flush();
        FileInputStream fin = null;
        try
        {
            fin = new FileInputStream(mimeheader.getFile());
            fin.skip(offset);
        }
        catch(FileNotFoundException e1)
        {
            e1.printStackTrace();
            return 0L;
        }
        long bytesSent = 0L;
        byte buffer[] = new byte[4096];
        try
        {
            int c;
            while((c = fin.read(buffer)) != -1) 
            {
                if(bytesSent < maxlength)
                {
                    try
                    {
                        httpOut.write(buffer, 0, c);
                        httpOut.flush();
                    }
                    catch(SocketException se)
                    {
                        se.printStackTrace();
                        JOptionPane.showMessageDialog(applet.applet, "Server closed connection.");
                        return bytesSent;
                    }
                    counter += c;
                    bytesSent += c;
                    myStatus.setValue((int)(offset + bytesSent), (int)mimeheader.getFile().length());
                    continue;
                }
                int rest = (int)(maxlength - bytesSent);
                if(rest > 0)
                {
                    httpOut.write(buffer, 0, rest);
                    httpOut.flush();
                    counter += rest;
                    bytesSent += rest;
                }
                break;
            }
            fin.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return bytesSent;
    }

    private void showErrorMessage(String string)
    {
        JOptionPane.showMessageDialog(applet, string, "JUpload Error Status", 0);
    }

}
