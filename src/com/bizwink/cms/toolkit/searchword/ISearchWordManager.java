package com.bizwink.cms.toolkit.searchword;


import java.util.List;

public interface ISearchWordManager {

    List getAllsearchWord();

    List getAllsearchWord(String sql,int startIndex,int range);

    int getsearchWordCount(String sql);

    int createSearchWord(SearchWord searchWord);

    SearchWord getSearchWordById(int id);

    SearchWord getSearchWordByCname(String cname);

    void updateSearchWord(SearchWord searchWord,int id);

    void delSearchWord(int id);

}
