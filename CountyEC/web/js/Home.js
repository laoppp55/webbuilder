$(document).ready(function() {
    htmlobj = $.ajax({
        url: "http://www.gugulx.com/getProvinces.do",
        dataType: 'json',
        async: false,
        success: function (data) {
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
            selcitys(10);
        }
    });
    $("#toppro ul li a").click(function () {
        var txt = $(this).text();
        $("#toppro span").html(txt);
        $("#topTxt").html(txt);
        var value = $(this).attr("rel");
        $("#area").val(value);
        selcitys(value);
    });
});
function selcitys(provinceid) {
    htmlobj=$.ajax({
        url:"http://www.gugulx.com/getCitys.do",
        data:{"provinceid":provinceid},
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
    });

    //$("#keyword").val("请输入查询关键字");
}

function search_home(page){
    var keyword=$("#keyword").val();
    var area = $("#area").val();

}

function open_njy(val,type) {
    window.open("http://cityside.coosite.com/list.shtml?area="+val+"&type=njy");
}