<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.service.WuziclassService" %>
<%@ page import="com.bizwink.po.WzClass" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%
    ApplicationContext appContext = SpringInit.getApplicationContext();
    List<WzClass> wzClassList = null;
    if (appContext!=null) {
        WuziclassService wuziclassService = (WuziclassService)appContext.getBean("wuziclassService");
        wzClassList = wuziclassService.getMainClasses(BigDecimal.valueOf(0));
        System.out.println("size==" + wzClassList.size());
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>后台管理</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <link rel="stylesheet" href="../css/bootstrap/bootstrap-theme.min.css" />
    <link rel="stylesheet" href="../css/bootstrap/bootstrap.min.css" />
    <link rel="stylesheet" href="../css/bootstrap/bootstrap-table.css">
    <link rel="stylesheet" href="../css/bootstrap/jquery.treegrid.css">
</head>
<body>
<div class="container">
    <h1>Twitter bootstrap tutorial</h1>
    <nav class="navbar navbar-inverse">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-menu" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Brand</a>
        </div>

        <div id="navbar-menu" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#">Page One</a></li>
                <li><a href="#">Page Two</a></li>
            </ul>
        </div>
    </nav>

    <div id="content" class="row-fluid">
        <div class="col-md-3">
            <h2>Main Classes</h2>
            <ul class="nav nav-tabs nav-stacked">
                <%
                    for(int ii=0; ii<wzClassList.size(); ii++) {
                        WzClass wzClass = wzClassList.get(ii);
                %>
                <li><a href='#' onclick="javascript:showSubWzclass('<%=wzClass.getCODE()%>')"><%=wzClass.getNAME()%></a></li>
                <%}%>
                <!--li><a href='#'>Another Link 2</a></li>
                <li><a href='#'>Another Link 3</a></li-->
            </ul>

        </div>
        <div class="col-md-9">
            <h2>Sidebar</h2>
            <table id="table"></table>
        </div>
    </div>
</div>
<script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.treegrid.min.js"></script>
<script type="text/javascript" src="../js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/bootstrap/bootstrap-table.js"></script>
<script type="text/javascript" src="../js/bootstrap/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="../js/bootstrap/bootstrap-table-treegrid.js"></script>
<script language="JavaScript" type="text/javascript">
    $(function () {
        var $table = $("#table");
        $table.bootstrapTable({
            url:'treegrid.json',
            striped:true,
            sidePagenation:'server',
            idField:'id',
            columns:[
                {
                    field: 'ck',
                    checkbox: true
                },
                {
                    field:'name',
                    title:'名称'
                },
                {
                    field:'referred',
                    title:'简称'
                }
            ],
            treeShowField: 'name',
            parentIdField: 'pid',
            onLoadSuccess: function(data) {
                $table.treegrid({
                    initialState: 'collapsed',                                           //收缩
                    treeColumn: 1,                                                         //指明第几列数据改为树形
                    expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
                    expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
                    onChange: function() {
                        $table.bootstrapTable('resetWidth');
                    }
                });
            }
        });
    });
    function showSubWzclass(code) {
        alert(code);
    }
</script>
</body>
</html>
