/**
 *  Copyright (C) 2000 Enterprise Distributed Technologies Ltd.
 *
 */

package com.bizwink.net.ftp;

/**
 *  Enumerates the transfer types possible. We
 *  support only the two common types, ASCII and
 *  Image (often called binary).
 *
 *  @author             Bruce Blackshaw
 *  @version        $Revision: 1.1.1.1 $
 *
 */
 public class FTPTransferType {

     /**
      *  Revision control id
      */
     private static String cvsId = "$Id: FTPTransferType.java,v 1.1.1.1 2006/07/18 08:31:34 ericdu Exp $";

     /**
      *   Represents ASCII transfer type
      */
     public static FTPTransferType ASCII = new FTPTransferType();

     /**
      *   Represents Image (or binary) transfer type
      */
     public static FTPTransferType BINARY = new FTPTransferType();

     /**
      *   The char sent to the server to set ASCII
      */
     static String ASCII_CHAR = "A";

     /**
      *   The char sent to the server to set BINARY
      */
     static String BINARY_CHAR = "I";

     /**
      *  Private so no-one else can instantiate this class
      */
     private FTPTransferType() {
     }
 }
