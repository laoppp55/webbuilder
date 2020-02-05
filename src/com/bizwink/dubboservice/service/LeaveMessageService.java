package com.bizwink.dubboservice.service;

import com.bizwink.po.Comments;
import com.bizwink.po.LeaveWord;

import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 16-3-5.
 */
public interface LeaveMessageService {

    public int insertLeaveWord(LeaveWord leaveWord);

    public int insertComments(Comments comments);

    public Integer countLeaveWord (Map<String, Object> param);

    public List<LeaveWord> getLeaveWordList(Map<String, Object> param);

    public int deleteLeaveWords(String[] LeaveWordIds);

    public Integer countComments (Map<String, Object> param);

    public List<Comments> getCommnetList(Map<String, Object> param);

    public int deleteComments(String[] CommentIds);
}
