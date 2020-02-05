package com.bizwink.event;

public class TaskException extends Exception {
    /**
     * Construct an TaskException with specified detail
     * message.
     *
     * @param msg The detail message.
     */
    public TaskException( String msg )
    {
        super(msg);
    }
}
