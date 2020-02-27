package com.bizwink.controller;

import com.bizwink.dubboservice.service.LeaveMessageService;
import com.bizwink.dubboservice.service.SiteinfoService;
import com.bizwink.dubboservice.service.Users.UsersService;
import com.bizwink.po.Comments;
import com.bizwink.po.Companyinfo;
import com.bizwink.po.LeaveWord;
import com.bizwink.po.Siteinfo;
import com.bizwink.util.filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jhon on 2016/4/18.
 */
@Controller
@RequestMapping("/")
public class LeaveMessageController {
    @Autowired
    private LeaveMessageService leaveMessageService;

    @Autowired
    private SiteinfoService siteinfoService;

    @Autowired
    private UsersService usersService;

    @RequestMapping("selectBySitename.do")
    @ResponseBody
    public Siteinfo selectBySitename(String basePath){
        return siteinfoService.selectBySitename(basePath);
    }

    @RequestMapping("insertLeaveWord.do")
    @ResponseBody
    public int insertLeaveWord(LeaveWord leaveWord){
        leaveWord.setTitle(filter.excludeHTMLCode(leaveWord.getTitle()));
        leaveWord.setContent(filter.excludeHTMLCode(leaveWord.getContent()));
        return leaveMessageService.insertLeaveWord(leaveWord);
    }

    @RequestMapping("getCompanyInfoBySiteId.do")
    @ResponseBody
    public Companyinfo getCompanyInfoBySiteId(int siteId){
        return usersService.getCompanyinfoBySiteid(siteId);
    }

    @RequestMapping("insertComments.do")
    @ResponseBody
    public int insertComments(Comments comments){
        comments.setComments(filter.excludeHTMLCode(comments.getComments()));
        return leaveMessageService.insertComments(comments);
    }

    //查看留言
    @RequestMapping("getLeaveWords.do")
    @ResponseBody
    public LeaveWordPageInfo getLeaveWords(Integer pageNo,int siteId){
        int pageSize = 5;
        Integer count = 0;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("siteId",siteId);

        count = leaveMessageService.countLeaveWord(param);
        if (pageNo < 1)
            pageNo = 1;
        int startRec = (pageNo - 1) * pageSize + 1;
        int endRec = pageNo * pageSize;
        param.put("startRec", startRec);
        param.put("endRec", endRec);
        List<LeaveWord> LeaveWordList = leaveMessageService.getLeaveWordList(param);
        LeaveWordPageInfo leaveWordPageInfo = new LeaveWordPageInfo();
        leaveWordPageInfo.setCount(count);
        leaveWordPageInfo.setLeaveWordList(LeaveWordList);
        return leaveWordPageInfo;
    }

    //分页信息类，可以移出来
    public class LeaveWordPageInfo {
        private int count;
        private List<LeaveWord> leaveWordList;
        public int getCount() {
            return count;
        }
        public void setCount(int count) {
            this.count = count;
        }
        public List<LeaveWord> getLeaveWordList() {
            return leaveWordList;
        }
        public void setLeaveWordList(List<LeaveWord> leaveWordList) {
            this.leaveWordList = leaveWordList;
        }
    }


    @RequestMapping("deleteLeaveWords.do")
    @ResponseBody
    public int deleteLeaveWords(String[] LeaveWordIds){
        return leaveMessageService.deleteLeaveWords(LeaveWordIds);
    }

    //查看点评
    @RequestMapping("getComments.do")
    @ResponseBody
    public CommentPageInfo getComments(Integer pageNo,int siteId){
        int pageSize = 5;
        Integer count = 0;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("siteId",siteId);

        count = leaveMessageService.countComments(param);
        if (pageNo < 1)
            pageNo = 1;
        int startRec = (pageNo - 1) * pageSize + 1;
        int endRec = pageNo * pageSize;
        param.put("startRec", startRec);
        param.put("endRec", endRec);
        List<Comments> CommnetsList = leaveMessageService.getCommnetList(param);
        CommentPageInfo commentPageInfo = new CommentPageInfo();
        commentPageInfo.setCount(count);
        commentPageInfo.setCommnetsList(CommnetsList);
        return commentPageInfo;
    }

    //分页信息类，可以移出来
    public class CommentPageInfo {
        private int count;
        private List<Comments> CommnetsList;
        public int getCount() {
            return count;
        }
        public void setCount(int count) {
            this.count = count;
        }
        public List<Comments> getCommnetsList() {
            return CommnetsList;
        }
        public void setCommnetsList(List<Comments> CommnetsList) {
            this.CommnetsList = CommnetsList;
        }
    }

    @RequestMapping("deleteComments.do")
    @ResponseBody
    public int deleteComments(String[] CommentIds){
        return leaveMessageService.deleteComments(CommentIds);
    }
}