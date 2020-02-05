package com.bizwink.exception;

import java.lang.*;

public class InitializationException
    extends Exception
{
    /**
     * Construct an InitializationException with specified detail
     * message.
     *
     * @param msg The detail message.
     */
    public InitializationException( String msg )
    {
        super(msg);
    }
}
