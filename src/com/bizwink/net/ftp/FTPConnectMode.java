/**
 *  Copyright (C) 2000 Enterprise Distributed Technologies Ltd.
 *
 */

package com.bizwink.net.ftp;

/**
 *  Enumerates the connect modes that are possible,
 *  active & PASV
 *
 *  @author     Bruce Blackshaw
 *  @version    $Revision: 1.1.1.1 $
 *
 */
 public class FTPConnectMode {

     /**
      *  Revision control id
      */
     private static String cvsId = "$Id: FTPConnectMode.java,v 1.1.1.1 2006/07/18 08:31:33 ericdu Exp $";

     /**
      *   Represents active connect mode
      */
     public static FTPConnectMode ACTIVE = new FTPConnectMode();

     /**
      *   Represents PASV connect mode
      */
     public static FTPConnectMode PASV = new FTPConnectMode();

     /**
      *  Private so no-one else can instantiate this class
      */
     private FTPConnectMode() {
     }
 }
