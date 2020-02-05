<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-5-28
  Time: 下午3:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>layer-更懂你的web弹窗解决方案</title>
    <style>
        .table-d table{border-right:1px solid #F00;border-bottom:1px solid #F00}
        .table-d table td{border-left:1px solid #F00;border-top:1px solid #F00}
    </style>

    <link rel="stylesheet" type="text/css" href="../css/webuploader.css">
    <link rel="stylesheet" type="text/css" href="../css/diyUpload.css">
    <script type="text/javascript" src="../js/webuploader.html5only.min.js"></script>
    <script type="text/javascript" src="../js/diyUpload.js"></script>
    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
    <script src="../layer/layer.js"></script>

    <script type="text/javascript" language="javascript">
        function about() {
            layer.open({
                type: 2,
                title: '欢迎页',
                maxmin: true,
                area: ['800px', '600px'],
                content: 'addWzinfo.jsp'
                //cancel: function(){
                //       layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', {time: 5000, icon:6});
                //}
            });
        }

        function addwzinfo() {
            retval = window.showModalDialog("addWzinfo.jsp","","dialogWidth=600px;dialogHeight=550px;dialogLeft=400px;dialogTop=100px;center=no;resizable=yes");
        }

        //让指定的DIV始终显示在屏幕正中间
        function letDivCenter(divName){
            var top = ($(window).height() - $(divName).height())/2;
            var left = ($(window).width() - $(divName).width())/2;
            var scrollTop = $(document).scrollTop();
            var scrollLeft = $(document).scrollLeft();
            $(divName).css( { position : 'absolute', 'top' : top + scrollTop, left : left + scrollLeft } ).show();
        }
    </script>
</head>
<body>
<div>
    商品信息维护
</div>

<div>
    <!-- Unnamed (矩形) -->
    <div id="u3407" class="ax_default box_1">
        <div id="u3407_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u3408" class="text" style="visibility: visible;">
            <p><span>&nbsp;&nbsp;&nbsp; 发布商品只需三步：</span><span style="color:#797979;">发布商品信息&nbsp;&nbsp; &gt;&nbsp;&nbsp; 填写商品详情&nbsp;&nbsp; &gt;&nbsp;&nbsp; 提交信息成功，等待平台审核</span></p>
        </div>
    </div>

    <!-- Unnamed (矩形) -->
    <div id="u3241" class="ax_default button">
        <form name="wzsearch" action="">
            <div id="u3241_div" class=""></div>
            商品信息：<input id="u3240_input" type="text" value=""/>
            <input type="button" name="search" value="查询">
            状态：<select id="u3409_input" title="状态">
            <option selected value="请选择">请选择</option>
            <option value="上架">上架</option>
            <option value="审核中">审核中</option>
        </select>
        </form>
    </div>
    <div>
        <input type="button" name="pubwz" value="发布商品" onclick="javascript:addwzinfo();">
        <input type="button" name="delwz" value="删除"  onclick="javascript:delwz();">
        <!--input type="button" name="delwz" value="删除"  onclick="javascript:about();">
        <input type="button" name="tandiv" value="弹出层"  onclick="javascript:letDivCenter('#infos');"-->
    </div>

    <div class="table-d">
        <table cellspace="0">
            <tr>
                <td>请选择</td><td>商品图片</td><td>商品名称</td><td>所属类别</td><td>发布时间</td><td>商品描述</td><td>状态</td><td>操作</td>
            </tr>
        </table>
    </div>

    <div id="infos" class="layui-form" style="display: none">
        <form name="wzform" class="layui-form" action="">
            商品名称：<input type="text" name="wzname" value="" size="50" class="layui-input"><br />
            商品类别：
            <select name="wztype" style="width: 300">
                <option value="0">请选择</option>
                <option value="1">钢材</option>
            </select><br />
            商品描述：
            <textarea rows="20" cols="50" name="wzdesc"></textarea><br />
            商品图片：<br />
            <input type="file" name="wzimg1"><br />
            <input type="file" name="wzimg2"><br />
            <input type="file" name="wzimg3"><br />
            <input type="file" name="wzimg4"><br />
            <input type="file" name="wzimg5"><br />
            <input type="button" name="uploadinfo" value="确定">
            <input type="button" name="cancel" value="取消" onclick="window.close();">
        </form>
    </div>
</div>
</body>
</html>
