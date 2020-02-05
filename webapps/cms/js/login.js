        function loginUser()
        {
            var getloginuser = document.loginForm.user_id.value;
            if (getloginuser == "")
            {
                alert("请输入用户名");
                document.loginForm.user_id.focus();
                return false;
            }
            var getloginpass = document.loginForm.user_pwd.value;
            if (getloginpass == "")
            {
                alert("请输入密码");
                document.loginForm.user_pwd.focus();
                return false;
            }
        }

