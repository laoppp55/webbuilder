package com.bjca.idm.strategy;

import java.util.Map;

/**
 * Title:       TransformStrategy
 * Description:
 * Company:     BJCA
 * Author:      liwei
 * Date:        2014/4/18
 * Time:        9:57
 */
public interface TransformStrategy {
    public Map transResultStringToMap(String resultString);
}
