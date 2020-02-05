package com.bizwink.error;

/**
 * Title:        draw
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      bizwink
 * @author Eric
 * @version 1.0
 */

public class ErrorException extends Exception {
    public ErrorException( String msg )
    {
        super(msg);
    }
}