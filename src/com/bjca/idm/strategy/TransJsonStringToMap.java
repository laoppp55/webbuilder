package com.bjca.idm.strategy;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Title:       TransJsonStringToMap
 * Description:
 * Company:     BJCA
 * Author:      liwei
 * Date:        2014/4/18
 * Time:        10:01
 */
public class TransJsonStringToMap implements TransformStrategy {

    public Map transResultStringToMap(String result) {
        Map map = new HashMap();
        result = result.substring(1, result.length() - 2);
        StringTokenizer stringTokenizer = new StringTokenizer(result, SdkConsant.COMMA);
        while (stringTokenizer.hasMoreElements()) {
            String strTemp = stringTokenizer.nextToken();
            //特殊处理amUrl
            if (strTemp.startsWith(SdkConsant.AM_URL_PREFIX)) {
                String amUrlKey = strTemp.substring(1, strTemp.indexOf(SdkConsant.COLON) - 1);
                String amUrlValue = strTemp.substring(strTemp.indexOf(SdkConsant.COLON) + 2, strTemp.length() - 1);
                map.put(amUrlKey, amUrlValue);
            } else {
                StringTokenizer stringTokenizer1 = new StringTokenizer(strTemp, SdkConsant.COLON);
                while (stringTokenizer1.hasMoreTokens()) {
                    map.put(stringTokenizer1.nextToken().replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK), stringTokenizer1.nextToken().replaceAll(SdkConsant.QUOTATION, SdkConsant.BLANK));
                }
            }

        }
        return map;
    }

    public Map TransMap(String result){
      /*  StrategyContext strategyContext = new StrategyContext(new TransJsonStringToMap());
        return strategyContext.trans(result);*/
        Map map = new HashMap();
        Map infoMap=new HashMap();
        result=result.replaceAll("\"","");
        result = result.substring(1, result.length() - 2);
        String infos[]=new String[2];
        infos[0]=result.substring(0, result.indexOf(SdkConsant.COMMA));
        String status=null;
        if(infos[0].split(":").length>1){
            status=infos[0].split(":")[1];
        }
        infos[1]=result.substring(result.indexOf(SdkConsant.COMMA)+1, result.length() );
        String detailInfo=infos[1].substring(infos[1].indexOf("{")+1,infos[1].length()-1);


        StringTokenizer stringTokenizer=new StringTokenizer(detailInfo, SdkConsant.COMMA);
        while (stringTokenizer.hasMoreElements()) {
            String strTemp = stringTokenizer.nextToken();
            String key = strTemp.substring(0, strTemp.indexOf(SdkConsant.COLON) );
            String value = strTemp.substring(strTemp.indexOf(SdkConsant.COLON) + 1, strTemp.length() );
            value = value.replace("[{", "").replace("}]", "");
            infoMap.put(key, value);
        }
        map.put("info",infoMap);
        map.put("status",status);

        return map;
    }


    public Map<String, Map<String, Object>> readJson2Map(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Map<String, Object>> maps = objectMapper.readValue(json, Map.class);
        return maps;
    }

}
