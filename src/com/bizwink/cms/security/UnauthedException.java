/**
 * UnauthedException.java
 */

package com.bizwink.cms.security;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Thrown if a User does not have permission to access a particular method.
 */
public class UnauthedException extends Exception {

    private Throwable nestedThrowable = null;

    public UnauthedException() {
        super();
    }

    public UnauthedException( String msg ) {
        super(msg);
    }

    public UnauthedException(Throwable nestedThrowable) {
        this.nestedThrowable = nestedThrowable;
    }

    public UnauthedException(String msg, Throwable nestedThrowable) {
        super(msg);
        this.nestedThrowable = nestedThrowable;
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (nestedThrowable != null) {
            nestedThrowable.printStackTrace();
        }
    }

    public void printStackTrace(PrintStream ps) {
        super.printStackTrace(ps);
        if (nestedThrowable != null) {
            nestedThrowable.printStackTrace(ps);
        }
    }

    public void printStackTrace(PrintWriter pw) {
        super.printStackTrace(pw);
        if (nestedThrowable != null) {
            nestedThrowable.printStackTrace(pw);
        }
    }
}