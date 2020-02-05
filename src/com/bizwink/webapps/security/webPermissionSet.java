package com.bizwink.webapps.security;

import java.util.*;
import java.io.*;

public class webPermissionSet implements Serializable {
  private TreeSet set = new TreeSet();

  public boolean add(webPermission permission) {
    return set.add((Object) permission);
  }

  public boolean add(webPermissionSet permissionSet) {
    return set.addAll((Collection) permissionSet.set);
  }

  public boolean remove(webPermission permission) {
    return set.remove((Object) permission);
  }

  public void clear() {
    set.clear();
  }

  public boolean contains(webPermission permission) {
    return set.contains((Object) permission);
  }

  public boolean contains(int pid) {
    Iterator iter = set.iterator();
    while (iter.hasNext()) {
      webPermission permission = (webPermission) iter.next();
      if (pid == (permission.getRightID())) {
        return true;
      }
    }
    return false;
  }

  public webPermission getPermission(int permissionID) {
    Iterator iter = set.iterator();
    while (iter.hasNext()) {
      webPermission permission = (webPermission) iter.next();
      if (permissionID == permission.getRightID()) {
        return permission;
      }
    }
    return null;
  }

  public Iterator elements() {
    return set.iterator();
  }

  public int size() {
    return set.size();
  }
}