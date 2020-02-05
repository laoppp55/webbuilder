/**
 * Title:        Cms server
 * Description:  bizwink Cms Server
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author hujingyu
 * @version 1.0
 */

package com.bizwink.cms.news;

public class ArticleException extends Exception {
    /**
     * Construct an ArticleException with specified detail
     * message.
     *
     * @param msg The detail message.
     */
    public ArticleException( String msg )
    {
        super(msg);
    }
}
