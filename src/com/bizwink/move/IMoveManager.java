package com.bizwink.move;

import java.util.*;

public interface IMoveManager
{
  public List getArticles(int columnID,int moveType) throws MoveException;

  void Moving(Move move) throws MoveException;

  int getArticlesNum(int columnID) throws MoveException;
}

