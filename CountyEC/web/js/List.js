var pageSize = 12;
var Request = new QueryString();
var area_home = Request["area"];
var type="njy";
var infotype = Request["type"];
if(infotype != null && infotype !=""){
    type = infotype;
}

/*
 $(document).ready(function(){
 creatediv(type);
 htmlobj=$.ajax({
 url:"/_members/getProvinces.jsp",
 dataType:'json',
 async:false,
 success:function(data) {
 var html = "<span class=\"default-dest\" id=\"toppro_s\">北京市</span>";
 html += "<ul class=\"dest-list\" style=\"background-color:#f2f2f2;\">";
 var jj = 0;
 for (jj = 0; jj < data.length; jj++) {
 var value = data[jj].CODE;
 var text = data[jj].PROVNAME;
 html = html + "<li class=\"dest-case\"><a href=\"javascript:void(0);\"  rel=" + value + ">" + text + "</a></li>";
 }
 html = html + "</ul>"
 $("#toppro").html(html);
 if (type == "mlxc") {
 selzones_mlxc(1001);
 } else {
 selcitys(10);
 }
 getList(1);
 }
 });

 $("#toppro ul li a").click(function(){
 var txt = $(this).text();
 $("#toppro span").html(txt);
 $("#topTxt").html(txt);
 var value = $(this).attr("rel");
 $("#area").val(value);
 selcitys(value);
 getList(1);
 });


 //清空市区
 $("#city span").remove();
 $("#city").html("<span class=\"default-dest_ch\">请选择市区</span>");

 //清空县
 $("#tcity span").remove();
 $("#tcity").html("<span class=\"default-dest_ch\">请选择县</span>");

 //清空乡
 $("#town span").remove();
 $("#town").html("<span class=\"default-dest_ch\">请选择乡</span>");

 //$("#keyword").val("请输入查询关键字");
 });

 */

function creatediv(type){
    $("#body").show();
    $("#country").hide();
    var menu="<a href=\"/\">首页</a>"
        +"<a href=\"#\"><span>农家乐</span></a>"
        +"<a href=\"list.shtml?type=mlxc\">美丽乡村</a>"
        +"<a href=\"/list.shtml?type=czy\">采摘园</a>"
        +"<a href=\"/list.shtml?type=jq\">景点景区</a>";
    $(".menu").html(menu);
    $("#banner").attr("class","sub_banner2");
    $("#typeTxt").html("农家乐");
    if(type == "czy"){
        menu="<a href=\"/\">首页</a>"
            +"<a href=\"list.shtml?type=njy\">农家乐</span></a>"
            +"<a href=\"list.shtml?type=mlxc\">美丽乡村</a>"
            +"<a href=\"#\"><span>采摘园</span></a>"
            +"<a href=\"list.shtml?type=jq\">景点景区</a>";
        $(".menu").html(menu);
        $("#banner").attr("class","sub_banner");
        $("#typeTxt").html("采摘园");
    }else if(type =="jq"){
        menu="<a href=\"/\">首页</a>"
            +"<a href=\"list.shtml?type=njy\">农家乐</a>"
            +"<a href=\"list.shtml?type=mlxc\">美丽乡村</a>"
            +"<a href=\"list.shtml?type=czy\">采摘园</a>"
            +"<a href=\"#\"><span>景点景区</span></a>";
        $(".menu").html(menu);
        $("#banner").attr("class","sub_banner1");
        $("#typeTxt").html("景点景区");
    }else if(type == "mlxc"){
        $("#body").hide();
        $("#country").show();
        menu="<a href=\"/\">首页</a>"
            +"<a href=\"list.shtml?type=njy\">农家乐</a>"
            +"<a href=\"#\"><span>美丽乡村</span></a>"
            +"<a href=\"list.shtml?type=czy\">采摘园</a>"
            +"<a href=\"list.shtml?type=jq\">景点景区</a>";
        $(".menu").html(menu);
        $("#banner").attr("class","country_banner");

    }
}

function siteopen(val) {
    var sitename="";
    htmlobj=$.ajax({
        url:"/getSiteInfo.do?siteId=" + val,
        dataType:'json',
        async:false,
        success:function(data){
            sitename = data.USERID;
        }
    });

    window.open("http://" + sitename + ".gugulx.com");
}

function selcitys(val) {
    htmlobj=$.ajax({
        url:"/_members/getCitys.jsp?provinceid=" + val,
        dataType:'json',
        async:false,
        success:function(data){
            var html="<span class=\"default-dest_ch\">请选择市区</span>";
            html+="<ul class=\"dest-list_ch\" style=\"background-color:#f2f2f2;\">";
            var jj = 0;
            for(jj=0; jj < data.length; jj++) {
                var value=data[jj].CODE;
                var text=data[jj].CITYNAME;
                html=html+"<li class=\"dest-case_ch\"><a href=\"javascript:void(0);\" rel="+value+">"+text+"</a></li>";
            }
            html=html+"</ul>";
            $("#city").html(html);
        }
    });
    $("#city ul li a").click(function(){
        var txt = $(this).text();
        $("#city span").html(txt);
        $("#topTxt").html(txt);
        var value = $(this).attr("rel");
        $("#area").val(value);
        selzones(value);
        getList(1);
    });
    //清空县
    $("#tcity span").remove();
    $("#tcity").html("<span class=\"default-dest_ch\">请选择县</span>");

    //清空乡
    $("#town span").remove();
    $("#town").html("<span class=\"default-dest_ch\">请选择乡</span>");

    //$("#keyword").val("请输入查询关键字");
}

function selzones(val) {
    htmlobj=$.ajax({
        url:"/_members/getZones.jsp?cityid=" + val,
        dataType:'json',
        async:false,
        success:function(data){
            var html="<span class=\"default-dest_ch\">请选择县</span>";
            html+="<ul class=\"dest-list_ch\" style=\"background-color:#f2f2f2;\">";
            var jj = 0;
            for(jj=0; jj < data.length; jj++) {
                var value=data[jj].CODE;
                var text=data[jj].ZONENAME;
                if(value != '100101' && value != '100102')
                    html=html+"<li class=\"dest-case_ch\"><a href=\"javascript:void(0);\" rel="+value+">"+text+"</a></li>";
            }
            html=html+"</ul>"
            $("#tcity").html(html);

        }
    });
    $("#tcity ul li a").click(function(){
        var txt = $(this).text();
        $("#tcity span").html(txt);
        $("#topTxt").html(txt);
        var value = $(this).attr("rel");
        $("#area").val(value);
        seltowns(value);
        getList(1);
    });

    //清空乡
    $("#town span").remove();
    $("#town").html("<span class=\"default-dest_ch\">请选择乡</span>");

    //$("#keyword").val("请输入查询关键字");

}

function seltowns(val) {
    htmlobj=$.ajax({
        url:"/_members/getTowns.jsp?zoneid=" + val,
        dataType:'json',
        async:false,
        success:function(data){
            var selObj = $("#town");
            var html="<span class=\"default-dest_ch\">请选择乡</span>";
            html+="<ul class=\"dest-list_ch\" style=\"background-color:#f2f2f2;\">";
            var jj = 0;
            for(jj=0; jj < data.length; jj++) {
                var value=data[jj].CODE;
                var text=data[jj].TOWNNAME;
                html=html+"<li class=\"dest-case_ch\"><a href=\"javascript:void(0);\" rel="+value+">"+text+"</a></li>";
            }
            html=html+"</ul>"
            $("#town").html(html);

        }
    });
    $("#town ul li a").click(function(){
        var txt = $(this).text();
        $("#town span").html(txt);
        $("#topTxt").html(txt);
        var value = $(this).attr("rel");
        $("#area").val(value);
        //selvillages(value);
        getList(1);
    });
    //$("#keyword").val("请输入查询关键字");
}

/*function selvillages(val) {
 htmlobj=$.ajax({
 url:"_members/getVillages.jsp?townid=" + val,
 dataType:'json',
 async:false,
 success:function(data){
 var selObj = $("#village");
 var jj = 0;

 //清空原来的搜有值
 var selOpt = $("#village option");
 selOpt.remove();

 //增加新的OPTION的值
 selObj.append("<option value=\"01\">请选择村</option>");
 for(jj=0; jj < data.length; jj++) {
 var value=data[jj].CODE;
 var text=data[jj].VILLAGENAME;
 selObj.append("<option value=\""+value+"\">"+text+"</option>");
 }

 }
 });
 //alert(htmlobj.responseText);
 }*/
function getCityName(val,i){
    htmlobj=$.ajax({
        url: "/_members/getCityName.jsp?code=" + val,
        dataType: 'json',
        async: false,
        success: function (data) {
            $("#city"+i).html(data);
        }
    });
}
function getAreaName(val,i){
    htmlobj=$.ajax({
        url: "/_members/getZoneName.jsp?code=" + val,
        dataType: 'json',
        async: false,
        success: function (data) {
            $("#zone"+i).html(data);
        }
    });
}
function getTownName(val,i){
    htmlobj=$.ajax({
        url: "/_members/getTownName.jsp?code="+val,
        dataType: 'json',
        async: false,
        success: function (data) {
            $("#town"+i).html(data);
        }
    });
}
function getVillageName(val,i) {
    htmlobj=$.ajax({
        url: "/_members/getVillageName.jsp?code="+val,
        dataType: 'json',
        async: false,
        success: function (data) {
            $("#village"+i).html(data);
        }
    });

}

function getList(page){
    var keyword=$("#keyword").val();
    var area = $("#area").val();
    if(area_home!=null) area = area_home;

    if (typeof(area) == "undefined") {
        area = "";
    }

    if (typeof(keyword) == "undefined") {
        keyword = "";
    }

    //检查移动电话号码是否存在
    $.post("getResultList.do",
        {
            area:encodeURI(area),
            keyword:encodeURI(keyword),
            pageNo:page,
            type:type
        },
        function(data) {
            var html = "<ul>";
            var objstr = JSON.stringify(data);
            for (var jj = 0; jj < data.corpInfoList.length; jj++) {
                var siteId = data.corpInfoList[jj].SITEID;
                var company = data.corpInfoList[jj].COMPANYNAME;
                //var cityCode = data.corpInfoList[jj].CITY;
                var zoneCode = data.corpInfoList[jj].ZONE;
                //var townCode = data.corpInfoList[jj].TOWN;
                //var villageCode = data.corpInfoList[jj].VILLAGE;
                var  villageCode= data.corpInfoList[jj].VILLAGE;
                var  townCode= data.corpInfoList[jj].TOWN;

                var i = getRandom(20);
                var xc_siteId = getxc_siteId(townCode);
                html = html + "<li>" +
                    "<a href=\"javascript:siteopen('" + siteId + "');\" class=\"pic\"><img src=\"/images/pic/listpic_20" + i + ".jpg\" width=\"316\" height=\"203\" /></a>" +
                    "<a href=\"javascript:siteopen('" + siteId + "')\" class=\"txt\">" + company + "</a>";
                if( xc_siteId != null && xc_siteId!= 0){
                    html = html +"<a href=\"javascript:void(0)\" onclick='siteopen(" + xc_siteId + ")'><span id=\"village" + jj + "\"></span></a>"
                }else{
                    html = html + "<span id=\"village" + jj + "\"></span>"
                }
                //if( xc_siteId != null && xc_siteId!= 0)
                //    html = html + "<a href=\"#\" class=\"adress\">房山区</a> ● <a href=\"#\" class=\"adress\">史家营乡</a> ● <a href=\"#\" class=\"adress\">柳林水</a>" +
                //else
                //    html = html + "<span id=\"village" + jj + "\"></span>";
                html = html + "</li>";
            }
            html = html + "</ul>";
            //alert(html);
            $("#result").html(html);
            $("#paging").html(paging(page, data.count));
        },"json"
    )
}


function paging(page, count){
    var maxPage = (count % pageSize) == 0 ? (count / pageSize) : (parseInt(count / pageSize) + 1);
    var nexPage = maxPage,prePage = 1;
    if(page > 1) prePage = page - 1;
    if(page < maxPage) nexPage = page + 1;
    var html = "";
    if(count != 0) {
        if (page > maxPage) page = maxPage;
        if (page < 1) page = 1;

        if (page == 1) {
        } else {
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(" + prePage + ")\">上一页</a>\r\n";
        }
        var start = 1;
        if (page > 4) {
            start = prePage;
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(1)\")>1</a>\r\n";
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(2)\")>2</a>\r\n";
            html = html + "…\r\n";
        }
        var end = page+1;
        if (end > maxPage) {
            end = maxPage;
        }
        for (var i = start; i <= end; i++) {
            if (page == i) {
                html = html + "<a class=\"ts\" href=\"javascript:void(0)\">" + i + "</a>\r\n";
            } else {
                html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(" + i + ")\">" + i + "</a>\r\n";
            }
        }
        if (end <=  maxPage-2) {
            html = html + "…\r\n";
        }
        if (end < prePage) {
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(" + maxPage - 1 + ")\">" + maxPage - 1 + "</a>\r\n";
        }

        if (end < maxPage) {
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(" + maxPage + ")\">" + maxPage + "</a>\r\n";
        }
        if (page == maxPage) {
        } else {
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(" + nexPage+ ")\">下一页</a>\r\n";
        }
    }
    return html;
}

function searchpaging(page, count){
    var maxPage = (count % pageSize) == 0 ? (count / pageSize) : (parseInt(count / pageSize) + 1);
    var nexPage = maxPage,prePage = 1;
    if(page > 1) prePage = page - 1;
    if(page < maxPage) nexPage = page + 1;
    var html = "";
    if(count != 0) {
        if (page > maxPage) page = maxPage;
        if (page < 1) page = 1;

        if (page == 1) {
        } else {
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(" + prePage + ")\">上一页</a>\r\n";
        }
        var start = 1;
        if (page > 4) {
            start = prePage;
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(1)\")>1</a>\r\n";
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(2)\")>2</a>\r\n";
            html = html + "…\r\n";
        }
        var end = page+1;
        if (end > maxPage) {
            end = maxPage;
        }
        for (var i = start; i <= end; i++) {
            if (page == i) {
                html = html + "<a class=\"ts\" href=\"javascript:void(0)\">" + i + "</a>\r\n";
            } else {
                html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(" + i + ")\">" + i + "</a>\r\n";
            }
        }
        if (end <=  maxPage-2) {
            html = html + "…\r\n";
        }
        if (end < prePage) {
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(" + maxPage - 1 + ")\">" + maxPage - 1 + "</a>\r\n";
        }

        if (end < maxPage) {
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(" + maxPage + ")\">" + maxPage + "</a>\r\n";
        }
        if (page == maxPage) {
        } else {
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getList(" + nexPage+ ")\">下一页</a>\r\n";
        }
    }
    return html;
}

function getRandom(n){
    return Math.floor(Math.random()*n+1)
}

function QueryString() {
    var name, value, i;
    var str = location.href;
    var num = str.indexOf("?");
    str = str.substr(num + 1);
    var arrtmp = str.split("&");
    for (i = 0; i < arrtmp.length; i++) {
        num = arrtmp[i].indexOf("=");
        if (num > 0) {
            name = arrtmp[i].substring(0, num);
            value = arrtmp[i].substr(num + 1);
            //使value必须是int型数字
            //if(isNaN(parseInt(value))) value = 0;
            this[name] = value;
        }
    }
}


function selzones_mlxc(val) {
    htmlobj=$.ajax({
        url:"/_members/getZones.jsp?cityid=" + val,
        dataType:'json',
        async:false,
        success:function(data){
            var html="<div class=\"country_lei_1\">地区：<a href=\"javascript:void(0);\" rel=\"1001\">全部</a></div>";
            html+="<div class=\"country_lei_2\"><ul>";
            var jj = 0;
            for(jj=0; jj < data.length; jj++) {
                var value=data[jj].CODE;
                var text=data[jj].ZONENAME;
                if(value != '100101' && value != '100102') {
                    if( $("#area").val()==value){
                        html = html + "<li><a href=\"javascript:void(0);\" class='hovr' rel=\"" + value + "\" >" + text + "</a></li>";
                    }else {
                        html = html + "<li><a href=\"javascript:void(0);\" rel=\"" + value + "\" >" + text + "</a></li>";
                    }
                }
            }
            html=html+"</ul></div>"
            $("#country_lei").html(html);
        }
    });
    $("#country_lei a").click(function(){
        $("#country_lei a").removeClass("hovr");
        $(this).addClass('hovr');
        var value = $(this).attr("rel");
        $("#area").val(value);
        getList(1);
    });
}

function getxc_siteId(val){
    var siteId=0;
    htmlobj=$.ajax({
        url:"getxc_siteId.jsp?code=" + val,
        dataType:'json',
        async:false,
        success:function(data){
            siteId =data;
        }
    });
    return siteId;
}
