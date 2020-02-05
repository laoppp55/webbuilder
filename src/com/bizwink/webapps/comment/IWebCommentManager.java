package com.bizwink.webapps.comment;
import java.util.*;

public interface IWebCommentManager
{

     public int createComment(webComment comment) throws webCommentException;

    public List getAllcommentInfo(String sql, int start, int range);

    public int getAllCommentNum(String sql);

    public int deleteACommentInfo(int id);
}