package com.heaton.bot;

import java.io.*;

/**
 *
 * @author  jheaton
 */
public class ByteList {

  public static final int INITIAL_SIZE = 32768;

  private byte buffer[] = new byte[INITIAL_SIZE];
  private int index = 0;

  public byte[]detach()
  {
    byte result[] = new byte[index];
    System.arraycopy(buffer,0,result,0,index);
    buffer = null;
    index = 0;
    return result;
  }

  private int capacity()
  {
    return( buffer.length-index );
  }

  private void expand()
  {
    byte newbuffer[] = new byte[buffer.length*2];
    System.arraycopy(buffer,0,newbuffer,0,index);
    buffer = newbuffer;
  }

  public void append(byte buffer[])
  {        
    System.arraycopy(buffer,0,this.buffer,index,buffer.length);
  }

  public void read(InputStream in,int max)
  throws IOException
  {       
    long l = 0;

    do {
      int size;

      // only read up to the max size, if the max size was
      // specified
      if ( max!=-1 )
        size = Math.min(capacity(),max);
      else
        size = capacity();

      // actually read the block
      l = in.read(buffer,index,capacity());

      // quit if we hit EOF
      if ( l<0 )
        break;

      // adjust capacity if needed
      index+=l;

      if ( capacity()<10 )
        expand();

      // see if we hit the max length
      if ( max!=-1 ) {
        max-=l;
        if ( max<=0 )
          break;
      }
    } while ( l!=0 );
  }
}
