package com.bizwink.cms.util;

import com.bizwink.util.zTreeNodeObj;

import java.util.List;

/**
 * <p>Title: BW-WebBuilder</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: Beijing Bizwink SoftwareInc</p>
 * @author unascribed
 * @version 1.0
 */

public class listElement {
    public String cnname;
    public String url;
    public int errcode;
    public int num;

    public listElement() {

    }

    public static String getTextValue(List options,String strvalues) {
        String textval = "";
        String[] values = strvalues.split(",");
        for(int kk=0; kk<values.length; kk++) {
            for(int ii=0; ii<options.size();ii++) {
                zTreeNodeObj o = (zTreeNodeObj)options.get(ii);
                String tbuf = o.getName();
                int posi = tbuf.indexOf("|");
                String text = tbuf.substring(0,posi);
                String keyval = tbuf.substring(posi+1);
                if (keyval.trim().equals(values[kk].trim())) {
                    textval = textval + text + ",";
                    break;
                }
            }
        }
        if (textval.length()>0) textval = textval.substring(0,textval.length()-1);
        return textval;
    }
}