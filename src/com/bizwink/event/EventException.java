package com.bizwink.event;

public class EventException extends Exception {
    /**
     * Construct an EventException with specified detail
     * message.
     *
     * @param msg The detail message.
     */
    public EventException( String msg )
    {
        super(msg);
    }
}
