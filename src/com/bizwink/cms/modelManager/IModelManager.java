package com.bizwink.cms.modelManager;

import java.util.*;
import java.io.*;

public interface IModelManager {

    int Create(Model model, int siteid,int samsiteid,int sitetype) throws ModelException;                           //创建模板

    void Update(Model model, int siteid,int samsiteid,int sitetype) throws ModelException;                           //修改模板

    void Update(Model model, int siteID,int columnid,int samsiteid,int sitetype) throws ModelException;

    void updateModelContent(String content,int id);

    void updatePubFlag(Model model) throws ModelException;

    void updatecancle(int lockstatus, int id) throws ModelException;

    boolean setupDefault(int ID, int columnID, int modelType) throws ModelException;       //设置为默认模板

    void Remove(int ID, int siteid) throws ModelException;                                //删除模板

    Model getModel(int ID) throws ModelException;                                       //获取某个模板

    Model getModel(int siteid,int columnid,int tempno) throws ModelException;

    Model getModel(int ID, String editor) throws ModelException;                         //获取某个模板

    Model getDefaultModel(int columnID, int isArticle) throws ModelException;            //获取某个栏目的默认模板

    Model getCurrentModel(int columnID, int isArticle) throws ModelException;            //获取某个栏目的当前模板

    List getModelsForAutoPub(int siteid) throws ModelException;                         //为自动发布获取所有需要发布的模板

    int getContentModelCount(int columnID, int type) throws ModelException;                        //获取某个栏目的内容模板

    int getIndexModelCount(int columnID, int type) throws ModelException;                          //获取某个栏目的索引模板

    int getHomeModelCount(int webRootID) throws ModelException;                          //获取首页的模板数目

    int getTopicModelCount() throws ModelException;                                      //获取专题的模板数目

    int getModelCount(int siteid,int samsiteid,int samsitetype,int columnID,int tempnum) throws ModelException;         //获取某个栏目的模板总数
    //获取某个栏目的模板总数

    String readModelFile(String filename, String appPath, String sitename, int siteid, String dir, int imgflag, int
            languageType, int cssjsdir) throws IOException;               //读取原始的模板文件

    List getModels(int siteid,int samsiteid,int samsitetype,int ColumnID,int tempnum, int startIndex, int numResults) throws ModelException;       //获得某个范围内的模板

    List getModels(String cids, int startIndex, int numResults) throws ModelException;  //获得某个范围内的模板

    int getModelsNum(String cids) throws ModelException;                                //获得某个范围内的栏目模板的数目

    List getModels(int columnID) throws ModelException;                                 //获得某个栏目的所有模板

    void removeModelsInColumn(int columnID) throws ModelException;                       //删除某个栏目时要先删除该栏目下的所有文章（add by lxm 2003.5.15）

    int checklock(Model model) throws ModelException;                                    //检查lockstatus标志

    String findPicinString(String buf, String sitename, String dir, int imgflag);

    boolean hasArticcleModel(int columnID) throws ModelException;                        //检查栏目是否有文章模板

    boolean hasSameModelName(int siteid,int columnID, String templateName);

    boolean hasSameModelNameForProgram(int siteid,int columnID,int id, String templateName);

    List getArticleModels(int ColumnID) throws ModelException;

    int getNotReferredModelCount(int columnID);

    List getNotReferredModels(int ColumnID, int startIndex, int numResults) throws ModelException;

    //Add by Eric 2007-6-18, Modified by Eric 2007-9-12
    String readModelFile(int columnID,String path, String sitename, int siteid, int imgflag, int cssjsdir) throws IOException;

    List getModelsForProgram(int siteID, int ColumnID, int startIndex, int numResults) throws ModelException;

    int getModelCountForPragram(int siteid,int columnID);

    public List getSiteidModel(int siteid);

    public List getModelsForProgram(int siteID,int samsiteid, int ColumnID, int startIndex, int numResults) throws ModelException;

    public int getModelCountForPragram(int siteid,int samsiteid,int columnID);

    public List getIncludeFileMaintitleString(String ids);

    public List getIncludeModels(int ColumnID, int startIndex, int numResults) throws ModelException ;

    public int getIncludeModelsNum(int ColumnID) throws ModelException;

    public Model getArticleModel(int columnid);

    Model getModelBySiteIDAndColumnid(int columnID, int siteID) throws ModelException;

    public void PublishHomePage(int siteID) throws ModelException;
}
