/**
 * Title:        Cms server
 * Description:  bizwink Cms Server
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author hujingyu
 * @version 1.0
 */

package com.bizwink.publishQueue;

public class PublishQueueException extends Exception {
    /**
     * Construct an ArticleException with specified detail
     * message.
     *
     * @param msg The detail message.
     */
    public PublishQueueException( String msg )
    {
        super(msg);
    }
}
