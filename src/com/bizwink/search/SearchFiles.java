package com.bizwink.search;

import java.util.List;

class SearchFiles {
  public static void main(String[] args) {
      SearchFilesServlet sfs = new SearchFilesServlet();

      List ls = sfs.getArticles("www_bjethnic_gov_cn","朝阳区",1,10);

      System.out.println(ls.size());
  }
}
