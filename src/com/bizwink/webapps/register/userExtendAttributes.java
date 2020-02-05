package com.bizwink.webapps.register;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-8-29
 * Time: 21:43:47
 * To change this template use File | Settings | File Templates.
 */
public class userExtendAttributes {
    private int ID;                     //扩展属性ID
    private int articleID;              //文章ID
    private int controlType;            //扩展属性控件类型
    private int dataType;               //扩展属性数据类型
    private String cname;               //扩展属性中文名称
    private String ename;               //扩展属性英文名称
    private String stringValue;         //字符串值
    private String textValue;           //文本值
    private int numericValue;           //数值
    private float floatValue;           //小数值
    private int picWidth;
    private int picHeight;

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public int getID()
    {
        return ID;
    }

    public void setArticleID(int articleID)
    {
        this.articleID = articleID;
    }

    public int getArticleID()
    {
        return articleID;
    }

    public void setControlType(int controlType)
    {
        this.controlType = controlType;
    }

    public int getControlType()
    {
        return controlType;
    }

    public void setDataType(int dataType)
    {
        this.dataType = dataType;
    }

    public int getDataType()
    {
        return dataType;
    }

    public void setNumericValue(int numericValue)
    {
        this.numericValue = numericValue;
    }

    public int getNumericValue()
    {
        return numericValue;
    }

    public void setCName(String cname)
    {
        this.cname = cname;
    }

    public String getCName()
    {
        return cname;
    }

    public void setEName(String ename)
    {
        this.ename = ename;
    }

    public String getEName()
    {
        return ename;
    }

    public void setStringValue(String stringValue)
    {
        this.stringValue = stringValue;
    }

    public String getStringValue()
    {
        return stringValue;
    }

    public void setTextValue(String textValue)
    {
        this.textValue = textValue;
    }

    public String getTextValue()
    {
        return textValue;
    }

    public void setFloatValue(float floatValue)
    {
        this.floatValue = floatValue;
    }

    public float getFloatValue()
    {
        return floatValue;
    }

    public void setPicWidth(int picWidth)
    {
        this.picWidth = picWidth;
    }

    public int getPicWidth()
    {
        return picWidth;
    }

    public void setPicHeight(int picHeight)
    {
        this.picHeight = picHeight;
    }

    public int getPicHeight()
    {
        return picHeight;
    }
}
