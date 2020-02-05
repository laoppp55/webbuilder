package com.bizwink.cms.sjswsbs;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2013-9-24
 * Time: 16:53:13
 * To change this template use File | Settings | File Templates.
 */
public interface IWsbsManager {

    void updateIndexflag();

    public List getCurrentWsbsList(int startrow, int range);

    public int getAllWsbsNum();

    public int getAllWsbsNum(String sqlstr);

    public List getCurrentQureyWsbsList(String sqlstr, int startrow, int range);

    public WsbsEntity getByIdwsbs(int id);

    public BasisEntity getByIdwsbsgist(int id);

    public List getByIdGist(int id);

    public CatgEntity getByIdcatg(int id);

    public List getAllCatgEntity(int a,int b);

    public int insertWsbs(WsbsEntity wsbs,List list);

    public int insertWsbsGist(int id,List list);

    public void updateWsbs(WsbsEntity wsbs,List list);

    public void updateWsbsGist(List list);

    public void deleteWsbsGist(int id);

    public void deleteWsbs(int id);

    public String  getConfig();

    //打黑举报

    public int getAlldhjbNum();

    public int getAlldhjbNum(String sqlstr);

    public List getCurrentdhjbList(int startrow, int range);

    public List getCurrentQureydhjbList(String sqlstr, int startrow, int range);

    public void deletedhjb(int id);

    public ReportGangdom getByIddhjb(int id);

    //纪委打黑举报

    public int getAlljwdhNum();

    public int getAlljwdhNum(String sqlstr);

    public List getCurrentjwdhList(int startrow, int range);

    public List getCurrentQureyjwdhList(String sqlstr, int startrow, int range);

    public void deletejwdh(int id);

    public ReportGangdom getByIdjwdh(int id);

    public void updatejwdh(int id,int auditflag);

    //政民互动--我要写信

    public int getLetterCount(int status);

    public int getLetterCount(String sqlstr);

    public List getCurrentLetterList(int startrow, int range,int status);

    public List getCurrentQureyLetterList(String sqlstr, int startrow, int range);

    public void deleteLetter(int id);

    public Letter getLetterById(int id);

    public void updateLetter(int id,Letter letter);

    public String getDepartment(int depid);

    public String getCategory(int Categoryid);


}
