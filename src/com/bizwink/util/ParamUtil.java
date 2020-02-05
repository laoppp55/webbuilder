package com.bizwink.util;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ParamUtil {
    public static String getParameter( HttpServletRequest request, String paramName ) {
        String temp = request.getParameter(paramName);
        if( temp != null && !temp.equals("") ) {
            return temp;
        } else {
            return null;
        }
    }

    public static String getParameter( HttpServletRequest request, String paramName, boolean emptyStringsOK ) {
        String temp = request.getParameter(paramName);
        if( emptyStringsOK ) {
            if( temp != null ) {
                return temp;
            } else {
                return null;
            }
        }
        else {
            return getParameter( request, paramName );
        }
    }

    public static List getParameterValues( HttpServletRequest request, String paramName ) {
        String temp[] = request.getParameterValues(paramName);
        List list = new ArrayList();

        if (temp != null) {
            for (int i=0; i<temp.length; i++) {
                list.add(temp[i]);
            }
        }
        return list;
    }

    public static boolean getBooleanParameter( HttpServletRequest request, String paramName ) {
        String temp = request.getParameter(paramName);
        if( temp != null && (temp.equals("true")||temp.equals("1"))) {
            return true;
        } else {
            return false;
        }
    }

    public static int getIntParameter( HttpServletRequest request, String paramName, int defaultNum ) {
        String temp = request.getParameter(paramName);
        if( temp != null && !temp.equals("") ) {
            int num = defaultNum;
            try {
                num = Integer.parseInt(temp);
            }
            catch( Exception ignored ) {}

            return num;
        } else {
            return defaultNum;
        }
    }

    public static boolean getCheckboxParameter( HttpServletRequest request, String paramName ) {
        String temp = request.getParameter(paramName);
        if( temp != null) {
            return true;
        } else {
            return false;
        }
    }

    public static String getAttribute( HttpServletRequest request, String attribName ) {
        String temp = (String)request.getAttribute(attribName);
        if( temp != null && !temp.equals("") ) {
            return temp;
        } else {
            return null;
        }
    }

    public static boolean getBooleanAttribute( HttpServletRequest request, String attribName ) {
        String temp = (String)request.getAttribute(attribName);
        if( temp != null && temp.equals("true") ) {
            return true;
        } else {
            return false;
        }
    }

    public static int getIntAttribute( HttpServletRequest request, String attribName, int defaultNum ) {
        String temp = (String)request.getAttribute(attribName);
        if( temp != null && !temp.equals("") ) {
            int num = defaultNum;
            try {
                num = Integer.parseInt(temp);
            }
            catch( Exception ignored ) {}

            return num;
        } else {
            return defaultNum;
        }
    }

    public static long getLongParameter( HttpServletRequest request, String paramName, long defaultNum ) {
        String temp = request.getParameter(paramName);
        if( temp != null && !temp.equals("") ) {
            long num = defaultNum;
            try {
                num = Long.parseLong(temp);
            }
            catch( Exception ignored ) {}
            return num;
        } else {
            return defaultNum;
        }
    }


    public static float getFloatParameter( HttpServletRequest request, String paramName, float defaultNum ) {
        String temp = request.getParameter(paramName);
        if( temp != null && !temp.equals("") ) {
            float num = defaultNum;
            try {
                num = Float.parseFloat(temp);
            }
            catch( Exception ignored ) {}
            return num;
        } else {
            return defaultNum;
        }
    }

    public static double getDoubleParameter( HttpServletRequest request, String paramName, double defaultNum ) {
        String temp = request.getParameter(paramName);
        if( temp != null && !temp.equals("") ) {
            double num = defaultNum;
            try {
                num = Double.parseDouble(temp);
            }
            catch( Exception ignored ) {}
            return num;
        } else {
            return defaultNum;
        }
    }
}
