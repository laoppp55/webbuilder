function addfriend()
        {
            var getname = document.finduserForm.findusername.value;
            if (getname == "")
            {
                alert("请输入要加入的好友");
                document.finduserForm.findusername.focus();
                return false;
            }
            else
            {
                var objXml = new ActiveXObject("Microsoft.XMLHTTP");
                objXml.open("POST", "friendway.jsp?name="+getname,false);
                objXml.Send();
                var valuefriend = objXml.responseText;
                if (valuefriend.indexOf("1") != -1)
                {
                    alert("不能加自己为好友");
                    document.finduserForm.findusername.value = "";
                    document.finduserForm.findusername.focus();
                    return false;
                }
                if(valuefriend.indexOf("2") != -1)
                {
                    alert("好友已经存在列表中");
                    document.finduserForm.findusername.value = "";
                    document.finduserForm.findusername.focus();
                    return false;
                }
                if(valuefriend.indexOf("3") != -1)
                {
                    alert("该用户不存在");
                    document.finduserForm.findusername.value = "";
                    document.finduserForm.findusername.focus();
                    return false;
                }
                if(valuefriend.indexOf("4") != -1)
                {
                    return true;
                }
            }
        }