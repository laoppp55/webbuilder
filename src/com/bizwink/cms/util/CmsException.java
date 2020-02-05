package com.bizwink.cms.util;

public class CmsException extends Exception {
    int error;
    int errorDetail;
    Object arg;

    /**
     * Create a simple exception.
     */
    public CmsException(String message) {
	    this(0, -1, null, message);
    }

    /**
     * Create a new detailed exception with an argument.
     */
    public CmsException(int error, String message) {
	    this(error, -1, null, message);
    }

    /**
     * Create a new detailed exception with an argument.
     */
    public CmsException(int error, int errorDetail, Object arg, String message) {
	    super(message);
	    this.error = error;
	    this.errorDetail = errorDetail;
	    this.arg = arg;
    }

    /**
     * Get major error code.
     */
    public int getError() {
	    return error;
    }
    /**
     * Get error detail code.
     */
    public int getDetail() {
	    return errorDetail;
    }

    /**
     * Get the argument.
     */
    public Object getArgument() {
	    return arg;
    }

    /**
     * Convert to a string for debugging.
     */
    public String toString() {
	    if (getMessage() != null) {
	        return super.toString();
	    } else {
	        return getClass().getName() + "[" + error + "/" + errorDetail + ": " + arg + "]";
	    }
    }
}
