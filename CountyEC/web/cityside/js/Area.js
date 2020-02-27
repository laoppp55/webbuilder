$(document).ready(function(){
    htmlobj=$.ajax({
        url:"/_members/getProvinces.jsp",
        dataType:'json',
        async:false,
        success:function(data){
            var html="<span class=\"default-dest\" id=\"toppro_s\">北京市</span>";
            html+="<ul class=\"dest-list\" style=\"background-color:#f2f2f2;\">";
            var jj = 0;
            for(jj=0; jj < data.length; jj++) {
                var value=data[jj].CODE;
                var text=data[jj].PROVNAME;
                html=html+"<li class=\"dest-case\"><a href=\"javascript:void(0);\"  rel="+value+">"+text+"</a></li>";
            }
            html=html+"</ul>"
            $("#toppro").html(html);
            selcitys(10);
            getnjlList(1);
        }
    });
    $("#toppro ul li a").click(function(){
        var txt = $(this).text();
        $("#toppro span").html(txt);
        $("#topTxt").html(txt);
        var value = $(this).attr("rel");
        $("#area").val(value);
        selcitys(value);
        getnjlList(1);
    });

  /*  //清空市区
    $("#city span").remove();
    $("#city").html("<span class=\"default-dest_ch\">请选择市区</span>");

    //清空县
    $("#tcity span").remove();
    $("#tcity").html("<span class=\"default-dest_ch\">请选择县</span>");

    //清空乡
    $("#town span").remove();
    $("#town").html("<span class=\"default-dest_ch\">请选择乡</span>");*/

    //$("#keyword").val("请输入查询关键字");
});


function siteopen(sitename) {
    window.open("http://" + sitename + ".coosite.com");
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
        getnjlList(1);
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
        getnjlList(1);
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
        getnjlList(1);
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


var pageSize = 6;
var Request = new QueryString();
var area_home = Request["area"];
function getnjlList(page){
    var keyword=$("#keyword").val();
    var area = $("#area").val();
    if(area_home!=null) area = area_home;
    htmlobj=$.ajax({
        url:"getNjy.jsp?area="+area+"&keyword="+keyword+"&pageNo="+page,
        dataType:'json',
        async:false,
        success:function(data){
            var html="<div class=\"sub_con\"><div class=\"czy_list\">";
            for(var jj=0; jj < data.list.length; jj++) {
                var userId=data.list[jj].USERID;
                var company=data.list[jj].COMPANY;
                //var cityCode = data.list[jj].CITY;
                var zoneCode = data.list[jj].AREA;
                var townCode = data.list[jj].SHEQU;
                var villageCode = data.list[jj].JIEDAO;
                var i=getRandom(20);
                html=html+"<div class=\"czy_list_1\">"
                         +"<div style=\" background:url(images/pic/listpic_201631_"+i+".jpg) top no-repeat; width:464px; height:330px;\" onclick=\"javascript:siteopen('"+userId+"')\">"
                         +"<div class=\"pic_small\"><img src=\"images/20150527_4.png\" /></div>"
                         +"</div>"
                         +"<div class=\"pic_bt\"><a href=\"javascript:siteopen('"+userId+"')\">"+company+"</a></div>"
                         +"<div class=\"pic_js\">"
                         +"<div class=\"pic_js_1\"><a href=\"#\"><span id=\"zone"+jj+"\"></span></a>&nbsp;●&nbsp;<a href=\"#\"><span id=\"town"+jj+"\"></span></a>&nbsp;●&nbsp;<a href=\"#\"><span id=\"village"+jj+"\"></span></a></div>"
                         +"</div>"
                         +"<div class=\"pic_js\">"
                         +"<div class=\"pic_js_1\"><a href=\"javascript:siteopen('"+userId+"')\">整套房型：单人间10套/双人间20套/多人间5套</a></div>"
                         +"<div class=\"pic_js_2\"><span>★★★★★</span> 50条评论</div>"
                         +"</div>"
                         +"</div>"
                         +"<script>"
                        // +"getCityName("+cityCode+","+jj+");"
                         +"getAreaName("+zoneCode+","+jj+");"
                         +"getTownName("+townCode+","+jj+");"
                         +"getVillageName("+villageCode+","+jj+");"
                         +"</script>";
            }
            html=html+"<div class=\"clear\"></div></div>";
            $("#result").html(html);
            $("#paging").html(paging(page, data.count));
        }
    });
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
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getnjlList(" + prePage + ")\">上一页</a>\r\n";
        }
        var start = 1;
        if (page > 4) {
            start = prePage;
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getnjlList(1)\")>1</a>\r\n";
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getnjlList(2)\")>2</a>\r\n";
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
                html = html + "<a href=\"javascript:void(0)\" onclick=\"getnjlList(" + i + ")\">" + i + "</a>\r\n";
            }
        }
        if (end <  maxPage-2) {
            html = html + "…\r\n";
        }
        if (end < prePage) {
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getnjlList(" + maxPage - 1 + ")\">" + maxPage - 1 + "</a>\r\n";
        }

        if (end < maxPage) {
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getnjlList(" + maxPage + ")\">" + maxPage + "</a>\r\n";
        }
        if (page == maxPage) {
        } else {
            html = html + "<a href=\"javascript:void(0)\" onclick=\"getnjlList(" + nexPage+ ")\">下一页</a>\r\n";
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

