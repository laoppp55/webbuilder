package com.heaton.bot;
import java.net.*;
import java.io.*;

public class HTTPSocket extends HTTP {

    synchronized public void lowLevelSend(String url,String post) throws java.net.UnknownHostException, java.io.IOException {
        String command;// What kind of send POST or GET
        StringBuffer headers;// Used to hold outgoing client headers
        byte buffer[]=new byte[1024];//A buffer for reading
        int l,i;// Counters
        Attribute a;// Used to process incoming attributes
        int port=80;// What port, default to 80
        boolean https = false;// Are we using HTTPS?
        URL u;// Used to help parse the url parameter
        Socket socket = null;
        OutputStream out = null;
        InputStream in = null;

        // parse the URL
        try {
            //System.out.println("url=" + url);
            //if (url != null) {
            Log.log(Log.LOG_LEVEL_NORMAL,"开始抓取:" + url);
            if ( url.toLowerCase().startsWith("https") ) {
                url = "http" + url.substring(5);// so it can be parsed
                u = new URL(url);
                if ( u.getPort()==-1 )
                    port=443;
                https = true;
            } else
                u = new URL(url);

            if ( u.getPort()!=-1 )
                port = u.getPort();

            // proxy support
            //if (proxyhost != null && proxyport != null) {
            //System.out.println("proxyhost==" + proxyhost);
            //System.out.println("proxyport==" + proxyport);
            //SocketFactory.setProxyHost("proxy.sinopec.com");
            //SocketFactory.setProxyPort(8080);
            //}

            // connect
            socket = SocketFactory.getSocket(u.getHost(),port,https);

            socket.setSoTimeout(timeout);
            out = socket.getOutputStream();
            in = socket.getInputStream();

            // send command, i.e. GET or POST
            if ( post == null )
                command = "GET ";
            else
                command = "POST ";

            String file = u.getFile();
            if ( file.length()==0 )
                file="/";

            if( SocketFactory.useProxy()) {
                addProxyAuthHeader();
                if(port!=80)
                    file = "http://" + u.getHost() + ":" + port + file;
                else
                    file = "http://" + u.getHost() + file;
            }

            //end proxy support
            command = command + file + " HTTP/1.0";
            SocketFactory.writeString(out,command);
            Log.log(Log.LOG_LEVEL_NORMAL,"Request: " + command );

            // Process client headers
            if ( post!=null ) clientHeaders.set("Content-Length","" + post.length());
            clientHeaders.set("Host", u.getHost());
            i=0;
            headers = new StringBuffer();
            do {
                a = clientHeaders.get(i++);
                if ( a!=null ) {
                    headers.append(a.getName());
                    headers.append(": ");
                    headers.append(a.getValue());
                    headers.append("\r\n");
                    Log.log(Log.LOG_LEVEL_TRACE,"Client Header:" + a.getName() + "=" + a.getValue() );
                }
            } while ( a!=null );

            Log.log(Log.LOG_LEVEL_DUMP,"Writing client headers:" + headers.toString());
            Log.log(Log.LOG_LEVEL_NORMAL,"传送客户端头信息到服务器");
            if ( headers.length()>=0 ) out.write(headers.toString().getBytes());

            // Send a blank line to signal end of HTTP headers
            SocketFactory.writeString(out,"");

            if ( post!=null ) {
                System.out.println("postdata=" + post);
                //Log.log(Log.LOG_LEVEL_TRACE,"Socket Post(" + post.length() + " bytes):" + new String(post) );
                out.write(post.getBytes());
            }

            /* Read the result */
            /* First read HTTP headers */
            Log.log(Log.LOG_LEVEL_NORMAL,"读取返回头信息");
            header.setLength(0);
            int chars = 0;
            boolean done = false;

            while ( !done ) {
                int ch;

                ch = in.read();
                if ( ch==-1 )
                    done=true;

                switch ( ch ) {
                    case '\r':
                        break;
                    case '\n':
                        if ( chars==0 )
                            done =true;
                        chars=0;
                        break;
                    default:
                        chars++;
                        break;
                }

                header.append((char)ch);
            }

            Log.log(Log.LOG_LEVEL_NORMAL,"分析返回头信息");
            // now parse the headers and get content length
            parseHeaders();
            Attribute acl = serverHeaders.get("Content-length");
            int contentLength=0;
            try {
                if ( acl!=null )
                    contentLength = Integer.parseInt(acl.getValue());
            } catch ( Exception e ) {
                Log.logException("Bad value for content-length:",e);
            }

            int max;
            if ( maxBodySize!=-1 )
                max = Math.min(maxBodySize,contentLength );
            else
                max = contentLength;

            if ( max<1 )
                max=-1;

            Log.log(Log.LOG_LEVEL_NORMAL,"读取网页信息");
            BufferedReader r_in = new BufferedReader(new InputStreamReader(in,"utf-8"));
            String line = "";
            StringBuffer buf = new StringBuffer();
            //将调用url的所有返回信息做成一个字符串
            while(line != null)
            {
                try {
                    line = r_in.readLine();
                } catch (IOException e) {
                    System.out.println("Timeout");
                    break;
                }
                buf.append(line+"\r\n");
            }
            body = buf.toString().getBytes();

            /*ByteList byteList = new ByteList();
            byteList.read(in,max);
            body = byteList.detach();

            Log.log(Log.LOG_LEVEL_DUMP,"Socket Page Back:" + new String(body) + "\r\n" );

            if ( (err>=400) && (err<=599) ) {
                Log.log(Log.LOG_LEVEL_ERROR,"HTTP Exception:" + response );
                //throw new HTTPException(response);
            }*/
            //}
        }finally {
            if ( out!=null ) {
                try {
                    out.close();
                } catch ( Exception e ) {
                }
            }

            if ( in!=null ) {
                try {
                    in.close();
                } catch ( Exception e ) {
                }
            }

            if ( socket!=null ) {
                try {
                    socket.close();
                } catch ( Exception e ) {
                }
            }
        }
    }

    HTTP copy() {
        return new HTTPSocket();
    }


    /**
     * This method is called to add the user authorization headers
     * to the HTTP request.
     */
    protected void addProxyAuthHeader() {
        if( (SocketFactory.getProxyUID()!=null) && (SocketFactory.getProxyUID().length()>0) ) {
            String hdr = SocketFactory.getProxyUID() + ":" + SocketFactory.getProxyPWD()==null?"":SocketFactory.getProxyPWD();
            String encode = URLUtility.base64Encode(hdr);
            clientHeaders.set("Proxy-Authorization","Basic " + encode );
        }
    }
}
