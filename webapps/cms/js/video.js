//定义多媒体文件播放标记
function f1(columnid) {
    //alert("hello word");
    var attrVal = "[TAG][PLAYMEDIA]";
    attrVal = attrVal + "[COLUMNID]" + columnid + "[/COLUMNID][WIDTH]" + markForm.width.value + "[/WIDTH]";
    attrVal = attrVal + "[HEIGHT]" + markForm.height.value + "[/HEIGHT]";
    attrVal = attrVal + "[MEDIATYPE]" + markForm.mediatype.value + "[/MEDIATYPE]";
    attrVal = attrVal + "[MEDIAPOSI]" + markForm.mediaposi.value + "[/MEDIAPOSI]";

    attrVal = attrVal + "[INNERHTMLFLAG]0[/INNERHTMLFLAG]";
    attrVal = attrVal + "[CHINESENAME]视频播放[/CHINESENAME][NOTES][/NOTES]";
    attrVal = attrVal + "[/PLAYMEDIA][/TAG]";

    markForm.content.value = attrVal;
    markForm.submit();
}

function Wa_SetImgAutoSize(img)
{
    //var img=document.all.img1;//获取图片
    var MaxWidth = 200;
    //设置图片宽度界限
    var MaxHeight = 100;
    //设置图片高度界限
    var HeightWidth = img.offsetHeight / img.offsetWidth;
    //设置高宽比
    var WidthHeight = img.offsetWidth / img.offsetHeight;
    //设置宽高比
    if (img.readyState != "complete") {
        return false;
        //确保图片完全加载
    }

    if (img.offsetWidth > MaxWidth) {
        img.width = MaxWidth;
        img.height = MaxWidth * HeightWidth;
    }
    if (img.offsetHeight > MaxHeight) {
        img.height = MaxHeight;
        img.width = MaxHeight * WidthHeight;
    }
}

function CheckImg(img)
{
    var message = "";
    var MaxWidth = 1;
    //设置图片宽度界限
    var MaxHeight = 1;
    //设置图片高度界限

    if (img.readyState != "complete") {
        return false;
        //确保图片完全加载
    }
    if (img.offsetHeight > MaxHeight) message += "\r高度超额：" + img.offsetHeight;
    if (img.offsetWidth > MaxWidth) message += "\r宽度超额：" + img.offsetWidth;
}

function checkvideo()
{
    var getvideopic = document.videoForm.videopic.value;
    if(getvideopic=="")
    {
        alert("请选择视频截图！");
        return false
    }
    var getvideoname = document.videoForm.videoname.value;
    if(getvideoname=="")
    {
        alert("请填写视频名称！");
        document.videoForm.videoname.focus();
        return false;
    }
    var getschoolname = document.videoForm.myschool.value;
    if(getschoolname=="")
    {
        alert("请填写学校名称！");
        document.videoForm.myschool.focus();
        return false;
    }
    var getupvideo = document.videoForm.upvideo.value;
    if(getupvideo=="")
    {
        alert("请上传视频后提交！");
        return false;
    }

    //document.videoForm.upsubmit.disabled = true;
}