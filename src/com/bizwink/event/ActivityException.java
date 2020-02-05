package com.bizwink.event;

public class ActivityException extends Exception {
    /**
     * Construct an ActivityException with specified detail
     * message.
     *
     * @param msg The detail message.
     */
    public ActivityException( String msg )
    {
        super(msg);
    }
}
