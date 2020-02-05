package com.bizwink.cms.multimedia;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-10-13
 * Time: 10:45:23
 * To change this template use File | Settings | File Templates.
 */
public interface IMultimediaManager {

    int insertMult(Multimedia mult);

    List<Attechment> getAttechments(int articleid);
}
