<%@ page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.log.*" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String userid = authToken.getUserID();
    String deptid = authToken.getDepartment();
    String baseDir= application.getRealPath("/");
    int siteid = authToken.getSiteID();

    ILogManager logManager = LogPeer.getInstance();
    LogColumnTree logColumnTree = logManager.getLogColumnTree(authToken.getSiteID());
    PublishArticleCountOfColumn[] columnTreeNode = logColumnTree.getPublishArticleCountOfColumns();

    //首页默认统计一个季度内各个栏目的信息发布量
    logColumnTree = logManager.getWorkingByCol(siteid,logColumnTree);
    columnTreeNode = logColumnTree.getPublishArticleCountOfColumns();

    List<PublishArticleCountOfColumn> thelist = new ArrayList<>();
    thelist.add(columnTreeNode[0]);

    StringBuffer rowHtml = new StringBuffer();
    List<NodeHTML> nodeHTMLList = new ArrayList<>();
    for(int ii=0; ii<columnTreeNode.length;ii++) {
        if (columnTreeNode[ii]!=null) {
            if (columnTreeNode[ii].getLevel() == 1) {
                //找出当前这个第一级栏目的所有子栏目
                PublishArticleCountOfColumn[] secondSubNodes = new PublishArticleCountOfColumn[columnTreeNode[ii].getSubcolnum()];
                int subnodenum = 0;
                for(int jj=0;jj<columnTreeNode.length;jj++) {
                    if (columnTreeNode[jj]!=null) {
                        if (columnTreeNode[jj].getPcolumnid() == columnTreeNode[ii].getColumnid()){
                            secondSubNodes[subnodenum] = columnTreeNode[jj];
                            subnodenum = subnodenum + 1;
                        }
                    }
                    //如果找出的子节点数已经等于当前的节点的子节点数，停止查找
                    if (subnodenum == columnTreeNode[ii].getSubcolnum()) break;
                }

                //获取第二级栏目的所有子栏目
                PublishArticleCountOfColumn[][] threeSubNodes = new PublishArticleCountOfColumn[secondSubNodes.length][];
                for(int jj=0;jj<secondSubNodes.length;jj++) {
                    threeSubNodes[jj] = new PublishArticleCountOfColumn[secondSubNodes[jj].getSubcolnum()];
                    subnodenum = 0;
                    for(int kk=0;kk<columnTreeNode.length;kk++) {
                        if (columnTreeNode[kk]!=null) {
                            if (columnTreeNode[kk].getPcolumnid() == secondSubNodes[jj].getColumnid()){
                                threeSubNodes[jj][subnodenum] = columnTreeNode[kk];
                                subnodenum = subnodenum + 1;
                            }
                        }
                        //如果找出的子节点数已经等于当前的节点的子节点数，停止查找
                        if (subnodenum == secondSubNodes[jj].getSubcolnum()) {
                            break;
                        }
                    }
                }

                rowHtml = new StringBuffer();
                NodeHTML nodeHTML = new NodeHTML();
                nodeHTML.setColumnid(columnTreeNode[ii].getColumnid());

                /*
                //两层栏目统计表
                if (secondSubNodes.length > 0)
                    rowHtml.append("<tr><td rowspan=\"" + columnTreeNode[ii].getSubcolnum() + "\">" + columnTreeNode[ii].getColname() + "</td><td>" + secondSubNodes[0].getColname() + "</td></tr>\r\n");
                else
                    rowHtml.append("<tr><td rowspan=\"" + columnTreeNode[ii].getSubcolnum() + "\">" + columnTreeNode[ii].getColname() + "</td><td>&nbsp;</td></tr>\r\n");

                for (int jj = 1; jj < columnTreeNode[ii].getSubcolnum(); jj++) {
                    rowHtml.append("<tr><td>" + secondSubNodes[jj].getColname() + "</td></tr>\r\n");
                }*/

                //三层栏目统计表
                if (secondSubNodes.length > 0) {
                    int firstSpanRows = 0;
                    for(int jj=0;jj<secondSubNodes.length;jj++) {
                        if (secondSubNodes[jj].getSubcolnum()>0)
                            firstSpanRows = firstSpanRows + secondSubNodes[jj].getSubcolnum();
                        else
                            firstSpanRows = firstSpanRows + 1;
                    }
                    rowHtml.append("<tr><td rowspan=\"" + firstSpanRows + "\">" + columnTreeNode[ii].getColname() + "</td><td rowspan=\"" + secondSubNodes[0].getSubcolnum() + "\">" + secondSubNodes[0].getColname() + "</td><td>" + threeSubNodes[0][0].getColname() + "</td></tr>\r\n");
                }else
                    rowHtml.append("<tr><td rowspan=\"" + columnTreeNode[ii].getSubcolnum() + "\">" + columnTreeNode[ii].getColname() + "</td><td>&nbsp;</td><td>&nbsp;</td></tr>\r\n");

                for(int k=0;k<secondSubNodes.length;k++) {
                    for(int l=0;l<secondSubNodes[k].getSubcolnum();l++) {
                        rowHtml.append("<tr><td>" + threeSubNodes[k][l].getColname() + "</td></tr>\r\n");
                    }
                    //rowHtml.append("<tr><td rowspan=\"" + secondSubNodes[k].getSubcolnum() + "\">" + secondSubNodes[k].getColname() + "</td><td>" + threeSubNodes[k][0].getColname() + "</td></tr>\r\n");
                }

                nodeHTML.setRowHtml(rowHtml.toString());
                //System.out.println(nodeHTML.getRowHtml());
                nodeHTMLList.add(nodeHTML);
            }
        }
    }

    String html = "<html>\r\n" +
            "<head><title>test</title></head>\r\n" +
            "</body>\r\n" +
            "<table border=1>\r\n";
    for(int ii=0;ii<nodeHTMLList.size();ii++) {
        NodeHTML nodeHTML = nodeHTMLList.get(ii);
        int colid = nodeHTML.getColumnid();
        html = html + nodeHTML.getRowHtml();
    }
    html = html + "</table>\r\n" +
            "</body>\r\n" +
            "</html>";

    FileUtil.writeTextFile(html,"c:\\data\\11.html");


    //out.println(tableHtml);
%>
