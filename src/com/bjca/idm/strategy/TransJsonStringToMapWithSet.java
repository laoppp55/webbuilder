package com.bjca.idm.strategy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Title:       TransJsonStringToMapWithSet
 * Description:
 * Company:     BJCA
 * Author:      liwei
 * Date:        2014/4/18
 * Time:        10:02
 */
public class TransJsonStringToMapWithSet implements TransformStrategy {

    public Map transResultStringToMap(String result) {
        Map resultMap = new HashMap();
        //去换行
        result = result.replaceAll(SdkConsant.WRAP, SdkConsant.BLANK).replaceAll(SdkConsant.ENTER, SdkConsant.BLANK).trim();
        //去前后大括号
        result = result.substring(1, result.length() - 1);
        //分割键值对
        String[] s = result.split(SdkConsant.CLOSE_BRACKET + SdkConsant.COMMA);
        for (int i = 0; i < s.length; i++) {
            String attr = s[i];
            //分割键值
            String[] attrs = attr.split(SdkConsant.QUOTATION + SdkConsant.COLON);
            if (attrs.length > 1) {
                if (attrs[1].indexOf(SdkConsant.OPEN_BRACKET) != -1) {
                    String[] attrValues = attrs[1].replaceAll(SdkConsant.OPEN_BRACKET1, SdkConsant.BLANK)
                            .replaceAll(SdkConsant.CLOSE_BRACKET, SdkConsant.BLANK).split(SdkConsant.QUOTATION + SdkConsant.COMMA);
                    //value为多值
                    if (attrValues.length > 1) {
                        //扩展属性
                        if (attr.indexOf(SdkConsant.EXTPROPERTIES) != -1) {
                            Map attrValueMap = new HashMap();
                            for (int j = 0; j < attrValues.length; j++) {
                                String attrValue = attrValues[j];
                                String[] extAttrs = attrValue.split(SdkConsant.EQUAL);
                                String extAttrName = extAttrs[0].replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK).trim();
                                String extAttrValue = "";
                                if (extAttrs.length > 1) {
                                    extAttrValue = extAttrs[1].replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK);
                                }
                                attrValueMap.put(extAttrName, extAttrValue);
                            }
                            resultMap.put(attrs[0].replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK).trim(), attrValueMap);
                        } else {
                            //用户组岗位角色机构多值情况
                            StringBuffer attrValueBuffer = new StringBuffer();
                            for (int j = 0; j < attrValues.length; j++) {
                                attrValueBuffer.append(attrValues[j]).append(SdkConsant.COMMA);
                            }
                            String attrValueTemp = attrValueBuffer.toString().substring(0, attrValueBuffer.length() - 1).replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK);
                            String[] attrValuesTemp = {};
                            if (attrValueTemp.indexOf(SdkConsant.UNIQUE_ID) != -1) {
                                attrValuesTemp = attrValueTemp.split(SdkConsant.UNIQUE_ID);
                            } else if (attrValueTemp.indexOf(SdkConsant.O) != -1) {
                                attrValuesTemp = attrValueTemp.split(SdkConsant.O);
                            }
                            StringBuffer sb = new StringBuffer();
                            for (int j = 0; j < attrValuesTemp.length; j++) {
                                if (attrValuesTemp[j].trim().length() > 0) {
                                    sb.append(attrValuesTemp[j].trim().substring(0, 32)).append(SdkConsant.COMMA);
                                }
                            }
                            resultMap.put(attrs[0].replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK).trim(), sb.toString().substring(0, sb.length() - 1));
                        }

                    } else {
                        //单值
                        String attrValue = attrValues[0].replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK).trim();
                        if (attrValue.indexOf(SdkConsant.UNIQUE_ID) != -1) {
                            attrValue = attrValue.trim().replaceAll(SdkConsant.UNIQUE_ID, SdkConsant.BLANK).substring(0, 32);
                        } else if (attrValue.indexOf(SdkConsant.O) != -1) {
                            attrValue = attrValue.trim().replaceAll(SdkConsant.O, SdkConsant.BLANK).substring(0, 32);
                        } else if (attrs[0].indexOf(SdkConsant.EXTPROPERTIES) != -1) {
                            Map attrValueMap = new HashMap();
                            for (int j = 0; j < attrValues.length; j++) {
                                String[] extAttrs = attrValues[j].split(SdkConsant.EQUAL);
                                String extAttrName = extAttrs[0].replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK).trim();
                                String extAttrValue = "";
                                if (extAttrs.length > 1) {
                                    extAttrValue = extAttrs[1].replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK);
                                }
                                attrValueMap.put(extAttrName, extAttrValue);
                            }
                            resultMap.put(attrs[0].replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK).trim(), attrValueMap);
                            continue;
                        }
                        resultMap.put(attrs[0].replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK).trim(), attrValue);
                    }
                } else {
                    resultMap.put(attrs[0].replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK).trim(), attrs[1].replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK).trim());
                }
            }

        }
        Map map = new HashMap();
        Iterator iterator = resultMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Object value = resultMap.get(key);
            if (value instanceof Set && ((Set) value).size() == 1) {
                map.put(key, ((Set) value).iterator().next());
            } else {
                map.put(key, value);
            }
        }
        return map;
    }
}
