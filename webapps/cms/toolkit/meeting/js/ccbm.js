$('#btn').click(function() {
    var curNum = $('#table tr').length;
    var html='<tr>'
        +'<td><input type="text" class="name" name="name'+curNum+'"/></td>'
        +'<td><input type="text" class="dept" name="dept'+curNum+'"/></td>'
        +'<td><input type="text" class="phone" name="phone'+curNum+'"/></td>'
        +'<td><input type="text" class="fax" name="fax'+curNum+'" /></td>'
        +'<td><input type="text" class="mail" name="mail'+curNum+'"/></td>'
        +'<td><label>'
        + '<select name="select'+curNum+'" id="select'+curNum+ '" onchange="getaddress('+curNum+')">'
        + '</select>'
        + '</label></td>'
        + '<td><span id="address'+curNum+'"></span></td>'
        + '<td onClick="getDel(this)"><a href="javascript:void(0);">删除</a></td>'
        + '</tr>';
    $('#table').append(html);
    getTime(curNum);
    $('#fee').html(1000*curNum);
    $('#fees').val(1000*curNum);
    $('#curNum').val(curNum);
    setFee(1000*curNum);
})

function getDel(k){
    var curNum = $('#table tr').length;
    $('#fee').html(1000*(curNum-2));
    $('#fees').val(1000*(curNum-2));
    setFee(1000*(curNum-2));
    $('#curNum').val(curNum-2);
    $(k).parent().remove();
}

//获取时间地点
function getTime(i){
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "getTime.jsp", false);
    objXml.send(null);
    var retstr = objXml.responseText;
    $('#select'+i).html(retstr);
}

//获取培训会地点
function getaddress(i){
    var id=$("#select"+i).val();
    //alert(id);
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "getTime.jsp?id="+id, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    //document.getElementById("address"+i).innerHTML=retstr;
    $('#address'+i).html(retstr);
}

//获取费用大写
function setFee(fee){
    var objXml;
    if (window.ActiveXObject) {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        objXml = new XMLHttpRequest("");
    }
    objXml.open("POST", "getFee.jsp?fee="+fee, false);
    objXml.send(null);
    var retstr = objXml.responseText.replace(/\s/ig, '');
    $('#dfee').html(retstr);
}
