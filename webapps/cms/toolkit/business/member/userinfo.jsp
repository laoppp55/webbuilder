<script language="javascript">
    function show(name){
        if(document.all('layer'+name).style.visibility=='hidden'){
            document.all('layer'+name).style.visibility='visible'
        }else{
            document.all('layer'+name).style.visibility='hidden'
        }
    }
    function editpass(userid){
        window.open("editpass.jsp?userid="+userid,"修改密码","width=500,height=400,toolbar=no,top=200,left=200,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no");
    }
</script>
<table width="100%" border="0" cellpadding="2" cellspacing="1" height="571">
<tr bgcolor="#FFFFFF" align="left">
    <td class="txt"  align="left">

    </td>
    <td class="txt" >
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--font color="red">双击进行修改&nbsp;</font-->
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </td>
    <td >
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </td>
</tr>
<tr bgcolor="#FFFFFF" align="left">
    <td class="txt" align="center">用户真实姓名：</td>
    <td class="txt" >
        <!--a href="###"  onDblClick="show('username')"-->
            <%=username==null?"":StringUtil.gb2iso4View(username)%>
        <!--/a-->

    <td >
        <div id="layerusername" style="visibility:hidden;">
            <input type="text" name="username" size="14" value="<%=username==null?"":StringUtil.gb2iso4View(username)%>">
            <font color="#FF0000"> * </font>
        </div>
    </td>
</tr>
<tr bgcolor="#FFFFFF" align="left">
    <td class="txt" align="center">用户密码：</td>
    <td class="txt" >
        <a href="###"  onDblClick="editpass('<%=userid%>')">
            双击修改
            <!--/a-->
            <td >
            </td>
</tr>
<tr bgcolor="#FFFFFF" align="left">
    <td class="txt"  align="center">用户注册时间：</td>
    <td >
        <%=showdate%>
    </td>
    <td  class="txt">
        <font color="#FF0000"> 此项无法修改 </font>
    </td>

</tr>
<tr bgcolor="#FFFFFF" align="left">
    <td class="txt" align="center">email：</td>
    <td class="txt" >
        <!--a href="###"  onDblClick="show('email')"-->
            <%=email==null?"":StringUtil.gb2iso4View(email)%>
        <!--/a-->
    <td >
        <div id="layeremail" style="visibility:hidden;">
            <input type="text" name="email" size="20" value="<%=email==null?"":StringUtil.gb2iso4View(email)%>">

        </div>
    </td>
</tr>
<tr bgcolor="#FFFFFF" align="left">
    <td class="txt" align="center">性别：</td>
    <td >
        <!--a href="###"  onDblClick="show('sex')"-->
        <%if(sex==0){%>
        男
        <%}else{%>
        女
        <%}%>
        <!--/a-->
    </td>
    <td class="txt" >
        <div id="layersex" style="visibility:hidden;">
            <%if(sex==0){%>
            <select name="sex">
                <option value="-1">请选择</option>
                <option value="0" selected>男</option>
                <option value="1">女</option>
            </select>
            <%}else{%>
            <select name="sex">
                <option value="-1">请选择</option>
                <option value="0">男</option>
                <option value="1" selected>女</option>
            </select>
            <%}%>

        </div>
    </td>
</tr>

<tr bgcolor="#FFFFFF" align="left">
    <td class="txt"  align="center">职业：</td>
    <td >
        <!--a href="###"  onDblClick="show('occupation')"-->
        <%=occupation==null?"无资料":StringUtil.gb2iso4View(occupation)%>
        <!--/a-->
    </td>
    <td class="txt" >
        <div id="layeroccupation" style="visibility:hidden;">
            <select name="occupation">
                <%occupation = occupation==null?"":StringUtil.gb2iso4View(occupation);%>
                <option value="">请选择</option>
                <option value="学生" <%if((occupation != null)&&(occupation.equals("学生"))){%>selected<%}%>>学生</option>
                <option value="执行官/经理" <%if((occupation != null)&&(occupation.equals("执行官/经理"))){%>selected<%}%>>执行官/经理</option>
                <option value="专家" <%if((occupation != null)&&(occupation.equals("专家"))){%>selected<%}%>>专家</option>
                <option value="教授/老师" <%if((occupation != null)&&(occupation.equals("教授/老师"))){%>selected<%}%>>教授/老师</option>
                <option value="技术人员/工程师" <%if((occupation != null)&&(occupation.equals("技术人员/工程师"))){%>selected<%}%>>技术人员/工程师</option>
                <option value="服务人员" <%if((occupation != null)&&(occupation.equals("服务人员"))){%>selected<%}%>>服务人员</option>
                <option value="行政干部" <%if((occupation != null)&&(occupation.equals("行政干部"))){%>selected<%}%>>行政干部</option>
                <option value="销售/市场" <%if((occupation != null)&&(occupation.equals("销售/市场"))){%>selected<%}%>>销售/市场</option>
                <option value="艺术家" <%if((occupation != null)&&(occupation.equals("艺术家"))){%>selected<%}%>>艺术家</option>
                <option value="自由职业者" <%if((occupation != null)&&(occupation.equals("自由职业者"))){%>selected<%}%>>自由职业者</option>
                <option value="演员/歌星" <%if((occupation != null)&&(occupation.equals("演员/歌星"))){%>selected<%}%>>演员/歌星</option>
                <option value="失业" <%if((occupation != null)&&(occupation.equals("失业"))){%>selected<%}%>>失业</option>
                <option value="离/退休" <%if((occupation != null)&&(occupation.equals("离/退休"))){%>selected<%}%>>离/退休</option>
                <option value="主妇" <%if((occupation != null)&&(occupation.equals("主妇"))){%>checked<%}%>>主妇</option>
                <option value="普通职员" <%if((occupation != null)&&(occupation.equals("普通职员"))){%>selected<%}%>>普通职员</option>
                <option value="其它" <%if((occupation != null)&&(occupation.equals("其它"))){%>selected<%}%>>其它</option>
            </select>
        </div>
    </td>
</tr>
<tr bgcolor="#FFFFFF" align="left">
    <td class="txt" height="34" align="center">联系电话：</td>
    <td >
        <!--a href="###"  onDblClick="show('phone')"-->
        <%=(phone==null)?"无资料":phone%>
        <!--/a-->
    </td>
    <td class="txt" height="34">
        <div id="layerphone" style="visibility:hidden;">
            <input type="text" name="phone" size="20" value="<%=phone==null?"":StringUtil.gb2iso4View(phone)%>">
        </div>
    </td>
</tr>
<tr bgcolor="#FFFFFF" align="left">
    <td class="txt"  align="center">手机：</td>
    <td >
        <!--a href="###"  onDblClick="show('mobilephone')"-->
        <%=(mobilephone==null)?"无资料":mobilephone%>
        <!--/a-->
    </td>
    <td class="txt" >
        <div id="layermobilephone" style="visibility:hidden;">
            <input type="text" name="mobilephone" size="18" value="<%=mobilephone==null?"":mobilephone%>">
        </div>
    </td>
</tr>
<tr bgcolor="#FFFFFF" align="left">
    <td class="txt" height="34" align="center">邮寄地址：</td>
    <td >
        <!--a href="###"  onDblClick="show('address')"-->
        <%=address==null?"":StringUtil.gb2iso4View(address)%>
        <!--/a-->
    </td>
    <td class="txt" height="34">
        <div id="layeraddress" style="visibility:hidden;">
            <input type="text" name="address" size="20" value="<%=address==null?"":StringUtil.gb2iso4View(address)%>">

        </div>
    </td>
</tr>
<tr bgcolor="#FFFFFF" align="left">
    <td class="txt"  align="center">邮政编码：</td>
    <td >
        <!--a href="###"  onDblClick="show('postcode')"-->
        <%=postcode==null?"":postcode%>
        <!--/a-->
    </td>
    <td  class="txt">
        <div id="layerpostcode" style="visibility:hidden;">
            <input type="text" name="postcode" size="13" value="<%=postcode==null?"":postcode%>">
        </div>
    </td>
</tr>
<tr bgcolor="#FFFFFF" align="left">
    <td class="txt"  align="center">出生日期：</td>
    <td >
        <!--a href="###"  onDblClick="show('birthday')"-->
        <%=birthday_year%>年<%=birthday_month%>月<%=birthday_day%>日
        <!--/a-->
    </td>
    <td class="txt" >
        <div id="layerbirthday" style="visibility:hidden;">
            <input type="text" name="birthday_year" maxlength="4" size="4" value=<%=birthday_year%>>
            年
            <input name="birthday_month" type="text" maxlength="2" size="3" value=<%=birthday_month%>>
            月
            <input type="text" name="birthday_day" maxlength="2" size="3" value=<%=birthday_day%>>
            日
        </div>
    </td>
</tr>

<tr bgcolor="#FFFFFF" align="left">
    <td class="txt"  align="center">用户状态：</td>
    <td >
        <!--a href="###"  onDblClick="show('lockflag')"-->
        <%if(lockflag == 0){%>可用<%}%>
        <%if(lockflag == 1){%>暂停订购<%}%>
        <%if(lockflag == 2){%>暂停登陆<%}%>
        <!--/a-->
    </td>
    <td>
        <div id="layerlockflag" style="visibility:hidden;">
            <select name="lockflag">
                <option value="0" <%if(lockflag == 0){%>selected<%}%>>可用</option>
                <option value="1" <%if(lockflag == 1){%>selected<%}%>>暂停订购</option>
                <option value="2" <%if(lockflag == 2){%>selected<%}%>>暂停登陆</option>
            </select>
        </div>
    </td>
</tr>
</table>

