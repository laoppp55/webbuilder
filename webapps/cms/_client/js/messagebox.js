
    var messageBox={};
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*此为弹窗脚本文件  依赖于jquery实现  先引入jquery 后引入此脚本*/
    /*弹窗方法 确定和取消*/
    /*
     * str   string   要提示的文字信息
     * callback function  回调函数    根据回调函数返回的flag值执行相应操作
     * */

    /*方法调用
     * 例：messageBox.confirm("您确定删除吗"，function(flag){ <-- 逻辑代码--->})
     * */
    messageBox.confirm=function(str,callback){
        var tipStr='<div class="message-shade" style="position:fixed;width:100%;height:100%;left:0px;top:0px;background: #fff;opacity: .6;filter: alpha(opacity=60);display:block;z-index:1000;"></div>'
            +'<div class="tipBox" style="width: 300px;height: 140px;position: fixed; top: 50%;left: 50%;margin-left: -150px;margin-top: -70px;z-index: 1500;background-color: #fff;border: 1px solid #cdcdcd;">'
            +'<img style="width:30px;height: 30px;position: absolute;left: 57px;top: 30px;" src="/images/messageBox/icon_warning.png" alt=""/>'
            +'<div style="width:180px;position: absolute;left: 100px;top: 34px;line-height: 20px;">'
            +'<span style="font-size: 14px;color:#666;font-family: 宋体">'+str+'</span>'
            +'</div>'
            +'<div style="text-align: center;position:absolute;left: 50%;bottom: 15px;margin-left: -90px;">'
            +'<span class="tipSure" style="display:inline-block; margin:0 15px; font-size: 15px; padding:0 15px;height:30px;line-height: 30px;background-color: #ef7900;border-radius: 15px;border:0 none;color:#fff;">确定</span>'
            +'<span class="tipCancel" style="display:inline-block; margin:0 15px; font-size: 15px; padding:0 15px;height:30px;line-height: 30px;background-color: #ef7900;border-radius: 15px;border:0 none;color:#fff;">取消</span>'
            +'</div>'
            +'</div>';
        $("body").append($(tipStr));
        $(".tipSure").on("click",function(){
            flag=true;
            callback(flag);
            $(".tipBox").remove();
            $(".message-shade").remove();
        })
        $(".tipCancel").on("click",function(event){
            flag=false;
            callback(flag);
            $(".tipBox").remove();
            $(".message-shade").remove();
        })
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*弹窗消息  提示消息*/
    /*
     * str   string   要提示的文字信息
     * */

    /*方法调用
     * 例：messageBox.alert("您的验证码不正确!")
     * */
    messageBox.alert=function(str,callback){
        var tipStr='<div class="message-shade" style="position:fixed;width:100%;height:100%;left:0px;top:0px;background: #fff;opacity: .6;filter: alpha(opacity=60);display:block;z-index:1000;"></div>'
            +'<div class="tipBox" style="width: 300px;height: 140px;position: fixed; top: 50%;left: 50%;margin-left: -150px;margin-top: -70px;z-index: 1500;background-color: #fff;border: 1px solid #cdcdcd;">'
            +'<img style="width:30px;height: 30px;position: absolute;left: 57px;top: 30px;" src="/images/messageBox/icon_warning.png" alt=""/>'
            +'<div style="width:180px;position: absolute;left: 100px;top: 34px;line-height: 20px;">'
            +'<span style="font-size: 14px;color:#666;font-family: 宋体">'+str+'</span>'
            +'</div>'
            +'<div style="text-align: center;position:absolute;left: 50%;bottom: 15px;margin-left: -45px;">'
            +'<span class="tipSure" style="display:inline-block; margin:0 15px; font-size: 15px; padding:0 15px;height:30px;line-height: 30px;background-color: #ef7900;border-radius: 15px;border:0 none;color:#fff;">确定</span>'
            +'</div>'
            +'</div>';
        $("body").append($(tipStr));
        $(".tipSure").on("click",function(){
            $(".tipBox").remove();
            $(".message-shade").remove();
            if(callback){
            	callback();
            }
        })    
    }
   ////////////////////////////////////////////////////////////////////////////////////////////////////////
    messageBox.isSure=function($li,callback){
        if($(".isSure").length){
            $(".isSure").remove();
        }
        var flag;
        var hTop=$li.height();
        var str='<div class="isSure" style="width: 118px;height: 18px;border:1px solid #0083cd;text-align: center;line-height: 18px;font-size: 12px;color: #666;position: absolute;top:'+hTop+';right: 0;z-index:999">'
            +'<div class="tipSure" style="float:left;width: 59px;height: 18px;background-color:#0083cd;color: #fff;">确定</div>'
            +'<div class="tipCancel" style="float:right;width: 59px;height: 18px;background-color:#fff;">取消</div>'
            +'</div>';
        $li.append($(str));
        $(".isSure").animate({"height":"18px"},100);
        $li.find(".tipSure").on("click",function(event){
            flag=true;
            $li.find(".isSure").remove();
            callback(flag);
            var event = window.event || arguments.callee.caller.arguments[0];
            if(event.stopPropagation){
                event.stopPropagation();
            }else{
                event.cancelBubble=true;
            }
        });
        $li.find(".tipCancel").on("click",function(event){
            flag=false;
            $li.find(".isSure").remove();
            callback(flag);
            var event = window.event || arguments.callee.caller.arguments[0];
            if(event.stopPropagation){
                event.stopPropagation();
            }else{
                event.cancelBubble=true;
            }
        })
        var event = window.event || arguments.callee.caller.arguments[0];
        if(event.stopPropagation){
            event.stopPropagation();
        }else{
            event.cancelBubble=true;
        }
    }
    $(document).click(function(e){
        if($(".isSure").length){
            $(".isSure").remove();
        }
    });
