package com.bizwink.cms.toolkit.task;

import java.util.*;

public interface ITaskManager {

  public List getTask(String memberid) throws TaskException;

  public List getCurrentTask(String memberid, int startrow, int range) throws TaskException;

  public Task getA_Task(int id) throws TaskException;

  public void insertTask(Task task) throws TaskException;

  public void updateTask(Task task, int id) throws TaskException;

  public void deleteTask(int id) throws TaskException;

  public void updateTaskFlag(int id,int status) throws TaskException;

}