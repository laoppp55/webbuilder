<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jdom.Element" %>
<%@ page import="java.util.Iterator" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    String realpath = request.getRealPath("/");
    FileReader fr = new FileReader(realpath + File.separator + "webfeedback" + File.separator + "sam1.xml");
    BufferedReader br = new BufferedReader(fr);

    int l;
    String readoneline = null;
    StringBuffer sb = new StringBuffer();
    while((readoneline = br.readLine()) != null){
        sb.append(readoneline+"\r\n");
    }
    br.close();

    XMLProperties properties = new XMLProperties(StringUtil.iso2gb(sb.toString()),0);
    List fields = properties.getFirstLevelChildrens();
    StringBuffer sql_sb = new StringBuffer();
    sql_sb.append("create table smp1 (\r\n" + "id int not null,\r\n");
    //for (Iterator iter = fields.iterator(); iter.hasNext(); ) {
    //Element el = (Element) iter.next();
    for(int i=0; i<fields.size(); i++) {
        Element el = (Element) fields.get(i);
        List vl = el.getChildren();
        Element el1 = (Element) vl.get(0);
        if (el1.getName().equals("name")) {
            sql_sb.append(el1.getValue() + " ");
        }

        int type = 0;     //设置字段是否需要定义长度
        el1 = (Element) vl.get(1);
        if (el1.getName().equals("type")) {
            if (el1.getValue().equals("text") || el1.getValue().equals("password")) {
                type = 1;
                sql_sb.append("varchar2(");
            } else if(el1.getValue().equals("radio") || el1.getValue().equals("select")) {
                sql_sb.append("int,\r\n");
            }
        }

        el1 = (Element) vl.get(2);
        if (el1.getName().equals("size") && type == 1) {
            if (el1.getValue() != null) {
                sql_sb.append(el1.getValue() + "),\r\n");
            } else {
                sql_sb.append("\r\n");
            }
        }
    }
    sql_sb.append("primary key(id)\r\n)");
    System.out.println(sql_sb.toString());
%>