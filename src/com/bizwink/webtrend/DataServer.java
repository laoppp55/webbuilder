package com.bizwink.webtrend;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;

/**
  * <p>This servlet shows how to read data from and write data
  * to a client using data input/output streams.
  */

public class DataServer extends HttpServlet
{
    public void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException, java.io.IOException
    {
        // Get the input stream for reading data from the client
        DataInputStream in =
          new DataInputStream(req.getInputStream());

        // We'll be sending binary data back to the client so
        // set the content type appropriately
        resp.setContentType("application/octet-stream");

        // Data will always be written to a byte array buffer so
        // that we can tell the client the length of the data
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

        // Create the output stream to be used to write the
        // data to our buffer
        DataOutputStream out = new DataOutputStream(byteOut);

        // Read the data from the client.
        boolean booleanValue = in.readBoolean();
        byte byteValue = in.readByte();
        char charValue = in.readChar();
        short shortValue = in.readShort();
        int intValue = in.readInt();
        float floatValue = in.readFloat();
        double doubleValue = in.readDouble();
        String stringValue = in.readUTF();

        // Write the data to our internal buffer.
        out.writeBoolean(booleanValue);
        out.writeByte(byteValue);
        out.writeChar(charValue);
        out.writeShort(shortValue);
        out.writeInt(intValue);
        out.writeFloat(floatValue);
        out.writeDouble(doubleValue);
        out.writeUTF(stringValue);

        // Flush the contents of the output stream to the
        // byte array
        out.flush();

        // Get the buffer that is holding our response
        byte[] buf = byteOut.toByteArray();

        // Notify the client how much data is being sent
        resp.setContentLength(buf.length);

        // Send the buffer to the client
        ServletOutputStream servletOut = resp.getOutputStream();

        // Wrap up
        servletOut.write(buf);
      servletOut.close();    }
}
