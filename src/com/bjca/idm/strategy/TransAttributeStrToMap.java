package com.bjca.idm.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Title:       TransAttributeStrToMap
 * Description:
 * Company:     BJCA
 * Author:      liwei
 * Date:        2014/4/18
 * Time:        10:00
 */
public class TransAttributeStrToMap implements TransformStrategy {

    public Map transResultStringToMap(String result) {
        Map map = new HashMap();
        String[] results = result.split(SdkConsant.SPACE);
        int resultLength = results.length;
        for (int i = 0; i < resultLength - 1; i++) {
            String s = results[i];
            System.out.println(i+"="+ s);
            //如果是attribute.name（密码的hash值不给用户发送）
            if (s != null && s.indexOf(SdkConsant.USERDETAILS_ATTRIBUTE_NAME) != -1 && s.indexOf(SdkConsant.PASSWORD) == -1 && s.indexOf(SdkConsant.OBJECTCLASS) == -1) {
                //如果是如果属性值不为空
                if (i < resultLength - 2) {
                    //i为name，判断i+1和i+2是不是都是value，如果是则为多值
                    if (results[i + 1].indexOf(SdkConsant.USERDETAILS_ATTRIBUTE_VALUE) != -1 && results[i + 2].indexOf(SdkConsant.USERDETAILS_ATTRIBUTE_VALUE) != -1) {
                        if (s.indexOf(SdkConsant.EXTPROPERTIES) != -1) {
                            Map extPropertiesMap = new HashMap();
                            //从i+1即value值开始遍历
                            for (int j = i + 1; j < resultLength; j++) {
                                String extProperty = results[j];
                                if (extProperty != null && extProperty.indexOf(SdkConsant.USERDETAILS_ATTRIBUTE_NAME) != -1) {
                                    //value遍历完成，因为下一次循环i还要+1，所以
                                    i = j - 1;
                                    break;
                                } else {
                                    //扩展属性的值是key=value，解析成map返回
                                    String[] extPropertyKeyValue = {};
                                    if (extProperty != null) {
                                        extPropertyKeyValue = extProperty.split("=");
                                        if (extPropertyKeyValue.length > 2) {
                                            extPropertiesMap.put(extPropertyKeyValue[1], extPropertyKeyValue[2]);
                                        }
                                    }
                                }
                            }
                            map.put(s.replaceAll(SdkConsant.USERDETAILS_ATTRIBUTE_NAME, SdkConsant.BLANK), extPropertiesMap);
                        } else {
                            //如果是其他多值情况现在多值都是DN格式，机构，角色，岗位，用户组
                            StringBuffer multiValuesBuffer = new StringBuffer();
                            //从i+1即value值开始遍历
                            for (int j = i + 1; j < resultLength; j++) {
                                String multiValue = results[j];
                                if (multiValue != null && multiValue.indexOf(SdkConsant.USERDETAILS_ATTRIBUTE_NAME) != -1) {
                                    //value遍历完成，因为下一次循环i还要+1，所以
                                    i = j - 1;
                                    break;
                                } else {
                                    //返回DN格式的多值
                                    if (multiValue != null) {
                                        multiValuesBuffer.append(multiValue.replaceAll(SdkConsant.USERDETAILS_ATTRIBUTE_VALUE, SdkConsant.BLANK)).append(SdkConsant.COMMA);
                                    }
                                }
                            }
                            //去掉结尾逗号
                            String attrValueTemp = multiValuesBuffer.toString().substring(0, multiValuesBuffer.length() - 1);
                            String[] attrValuesTemp = {};

                            if (attrValueTemp.indexOf(SdkConsant.UNIQUE_ID) != -1) {
                                //如果是角色岗位用户组
                                attrValuesTemp = attrValueTemp.split(SdkConsant.UNIQUE_ID);
                            } else if (attrValueTemp.indexOf(SdkConsant.O) != -1) {
                                //如果是机构
                                attrValuesTemp = attrValueTemp.split(SdkConsant.O);
                            }
                            StringBuffer sb = new StringBuffer();
                            for (int j = 0; j < attrValuesTemp.length; j++) {
                                if (attrValuesTemp[j].trim().length() > 0) {
                                    //取流水号
                                    sb.append(attrValuesTemp[j].trim().substring(0, 32)).append(SdkConsant.COMMA);
                                }
                            }
                            map.put(s.replaceAll(SdkConsant.USERDETAILS_ATTRIBUTE_NAME, SdkConsant.BLANK), sb.toString().substring(0, sb.length() - 1));
                        }
                    } else {
                        //单值
                        String attrValue = results[i + 1].replaceAll(SdkConsant.USERDETAILS_ATTRIBUTE_VALUE, SdkConsant.BLANK);
                        if (attrValue.indexOf(SdkConsant.UNIQUE_ID) != -1) {
                            attrValue = attrValue.trim().replaceAll(SdkConsant.UNIQUE_ID, SdkConsant.BLANK).substring(0, 32);
                        } else if (attrValue.indexOf(SdkConsant.O) != -1) {
                            attrValue = attrValue.trim().replaceAll(SdkConsant.O, SdkConsant.BLANK).substring(0, 32);
                        } else if (s.indexOf(SdkConsant.EXTPROPERTIES) != -1) {
                            Map extPropertiesMap = new HashMap();
                            String[] extPropertyKeyValue = attrValue.split(SdkConsant.EQUAL);
                            if (extPropertyKeyValue.length > 1) {
                                extPropertiesMap.put(extPropertyKeyValue[0], extPropertyKeyValue[1]);
                            }
                            map.put(s.replaceAll(SdkConsant.USERDETAILS_ATTRIBUTE_NAME, SdkConsant.BLANK), extPropertiesMap);
                            i++;
                            continue;
                        }
                        map.put(s.replaceAll(SdkConsant.USERDETAILS_ATTRIBUTE_NAME, SdkConsant.BLANK), attrValue);
                        i++;
                    }
                } else if (i == resultLength - 1) {
                    map.put(s.replaceAll(SdkConsant.USERDETAILS_ATTRIBUTE_NAME, SdkConsant.BLANK), SdkConsant.BLANK);
                } else {
                    //单值
                    String attrValue = results[i + 1].replaceAll(SdkConsant.USERDETAILS_ATTRIBUTE_VALUE, SdkConsant.BLANK);
                    if (attrValue.indexOf(SdkConsant.UNIQUE_ID) != -1) {
                        attrValue = attrValue.trim().replaceAll(SdkConsant.UNIQUE_ID, SdkConsant.BLANK).substring(0, 32);
                    } else if (attrValue.indexOf(SdkConsant.O) != -1) {
                        attrValue = attrValue.trim().replaceAll(SdkConsant.O, SdkConsant.BLANK).substring(0, 32);
                    } else if (s.indexOf(SdkConsant.EXTPROPERTIES) != -1) {
                        Map extPropertiesMap = new HashMap();
                        String[] extPropertyKeyValue = attrValue.split(SdkConsant.EQUAL);
                        if (extPropertyKeyValue.length > 1) {
                            extPropertiesMap.put(extPropertyKeyValue[0], extPropertyKeyValue[1]);
                        }
                        map.put(s.replaceAll(SdkConsant.USERDETAILS_ATTRIBUTE_NAME, SdkConsant.BLANK), extPropertiesMap);
                        i++;
                        continue;
                    }
                    map.put(s.replaceAll(SdkConsant.USERDETAILS_ATTRIBUTE_NAME, SdkConsant.BLANK), attrValue);
                    i++;
                }
            }
        }
        return map;
    }
}
