/*
 * HTTPException.java
 *
 * Created on July 19, 2003, 10:50 PM
 */

package com.heaton.bot;

/**
 *
 * @author  jheaton
 */
public class HTTPException extends java.io.IOException {
    
    /**
     * Creates a new instance of <code>HTTPException</code> without detail message.
     */
    public HTTPException() {
    }
    
    
    /**
     * Constructs an instance of <code>HTTPException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public HTTPException(String msg) {
        super(msg);
    }
}
