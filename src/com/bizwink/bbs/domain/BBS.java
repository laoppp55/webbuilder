package com.bizwink.bbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class BBS implements Serializable {
    private int id;
    private String bbsname;
    private String bbsdesc;
    private Timestamp creationdate;
    private String manager;
    private String managerpass;
    private int threadnum;
    private int topicnum;
    private String postman;
    private Timestamp postdate;
    private String lastposter;
    private String lasttopic;
    private Timestamp lastposttime;
    private String pic;
    private int threadid;
    private int forumid;
    private int answerid;
    private String threadname;
    private String threadcontent;
    private Timestamp posttime;
    private String author;
    private int hitnum;
    private int answernum;
    private String ipaddress;
    private String answeruser;
    private Timestamp answertime;
    private int gotop;
    private int typeflag;
    private int hiddenflag;
    private int lockflag;
    private Timestamp logintime;
    private String username;
    private String userid;
    private int postnum;
    private String grade;
    private int score;
    private int sex;
    private int loginnum;
    private String poster;
    private String receiver;
    private String message;
    private Timestamp senddate;
    private String friend;

    public BBS()
    {
    }

    public void setID(int id)
    {
        this.id = id;
    }

    public int getID()
    {
        return id;
    }

    public void setBBSName(String bbsname)
    {
        this.bbsname = bbsname;
    }

    public String getBBSName()
    {
        return bbsname;
    }

    public void setBBSDesc(String bbsdesc)
    {
        this.bbsdesc = bbsdesc;
    }

    public String getBBSDesc()
    {
        return bbsdesc;
    }

    public void setCreationDate(Timestamp creationdate)
    {
        this.creationdate = creationdate;
    }

    public Timestamp getCreationDate()
    {
        return creationdate;
    }

    public void setManager(String manager)
    {
        this.manager = manager;
    }

    public String getManager()
    {
        return manager;
    }

    public void setManagerPass(String managerpass)
    {
        this.managerpass = managerpass;
    }

    public String getManagerPass()
    {
        return managerpass;
    }

    public void setThreadNum(int threadnum)
    {
        this.threadnum = threadnum;
    }

    public int getThreadNum()
    {
        return threadnum;
    }

    public void setTopicNum(int topicnum)
    {
        this.topicnum = topicnum;
    }

    public int getTopicNum()
    {
        return topicnum;
    }

    public void setPostMan(String postman)
    {
        this.postman = postman;
    }

    public String getPostMan()
    {
        return postman;
    }

    public void setPostDate(Timestamp postdate)
    {
        this.postdate = postdate;
    }

    public Timestamp getPostDate()
    {
        return postdate;
    }

    public void setLastPoster(String lastposter)
    {
        this.lastposter = lastposter;
    }

    public String getLastPoster()
    {
        return lastposter;
    }

    public void setLastTopic(String lasttopic)
    {
        this.lasttopic = lasttopic;
    }

    public String getLastTopic()
    {
        return lasttopic;
    }

    public void setLastPostTime(Timestamp lastposttime)
    {
        this.lastposttime = lastposttime;
    }

    public Timestamp getLastPostTime()
    {
        return lastposttime;
    }

    public void setThreadID(int threadid)
    {
        this.threadid = threadid;
    }

    public int getThreadID()
    {
        return threadid;
    }

    public void setForumID(int forumid)
    {
        this.forumid = forumid;
    }

    public int getFroumID()
    {
        return forumid;
    }

    public void setAnswerID(int answerid)
    {
        this.answerid = answerid;
    }

    public int getAnswerID()
    {
        return answerid;
    }

    public void setThreadName(String threadname)
    {
        this.threadname = threadname;
    }

    public String getThreadName()
    {
        return threadname;
    }

    public void setThreadContent(String threadcontent)
    {
        this.threadcontent = threadcontent;
    }

    public String getThreadContent()
    {
        return threadcontent;
    }

    public void setPostTime(Timestamp posttime)
    {
        this.posttime = posttime;
    }

    public Timestamp getPostTime()
    {
        return posttime;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setHitNum(int hitnum)
    {
        this.hitnum = hitnum;
    }

    public int getHitNum()
    {
        return hitnum;
    }

    public void setAnswerNum(int answernum)
    {
        this.answernum = answernum;
    }

    public int getAnswerNum()
    {
        return answernum;
    }

    public void setIPAddress(String ipaddress)
    {
        this.ipaddress = ipaddress;
    }

    public String getIPAddress()
    {
        return ipaddress;
    }

    public void setAnswerUser(String answeruser)
    {
        this.answeruser = answeruser;
    }

    public String getAnswerUser()
    {
        return answeruser;
    }

    public void setAnswerTime(Timestamp answertime)
    {
        this.answertime = answertime;
    }

    public Timestamp getAnswerTime()
    {
        return answertime;
    }

    public void setGoTop(int gettop)
    {
        gotop = gotop;
    }

    public int getGoTop()
    {
        return gotop;
    }

    public void setLoginTime(Timestamp logintime)
    {
        this.logintime = logintime;
    }

    public Timestamp getLoginTime()
    {
        return logintime;
    }

    public void setUserName(String username)
    {
        this.username = username;
    }

    public String getUserName()
    {
        return username;
    }

    public void setUserID(String userid)
    {
        this.userid = userid;
    }

    public String getUserID()
    {
        return userid;
    }

    public void setPostNum(int postnum)
    {
        this.postnum = postnum;
    }

    public int getPostNum()
    {
        return postnum;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getScore()
    {
        return score;
    }

    public void setPoster(String poster)
    {
        this.poster = poster;
    }

    public String getPoster()
    {
        return poster;
    }

    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

    public String getReceiver()
    {
        return receiver;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public void setSendDate(Timestamp senddate)
    {
        this.senddate = senddate;
    }

    public Timestamp getSendDate()
    {
        return senddate;
    }

    public void setFriend(String friend)
    {
        this.friend = friend;
    }

    public String getFriend()
    {
        return friend;
    }

    public void setTypeFlag(int typeflag)
    {
        this.typeflag = typeflag;
    }

    public int getTypeFlag()
    {
        return typeflag;
    }

    public void setHiddenFlag(int hiddenflag)
    {
        this.hiddenflag = hiddenflag;
    }

    public int getHiddenFlag()
    {
        return hiddenflag;
    }

    public void setLockFlag(int lockflag)
    {
        this.lockflag = lockflag;
    }

    public int getLockFlag()
    {
        return lockflag;
    }

    public void setPic(String pic)
    {
        this.pic = pic;
    }

    public String getPic()
    {
        return pic;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public int getSex()
    {
        return sex;
    }

    public void setLoginNum(int loginnum)
    {
        this.loginnum = loginnum;
    }

    public int getLoginNum()
    {
        return loginnum;
    }
}
