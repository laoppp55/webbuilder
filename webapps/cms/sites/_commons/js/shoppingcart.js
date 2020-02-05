function insertcart(articleid) {
    var hostName = window.location.hostname;
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/doshopping.jsp?num=1&pid=" + articleid + "&sitename=" + hostName, false);
    objXml.send(null);

    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        alert(retstr);
    }
}
function checkAddress(sitename, login_to_url) {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/checkAddress.jsp", false);
    objXml.send(null);

    var retstr = objXml.responseText;
    if (retstr.length > 0 && retstr.indexOf("false") < 0) {

        //写入用户收货人信息
        if (retstr.indexOf("nologin") > 0) {
            alert("您还没有登录，请先登录！");
            //window.location="/" + sitename + "/_prog/login.jsp?refererurl=" + login_to_url;
            window.location = "/";
        } else {
            document.getElementById("alladdress").innerHTML = retstr;
            selectadd1();
        }
    }
    if (retstr.length > 0 && retstr.indexOf("false") > 0) {
        //没有则创建
        var add_address = "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='dot' class='biz_table'>"
                + "<tr>"
                + "<td width='40' rowspan='7' valign='top'><!--DWLayoutEmptyCell-->&nbsp;</td>"
                + "<td height='44' colspan='6' valign='middle' class='style1'>收货人信息</td>"
                + "</tr>"
                + "<tr>"
                + "<td width='72' height='38' align='right' valign='top'>收货人：</td>"
                + "<td colspan='5' valign='top' align='left'><input name='connname' type='text' size='14' /></td>"
                + "</tr>"
                + "<tr>"
                + "<td height='38' align='right' valign='top'>省份：</td>"
                + "<td width='115' valign='top' align='left'><label><input name=provice type=text>"
                + "</label></td>"
                + "<td width='92' valign='top' align='left'>区域：</td>"
                + "<td width='116' valign='top' align='left'><input name=city type=text></td>"
                + "<td width='70' valign='top' align='left'>地区：</td>"
                + "<td width='138' valign='top' align='left'><input name=zone type=text>"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td height='38' align='right' valign='top'>详细地址：</td>"
                + "<td colspan='5' valign='top' align='left'><input name='address' type='text' size='40' /></td>"
                + "</tr>"
                + "<tr>"
                + "<td height='38' align='right' valign='top'>邮政编码：</td>"
                + "<td valign='top'><input name='zip' type='text' size='14' /></td>"
                + "<td colspan='4' valign='top'>请正确填写邮编，以确保您的定单顺利送达</td>"
                + "</tr>"
                + "<tr>"
                + "<td height='38' align='right' valign='top'>移动电话：</td>"
                + "<td valign='top'><input name='mobile' type='text' size='14' /></td>"
                + "<td align='center' valign='top'>固定电话：</td>"
                + "<td valign='top'><input name='phone' type='text' size='14' /></td>"
                + "<td colspan='2' valign='top'><!--DWLayoutEmptyCell-->&nbsp;</td>"
                + "</tr>"
                + "<tr>"
                + "<td height='52' colspan='6' align='center' valign='top'><input type=button name=button value=\"提交\" onClick='javascript:conninfo1(0);'></td>"
                + "</tr>"
                + "</table>";
        document.getElementById("connaddr").innerHTML = add_address;
    }
}
function add1() {
    var add_address = "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='dot' class='biz_table'>"
            + "<tr>"
            + "<td width='40' rowspan='7' valign='top'><!--DWLayoutEmptyCell-->&nbsp;</td>"
            + "<td height='44' colspan='6' valign='middle' class='style1'>收货人信息</td>"
            + "</tr>"
            + "<tr>"
            + "<td width='72' height='38' align='right' valign='top'>收货人：</td>"
            + "<td colspan='5' valign='top' align='left'><input name='connname' type='text' size='14' /></td>"
            + "</tr>"
            + "<tr>"
            + "<td height='38' align='right' valign='top'>省份：</td>"
            + "<td width='115' valign='top' align='left'><label><input name=provice type=text>"
            + "</label></td>"
            + "<td width='92' valign='top' align='left'>区域：</td>"
            + "<td width='116' valign='top' align='left'><input name=city type=text></td>"
            + "<td width='70' valign='top' align='left'>地区：</td>"
            + "<td width='138' valign='top' align='left'><input name=zone type=text>"
            + "</td>"
            + "</tr>"
            + "<tr>"
            + "<td height='38' align='right' valign='top'>详细地址：</td>"
            + "<td colspan='5' valign='top' align='left'><input name='address' type='text' size='40' /></td>"
            + "</tr>"
            + "<tr>"
            + "<td height='38' align='right' valign='top'>邮政编码：</td>"
            + "<td valign='top'><input name='zip' type='text' size='14' /></td>"
            + "<td colspan='4' valign='top'>请正确填写邮编，以确保您的定单顺利送达</td>"
            + "</tr>"
            + "<tr>"
            + "<td height='38' align='right' valign='top'>移动电话：</td>"
            + "<td valign='top'><input name='mobile' type='text' size='14' /></td>"
            + "<td align='center' valign='top'>固定电话：</td>"
            + "<td valign='top'><input name='phone' type='text' size='14' /></td>"
            + "<td colspan='2' valign='top'><!--DWLayoutEmptyCell-->&nbsp;</td>"
            + "</tr>"
            + "<tr>"
            + "<td height='52' colspan='6' align='center' valign='top'><input type=button name=button value=\"提交\" onClick='javascript:conninfo1(0);'></td>"
            + "</tr>"
            + "</table>";
    document.getElementById("connaddr").innerHTML = add_address;
    document.getElementById("sendway").innerHTML = "";
    document.getElementById("payway").innerHTML = "";
    document.getElementById("productinfo").innerHTML = "";
}
function conninfo1(type) {
    if (document.confirmform.connname.value == null || document.confirmform.connname.value == "") {
        alert("请输入收货人姓名");
        document.confirmform.connname.focus();
        return false;
    }
    if (document.confirmform.address.value == null || document.confirmform.address.value == "") {
        alert("请输入详细地址");
        document.confirmform.address.focus();
        return false;
    }
    if (document.confirmform.zip.value == null || document.confirmform.zip.value == "") {
        alert("请输入正确的邮政编码");
        document.confirmform.zip.focus();
        return false;
    }
    if (document.confirmform.mobile.value == null || document.confirmform.mobile.value == "") {
        alert("请输入手机号码");
        document.confirmform.mobile.focus();
        return false;
    }
    if (document.confirmform.phone.value == null || document.confirmform.phone.value == "") {
        alert("请输入电话号码");
        document.confirmform.phone.focus();
        return false;
    }
    var connname = document.confirmform.connname.value;
    var prov = document.confirmform.provice.value;
    var city = document.confirmform.city.value;
    var zone = document.confirmform.zone.value;
    var zip = document.confirmform.zip.value;
    var mobile = document.confirmform.mobile.value;
    var phone = document.confirmform.phone.value;
    var address = document.confirmform.address.value;

    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/sendinfo1.jsp?id=" + type + "&connname=" + connname + "&prov=" + prov + "&city=" + city + "&zone=" + zone + "&zip=" + zip + "&mobile=" + mobile + "&phone=" + phone + "&address=" + address, false);
    objXml.send(null);

    var retstr = objXml.responseText;
    if (retstr.length > 0) {
        document.getElementById("alladdress").innerHTML = retstr;
        document.getElementById("connaddr").innerHTML = "";
        selectadd1();
    }
    else {
        alert("添加失败！");
    }
}
function edit(id) {
    if (id == 0) {
        alert("地址为空！！！不能进行修改操作！")
        return false;
    } else {
        var objXml;
        if (window.ActiveXObject) {
            objXml = new ActiveXObject("Microsoft.XMLHTTP");
        } else if (window.XMLHttpRequest) {
            objXml = new XMLHttpRequest("");
        }
        objXml.open("POST", "/_commons/editadd.jsp?id=" + id, false);
        objXml.send(null);

        var retstr = objXml.responseText;
        if (retstr != null && retstr.length > 0) {
            document.getElementById("connaddr").innerHTML = retstr;
            document.getElementById("sendway").innerHTML = "";
            document.getElementById("payway").innerHTML = "";
            document.getElementById("productinfo").innerHTML = "";
        }
    }
}
function del(id) {
    if (id == 0) {
        alert("地址为空！！！不能进行删除操作！")
        return false;
    } else {
        var val;
        val = confirm("你确定要删除吗？");
        if (val == 1) {
            var objXml;
            if (window.ActiveXObject) {
                objXml = new ActiveXObject("Microsoft.XMLHTTP");
            } else if (window.XMLHttpRequest) {
                objXml = new XMLHttpRequest("");
            }
            objXml.open("POST", "/_commons/delete.jsp?id=" + id + "&startflag=1", false);
            objXml.send(null);

            var retstr = objXml.responseText;
            if (retstr != null && retstr.length > 0 && retstr.indexOf("true") < 0 && retstr.indexOf("false") < 0) {
                alert("删除成功！");
                document.getElementById("alladdress").innerHTML = retstr;
            }
            if (retstr != null && retstr.length > 0 && retstr.indexOf("true") > 0) {
                alert("删除成功！");
                document.getElementById("alladdress").innerHTML = "            <table width='90%' border='0' cellpadding='0' cellspacing='0' class='biz_table'>\n" +
                                                                  "                <!--DWLayoutTable-->\n" +
                                                                  "                <tr>\n" +
                                                                  "                  <td height='41' valign='middle' style='background:#F1F1F1; color:#3F3F3F; padding-left:20px;'><!--DWLayoutEmptyCell-->&nbsp;</td>\n" +
                                                                  "                  <td height='41' valign='middle' style='background:#F1F1F1; color:#3F3F3F; padding-left:20px;'>姓名</td>\n" +
                                                                  "                  <td height='41' valign='middle' style='background:#F1F1F1; color:#3F3F3F; padding-left:20px;'>省份</td>\n" +
                                                                  "                  <td width='56' align='center' valign='middle' style='background:#F1F1F1; color:#3F3F3F; '>区域</td>\n" +
                                                                  "                  <td width='170' align='center' valign='middle' style='background:#F1F1F1; color:#3F3F3F;'>地址</td>\n" +
                                                                  "                  <td width='63' align='center'  valign='middle' style='background:#F1F1F1; color:#3F3F3F;;'>邮编</td>\n" +
                                                                  "                  <td width='66' align='center'  valign='middle' style='background:#F1F1F1; color:#3F3F3F;;'>电话</td>\n" +
                                                                  "                  <td width='109' align='center'  valign='middle' style='background:#F1F1F1; color:#3F3F3F;;'><!--DWLayoutEmptyCell-->&nbsp;</td>\n" +
                                                                  "                </tr>" +
                                                                  "<tr><td height='37' colspan='8' align='center' valign='middle' style='border-bottom:#AFAFAF 1px solid;color:#61749F; padding-left:20px;'><span style='color:#333333;border-bottom:#AFAFAF 1px solid;'>您没有提供收货地址，请填写您的详细收货信息后进行下一步操作。</span></td></tr><!--DWLayoutTable--></table>";
                document.getElementById("connaddr").innerHTML = "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='dot' class='biz_table'>"
                        + "<tr>"
                        + "<td width='40' rowspan='7' valign='top'><!--DWLayoutEmptyCell-->&nbsp;</td>"
                        + "<td height='44' colspan='6' valign='middle' class='style1'>收货人信息</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td width='72' height='38' align='right' valign='top'>收货人：</td>"
                        + "<td colspan='5' valign='top' align='left'><input name='connname' type='text' size='14' /></td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td height='38' align='right' valign='top'>省份：</td>"
                        + "<td width='115' valign='top' align='left'><label><input name=provice type=text>"
                        + "</label></td>"
                        + "<td width='92' valign='top' align='left'>区域：</td>"
                        + "<td width='116' valign='top' align='left'><input name=city type=text></td>"
                        + "<td width='70' valign='top' align='left'>地区：</td>"
                        + "<td width='138' valign='top' align='left'><input name=zone type=text>"
                        + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td height='38' align='right' valign='top'>详细地址：</td>"
                        + "<td colspan='5' valign='top' align='left'><input name='address' type='text' size='40' /></td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td height='38' align='right' valign='top'>邮政编码：</td>"
                        + "<td valign='top'><input name='zip' type='text' size='14' /></td>"
                        + "<td colspan='4' valign='top'>请正确填写邮编，以确保您的定单顺利送达</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td height='38' align='right' valign='top'>移动电话：</td>"
                        + "<td valign='top'><input name='mobile' type='text' size='14' /></td>"
                        + "<td align='center' valign='top'>固定电话：</td>"
                        + "<td valign='top'><input name='phone' type='text' size='14' /></td>"
                        + "<td colspan='2' valign='top'><!--DWLayoutEmptyCell-->&nbsp;</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td height='52' colspan='6' align='center' valign='top'><input type=button name=button value=\"提交\" onClick='javascript:conninfo1(0);'></td>"
                        + "</tr>"
                        + "</table>";
                document.getElementById("sendway").innerHTML = "";
                document.getElementById("payway").innerHTML = "";
                document.getElementById("productinfo").innerHTML = "";
            }
            if (retstr != null && retstr.length > 0 && retstr.indexOf("false") > 0) {
                alert("删除失败！");
            }
        }
    }
}
function selectadd(id) {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/selectadd.jsp?id=" + id, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        document.getElementById("sendway").innerHTML = retstr;
        document.getElementById("connaddr").innerHTML = "";
        document.getElementById("payway").innerHTML = "";
        document.getElementById("productinfo").innerHTML = "";
    }
}
function selectadd1() {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/selectadd1.jsp", false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        document.getElementById("sendway").innerHTML = retstr;
        document.getElementById("connaddr").innerHTML = "";
        document.getElementById("payway").innerHTML = "";
        document.getElementById("productinfo").innerHTML = "";
    }
}
function selectSendway() {
    var sendway = 0;
    var len = confirmform.sendway.length;
    if (len == undefined) {
        if (confirmform.sendway.checked) {
            sendway = confirmform.sendway.value;
        }
    }
    else {
        for (var i = 0; i < len; i++) {
            if (confirmform.sendway[i].checked) {
                sendway = confirmform.sendway[i].value;
                break;
            }
        }
    }
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/selectsendway.jsp?sendway=" + sendway, false);
    objXml.send(null);
    var retstr = objXml.responseText;


    if (retstr != null && retstr.length > 0) {
        document.getElementById("sendway").innerHTML = retstr;
        var objXml1;
        if (window.ActiveXObject) {
            objXml1 = new ActiveXObject("Microsoft.XMLHTTP");
        } else if (window.XMLHttpRequest) {
            objXml1 = new XMLHttpRequest("");
        }
        objXml1.open("POST", "/_commons/getpayway.jsp", false);
        objXml1.send(null);
        var paywaystr = objXml1.responseText;
        if (paywaystr.length > 0) {
            document.getElementById("payway").innerHTML = paywaystr;
            document.getElementById("productinfo").innerHTML = "";
        }
    }

}
function editSendway(sendway) {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/editsendway.jsp?sendway=" + sendway, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        document.getElementById("sendway").innerHTML = retstr;
        document.getElementById("payway").innerHTML = "";
        document.getElementById("productinfo").innerHTML = "";
    }
}
function selectPayway() {
    var payway = 0;
    var len = document.confirmform.payway.length;
    if (len == undefined) {
        if (document.confirmform.payway.checked) {
            payway = document.confirmform.payway.value;
        }
    }
    else {
        for (var i = 0; i < len; i++) {
            if (document.confirmform.payway[i].checked) {
                payway = document.confirmform.payway[i].value;
                break;
            }
        }
    }
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/selectpayway.jsp?payway=" + payway, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        document.getElementById("payway").innerHTML = retstr;
    }
    var objXml1;
    if (window.ActiveXObject) {
        objXml1 = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml1 = new XMLHttpRequest("");
    }
    ;
    objXml1.open("POST", "/_commons/productinfo.jsp", false);
    objXml1.send(null);
    var retstr1 = objXml1.responseText;
    if (retstr1 != null && retstr1.length > 0) {
        document.getElementById("productinfo").innerHTML = retstr1;
    }
}
function editPayway(payway) {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getpayway.jsp?payway=" + payway, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        document.getElementById("payway").innerHTML = retstr;
        document.getElementById("productinfo").innerHTML = "";
    }
}
function getval(sitename) {
    var b = document.getElementById("newtj");
    b.disabled = true;
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/createOrder.jsp?startflag=1", false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr.length > 0 && retstr.indexOf("false") == -1) {
        retstr = retstr.substring((retstr.indexOf("<orderid>") + 9), retstr.indexOf("</orderid>"));
        window.location = "/" + sitename + "/_prog/orderdisplay.jsp?orderid=" + retstr;
    }
    else {
        alert("提交定单失败");
        window.location = "/";
    }
}
function isNumber(num) {
    strRef = "1234567890";
    for (i = 0; i < num.length; i++)
    {
        tempChar = num.substring(i, i + 1);
        if (strRef.indexOf(tempChar, 0) == -1) {
            return false;
        }
    }
    return true;
}

function updateCart(pid, num, ssname) {
    if (!isNumber(num)) {
        alert("请输入一个大于等于0的整数！");
        return false;
    } else if (num > 0) { //更新数量
        var objXml;
        if (window.ActiveXObject) {
            objXml = new ActiveXObject("Microsoft.XMLHTTP");
        } else if (window.XMLHttpRequest) {
            objXml = new XMLHttpRequest("");
        }
        objXml.open("POST", "/_commons/update_shoppingcart.jsp?pid=" + pid + "&num=" + num, false);
        objXml.send(null);

        var retstr = objXml.responseText;
        /*if (retstr != null && retstr.length > 0 && retstr.indexOf("true") > -1) {
         shoppingcart(ssname);
         }*/
        window.location = "/" + ssname + "/_prog/shoppingcar.jsp";
    } else if (num == "0") { //删除商品
        delete_shoppingcart(pid, ssname);
    }
}
function shoppingcart(sname) {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/viewshopping.jsp?sitename=" + sname, false);
    objXml.send(null);

    var retstr = objXml.responseText;

    if (retstr != null && retstr.length > 0 && retstr.indexOf("false") < 0) {
        document.getElementById("shopping").innerHTML = retstr;
    }
    if (retstr != null && retstr.length > 0 && retstr.indexOf("false") > -1) {
        document.getElementById("shopping").innerHTML = "您还没有购买任何商品";
    }
}
function delete_shoppingcart(id, ssname) {
    var val;
    val = confirm("你确定要删除吗？");
    if (val == 1) {
        var objXml;
        if (window.ActiveXObject) {
            objXml = new ActiveXObject("Microsoft.XMLHTTP");
        } else if (window.XMLHttpRequest) {
            objXml = new XMLHttpRequest("");
        }
        objXml.open("POST", "/_commons/delete_shoppingcart.jsp?pid=" + id, false);
        objXml.send(null);

        var retstr = objXml.responseText;
        if (retstr != null && retstr.length > 0 && retstr.indexOf("true") > -1) {
            alert("删除商品成功！");
            window.location = "/" + ssname + "/_prog/shoppingcar.jsp";
        }
        if (retstr != null && retstr.length > 0 && retstr.indexOf("false") > -1) {
            alert("删除商品失败！");
        }
    }
}
function gobuy(sitename, login_to_url) {
    //window.location = "js.shtml";
    //alert(sitename);
    window.location = "/" + sitename + "/_prog/ordergenerate.jsp?loginurl=" + login_to_url;
}

function getUerOrderList(sitename) {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getUserOrderList.jsp", false);
    objXml.send(null);

    var retstr = objXml.responseText;
    if (retstr.indexOf("nologin") > -1) {
        alert("您还没有登录，请先登录！");
        //window.location = "/" + sitename + "/_prog/login.jsp";
        window.location = "/";
    } else {
        if (retstr != null && retstr.length > 0 && retstr.indexOf("false") == -1) {
            document.getElementById("orderlist").innerHTML = retstr;
        }
    }
}
function getAOrder() {
    String.prototype.getQueryString = function(name)
    {
        var reg = new RegExp("(^|&|\\?)" + name + "=([^&]*)(&|$)"), r;
        if (r = this.match(reg)) return unescape(r[2]);
        return null;
    }
    var httpUrl = document.URL;
    var userName_f1 = httpUrl.getQueryString("orderid");
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getAOrder.jsp?orderid=" + userName_f1, false);
    objXml.send(null);

    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0 && retstr.indexOf("false") == -1) {
        document.getElementById("aorder").innerHTML = retstr;
    }
}

function usescores(scores) {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/doScores.jsp?startflag=1&scores=" + scores, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        if (retstr.indexOf("false") > -1) {
            alert("您的积分不够！");
            return;
        } else {
            var objXml1;
            if (window.ActiveXObject) {
                objXml1 = new ActiveXObject("Microsoft.XMLHTTP");
            } else if (window.XMLHttpRequest) {
                objXml1 = new XMLHttpRequest("");
            }
            objXml1.open("POST", "/_commons/productinfo.jsp?", false);
            objXml1.send(null);
            var retstr1 = objXml1.responseText;
            if (retstr1 != null && retstr1.length > 0) {
                document.getElementById("productinfo").innerHTML = retstr1;
            }
        }
    }
}

function usecards(cards, productid) {
    window.open("/_commons/card.jsp?cards=" + cards + "&pid=" + productid, "", "left=400,top=400,width=300,height=150,status,scrollbars");
}

function getProductAddress(sitename, login_to_url, markid) {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/productaddress.jsp?markid=" + markid, false);
    objXml.send(null);

    var retstr = objXml.responseText;
    if (retstr.length > 0 && retstr.indexOf("false") < 0) {

        //写入用户收货人信息
        if (retstr.indexOf("nologin") > 0) {
            alert("您还没有登录，请先登录！");
            window.location = "/";
            // window.location="/" + sitename + "/_prog/login.jsp?refererurl=" + login_to_url;
        } else {
            document.getElementById("biz_address").innerHTML = retstr;
        }
    }
    if (retstr.length > 0 && retstr.indexOf("false") > 0) {
        //没有则创建
        var add_address = "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='dot' class='biz_table'>"
                + "<tr>"
                + "<td width='40' rowspan='7' valign='top'><!--DWLayoutEmptyCell-->&nbsp;</td>"
                + "<td height='44' colspan='6' valign='middle' class='style1'>收货人信息</td>"
                + "</tr>"
                + "<tr>"
                + "<td width='72' height='38' align='right' valign='top'>收货人：</td>"
                + "<td colspan='5' valign='top' align='left'><input name='connname' type='text' size='14' /></td>"
                + "</tr>"
                + "<tr>"
                + "<td height='38' align='right' valign='top'>省份：</td>"
                + "<td width='115' valign='top' align='left'><div id=\"provinceinfo\"></div>"
                + "</label></td>"
                + "<td width='92' valign='top' align='left'>区域：</td>"
                + "<td width='116' valign='top' align='left'><div id=\"cityinfo\"></td>"
                + "<td width='70' valign='top' align='left'>地区：</td>"
                + "<td width='138' valign='top' align='left'><div id=\"zoneinfo\">"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td height='38' align='right' valign='top'>详细地址：</td>"
                + "<td colspan='5' valign='top' align='left'><input name='address' type='text' size='40' /></td>"
                + "</tr>"
                + "<tr>"
                + "<td height='38' align='right' valign='top'>邮政编码：</td>"
                + "<td valign='top'><input name='zip' type='text' size='14' /></td>"
                + "<td colspan='4' valign='top'>请正确填写邮编，以确保您的定单顺利送达</td>"
                + "</tr>"
                + "<tr>"
                + "<td height='38' align='right' valign='top'>移动电话：</td>"
                + "<td valign='top'><input name='mobile' type='text' size='14' /></td>"
                + "<td align='center' valign='top'>固定电话：</td>"
                + "<td valign='top'><input name='phone' type='text' size='14' /></td>"
                + "<td colspan='2' valign='top'><!--DWLayoutEmptyCell-->&nbsp;</td>"
                + "</tr>"
                + "<tr>"
                + "<td height='52' colspan='6' align='center' valign='top'><input type=button name=button value=\"提交\" onClick='javascript:updateaddressinfo(0);'></td>"
                + "</tr>"
                + "</table>";
        document.getElementById("biz_address").innerHTML = add_address;
        //初始化省份 城市 地区

        //load province
        getprovinceinfo(-1);
        //load city
        getcityinfo(-1, "");
        //load zone
        getzoneinfo(-1, "");
    }
}
function getSendWay(markid, siteid) {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getSendway.jsp?markid=" + markid + "&siteid=" + siteid, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        document.getElementById("biz_sendway").innerHTML = retstr;

    }
}
function editaddress(id) {
    if (id == 0) {
        alert("地址为空！！！不能进行修改操作！");
        return false;
    } else {
        var objXml;
        if (window.ActiveXObject) {
            objXml = new ActiveXObject("Microsoft.XMLHTTP");
        } else if (window.XMLHttpRequest) {
            objXml = new XMLHttpRequest("");
        }
        objXml.open("POST", "/_commons/editaddress.jsp?id=" + id, false);
        objXml.send(null);

        var retstr = objXml.responseText;
        if (retstr != null && retstr.length > 0) {
            document.getElementById("biz_address").innerHTML = retstr;
            //load province
            getprovinceinfo(id);
            //load city
            getcityinfo(id, "");
            //load zone
            getzoneinfo(id, "");
        }
    }
}
function updateaddressinfo(type) {
    if (document.confirmform.connname.value == null || document.confirmform.connname.value == "") {
        alert("请输入收货人姓名");
        document.confirmform.connname.focus();
        return false;
    }
    if (document.confirmform.address.value == null || document.confirmform.address.value == "") {
        alert("请输入详细地址");
        document.confirmform.address.focus();
        return false;
    }
    if (document.confirmform.zip.value == null || document.confirmform.zip.value == "") {
        alert("请输入正确的邮政编码");
        document.confirmform.zip.focus();
        return false;
    }
    if (document.confirmform.mobile.value == null || document.confirmform.mobile.value == "") {
        alert("请输入手机号码");
        document.confirmform.mobile.focus();
        return false;
    }
    if (document.confirmform.phone.value == null || document.confirmform.phone.value == "") {
        alert("请输入电话号码");
        document.confirmform.phone.focus();
        return false;
    }
    var connname = document.confirmform.connname.value;
    var pobj = document.getElementById("province");
    var pindex = pobj.selectedIndex;
    var prov = pobj.options[pindex].value;
    //var prov = confirmform.provice.value;
    // var city = confirmform.city.value;
    var obj = document.getElementById("city");
    var index = obj.selectedIndex;
    var city = obj.options[index].value;

    //var zone = confirmform.zone.value;
    var zobj = document.getElementById("zone");
    var zindex = zobj.selectedIndex;
    var zone = zobj.options[zindex].value;
    var zip = document.confirmform.zip.value;
    var mobile = document.confirmform.mobile.value;
    var phone = document.confirmform.phone.value;
    var address = document.confirmform.address.value;
    var markid = document.confirmform.markid.value;
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/updateaddressinfo.jsp?id=" + type + "&connname=" + connname + "&prov=" + prov + "&city=" + city + "&zone=" + zone + "&zip=" + zip + "&mobile=" + mobile + "&phone=" + phone + "&address=" + address + "&markid=" + markid, false);
    objXml.send(null);

    var retstr = objXml.responseText;
    if (retstr.length > 0) {
        document.getElementById("biz_address").innerHTML = retstr;
    }
    else {
        alert("添加失败！");
    }
}
function submitSendway(markid, siteid) {
    var sendway = 0;
    var len = document.confirmform.sendway.length;
    if (len == undefined) {
        if (document.confirmform.sendway.checked) {
            sendway = document.confirmform.sendway.value;
        }
    }
    else {
        for (var i = 0; i < len; i++) {
            if (document.confirmform.sendway[i].checked) {
                sendway = document.confirmform.sendway[i].value;
                break;
            }
        }
    }
    var sendtime = 0;
    var sendtimelen = document.confirmform.linktime.length;
    if (sendtimelen == undefined) {
        if (document.confirmform.linktime.checked) {
            sendtime = document.confirmform.linktime.value;
        }
    }
    else {
        for (var sendtimei = 0; sendtimei < sendtimelen; sendtimei++) {
            if (document.confirmform.linktime[sendtimei].checked) {
                sendtime = document.confirmform.linktime[sendtimei].value;
                break;
            }
        }
    }
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/submitsendway.jsp?sendway=" + sendway + "&markid=" + markid + "&siteid=" + siteid + "&sendtime=" + sendtime, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        document.getElementById("biz_sendway").innerHTML = retstr;
    }
    getProductInfo(markid, siteid);
}
function getPayWay(markid, siteid) {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getpaywayinfo.jsp?markid=" + markid + "&siteid=" + siteid, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        document.getElementById("biz_payway").innerHTML = retstr;

    }
}
function submitPayway(markid, siteid) {
    var payway = 0;
    var len = document.confirmform.payway.length;
    if (len == undefined) {
        if (document.confirmform.payway.checked) {
            payway = document.confirmform.payway.value;
        }
    }
    else {
        for (var i = 0; i < len; i++) {
            if (document.confirmform.payway[i].checked) {
                payway = document.confirmform.payway[i].value;
                break;
            }
        }
    }
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/selectpaywayinfo.jsp?payway=" + payway + "&markid=" + markid + "&siteid=" + siteid, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        document.getElementById("biz_payway").innerHTML = retstr;
    }

}
function getProductInfo(markid, sitename) {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getproductinfo.jsp?markid=" + markid + "&siteid=" + sitename, false);
    objXml.send(null);
    var retstr = objXml.responseText;

    if (retstr != null && retstr.length > 0) {
        document.getElementById("biz_product").innerHTML = retstr;

    }
}
function usescore(scores, markid, siteid) {
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/doScores.jsp?startflag=1&scores=" + scores, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        if (retstr.indexOf("false") > -1) {
            alert("您的积分不够！");
            return;
        } else {
            getProductInfo(markid, siteid);
        }
    }
}
//get province info
function getprovinceinfo(addressid)
{
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getprovinceinfo.jsp?addressid=" + addressid, false);
    objXml.send(null);
    var retstr = objXml.responseText;

    if (retstr != null && retstr.length > 0) {
        document.getElementById("provinceinfo").innerHTML = retstr;
    }
}
//get city info
function getcityinfo(addressid, pname)
{
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getcityinfo.jsp?addressid=" + addressid + "&pname=" + pname, false);
    objXml.send(null);
    var retstr = objXml.responseText;

    if (retstr != null && retstr.length > 0) {
        document.getElementById("cityinfo").innerHTML = retstr;
    }
}
//get zone info
function getzoneinfo(addressid, cityname)
{
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getzoneinfo.jsp?addressid=" + addressid + "&cityname=" + cityname, false);
    objXml.send(null);
    var retstr = objXml.responseText;

    if (retstr != null && retstr.length > 0) {
        document.getElementById("zoneinfo").innerHTML = retstr;
    }
}
//select province
function selectprovince(pname) {
    //load city info
    getcityinfo(-1, pname);
    //get city name
    var obj = document.getElementById("city");
    var index = obj.selectedIndex;
    var cname = obj.options[index].value;
    //load zone info
    getzoneinfo(-1, cname);
}
//select city
function selectcity(cname) {
    //load zone
    getzoneinfo(-1, cname);
}
//get shoppingcar product numbers
function getShoppingCarProductNumbers()
{
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }

    objXml.open("POST", "/_commons/getproductnum.jsp", false);
    objXml.send(null);
    var retstr = objXml.responseText;

    if (retstr != null && retstr.length > 0) {
        document.getElementById("productnum").innerHTML = retstr;
        //document.write(retstr);
    }
}
//get order search result
function getordersearchresult(markid, startrow)
{
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getUserOrderList.jsp?markid=" + markid + "&startrow=" + startrow, false);
    objXml.send(null);
    var retstr = objXml.responseText;

    if (retstr != null && retstr.length > 0) {
        document.getElementById("biz_ordersearch_list").innerHTML = retstr;
        //document.write(retstr);
    }
}
//get order detail info
function getorderdetailresult(markid)
{
    //获得url参数
    String.prototype.getQueryString = function(name)
    {
        var reg = new RegExp("(^|&|\\?)" + name + "=([^&]*)(&|$)"), r;
        if (r = this.match(reg)) return unescape(r[2]);
        return null;
    }
    var httpUrl = document.URL;
    var orderid = httpUrl.getQueryString("orderid");

    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getOrderDetail.jsp?markid=" + markid + "&orderid=" + orderid, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        document.write(retstr);
    }

    /*if(retstr != null && retstr.length > 0){
     document.getElementById("biz_orderdetail_list").innerHTML = retstr;
     }*/
}
//update password
function updatepassword()
{
    var oldpasswd = document.getElementById("oldpasswd").value;
    var newpasswd = document.getElementById("newpasswd").value;
    var newpasswd_cf = document.getElementById("newpasswdcf").value;
    if (oldpasswd == null || oldpasswd == "") {
        alert("请输入旧密码！");
        return false;
    }
    if (newpasswd == null || newpasswd == "") {
        alert("请输入新密码！");
        return false;
    }
    if (newpasswd_cf == null || newpasswd_cf == "") {
        alert("请输入确认新密码！");
        return false;
    }
    if (newpasswd != newpasswd_cf) {
        alert("两次输入的密码不一致！");
        return false;
    }
    return true;
}
//get invoice info
function getInvoiceInfo(markid)
{
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getInvoiceInfo.jsp?markid=" + markid, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        document.getElementById("biz_invoiceinfo_list").innerHTML = retstr;
    }
}
//edit invoice info
function editinvoice(markid)
{
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/eidtInvoiceInfo.jsp?markid=" + markid, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    if (retstr != null && retstr.length > 0) {
        document.getElementById("biz_invoiceinfo_list").innerHTML = retstr;
    }
}
//submit invoice
function submitinvoice(markid)
{
    var invoicetype = 0;
    var len = document.confirmform.invoicetype.length;
    if (len == undefined) {
        if (document.confirmform.invoicetype.checked) {
            invoicetype = document.confirmform.invoicetype.value;
        }
    }
    else {
        for (var i = 0; i < len; i++) {
            if (document.confirmform.invoicetype[i].checked) {
                invoicetype = document.confirmform.invoicetype[i].value;
                break;
            }
        }
    }
    var title = 0;
    var titlelen = document.confirmform.title.length;
    if (titlelen == undefined) {
        if (document.confirmform.title.checked) {
            title = document.confirmform.title.value;
        }
    }
    else {
        for (var ti = 0; ti < titlelen; ti++) {
            if (document.confirmform.title[ti].checked) {
                title = document.confirmform.title[ti].value;
                break;
            }
        }
    }
    var companyname = document.confirmform.companyname.value;
    var content = 0;
    var contentlen = document.confirmform.content.length;
    if (contentlen == undefined) {
        if (document.confirmform.content.checked) {
            content = document.confirmform.content.value;
        }
    }
    else {
        for (var ci = 0; ci < contentlen; ci++) {
            if (document.confirmform.content[ci].checked) {
                content = document.confirmform.content[ci].value;
                break;
            }
        }
    }
    var identification = document.confirmform.identification.value;
    var registeraddress = document.confirmform.registeraddress.value;
    var phone = document.confirmform.phone.value;
    var bankname = document.confirmform.bankname.value;
    var bankaccount = document.confirmform.bankaccount.value;
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/submitinvoice.jsp?invoicetype=" + invoicetype + "&markid=" + markid + "&title=" + title + "&companyname=" + companyname + "&content=" + content + "&identification=" + identification + "&registeraddress=" + registeraddress + "&phone=" + phone + "&bankname=" + bankname + "&bankaccount=" + bankaccount, false);
    objXml.send(null);
    /*var retstr = objXml.responseText;
     if (retstr != null && retstr.length > 0) {
     document.getElementById("biz_invoiceinfo_list").innerHTML = retstr;
     }*/
    getInvoiceInfo(markid);
}
//use card
function usecard(cardnum, markid)
{
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/usecard.jsp?cardnum=" + cardnum, false);
    objXml.send(null);

    getProductInfo(markid, "");
}
//get tuan gou aritcle list
function getTuangouList(markType,articleID,columnID,siteid,sitename,imgflag,username,fragPath,isPreview,markID)
{
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "/_commons/getTuangouList.jsp?markType=" + markType+"&articleID="+articleID+"&columnID="+columnID+"&siteid="+siteid+"&sitename="+sitename+"&imgflag="+imgflag+"&username="+username+"&fragPath="+fragPath+"&isPreview="+isPreview+"&markID="+markID, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    document.write(retstr);
}