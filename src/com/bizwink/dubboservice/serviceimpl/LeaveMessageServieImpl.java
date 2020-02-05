package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.LeaveMessageService;
import com.bizwink.persistence.CommentsMapper;
import com.bizwink.persistence.LeaveWordMapper;
import com.bizwink.po.Comments;
import com.bizwink.po.LeaveWord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 16-3-5.
 */
@Service
public class LeaveMessageServieImpl implements LeaveMessageService {
    @Autowired
    private LeaveWordMapper leaveWordMapper;

    @Autowired
    private CommentsMapper commentsMapper;

    private static Logger logger = Logger.getLogger(LeaveMessageServieImpl.class.getName());

    public int insertLeaveWord(LeaveWord leaveWord){
        return leaveWordMapper.insertLeaveWord(leaveWord);
    }

    public int insertComments(Comments comments){
        return commentsMapper.insertComments(comments);
    }

    public Integer countLeaveWord (Map<String, Object> param){
           return leaveWordMapper.countLeaveWord(param);
    }

    public List<LeaveWord> getLeaveWordList(Map<String, Object> param){
          return leaveWordMapper.getLeaveWordList(param);
    }

    public int deleteLeaveWords(String[] LeaveWordIds){
        return leaveWordMapper.deleteLeaveWords(LeaveWordIds);
    }

    public Integer countComments (Map<String, Object> param){
         return  commentsMapper.countComments(param);
    }

    public List<Comments> getCommnetList(Map<String, Object> param){
        return  commentsMapper.getCommnetList(param);
    }

    public int deleteComments(String[] CommentIds){
       return  commentsMapper.deleteComments(CommentIds);
    }
}
