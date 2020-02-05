<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
    <title>
        Ajax进度条
    </title>
    <script type="text/javascript">
        //xmlHttpRequest对象
        var xmlHttp;
        var key;

        //进度条颜色
        var bar_color='gray';
        var span_id='block';
        var clear="&nbsp;&nbsp;&nbsp;"

        //创建xmlHttpRequest对象
        function createXMLHttpRequest(){
            if (window.ActiveXObject){
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            else if(window.XMLHttpRequest){
                xmlHttp = new XMLHttpRequest();
            }
        }

        //执行方法
        function go(){
            createXMLHttpRequest();
            checkDiv();
            var url = "/webbuilder/probar?task=create";
            //隐藏按钮
            var button = document.getElementById("go");
            button.disabled = true;

            xmlHttp.open("GET",url,true);
            xmlHttp.onreadystatechange = goCallback;
            xmlHttp.send(null);
        }

        //回调方法
        function goCallback(){
            alert("go Callback here");
            if (xmlHttp.readyState == 4){
                alert("go Callback here 4==" + xmlHttp.status);
                if (xmlHttp.status == 200){
                    alert("go Callback here 4 200");
                    //设置更新时间
                    setTimeout("pollServer()",2000);
                }
            }
        }

        //刷新，重新发送
        function pollServer(){
            alert("hello word");
            createXMLHttpRequest();
            var url = "/webbuilder/probar?task=poll&key=" + key;
            xmlHttp.open("GET",url,true);
            xmlHttp.onreadystatechange = pollCallback;
            xmlHttp.send(null);
        }

        //回调方法
        function pollCallback(){
            if (xmlHttp.readyState == 4){
                if (xmlHttp.status == 200){
                    //完成百分比
                    var percent_complete = xmlHttp.responseXML.getElementsByTagName("percent")[0].firstChild.data;
                    alert(percent_complete);
                    alert("processResult=" + xmlHttp.status);
                    var index = processResult(percent_complete);
                    alert("pollCallback=" + xmlHttp.status);

                    for (var i = 1; i <= index;i++){
                        var elem = document.getElementById("block" + i);
                        elem.innerHTML = clear;

                        elem.style.backgroundColor = bar_color;
                        var next_cell = i + 1;
                        if (next_cell > index && next_cell <= 9){
                            document.getElementById("block" + next_cell).innerHTML = percent_complete + "%";
                        }
                    }

                    if (index < 9){
                        setTimeout("pollServer()",2000);
                    }else{
                        document.getElementById("complete").innerHTML = "Complete!";
                        document.getElementById("go").disabled = false;
                    }
                }
            }
        }

        //解析结果
        function processResult(){
            var ind;
            if (percent_complete.length == 1){
                ind = 1;
            }else if (percent_complete.length == 2){
                ind = percent_complete.substring(0,1);
            }else{
                ind = 9;
            }

            return ind;
        }

        //检查提示层信息
        function checkDiv(){
            var progress_bar = document.getElementById("progressBar");
            if (progress_bar.style.visibility == "visible"){
                //清除进度条
                clearBar();
                document.getElementById("complete").innerHTML = "";
            }else{
                progress_bar.style.visibility = "visible";
            }
        }

        //清除进度条
        function clearBar(){
            for (var i = 1; i < 10; i++){
                var elem = document.getElementById("block" + i);
                elem.innerHTML = clear;
                elem.style.backgroundColor = "white";
            }
        }
    </script>
</head>
<body bgcolor="#ffffff">
<h1>
    Ajax进度条示例
</h1>
运行进度条:
<input type="button" value="运行" id="go" onclick="go()"/>
<p>
</p>
<table align="center">
    <tbody>
    <tr>
        <td><div id="progressBar" style="padding:2px; border:solid black 2px; visibility:hidden">
            <span id="block1">&nbsp;&nbsp;&nbsp;</span>
            <span id="block2">&nbsp;&nbsp;&nbsp;</span>
            <span id="block3">&nbsp;&nbsp;&nbsp;</span>
            <span id="block4">&nbsp;&nbsp;&nbsp;</span>
            <span id="block5">&nbsp;&nbsp;&nbsp;</span>
            <span id="block6">&nbsp;&nbsp;&nbsp;</span>
            <span id="block7">&nbsp;&nbsp;&nbsp;</span>
            <span id="block8">&nbsp;&nbsp;&nbsp;</span>
            <span id="block9">&nbsp;&nbsp;&nbsp;</span>
        </div></td>
    </tr>
    <tr><td align="center" id="complete"></td> </tr>
    </tbody>
</table>
</body>
</html>
 