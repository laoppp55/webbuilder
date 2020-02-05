<%@ page contentType="text/html;charset=utf-8"
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title></title>
	<script src="../ckeditor/ckeditor.js"></script>
	<script type="text/javascript">
        window.onload = function()
        {
            CKEDITOR.replace( 'description');
        };
	</script>
</head>
<body>
<form method="post" action="job/add">
	招聘岗位：<input type="text" name="position" id="position"/>
	招聘人数：<input type="text" name="quantity" id="quantity"/>
	学历要求：<input type="text" name="education" id="education"/>
	薪资：<input type="text" name="salary" id="salary"/>
	联系人：<input type="text" name="contact" id="contact"/>
	联系电话：<input type="text" name="telephone" id="telephone"/>
	描述：<textarea name="description" id="description"/></textarea>
	<input type="submit"/>
</form>
</body>
</html>