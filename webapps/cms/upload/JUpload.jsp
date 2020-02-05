<%@ page contentType="text/html; charset=iso-8859-1" language="java"  import="java.util.*,java.io.*"%>
<%
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= PUT PARAMS HERE =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    String location = "c:\\temp\\";  // put the file system path to where you wold like to save the file.
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    parseMultiForm pMF = new parseMultiForm( request );
    String oldFileName = "";
    boolean done = false;
    String param = "";
    while ( (param = pMF.getNextParameter()) != null ){
        System.out.println("param=" + param);
        oldFileName = pMF.getFilename();
        File theFile = new File( location + oldFileName );
        FileOutputStream OutFile = new FileOutputStream( theFile );
        done = pMF.getParameter( OutFile );
        OutFile.close();
    }
%>
<%!
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= SHOULD NOT HAVE TO MODIFY THIS =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //found much of this on the web, majority could have been from "Java Servlet programming by O'Rielly"
    class parseMultiForm extends java.lang.Object {
        private ServletInputStream In;
        private byte buffer[] = new byte[4096];
        private String delimitor = null;
        private String filename=null;

        public parseMultiForm( ServletRequest _req ) throws IOException{
            In  = _req.getInputStream();
            delimitor   = _req.getContentType();
            if ( delimitor.indexOf("boundary=") != -1 ){
                delimitor = delimitor.substring( delimitor.indexOf("boundary=")+9,delimitor.length() );
                delimitor = "--" + delimitor;
            }
        }

        private String readLine(){
            try{
                int noData = In.readLine( buffer, 0, buffer.length );
                if ( noData != -1 )
                    return new String( buffer, 0, noData, "ISO-8859-1");
            }catch(Exception E){}
            return null;
        }

        void test() throws IOException{
            String LineIn;
            while( (LineIn=readLine()) != null )
                System.out.println( LineIn  );
        }

        public String getFilename(){
            return filename;
        }

        public String getParameter(){
            return readLine();
        }

        public String getNextParameter() {
            try{
                String LineIn=null, paramName=null;

                while ( (LineIn=readLine()) != null ){
                    if ( LineIn.indexOf( "name=" ) != -1 ){
                        int c1 = LineIn.indexOf( "name=" );
                        int c2 = LineIn.indexOf( "\"", c1+6 );
                        paramName = LineIn.substring( c1+6, c2 );

                        if ( LineIn.indexOf( "filename=") != -1 ){
                            c1 = LineIn.indexOf( "filename=" );
                            c2 = LineIn.indexOf( "\"", c1+10 );
                            filename = LineIn.substring( c1+10, c2 );
                            if ( filename.lastIndexOf( "\\" ) != -1 )
                                filename = filename.substring( filename.lastIndexOf( "\\" )+1 );

                            if ( filename.length() == 0 )
                                filename = null;
                        }

                        //- Move the pointer to the start of the data
                        LineIn = readLine();
                        if ( LineIn.indexOf( "Content-Type" ) != -1 )
                            LineIn = readLine();

                        return paramName;
                    }
                }
            }
            catch( Exception E ){}
            return null;
        }

        public boolean getParameter( OutputStream _Out ){
            try{
                int noData;
                while ( (noData=In.readLine(buffer,0,buffer.length)) != -1 ){
                    String buf = new String( buffer, 0, noData, "ISO-8859-1");
                    System.out.println("buf=" + buf);
                    if ( buf.indexOf(delimitor) == 0 ){
                        break;
                    }else
                        _Out.write( buffer, 0, noData );
                }
                _Out.flush();
                return true;
            }
            catch( Exception E ){
                System.out.println( E  );
            }

            return false;
        }
    }
%>