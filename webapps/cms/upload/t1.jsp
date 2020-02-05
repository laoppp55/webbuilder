<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>UploadiFive Test</title>
	<script src="../js/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="uploadify/jquery.uploadify.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="uploadify/uploadify.css">
	<style type="text/css">
		body {
			font: 13px Arial, Helvetica, Sans-serif;
		}
	</style>
</head>

<body>
<h1>Uploadify Demo</h1>
<form>
	<div id="queue"></div>
	<input id="file_upload" name="file_upload" type="file" multiple="true">
</form>

<script type="text/javascript">
    $(function() {
        $('#file_upload').uploadify({
            'swf'          : 'uploadify/uploadify.swf',
            'uploader'    : 'uploadify/uploadify.php',
            //'buttonImg'   : 'uploadify/upload.png',      //浏览按钮的图片的路径 。
            'buttonText'  : ' 选择文件',
            'wmode'       : 'transparent',                 //设置该项为transparent 可以使浏览按钮的flash背景文件透明，并且flash文件会被置为页面的最高层。
            'height'      : 40,                              //设置浏览按钮的高度
            'width'       : 80                               //设置浏览按钮的宽度
        });
    });
</script>
</body>
</html>