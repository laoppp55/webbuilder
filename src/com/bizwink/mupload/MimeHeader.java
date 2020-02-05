package com.bizwink.mupload;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import org.apache.commons.httpclient.util.Base64;

// Referenced classes of package JUpload:
//            Configurator, MyFile, ProxyConfig

public class MimeHeader
{

    private MyFile fFile;
    private String boundary;
    private String newline;
    private String tagName;

    MimeHeader(MyFile f, String tagname, String boundary)
    {
        newline = System.getProperty("line.separator");
        debug("MimeHeader() file=" + f + " tag=" + tagname + " boundary=" + boundary);
        fFile = f;
        tagName = tagname;
        this.boundary = boundary;
    }

    public MimeHeader(MyFile f)
    {
        newline = System.getProperty("line.separator");
        fFile = f;
    }

    public long getContentLength()
    {
        return fFile.length();
    }

    public File getFile()
    {
        return fFile;
    }

    public String getFooter()
    {
        String strFooter = newline + "--" + boundary;
        return strFooter;
    }

    public String getHeader()
    {
        String strHeader = newline;
        strHeader = strHeader + "content-disposition: attachment;";
        strHeader = strHeader + " ";
        strHeader = strHeader + "name=\"";
        strHeader = strHeader + tagName;
        strHeader = strHeader + "\";";
        strHeader = strHeader + " ";
        strHeader = strHeader + "filename=\"";
        strHeader = strHeader + fFile.getName();
        strHeader = strHeader + "\";";
        strHeader = strHeader + " ";
        strHeader = strHeader + "modification-date=\"";
        strHeader = strHeader + fFile.lastModified();
        strHeader = strHeader + "\"";
        strHeader = strHeader + newline;
        strHeader = strHeader + "Content-Length: " + fFile.length() + newline;
        strHeader = strHeader + "Content-Description: " + fFile.getAbsolutePath() + newline;
        strHeader = strHeader + "Content-Transfer-Encoding: binary" + newline;
        if(Configurator.getUseRecursivePaths())
        {
            String test = fFile.getRelativeFilename();
            byte test2[] = Base64.encode(test.getBytes());
            String test3 = new String(test2);
            strHeader = strHeader + "Content-Type: jupload/" + test3 + newline;
        } else
        {
            strHeader = strHeader + "Content-Type: " + getContentType() + newline;
        }
        strHeader = strHeader + newline;
        return strHeader;
    }

    public String getLastFooter()
    {
        String strFooter = newline + "--" + boundary + "--";
        return strFooter;
    }

    public void debug(String s)
    {
        if(Configurator.getDebug())
            System.out.println(s);
    }

    public int length()
    {
        return getHeader().length();
    }

    private String getContentType()
    {
        String ext = fFile.getName().toLowerCase();
        String mimes[][] = {
            {
                "application/andrew-inset", "ez"
            }, {
                "application/mac-binhex40", "hqx"
            }, {
                "application/mac-compactpro", "cpt"
            }, {
                "application/msword", "doc"
            }, {
                "application/octet-stream", "bin", "dms", "lha", "lzh", "exe", "class", "so", "dll"
            }, {
                "application/oda", "oda"
            }, {
                "application/pdf", "pdf"
            }, {
                "application/postscript", "ai", "eps", "ps"
            }, {
                "application/smil", "smi", "smil"
            }, {
                "application/vnd.wap.wbxml", "wbxml"
            }, {
                "application/vnd.wap.wmlc", "wmlc"
            }, {
                "application/vnd.wap.wmlscriptc", "wmlsc"
            }, {
                "application/x-bcpio", "bcpio"
            }, {
                "application/x-cdlink", "vcd"
            }, {
                "application/x-chess-pgn", "pgn"
            }, {
                "application/x-cpio", "cpio"
            }, {
                "application/x-csh", "csh"
            }, {
                "application/x-director", "dcr", "dir", "dxr"
            }, {
                "application/x-dvi", "dvi"
            }, {
                "application/x-futuresplash", "spl"
            }, {
                "application/x-gtar", "gtar"
            }, {
                "application/x-hdf", "hdf"
            }, {
                "application/x-javascript", "js"
            }, {
                "application/x-koan", "skp", "skd", "skt", "skm"
            }, {
                "application/x-latex", "latex"
            }, {
                "application/x-netcdf", "nc", "cdf"
            }, {
                "application/x-sh", "sh"
            }, {
                "application/x-shar", "shar"
            }, {
                "application/x-shockwave-flash", "swf"
            }, {
                "application/x-stuffit", "sit"
            }, {
                "application/x-sv4cpio", "sv4cpio"
            }, {
                "application/x-sv4crc", "sv4crc"
            }, {
                "application/x-tar", "tar"
            }, {
                "application/x-tcl", "tcl"
            }, {
                "application/x-tex", "tex"
            }, {
                "application/x-texinfo", "texinfo", "texi"
            }, {
                "application/x-troff", "t", "tr", "roff"
            }, {
                "application/x-troff-man", "man"
            }, {
                "application/x-troff-me", "me"
            }, {
                "application/x-troff-ms", "ms"
            }, {
                "application/x-ustar", "ustar"
            }, {
                "application/x-wais-source", "src"
            }, {
                "application/xhtml+xml", "xhtml", "xht"
            }, {
                "application/zip", "zip"
            }, {
                "audio/basic", "au", "snd"
            }, {
                "audio/midi", "mid", "midi", "kar"
            }, {
                "audio/mpeg", "mpga", "mp2", "mp3"
            }, {
                "audio/x-aiff", "aif", "aiff", "aifc"
            }, {
                "audio/x-mpegurl", "m3u"
            }, {
                "audio/x-pn-realaudio", "ram", "rm"
            }, {
                "audio/x-pn-realaudio-plugin", "rpm"
            }, {
                "audio/x-realaudio", "ra"
            }, {
                "audio/x-wav", "wav"
            }, {
                "chemical/x-pdb", "pdb"
            }, {
                "chemical/x-xyz", "xyz"
            }, {
                "image/bmp", "bmp"
            }, {
                "image/gif", "gif"
            }, {
                "image/ief", "ief"
            }, {
                "image/jpeg", "jpeg", "jpg", "jpe"
            }, {
                "image/png", "png"
            }, {
                "image/tiff", "tiff", "tif"
            }, {
                "image/vnd.djvu", "djvu", "djv"
            }, {
                "image/vnd.wap.wbmp", "wbmp"
            }, {
                "image/x-cmu-raster", "ras"
            }, {
                "image/x-portable-anymap", "pnm"
            }, {
                "image/x-portable-bitmap", "pbm"
            }, {
                "image/x-portable-graymap", "pgm"
            }, {
                "image/x-portable-pixmap", "ppm"
            }, {
                "image/x-rgb", "rgb"
            }, {
                "image/x-xbitmap", "xbm"
            }, {
                "image/x-xpixmap", "xpm"
            }, {
                "image/x-xwindowdump", "xwd"
            }, {
                "model/iges", "igs", "iges"
            }, {
                "model/mesh", "msh", "mesh", "silo"
            }, {
                "model/vrml", "wrl", "vrml"
            }, {
                "text/css", "css"
            }, {
                "text/html", "html", "htm"
            }, {
                "text/plain", "asc", "txt"
            }, {
                "text/richtext", "rtx"
            }, {
                "text/rtf", "rtf"
            }, {
                "text/sgml", "sgml", "sgm"
            }, {
                "text/tab-separated-values", "tsv"
            }, {
                "text/vnd.wap.wml", "wml"
            }, {
                "text/vnd.wap.wmlscript", "wmls"
            }, {
                "text/x-setext", "etx"
            }, {
                "text/xml", "xml", "xsl"
            }, {
                "video/mpeg", "mpeg", "mpg", "mpe"
            }, {
                "video/quicktime", "qt", "mov"
            }, {
                "video/vnd.mpegurl", "mxu"
            }, {
                "video/x-msvideo", "avi"
            }, {
                "video/x-sgi-movie", "movie"
            }, {
                "x-conference/x-cooltalk", "ice"
            }
        };
        String type = "application/octet-stream";
        for(int i = 0; i < mimes.length; i++)
        {
            for(int j = 1; j < mimes[i].length; j++)
                if(ext.endsWith(mimes[i][j]))
                    type = mimes[i][0];

        }

        return type;
    }

    public String getPutHeader(String strHost, long maxlength, long offset)
    {
        String strPutHeader = "";
        String strRessourceURI = getFile().getName();
        if(ProxyConfig.useProxy)
            strPutHeader = strPutHeader + "PUT " + Configurator.getActionURL() + strRessourceURI + " HTTP/1.1" + newline;
        else
            strPutHeader = strPutHeader + "PUT " + Configurator.getActionURL().getPath() + strRessourceURI + " HTTP/1.1" + newline;
        strPutHeader = strPutHeader + "Host: " + strHost + newline;
        strPutHeader = strPutHeader + "User-Agent: JUpload (www.haller-systemservice.net)" + newline;
        long len = 0L;
        long rest = 0L;
        if(getContentLength() <= maxlength)
            len = getContentLength();
        else
            len = maxlength;
        if(offset + maxlength > getContentLength())
            len = getContentLength() - offset;
        strPutHeader = strPutHeader + "Content-Length: " + len + newline;
        strPutHeader = strPutHeader + "Content-Range: bytes " + offset + "-" + (offset + len) + "/" + getContentLength() + newline;
        strPutHeader = strPutHeader + "Content-Type: " + getContentType() + newline;
        strPutHeader = strPutHeader + "Connection: keep-alive" + newline;
        if(ProxyConfig.useProxy)
            strPutHeader = strPutHeader + "Proxy-Connection: keep-alive" + newline;
        strPutHeader = strPutHeader + newline;
        return strPutHeader;
    }

    public String getHeadHeader(String strHost)
    {
        String strHeadHeader = "";
        String strRessourceURI = getFile().getName();
        if(ProxyConfig.useProxy)
            strHeadHeader = strHeadHeader + "HEAD " + Configurator.getActionURL() + strRessourceURI + " HTTP/1.1" + newline;
        else
            strHeadHeader = strHeadHeader + "HEAD " + Configurator.getActionURL().getPath() + strRessourceURI + " HTTP/1.1" + newline;
        strHeadHeader = strHeadHeader + "Host: " + strHost + newline;
        strHeadHeader = strHeadHeader + "User-Agent: JUpload (www.haller-systemservice.net)" + newline;
        strHeadHeader = strHeadHeader + "Connection: keep-alive" + newline;
        if(ProxyConfig.useProxy)
            strHeadHeader = strHeadHeader + "Proxy-Connection: keep-alive" + newline;
        strHeadHeader = strHeadHeader + newline;
        return strHeadHeader;
    }
}
