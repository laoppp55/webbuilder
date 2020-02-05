package com.bizwink.cms.selfDefine;

import java.util.List;

public interface ISelfDefineManager{

    void insertData(List nv) throws SelfDefineException;

    int createTable(String sql) throws SelfDefineException;

    int editTable(List newcol,List delcol) throws SelfDefineException;

    int delTable(String sql) throws SelfDefineException;
}
