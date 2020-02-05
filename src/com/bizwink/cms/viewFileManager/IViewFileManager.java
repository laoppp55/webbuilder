package com.bizwink.cms.viewFileManager;

import java.util.*;

public interface IViewFileManager
{
    int create(ViewFile vf) throws viewFileException;

    void update(ViewFile vf) throws viewFileException;

    int delete(int id) throws viewFileException;

    List getViewFileList(int siteID,int startIndex,int numResult) throws viewFileException;

    int getViewFileNUM(int siteID) throws viewFileException;

    ViewFile getAViewFile(int id) throws viewFileException;

    List getViewFileC(int siteid, int type) throws viewFileException;

    int createNavigator(navigator nv) throws viewFileException;

    List getNavigatorList(int startIndex, int numResult) throws viewFileException;

    int getNavigatorCount() throws viewFileException;

    navigator getNavigator(int id) throws viewFileException;

    void update(navigator nv) throws viewFileException;

    void deleteNavigator(int id) throws viewFileException;
}
