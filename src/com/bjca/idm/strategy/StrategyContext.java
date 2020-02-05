package com.bjca.idm.strategy;

import java.util.Map;

/**
 * Title:       StrategyContext
 * Description:
 * Company:     BJCA
 * Author:      liwei
 * Date:        2014/4/18
 * Time:        10:04
 */
public class StrategyContext {
    public TransformStrategy transformStrategy;

    public StrategyContext(TransformStrategy transformStrategy) {
        this.transformStrategy = transformStrategy;
    }

    public Map trans(String result){
        return this.transformStrategy.transResultStringToMap(result);
    }

}
