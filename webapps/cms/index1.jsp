<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.security.SecurityCheck" %>
<%@ page import="com.bizwink.log.ILogManager" %>
<%@ page import="com.bizwink.log.LogPeer" %>
<%@ page import="com.bizwink.cms.news.Article" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.bizwink.log.PublishArticleListOfWeek" %>
<%@ page import="com.bizwink.log.PublishArticleCountOfDept" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Calendar" %>
<%--Created by IntelliJ IDEA.
User: Administrator
Date: 18-6-6
Time: 上午10:22
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String deptid = authToken.getDepartment();
    int siteid = authToken.getSiteID();
    String xdata = "[";
    String data = "[";
    String legendData = "[";
    String dataOfDept = "[";

    ILogManager logManager = LogPeer.getInstance();
    List<PublishArticleListOfWeek> articles = null;
    if (SecurityCheck.hasPermission(authToken,54))
        articles = logManager.getArticlesOfToday(siteid,null);
    else
        articles = logManager.getArticlesOfToday(siteid,deptid);

    //获取从今天开始往前一个月各个部门的文章发布量，并进行倒序排列
    List<PublishArticleCountOfDept> publishArticleCountOfDepts = logManager.getPublishArticleCountOFMonthOfDept(siteid);
    Timestamp today = new Timestamp(System.currentTimeMillis());
    Calendar start_cal = Calendar.getInstance();
    start_cal.setTimeInMillis(today.getTime());
    start_cal.add(Calendar.MONTH,-1);
    Timestamp starttime = new Timestamp(start_cal.getTimeInMillis());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
%>
<!DOCTYPE html>
<html>
<head>
    <title>网站内容管理系统</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="design/css/layui.css">
    <link rel="stylesheet" href="css/mycss.css">
    <link rel="stylesheet" href="css/cms_css.css">
    <link rel="stylesheet" type="text/css" href="css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="css/themes/icon.css">

    <script type="text/javascript" src="js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="js/echarts.min.js"></script>
    <script type="text/javascript" src="js/json2.js"></script>
    <script type="text/javascript" src="design/layui.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui-lang-zh_CN.js"></script>

    <!--引用xtree-->
    <script type="text/javascript" src="design/layui-xtree.js"></script>
    <script>
        $(document).ready(function(){
            layui.use('element', function(){
                var element = layui.element;
            });
        });
        function goout() {
            window.location.href = "exit.jsp";
        }
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">网站内容管理系统</div>
        <jsp:include page="include/homepagemenu.jsp"></jsp:include>    </div>

    <div class="layui-body" style="left: 30px;top:80px;">
        <!-- 内容主体区域 -->
        <div id="contentid">
            <!--div id="searchtimeid" style="padding-bottom: 20px">
                开始时间：<input type=text  class="easyui-datetimebox" name="bdatetime" value="" style="width:200px">
                结束时间：<input type=text  class="easyui-datetimebox" name="edatetime" value="" style="width:200px">
                          <input type="button" name="queryPublisArticles" value="查询" class="easyui-linkbutton">
            </div-->


            <% if (articles.size()>0) {%>
            <div id="deptbrowseid" style="padding-bottom: 5px;">
                <div style="float: left">最近三天发布的信息</div>  <div style="padding-left: 550px;">&nbsp;<!--查看更多排行--></div>
                <div style="float: left">
                    <table border="1" width="100%" align="center">
                        <tr style="height: 30px;background-color: #F7F7F7">
                            <td width="10%" align="center">发布日期</td>
                            <td width="10%"  align="center">发布数量</td>
                            <td width="80%">文章标题</td>
                        </tr>
                        <%
                            int days = (articles.size()>3)?3:articles.size();
                            for(int ii=0;ii<days;ii++) {
                                PublishArticleListOfWeek article = articles.get(ii);
                                String createdate = article.getStrdate();
                                xdata = xdata + createdate + ",";
                                List<Article> tlist = article.getArticles();
                                int rows = tlist.size();
                                data = data + rows + ",";
                                Article article11 = tlist.get(0);
                        %>
                        <tr style="height: 30px;background-color: #F7F7F7">
                            <td width="10%" rowspan="<%=(rows>2)?2:rows%>" align="center"><%=createdate%></td>
                            <td width="10%" rowspan="<%=(rows>2)?2:rows%>" align="center"><%=article.getArticles().size()%></td>
                            <td width="80%"><%=article11.getMainTitle()%></td>
                        </tr>
                        <%for(int jj=1;jj<((rows>2)?2:rows);jj++) {
                            article11 = tlist.get(jj);
                        %>
                        <tr style="height: 30px;background-color: #F7F7F7">
                            <td width="80%"><%=article11.getMainTitle()%></td>
                        </tr>
                        <%}}
                            xdata = xdata.substring(0,xdata.length()-1) + "]";
                            data = data.substring(0,data.length()-1) + "]";
                            System.out.println(xdata);
                            System.out.println(data);
                        %>
                    </table>
                </div>
                <div id="daypic" style="padding-top:0px;padding-left: 550px;width:700px;height:250px;"></div>
            </div>
            <%}%>

            <!--div id="deptpubid" style="padding-bottom: 5px"><img src="images/deptpub.png"></div-->
            <div id="deptpubofmonthid" style="padding-bottom: 5px;width: 550px;float:left;">
                <table border="1" width="100%" align="center">
                    <tr style="height: 30px;background-color: #F7F7F7">
                        <td colspan="2" align="center">
                            <%=simpleDateFormat.format(starttime) + "----" + simpleDateFormat.format(today)%>
                        </td>
                    </tr>
                    <tr style="height: 30px;background-color: #F7F7F7">
                        <td width="50%" align="center">部门名称</td>
                        <td width="50%"  align="center">信息发布量</td>
                    </tr>
                    <%
                        int totalPublishArticleCount = 0;
                        for(int ii=0;ii<publishArticleCountOfDepts.size();ii++) {
                            PublishArticleCountOfDept publishArticleCountOfDept = publishArticleCountOfDepts.get(ii);
                            String deptname = publishArticleCountOfDept.getDeptname();
                            int publishArticleCount = publishArticleCountOfDept.getPublishArticleCount();
                            totalPublishArticleCount = totalPublishArticleCount + publishArticleCount;
                    %>
                    <tr style="height: 30px;background-color: #F7F7F7">
                        <td width="50%" align="center"><%=deptname%></td>
                        <td width="50%"  align="center"><%=publishArticleCount%></td>
                    </tr>
                    <%}
                        // {value: 50, name: '50%-学生'},
                        for(int ii=0;ii<publishArticleCountOfDepts.size();ii++) {
                            PublishArticleCountOfDept publishArticleCountOfDept = publishArticleCountOfDepts.get(ii);
                            String deptname = publishArticleCountOfDept.getDeptname();
                            int publishArticleCount = publishArticleCountOfDept.getPublishArticleCount();
                            String percent = (double)Math.round( publishArticleCount/(double)totalPublishArticleCount*1000)/10+"%";
                            legendData = legendData + "'" + percent + deptname + "',";
                            dataOfDept = dataOfDept + "{value:" + publishArticleCount + ",name:'" + percent + "-" + deptname + "'},";
                        }
                        legendData = legendData.substring(0,legendData.length()-1) + "]";
                        dataOfDept = dataOfDept.substring(0,dataOfDept.length()-1) + "]";
                        System.out.println(legendData);
                        System.out.println(dataOfDept);
                    %>
                </table>
                <!--img src="images/1111.jpg"-->
            </div>
            <div id="deptpic" style="padding-left: 550px;padding-bottom: 10px;width:700px;height:400px;"></div>
        </div>
    </div>

    <div class="layui-footer" style="left: 0px;">
    </div>
</div>
<script type="text/javascript">
    <% if (articles.size()>0) {%>
    var myChart = echarts.init(document.getElementById('daypic'));
    var option = {
        tooltip: {},
        xAxis: {
            data: <%=xdata%>
        },
        yAxis: {
            type: 'value',
            name: '发布量',
            // min: 0,
            // max: 250,
            // interval: 10, //间隔
            minInterval: 1, //设置成1保证坐标轴分割刻度显示成整数。
            axisLabel: {
                formatter: '{value}'
            }
        },
        series: [{
            name: '最近7天文章发布量',
            type: 'line',
            data: <%=data%>
        }]
    };
    myChart.setOption(option);
    <%}%>

    var deptChart = echarts.init(document.getElementById('deptpic'));
    optionOfDept = {
        title:{
            text:'按类型统计',
            top:'bottom',
            left:'center',
            textStyle:{
                fontSize: 12,
                fontWeight: '',
                color: '#333'
            },
        },//标题
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },//提示框，鼠标悬浮交互时的信息提示
        legend: {
            show: false,
            orient: 'vertical',
            x: 'left',
            data: <%=legendData%>
        },//图例属性，以饼状图为例，用来说明饼状图每个扇区，data与下边series中data相匹配
        graphic:{
            type:'text',
            left:'center',
            top:'center',
            style:{
                text:'文章发布量部门排行统计\n'+'10', //使用“+”可以使每行文字居中
                textAlign:'center',
                font:'italic bolder 16px cursive',
                fill:'#000',
                width:30,
                height:30
            }
        },//此例饼状图为圆环中心文字显示属性，这是一个原生图形元素组件，功能很多
        series: [
            {
                name:'文章发布量统计',//tooltip提示框中显示内容
                type: 'pie',//图形类型，如饼状图，柱状图等
                radius: ['35%', '65%'],//饼图的半径，数组的第一项是内半径，第二项是外半径。支持百分比，本例设置成环形图。具体可以看文档或改变其值试一试
                //roseType:'area',是否显示成南丁格尔图，默认false
                itemStyle: {
                    normal:{
                        label:{
                            show:true,
                            textStyle:{color:'#3c4858',fontSize:"12"},
                            formatter:function(val){   //让series 中的文字进行换行
                                return val.name.split("-").join("\n");}
                        },//饼图图形上的文本标签，可用于说明图形的一些数据信息，比如值，名称等。可以与itemStyle属性同级，具体看文档
                        labelLine:{
                            show:true,
                            lineStyle:{color:'#3c4858'}
                        }//线条颜色
                    },//基本样式
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)',//鼠标放在区域边框颜色
                        textColor:'#000'
                    }//鼠标放在各个区域的样式
                },
                data: <%=dataOfDept%>,//数据，数据中其他属性，查阅文档
                color: ['#51CEC6','#FFB703','#5FA0FA'],//各个区域颜色
            },//数组中一个{}元素，一个图，以此可以做出环形图
        ],//系列列表
    };
    deptChart.setOption(optionOfDept)
</script>
</body>
</html>
