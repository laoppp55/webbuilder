package com.bizwink.picture;

import java.util.*;

public interface IPictureManager{

  public String saveOnePicture(Picture picture) throws PictureException;

  public List saveMorePicture(List picture) throws PictureException;

  public boolean existThePicture(String name,int siteid,int columnid) throws PictureException;
}
