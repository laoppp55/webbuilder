package com.bizwink.collectionmgr;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 2007-11-20
 * Time: 14:03:52
 */
public interface IMatchUrl_SpecialCodeManager {
    public List getSpCode(int basicId);
    public void addSpCode(int siteid,MatchUrl_SpecialCode musc);
    public void updateSpCode(MatchUrl_SpecialCode musc);
    public void delSpCode(int id);
    public MatchUrl_SpecialCode getSc(int sc_id);

    public List getMtUrl(int basicId);
    public void addMtUrl(int siteid,MatchUrl_SpecialCode musc);
    public void updateMtUrl(MatchUrl_SpecialCode musc);
    public void delMtUrl(int mu_id);
    public MatchUrl_SpecialCode getMu(int mu_id);

}
