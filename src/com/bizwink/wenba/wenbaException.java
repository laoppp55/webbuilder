package com.bizwink.wenba;

public class wenbaException extends Exception {
    /**
     * Construct an TaskException with specified detail
     * message.
     *
     * @param msg The detail message.
     */
    public wenbaException( String msg )
    {
        super(msg);
    }
}
